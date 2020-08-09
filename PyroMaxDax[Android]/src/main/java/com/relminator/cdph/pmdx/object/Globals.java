package com.relminator.cdph.pmdx.object;

public class Globals 
{
    public static final int TILE_SIZE = 32;
	public static final int SCREEN_WIDTH = 320;
	public static final int SCREEN_HEIGHT = 480;
	public static final float FIXED_TIME_STEP = (1.0f/60.0f);
	public static final int JUMPHEIGHT = 7;
	public static final float GRAVITY = 0.30f;
	public static final float FRICTION = 0.022f;
	public static final float ACCEL = 0.099f;
	public static final float DAMPER = (FRICTION * 4);
	public static final float ICE_DAMPER = (FRICTION);
	public static final float MINIMUM_SPEED_THRESHOLD = 0.5f;

	public static final int PARALLAX_PLANE = (-28);
	public static final int BACKGROUND_PLANE = (-2);
	public static final int FOREGROUND_PLANE = 28;

	public static final int MAX_DISTANCE_FROM_PLAYER = 12;
	public static final int MAX_ENEMY_BLINK_COUNTER = 15;
	
	public int QuakeCounter = 0;
	
	public Globals setQuakeCounter(int val)
	{
		QuakeCounter = val;
		return this;
	}
	
	public int quake()
	{
		QuakeCounter -= 1;
		
		if(QuakeCounter > 0)
			return 1;
		return 0;
	}
}
