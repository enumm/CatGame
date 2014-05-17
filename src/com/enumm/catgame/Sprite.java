package com.enumm.catgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
 
public class Sprite 
{
	float x;
	float y;
	int vx, vy;
	private Bitmap texture;
  
	public Sprite(Bitmap texture)
	{
		this.texture = texture;
	}
  
	public int getWidth()  
	{ 
		return texture.getWidth();  
	}
	public int getHeight() 
	{ 
		return texture.getHeight(); 
	}
	
	public Bitmap getBitmap()
	{
		return texture;
	}
 
	public void update(int velocity) 
	{
		x+= vx + velocity;
	}

	public void draw(Canvas c) 
	{
		c.drawBitmap(texture, x, y, null);
	}
	
	public void draw(Canvas c, Matrix a) 
	{
		c.drawBitmap(texture, a, null);
	}
}