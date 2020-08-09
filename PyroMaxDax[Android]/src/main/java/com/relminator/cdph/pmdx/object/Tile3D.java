package com.relminator.cdph.pmdx.object;

import javax.microedition.khronos.opengles.GL10;
import net.phatcode.rel.utils.SpriteBatcher;
import net.phatcode.rel.utils.SpriteGL;
import net.phatcode.rel.utils.ImageAtlas;
import net.phatcode.rel.utils.Array;

public class Tile3D
{
	public enum TileType {
		TILE_NONE,
		TILE_SOLID,
		TILE_SLOPE,
		TILE_SLOPE_RIGHT,
		TILE_SLOPE_LEFT,
		TILE_SLOPE_RIGHT_HALF_TOP,
		TILE_SLOPE_RIGHT_HALF_BOT,
		TILE_SLOPE_LEFT_HALF_TOP,
		TILE_SLOPE_LEFT_HALF_BOT
	}
	
	public class Player
	{
		public static final int BORED_WAIT_TIME = 60 * 3;
		private Direction direction;
		private State state;
		private boolean canJump = false;
		private int counter;
		private int frame;
		private int baseFrame;
		private int maxFrame;
		private int flipMode;
		private int standingCounter;
		private int width;
		private int height;
		private int cameraX;
		private int cameraY;
		private float x;
		private float y;
		private float Dx;
		private float Dy;
		private float Speed;
		
		public enum State {
			IDLE,
			WALKING,
			JUMPING,
			FALLING,
			BORED,
			PLANT_BOMB,
			LIGHT_DYNAMITE,
			DIED
		}
		
		public enum Direction {
			DIR_LEFT,
			DIR_UP,
			DIR_RIGHT,
			DIR_BOTTOM
		}
		
		public enum Collision {
			COLLIDE_NONE,
			COLLIDE_RIGHT,
			COLLIDE_LEFT,
			COLLIDE_CEILING,
			COLLIDE_FLOOR
		}
		
		public Player()
		{
			counter = 0;
			x = 32 * 2;
			y = 32 * 6;
			Speed = 2.5f;
			width = 24;
			height = 30;
			canJump = false;
			
			maxFrame = 8;
			baseFrame = 0;
			frame = 0;
			
			flipMode = SpriteGL.FLIP_NONE;
			direction = Direction.DIR_RIGHT;
			state = State.FALLING;
			cameraX = 0;
			cameraY = 0;
			standingCounter = 0;
			resolveAnimationParameters();
		}
		
		public boolean collideWalls(int ix, int iy, int tileX, Map[][] map)
		{
			int tileYPixels = iy - (iy % Globals.TILE_SIZE);
			int testEnd = (iy + height) / Globals.TILE_SIZE;
			int tileY = tileYPixels / Globals.TILE_SIZE;
			tileX = ix / Globals.TILE_SIZE;
			
			while(tileY <= testEnd)
			{
				if(map[tileX][tileY].Collision == TileType.TILE_SOLID)
					return true;
				tileY += 1;
			}
			
			return false;
		}
		
		public boolean collideFloors(int ix, int iy, int tileY, Map[][] map)
		{
			int tileXPixels = ix - (ix % Globals.TILE_SIZE);
			int testEnd = (ix + width) / Globals.TILE_SIZE;
			int tileX = tileXPixels / Globals.TILE_SIZE;
			tileY = iy / Globals.TILE_SIZE;
			
			while(tileX < testEnd)
			{
				if(map[tileX][tileY].Collision == TileType.TILE_SOLID)
					return true;
				tileX += 1;
			}
			
			return false;
		}
		
		public Collision collideOnMap(Map[][] map)
		{
			Collision collisionType = Collision.COLLIDE_NONE;
			int tileX = 0, tileY = 0;
			
			if(Dx > 0)
			{
				if(collideWalls((int) x + (int) Dx + width, (int) y, tileX, map))
				{
					x = tileX * Globals.TILE_SIZE - width - 1;
					collisionType = Collision.COLLIDE_RIGHT;
				}
				else
					x += Dx;
			}
			else if(Dx < 0)
			{
				if(collideWalls((int) x + (int) Dx, (int) y, tileX, map))
				{
					x = (tileX + 1) * Globals.TILE_SIZE + 1;
					collisionType = Collision.COLLIDE_LEFT;
				}
				else
					x += Dx;
			}
			
			if(Dy > 0)
			{
				if(collideFloors((int) x, (int) y + (int) Dy + height, tileY, map))
				{
					y = (tileY) * Globals.TILE_SIZE - height - 1;
					Dy = 1;
					collisionType = Collision.COLLIDE_FLOOR;
				}
				else
				{
					y += Dy;
					Dy += Globals.GRAVITY;
				}
			}
			else
			{
				if(collideFloors((int) x, (int) y + (int) Dy, tileY, map))
				{
					y = (tileY + 1) * Globals.TILE_SIZE + 1;
					Dy = 0;
					collisionType = Collision.COLLIDE_CEILING;
				}
				else
				{
					y += Dy;
					Dy += Globals.GRAVITY;
				}
			}
			
			return collisionType;
		}
		
		public Player update(Map[][] map)
		{
			counter += 1;
			animate();
			
			//player states here..
			
			final int SCREEN_MID_X = Globals.SCREEN_WIDTH / 2;
			final int SCREEN_MID_Y = Globals.SCREEN_HEIGHT / 2;
			final int MAP_WIDTH = Array.ubound(map, 1);
			final int MAP_HEIGHT = Array.ubound(map, 2);
			final int MAP_MAX_X = MAP_WIDTH * Globals.TILE_SIZE - Globals.SCREEN_WIDTH;
			final int MAP_MAX_Y = MAP_HEIGHT * Globals.TILE_SIZE - Globals.SCREEN_HEIGHT;
			
			cameraX = (int) x - SCREEN_MID_X;
			cameraY = (int) y - SCREEN_MID_Y;
			
			cameraX = (cameraX < 0) ? 0 : cameraX;
			cameraX = (cameraX > MAP_MAX_X) ? MAP_MAX_X : cameraX;
			
			cameraY = (cameraY < 0) ? 0 : cameraY;
			cameraY = (cameraY > MAP_MAX_Y) ? MAP_MAX_Y : cameraY;
			
			return this;
		}
		
		public Player resolveAnimationParameters()
		{
			if(state == State.IDLE)
			{
				frame = 0;
				baseFrame = 16;
				maxFrame = 1;
			}
			else if(state == State.WALKING)
			{
				frame = 0;
				baseFrame = 0;
				maxFrame = 8;
			}
			else if(state == State.JUMPING || state == State.FALLING)
			{
				frame = 0;
				baseFrame = 17;
				maxFrame = 1;
			}
			else if(state == State.BORED)
			{
				frame = 0;
				baseFrame = 9;
				maxFrame = 7;
			}
			else if(state == State.LIGHT_DYNAMITE)
			{
				frame = 0;
				baseFrame = 18;
				maxFrame = 7;
			}
			else if(state == State.PLANT_BOMB)
			{
				frame = 0;
				baseFrame = 24;
				maxFrame = 5;
			}
			else if(state == State.DIED)
			{
				frame = 0;
				baseFrame = 28;
				maxFrame = 4;
			}
			
			return this;
		}
		
		public Player draw(SpriteBatcher batcher, ImageAtlas image)
		{
			int xOff = (Globals.TILE_SIZE - width) / 2;
			int yOff = (Globals.TILE_SIZE - height) / 2;
			
			batcher.sprite3D(x - xOff, y - yOff, 0, flipMode, image.getSprite(baseFrame + frame));
			
			return this;
		}
		
		public Player animate()
		{
			if((counter & 3) == 0)
				frame = (frame + 1) % maxFrame;
				
			flipMode = (direction == Direction.DIR_RIGHT) ? SpriteGL.FLIP_NONE : SpriteGL.FLIP_H;
			
			return this;
		}
		
		public float getX()
		{
			return x;
		}
		
		public float getY()
		{
			return y;
		}
		
		public float getCameraX()
		{
			return cameraX;
		}
		
		public float getCameraY()
		{
			return cameraY;
		}
	}
}
