package com.relminator.cdph.pmdx.object;

import android.opengl.GLU;
import javax.microedition.khronos.opengles.GL10;
import net.phatcode.rel.utils.Array;

public class Camera 
{
	public Vector3D position;
	public Vector3D target;
	public Vector3D up;
	public int eyeDistanceFromScreen;
	
    public Camera()
	{
		eyeDistanceFromScreen = Globals.TILE_SIZE * 18;
		position.x = position.y = 0;
		position.z = eyeDistanceFromScreen;
		target.x = target.y = target.z = 0;
		up.x = up.z = 0;
		up.y = 1;
	}
	
	public Camera follow(float x, float y, Map[][] map)
	{
		int mapWidth = Array.ubound(map, 1);
		int mapHeight = Array.ubound(map, 2);
		
		float minX = Globals.SCREEN_WIDTH / 2;
		float minY = Globals.SCREEN_HEIGHT / 2;
		float maxX = (mapWidth * Globals.TILE_SIZE) - minX;
		float maxY = (mapHeight * Globals.TILE_SIZE) - minY;
		
		x = (x < minX) ? minX : x;
		y = (y < minY) ? minY : y;
		x = (x > maxX) ? maxX : x;
		y = (y > maxY) ? maxY : y;
		
		position.x = x;
		position.y = y;
		position.z = eyeDistanceFromScreen;
		
		target.x = x;
		target.y = y;
		target.z = 0;
		
		up.x = 0;
		up.y = 1;
		up.z = 0;
		
		return this;
	}
	
	public Camera look(GL10 gl)
	{
		GLU glu = new GLU();
		
		glu.gluLookAt(gl, position.x, position.y, position.z, 
						  target.x, target.y, target.z, 
						  up.x, up.y, up.z);
		
		return this;
	}
	
	public Camera zoom(float value)
	{
		eyeDistanceFromScreen += value;
		
		return this;
	}
	
	public class Vector3D
	{
		public float x;
		public float y;
		public float z;
	}
}
