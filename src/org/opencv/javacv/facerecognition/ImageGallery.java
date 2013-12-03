package org.opencv.javacv.facerecognition;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class ImageGallery extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.photos_confirmation_view);
		
		Intent intent = getIntent();
	    String imagesPath = intent.getStringExtra(FdActivity.EXTRA_MESSAGE);
		
		GridView gridview = (GridView) findViewById(R.id.gridview);
	    gridview.setAdapter(new ImageAdapter(this.getBaseContext(), imagesPath));

	    gridview.setOnItemClickListener(new OnItemClickListener() {
	        
	    	@Override
	    	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	            Toast.makeText(ImageGallery.this, "" + position, Toast.LENGTH_SHORT).show();
	        }
	    });
	}
	
	@Override
	public void onPause() {
		
	}
}
