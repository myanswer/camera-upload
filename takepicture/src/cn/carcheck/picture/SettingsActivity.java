package cn.carcheck.picture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity {

	private final FileManager mFileManager = new FileManager();
	private EditText mEditURL;

	// 界面初始化
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_settings);
		this.setOkButtonListener();
		this.setCancelButtonListener();
		this.mEditURL = (EditText) this.findViewById(R.id.edit_url);
	}

	@Override
	protected void onResume() {

		super.onResume();

		this.mFileManager.load();
		String url = this.mFileManager.getProperties().getProperty(
				Const.upload_url);
		this.mEditURL.setText(url);
	}

	private void setCancelButtonListener() {
		Button btn = (Button) this.findViewById(R.id.button_cancel);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SettingsActivity.this.goBack();
			}
		});

	}

	private void setOkButtonListener() {
		Button btn = (Button) this.findViewById(R.id.button_ok);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				SettingsActivity.this.saveSettings();
				SettingsActivity.this.goBack();
			}
		});

	}

	protected void saveSettings() {
		String url = this.mEditURL.getText().toString();
		this.mFileManager.getProperties().setProperty(Const.upload_url, url);
		this.mFileManager.save();
	}

	public void goBack() {
		Intent intent = new Intent(this, UploadfileActivity.class);
		SettingsActivity.this.startActivity(intent);
	}

}
