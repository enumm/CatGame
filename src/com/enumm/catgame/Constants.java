package com.enumm.catgame;

public abstract class Constants
{
	public static class Speed
	{
		public static final int farBackgroundMovementSpeed = -2;
		public static final int nearBackgroundMovementSpeed = -15;
		public static final int dashSpeedDivider = 30;
	}
	
	public static class Lenths
	{
		public static final int jumpUpHeight = 300;
		public static final int obstaclesOfsset = 1200;
		public static final int obstaclesRandomOfssetMin = 50;
		public static final int obstaclesRandomOfssetMax = 200;
	}
	
	public static class Size
	{
		public static final double imageWidth = 1280;
		public static final double imageHeight = 720;
		
		public static final double catWidth = 310;
		public static final double catHeight = 170;
		
		public static final double burgerWidth = 178;
		public static final double burgerHeight = 165;
		
		public static final double sprinklerWidth = 82;
		public static final double sprinklerHeight = 165;
		
		public static final double farbackgroundWidth = 2560;
		public static final double farbackgroundheight = 720;
		
		public static final double nearbackgroundWidth = 2560;
		public static final double nearbackgroundheight = 200;
	}
	
	public static class Positions
	{
		public static final int[] catPossition = {140, 408};
		public static final int burgerStartingPosition[] = {(int) (1280 + Constants.Size.burgerWidth), 415};
		public static final int sprinklerStartingPosition[] = {(int) (1280 + Constants.Size.sprinklerWidth), 415};
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
	}
}
