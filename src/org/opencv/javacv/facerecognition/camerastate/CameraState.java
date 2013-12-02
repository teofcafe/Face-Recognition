package org.opencv.javacv.facerecognition.camerastate;

import org.opencv.core.Mat;
import org.opencv.javacv.facerecognition.FdActivity;

//As classes que estendem esta devem de ser final
//Nao tem logica permitir extensoes dessas classes
public abstract class CameraState {
	FdActivity cameraActivity = null;
	
	public CameraState(FdActivity cameraActivity) {
		this.cameraActivity = cameraActivity;
	}
	
	public abstract void execute(Mat face);
}
