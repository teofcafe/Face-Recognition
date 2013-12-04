package org.opencv.javacv.facerecognition.camerastate;

import org.opencv.core.Mat;
import org.opencv.javacv.facerecognition.FdActivity;

public class FaceRecognitionState extends CameraState {

	public FaceRecognitionState(FdActivity cameraActivity) {
		super(cameraActivity);
	}

	@Override
	public Mat execute(Mat face) {
		return face;
	}
	
}
