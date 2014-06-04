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
  
  private int state;
  private Bitmap texture[];
  private Bitmap skin1[];
  private Bitmap skin2[];
  private int width, height;
  
  public Cat(Bitmap[] texture ,Bitmap[] skin1, Bitmap[] skin2)
  {
	  this.texture = texture;
	  this.skin1 = skin1;
	  this.skin2 = skin2;
	  frame = 0;
	  maxFrame = texture.length-2;
	  width = texture[0].getWidth();
	  height = texture[0].getHeight();
	  state = 0;
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
  
  public void setCatState(int state)
  {
	    this.state = state;
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
	  if(state == 0)
	  {
		  c.drawBitmap(texture[frame], x, y, null);  
	  }
	  if(state == 1)
	  {
		  c.drawBitmap(skin1[frame], x, y, null);  
	  }  
	  if(state == 2)
	  {
		  c.drawBitmap(skin2[frame], x, y, null);  
	  }
  }
}