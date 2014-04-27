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
  private int width, height, halfWidth, halfHeight;
  
  public Cat(Bitmap[] texture)
  {
	  this.texture = texture;
	  frame = 0;
	  maxFrame = texture.length-2;
	  width = texture[0].getWidth();
	  height = texture[0].getHeight();
	  halfWidth = this.width/2;
	  halfHeight = this.height/2;
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
	  c.drawBitmap(texture[frame], x - halfWidth, y - halfHeight, null);
  }
  
//  public void draw(Canvas c, Matrix a) 
//  {
//	    c.drawBitmap(texture[frame], a, null);
//  }
}