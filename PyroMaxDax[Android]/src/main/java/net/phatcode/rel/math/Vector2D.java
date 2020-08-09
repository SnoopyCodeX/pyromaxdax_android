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



package net.phatcode.rel.math;


public class Vector2D 
{

	public static final float EPSILON = 0.0000000001f; 

	public float x;
	public float y;
	
	public Vector2D()
	{
	}
	
	public Vector2D( float a, float b )
	{
		x = a;
		y = b;
	}
	
	public Vector2D( Vector2D v )
	{
		x = v.x;
		y = v.y;
	}
	
	public Vector2D set( float a, float b )
	{
		x = a;
		y = b;
		return this;
	}
	
	public Vector2D set( Vector2D v )
	{
		x = v.x;
		y = v.y;
		return this;
	}
	
	public Vector2D add( float x, float y )
	{
		this.x += x;
		this.y += y;
		return this;
	}
	
	public Vector2D add( Vector2D v )
	{
		this.x += v.x;
		this.y += v.y;
		return this;
	}
	
	public Vector2D sub( float x, float y )
	{
		this.x -= x;
		this.y -= y;
		return this;
	}
	
	public Vector2D sub( Vector2D v )
	{
		this.x -= v.x;
		this.y -= v.y;
		return this;
	}
	
	public Vector2D mul( float x, float y )
	{
		this.x *= x;
		this.y *= y;
		return this;
	}
	
	public Vector2D mul( Vector2D v )
	{
		this.x *= v.x;
		this.y *= v.y;
		return this;
	}
	
	public Vector2D div( float x, float y )
	{
		this.x /= x;
		this.y /= y;
		return this;
	}
	
	public Vector2D div( Vector2D v )
	{
		this.x /= v.x;
		this.y /= v.y;
		return this;
	}
	
	public float magnitude()
	{
		return (float)Math.sqrt( (x * x) + (y * y) );
	}
	
	public float magnitudeSquared()
	{
		return ( (x * x) + (y * y) );
	}
	
	public float dot( Vector2D v )
	{
		return ( (x * v.x) + (y * v.y) );
	}
	
	public Vector2D normal( boolean side )
	{
		return ( side == false ) ? new Vector2D( -y, x ) : new Vector2D( y, -x );
	}
	
	public Vector2D normalize()
	{
		float length = magnitude();
		float invMag = 1.0f/length;
		x *= invMag;
		y *= invMag;
		return this;
	}
	
	public Vector2D scale( float s )
	{
		this.x *= s;
		this.y *= s;
		return this;
	}
	
	public float angleBetween( Vector2D v )
	{
		return (float)(Math.atan2(v.y - y, v.x - x));
	}
	
	public Vector2D reflect( Vector2D n )
	{
		// n -> normal of the vector *this would reflect from
		// Assumes normal(n) is normalized
		// -2 * (V dot N) * N + V
		// Or
		// -2 * (V dot N)/|N| *N + V
		
		float dot = (x * n.x) + (y * n.y);
		Vector2D n2 = new Vector2D( n.x * -2.0f, n.y * -2.0f );
		n2.scale(dot);
		n2.x += x;
		n2.y += y;
		
		return n2;
	}
	
	public void displayComponents()
	{
		System.out.println( "v.x = " + x + " :: v.y = " + y );
	}
	
	public String toString()
	{
		return ( "v.x = " + x + " :: v.y = " + y );
	}
	
	
	// Static 
	public static Vector2D add( Vector2D a, Vector2D b )
	{
		return new Vector2D( a.x + b.x , a.y + b.y );
	}
	
	public static Vector2D add( Vector2D a, float x, float y )
	{
		return new Vector2D( a.x + x , a.y + y );
	}
	
	public static Vector2D sub( Vector2D a, Vector2D b )
	{
		return new Vector2D( a.x - b.x , a.y - b.y );
	}
	
	public static Vector2D sub( Vector2D a, float x, float y )
	{
		return new Vector2D( a.x - x , a.y - y );
	}
	
	public static Vector2D mul( Vector2D a, Vector2D b )
	{
		return new Vector2D( a.x * b.x , a.y * b.y );
	}
	
	public static Vector2D mul( Vector2D a, float x, float y )
	{
		return new Vector2D( a.x * x , a.y * y );
	}
	
	public static Vector2D div( Vector2D a, Vector2D b )
	{
		return new Vector2D( a.x / b.x , a.y / b.y );
	}
	
	public static Vector2D div( Vector2D a, float x, float y )
	{
		return new Vector2D( a.x / x , a.y / y );
	}
	
	public static float magnitude( Vector2D v )
	{
		return v.magnitude();
	}
	
	public static float magnitudeSquared( Vector2D v )
	{
		return v.magnitudeSquared();
	}
	
	public static Vector2D normalize( Vector2D v )
	{
		float length = v.magnitude(); 
		float invMag = 1.0f/length;
		v.x *= invMag;
		v.y *= invMag;
		return v;
	}
	
	public static float dot( Vector2D a, Vector2D b )
	{
		return ( (a.x * b.x) + (a.y * b.y) );
	}
	
	public static Vector2D Projection( Vector2D u, Vector2D v )
	{
		float dotUV = dot(u,v);
		float dotUU = dot(u,u);
		return u.scale( dotUV/dotUU );
	}
	
	public static Vector2D Normal( Vector2D v, boolean side )
	{
		return ( side == false ) ? new Vector2D( -v.y, v.x ) : new Vector2D( v.y, -v.x );
	}
	
	public static Vector2D Normal( Vector2D v1, Vector2D v2, boolean side )
	{
		Vector2D v = new Vector2D( v2.x - v1.x, v2.y - v1.y );
		return ( side == false ) ? new Vector2D( -v.y, v.x ) : new Vector2D( v.y, -v.x );
	}
	
	public static float Distance( Vector2D a, Vector2D b )
	{
		Vector2D d = new Vector2D( a.x - b.x, a.y - b.y );
		return d.magnitude();
	}

	public static float angleBetween( Vector2D a, Vector2D b )
	{
		return (float)(Math.atan2(b.y - a.y, b.x - a.x));
	}
	
	public static Vector2D reflect( Vector2D v, Vector2D n )
	{
		// Assumes normal(n) is normalized
		// -2 * (V dot N) * N + V
		// Or
		// -2 * (V dot N)/|N| *N + V
		
		float dot = (v.x * n.x) + (v.y * n.y);
		Vector2D n2 = new Vector2D( n.x * -2.0f, n.y * -2.0f );
		n2.scale(dot);
		n2.x += v.x;
		n2.y += v.y;
		
		return n2;
	}
	
	public static Vector2D midPoint( Vector2D a, Vector2D b )
	{
		return new Vector2D( (b.x + a.x)/2.0f, (b.y + a.y)/2.0f );
	}
	
	public static Vector2D randomVector( float maxX, float maxY )
	{
		return new Vector2D( (float)(Math.random() * maxX), (float)(Math.random() * maxY) );
	}

	
}  // end class
