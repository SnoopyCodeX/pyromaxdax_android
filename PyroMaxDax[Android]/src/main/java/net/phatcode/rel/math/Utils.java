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

public class Utils
{
	public static float PI = 3.1415926535897932384626433832795f;
	public static float DEG_TO_RAD = PI / 180.0f ; 
	public static float RAD_TO_DEG = 180 / PI;
	
	public static double getDeltaTime(double fps, double timerInSeconds)
	{
		double framesPerSecond = 0.0;
		double previousTime = 0.0;
		double oldTime = 0.0;
		double currentTime = timerInSeconds;
		double elapsedTime = currentTime - oldTime;
		
		oldTime = currentTime;
		framesPerSecond += 1;
		
		if((currentTime - previousTime) > 1.0)
		{
			previousTime = currentTime;
			fps = framesPerSecond;
			framesPerSecond = 0;
		}
		
		return elapsedTime;
	}
	
	public static float deg2Rad( float degrees )
	{
		return degrees * PI / 180.0f;
	}
	
	public static float deg2Rad( int degrees )
	{
		return degrees * PI / 180.0f;
	}
	
	public static float rad2Deg( float radians )
	{
		return radians * 180.0f / PI;
	}
	
	public static float smoothStep( float x )
	{
		return ((x) * (x) * (3 - 2 * (x)));
	}
	
	public static float smoothStep( float a, float b, float v )
	{
		if( v < a ) return 0;
		if( v > b ) return 1;
		v = ( v - a )/( b - a );
		return ((v) * (v) * (3 - 2 * (v)));
	}

	public static float lerp( float start, float end, float t )
	{
		return ( start + ( ( end - start ) * t ) );
	}

	public static float lerpSmooth( float start, float end, float t )
	{
		return ( start + ( ( end - start ) * smoothStep(t) ) );	
	}

	public static float weightedAverage( float v, float w, float n )
	{
		 return ((v * (n - 1)) + w) / n; 
	}

	public static float clamp( float a, float min, float max )
	{	
		return (a < min) ? min : (a > max) ? max : a;
	}

	public static int clamp( int a, int min, int max )
	{	
		return (a < min) ? min : (a > max) ? max : a;
	}

	public static float wrap( float a, float min, float max )
	{
		return (a < min) ? (a - min) + max : (a > max) ? (a - max) + min : a;
	}

	public static int wrap( int a, int min, int max )
	{
		return (a < min) ? (a - min) + max : (a > max) ? (a - max) + min : a;
	}

	public static float catMullRom( float p0, float p1, float p2, float p3, float t )
	{
		
		return 0.5f * ( (2 * p1) +
					  (-p0 + p2) * t +
					  (2 * p0 - 5 * p1 + 4 * p2 - p3) * t * t +
					  (-p0 + 3 * p1 - 3 * p2 + p3) * t * t * t );

	}

	public static float catMullRomDerivative( float p0, float p1, float p2, float p3, float t )
	{
		return 0.5f * ( p2 - p0 + 2 * t * ( 2 * p0 - 5 * p1 + 4 * p2 - p3 ) + 
					    3 * t * t * (3 * p1 + p3 - p0 - 3 * p2 ) );
	}
	
	public static float bezier( float p0, float p1, float p2, float p3, float t )
	{
		
		float b = 1 - t;
	    float b2 = b * b;
	    float b3 = b * b * b;
	    
	    float t2 = t * t;
	    float t3 = t * t * t;
	    
	    return p1 * b3 + 3* p0 *(b2) * t + 3 * p3 *(b) * (t2) + p2 * (t3);
		
	}

	public static float bezierDerivative( float p0, float p1, float p2, float p3, float t )
	{
		
		float t2 = t * t;
	    
		float a = p0 * ( 1 - (4 * t) + (3 * t2) );
		float b = p1 * ( (2 * t) -  1 - t2 );
		float c = p2 * t2;
		float d = p3 * ( (2 * t) - (3 * t2) );
		
		return 3 * (a + b + c + d);
		
	}

	public static String int2Score( int score, String filler )
	{
		
		String sc = score + "";
		if( filler.length() < sc.length() )
		{
			String ch = filler.substring(1);
			while( filler.length() < sc.length() )
			{
				filler += ch;
			}
			 
		}
		
		String text = filler.substring(sc.length());
		return text + sc;
				
	}   
	
	public static double getSystemSeconds() 
	{
		return System.nanoTime() / 1000000000.0;
	}

	public static double getSystemMilliSeconds() 
	{
		return System.nanoTime() / 1000000.0;
	}
	
}
