package application.grade.calculator.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class Database extends SQLiteOpenHelper {
	private static final String DATABASE_NAME = "GradeCalc.db";
	private static final int DATABASE_VERSION = 3;
	
	//Exercise Database columns
	public static final String CLASSES_TABLE = "classes";
	public static final String _ID ="_id";	//int
	public static final String NAME = "name";	//text
	//public static final String DESCRIPTION = "description";	//text
	public static final String TOTAL_GRADE = "total_grade";	//int
	public static final String SEMESTER = "semester";	//text or int
	public static final String YEAR = "year";	//int


	
	public static final String DatabaseCreateString = "create table if not exists "+CLASSES_TABLE+" ("+_ID
						+" integer primary key autoincrement, "+NAME
						+" text not null, "
						//+DESCRIPTION+" text, "
						+TOTAL_GRADE+" text, "
						+YEAR+" integer, "+SEMESTER+" text);";

	//Record
	
	public Context ctx;
	
	public Database(Context context ){
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		ctx=context;
	}

	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DatabaseCreateString);
		addClass("CS180",db);
		addClass("PHYS172",db);
		addClass("PSY200",db);
		addClass("CS240",db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("drop table if exists "+CLASSES_TABLE);
		onCreate(db);
	}
	
	public void addClass(String name, SQLiteDatabase db){
	
		ContentValues values = new ContentValues();
		values.put(NAME, name);
		values.put(TOTAL_GRADE, 77);
		values.put(SEMESTER, 1);
		values.put(YEAR, 2010);
		db.insert(CLASSES_TABLE, null, values);
		
	}
	

}
