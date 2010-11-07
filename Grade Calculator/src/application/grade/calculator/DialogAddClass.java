package application.grade.calculator;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import application.grade.calculator.adapters.AddClassAdapter;

public class DialogAddClass extends Activity {
	
	ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dialogaddclass);
		
		listview = (ListView)findViewById(R.id.ListView01);
	//	listview.setAdapter(new AddClassAdapter(this));
		
	}

}
