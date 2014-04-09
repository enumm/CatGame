package com.enumm.catgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
 
public class Background {
  float y = 0;
  private float x, x2;
  
  int speed;
  int screenWidth;
  int textureWidth;
  
  private Bitmap texture;
  
  public Background(Bitmap texture, int width)
  {
	  this.texture = texture;
	  this.screenWidth = width;
	  this.textureWidth = texture.getWidth();
	  
	  x = 0;
	  x2 = textureWidth;
  }
  
  public void updateTexture(Bitmap texture)
  {
	    this.texture = texture;
  }
  
  public void draw(Canvas c)
  {
	  if(inScreenBounds(x, textureWidth, screenWidth))
	  {
		  c.drawBitmap(texture, x, y, null);
	  }
	  if(inScreenBounds(x2, textureWidth, screenWidth))
	  {
		  c.drawBitmap(texture, x2, y, null);
	  }
  }
  
  public void update(int velocity) 
  {
	  	x+= speed + velocity;
	    x2+= speed + velocity;
	    
	    if(x + textureWidth <= 0)
		{
			x = x2+ textureWidth;
		}  
	    if(x2 + textureWidth <= 0)
		{
			x2 = x+textureWidth;
		}   
  }
  
  private boolean inScreenBounds(float x, int textureWidth, int screenWidth)
  {
	  if (x + textureWidth >= 0)
	  {
		  return true;
	  }
	  
	  return false;
  }
  
}