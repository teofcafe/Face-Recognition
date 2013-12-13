package org.opencv.javacv.facerecognition.menu;

import java.io.File;

import org.opencv.javacv.facerecognition.R;
import org.opencv.javacv.facerecognition.cipher.AESCipher;
import org.opencv.javacv.facerecognition.cipher.BlowFishCipher;
import org.opencv.javacv.facerecognition.cipher.TripleDESCipher;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class InitialRequest extends Activity{
	EditText password = (EditText)findViewById(R.id.editText1);
	EditText confirmPassword = (EditText)findViewById(R.id.editText2);
	EditText pin = (EditText)findViewById(R.id.editText3);
	EditText confirmPin = (EditText)findViewById(R.id.editText3);
	Button confirmation = (Button)findViewById(R.id.button2);
	Button cancel = (Button)findViewById(R.id.button1);
	File safety = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.initial_request);	
		
		confirmation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if((password.getText().equals(confirmPassword.getText())) &&
						pin.getText().equals(confirmPin.getText())){
					
					TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
					String key = pin.toString() + telephonyManager.getDeviceId();	
					AESCipher aesCipher = new AESCipher(key.getBytes());
					BlowFishCipher blowfish = new BlowFishCipher(key.getBytes());
					TripleDESCipher tripleDESCipher = new TripleDESCipher(key.getBytes());
					
					byte[] result = tripleDESCipher.cipher(blowfish.cipher(aesCipher.cipher(password.toString().getBytes())));
				}
			}
		});
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				moveTaskToBack(true);	
			}
		});
	}
}
