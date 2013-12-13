package org.opencv.javacv.facerecognition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.javacv.facerecognition.R;
import org.opencv.javacv.facerecognition.camerastate.CameraState;
import org.opencv.javacv.facerecognition.camerastate.FaceDetectionState;
import org.opencv.javacv.facerecognition.camerastate.FaceRecognitionState;
import org.opencv.javacv.facerecognition.camerastate.SaveOneFrameState;
import org.opencv.javacv.facerecognition.menu.InsertPassword;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.objdetect.CascadeClassifier;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;

public class FdActivity extends Activity implements CvCameraViewListener2 {

	private static final String TAG = "OCVSample::Activity";
	public final static String EXTRA_MESSAGE = "IMAGES_PATH";

	private static final int frontCam =1;
	private static final int backCam =2;

	public CascadeClassifier mJavaDetector = null;
	public static String APP_PATH = null;
	public static String CASCADES_PATH = null;
	public static String YML_FACE_MODEL_PATH = null;
	public static String REPOSITORY_PATH = null;
	public static String SDCARD_PATH = null;

	public static String IMAGES_TO_ACCEPT_PATH = null;
	public static String YML_FACE_MODEL_FILE_PATH = null;
	public static String CASCADE_FRONTAL_FACE_FILE_PATH = null;

	private MenuItem               nBackCam;
	private MenuItem               mFrontCam;



	private Tutorial3View   mOpenCvCameraView;
	private CameraState cameraState = null;
	private int mChooseCamera = backCam;
	private int counter = 0;

	public static boolean firstTime = true;
	public static boolean updateFace = false;

	ImageButton toggleButtonTrain;
	ImageButton imCamera;

	com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer faceRecognizer;

	private boolean loadCascadeFile(int cascadeFileId, String dest) {
		File cascadeFile = new File(dest);

		if(cascadeFile.exists()) return true;

		try {
			// load cascade file from application resources
			InputStream is = getResources().openRawResource(cascadeFileId);
			FileOutputStream os = new FileOutputStream(cascadeFile);

			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = is.read(buffer)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			is.close();
			os.close();

			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
			case LoaderCallbackInterface.SUCCESS:
			{	
				if(new File(FdActivity.YML_FACE_MODEL_FILE_PATH).exists())
					FdActivity.this.cameraState = new FaceRecognitionState(FdActivity.this);
				else if(loadCascadeFile(R.raw.lbpcascade_frontalface, FdActivity.CASCADE_FRONTAL_FACE_FILE_PATH))
					FdActivity.this.cameraState = new FaceDetectionState(FdActivity.this);

				mOpenCvCameraView.enableView();
			} break;
			default:
			{
				super.onManagerConnected(status);
			} break;
			}
		}
	};

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "called onCreate");
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.face_detect_surface_view);

		FdActivity.APP_PATH = getFilesDir().getAbsolutePath();
		FdActivity.CASCADES_PATH = FdActivity.APP_PATH + "/cascades/";

		FdActivity.IMAGES_TO_ACCEPT_PATH = FdActivity.APP_PATH + "/ImagesToAccept/";
		FdActivity.YML_FACE_MODEL_PATH = FdActivity.APP_PATH + "/facemodel/";
		FdActivity.REPOSITORY_PATH = FdActivity.APP_PATH + "/repository/";
		FdActivity.SDCARD_PATH = "/sdcard/";

		FdActivity.YML_FACE_MODEL_FILE_PATH = FdActivity.APP_PATH + "/facemodel/facemodel.yml";
		FdActivity.CASCADE_FRONTAL_FACE_FILE_PATH = FdActivity.CASCADES_PATH + "lbpfrontalcascade.xml";

		(new File(FdActivity.CASCADES_PATH)).mkdirs();
		(new File(FdActivity.YML_FACE_MODEL_PATH)).mkdirs();
		(new File(FdActivity.IMAGES_TO_ACCEPT_PATH)).mkdirs();
		(new File(FdActivity.REPOSITORY_PATH)).mkdirs();

		mOpenCvCameraView = (Tutorial3View) findViewById(R.id.tutorial3_activity_java_surface_view);
		mOpenCvCameraView.setCvCameraViewListener(this);

		toggleButtonTrain=(ImageButton)findViewById(R.id.takePhotoButton);
		imCamera=(ImageButton)findViewById(R.id.imageButton1);

		toggleButtonTrain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FdActivity.this.cameraState = new SaveOneFrameState(FdActivity.this, FdActivity.IMAGES_TO_ACCEPT_PATH + String.valueOf(FdActivity.this.counter++));
			}
		});


		ImageButton acceptFaces = (ImageButton) findViewById(R.id.acceptFaces);
		acceptFaces .setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ImageGallery.class);
				intent.putExtra(EXTRA_MESSAGE, FdActivity.IMAGES_TO_ACCEPT_PATH);
				startActivity(intent);
			}
		});

		ImageButton insertPasswordMenu = (ImageButton) findViewById(R.id.insertPassword);
		insertPasswordMenu.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), InsertPassword.class);
				startActivity(intent);
			}
		});

		firstTime = !new File(FdActivity.YML_FACE_MODEL_FILE_PATH).exists();

		if(firstTime || updateFace) {
			ImageButton b = (ImageButton) findViewById(R.id.acceptFaces);
			b.setVisibility(View.VISIBLE);
		} else {
			ImageButton b1 = (ImageButton) findViewById(R.id.acceptFaces);
			b1.setVisibility(View.GONE);
			ImageButton b2 = (ImageButton) findViewById(R.id.takePhotoButton);
			b2.setVisibility(View.GONE);
		}

		imCamera.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (mChooseCamera==frontCam) {
					mChooseCamera=backCam;
					mOpenCvCameraView.setCamBack();
				} else {
					mChooseCamera=frontCam;
					mOpenCvCameraView.setCamFront();
				}
			}
		});
	}

	@Override
	public void onPause() {
		super.onPause();
		if (mOpenCvCameraView != null)
			mOpenCvCameraView.disableView();       
	}

	@Override
	public void onResume() {
		super.onResume();
		OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_2_4_3, this, mLoaderCallback);
	}

	public void onDestroy() {
		super.onDestroy();
		mOpenCvCameraView.disableView();
	}

	public void onCameraViewStarted(int width, int height) {
		//Nothing to do, I guess
	}

	public void onCameraViewStopped() {
		//Nothing to do, maybe
	}

	//Fazer o release dos mats para libertar espaco talvez
	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
		return this.cameraState.execute(inputFrame.rgba());
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.i(TAG, "called onCreateOptionsMenu");
		if (mOpenCvCameraView.numberCameras()>1) {
			nBackCam = menu.add(getResources().getString(R.string.SFrontCamera));
			mFrontCam = menu.add(getResources().getString(R.string.SBackCamera));
		} else imCamera.setVisibility(View.GONE);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Log.i(TAG, "called onOptionsItemSelected; selected item: " + item);
		nBackCam.setChecked(false);
		mFrontCam.setChecked(false);
		if (item == nBackCam) {
			mOpenCvCameraView.setCamFront();
			mChooseCamera=frontCam;
		} else if (item==mFrontCam) {
			mChooseCamera=backCam;
			mOpenCvCameraView.setCamBack();
		}

		item.setChecked(true);

		return true;
	}

	public void setCameraState(CameraState newCameraState) {
		this.cameraState = newCameraState;
	}

	public CameraState getCameraState() {
		return cameraState;
	}
}
