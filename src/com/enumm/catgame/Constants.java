package com.enumm.catgame;

public abstract class Constants
{
	public static class Speed
	{
		public static final int farBackgroundMovementSpeed = -2;
		public static final int nearBackgroundMovementSpeed = -17;
		public static final int dashSpeedDivider = 30;
		public static final float dificultyModifier = 2;
	}
	
	public static class Lenths
	{
		public static final int jumpUpHeight = 300;
		public static final int obstaclesOfsset = 1400;
		public static final int firstobstaclesOfsset = 1200;
		public static final int obstaclesRandomOfssetMin = 50;
		public static final int obstaclesRandomOfssetMax = 200;
	}
	
	public static class Size
	{
		public static final float jumpLabelWidth = 246;
		public static final float jumpLabelHeight = 61;
		
		public static final float dashLabelWidth = 246;
		public static final float dashLabelHeight = 61;
		
		public static final int textSize = 50;
		
		public static final float imageWidth = 1280;
		public static final float imageHeight = 720;
		
		public static final float catWidth = 310;
		public static final float catHeight = 170;
		
		public static final float burgerWidth = 178;
		public static final float burgerHeight = 165;
		
		public static final float sprinklerWidth = 82;
		public static final float sprinklerHeight = 165;
		
		public static final float plantWidth = 200;
		public static final float plantHeight = 200;
		
		public static final float plant1Width = 155;
		public static final float plant1Height = 153;
		
		public static final float dogeWidth = 178;
		public static final float dogeHeight = 165;
		
		public static final float farbackgroundWidth = 2560;
		public static final float farbackgroundheight = 720;
		
		public static final float nearbackgroundWidth = 2560;
		public static final float nearbackgroundheight = 200;
	}
	
	public static class Positions
	{
		public static final float[] catPossition = {140, 408};
		public static final float burgerStartingPosition[] = {(1280 + Constants.Size.burgerWidth), 415};
		public static final float sprinklerStartingPosition[] = {(1280 + Constants.Size.sprinklerWidth), 415};
		
		public static final float enemyY = 415;
		public static final float enemy1Y = 415;
		public static final float enemy2Y = 380;
		public static final float enemy3Y = 427;
		
		public static final int[] labelDistance = {950, 40};
		public static final int[] labelBest = {1040, 80};
		public static final int[] labelDash = {1135, 650};
		public static final int[] labelJump = {50, 650};
		public static final int[] labelScore = {520, 320};
	}
	public static class ButtonPositions
	{
		public static final int[] btnStartX = {542, 786};
		public static final int[] btnStartY = {200, 269};
		
		public static final int[] btnAboutX = {542, 786};
		public static final int[] btnAboutY = {339, 408};
		
		public static final int[] btnExitX = {542, 786};
		public static final int[] btnExitY = {471, 538};
		
		public static final int[] btnRepalyX = {356, 600};
		public static final int[] btnReplayY = {473, 542};
		
		public static final int[] btnMenuX = {741, 986};
		public static final int[] btnMenuY = {473, 542};
		
		public static final int[] btnJumpX = {0, 300};
		public static final int[] btnJumpY = {520, 720};
		
		public static final int[] btnDashX = {1080, 1280};
		public static final int[] btnDashY = {520, 720};
	}
	
	public static class Time
	{
		public static final int animationChangeOnFrame = 8;
		public static final int menuFrameChangeOnFrame = 50;
		public static final int distanceCounterUpdateOnFrame = 30;
		public static final int jumpTimeFrames = 46;
		public static final int dashTimeFrames = 30;
		public static final float songFadeTimeFrames = 60;
	}
}
