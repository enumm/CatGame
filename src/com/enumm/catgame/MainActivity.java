package com.enumm.catgame;

import com.enumm.catgame.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {

	Activity activity;
	GameView mGameView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
	    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	    WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_main);
		activity = this;
		mGameView = new GameView(activity);
		mGameView.mThread.doStart();
		setContentView(mGameView);	
		
//		final Button btnPlay = (Button) findViewById(R.id.button1);
//        
//		btnPlay.setOnClickListener(new  View.OnClickListener() {
//        	
//        	public void onClick(View v) {
//        		activity = this;
//        		mGameView = new GameView(activity);
//        		mGameView.mThread.doStart();
//        		setContentView(mGameView);
//        	}
//        });
//        
//        final Button btnExit = (Button) findViewById(R.id.button2);
//        
//        btnExit.setOnClickListener(new  View.OnClickListener() {
//        	
//        	public void onClick(View v) {
//        		finish();
//                System.exit(0);
//        	}
//        });
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	mGameView.mThread.onKeyDownBack();    	
	    	
	        return true;
	    }
	    
	    return super.onKeyDown(keyCode, event);
	}
	
//	 @Override
//	 protected void onPause() {
//	     // TODO Auto-generated method stub
//	     super.onPause();
//	     mGameView.StopView();
//		 //setContentView(R.layout.activity_main);
//	 }
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	  try {
		//mGameView.mThread.onToucha(event);
	    mGameView.mThread.onTouch(event);
	  } catch(Exception e) {}
	  
	  return true;
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}
	
}