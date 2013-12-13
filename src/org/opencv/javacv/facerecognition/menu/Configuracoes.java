package org.opencv.javacv.facerecognition.menu;

import java.util.ArrayList;
import java.util.List;

import org.opencv.javacv.facerecognition.FdActivity;
import org.opencv.javacv.facerecognition.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Spinner;

public class Configuracoes extends Fragment {

	private Spinner spn1 = null;
	private List<String> nomes = new ArrayList<String>();
	private ArrayAdapter<String> arrayAdapter = null;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.options_menu, container, false);

		nomes.add("AES");
		nomes.add("AES + Blowfish");
		nomes.add("3XDES + Blowfish");
		nomes.add("AES + Blowfish + 3XDES");

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);

		spn1 = (Spinner) getActivity().findViewById(R.id.spinner1);
		arrayAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_dropdown_item, nomes);
		ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		spn1.setAdapter(spinnerArrayAdapter);

		Button changePasswordButton = (Button) getActivity().findViewById(R.id.button1);
		changePasswordButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ChangePasswordMenu.class);
				startActivity(intent);
			}
		});

		Button changePinButton = (Button) getActivity().findViewById(R.id.button2);
		changePinButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), ChangePinMenu.class);
				startActivity(intent);
			}
		});
		
		Button updateFace = (Button) getActivity().findViewById(R.id.button3);
		updateFace.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FdActivity.updateFace = true;
				Intent intent = new Intent(getActivity(), FdActivity.class);
				startActivity(intent);
			}
		});
		
		final RadioButton securityRadioButton = (RadioButton) getActivity().findViewById(R.id.seguranca);
		final RadioButton intermidiateRadioButton = (RadioButton) getActivity().findViewById(R.id.intermedio);
		final RadioButton speedRadioButton = (RadioButton) getActivity().findViewById(R.id.velocidade);

		securityRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				intermidiateRadioButton.setChecked(false);
				speedRadioButton.setChecked(false);
				if(!nomes.get(0).equals("AES + Blowfish + 3XDES")){
					int aux= nomes.indexOf("AES + Blowfish + 3XDES");
					nomes.set(aux, nomes.get(0));
					nomes.set(0,"AES + Blowfish + 3XDES");
					arrayAdapter.notifyDataSetChanged();
				}
				
			}
		});

		intermidiateRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				securityRadioButton.setChecked(false);
				speedRadioButton.setChecked(false);
				if(!nomes.get(0).equals("AES + Blowfish")){
					int aux= nomes.indexOf("AES + Blowfish");
					nomes.set(aux, nomes.get(0));
					nomes.set(0,"AES + Blowfish");
					arrayAdapter.notifyDataSetChanged();
				}
			}
		});

		speedRadioButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				securityRadioButton.setChecked(false);
				intermidiateRadioButton.setChecked(false);
				if(!nomes.get(0).equals("AES")){
					int aux= nomes.indexOf("AES");
					nomes.set(aux, nomes.get(0));
					nomes.set(0, "AES");
					arrayAdapter.notifyDataSetChanged();
				}
	
			}
		});
	}


}