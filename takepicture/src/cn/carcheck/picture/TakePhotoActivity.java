package cn.carcheck.picture;

import java.io.File;
import java.io.FileOutputStream;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class TakePhotoActivity extends Activity {
	private View layout;
	private Camera camera;
	private FileManager mFileManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.activity_camera);

		layout = this.findViewById(R.id.buttonlayout);

		{
			SurfaceView surfaceView = (SurfaceView) this
					.findViewById(R.id.surfaceView);
			surfaceView.getHolder().setType(
					SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
			surfaceView.getHolder().setFixedSize(176, 144);
			surfaceView.getHolder().setKeepScreenOn(true);
			surfaceView.getHolder().addCallback(new SurfaceCallback());

		}

		this.setupButtonListenerForUpload();
		this.setupButtonListenerForTakephoto();

	}

	@Override
	protected void onResume() {

		super.onResume();
		String path = this.getIntent().getStringExtra(Const.car_dir_path);
		if (path != null) {
			File file = new File(path);
			file.mkdirs();
			this.mFileManager = new FileManager(file);
		}

	}

	private void setupButtonListenerForUpload() {

		Button btn = (Button) this.findViewById(R.id.button_upload);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TakePhotoActivity pThis = TakePhotoActivity.this;
				Intent intent = new Intent(pThis, UploadfileActivity.class);
				File path = pThis.mFileManager.getPath();
				intent.putExtra(Const.car_dir_path, path.getAbsolutePath());
				pThis.startActivity(intent);
			}
		});

	}

	private void setupButtonListenerForTakephoto() {

		Button btn = (Button) this.findViewById(R.id.button_shut);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				TakePhotoActivity pThis = TakePhotoActivity.this;
				pThis.camera.takePicture(null, null, new MyPictureCallback());
			}
		});

	}

	public void takepicture(View v) {
		if (camera != null) {
			switch (v.getId()) {
			case R.id.takepicture:
				camera.takePicture(null, null, new MyPictureCallback());
				break;
			case R.id.autofocus:
				camera.autoFocus(null);
			default:
				break;
			}
		}
	}

	private final class MyPictureCallback implements PictureCallback {
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				File path = TakePhotoActivity.this.mFileManager.getPath();
				File jpgFile = new File(path, System.currentTimeMillis()
						+ ".jpg");
				FileOutputStream outstream = new FileOutputStream(jpgFile);
				outstream.write(data);
				outstream.close();
				camera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private final class SurfaceCallback implements Callback {
		public void surfaceChanged(SurfaceHolder holder, int format, int width,
				int height) {

		}

		public void surfaceCreated(SurfaceHolder holder) {
			System.out.println(this + ".surfaceCreated");
			try {
				camera = Camera.open();// ������ͷ
				boolean noused = true;
				if (!noused) {
					Camera.Parameters parameters = camera.getParameters();
					// Log.i("MainActivity", parameters.flatten());
					parameters.setPreviewSize(800, 480);
					parameters.setPreviewFrameRate(5);
					parameters.setPictureSize(1024, 768);
					parameters.setJpegQuality(80);
					camera.setParameters(parameters);
				}
				camera.setPreviewDisplay(holder);
				camera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		public void surfaceDestroyed(SurfaceHolder holder) {
			System.out.println(this + ".surfaceDestroyed");
			if (camera != null) {
				camera.release();
				camera = null;
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			layout.setVisibility(ViewGroup.VISIBLE);
			return true;
		}
		return super.onTouchEvent(event);
	}

}