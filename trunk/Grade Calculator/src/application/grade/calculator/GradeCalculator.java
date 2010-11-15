package application.grade.calculator;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import application.grade.calculator.adapters.HomeScreenAdapter;
import application.grade.calculator.database.Database;

public class GradeCalculator extends Activity {
    /** Called when the activity is first created. */
//	
	static final int AddClassIntent = 1;
	Button add;
	EditText class1;
	Button button;
	public static final String CLASS_ID = "class_id";
	public static final String CLASS_NAME = "class_name";
	public static final String CLASS_PIC = "class_pic";
	public static final int AddClass = 0;
    HomeScreenAdapter adapter;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        Database classes = new Database(this,this);       
		SQLiteDatabase db = classes.getWritableDatabase();
       final Cursor cursor = db.query(Database.CLASSES_TABLE, null, null, null, null, null, null);
       startManagingCursor(cursor); 
       
       if(cursor.moveToFirst()){
    	 TextView v = (TextView)findViewById(R.id.text);
    	 v.setVisibility(View.INVISIBLE);
    	 v.setTextSize(0);
       }
       adapter = new HomeScreenAdapter(this,cursor);
        gridview.setAdapter(adapter);
        
       
        gridview.setTextFilterEnabled(true);

        gridview.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  	Intent i = new Intent(getBaseContext(),ClassProfile.class);
        	  	cursor.moveToPosition(position);
        	  	i.putExtra(CLASS_ID, cursor.getInt(cursor.getColumnIndex(Database._ID)));
        	  	i.putExtra(CLASS_NAME, cursor.getString(cursor.getColumnIndex(Database.NAME)));
        	  	i.putExtra(CLASS_PIC, cursor.getInt(cursor.getColumnIndex(Database.PIC)));
                startActivity(i);
          }
        });
        

    }
    
    
    
       
    @Override
	protected void onResume() {
		super.onResume();
		adapter.notifyDataSetChanged();
	}




	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		adapter.notifyDataSetChanged();
	}




	public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // This is our one standard application action -- inserting a
        // new note into the list.
        String addClassButton = this.getResources().getString(R.string.addClass);
        menu.add(0, 1, 0, addClassButton)
                .setShortcut('3', 'a')
                .setIcon(android.R.drawable.ic_menu_add);
        
        String deleteClassButton = this.getResources().getString(R.string.deleteClass);
        menu.add(0, 2, 0, deleteClassButton)
                .setShortcut('3', 'a')
                .setIcon(android.R.drawable.ic_menu_delete);

        // Generate any additional actions that can be performed on the
        // overall list.  In a normal install, there are no additional
        // actions found here, but this allows other applications to extend
        // our menu with their own actions.

        
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case 1: Intent i = new Intent(getBaseContext(),AddClassActivity.class);
        	startActivityForResult(i,  AddClass);
        	break;
        case 2:
        	
        	break;
        
        }
        return true;
    }
    
    public void showDeleteClassDialog(){
    	Dialog dialog = new Dialog(GradeCalculator.this);
    	dialog.setCancelable(true);
    	
    	
    }
    
    public static class LinedEditText extends EditText {
        private Rect mRect;
        private Paint mPaint;

        // we need this constructor for LayoutInflater
        public LinedEditText(Context context, AttributeSet attrs) {
            super(context, attrs);

            mRect = new Rect();
            mPaint = new Paint();
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(0x800000FF);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            int count = getLineCount();
            Rect r = mRect;
            Paint paint = mPaint;

            for (int i = 0; i < count; i++) {
                int baseline = getLineBounds(i, r);

                canvas.drawLine(r.left, baseline + 1, r.right, baseline + 1, paint);
            }

            super.onDraw(canvas);
        }
    }

    
}