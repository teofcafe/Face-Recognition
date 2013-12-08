package org.opencv.javacv.facerecognition.camerastate;

import java.io.FileOutputStream;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.javacv.facerecognition.FdActivity;
import org.opencv.javacv.facerecognition.utils.FaceDetectionUtils;

import android.graphics.Bitmap;
import android.util.Log;

public final class SaveOneFrameState extends CameraState {

	private final static int WIDTH = 128;
	private final static int HEIGHT = 128;
	private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
	
	private String fileName = null;
	
	private CameraState oldState = null;
	
	public SaveOneFrameState(FdActivity cameraActivity, String fileName) {
		super(cameraActivity);
		this.fileName = fileName;
		this.oldState = cameraActivity.getCameraState();
	}
	
	@Override
	public Mat execute(Mat image) {
		Rect[] facesArray = FaceDetectionUtils.getFacesFromMat(image);
		if(facesArray.length <= 0) return image;
		
		Mat isolatedFace = image.submat(facesArray[0]);
		
		Bitmap bmp = Bitmap.createBitmap(isolatedFace.width(), 
				isolatedFace.height(), Bitmap.Config.ARGB_8888);
		
		Utils.matToBitmap(isolatedFace,bmp);
		bmp = Bitmap.createScaledBitmap(bmp, WIDTH, HEIGHT, false);
		
		FileOutputStream imageFile = null;
		try {
			imageFile = new FileOutputStream(fileName+".jpg",true);
			bmp.compress(Bitmap.CompressFormat.JPEG, 100, imageFile);
			imageFile.close();
		} catch (Exception e) {
			Log.i("Excepcao", e.getMessage());
		}
		
		Core.rectangle(image, facesArray[0].tl(), facesArray[0].br(), FACE_RECT_COLOR, 3);
		
		super.cameraActivity.setCameraState(this.oldState);
		
		return image;
	}

}
