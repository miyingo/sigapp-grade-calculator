package application.grade.calculator;

import android.app.Activity;
import android.app.ExpandableListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.TextView;
import application.grade.calculator.database.Database;

public class ClassProfile extends ExpandableListActivity {

    ExpandableListAdapter mAdapter;
    
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int class_id = getIntent().getIntExtra(GradeCalculator.CLASS_ID, 0);
        // Set up our adapter

        mAdapter = new MyExpandableListAdapter(this,class_id);
        setListAdapter(mAdapter);
        registerForContextMenu(getExpandableListView());
    }

    /*
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Sample menu");
        menu.add(0, 0, 0, R.string.expandable_list_sample_action);
    }
	
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ExpandableListContextMenuInfo info = (ExpandableListContextMenuInfo) item.getMenuInfo();

        String title = ((TextView) info.targetView).getText().toString();

        int type = ExpandableListView.getPackedPositionType(info.packedPosition);
        if (type == ExpandableListView.PACKED_POSITION_TYPE_CHILD) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            int childPos = ExpandableListView.getPackedPositionChild(info.packedPosition); 
            Toast.makeText(this, title + ": Child " + childPos + " clicked in group " + groupPos,
                    Toast.LENGTH_SHORT).show();
            return true;
        } else if (type == ExpandableListView.PACKED_POSITION_TYPE_GROUP) {
            int groupPos = ExpandableListView.getPackedPositionGroup(info.packedPosition); 
            Toast.makeText(this, title + ": Group " + groupPos + " clicked", Toast.LENGTH_SHORT).show();
            return true;
        }

        return false;
    }
	*/
    /**
     * A simple adapter which maintains an ArrayList of photo resource Ids. 
     * Each photo is displayed as an image. This adapter supports clearing the
     * list of photos and adding a new photo.
     *
     */
    public class MyExpandableListAdapter extends BaseExpandableListAdapter {
    	
    	private Cursor cur;
    	private SQLiteDatabase db;
    	private Activity act;
    	private Cursor gradeCursor;
    	private int class_id;
    	
    	MyExpandableListAdapter(Activity act, int class_id){
            Database database = new Database(act,act);
            this.db = database.getWritableDatabase();
            Cursor cur = db.query(Database.COMPONENTS_TABLE, null, null, null, null, null, null);
            this.act = act;
            this.class_id=class_id;
            act.startManagingCursor(cur);
            //act.startManagingCursor(gradeCursor);
    		this.cur = cur;
    		
    	}
    	
        // Sample data set.  children[i] contains the children (String[]) for groups[i].
        private String[] groups = { "People Names", "Dog Names", "Cat Names", "Fish Names" };
        private String[][] children = {
                { "Arnold", "Barry", "Chuck", "David" },
                { "Ace", "Bandit", "Cha-Cha", "Deuce" },
                { "Fluffy", "Snuggles" },
                { "Goldy", "Bubbles" }
        };
        public Object getChild(int groupPosition, int childPosition) {
        	cur.moveToPosition(groupPosition);
        	int  id = cur.getInt(cur.getColumnIndex(Database._ID));
        	Cursor gradeCursor = db.query(Database.GRADE_TABLE, null, null, null, null, null, null);
        	act.startManagingCursor(gradeCursor);
        	gradeCursor.moveToPosition(childPosition);
            return gradeCursor.getString(gradeCursor.getColumnIndex(Database.NAME));
        }

        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        public int getChildrenCount(int groupPosition) {
        	cur.moveToPosition(groupPosition);
        	int  id = cur.getInt(cur.getColumnIndex(Database._ID));
        	Cursor gradeCursor = db.query(Database.GRADE_TABLE, null, null, null, null, null, null);
        	act.startManagingCursor(gradeCursor);
            return gradeCursor.getCount();
        }

        public TextView getGenericView() {
            // Layout parameters for the ExpandableListView
            AbsListView.LayoutParams lp = new AbsListView.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 64);

            TextView textView = new TextView(ClassProfile.this);
            textView.setLayoutParams(lp);
            // Center the text vertically
            textView.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            // Set the text starting position
            textView.setPadding(36, 0, 0, 0);
            return textView;
        }

        public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                View convertView, ViewGroup parent) {
            TextView textView = getGenericView();
            
            textView.setText(getChild(groupPosition, childPosition).toString());
            return textView;
        }

        public Object getGroup(int groupPosition) {
        	cur.moveToPosition(groupPosition);
            return cur.getString(cur.getColumnIndex(Database.NAME));
        }

        public int getGroupCount() {
            return cur.getCount();
        }

        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        public View getGroupView(int groupPosition, boolean isExpanded, View convertView,
                ViewGroup parent) {
            TextView textView = getGenericView();
            cur.moveToPosition(groupPosition);
            textView.setText((String)getGroup(groupPosition));
            return textView;
        }

        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

        public boolean hasStableIds() {
            return true;
        }

    }
}