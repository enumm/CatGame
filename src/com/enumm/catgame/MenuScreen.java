package com.enumm.catgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
 
public class MenuScreen
{
	float x;
	float y;
	int frame;
	int maxFrame;
	
	private Bitmap texture[];
  
	public MenuScreen(Bitmap[] texture)
	{
		this.texture = texture;
		frame = 0;
		maxFrame = texture.length;
	}
 
	public void animate()
	{
		frame++;
		
		if(frame >= maxFrame)
		{
			frame = 0;
	    }
	 }
	 
	public void draw(Canvas c)
	{
		c.drawBitmap(texture[frame], x , y , null);
	}
}