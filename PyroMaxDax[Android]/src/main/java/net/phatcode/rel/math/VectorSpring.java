package net.phatcode.rel.math;

public class VectorSpring 
{
	public static final float CFORCE = 10.0f;
	public static final float CFRICTION = 0.849996f;
	public static final float CMAGNITUDE = 15.0f;
	public float x, y, Dx, Dy, Ox, Oy, Force, Frict, Magnitude, Mass, PullAccel;
	
    public VectorSpring()
    {
        this.x = this.y = this.Dx = this.Dy = this.Ox = this.Oy = 0;
		this.Mass = 400.0f;
		this.Force = CFORCE;
		this.Frict = CFRICTION;
		this.Magnitude = CMAGNITUDE;
		this.PullAccel = Force / Mass;
    }
	
	public float getX()
	{
		return x;
	}
	
	public float getY()
	{
		return y;
	}
	
	public float getDx()
	{
		return Dx;
	}
	
	public float getDy()
	{
		return Dy;
	}
	
	public void setX(float x)
	{
		this.x = x;
	}
	
	public void setY(float y)
	{
		this.y = y;
	}
	
	public void setDx(float dx)
	{
		this.Dx = dx;
	}
	
	public void setDy(float dy)
	{
		this.Dy = dy;
	}
	
	public void setOx(float ox)
	{
		this.Ox = ox;
	}
	
	public void setOy(float oy)
	{
		this.Oy = oy;
	}
	
	public VectorSpring update()
	{
		float ix = x - Ox;
		float iy = y - Oy;
		float pullForce = PullAccel * Magnitude;
		Dx -= (ix * pullForce);
		Dy -= (iy * pullForce);
		Dx *= pullForce;
		Dy *= pullForce;
		x += Dx;
		y += Dy;
		
		return this;
	}
	
	public VectorSpring setParameters(float mass, float force, float friction, float magnitude)
	{
		this.Force = force;
		this.Mass = mass;
		this.Frict = friction;
		this.Magnitude = magnitude;
		this.PullAccel = Force / Mass;
		
		return this;
	}
}
