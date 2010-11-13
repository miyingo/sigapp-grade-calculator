package application.grade.calculator;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import application.grade.calculator.adapters.AddClassAdapter;
import application.grade.calculator.adapters.GalleryAdapter;
import application.grade.calculator.database.Database;

public class AddClassActivity extends Activity {
	
	Activity x = this;
	ListView listview;
	Button add;
	Button cancel;
	Button ok;
	ArrayList<String> name;
	ArrayList<Integer> value;
	AddClassAdapter adapter;
	EditText classname;
	EditText classsemester;
	EditText classyear;
	ImageView image;
	static final int DIALOG_PICK_PIC = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogaddclass);
		
		name = new ArrayList<String>();
		value = new ArrayList<Integer>();
	
		adapter = new AddClassAdapter(this,name,value);
		listview = (ListView)findViewById(R.id.list);
		listview.setAdapter(adapter);
		
		classname = (EditText)findViewById(R.id.EditText01);
		classsemester = (EditText)findViewById(R.id.EditText02);
		classyear = (EditText)findViewById(R.id.EditText03);
		image = (ImageView)findViewById(R.id.ImageView01);
		
		add = (Button)findViewById(R.id.Button3);
		cancel = (Button)findViewById(R.id.Button02);
		ok = (Button)findViewById(R.id.Button01);
		
		image.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
			//showDialog(DIALOG_PICK_PIC);	
			}
		});

		add.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				name.add("New Class Component");
				value.add(0);
				adapter.notifyDataSetChanged();
				Toast.makeText(v.getContext(), "create text and size is "+name.size(), Toast.LENGTH_SHORT).show();
			}			
		});
		
		cancel.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				x.setResult(Activity.RESULT_CANCELED);
				x.finishActivity(GradeCalculator.AddClass);
				x.finish();
			}			
		});
		
		ok.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				//Check if year and semester are valid
				boolean ok = true; //set to false to not update datbase
				String semesterString =classsemester.getText().toString();
				String yearString = classyear.getText().toString();
				
				int year=-1;
				try{
				year = Integer.valueOf(yearString);
				}catch(Exception e){
					year=-1;
				}
				semesterString.toUpperCase();
				semesterString.trim();
				if(semesterString.equals(""))
					ok=false;

				if(year == -1)
					ok=false;
				
				int percent = 0;
				for(int i=0;i<name.size();i++)
					try{
						percent += value.get(i);
					}catch(Exception e){
						
						ok=false;
					}
				
				if(!(percent>=100))
					ok = false;
				
				if(classname.getText().toString().equalsIgnoreCase(""))
					ok=false;

				if(ok){
					Database data = new Database(x,x);
					SQLiteDatabase db = data.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put(Database.NAME, classname.getText().toString());
					values.put(Database.SEMESTER, semesterString);
					values.put(Database.PIC, 2);
					values.put(Database.YEAR, year);
					values.put(Database.TOTAL_GRADE, 0);
					
					int result = (int) db.insert(Database.CLASSES_TABLE, null, values);
					
					values = new ContentValues();
					for(int i=0;i<name.size();i++){
					values.put(Database.CLASS_ID, result);
					values.put(Database.TOTAL_GRADE_MADE, 0);
					values.put(Database.TOTAL_GRADE_OUT_OF, value.get(i).intValue());
					values.put(Database.NAME, name.get(i).toString());
					db.insert(Database.COMPONENTS_TABLE, null, values);
					}
					
					Toast.makeText(v.getContext(), "create text and size is "+name.size()+"\n result is "+result, Toast.LENGTH_SHORT).show();

					x.setResult(Activity.RESULT_OK);
					
					//figure out why these line does not quit activity
					x.finishActivity(GradeCalculator.AddClass);
					x.finish();
				}
				
			}			
		});
		
	}
	
	protected Dialog onCreateDialog(int id) {
	    Dialog dialog = new Dialog(getBaseContext());

	    switch(id) {
	    case DIALOG_PICK_PIC:
		    Context mContext = getApplicationContext();
		    dialog.setContentView(R.layout.pickpicturedialog);
		    dialog.setTitle("Pick a Picture");
	    	Gallery g = (Gallery) findViewById(R.id.gallery);
	        g.setAdapter(new GalleryAdapter(mContext));
	    	break;
	    case 1:
	        // do the work to define the game over Dialog
	        break;
	   // default:
	     //   dialog = null;
	    }
	    return dialog;
	}


	protected void onResume() {
		super.onResume();
				
	}
	
	

}
