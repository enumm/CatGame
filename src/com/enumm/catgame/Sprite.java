package com.enumm.catgame;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
 
public class Sprite {
  float x;
float y;
// x,y coordinates  x,y velocities
  int vx, vy;
  private Bitmap texture; // A bitmap texture
  
  public Sprite(Bitmap texture) {
    this.texture = texture;
  }
  
  public int getWidth()  { return texture.getWidth();  }
  public int getHeight() { return texture.getHeight(); }
 
  public void draw(Canvas c) {
    // Simply draw the object onto the canvas, at specified position
    c.drawBitmap(texture, x, y, null);
  }
  public void draw(Canvas c, Matrix a) {
	    // Simply draw the object onto the canvas, at specified position
	    c.drawBitmap(texture, a, null);
	  }
}