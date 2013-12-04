package org.opencv.javacv.facerecognition.camerastate;

import java.io.FileOutputStream;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.javacv.facerecognition.FdActivity;

import android.graphics.Bitmap;
import android.util.Log;

public final class SaveOneFrameState extends CameraState {

	private final static int WIDTH = 128;
	private final static int HEIGHT = 128;
	
	private String fileName = null;
	
	private CameraState oldState = null;
	
	public SaveOneFrameState(FdActivity cameraActivity, String fileName) {
		super(cameraActivity);
		this.fileName = fileName;
		this.oldState = cameraActivity.getCameraState();
	}
	
	@Override
	public Mat execute(Mat faceImage) {
		Bitmap bmp = Bitmap.createBitmap(faceImage.width(), 
										faceImage.height(), 
										Bitmap.Config.ARGB_8888);
		
		Utils.matToBitmap(faceImage,bmp);
		bmp = Bitmap.createScaledBitmap(bmp, WIDTH, HEIGHT, false);
		
		FileOutputStream imageFile = null;
		try {
			imageFile = new FileOutputStream(fileName+".jpg",true);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, imageFile);
			imageFile.close();
		} catch (Exception e) {
			Log.e("error",e.getCause()+" "+e.getMessage());
		}
		
		//Grava so um frame
		this.cameraActivity.setCameraState(this.oldState);
		
		return faceImage;
	}

}
