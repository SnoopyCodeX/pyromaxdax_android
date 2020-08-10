package com.relminator.cdph.pmdx.object;

import net.phatcode.rel.math.Utils;
import net.phatcode.rel.math.Vector3D;

public class Particles
{
	public static final int MAX_PARTICLES = 511;
	public Particle[] particles = new Particle[MAX_PARTICLES];
	public int ActiveParticles;
	
	public final class ExplodeType
	{
		public static final int TINY = 0;
		public static final int MEDIUM = 1;
		public static final int LARGE = 2;
	}
	
	public final class Particle
	{
		public Vector3D Position, Direction;
		public int TimeActive, Tima, Red, Green, Blue, Angle, DeathID;
		public boolean Active;
		public float Speed, Alpha;
		
		public Particle()
		{
			Active = false;
			Speed = 8.0f;
			TimeActive = 60;
			Tima = 0;
			DeathID = ExplodeType.TINY;
		}
		
		public Particle spawn(Vector3D pos, Vector3D dir, int ID)
		{
			Active = true;
			Speed = (float) Math.random() * (10 + ID * 10) + (float) Math.random() * (10 + ID * 10);
			Tima = 0;
			Angle = (int) Utils.rad2Deg((float) Math.atan2(dir.y, dir.x));
			Position = pos;
			Direction = dir;
			
			DeathID = ID;
			TimeActive = 20 + (int) Math.random() * (120);
			
			switch(DeathID)
			{
				case ExplodeType.TINY:
					Red = 200;
					Green = 200;
					Blue = 0;
					Alpha = 255;
				break;
				
				case ExplodeType.MEDIUM:
				case ExplodeType.LARGE:
				default:
					Red = (int) Math.random() * 255;
					Green = (int) Math.random() * 255;
					Blue = (int) Math.random() * 255;
					Alpha = 255;
				break;
			}
			
			return this;
		}
		
		public Particle spawn(Vector3D pos, int angle, int ID)
		{
			Active = true;
			Speed = (float) Math.random() * (10 + ID * 10) + (float) Math.random() * (10 + ID * 10);
			Tima = 0;
			Angle = angle;
			Position = pos;
			Direction = new Vector3D((float) Math.cos(Utils.deg2Rad(angle)), (float) Math.sin(Utils.deg2Rad(angle)), 0);

			DeathID = ID;
			TimeActive = 20 + (int) Math.random() * (120);

			switch(DeathID)
			{
				case ExplodeType.TINY:
					Red = 200;
					Green = 200;
					Blue = 0;
					Alpha = 255;
					break;

				case ExplodeType.MEDIUM:
				case ExplodeType.LARGE:
				default:
					Red = (int) Math.random() * 255;
					Green = (int) Math.random() * 255;
					Blue = (int) Math.random() * 255;
					Alpha = 255;
					break;
			}
			
			return this;
		}
		
		public Particle spawn(Vector3D pos, Vector3D dir, float speed, int ID)
		{
			Active = true;
			Speed = speed;
			Tima = 0;
			Angle = (int) Utils.rad2Deg((float) Math.atan2(dir.y, dir.x));
			Position = pos;
			Direction = dir;

			DeathID = ID;
			TimeActive = 120;

			switch(DeathID)
			{
				case ExplodeType.TINY:
					Red = 200;
					Green = 200;
					Blue = 0;
					Alpha = 255;
					break;

				case ExplodeType.MEDIUM:
				case ExplodeType.LARGE:
				default:
					Red = (int) Math.random() * 255;
					Green = (int) Math.random() * 255;
					Blue = (int) Math.random() * 255;
					Alpha = 255;
					break;
			}
			
			return this;
		}
		
		public Particle update()
		{
			Position.add(Direction.mul(Speed));
			Alpha -= 10;
			
			if(Alpha < 0)
				Alpha = 0;
				
			Tima += 1;
			
			if(Tima >= TimeActive)
				Active = false;
			
			return this;
		}
	}
}
