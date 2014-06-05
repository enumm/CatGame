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
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) 
	{
	    if (keyCode == KeyEvent.KEYCODE_BACK)
	    {
	    	mGameView.mThread.onKeyDownBack();    	
	    	
	        return true;
	    }
	   
	    return super.onKeyDown(keyCode, event);
	}
	
	@Override
	 protected void onPause() 
	 {
		//lewl
		System.exit(0);
	 }
	
	 @Override
	 protected void onResume() 
	 {  
	     super.onResume();
	 }
	 
	@Override
	public boolean onTouchEvent(MotionEvent event) {
	  try 
	  {
	    mGameView.mThread.onTouch(event);
	  } 
	  catch(Exception e) 
	  {  
	  }
	  
	  return true;
	}
}