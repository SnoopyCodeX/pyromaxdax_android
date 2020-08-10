/**
 *
 * @author Richard Eric M. Lope (Relminator)
 * @version 1.00 2014/29/03
 * 
 * Http://rel.phatcode.net
 * 
 * License: GNU LGPLv2 or later
 * 
 */


package net.phatcode.rel.utils;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

public class ImageAtlas 
{

	private int[] textureIDs = new int[1];

	private int width = 0;
	private int height = 0;
	
	private SpriteGL[] sprites;
	
	public ImageAtlas()
	{
	}
	
	public void loadTexture( GL10 gl, 
							 Context context, 
						     int resID, 
						     ImageTextureData textureData, 
						     int tileWidth,
						     int tileHeight,
							 int filtermode )
	{
		
	   Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), resID );

	   width = bitmap.getWidth();
	   height = bitmap.getHeight();
	   
       int numImages = (width/tileWidth) * (height/tileHeight);
       
       sprites = new SpriteGL[numImages];
      
       int i = 0;
  	
       for( int y = 0; y < (height/tileHeight); y++)
      	{
      		for( int x = 0; x < (width/tileWidth); x++) 
      		{
      			
      			int ix = x * tileWidth;
                int iy = y * tileHeight;
                int iw = tileWidth;
                int ih = tileHeight;
                  
                sprites[i] = new SpriteGL();
                sprites[i].width = tileWidth;
                sprites[i].height = tileHeight;
				sprites[i].texture_width = width;
				sprites[i].texture_height = height;
				sprites[i].u_off = ix;
				sprites[i].v_off = iy;
                sprites[i].u1 = ix / (float) width;
                sprites[i].v1 = iy / (float) height;
                sprites[i].u2 = (ix + iw) / (float) width;
                sprites[i].v2 = (iy + ih) / (float) height;
                  
                i++;
                  
      		}
      	}

       gl.glGenTextures( 1, textureIDs, 0 );
		
       gl.glBindTexture( GL10.GL_TEXTURE_2D, textureIDs[0] );
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, filtermode);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, filtermode);
       
       GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );
	   bitmap.recycle();
	}


	public void loadTexture( GL10 gl, 
							 AndroidFileIO fileIO, 
							 String fileName,
							 ImageTextureData textureData, 
						     int tileWidth,
						     int tileHeight,
							 int filtermode )
	{
		
		Bitmap bitmap = null;
		InputStream in = null;
		try 
		{
		    in = fileIO.readAsset(fileName);
		    bitmap = BitmapFactory.decodeStream(in);
		} 
		catch( IOException e ) 
		{
		    throw new RuntimeException("Couldn't load texture '" + fileName +"'", e);
	    } 
	    finally 
	    {
	        if(in != null)
	            try { in.close(); } catch (IOException e) { }
	    }
	   
		width = bitmap.getWidth();
		height = bitmap.getHeight();
	   	   
       int numImages = (width/tileWidth) * (height/tileHeight);
       
       sprites = new SpriteGL[numImages];
      
       int i = 0;
  	
       for( int y = 0; y < (height/tileHeight); y++)
      	{
      		for( int x = 0; x < (width/tileWidth); x++) 
      		{
      			
      			int ix = x * tileWidth;
                int iy = y * tileHeight;
                int iw = tileWidth;
                int ih = tileHeight;
                  
                sprites[i] = new SpriteGL();
                sprites[i].width = tileWidth;
                sprites[i].height = tileHeight;
				sprites[i].texture_width = width;
				sprites[i].texture_height = height;
				sprites[i].u_off = ix;
				sprites[i].v_off = iy;
                sprites[i].u1 = ix / (float)width;
                sprites[i].v1 = iy / (float)height;
                sprites[i].u2 = (ix + iw) / (float)width;
                sprites[i].v2 = (iy + ih) / (float)height;
                  
                i++;
                  
      		}
      	}

       gl.glGenTextures( 1, textureIDs, 0 );
		
       gl.glBindTexture( GL10.GL_TEXTURE_2D, textureIDs[0] );
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, filtermode);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, filtermode);
       
       GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );
	   bitmap.recycle();
       
	}

	public void loadTexture( GL10 gl, Context context, int resID, ImageTextureData textureData, int filtermode )
	{
		
	   Bitmap bitmap = BitmapFactory.decodeResource( context.getResources(), resID );

	   width = bitmap.getWidth();
	   height = bitmap.getHeight();
	   
       int numImages = textureData.getNumImages();
       int texcoords[] = textureData.getArray();
   
       sprites = new SpriteGL[numImages];
       
       for( int i = 0; i < numImages; i++ )
       {
       	
       	   int j = i * 4;
           int x = texcoords[j];
           int y = texcoords[j+1];
           int w = texcoords[j+2];
           int h = texcoords[j+3];
           
           sprites[i] = new SpriteGL();
           sprites[i].width = w;
           sprites[i].height = h;
		   sprites[i].texture_width = width;
		   sprites[i].texture_height = height;
		   sprites[i].u_off = x;
		   sprites[i].v_off = y;
           sprites[i].u1 = x / (float)width;
           sprites[i].v1 = y / (float)height;
           sprites[i].u2 = (x + w) / (float)width;
           sprites[i].v2 = (y + h) / (float)height;
	
       }
       
       gl.glGenTextures( 1, textureIDs, 0 );
		
       gl.glBindTexture( GL10.GL_TEXTURE_2D, textureIDs[0] );
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, filtermode);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, filtermode);
       
       GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );
	   bitmap.recycle();
       
	}

	
	public void loadTexture( GL10 gl, AndroidFileIO fileIO, String fileName, ImageTextureData textureData, int filtermode )
	{
		
		Bitmap bitmap = null;
		InputStream in = null;
		try 
		{
		    in = fileIO.readAsset(fileName);
		    bitmap = BitmapFactory.decodeStream(in);
		} 
		catch( IOException e ) 
		{
		    throw new RuntimeException("Couldn't load texture '" + fileName +"'", e);
	    } 
	    finally 
	    {
	        if(in != null)
	            try { in.close(); } catch (IOException e) { }
	    }
	   
		width = bitmap.getWidth();
		height = bitmap.getHeight();
	   
       int numImages = textureData.getNumImages();
       int texcoords[] = textureData.getArray();
   
       sprites = new SpriteGL[numImages];
       
       for( int i = 0; i < numImages; i++ )
       {
       	
       	   int j = i * 4;
           int x = texcoords[j];
           int y = texcoords[j+1];
           int w = texcoords[j+2];
           int h = texcoords[j+3];
           
           sprites[i] = new SpriteGL();
           sprites[i].width = w;
           sprites[i].height = h;
		   sprites[i].texture_width = width;
		   sprites[i].texture_height = height;
		   sprites[i].u_off = x;
		   sprites[i].v_off = y;
           sprites[i].u1 = x / (float)width;
           sprites[i].v1 = y / (float)height;
           sprites[i].u2 = (x + w) / (float)width;
           sprites[i].v2 = (y + h) / (float)height;
	
       }
       
       gl.glGenTextures( 1, textureIDs, 0 );
		
       gl.glBindTexture( GL10.GL_TEXTURE_2D, textureIDs[0] );
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, filtermode);
       gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, filtermode);
       
       GLUtils.texImage2D( GL10.GL_TEXTURE_2D, 0, bitmap, 0 );
	   bitmap.recycle();
       
	}

	public void shutDown( GL10 gl )
	{
		
		gl.glBindTexture( GL10.GL_TEXTURE_2D, 0 ); 
		int[] textureID = {textureIDs[0]};
		gl.glDeleteTextures( 1, textureID, 0 );
	
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
		
	public int getNumImages()
	{
		return sprites.length;
	}
	
	public SpriteGL getSprite( int index )
	{
		return sprites[index];
	}

	public int getTextureID()
	{
		return textureIDs[0];
	}

	public void setWidth( int width )
	{
		this.width = width;
	}

	public void setHeight(int height)
	{
		this.height = height;
	}
	
	
}
