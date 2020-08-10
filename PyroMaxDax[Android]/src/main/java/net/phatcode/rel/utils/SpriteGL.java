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

public class SpriteGL
{
	
	public static final int FLIP_NONE = 1 << 0;
	public static final int FLIP_V 	  = 1 << 1;
	public static final int FLIP_H	  = 1 << 2;

	public int texture_width;
	public int texture_height;
	public int width;
	public int height;
	public float u1;
	public float v1;
	public float u2;
	public float v2;
	public float u_off;
	public float v_off;
	
	public SpriteGL()
	{}
}
