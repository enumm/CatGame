package com.enumm.catgame;

import java.util.Random;

import com.enumm.catgame.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
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

public class GameThread extends Thread 
{
	SharedPreferences sp;
	SharedPreferences.Editor editor;
	private SurfaceHolder mHolder = null; // Holder reference
	boolean running = false; // exits the thread when set false
	boolean recordRun = false;
	boolean musicFading = false;
	private long mLastTime = 0; // used for maintaining FPS
	@SuppressWarnings("unused")
	private int fps, frameCount, animationCount, menuFrameCount, distanceCount, distanceFrameCount, best, catState; // store width and height of canvas,
	
	float width, height;
	
	public enum State {MENU, ABOUT, SCORE, GAME};
	
	State state = State.MENU;
	
	static Random rand;
	
	Bitmap btmScore;
	Bitmap btmAbout;
	//Bitmap btmMenu;
	Bitmap[] btmCat;
	Bitmap btmBackground;
	Bitmap btmNearBackground;
	
	float[] gameScale = new float[2];
	
	//Sprite burger;
	//Sprite enemy;
	
	Enemy[] obstacle;
	Bitmap enemyBitmap;
	Bitmap enemyBitmap1;
	Bitmap enemyBitmap2;
	Bitmap enemyBitmap3;
	Bitmap friendlyBitmap;
	
	Bitmap jumpLabel;
	Bitmap dashLabel;
	
	Sprite sprScore;
	Sprite sprAbout;
	Background farBackground;
	Background nearBackground;
	Cat cat;
	
	MenuScreen mainMenu;
	
	int jumpvelocity;
	float switchMusic;
	
	boolean jump, dash;
	boolean drawjump, drawdash;
	boolean upgrade = false;
	
	int jumpTime, halfJumpTime, dashTime;
	float dashVelocity;
	
	private Paint tekstas = new Paint();
	
	float inputX, inputY;
	
	SoundPool soundPool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0);

	int jumpSoundId;
	int dashSoundId;

	MediaPlayer menuMusic;
	MediaPlayer gameMusic;
	
	@SuppressLint("CommitPrefEdits") 
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
		
		sp = context.getSharedPreferences("SuperCatGame", Activity.MODE_PRIVATE);
		editor = sp.edit();
		
		wm = null;
		display = null;
		size = null;
		
		gameScale[0] = width / Constants.Size.imageWidth;
		gameScale[1] = height / Constants.Size.imageHeight;

		mainMenu = new MenuScreen(new Bitmap[]
				{
				scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.mainmenu1), gameScale, Constants.Size.imageWidth, Constants.Size.imageHeight),
			    scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.mainmenu2), gameScale, Constants.Size.imageWidth, Constants.Size.imageHeight)
				});
		
		btmAbout = BitmapFactory.decodeResource(context.getResources(), R.drawable.about);
		sprAbout = new Sprite(scaleBitmap(btmAbout, gameScale, Constants.Size.imageWidth, Constants.Size.imageHeight));
			
		btmScore = BitmapFactory.decodeResource(context.getResources(), R.drawable.score);
		sprScore = new Sprite(scaleBitmap(btmScore, gameScale, Constants.Size.imageWidth, Constants.Size.imageHeight));

		btmBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.bachgroundfar);
		farBackground = new Background(scaleBitmap(btmBackground, gameScale, Constants.Size.farbackgroundWidth, Constants.Size.farbackgroundheight), this.width);
		farBackground.speed = (Constants.Speed.farBackgroundMovementSpeed*gameScale[0]);
		
		btmNearBackground = BitmapFactory.decodeResource(context.getResources(), R.drawable.backgroundnear);
		nearBackground = new Background(scaleBitmap(btmNearBackground, gameScale, Constants.Size.nearbackgroundWidth, Constants.Size.nearbackgroundheight), this.width);
		nearBackground.y = height - Constants.Size.nearbackgroundheight*gameScale[1];
		nearBackground.speed = (Constants.Speed.nearBackgroundMovementSpeed*gameScale[0]);
		
		cat = new Cat(new Bitmap[]
					  {
					  	scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.fakin), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
					  	scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.fakindu), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
					  	scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.dash), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
					  	scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.jumps), gameScale, Constants.Size.catWidth, Constants.Size.catHeight)
					  },
					  new Bitmap[]
					  {
					  	scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.catprincess1), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
						scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.catprincessss2), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
						scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.catprincessdash), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
					  	scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.catprincessjump), gameScale, Constants.Size.catWidth, Constants.Size.catHeight)
					  },
					  new Bitmap[]
					  {
					    scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.catportal1), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
					    scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.catpotal2), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
					    scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.catportaldash), gameScale, Constants.Size.catWidth, Constants.Size.catHeight),
					  	scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.catportaljumps), gameScale, Constants.Size.catWidth, Constants.Size.catHeight)					  
					  }
					);
		
		cat.x = Constants.Positions.catPossition[0]*gameScale[0];
		cat.y = Constants.Positions.catPossition[1]*gameScale[1];
		
		catState = 0;
		
		obstacle = new Enemy[3];
		enemyBitmap = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.sprinkler), gameScale, Constants.Size.sprinklerWidth, Constants.Size.sprinklerHeight);
		enemyBitmap1 = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.doge), gameScale, Constants.Size.dogeWidth, Constants.Size.dogeHeight);
		enemyBitmap2 = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.plant), gameScale, Constants.Size.plantWidth, Constants.Size.plantHeight);
		enemyBitmap3 = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.plant1), gameScale, Constants.Size.plant1Width, Constants.Size.plant1Height);
		friendlyBitmap = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.burger), gameScale, Constants.Size.burgerWidth, Constants.Size.burgerHeight);
		
		jumpLabel = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.jumplabel), gameScale, Constants.Size.jumpLabelWidth, Constants.Size.jumpLabelHeight);
		dashLabel = scaleBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.dashlabel), gameScale, Constants.Size.dashLabelWidth, Constants.Size.dashLabelHeight);
		
		drawjump = true;
		drawdash = true;
		
		initializeObstacles();
		
		Typeface tf = Typeface.createFromAsset(context.getAssets(), "fonts/super_effective.ttf");
		
		tekstas.setTypeface(tf);
		tekstas.setColor(Color.rgb(244, 196, 67));
		tekstas.setTextSize((Constants.Size.textSize*gameScale[0]));
		
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
		switchMusic = Constants.Time.songFadeTimeFrames;
		best = GetSavedScore();
		
		jumpvelocity = (int)((Constants.Lenths.jumpUpHeight/(Constants.Time.jumpTimeFrames/2))*gameScale[1]);
		
		jumpSoundId = soundPool.load(context, R.raw.jump, 1);
		dashSoundId = soundPool.load(context, R.raw.dash, 2);
		
		menuMusic = MediaPlayer.create(context, R.raw.music);
		gameMusic = MediaPlayer.create(context, R.raw.music1);

		if(menuMusic!= null && gameMusic != null)
	    {
	        menuMusic.setLooping(true);
	        menuMusic.setVolume(50,50);
	        
	        gameMusic.setLooping(true);
	        gameMusic.setVolume(50,50);
	    }
	}
	
	public void doStart()
	{
		synchronized (mHolder)
		{
			mLastTime = System.currentTimeMillis() + 100;
			running = true;
			
			menuMusic.start();
			gameMusic.start();
			gameMusic.pause();
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
					this.updateMusic();

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

	private void updateMusic()
	{
		if(musicFading)
		{
			if(switchMusic != 0)
			{
				switchMusic--;
				
				if(state == State.GAME)
				{
					if(!gameMusic.isPlaying())
					{
						gameMusic.seekTo(0);
						gameMusic.start();
						gameMusic.setVolume(0, 0);
					}
					
					menuMusic.setVolume(switchMusic, switchMusic);
					gameMusic.setVolume(Constants.Time.songFadeTimeFrames - switchMusic, Constants.Time.songFadeTimeFrames - switchMusic);
					
					if(switchMusic == 0)
					{
						musicFading = false;
						menuMusic.pause();
						switchMusic = Constants.Time.songFadeTimeFrames;
					}
				}
				if(state == State.MENU)
				{
					if(!menuMusic.isPlaying())
					{
						menuMusic.start();
						menuMusic.setVolume(0, 0);
					}
					
					gameMusic.setVolume(switchMusic, switchMusic);
					menuMusic.setVolume(Constants.Time.songFadeTimeFrames - switchMusic, Constants.Time.songFadeTimeFrames - switchMusic);
					
					if(switchMusic == 0)
					{
						musicFading = false;
						gameMusic.pause();
						switchMusic = Constants.Time.songFadeTimeFrames;
					}
				}
			}
		}		
	}

	private void update() 
	{
		if (state == State.GAME)
		{	
			this.jumpLogics();
			this.dashLogic();
			this.distanceUpdate();
			
			this.farBackground.update(dashVelocity);
			this.nearBackground.update(dashVelocity);		
			
			obsticlesUpdate(distanceCount);
			executeColission();
		}
		
		else if (state == State.MENU)
		{
			if (inZone(inputX, inputY, Constants.ButtonPositions.btnStartX, Constants.ButtonPositions.btnStartY))
			{
				musicFading = true;
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
				musicFading = true;
			}
			
			inputX = 0;
			inputY = 0;
		}
	}

	private void resetGame() 
	{
		recordRun = false;
		jump= false;
		dash = false;
		dashTime = 0;
		dashVelocity = 0;
		jumpTime = Constants.Time.jumpTimeFrames;;
		distanceCount = 0;
		
		while(!downgradeCat())
		{
			
		}
		
		distanceFrameCount = 0;
		cat.x = (Constants.Positions.catPossition[0]*gameScale[0]);
		cat.y = (Constants.Positions.catPossition[1]*gameScale[1]);
		initializeObstacles();
	}

	private void obsticlesUpdate(int diffilcuty) 
	{	
		if ((diffilcuty%50) == 0 && diffilcuty != 0)
		{
			upgrade = true;
		}
		
		obstacle[0].update(dashVelocity);
		obstacle[1].update(dashVelocity);
		obstacle[2].update(dashVelocity);
		
		//TODO: add check if unbeatable, rewrite....
		
		if(obstacle[0].x < -400)
		{
			obstacle[0].deleteLabel();
			setRandomEnemy(obstacle[0]);
			obstacle[0].visible = true;
			obstacle[0].x = (obstacle[2].x + (Constants.Lenths.obstaclesOfsset*gameScale[0]) - (randInt(Constants.Lenths.obstaclesRandomOfssetMin, Constants.Lenths.obstaclesRandomOfssetMax) + (diffilcuty/Constants.Speed.dificultyModifier))*gameScale[0]);
		}
		
		if(obstacle[1].x < -400)
		{
			setRandomEnemy(obstacle[1]);
			obstacle[1].visible = true;
			obstacle[1].x = (obstacle[0].x + (Constants.Lenths.obstaclesOfsset*gameScale[0]) - (randInt(Constants.Lenths.obstaclesRandomOfssetMin, Constants.Lenths.obstaclesRandomOfssetMax) + (diffilcuty/Constants.Speed.dificultyModifier))*gameScale[0]);
		}
		
		if(obstacle[2].x < -400)
		{
			obstacle[2].deleteLabel();
			
			setRandomEnemy(obstacle[2]);
			obstacle[2].visible = true;
			obstacle[2].x = (obstacle[1].x + (Constants.Lenths.obstaclesOfsset*gameScale[0]) - (randInt(Constants.Lenths.obstaclesRandomOfssetMin, Constants.Lenths.obstaclesRandomOfssetMax) + (diffilcuty/Constants.Speed.dificultyModifier))*gameScale[0]);
			
			if(obstacle[2].friendly)
			{
				obstacle[2].friendly = false;
				upgrade = false;
			}
			
			if (upgrade)
			{
				upgrade = false;
				obstacle[2].friendly = true;
				obstacle[2].setBitmap(friendlyBitmap);
				obstacle[2].y = (Constants.Positions.enemyY*gameScale[1]);
				
				if(drawdash)
				{
					obstacle[2].setLabel(dashLabel);
					drawdash = false;
				}
			}
		}
	}
	
	public static int randInt(int min, int max) 
	{
	    return rand.nextInt((max - min) + 1) + min;
	}
	
	private void executeColission() 
	{
		for(int i = 0 ; i < 3 ;i++)
		{
			if(obstacle[i].visible)
			{
				if ( CollisionDetection.isCollisionDetected(cat.getCurrentBitmap(), (int)cat.x, (int)cat.y, obstacle[i].getBitmap(), (int)obstacle[i].x, (int)obstacle[i].y))
				{
					if(obstacle[i].friendly && dash)
					{
						upgradeCat();
						obstacle[i].visible = false;
						obstacle[i].friendly = false;
						obstacle[i].setBitmap(enemyBitmap);
					}
					else
					{
						if(catState == 0)
						{
							setScoreState();
						}
						else
						{
							obstacle[i].visible = false;
							downgradeCat();
						}
					}
				}
			}
		}
	}

	private void setScoreState() 
	{
		SaveScore(best);
		state = State.SCORE;
	}

	private void upgradeCat() 
	{
		if(catState < 2)
		{
			catState++;
			
			nearBackground.speed -= 2*gameScale[0];
			
			obstacle[0].vx -= 2*gameScale[0];
			obstacle[1].vx -= 2*gameScale[0];
			obstacle[2].vx -= 2*gameScale[0];
			
			cat.setCatState(catState);
		}
	}
	
	private boolean downgradeCat() 
	{
		if(catState != 0)
		{
			catState--;
			cat.setCatState(catState);
			
			nearBackground.speed += 2*gameScale[0];
			
			obstacle[0].vx += 2*gameScale[0];
			obstacle[1].vx += 2*gameScale[0];
			obstacle[2].vx += 2*gameScale[0];
			
			return false;
		}
		else
		{	
			return true;
		}
	}
		
	private void distanceUpdate() {
		distanceFrameCount++;
		
		if (distanceFrameCount == Constants.Time.distanceCounterUpdateOnFrame)
		{
			if(recordRun)
			{
				best++;
			}
			
			distanceCount++;
			distanceFrameCount = 0;
		}
		
		if (best < distanceCount)
		{
			best = distanceCount;
			recordRun = true;
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
				
			c.drawText("DISTANCE: " + distanceCount + "M", Constants.Positions.labelDistance[0]*gameScale[0], Constants.Positions.labelDistance[1]*gameScale[1], tekstas);
			c.drawText("BEST: " + best + "M", Constants.Positions.labelBest[0]*gameScale[0], Constants.Positions.labelBest[1]*gameScale[1], tekstas);
			
			c.drawText("JUMP", Constants.Positions.labelJump[0]*gameScale[0], Constants.Positions.labelJump[1]*gameScale[1], tekstas);
			c.drawText("DASH", Constants.Positions.labelDash[0]*gameScale[0], Constants.Positions.labelDash[1]*gameScale[1], tekstas);
		}
		
		else if (state == State.MENU)
		{
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
			
			c.drawText("DISTANCE: " + distanceCount + "M", Constants.Positions.labelScore[0]*gameScale[0], Constants.Positions.labelScore[1]*gameScale[1], tekstas);
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
	
	private Bitmap scaleBitmap(Bitmap bitmap, float[] scale, float originalWidth, float originalHeight)
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
			
			dashVelocity = (((dashTime*dashTime)-Constants.Time.dashTimeFrames*dashTime)/Constants.Speed.dashSpeedDivider)*gameScale[0];
			
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
		
		setRandomEnemy(obstacle[0]);
		
		if(drawjump)
		{
			obstacle[0].setLabel(jumpLabel);	
			drawjump = false;
		}
		
		obstacle[0].x = (Constants.Positions.sprinklerStartingPosition[0]*gameScale[0]) + Constants.Lenths.firstobstaclesOfsset*gameScale[0];
		obstacle[0].vx = (Constants.Speed.nearBackgroundMovementSpeed*gameScale[0]);
		
		setRandomEnemy(obstacle[1]);
		obstacle[1].x = (Constants.Positions.sprinklerStartingPosition[0]*gameScale[0]) + (Constants.Lenths.obstaclesOfsset*gameScale[0]) + Constants.Lenths.firstobstaclesOfsset*gameScale[0];
		obstacle[1].vx = (Constants.Speed.nearBackgroundMovementSpeed*gameScale[0]);
		
		setRandomEnemy(obstacle[2]);
		obstacle[2].x = (Constants.Positions.sprinklerStartingPosition[0]*gameScale[0]) +((Constants.Lenths.obstaclesOfsset*gameScale[0])*2) + Constants.Lenths.firstobstaclesOfsset*gameScale[0];
		obstacle[2].vx = (Constants.Speed.nearBackgroundMovementSpeed*gameScale[0]);
	}
	
	private void setRandomEnemy(Enemy enemy)
	{
		int enemyid = rand.nextInt((3 - 0) + 1) + 0;
		
		switch (enemyid) {
		case 0:
			enemy.setBitmap(enemyBitmap);
			enemy.y = (Constants.Positions.enemyY*gameScale[1]);
			break;
		case 1:
			enemy.setBitmap(enemyBitmap1);
			enemy.y = (Constants.Positions.enemy1Y*gameScale[1]);
			break;
		case 2:
			enemy.setBitmap(enemyBitmap2);
			enemy.y = (Constants.Positions.enemy2Y*gameScale[1]);
			break;
		case 3:
			enemy.setBitmap(enemyBitmap3);
			enemy.y = (Constants.Positions.enemy3Y*gameScale[1]);
			break;
		}
	}

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
					jump = true;
				}
				
				else if(inZone(event.getX(p), event.getY(p), Constants.ButtonPositions.btnDashX, Constants.ButtonPositions.btnDashY))
				{
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
			resetGame();
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
	
	private void SaveScore(int score)
	{	
		editor.putInt("Cat_Score", score);
		editor.commit();
	}
	
	private int GetSavedScore()
	{	
		return sp.getInt("Cat_Score", 0);
	}
}