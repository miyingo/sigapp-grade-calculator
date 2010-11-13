package application.grade.calculator.adapters;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;
import application.grade.calculator.R;

public class AddClassAdapter extends BaseAdapter {

	Activity ctx;
	View view;
	ArrayList<String> name;
	ArrayList<Integer> value;
	
	public AddClassAdapter(Activity ctx, ArrayList<String> name, ArrayList<Integer> value) {
		this.ctx=ctx;
		this.name = name;
		this.value = value;
	}

	public int getCount() {
		return name.size();
	}

	public Object getItem(int arg0) {
		return null;
	}

	public long getItemId(int arg0) {
		return arg0;
	}

	public View getView(final int position, View arg1, ViewGroup parent) {
		//view.setLayoutParams(parent.getLayoutParams());
		LayoutInflater inflater = (LayoutInflater)ctx.getLayoutInflater();
		view = inflater.inflate(R.layout.addclasslistview, null);
		EditText text1 = (EditText) view.findViewById(R.id.text1);
		EditText text2 = (EditText) view.findViewById(R.id.text2);
		text1.setText(name.get(position));
		text2.setText(value.get(position)+"");
		
		text1.addTextChangedListener(new TextWatcher(){

			public void afterTextChanged(Editable s) {
				name.set(position, s.toString());
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		text2.addTextChangedListener(new TextWatcher(){

			public void afterTextChanged(Editable s) {
				int val;
				try{
					val=Integer.valueOf(s.toString());
				}catch(Exception e){
					val=0;
				}
				value.set(position,val);
			}

			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		view.setBackgroundColor(Color.WHITE);
		return view;
	}

}
