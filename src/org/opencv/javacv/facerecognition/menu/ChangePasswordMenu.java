package org.opencv.javacv.facerecognition.menu;

import org.opencv.javacv.facerecognition.R;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ChangePasswordMenu extends Activity {
	EditText password = (EditText)findViewById(R.id.editText1);
	EditText newPassword = (EditText)findViewById(R.id.editText2);
	TextView newPasswordConfirmation = (EditText)findViewById(R.id.editText3);
	Button confirmation = (Button)findViewById(R.id.button1);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepw);	
		
		confirmation.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//if(password)
				TelephonyManager telephonyManager = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
				telephonyManager.getDeviceId();
				
			}
		});
	}
}
