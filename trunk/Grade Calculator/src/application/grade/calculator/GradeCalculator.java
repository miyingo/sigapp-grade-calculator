package application.grade.calculator;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
 
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        Database classes = new Database(this,this);       
		SQLiteDatabase db = classes.getWritableDatabase();
       final Cursor cursor = db.query(Database.CLASSES_TABLE, null, null, null, null, null, null);
       startManagingCursor(cursor);       
        gridview.setAdapter(new HomeScreenAdapter(this,cursor));
        
       
        gridview.setTextFilterEnabled(true);

        gridview.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	  	Toast.makeText(GradeCalculator.this, "Made it to +"+position, Toast.LENGTH_SHORT).show();
        	  	Intent i = new Intent(getBaseContext(),ClassProfile.class);
        	  	cursor.moveToPosition(position);
        	  	i.putExtra(CLASS_ID, cursor.getInt(cursor.getColumnIndex(Database._ID)));
                startActivity(i);
          }
        });
        

    }
    
       
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // This is our one standard application action -- inserting a
        // new note into the list.
        menu.add(0, 1, 0, "Add Class")
                .setShortcut('3', 'a')
                .setIcon(android.R.drawable.ic_menu_add);

        // Generate any additional actions that can be performed on the
        // overall list.  In a normal install, there are no additional
        // actions found here, but this allows other applications to extend
        // our menu with their own actions.

        
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case 1: Intent i = new Intent(getBaseContext(),DialogAddClass.class);
        startActivity(i);
        
        }
        return true;
    }
    
}