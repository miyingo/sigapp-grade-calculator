package application.grade.calculator.database;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.BaseAdapter;
import android.widget.SimpleCursorAdapter;
import application.grade.calculator.R;


public class Database extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "GradeCalc.db";
	private static final int DATABASE_VERSION = 5;
	
	//Exercise Database columns
	public static final String CLASSES_TABLE = "classes";
	public static final String _ID ="_id";	//int
	public static final String NAME = "name";	//text
	//public static final String DESCRIPTION = "description";	//text
	public static final String TOTAL_GRADE = "total_grade";	//int
	public static final String SEMESTER = "semester";	//text or int
	public static final String YEAR = "year";	//int
	public static final String PIC = "picture"; //int


	
	public static final String DatabaseCreateString = "create table if not exists "+CLASSES_TABLE+" ("+_ID
						+" integer primary key autoincrement, "+NAME
						+" text not null, "
						+PIC+" text, "
						+TOTAL_GRADE+" text, "
						+YEAR+" integer, "+SEMESTER+" text);";

	//Record
	
	public Context ctx;
	public SQLiteDatabase db;
	public Activity activity;
	
	public Database(Context context,Activity activity ){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		ctx=context;
		db = getWritableDatabase();
		this.activity=activity;
	}

	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DatabaseCreateString);
		addClass("CS180",db , 1);
		addClass("PHYS172",db, 2);
		addClass("PSY200",db, 1);
		addClass("CS240",db , 3);
		addClass("CS180",db , 1);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists "+CLASSES_TABLE);
		onCreate(db);
	}
	
	public void addClass(String name, SQLiteDatabase db, int semester){
	
		ContentValues values = new ContentValues();
		values.put(NAME, name);
		values.put(TOTAL_GRADE, 77);
		values.put(SEMESTER, semester);
		values.put(YEAR, 2010);
		db.insert(CLASSES_TABLE, null, values);
		
	}
	
	public BaseAdapter getClassesAdapter(){
		
        Cursor cursor = db.query(Database.CLASSES_TABLE, null, null, null, null, null, null);
        activity.startManagingCursor(cursor);
		SimpleCursorAdapter adapter2 = new SimpleCursorAdapter(ctx,
        	    R.layout.listview, // Use a template
        	                                          // that displays a
        	                                          // text view
        	    cursor, // Give the cursor to the list adapter
        	    new String[] {Database.NAME,Database.YEAR}, // Map the NAME column in the
        	                                         // people database to...
        	    new int[] {R.id.text1 , R.id.text2}); 
		return adapter2;
	}
	
	

}
