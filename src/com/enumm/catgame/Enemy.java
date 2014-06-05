package com.enumm.catgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class Enemy 
{
	float x;
	float y;
	float vx, vy;
	boolean visible;
	boolean friendly;
	private Bitmap texture;
	private Bitmap label;
	private Paint paint = new Paint();
  
	public Enemy()
	{
		visible = true;
		friendly = false;
	}
	
	public Enemy(Bitmap texture)
	{
		label = null;
		visible = true;
		friendly = false;
		this.texture = texture;
	}
	
	public Bitmap getBitmap()
	{
		return texture;
	}
	
	public void setLabel(Bitmap label)
	{
		paint.setAlpha(80);
		this.label = label;
	}
	
	public void deleteLabel()
	{
		label = null;
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
			
			if(label != null)
			{
				c.drawBitmap(label, x - Constants.Size.jumpLabelWidth, y, paint);
			}
		}
	}
}