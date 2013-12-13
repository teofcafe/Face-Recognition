package org.opencv.javacv.facerecognition.menu;

import java.io.File;
import java.util.List;
import org.opencv.javacv.facerecognition.FdActivity;
import org.opencv.javacv.facerecognition.R;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class SDCard extends Fragment {

	private ListView listView;
	private FileItemAdapter fileItemAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.repositorio, container, false);

		return rootView;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);

		fileItemAdapter = new FileItemAdapter(new File(FdActivity.SDCARD_PATH));
		listView = (ListView) getActivity().findViewById(R.id.listView1);
		listView.setAdapter(fileItemAdapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View v, int pos,
					long id) {

				if(pos == 0 && !fileItemAdapter.isOnRoot()) {
					fileItemAdapter.goBack();
				} else {
					File item = (File) listView.getItemAtPosition(pos);
					if(item.isDirectory()){
						fileItemAdapter.goTo(pos);
					}else{
						Uri webPage = Uri.parse("http://www.android.com");
						Intent webIntent = new Intent(Intent.ACTION_VIEW, webPage);
						PackageManager pmanager = getActivity().getPackageManager();
						List<ResolveInfo> activities = pmanager.queryIntentActivities(webIntent, 0);
						if(activities.size() > 0)
							startActivity(webIntent);

					}
				}
			}
		});

	}

}