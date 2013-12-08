package org.opencv.javacv.facerecognition.camerastate;

//import static com.googlecode.javacv.cpp.opencv_highgui.*;
//import static com.googlecode.javacv.cpp.opencv_core.*;

//import static com.googlecode.javacv.cpp.opencv_imgproc.*;
//import static com.googlecode.javacv.cpp.opencv_contrib.*;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.javacv.facerecognition.FdActivity;
import org.opencv.javacv.facerecognition.utils.FaceDetectionUtils;

import org.opencv.objdetect.CascadeClassifier;

import android.util.Log;

public final class FaceDetectionState extends CameraState {
	private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
	
	public FaceDetectionState(FdActivity cameraActivity) {
		super(cameraActivity);
	}

	@Override
	public Mat execute(Mat image) {
		Rect[] facesArray = FaceDetectionUtils.getFacesFromMat(image);
		
		//Desenha o rectangulo a volta da face
		if(facesArray.length > 0)
			Core.rectangle(image, facesArray[0].tl(), facesArray[0].br(), FACE_RECT_COLOR, 3);
		
		return image;
	}

}
