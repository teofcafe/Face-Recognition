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
	
	private File[] items = null;

	public FileItemAdapter(final File[] items) {
        this.items = items;
    }
	
	public void setItems(File[] items) {
		this.items=items;
	}

	@Override
	public int getCount() {
		return this.items.length;
	}

	@Override
	public Object getItem(int position) {
		 return this.items[position];
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final File item = this.items[position];
        View itemView = null;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) parent.getContext()
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            itemView = inflater.inflate(R.layout.item_list, null);
        } else {
            itemView = convertView;
        }

        // Set the text of the item
        TextView txtName = (TextView) itemView.findViewById(R.id.name);
        txtName.setText(item.getName());
        

        // Set the check-icon
        ImageView imgViewChecked = (ImageView) itemView
                .findViewById(R.id.file_icon);
        imgViewChecked.setImageResource(R.drawable.lightbulb);

        return itemView;
	}

}
