package application.grade.calculator;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
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
	int picture = 2;
	
	public static final int[] pic = { R.drawable.books,
		R.drawable.jean_victor_balin_book,
		R.drawable.johnny_automatic_roman_coliseum,
		R.drawable.organick_chemistry_set_9,
		R.drawable.felipecaparelli_gears_1,
		R.drawable.mcol_branching_tree,
		R.drawable.parabola,
		R.drawable.suitcase,
		R.drawable.theresaknott_microscope};
	
	GalleryAdapter adapter1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogaddclass);

		name = new ArrayList<String>();
		value = new ArrayList<Integer>();

		adapter = new AddClassAdapter(this, name, value);
		listview = (ListView) findViewById(R.id.list);
		listview.setAdapter(adapter);

		classname = (EditText) findViewById(R.id.EditText01);
		classsemester = (EditText) findViewById(R.id.EditText02);
		classyear = (EditText) findViewById(R.id.EditText03);
		image = (ImageView) findViewById(R.id.ImageView01);

		add = (Button) findViewById(R.id.Button3);
		cancel = (Button) findViewById(R.id.Button02);
		ok = (Button) findViewById(R.id.Button01);

		image.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				final Dialog dialog = new Dialog(AddClassActivity.this);
				dialog.setCancelable(true);
				dialog.setTitle(R.string.choosePic);
				dialog.setContentView(R.layout.pickpicturedialog);
				Gallery g = (Gallery) dialog.findViewById(R.id.gallery);
				adapter1 = new GalleryAdapter(AddClassActivity.this, image,
						dialog);
				g.setOnItemClickListener(new OnItemClickListener() {

					public void onItemClick(AdapterView parent, View v,
							int position, long id) {
						
						picture=position;
						image.setImageResource(pic[picture]);
						dialog.dismiss();
					}

				});

				g.setAdapter(adapter1);
				// g.setOnClickListener( new OnClickListener(){
				// public void onClick(View v) {
				// picture=adapter1.getPicture();
				// image.setImageResource(GalleryAdapter.pic[picture]);
				// dialog.dismiss();
				// }
				// });
				dialog.show();
				picture = adapter1.getPicture();
			}
		});

		add.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				name.add("");
				value.add(0);
				adapter.notifyDataSetChanged();
			}
		});

		cancel.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				x.setResult(Activity.RESULT_CANCELED);
				x.finishActivity(GradeCalculator.AddClass);
				x.finish();
			}
		});

		ok.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// Check if year and semester are valid
				boolean ok = true; // set to false to not update datbase
				String semesterString = classsemester.getText().toString();
				String yearString = classyear.getText().toString();

				int year = -1;
				try {
					year = Integer.valueOf(yearString);
				} catch (Exception e) {
					year = -1;
				}
				semesterString.toUpperCase();
				semesterString.trim();
				if (semesterString.equals(""))
					ok = false;

				if (year == -1)
					ok = false;

				int percent = 0;
				for (int i = 0; i < name.size(); i++)
					try {
						percent += value.get(i);
					} catch (Exception e) {

						ok = false;
					}

				if (!(percent >= 100))
					ok = false;

				if (classname.getText().toString().equalsIgnoreCase(""))
					ok = false;

				if (ok) {
					Database data = new Database(x, x);
					SQLiteDatabase db = data.getWritableDatabase();
					ContentValues values = new ContentValues();
					values.put(Database.NAME, classname.getText().toString());
					values.put(Database.SEMESTER, semesterString);
					//picture = adapter1.getPicture();
					values.put(Database.PIC, picture);
					values.put(Database.YEAR, year);
					values.put(Database.TOTAL_GRADE, 0);

					int result = (int) db.insert(Database.CLASSES_TABLE, null,
							values);

					values = new ContentValues();
					for (int i = 0; i < name.size(); i++) {
						values.put(Database.CLASS_ID, result);
						values.put(Database.TOTAL_GRADE_MADE, 0);
						values.put(Database.TOTAL_GRADE_OUT_OF, 0);
						values.put(Database.WEIGHT, value.get(i).intValue());
						values.put(Database.NAME, name.get(i).toString());
						db.insert(Database.COMPONENTS_TABLE, null, values);
					}


					x.setResult(Activity.RESULT_OK);

					// figure out why these line does not quit activity
					x.finishActivity(GradeCalculator.AddClass);
					x.finish();
				}

			}
		});

	}

	protected void onResume() {
		super.onResume();

	}

}
