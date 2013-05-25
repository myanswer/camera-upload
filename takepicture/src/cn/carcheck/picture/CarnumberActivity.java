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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.activity_carnumber);
		this.setstartButtonListener();
	}

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

	private File creatfile() {
		EditText edit = (EditText) this.findViewById(R.id.carnum);
		String str = edit.getText().toString();
		File file = android.os.Environment.getExternalStorageDirectory();
		file = new File(file, "takepicture/" + str);
		return file;
	}
}
