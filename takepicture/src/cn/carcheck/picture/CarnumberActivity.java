package cn.carcheck.picture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
				Intent intent = new Intent(pThis, TakePhotoActivity.class);
				pThis.startActivity(intent);
			}
		});

	}
	
private void creatfile(){
	
}	
}
