package org.opencv.javacv.facerecognition;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
	private Context context = null;
	private List<SelectableGridItem> items = null;
	
	
	private final static int WIDTH = 128;
	private final static int HEIGHT = 128;
	
	public ImageAdapter(Context context, String imagesPath) {
		this.context = context;
		
		File imagesFolder = new File(imagesPath);
		File[] onlyJpgImagesList = imagesFolder.listFiles(new FilenameFilter(){

			@Override
			public boolean accept(File dir, String name) {
				return (name.endsWith(".jpg"));
			}
			
		});
		
		this.items = new ArrayList<SelectableGridItem>(onlyJpgImagesList.length);
		
		for(File jpgImage : onlyJpgImagesList) {
			Bitmap original = BitmapFactory.decodeFile(jpgImage.getAbsolutePath());
			Log.i("Importante", jpgImage.getAbsolutePath());
			if(original == null) continue;
			Bitmap scaled = Bitmap.createScaledBitmap(original, WIDTH, HEIGHT, true);
			
			Drawable drawable = new BitmapDrawable(context.getResources(), scaled);
			this.items.add(new SelectableGridItem(drawable, jpgImage));
		}
			
	}

	@Override
	public int getCount() {
		return this.items.size();
	}

	@Override
	public Object getItem(int position) {
		return this.items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public List<SelectableGridItem> getItemList() {
		return this.items;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView;
		
		// if it's not recycled, initialize some attributes
        if (convertView == null) {
            imageView = new ImageView(this.context);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        SelectableGridItem item = items.get(position);
        
        imageView.setImageDrawable(item.getDrawable());
        
        if (item.isSelected())
        	imageView.setBackgroundColor(0xFF00FF00);
		else
			imageView.setBackgroundColor(0x00000000);
        
        return imageView;
	}
}
