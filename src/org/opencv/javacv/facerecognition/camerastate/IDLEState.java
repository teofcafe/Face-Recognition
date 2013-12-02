package org.opencv.javacv.facerecognition.camerastate;

import org.opencv.core.Mat;
import org.opencv.javacv.facerecognition.FdActivity;

public final class IDLEState extends CameraState {

	public IDLEState(FdActivity cameraActivity) {
		super(cameraActivity);
	}

	@Override
	public void execute(Mat face) {
		//Nothing is needed to detect faces.
	}

}
