package cn.carcheck.picture;

import java.io.File;
import java.io.FileOutputStream;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
	private View layout;
	private Camera camera;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		
		layout=this.findViewById(R.id.buttonlayout);
		
		SurfaceView surfaceView = (SurfaceView) this.findViewById(R.id.surfaceView);
		surfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceView.getHolder().setFixedSize(176, 144);
		surfaceView.getHolder().setKeepScreenOn(true);
		surfaceView.getHolder().addCallback(new SurfaceCallback());
	}
	public void takepicture(View v){
		if(camera!=null){
	        switch(v.getId()){
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
	private final class MyPictureCallback implements PictureCallback{
		public void onPictureTaken(byte[] data, Camera camera) {
			try {
				File jpgFile=new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");
				FileOutputStream outstream = new FileOutputStream(jpgFile);
				outstream.write(data);
				outstream.close();
			    camera.startPreview();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
	
    private final class SurfaceCallback implements Callback{
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
			
	}
	public void surfaceCreated(SurfaceHolder holder) {
		try{
		     camera=Camera.open();//´ò¿ªÉãÏñÍ·
		     Camera.Parameters parameters=camera.getParameters();
		     //Log.i("MainActivity", parameters.flatten());
		     parameters.setPreviewSize(800, 480);
		     parameters.setPreviewFrameRate(5);
		     parameters.setPictureSize(1024, 768);
		     parameters.setJpegQuality(80);
		     camera.setParameters(parameters);
		     camera.setPreviewDisplay(holder);
		}catch(Exception e){
			e.printStackTrace();
		}	
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
			if(camera!=null){
				camera.release();
				camera=null;
			}
	}
	
	
}

@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(event.getAction() == MotionEvent.ACTION_DOWN){
			layout.setVisibility(ViewGroup.VISIBLE);
			return true;
		}
		return super.onTouchEvent(event);
	}

}