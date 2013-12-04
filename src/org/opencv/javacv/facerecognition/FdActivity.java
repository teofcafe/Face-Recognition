package org.opencv.javacv.facerecognition;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;

import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.core.Mat;
import org.opencv.javacv.facerecognition.R;
import org.opencv.javacv.facerecognition.camerastate.CameraState;
import org.opencv.javacv.facerecognition.camerastate.IDLEState;
import org.opencv.javacv.facerecognition.camerastate.SaveOneFrameState;

import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;

import org.opencv.objdetect.CascadeClassifier;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.os.Build;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;






public class FdActivity extends Activity implements CvCameraViewListener2 {

	private static final String    TAG                 = "OCVSample::Activity";
	public static final int        JAVA_DETECTOR       = 0;
	public static final int        NATIVE_DETECTOR     = 1;

	private static final int frontCam =1;
	private static final int backCam =2;

	private MenuItem               nBackCam;
	private MenuItem               mFrontCam;
	public static File cascadeFile = null;

	private String[]               mDetectorName;

	public final static String EXTRA_MESSAGE = "IMAGES_PATH";
	public CascadeClassifier mJavaDetector = null;
	
	String imagesToAcceptUri = null;

	private Tutorial3View   mOpenCvCameraView;
	private CameraState cameraState = null;
	private int mChooseCamera = backCam;
	private int counter = 0;

	PersonRecognizer fr;
	Button toggleButtonTrain;
	ImageButton imCamera;

	com.googlecode.javacv.cpp.opencv_contrib.FaceRecognizer faceRecognizer;


	static final long MAXIMG = 10;

	ArrayList<Mat> alimgs = new ArrayList<Mat>();

	int[] labels = new int[(int)MAXIMG];
	int countImages=0;

	private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
		@Override
		public void onManagerConnected(int status) {
			switch (status) {
				case LoaderCallbackInterface.SUCCESS:
				{
					Log.i(TAG, "OpenCV loaded successfully");
	
					fr=new PersonRecognizer(imagesToAcceptUri);
					
					try {
                        // load cascade file from application resources
                        InputStream is = getResources().openRawResource(R.raw.lbpcascade_frontalface);
                        File cascadeDir = getDir("cascade", Context.MODE_PRIVATE);
                        File mCascadeFile = new File(cascadeDir, "lbpcascade.xml");
                        FileOutputStream os = new FileOutputStream(mCascadeFile);

                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                        is.close();
                        os.close();

                        mJavaDetector = new CascadeClassifier(mCascadeFile.getAbsolutePath());
                        if (mJavaDetector.empty()) {
                            Log.e(TAG, "Failed to load cascade classifier");
                            mJavaDetector = null;
                        } else
                            Log.i(TAG, "Loaded cascade classifier from " + mCascadeFile.getAbsolutePath());
                        
                        cascadeDir.delete();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e(TAG, "Failed to load cascade. Exception thrown: " + e);
                    }
					
					FdActivity.this.cameraState = new IDLEState(FdActivity.this);
					mOpenCvCameraView.enableView();
				} break;
				default:
				{
					super.onManagerConnected(status);
				} break;
			}
		}
	};

	public FdActivity() {
		mDetectorName = new String[2];
		mDetectorName[JAVA_DETECTOR] = "Java";
		mDetectorName[NATIVE_DETECTOR] = "Native (tracking)";

		Log.i(TAG, "Instantiated new " + this.getClass());
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "called onCreate");
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		setContentView(R.layout.face_detect_surface_view);

		mOpenCvCameraView = (Tutorial3View) findViewById(R.id.tutorial3_activity_java_surface_view);
		mOpenCvCameraView.setCvCameraViewListener(this);
		
		imagesToAcceptUri=getFilesDir()+"/ImagesToAccept/";
		
		toggleButtonTrain=(Button)findViewById(R.id.takePhotoButton);
		imCamera=(ImageButton)findViewById(R.id.imageButton1);

		toggleButtonTrain.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				FdActivity.this.cameraState = new SaveOneFrameState(FdActivity.this, imagesToAcceptUri+String.valueOf(FdActivity.this.counter++));
			}
		});

		ImageButton openPhotosConfirmationMenu = (ImageButton) findViewById(R.id.imageButtonAccept);
		openPhotosConfirmationMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), ImageGallery.class);
				intent.putExtra(EXTRA_MESSAGE, imagesToAcceptUri);
				startActivity(intent);
			}
		});
		
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

		boolean success=(new File(imagesToAcceptUri)).mkdirs();
		if (!success) {
			Log.e("Error","Error creating directory");
		}
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
		} else imCamera.setVisibility(View.INVISIBLE);
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
