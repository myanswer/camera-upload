package cn.carcheck.picture;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class UploadfileActivity extends Activity {
	// 要上传的文件路径，理论上可以传输任何文件，实际使用时根据需要处理
	private String uploadFile;
	// 服务器上接收文件的处理页面
	private String actionUrl = "http://192.168.1.103/receive_file.php";
	private TextView mText1;
	private TextView mText2;
	private Button mButton;
	private final FileManager mFileManager = new FileManager();

	@Override
	protected void onResume() {

		super.onResume();

		this.mFileManager.load();

		String file, url;
		url = this.mFileManager.getProperties().getProperty(Const.upload_url);

		file = "";
		File dir = this.mFileManager.getPath();
		for (File f : dir.listFiles()) {
			file = file + f.getName() + "\n";
		}

		mText1.setText("文件名称\n" + file);
		mText2.setText("上传URL\n" + url);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_uploadfile);

		mText1 = (TextView) findViewById(R.id.myText2);
		mText1.setText("文件名称\n" + uploadFile);
		mText2 = (TextView) findViewById(R.id.myText3);
		mText2.setText("上传路径\n" + actionUrl);
		/* 设置mButton的onClick事件处理 */
		mButton = (Button) findViewById(R.id.myButton);

		mButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {

				UploadfileActivity.this.mFileManager.doUpload();
			}
		});

		this.setupSettingsButtonListener();
	}

	private void setupSettingsButtonListener() {

		Button button = (Button) this.findViewById(R.id.button_settings);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				UploadfileActivity.this.showSettingsActivity();
			}
		});
	}

	protected void showSettingsActivity() {
		String path = this.mFileManager.getPath().getAbsolutePath();
		Intent intent = new Intent(this, SettingsActivity.class);
		intent.putExtra(Const.car_dir_path, path);
		this.startActivity(intent);
	}

}
