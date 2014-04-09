package com.enumm.catgame;

public abstract class Constants
{
	public static class Speed
	{
		public static final int farBackgroundMovementSpeed = -1;
		public static final int nearBackgroundMovementSpeed = -5;
		public static final int dashSpeedDivider = 30;
	}
	
	public static class Lenths
	{
		public static final int jumpUpHeight = 200;
	}
	
	public static class Size
	{
		public static final double imageWidth = 1280;
		public static final double imageHeight = 720;
		
		public static final double catWidth = 220;
		public static final double catHeight = 110;
		
		public static final double farbackgroundWidth = 2560;
		public static final double farbackgroundheight = 720;
		
		public static final double nearbackgroundWidth = 2560;
		public static final double nearbackgroundheight = 200;
	}
	
	public static class Positions
	{
		public static final int[] catPossition = {300, 500};
	}
	public static class ButtonPositions
	{
		public static final int[] btnStartX = {453, 812};
		public static final int[] btnStartY = {123, 214};
		
		public static final int[] btnAboutX = {452, 811};
		public static final int[] btnAboutY = {260, 353};
		
		public static final int[] btnExitX = {452, 811};
		public static final int[] btnExitY = {395, 484};
		
		public static final int[] btnJumpX = {0, 300};
		public static final int[] btnJumpY = {520, 720};
		
		public static final int[] btnDashX = {1080, 1280};
		public static final int[] btnDashY = {520, 720};
	}
	
	public static class Time
	{
		public static final int animationChangeOnFrame = 10;
		public static final int jumpTimeFrames = 60;
		public static final int dashTimeFrames = 30;
	}
}
