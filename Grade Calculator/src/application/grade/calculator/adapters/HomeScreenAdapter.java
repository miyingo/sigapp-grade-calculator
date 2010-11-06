package application.grade.calculator.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import application.grade.calculator.R;
import application.grade.calculator.database.Database;

public class HomeScreenAdapter extends BaseAdapter {

	Activity ctx;
	Cursor cursor;
	View view;
	
	int[] pic =  {R.drawable.books,R.drawable.felipecaparelli_gears_1, R.drawable.jean_victor_balin_book, R.drawable.johnny_automatic_roman_coliseum,R.drawable.organick_chemistry_set_9};
	
	public HomeScreenAdapter(Activity ctx,Cursor cursor) {
		this.ctx=ctx;
		this.cursor = cursor;
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return cursor.getCount();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return 0;
	}

	public View getView(int position, View arg1, ViewGroup parent) {
		//view.setLayoutParams(parent.getLayoutParams());
		cursor.moveToPosition(position);
		LayoutInflater inflater = (LayoutInflater)ctx.getLayoutInflater();
		view = inflater.inflate(R.layout.listview, null);
		ImageView image = (ImageView) view.findViewById(R.id.ImageView01);
		TextView text1 = (TextView) view.findViewById(R.id.text1);
		TextView text2 = (TextView) view.findViewById(R.id.text2);
        //image.setLayoutParams(new GridView.LayoutParams(85, 85));
		image.setImageResource(pic[cursor.getInt(cursor.getColumnIndex(Database.PIC))]);
        image.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

		text1.setText(cursor.getString(cursor.getColumnIndex(Database.NAME)));
		text2.setText(cursor.getInt(cursor.getColumnIndex(Database.YEAR))+"");
		//.setLayoutParams(new GridView.LayoutParams(85, 85));
		view.setBackgroundColor(Color.WHITE);
		//view.setLayoutParams(parent.getLayoutParams());
		return view;
	}

}
