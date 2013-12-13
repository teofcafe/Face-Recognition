package org.opencv.javacv.facerecognition.menu;

import org.opencv.javacv.facerecognition.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class InsertPin extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.insertpin);
		
		Button confirmPin = (Button) findViewById(R.id.button1);
		confirmPin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(InsertPin.this.getApplicationContext(), MenuInicial.class);
				startActivity(intent);
			}
		});
		
		
	}
}
