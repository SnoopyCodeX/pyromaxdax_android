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

public class AABB
{
	public float x1;
	public float y1;
	public float x2;
	public float y2;
	
	public float width;
	public float height;
	
	public AABB()
	{
		
	}
	
	public AABB( float x, float y, float width, float height )
	{
		this.x1 = x;
		this.y1 = y;
		this.x2 = ( x + width ) - 1;
		this.y2 = ( y + height ) - 1;
		this.width = width;
		this.height = height;
	}
	
	public AABB( AABB aabb )
	{
		this.x1 = aabb.x1;
		this.y1 = aabb.y1;
		this.x2 = aabb.x2;
		this.y2 = aabb.y2;
		this.width = aabb.width;
		this.height = aabb.height;
	}

	public void init( float x, float y, float width, float height )
	{
		this.x1 = x;
		this.y1 = y;
		this.x2 = ( x + width ) - 1;
		this.y2 = ( y + height ) - 1;
		this.width = width;
		this.height = height;
	}
	
	public void resize(float factor )
	{
	
		float w = ( (x2-x1) + 1 ) / 2.0f;
		float h = ( (y2-y1) + 1 ) / 2.0f;
		
		float wd = w * (1 - factor);
		float hd = h * (1 - factor);
		
		x1 += wd;
		y1 += hd;
		x2 -= wd;
		y2 -= hd;
		
		width = ( (x2-x1) + 1 );
		height = ( (y2-y1) + 1 );
	}
	
	public void resize(float xFactor, float yFactor )
	{
		
		float w = ( (x2-x1) + 1 ) / 2.0f;
		float h = ( (y2-y1) + 1 ) / 2.0f;
		
		float wd = w * (1 - xFactor);
		float hd = h * (1 - yFactor);
		
		x1 += wd;
		y1 += hd;
		x2 -= wd;
		y2 -= hd;
		
		width = ( (x2-x1) + 1 );
		height = ( (y2-y1) + 1 );
	}
	
	public boolean intersects( AABB aabb )
	{
		if( x2 < aabb.x1 ) return false;
		if( x1 > aabb.x2 ) return false;
		if( y2 < aabb.y1 ) return false;
		if( y1 > aabb.y2 ) return false;
		return true;
	}
	
	public boolean pointIsInside( float x, float y )
	{
		if( (x > x1) && (x < x2) ) return true;
		if( (y > y1) && (y < y2) ) return true;
		return false;
	}
}
