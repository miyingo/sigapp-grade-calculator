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
	private static final int DATABASE_VERSION = 4;
	
	//Classes Table Columns
	public static final String CLASSES_TABLE = "classes";
	public static final String _ID ="_id";	//int
	public static final String NAME = "name";	//text
	//public static final String DESCRIPTION = "description";	//text
	public static final String TOTAL_GRADE = "total_grade";	//int
	public static final String SEMESTER = "semester";	//text or int
	public static final String YEAR = "year";	//int
	public static final String PIC = "picture"; //int

	//Components Table Columns
	public static final String COMPONENTS_TABLE = "components";
	//public static final String _ID ="_id";	//int
	//public static final String NAME = "name";	//text
	public static final String CLASS_ID = "class_id"; //int holds the class id that has this component
	public static final String TOTAL_GRADE_MADE = "grade_made"; //double  holds grade made out of components from last update
	public static final String TOTAL_GRADE_OUT_OF = "grade_out_of"; //double holds grade total out of components from last update
	
	//Grade Table Columns
	public static final String GRADE_TABLE = "grades";
	//public static final String _ID ="_id";	//int
	//public static final String NAME = "name";	//text
	public static final String COMPONENTS_ID = "class_id"; //int holds the class id that has this component
	public static final String GRADE_MADE = "grade_made"; //double  holds grade made out of components from last update
	public static final String GRADE_OUT_OF = "grade_out_of"; //double holds grade total out of components from last update


	
	public static final String DatabaseCreateClassesString = "create table if not exists "+CLASSES_TABLE+" ("+_ID
						+" integer primary key autoincrement, "+NAME
						+" text not null, "
						+PIC+" int, "
						+TOTAL_GRADE+" text, "
						+YEAR+" float, "+SEMESTER+" text);";
	
	public static final String DatabaseCreateComponentsString = "create table if not exists "+COMPONENTS_TABLE+" ("+_ID
						+" integer primary key autoincrement, "
						+NAME+" text not null, "
						+CLASS_ID+" float, "
						+TOTAL_GRADE_MADE+" float, "
						+TOTAL_GRADE_OUT_OF+" float);";
	
	public static final String DatabaseCreateGradesString = "create table if not exists "+GRADE_TABLE+" ("+_ID
						+" integer primary key autoincrement, "
						+NAME+" text not null, "
						+COMPONENTS_ID+" integer, "
						+GRADE_MADE+" float, "
						+GRADE_OUT_OF+" float);";
	
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
		db.execSQL(DatabaseCreateClassesString);
		db.execSQL(DatabaseCreateComponentsString);
		db.execSQL(DatabaseCreateGradesString);
		addClass("CS180",db , 1,2);
		addClass("PHYS172",db, 2,3);
		addClass("PSY200",db, 1,1);
		addClass("CS240",db , 3,4);
		addClass("CS180",db , 1,2);
		addComponents("HOMEWORK",db,77,89);
		addComponents("Projects",db,73,83);
		addComponents("Projects",db,73,83);
		addGrade("Project1",db,73,82,1);
		addGrade("Project2",db,73,83,2);
		addGrade("Project3",db,73,83,0);
		addGrade("Project4",db,73,83,3);


	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists "+CLASSES_TABLE);
		onCreate(db);
	}
	
	public void addClass(String name, SQLiteDatabase db, int semester, int pic){
	
		ContentValues values = new ContentValues();
		values.put(NAME, name);
		values.put(TOTAL_GRADE, 77);
		values.put(SEMESTER, semester);
		values.put(YEAR, 2010);
		values.put(PIC, pic);
		db.insert(CLASSES_TABLE, null, values);
		
	}
	
	public void addComponents(String name, SQLiteDatabase db, int grade_made, int grade_out ){
		
		ContentValues values = new ContentValues();
		values.put(NAME, name);
		values.put(CLASS_ID, 1);
		values.put(TOTAL_GRADE_MADE, grade_made);
		values.put(TOTAL_GRADE_OUT_OF, grade_out );
		db.insert(COMPONENTS_TABLE, null, values);
		
	}
	
	public void addGrade(String name, SQLiteDatabase db, int grade_made, int grade_out , int id){
		
		ContentValues values = new ContentValues();
		values.put(NAME, name);
		values.put(COMPONENTS_ID, id);
		values.put(GRADE_MADE, grade_made);
		values.put(GRADE_OUT_OF, grade_out );
		db.insert(GRADE_TABLE, null, values);
		
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
