package application.grade.calculator.adapters;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import application.grade.calculator.R;

public class HomeScreenAdapter extends BaseAdapter {

	Activity ctx;
	Cursor cursor;
	View view;
	
	public HomeScreenAdapter(Activity ctx,Cursor cursor, GridView grid) {
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

	public View getView(int arg0, View arg1, ViewGroup parent) {
		//view.setLayoutParams(parent.getLayoutParams());
		LayoutInflater inflater = (LayoutInflater)ctx.getLayoutInflater();
		view = inflater.inflate(R.layout.listview, null);
		ImageView image = (ImageView) view.findViewById(R.id.ImageView01);
		TextView text1 = (TextView) view.findViewById(R.id.text1);
		TextView text2 = (TextView) view.findViewById(R.id.text2);
		image.setImageResource(R.drawable.books);
		text1.setText("text is nice");
		text2.setText("text has made it");
		//.setLayoutParams(new GridView.LayoutParams(85, 85));
		return view;
	}

}
