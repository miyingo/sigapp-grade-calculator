package application.grade.calculator.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import application.grade.calculator.R;

public class HomeScreenAdapter extends BaseAdapter {

	Context ctx;
	Cursor cursor;
	View view;
	
	public HomeScreenAdapter(Context ctx,Cursor cursor) {
		this.ctx=ctx;
		this.cursor = cursor;
		LayoutInflater inflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view=inflater.inflate(R.layout.listview, null);
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

	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ImageView image = (ImageView) view.findViewById(R.id.ImageView01);
		image.setImageResource(R.drawable.books);
		
		return image;
	}

}
