package org.opencv.javacv.facerecognition.matdecorator;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.javacv.facerecognition.FdActivity;
import org.opencv.objdetect.CascadeClassifier;

public final class FaceIsolationDecorator extends MatDecorator{
	
	private CascadeClassifier javaDetector = null;
	private float mRelativeFaceSize = 0.2f;
	private int mAbsoluteFaceSize = 0;
	
	public FaceIsolationDecorator(FdActivity cameraActivity) {
		super(cameraActivity);
	}

	@Override
	public Mat decorate(Mat face) {		
		Mat grayFace = new Mat();
		
		Imgproc.cvtColor(face, grayFace, Imgproc.COLOR_BGR2GRAY);

		MatOfRect faces = new MatOfRect();

		//Detecta as faces e coloca na variavel faces
		javaDetector.detectMultiScale(grayFace, faces, 1.1, 2, 2,
				new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());

		Rect[] facesArray = faces.toArray();

		if(facesArray.length > 0)
			return grayFace.submat(facesArray[0]);
		
		//TODO Exception
		return null;
	}

}
