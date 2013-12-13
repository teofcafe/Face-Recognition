package org.opencv.javacv.facerecognition.menu;

import java.io.File;
import org.opencv.javacv.facerecognition.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FileItemAdapter extends BaseAdapter {
	
	private File root = null;
	private File parent = null;
	private File[] files = null;
	private int positionOffset = 0;

	public FileItemAdapter(final File root) {
		this.root = root;
		this.parent = this.root;
        this.files = this.parent.listFiles();
    }
	
	public boolean goBack() {
		if(this.isOnRoot())
			return false;
		
		this.parent = this.parent.getParentFile();
		this.files = this.parent.listFiles();
		
		if(this.isOnRoot())
			this.positionOffset = 0;
		
		this.notifyDataSetChanged();
		
		return true;
	}
	
	public void goTo(int position) {
		this.parent = this.files[position - this.positionOffset];
		this.files = this.parent.listFiles();
		this.positionOffset = 1;
		
		this.notifyDataSetChanged();
	}
	
	public File getParent() {
		return this.parent;
	}
	
	public boolean isOnRoot() {
		return this.parent.equals(this.root);
	}
	
	@Override
	public int getCount() {
		return this.files.length + this.positionOffset;
	}

	@Override
	public Object getItem(int position) {
		 return this.files[position - this.positionOffset];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
        View itemView = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_list, null);
        } else {
            itemView = convertView;
        }

        TextView txtName = (TextView) itemView.findViewById(R.id.name);
        ImageView imgViewChecked = (ImageView) itemView.findViewById(R.id.file_icon);

        if(!this.isOnRoot() && position == 0) {
        		txtName.setText("..");
            	imgViewChecked.setImageResource(R.drawable.basic_undo_icon);
        } else {
        	final File item = this.files[position - this.positionOffset];
        	txtName.setText(item.getName());
            
            if(item.isDirectory())
            	imgViewChecked.setImageResource(R.drawable.folder_icon);
            else
            	imgViewChecked.setImageResource(R.drawable.document_icon);
        }

        return itemView;
	}

}
