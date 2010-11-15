package application.grade.calculator.adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import application.grade.calculator.R;

public class GalleryAdapter extends BaseAdapter {
	int mGalleryItemBackground;
    private Context mContext;
    int picture=2;
    Dialog dialog;
    ImageView image;

	public static final int[] pic = { R.drawable.books,
		R.drawable.jean_victor_balin_book,
		R.drawable.johnny_automatic_roman_coliseum,
		R.drawable.organick_chemistry_set_9,
		R.drawable.felipecaparelli_gears_1,
		R.drawable.mcol_branching_tree,
		R.drawable.parabola,
		R.drawable.suitcase,
		R.drawable.theresaknott_microscope};
	
    public GalleryAdapter(Context c, ImageView image, Dialog d) {
    	this.image=image;
    	this.dialog=d;
        mContext = c;
        TypedArray a = c.obtainStyledAttributes(R.styleable.HelloGallery);
        mGalleryItemBackground = a.getResourceId(
                R.styleable.HelloGallery_android_galleryItemBackground, 0);
        a.recycle();
    }

    public int getCount() {
        return pic.length;
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
    	ImageView i = new ImageView(mContext);

        i.setImageResource(pic[position]);
        i.setLayoutParams(new Gallery.LayoutParams(150, 100));
        i.setScaleType(ImageView.ScaleType.FIT_XY);
        i.setBackgroundResource(mGalleryItemBackground);
        
        return i;
    }
    
    public int getPicture(){
    	return picture;
    }
    
    
}