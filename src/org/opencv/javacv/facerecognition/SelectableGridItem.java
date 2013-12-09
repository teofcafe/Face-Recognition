package org.opencv.javacv.facerecognition;

import java.io.File;

import android.graphics.drawable.Drawable;

public class SelectableGridItem {
	private Drawable drawable = null;
	private File file = null;
	private boolean selected = false;
	
	public SelectableGridItem(Drawable drawable, File file) {
		this.drawable = drawable;
		this.file = file;
	}
	
	public void changeSelection() {
		this.selected = (this.selected) ? false : true;
	}
	
	public void unloadItem() {
		this.file.delete();
	}
	
	public Drawable getDrawable() {
		return this.drawable;
	}
	
	public boolean isSelected() {
		return this.selected;
	}
}
