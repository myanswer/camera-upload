package cn.carcheck.picture;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class UploadfileActivity extends Activity {
	// 要上传的文件路径，理论上可以传输任何文件，实际使用时根据需要处理
	private String uploadFile;
	private String srcPath;
	// 服务器上接收文件的处理页面
	private String actionUrl = "http://192.168.1.103/receive_file.php";
	private TextView mText1;
	private TextView mText2;
	private Button mButton;
	private FileManager mFileManager;

	@Override
	protected void onResume() {

		super.onResume();
		String path = this.getIntent().getStringExtra(Const.car_dir_path);
		if (path != null) {
			File file = new File(path);
			file.mkdirs();

			this.uploadFile = this.srcPath = file.getAbsolutePath();

			FileManager fm = new FileManager(file);
			this.mFileManager = fm;
		}
		mText1.setText("文件名称\n" + uploadFile);
		mText2.setText("上传路径\n" + actionUrl);
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
	}

}
