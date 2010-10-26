package application.grade.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
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
}