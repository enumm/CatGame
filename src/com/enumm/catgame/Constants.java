package com.enumm.catgame;

public abstract class Constants
{
	public static class Speed
	{
		public static final int farBackgroundMovementSpeed = -1;
		public static final int nearBackgroundMovementSpeed = -8;
		public static final int dashSpeedDivider = 30;
	}
	
	public static class Lenths
	{
		public static final int jumpUpHeight = 250;
	}
	
	public static class Size
	{
		public static final double imageWidth = 1280;
		public static final double imageHeight = 720;
		
		public static final double catWidth = 400;
		public static final double catHeight = 200;
		
		public static final double burgerWidth = 400;
		public static final double burgerHeight = 200;
		
		public static final double farbackgroundWidth = 2560;
		public static final double farbackgroundheight = 720;
		
		public static final double nearbackgroundWidth = 2560;
		public static final double nearbackgroundheight = 200;
	}
	
	public static class Positions
	{
		public static final int[] catPossition = {300, 487};
		public static final int burgerStartingPosition[] = {(int) (1280 + Constants.Size.burgerWidth), 393};
	}
	public static class ButtonPositions
	{
		public static final int[] btnStartX = {542, 786};
		public static final int[] btnStartY = {200, 269};
		
		public static final int[] btnAboutX = {542, 786};
		public static final int[] btnAboutY = {339, 408};
		
		public static final int[] btnExitX = {542, 786};
		public static final int[] btnExitY = {471, 538};
		
		public static final int[] btnJumpX = {0, 300};
		public static final int[] btnJumpY = {520, 720};
		
		public static final int[] btnDashX = {1080, 1280};
		public static final int[] btnDashY = {520, 720};
	}
	
	public static class Time
	{
		public static final int animationChangeOnFrame = 8;
		public static final int menuFrameChangeOnFrame = 50;
		public static final int distanceCounterUpdateOnFrame = 60;
		public static final int jumpTimeFrames = 60;
		public static final int dashTimeFrames = 30;
	}
}
