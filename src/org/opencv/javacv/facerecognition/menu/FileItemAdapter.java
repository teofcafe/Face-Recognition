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

	public FileItemAdapter(final File root) {
		this.root = root;
		this.parent = this.root;
        this.files = this.parent.listFiles();
    }
	
	public void setItems(File[] items) {
		this.files = items;
	}

	public void setParent(File parent) {
		this.parent = parent;
		this.files = this.parent.listFiles();
	}
	
	public File getParent() {
		return this.parent;
	}
	
	@Override
	public int getCount() {
		return this.files.length;
	}

	@Override
	public Object getItem(int position) {
		 return this.files[position-1];
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

        if(position == 0) {
        	txtName.setText("..");
        	imgViewChecked.setImageResource(R.drawable.basic_undo_icon);
        } else {
        	final File item = this.files[position-1];
        	txtName.setText(item.getName());        
            
            if(item.isDirectory())
            	imgViewChecked.setImageResource(R.drawable.folder_icon);
            else
            	imgViewChecked.setImageResource(R.drawable.document_icon);
        }

        return itemView;
	}

}
