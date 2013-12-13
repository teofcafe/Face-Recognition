package org.opencv.javacv.facerecognition.menu;

import org.opencv.javacv.facerecognition.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePasswordMenu extends Activity {
	EditText password = null;
	EditText newPassword = null;
	TextView newPasswordConfirmation = null;
	Button confirmation = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepw);	
		
		password = (EditText)findViewById(R.id.editText1);
		newPassword = (EditText)findViewById(R.id.editText2);
		newPasswordConfirmation = (EditText)findViewById(R.id.editText3);
		confirmation = (Button)findViewById(R.id.button1);
		
		confirmation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
//				TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
//				telephonyManager.getDeviceId();
				
			}
		});
	}
}
