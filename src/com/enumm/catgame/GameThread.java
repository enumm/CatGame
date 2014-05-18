package com.enumm.catgame;

//import java.util.ArrayList;
//import java.util.List;
//import java.util.Random;

import java.util.Random;

import com.enumm.catgame.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;


//TODO: speed for different resolutions - game scale works for upscaling, not downscaling 


public class GameThread extends Thread {
	private SurfaceHolder mHolder = null; // Holder reference
	boolean running = false; // exits the thread when set false
	private long mLastTime = 0; // used for maintaining FPS
	private int width, height, fps, frameCount, animationCount, menuFrameCount, distanceCount, distanceFrameCount; // store width and height of canvas,
	
	public enum State {MENU, ABOUT, SCORE, GAME};
	
	State state = State.MENU;
	
	static Random rand;
	
	Bitmap btmScore;
	Bitmap btmAbout;
	//Bitmap btmMenu;
	Bitmap[] btmCat;
	Bitmap btmBackground;
	Bitmap btmNearBackground;
	
	double[] gameScale = new double[2];
	
	//Sprite burger;
	//Sprite enemy;
	
	Enemy[] obstacle;
	Bitmap enemyBitmap;
	Bitmap enemyBitmap1;
	Bitmap friendlyBitmap;
	
	Sprite sprScore;
	Sprite sprAbout;
	Background farBackground;
	Background nearBackground;
	Cat cat;
	
	MenuScreen mainMenu;
	
	int jumpvelocity;
	
	boolean jump, dash;
	boolean upgrade = false;
	
	int jumpTime, halfJumpTime, dashTime, dashVelocity;
	
	private Paint tekstas = new Paint();
	
	float inputX, inputY;
	
	SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

	int jumpSoundId;
	int dashSoundId;

	MediaPlayer mPlayer;

	public GameThread(SurfaceHolder surfaceholder, Context context, Handler handler)
	{
		this.mHolder = surfaceholder;
		
		frameCount = fps = 0;
		animationCount = 0;
		
		rand = new Random();
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		this.width = size.x;
		this.height = size.y;
		
		wm = null;
		display = null;
		size = null;
		
		gameScale[0] = width / Constants.Size.imageWidth;
		gameScale[1] = height / Constants.Size.imageHeight;
		
		//btmMenu = BitmapFactory.decodeResource(context.getResources(), R.drawable.menu);
		//sprMenu = new Sprite(scaleBitmap(btmMenu, gameScale, Constants.Size.imageWidth, Constants.Size.imageHeight));
		mainMenu = new MenuScreen(new Bitmap[]
				{
				scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.mainmenu1), gameScale, Constants.Size.imageWidth, Constants.Size.imageHeight),
			    scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.mainmenu2), gameScale, Constants.Size.imageWidth, Constants.Size.imageHeight)
				});
		
		btmAbout = BitmapFactory.decodeResource(context.getResources(), R.drawable.about);
		sprAbout = new Sprite(scaleBitmap(btmAbout, gameScale, Constants.Size.imageWidth, Constants.Size.imageHeight));
			
		btmScore = BitmapFactory.decodeResource(context.getResources(), R.drawable.score);
		sprScore = new Sprite(scaleBitmap(btmScore, gameScale, Constants.Size.imageWidth, Constants.Size.imageHeight));
		
		//burger = new Sprite(scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.burger), gameScale, Constants.Size.burgerWidth, Constants.Size.burgerHeight));
		
		//enemy = new Sprite(scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.sprinkler), gameScale, Constants.Size.sprinklerWidth, Constants.Size.sprinklerHeight));
		
		btmBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bachgroundfar);
		farBackground = new Background(scaleBitmap(btmBackground, gameScale, Constants.Size.farbackgroundWidth, Constants.Size.farbackgroundheight), this.width);
		farBackground.speed = (int) (Constants.Speed.farBackgroundMovementSpeed*gameScale[0]);
		
		btmNearBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.backgroundnear);
		nearBackground = new Background(scaleBitmap(btmNearBackground, gameScale, Constants.Size.nearbackgroundWidth, Constants.Size.nearbackgroundheight), this.width);
		nearBackground.y = height - (int)(Constants.Size.nearbackgroundheight*gameScale[1]);
		nearBackground.speed = (int) (Constants.Speed.nearBackgroundMovementSpeed*gameScale[0]);
		
		cat = new Cat(new Bitmap[]
				{
					scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.fakin), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
					scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.fakindu), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
					scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.dash), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
					scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.jumps), gameScale, Constants.Size.catWidth, Constants.Size.catHeight)
				});
		
		cat.x = (int)(Constants.Positions.catPossition[0]*gameScale[0]);
		cat.y = (int)(Constants.Positions.catPossition[1]*gameScale[1]);
		
//		burger.x = (int)(Constants.Positions.burgerStartingPosition[0]*gameScale[0]);
//		burger.y = (int)(Constants.Positions.burgerStartingPosition[1]*gameScale[1]);
//		burger.vx = Constants.Speed.nearBackgroundMovementSpeed;
//		
//		enemy.x = (int)(Constants.Positions.sprinklerStartingPosition[0]*gameScale[0]);
//		enemy.y = (int)(Constants.Positions.sprinklerStartingPosition[1]*gameScale[1]);
//		enemy.vx = Constants.Speed.nearBackgroundMovementSpeed;
		
		
		obstacle = new Enemy[3];
		enemyBitmap = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.sprinkler), gameScale, Constants.Size.sprinklerWidth, Constants.Size.sprinklerHeight);
		enemyBitmap1 = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.doge), gameScale, Constants.Size.dogeWidth, Constants.Size.dogeHeight);
		friendlyBitmap = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.burger), gameScale, Constants.Size.burgerWidth, Constants.Size.burgerHeight);
		
		initializeObstacles();
		
		Typeface tf = Typeface.create("Helvetica",Typeface.BOLD);
		tekstas.setTypeface(tf);
		tekstas.setColor(Color.MAGENTA);
		tekstas.setTextSize(40);
		
		//btmMenu.recycle();
		btmScore.recycle();
		btmBackground.recycle();
		btmAbout.recycle();
		btmBackground.recycle();
		btmNearBackground.recycle();
		
		jump= false;
		dash = false;
		jumpTime = Constants.Time.jumpTimeFrames;
		halfJumpTime = Constants.Time.jumpTimeFrames/2;
		dashTime = 0;
		dashVelocity = 0;
		distanceCount = 0;
		distanceFrameCount = 0;
		
		jumpvelocity = (int)((Constants.Lenths.jumpUpHeight/(Constants.Time.jumpTimeFrames/2))*gameScale[1]);
		
		jumpSoundId = soundPool.load(context, R.raw.jump, 1);
		dashSoundId = soundPool.load(context, R.raw.dash, 2);
		
		mPlayer = MediaPlayer.create(context, R.raw.music);

		if(mPlayer!= null)
	    {
	        mPlayer.setLooping(true);
	        mPlayer.setVolume(50,50);
	    }
		
	}
	
	public void doStart()
	{
		synchronized (mHolder)
		{
			mLastTime = System.currentTimeMillis() + 100;
			running = true;
			mPlayer.start();
		}
	}
	
	@Override
	public void run()
	{
		while (running)
		{
			Canvas c = null;
			
			try 
			{
				c = mHolder.lockCanvas();

				synchronized (mHolder)
				{
					long now = System.currentTimeMillis();

					this.update();
					this.draw(c);	
					this.animateSprites();	

					frameCount++;
					
					if (now > (mLastTime + 1000))
					{
						mLastTime = now;
						fps = frameCount;
						frameCount = 0;
					}
				}
			}
			finally
			{
				if (c != null) 
				{
					mHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}

	private void update() 
	{
		if (state == State.GAME)
		{	
			this.distanceUpdate();
			
			this.farBackground.update(dashVelocity);
			this.nearBackground.update(dashVelocity);		
			
			this.jumpLogics();
			this.dashLogic();
			
			obsticlesUpdate(distanceCount);
			executeColission();
		}
		
		else if (state == State.MENU)
		{
			if (inZone(inputX, inputY, Constants.ButtonPositions.btnStartX, Constants.ButtonPositions.btnStartY))
			{
				state = State.GAME;	
			}
			
			else if(inZone(inputX, inputY, Constants.ButtonPositions.btnAboutX, Constants.ButtonPositions.btnAboutY))
			{
				state = State.ABOUT;
			}
			
			else if(inZone(inputX, inputY, Constants.ButtonPositions.btnExitX, Constants.ButtonPositions.btnExitY))
			{
				System.exit(0);
			}
			
			inputX = 0;
			inputY = 0;
		}
		
		else if (state == State.ABOUT )
		{
			
		}
		
		else if(state == State.SCORE)
		{
			if (inZone(inputX, inputY, Constants.ButtonPositions.btnRepalyX, Constants.ButtonPositions.btnReplayY))
			{
				resetGame();
				state = State.GAME;				
			}
			
			else if(inZone(inputX, inputY, Constants.ButtonPositions.btnMenuX, Constants.ButtonPositions.btnMenuY))
			{
				resetGame();
				state = State.MENU;	
			}
			
			inputX = 0;
			inputY = 0;
		}
	}

	private void resetGame() {
		jump= false;
		dash = false;
		dashTime = 0;
		dashVelocity = 0;
		jumpTime = Constants.Time.jumpTimeFrames;;
		distanceCount = 0;
		distanceFrameCount = 0;
		cat.x = (int)(Constants.Positions.catPossition[0]*gameScale[0]);
		cat.y = (int)(Constants.Positions.catPossition[1]*gameScale[1]);
		initializeObstacles();
	}

	private void obsticlesUpdate(int diffilcuty) 
	{	
		if ((diffilcuty%10) == 0)
		{
			upgrade = true;
		}
		
		obstacle[0].update(dashVelocity);
		obstacle[1].update(dashVelocity);
		obstacle[2].update(dashVelocity);
		
		//TODO: add check if unbeatable 
		
		if(obstacle[0].x < -400)
		{
			obstacle[0].x = (float) (obstacle[2].x + (Constants.Lenths.obstaclesOfsset*gameScale[0]) - randInt(Constants.Lenths.obstaclesRandomOfssetMin, Constants.Lenths.obstaclesRandomOfssetMax) - diffilcuty);
		}
		
		if(obstacle[1].x < -400)
		{
			obstacle[1].x = (float) (obstacle[0].x + (Constants.Lenths.obstaclesOfsset*gameScale[0]) - randInt(Constants.Lenths.obstaclesRandomOfssetMin, Constants.Lenths.obstaclesRandomOfssetMax) - diffilcuty);
		}
		
		if(obstacle[2].x < -400)
		{
			obstacle[2].visible = true;
			obstacle[2].x = (float) (obstacle[1].x + (Constants.Lenths.obstaclesOfsset*gameScale[0]) - randInt(Constants.Lenths.obstaclesRandomOfssetMin, Constants.Lenths.obstaclesRandomOfssetMax) - diffilcuty);
			
			if (upgrade)
			{
				upgrade = false;
				obstacle[2].friendly = true;
				obstacle[2].setBitmap(friendlyBitmap);
			}
		}
		
//		obstacle[0].x = (int)(Constants.Positions.sprinklerStartingPosition[0]*gameScale[0]);
//		obstacle[1].x = (float) ((Constants.Positions.sprinklerStartingPosition[0]*gameScale[0])+(Constants.Lenths.obstaclesOfsset*gameScale[0]));
//		obstacle[2].x = (float) ((Constants.Positions.sprinklerStartingPosition[0]*gameScale[0])+((Constants.Lenths.obstaclesOfsset*gameScale[0])*2));
//		
		
		//TODO: update to dynamic logics 
		
//		burger.update(dashVelocity);
//		enemy.update(dashVelocity);
//		
//		if (burger.x < -400)
//		{
//			burger.x = (int)(Constants.Positions.burgerStartingPosition[0]*gameScale[0]);
//		}
//		
//		if (enemy.x < -400)
//		{
//			enemy.x = (int)(Constants.Positions.sprinklerStartingPosition[0]*gameScale[0]);
//		}
	}
	
	public static int randInt(int min, int max) 
	{
	    return rand.nextInt((max - min) + 1) + min;
	}
	
	private void executeColission() 
	{
		
		
//		if ( CollisionDetection.isCollisionDetected(cat.getCurrentBitmap(), (int)cat.x, (int)cat.y,
//				burger.getBitmap(), (int)burger.x, (int)burger.y))
//		{
//			if(dash)
//			{
//				burger.x =  (float)(Constants.Positions.burgerStartingPosition[0]*gameScale[0]);
//			}
//			else
//			{
//				burger.x = (float) (Constants.Positions.burgerStartingPosition[0]*gameScale[0]);
//				setScoreState();
//			}
//		}
		for(int i = 0 ; i < 3 ;i++)
		{
			if(obstacle[i].visible)
			{
				if ( CollisionDetection.isCollisionDetected(cat.getCurrentBitmap(), (int)cat.x, (int)cat.y, obstacle[i].getBitmap(), (int)obstacle[i].x, (int)obstacle[i].y))
				{
					if(obstacle[i].friendly && dash)
					{
						obstacle[i].visible = false;
						obstacle[i].friendly = false;
						obstacle[i].setBitmap(enemyBitmap);
					}
					else
					{
						setScoreState();
					}
				}
			}
		}
	}

	private void setScoreState() 
	{
		state = State.SCORE;
	}

	private void distanceUpdate() {
		distanceFrameCount++;
		
		if (distanceFrameCount == Constants.Time.distanceCounterUpdateOnFrame)
		{
			distanceCount++;
			distanceFrameCount = 0;
		}
	}

	private void draw(Canvas c) {
		
		if(state == State.GAME)
		{	
			farBackground.draw(c);
			nearBackground.draw(c);
			
			cat.draw(c);
			
			obstacle[0].draw(c);
			obstacle[1].draw(c);
			obstacle[2].draw(c);
			
//			burger.draw(c);
//			enemy.draw(c);
				
			c.drawText("Distance: " + distanceCount + "m", width - 320, 40, tekstas);
			
			c.drawText("jump", 50, height - 50, tekstas);
			c.drawText("dash", width - 100, height - 50, tekstas);
			
			c.drawText("fps= "+ fps, 10, 20, tekstas);
		}
		
		else if (state == State.MENU)
		{
			//sprMenu.draw(c);
			mainMenu.draw(c);
		}
		
		else if (state == State.ABOUT)
		{
			sprAbout.draw(c);
		}
		
		else if(state == State.SCORE)
		{
			farBackground.draw(c);
			nearBackground.draw(c);
			
			cat.draw(c);
			
			obstacle[0].draw(c);
			obstacle[1].draw(c);
			obstacle[2].draw(c);
			
			sprScore.draw(c);
			
			c.drawText("Distance: " + distanceCount + "m", width/2, height/2, tekstas);
		}
	}
	
	private void animateSprites()
	{
		if (state == State.GAME)
		{
			animationCount++;
			
			if (animationCount == Constants.Time.animationChangeOnFrame)
			{
				cat.animate(jump, dash);
				animationCount = 0;
			}
		}
		
		if (state == State.MENU)
			{
			menuFrameCount++;
			
			if (menuFrameCount == Constants.Time.menuFrameChangeOnFrame)
			{
				mainMenu.animate();
				menuFrameCount = 0;
			}
		}
	}
	
	private Bitmap scaleBitmap(Bitmap bitmap, double[] scale, double originalWidth, double originalHeight)
	{
		return Bitmap.createScaledBitmap(bitmap, (int)(originalWidth*scale[0]), (int)(originalHeight*scale[1]), true);
	}
	
	private void dashLogic() {
		if (dash)
		{
			if (dashTime == 0)
			{
				soundPool.play(dashSoundId, 100, 100, 2, 0, 1);
			}
			
			dashVelocity = (((dashTime*dashTime)-Constants.Time.dashTimeFrames*dashTime)/Constants.Speed.dashSpeedDivider);
			
			if (dashTime == Constants.Time.dashTimeFrames)
			{
				dash = false;
				dashTime = 0;
			}
			else
			{
				dashTime++;
			}
		}
	}

	private void jumpLogics() {
		if (jump)
		{
			if (jumpTime == Constants.Time.jumpTimeFrames)
			{
				soundPool.play(jumpSoundId, 100, 100, 1, 0, 1);
			}
				
			if(jumpTime <= halfJumpTime)
			{
				cat.y += jumpvelocity;
			}
			else
			{
				cat.y -= jumpvelocity;
			}
			--jumpTime;
			
			if (jumpTime == 0)
			{
				jump = false;
				jumpTime = Constants.Time.jumpTimeFrames;
			}
		}
	}
	
	private void initializeObstacles() 
	{
		if(obstacle[0] == null)
		{
			obstacle[0] = new Enemy();
		}
		if(obstacle[1] == null)
		{
			obstacle[1] = new Enemy();
		}
		if(obstacle[2] == null)
		{
			obstacle[2] = new Enemy();
		}
		obstacle[0].setBitmap(enemyBitmap);
		obstacle[0].x = (int)(Constants.Positions.sprinklerStartingPosition[0]*gameScale[0]);
		obstacle[0].y = (int)(Constants.Positions.sprinklerStartingPosition[1]*gameScale[1]);
		obstacle[0].vx = (int) (Constants.Speed.nearBackgroundMovementSpeed*gameScale[0]);
		
		obstacle[1].setBitmap(enemyBitmap1);
		obstacle[1].x = (float) ((Constants.Positions.sprinklerStartingPosition[0]*gameScale[0])+(Constants.Lenths.obstaclesOfsset*gameScale[0]));
		obstacle[1].y = (int)(Constants.Positions.sprinklerStartingPosition[1]*gameScale[1]);
		obstacle[1].vx = (int) (Constants.Speed.nearBackgroundMovementSpeed*gameScale[0]);
		
		obstacle[2].setBitmap(enemyBitmap);
		obstacle[2].x = (float) ((Constants.Positions.sprinklerStartingPosition[0]*gameScale[0])+((Constants.Lenths.obstaclesOfsset*gameScale[0])*2));
		obstacle[2].y = (int)(Constants.Positions.sprinklerStartingPosition[1]*gameScale[1]);
		obstacle[2].vx = (int) (Constants.Speed.nearBackgroundMovementSpeed*gameScale[0]);
	}
	
//	private void drawObstacles(Canvas c) 
//	{
//		obstacle[0].draw(c);
//		obstacle[1].draw(c);
//		obstacle[2].draw(c);
//	}
	
	public void onTouch(MotionEvent event)
	{
		if (state != State.GAME )
		{
			inputX = event.getX();
			inputY = event.getY();
		}
		else
		{
			final int pointerCount = event.getPointerCount();
			for (int p = 0; p < pointerCount; p++) {
				if (inZone(event.getX(p), event.getY(p), Constants.ButtonPositions.btnJumpX, Constants.ButtonPositions.btnJumpY))
				{
					//jump
					jump = true;
				}
				
				else if(inZone(event.getX(p), event.getY(p), Constants.ButtonPositions.btnDashX, Constants.ButtonPositions.btnDashY))
				{
					//dash
					dash = true;
				}
			}		
		}
	}
	
	public void onKeyDownBack()
	{
		if(state != State.MENU)
		{
			state = State.MENU;
			inputX = 0;
			inputY = 0;
		}
		else
		{
          System.exit(0);
		}
	}
	
	private boolean inZone(float currentX, float currentY, int[] buttonX, int[] buttonY)
	{
	
		if(currentX >  buttonX[0]*gameScale[0]   && currentX < buttonX[1]*gameScale[0] && currentY > buttonY[0]*gameScale[1] && currentY < buttonY[1]*gameScale[1])
		{
			return true;
		}
		
		return false;	
	}
}