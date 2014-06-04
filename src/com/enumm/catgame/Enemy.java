package com.enumm.catgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Enemy 
{
	float x;
	float y;
	float vx, vy;
	boolean visible;
	boolean friendly;
	private Bitmap texture;
  
	public Enemy()
	{
		visible = true;
		friendly = false;
	}
	public Enemy(Bitmap texture)
	{
		visible = true;
		friendly = false;
		this.texture = texture;
	}
	
	public Bitmap getBitmap()
	{
		return texture;
	}
	
	public void setBitmap(Bitmap texture)
	{
		this.texture = texture;
	}
 
	public void update(float velocity) 
	{
		x+= vx + velocity;
	}

	public void draw(Canvas c) 
	{
		if (visible)
		{
			c.drawBitmap(texture, x, y, null);
		}
	}
}