package com.enumm.catgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
 
public class Cat {
  float x;
  float y;
  
  int vx;
  int vy;
  int frame;
  int maxFrame;
  
  private Bitmap texture[];
  private int width, height;
  
  public Cat(Bitmap[] texture)
  {
	  this.texture = texture;
	  frame = 0;
	  maxFrame = texture.length-2;
	  width = texture[0].getWidth();
	  height = texture[0].getHeight();
  }
  
  public void animate(boolean jump, boolean dash)
  {
	  if(!jump && !dash)
	  {
		  frame++;
	  
	  	if(frame >= maxFrame)
	  	{
		  frame = 0;
	  	}
	  }
	  
	  if(jump)
	  {
		  frame = 3;
	  }
	  
	  if(dash)
	  {
		  frame = 2;
	  }
  }
  
  public void updateTexture(Bitmap texture[])
  {
	    this.texture = texture;
  }
  
  public Bitmap getCurrentBitmap()
  {
	  return texture[frame];
  }
  
  public int getWidth()
  { 
	  return this.width; 
  }
  
  public int getHeight() 
  {
	  return this.height;
  }
 
  public void draw(Canvas c)
  {  
	  c.drawBitmap(texture[frame], x, y, null);
  }
}