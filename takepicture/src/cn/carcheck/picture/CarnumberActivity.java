package cn.carcheck.picture;

import java.io.File;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class CarnumberActivity extends Activity {
    //界面初始化
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_carnumber);
		this.setstartButtonListener();
	}

/**
 * 设置监听器
 * */
	private void setstartButtonListener() {

		Button btn = (Button) this.findViewById(R.id.start);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				CarnumberActivity pThis = CarnumberActivity.this;
				String path = pThis.creatfile().getAbsolutePath();
				Intent intent = new Intent(pThis, TakePhotoActivity.class);
				intent.putExtra(Const.car_dir_path, path);
				pThis.startActivity(intent);
			}
		});

	}
	/**
	 * 创建文件的路径
	 * */
	private File creatfile() {
		EditText edit = (EditText) this.findViewById(R.id.carnum);
		String str = edit.getText().toString();
		File file = android.os.Environment.getExternalStorageDirectory();
		file = new File(file, "takepicture/" + str);
		return file;
	}
}
