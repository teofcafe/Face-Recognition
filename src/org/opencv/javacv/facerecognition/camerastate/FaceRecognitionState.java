package org.opencv.javacv.facerecognition.camerastate;

import static com.googlecode.javacv.cpp.opencv_core.IPL_DEPTH_8U;
import static com.googlecode.javacv.cpp.opencv_imgproc.cvCvtColor;

import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.javacv.facerecognition.FdActivity;
import org.opencv.javacv.facerecognition.menu.MenuInicial;
import org.opencv.javacv.facerecognition.utils.FaceDetectionUtils;

import com.googlecode.javacv.cpp.opencv_imgproc;
import com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer;
import com.googlecode.javacv.cpp.opencv_core.IplImage;

import android.content.Intent;
import android.graphics.Bitmap;

public class FaceRecognitionState extends CameraState {
	private final static Scalar COR_VERDE = new Scalar(0, 255, 0, 255);
	private final static Scalar COR_ROSA = new Scalar(255, 0, 255, 0);

	private static Scalar FACE_RECT_COLOR = COR_VERDE;

	private final static int WIDTH = 128;
	private final static int HEIGHT = 128;

	FaceRecognizer faceRecognizer = com.googlecode.javacv.cpp.opencv_contrib.createLBPHFaceRecognizer();;

	public FaceRecognitionState(FdActivity cameraActivity) {
		super(cameraActivity);

		faceRecognizer.load(FdActivity.YML_FACE_MODEL_FILE_PATH);
	}

	@Override
	public Mat execute(Mat image) {

		Rect[] facesArray = FaceDetectionUtils.getFacesFromMat(image);
		if(facesArray.length <= 0) return image;

		Mat isolatedFace = image.submat(facesArray[0]);
		IplImage iplIsolatedFace = MatToIplImage(isolatedFace,WIDTH, HEIGHT);
		//Valor discartavel, pois so e aceite uma pessoa
		int[] label = new int[1];
		double[] confidence = new double[1];
		faceRecognizer.predict(iplIsolatedFace, label, confidence);

		if(confidence[0] > 70)
			FACE_RECT_COLOR = COR_VERDE;
		else {
			FACE_RECT_COLOR = COR_ROSA;
			Intent intent = new Intent(super.cameraActivity.getApplicationContext(), MenuInicial.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			super.cameraActivity.getApplicationContext().startActivity(intent);
		}

		//Desenha o rectangulo a volta da face
		if(facesArray.length > 0)
			Core.rectangle(image, facesArray[0].tl(), facesArray[0].br(), FACE_RECT_COLOR, 3);

		return image;
	}

	private IplImage MatToIplImage(Mat m,int width,int heigth) {
		Bitmap bmp=Bitmap.createBitmap(m.width(), m.height(), Bitmap.Config.ARGB_8888);
		Utils.matToBitmap(m, bmp);
		return BitmapToIplImage(bmp,width, heigth);
	}

	private IplImage BitmapToIplImage(Bitmap bmp, int width, int height) {
		if ((width != -1) || (height != -1)) {
			Bitmap bmp2 = Bitmap.createScaledBitmap(bmp, width, height, false);
			bmp = bmp2;
		}
		IplImage image = IplImage.create(bmp.getWidth(), bmp.getHeight(),
				IPL_DEPTH_8U, 4);
		bmp.copyPixelsToBuffer(image.getByteBuffer());
		IplImage grayImg = IplImage.create(image.width(), image.height(),
				IPL_DEPTH_8U, 1);
		cvCvtColor(image, grayImg, opencv_imgproc.CV_BGR2GRAY);
		return grayImg;
	}

}
