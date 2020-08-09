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

import javax.microedition.khronos.opengles.GL10;

import net.phatcode.rel.math.Utils;




public class SpriteFont
{
	private ImageAtlas fontAtlas;
	private SpriteBatcher fonts;
	int charWidth = 16;
	int charHeight = 16;
	int startIndex = 0;
	
	public SpriteFont()
	{
		fonts = new SpriteBatcher();
	}
	
	public SpriteFont( ImageAtlas imageAtlas )
	{
		fontAtlas = imageAtlas;
		charWidth = fontAtlas.getSprite(0).width;
		charHeight = fontAtlas.getSprite(0).height;
		startIndex = 0;
		fonts = new SpriteBatcher();

	}

	public SpriteFont( ImageAtlas imageAtlas, int startIndex )
	{
		fontAtlas = imageAtlas;
		charWidth = fontAtlas.getSprite(startIndex).width;
		charHeight = fontAtlas.getSprite(startIndex).height;
		this.startIndex = startIndex;

	}

	public void loadAtlas( ImageAtlas imageAtlas )
	{
		fontAtlas = imageAtlas;
		charWidth = fontAtlas.getSprite(0).width;
		charHeight = fontAtlas.getSprite(0).height;
		startIndex = 0;
	}

	public void loadAtlas( ImageAtlas imageAtlas, int startIndex )
	{
		fontAtlas = imageAtlas;
		charWidth = fontAtlas.getSprite(startIndex).width;
		charHeight = fontAtlas.getSprite(startIndex).height;
		this.startIndex = startIndex;
	}


	public void print( int x, int y, String text )
	{
		int len = text.length(); 
		for(int i = 0; i< len; i++ )
		{
			int index = text.charAt(i) - 32 + startIndex;
			fonts.sprite( x, y, SpriteGL.FLIP_NONE, fontAtlas.getSprite(index) );
			x += charWidth; 
		}
		
	}

	public void print( int x, int y, String text, float r, float g, float b, float a  )
	{
		int len = text.length(); 
		for(int i = 0; i< len; i++ )
		{
			int index = text.charAt(i) - 32 + startIndex;
			fonts.sprite( x, y, r, g, b, a, SpriteGL.FLIP_NONE, fontAtlas.getSprite(index) );
			x += charWidth; 
		}
		
	}

	public void print( int x, int y, StringBuilder text )
	{
		int len = text.length(); 
		for(int i = 0; i< len; i++ )
		{
			int index = text.charAt(i) - 32 + startIndex;
			fonts.sprite( x, y, SpriteGL.FLIP_NONE, fontAtlas.getSprite(index) );
			x += charWidth; 
		}
		
	}

	public void printSine(int x, int y, String text, int height, int cycles, int startAngle )
	{
		int len = text.length();
		int angleInc = (360/len)*cycles;
		
		for (int i = 0; i < len; i++)
		{
			int index = text.charAt(i) - 32 + startIndex; // offset the index by space(32)
			float ySine = (float)Math.sin(Utils.DEG_TO_RAD * startAngle) * height;  
			fonts.sprite( x, ySine + y, SpriteGL.FLIP_NONE, fontAtlas.getSprite(index) );
			x += charWidth; // next location
			startAngle += angleInc;
		}

	}

	public void printSine(int x, int y, StringBuilder text, int height, int cycles, int startAngle )
	{
		int len = text.length();
		int angleInc = (360/len)*cycles;
		
		for (int i = 0; i < len; i++)
		{
			int index = text.charAt(i) - 32 + startIndex; // offset the index by space(32)
			float ySine = (float)Math.sin(Utils.DEG_TO_RAD * startAngle) * height;  
			fonts.sprite( x, ySine + y, SpriteGL.FLIP_NONE, fontAtlas.getSprite(index) );
			x += charWidth; // next location
			startAngle += angleInc;
		}

	}

	public void printCenter( int x, int y, int screenWidth, String text )
	{
		int len = text.length(); 
		x = (screenWidth - (len * charWidth))/2;
		for(int i = 0; i< len; i++ )
		{
			int index = text.charAt(i) - 32 + startIndex;
			fonts.sprite( x, y, SpriteGL.FLIP_NONE, fontAtlas.getSprite(index) );
			x += charWidth; 
		}
		
	}

	public void printCenter( int x, int y, int screenWidth, String text, float r, float g, float b, float a )
	{
		int len = text.length(); 
		x = (screenWidth - (len * charWidth))/2;
		for(int i = 0; i< len; i++ )
		{
			int index = text.charAt(i) - 32 + startIndex;
			fonts.sprite( x, y, r, g, b, a, SpriteGL.FLIP_NONE, fontAtlas.getSprite(index) );
			x += charWidth; 
		}
		
	}

	public void printCenter( int x, int y, int screenWidth, StringBuilder text )
	{
		int len = text.length(); 
		x = (screenWidth - (len * charWidth))/2;
		for(int i = 0; i< len; i++ )
		{
			int index = text.charAt(i) - 32 + startIndex;
			fonts.sprite( x, y, SpriteGL.FLIP_NONE, fontAtlas.getSprite(index) );
			x += charWidth; 
		}
		
	}

	public void printCenterSine( int x, int y, int screenWidth, 
								 int height, int cycles, int startAngle, String text )
	{
		int len = text.length();
		int angleInc = (360/len)*cycles;
		x = (screenWidth - (len * charWidth)) / 2;
		for (int i = 0; i < len; i++)
		{
			int index = text.charAt(i) - 32 + startIndex;
			float ySine = (float)Math.sin(Utils.DEG_TO_RAD * startAngle) * height;  
			fonts.sprite( x, ySine + y, SpriteGL.FLIP_NONE, fontAtlas.getSprite(index) );
			x += charWidth;
			startAngle += angleInc;
		}
	}
	
	public void printCenterSine( int x, int y, int screenWidth, 
			 					 int height, int cycles, int startAngle, StringBuilder text )
	{
		int len = text.length();
		int angleInc = (360/len)*cycles;
		x = (screenWidth - (len * charWidth)) / 2;
		for (int i = 0; i < len; i++)
		{
			int index = text.charAt(i) - 32 + startIndex;
			float ySine = (float)Math.sin(Utils.DEG_TO_RAD * startAngle) * height;  
			fonts.sprite( x, ySine + y, SpriteGL.FLIP_NONE, fontAtlas.getSprite(index) );
			x += charWidth;
			startAngle += angleInc;
		}
	}
	
	
	public void render( GL10 gl )
	{
		fonts.render( gl, fontAtlas.getTextureID() );
	}
	
	public void shutDown( GL10 gl )
	{
		fontAtlas.shutDown( gl );
	}
	
	public void setCharWidth( int charWidth )
	{
		this.charWidth = charWidth;
	}
	
	public void setCharHeight( int charHeight )
	{
		this.charHeight = charHeight;
	}
	
	public int getCharWidth()
	{
		return charWidth;
	}

	public int getCharHeight()
	{
		return charHeight;
	}
	
	public int getNumSprites()
	{
		return fonts.getNumSprites();
	}

	public int getStartIndex()
	{
		return startIndex;
	}

	public void setStartIndex(int startIndex)
	{
		this.startIndex = startIndex;
	}

}
