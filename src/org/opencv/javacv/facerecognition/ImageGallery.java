package org.opencv.javacv.facerecognition;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_highgui.cvLoadImage;
import static com.googlecode.javacv.cpp.opencv_imgproc.CV_BGR2GRAY;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

import java.io.File;
import java.io.FilenameFilter;

import com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer;
import com.googlecode.javacv.cpp.opencv_core.IplImage;
import com.googlecode.javacv.cpp.opencv_core.MatVector;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageButton;
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
	    
	    ImageButton acceptAllButton = (ImageButton) findViewById(R.id.imageButton2);
	    acceptAllButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				File imagesToAcceptFolder = new File(FdActivity.IMAGES_TO_ACCEPT_PATH);
				File[] acceptedImages = imagesToAcceptFolder.listFiles(new FilenameFilter() {
					
					@Override
					public boolean accept(File dir, String filename) {
						return (filename.endsWith(".jpg"));
					}
				});
				
				MatVector images = new MatVector(acceptedImages.length);
				int[] labels = new int[acceptedImages.length];
				int counter = 0;
				
				for (File image : acceptedImages) {
		        	String p = image.getAbsolutePath();
		        	IplImage img = cvLoadImage(p);
		            
		            if (img==null) continue;

		            IplImage grayImg = IplImage.create(img.width(), img.height(), IPL_DEPTH_8U, 1);
		            cvCvtColor(img, grayImg, CV_BGR2GRAY);
		            
		            images.put(counter, grayImg);

		            labels[counter++] = 0;
		        }
				
				FaceRecognizer faceRecognizer = com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer();
				faceRecognizer.train(images, labels);
				faceRecognizer.save(FdActivity.YML_FACE_MODEL_FILE_PATH);
				
				Intent intent = new Intent(getApplicationContext(), FdActivity.class);
				startActivity(intent);
			}
		});
	}
	
	@Override
	public void onPause() {
		super.onPause();
	}
}
