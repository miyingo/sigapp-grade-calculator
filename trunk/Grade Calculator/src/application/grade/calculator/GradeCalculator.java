package application.grade.calculator;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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
       
        
        Database classes = new Database(this);
        
		SQLiteDatabase db = classes.getWritableDatabase();
        Cursor cursor = db.query(Database.CLASSES_TABLE, null, null, null, null, null, null);
		startManagingCursor(cursor);
		
        SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(this,
        	    R.layout.listview, // Use a template
        	                                          // that displays a
        	                                          // text view
        	    cursor, // Give the cursor to the list adapter
        	    new String[] {Database.NAME}, // Map the NAME column in the
        	                                         // people database to...
        	    new int[] {R.id.text1}); 
        gridview.setAdapter(adapter2);
     
       
        gridview.setTextFilterEnabled(true);

        gridview.setOnItemClickListener(new OnItemClickListener() {
          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            // When clicked, show a toast with the TextView text    
        	  	Toast.makeText(GradeCalculator.this, "Made it to +"+position, Toast.LENGTH_SHORT).show();
          }
        });
        
        
    }
}