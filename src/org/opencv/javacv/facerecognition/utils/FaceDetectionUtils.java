package org.opencv.javacv.facerecognition.utils;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;
import org.opencv.javacv.facerecognition.FdActivity;
import org.opencv.objdetect.CascadeClassifier;

public final class FaceDetectionUtils {
	private static final float mRelativeFaceSize = 0.2f;
	private static int mAbsoluteFaceSize = 0;
	private static CascadeClassifier javaDetector = null;
	
	static {
		javaDetector = new CascadeClassifier(FdActivity.CASCADE_FRONTAL_FACE_FILE_PATH);
        
		if (javaDetector != null && javaDetector.empty())
            javaDetector = null;
	}
	
	public final static Rect[] getFacesFromMat(Mat image) {
		Mat grayFace = new Mat();
		Imgproc.cvtColor(image, grayFace, Imgproc.COLOR_BGR2GRAY);
		
		if (mAbsoluteFaceSize == 0) {
            int height = grayFace.rows();
            if (Math.round(height * mRelativeFaceSize) > 0) {
                mAbsoluteFaceSize = Math.round(height * mRelativeFaceSize);
            }
        }
		
		MatOfRect faces = new MatOfRect();

		//Detecta as faces e coloca na variavel faces
		javaDetector.detectMultiScale(grayFace, faces, 1.1, 2, 2,
				new Size(mAbsoluteFaceSize, mAbsoluteFaceSize), new Size());
		
		return faces.toArray();
	}
}
