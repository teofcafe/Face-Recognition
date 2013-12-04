package org.opencv.javacv.facerecognition.matdecorator;

import org.opencv.core.Mat;
import org.opencv.javacv.facerecognition.FdActivity;

public abstract class MatDecorator {

	FdActivity cameraActivity = null;

	public MatDecorator(FdActivity cameraActivity) {
		this.cameraActivity = cameraActivity;
	}

	public abstract Mat decorate(Mat face);
}
