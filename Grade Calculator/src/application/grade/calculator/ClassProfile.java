package application.grade.calculator;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import application.grade.calculator.adapters.GalleryAdapter;
import application.grade.calculator.database.Database;

public class ClassProfile extends Activity {

	ExpandableListView exlistview;
    ExpandableListAdapter mAdapter;
    int class_id;
    String class_name;
    int class_pic;
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.classprofile);
        
        //Getting class name, pic, and grade to display, getting id to access database info on this class
        Intent i = getIntent();
        class_id = i.getIntExtra(GradeCalculator.CLASS_ID, 0);
        class_name = i.getStringExtra(GradeCalculator.CLASS_NAME);
        class_pic =  i.getIntExtra(GradeCalculator.CLASS_PIC, 0);
        
        //setup views
        exlistview = (ExpandableListView)findViewById(R.id.ExpandableListView01);
        
        //Display class name
        TextView text = (TextView)findViewById(R.id.TextView01);
        text.setText(class_name);
       
        //Display class grade
        Database database = new Database(this,this);
        SQLiteDatabase db = database.getWritableDatabase();
        float gj = database.updateCourseGrades(class_id);
        Cursor cur = db.query(Database.CLASSES_TABLE, null, Database._ID+" = "+class_id, null, null, null, null);
        TextView text1 = (TextView)findViewById(R.id.TextView03);
        text1.setText("0%");
        if(cur.moveToFirst()){
        float f = cur.getFloat(cur.getColumnIndex(Database.TOTAL_GRADE));
        text1.setText(String.format("%3.1f", f));
        }
         
        ((ImageView)findViewById(R.id.ImageView01)).setImageResource(GalleryAdapter.pic[cur.getInt(cur.getColumnIndex(Database.PIC))]);
        
        // Set up our adapter
        mAdapter = new MyExpandableListAdapter(this,class_id);
        exlistview.setAdapter(mAdapter);
        registerForContextMenu(exlistview);
    }

    /*
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Sample menu");
        menu.add(0, 0, 0, R.string.expandable_list_sample_action);
    }
	
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

        String title = ((TextView) info.targetView).getText().toString();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition); 
            Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos,
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
	*/
    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids. 
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     *
     */
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    	
    	private Cursor cur;
    	private SQLiteDatabase db;
    	private Activity act;
    	private Cursor gradeCursor;
    	private int class_id;
    	
    	MyExpandableListAdapter(Activity act, int class_id){
            Database database = new Database(act,act);
            this.db = database.getWritableDatabase();
            Cursor cur = db.query(Database.COMPONENTS_TABLE, null, Database.CLASS_ID+" = "+class_id, null, null, null, null);

            this.act = act;
            this.class_id=class_id;
            act.startManagingCursor(cur);
            //act.startManagingCursor(gradeCursor);
    		this.cur = cur;    		
    	}

        public View getChild(int groupPosition, int childPosition) {
        	cur.moveToPosition(groupPosition);
        	final int  id = cur.getInt(cur.getColumnIndex(Database._ID));
        	final Cursor gradeCursor = db.query(Database.GRADE_TABLE, null, Database.COMPONENTS_ID+" = "+id, null, null, null, null);
        	act.startManagingCursor(gradeCursor);
        	gradeCursor.moveToPosition(childPosition);
        	
    		LayoutInflater inflater = (LayoutInflater)act.getLayoutInflater();
    		View view = inflater.inflate(R.layout.indivgradeview, null);
    		TextView text = (TextView)view.findViewById(R.id.TextView03);
    		TextView text1 = (TextView)view.findViewById(R.id.TextView01);
    		TextView text2 = (TextView)view.findViewById(R.id.TextView02);
    		ImageView image = (ImageView)view.findViewById(R.id.ImageView01);
    		
    		final int grade_id = gradeCursor.getInt(cur.getColumnIndex(Database._ID));
    		image.setOnClickListener(new OnClickListener(){

				public void onClick(View v) {
					
					final Dialog dialog = new Dialog(ClassProfile.this);
					//dialog.setTitle(ClassProfile.this.getResources().getString(R.string.deleteGrade));
					dialog.setCancelable(true);
					dialog.setContentView(R.layout.deletegrade);
					Button positive = (Button)dialog.findViewById(R.id.Button01);
					Button negative = (Button)dialog.findViewById(R.id.Button02);

					positive.setOnClickListener(new OnClickListener(){

						public void onClick(View v) {
							Database data = new Database(ClassProfile.this,ClassProfile.this);
							db.delete(Database.GRADE_TABLE, Database._ID+" = "+grade_id, null); 
							data.updateComponentGrades(id);
							data.updateCourseGrades(class_id);
							data.close();
							cur.requery();
							gradeCursor.requery();
							notifyDataSetChanged();
							ClassProfile.this.onCreate(null);
				    		notifyDataSetChanged();
			                dialog.dismiss();							
						}
					});
					
					negative.setOnClickListener(new OnClickListener(){
						 public void onClick(View v) {
				                dialog.dismiss();
							}
					});
					dialog.show();
					          
				}
    		});
    		Float made = gradeCursor.getFloat(gradeCursor.getColumnIndex(Database.GRADE_MADE));
        	Float out_of = gradeCursor.getFloat(gradeCursor.getColumnIndex(Database.GRADE_OUT_OF));
    		text.setText(gradeCursor.getString(gradeCursor.getColumnIndex(Database.NAME)));
    		text2.setText(""+made);
    		if(!new Float(made/out_of).equals(Float.NaN))
    		text2.setText(String.format("%3.1f",new Float((made/out_of)*100))+"%");
    		text1.setText(made+"/"+out_of);
            return view;
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
        	cur.moveToPosition(groupPosition);
        	int  id = cur.getInt(cur.getColumnIndex(Database._ID));
        	Cursor gradeCursor = db.query(Database.GRADE_TABLE, null, Database.COMPONENTS_ID+" = "+id, null, null, null, null);
        	act.startManagingCursor(gradeCursor);
            return gradeCursor.getCount()+1;
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 64);

            TextView textView = new TextView(ClassProfile.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(36, 0, 0, 0);
            return textView;
        }

        public View getChildView(final int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            
            if(childPosition==0){

        		LayoutInflater inflater = (LayoutInflater)act.getLayoutInflater();
        		View view = inflater.inflate(R.layout.addgradelistview, null);
        		ImageView imageview = (ImageView)view.findViewById(R.id.ImageView01);
        		
        		view.setOnClickListener(new OnClickListener(){

					public void onClick(View v) {
						
						final Dialog dialog = new Dialog(ClassProfile.this);
							dialog.setTitle(ClassProfile.this.getResources().getString(R.string.addGrade));
							dialog.setCancelable(true);
							dialog.setContentView(R.layout.addgrade);
							final EditText text1 = (EditText)dialog.findViewById(R.id.EditText01);
							final EditText text2 = (EditText)dialog.findViewById(R.id.EditText02);
							Button button = (Button)dialog.findViewById(R.id.Button01);
							Button button1 = (Button)dialog.findViewById(R.id.Button02);
							final EditText text3 = (EditText)dialog.findViewById(R.id.EditText04);
							
							text3.setHint((String)getGroup(groupPosition));

							
							button.setOnClickListener(new OnClickListener(){
								public void onClick(View v) {
									Database data = new Database(ClassProfile.this,ClassProfile.this);
									SQLiteDatabase db =  data.getWritableDatabase();
									
									double made;
									double out_of;
									try{
										made=Double.valueOf(text1.getText().toString());
										out_of=Double.valueOf(text2.getText().toString());
									}catch(Exception e){
										made=0;
										out_of=0;
									}
									cur.moveToPosition(groupPosition);
									ContentValues values = new ContentValues();
									values.put(Database.NAME, text3.getText().toString());
									values.put(Database.GRADE_MADE, made );
									values.put(Database.GRADE_OUT_OF, out_of );
									values.put(Database.COMPONENTS_ID,  cur.getInt(cur.getColumnIndex(Database._ID)));
									int result = (int)db.insert(Database.GRADE_TABLE, null, values);
									cur.requery();
						            cur.moveToPosition(groupPosition);
									data.updateComponentGrades(cur.getInt(cur.getColumnIndex(Database._ID)));
									data.updateCourseGrades(class_id);
									data.close();
									ClassProfile.this.onCreate(null);
						    		notifyDataSetChanged();
									dialog.dismiss();
								}
							});
							
							button1.setOnClickListener(new OnClickListener(){
								public void onClick(View v) {
									dialog.dismiss();
								}
							});

							dialog.show();
					}
        			
        		});
        		
            	return view;
            }
            
            
            return getChild(groupPosition, childPosition-1);
        }

        public Object getGroup(int groupPosition) {
        	cur.moveToPosition(groupPosition);
            return cur.getString(cur.getColumnIndex(Database.NAME));
        }

        public int getGroupCount() {
            return cur.getCount();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
           // TextView textView = getGenericView();
            cur.moveToPosition(groupPosition);
    		LayoutInflater inflater = (LayoutInflater)act.getLayoutInflater();
    		View view = inflater.inflate(R.layout.groupview, null);
    		TextView text = (TextView)view.findViewById(R.id.TextView01);     
    		TextView text1 = (TextView)view.findViewById(R.id.TextView02);            
            cur.moveToPosition(groupPosition);
            Database database = new Database(ClassProfile.this,ClassProfile.this);
            cur.moveToPosition(groupPosition);
			database.updateComponentGrades(cur.getInt(cur.getColumnIndex(Database._ID)));
            text.setText(cur.getString(cur.getColumnIndex(Database.NAME)));
            Float k = cur.getFloat(cur.getColumnIndex(Database.TOTAL_GRADE_OUT_OF));
            if(k!=0)
            	k=cur.getFloat(cur.getColumnIndex(Database.TOTAL_GRADE_MADE))/k;
            else
            	k=cur.getFloat(cur.getColumnIndex(Database.TOTAL_GRADE_MADE))*10000;
            String f = String.format("%3.1f", k*100);
            text1.setText(f+"%");
            return view;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }
}