package application.grade.calculator;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import application.grade.calculator.database.Database;

public class GradeCalculator extends Activity {
    /** Called when the activity is first created. */
	
	Button add;
	EditText class1;
	Button button;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
//        
//        add = (Button)findViewById(R.id.Button01);
//        class1 = (EditText)findViewById(R.id.EditText01);
//        button = (Button)findViewById(R.id.Button);
//        
//        button.setOnClickListener(new OnClickListener(){
//
//        	public void onClick(View v) {
//        		
//        		Intent i = new Intent(getBaseContext(),NewSubject.class);
//        		startActivity(i);
//        		
//			}
//        	
//        });
//        
//        add.setOnClickListener(new OnClickListener(){
//
//        	public void onClick(View v) {
//			
//			add.setText(class1.getText().toString());	
//			}
//        	
//        });
//              
        GridView gridview = (GridView) findViewById(R.id.gridview);
        
        Database classes = new Database(this,this);
//        
//		SQLiteDatabase db = classes.getWritableDatabase();
//        Cursor cursor = db.query(Database.CLASSES_TABLE, new String[] {Database._ID,Database.NAME,Database.YEAR, Database.SEMESTER}, Database.YEAR+" like 2010 AND "+Database.NAME+" like 'CS180' AND "+Database.SEMESTER+" like 1", null, null, null, null);
//        startManagingCursor(cursor);
		//db.close();
		//classes.close();
//        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
//        	    R.layout.listview, // Use a template
//        	                                          // that displays a
//        	                                          // text view
//        	    cursor, // Give the cursor to the list adapter
//        	    new String[] {Database.NAME,Database.YEAR}, // Map the NAME column in the
//        	                                         // people database to...
//        	    new int[] {R.id.text1 , R.id.text2}); 
        gridview.setAdapter(classes.getClasses());
        
       
        gridview.setTextFilterEnabled(true);

        gridview.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // When clicked, show a toast with the TextView text    
        	  	Toast.makeText(GradeCalculator.this, "Made it to +"+position, Toast.LENGTH_SHORT).show();
          }
        });
        

    }
    
       
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // This is our one standard application action -- inserting a
        // new note into the list.
        menu.add(0, 1, 0, R.string.app_name)
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