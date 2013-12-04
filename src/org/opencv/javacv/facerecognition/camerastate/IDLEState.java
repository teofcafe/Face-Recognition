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

import org.opencv.objdetect.CascadeClassifier;

public final class IDLEState extends CameraState {

	private float mRelativeFaceSize = 0.2f;
	private int mAbsoluteFaceSize = 0;
	private CascadeClassifier javaDetector = null;
	private static final Scalar FACE_RECT_COLOR = new Scalar(0, 255, 0, 255);
	
	
	public IDLEState(FdActivity cameraActivity) {
		super(cameraActivity);
		
		this.javaDetector = cameraActivity.mJavaDetector;
	}

	@Override
	public Mat execute(Mat face) {
		Mat grayFace = new Mat();
		
		Imgproc.cvtColor(face, grayFace, Imgproc.COLOR_BGR2GRAY);
		
		MatOfRect faces = new MatOfRect();

		//Detecta as faces e coloca na variavel faces
		javaDetector.detectMultiScale(grayFace, faces, 1.1, 2, 2,
				new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
		
		Rect[] facesArray = faces.toArray();
		
		//Desenha o rectangulo a volta da face
		if(facesArray.length > 0)
			Core.rectangle(face, facesArray[0].tl(), facesArray[0].br(), FACE_RECT_COLOR, 3);
		
		return face;
	}

}
