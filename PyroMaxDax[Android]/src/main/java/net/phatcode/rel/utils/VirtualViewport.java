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

public class VirtualViewport
{

	private float cropX;
	private float cropY;
	private float width;
	private float height;

	
	public VirtualViewport()
	{
		
	}

	public VirtualViewport( int virtualWidth, int virtualHeight,
							int screenWidth, int screenHeight )
	{
		
		float scale = 1f;
		float aspectRatio = (float)screenWidth / (float)screenHeight;
		float virtualAspectRatio = (float)virtualWidth / (float)virtualHeight;
	    
		if( aspectRatio > virtualAspectRatio )
	    {
	        scale = (float)screenHeight / (float)virtualHeight;
	        cropX = (screenWidth - virtualWidth * scale) / 2f;
	    }
	    else if(aspectRatio < virtualAspectRatio)
	    {
	        scale = (float)screenWidth / (float)virtualWidth;
	        cropY = (screenHeight - virtualHeight * scale) / 2f;
	    }
	    else
	    {
	        scale = (float)screenWidth / (float)virtualWidth;
	    }

	    width = (float)virtualWidth * scale;
	    height = (float)virtualHeight * scale;

	       
	}

	

	public void initialize( int virtualWidth, int virtualHeight,
							int screenWidth, int screenHeight )
	{
		float scale = 1f;
		float aspectRatio = (float)screenWidth / (float)screenHeight;
		float virtualAspectRatio = (float)virtualWidth / (float)virtualHeight;
	    
		if( aspectRatio > virtualAspectRatio )
	    {
	        scale = (float)screenHeight / (float)virtualHeight;
	        cropX = (screenWidth - virtualWidth * scale) / 2f;
	    }
	    else if(aspectRatio < virtualAspectRatio)
	    {
	        scale = (float)screenWidth / (float)virtualWidth;
	        cropY = (screenHeight - virtualHeight * scale) / 2f;
	    }
	    else
	    {
	        scale = (float)screenWidth / (float)virtualWidth;
	    }

	    width = (float)virtualWidth * scale;
	    height = (float)virtualHeight * scale;

	       
	}

	public int getCropX()
	{
		return (int)cropX;
	}

	public int getCropY()
	{
		return (int)cropY;
	}

	public int getWidth()
	{
		return (int)width;
	}

	public int getHeight()
	{
		return (int)height;
	}
	
	
}

