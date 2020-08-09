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

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

public class LineBatcher
{
	private IntBuffer vertexBuffer;	
	private IntBuffer colorBuffer;	
	private int numLines;
	private int maxSize;
	private int index;
	private int cindex;
	
	private float[] vertices; 
	private float[] colors; 
	private int[] tempBuffer;
	
	private ByteBuffer vertexByteBuffer;
	private ByteBuffer colorByteBuffer;
	
	public LineBatcher()
	{
		this( 2048 );
	}

	public LineBatcher( int maxLines )
	{
		this.index = 0;
		this.cindex = 0;
		this.numLines = 0;
		this.maxSize = maxLines * 2 * 2;  // 2 verts per line * 2 floats per vertex
		
		this.vertices = new float[this.maxSize];
		this.colors = new float[maxLines * 2 * 4];   // 4(rgba) * 2 verts per line
		
		tempBuffer = new int[maxLines * 2 * 4];
		
		for( int i = 0; i < colors.length; i++ )
		{
			this.colors[i] = 1.0f;
		}
			
		
		vertexByteBuffer = ByteBuffer.allocateDirect(maxLines * 4 * 4);
		vertexByteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = vertexByteBuffer.asIntBuffer();
		
		colorByteBuffer = ByteBuffer.allocateDirect(maxLines * 8 * 4);
		colorByteBuffer.order(ByteOrder.nativeOrder());
		colorBuffer = colorByteBuffer.asIntBuffer();
	
		
	}
	
	
	public void line( float x1, float y1, float x2, float y2, 
					  float r, float g, float b, float a )
	{
			
		vertices[index++] = x1;
		vertices[index++] = y1;
		vertices[index++] = x2;
		vertices[index++] = y2;
		
		colors[ cindex++ ] = r; colors[ cindex++ ] = g; colors[ cindex++ ] = b; colors[ cindex++ ] = a;
		colors[ cindex++ ] = r; colors[ cindex++ ] = g; colors[ cindex++ ] = b; colors[ cindex++ ] = a;
		
        numLines++; 
	
	}

	public void line( float x1, float y1, float x2, float y2, 
			  		  float r1, float g1, float b1, float a1,
			  		  float r2, float g2, float b2, float a2 )
	{
		
		vertices[index++] = x1;
		vertices[index++] = y1;
		vertices[index++] = x2;
		vertices[index++] = y2;
		
		colors[ cindex++ ] = r1; colors[ cindex++ ] = g1; colors[ cindex++ ] = b1; colors[ cindex++ ] = a1;
		colors[ cindex++ ] = r2; colors[ cindex++ ] = g2; colors[ cindex++ ] = b2; colors[ cindex++ ] = a2;
		
		numLines++; 
	
	}

	public void box( float x1, float y1, float x2, float y2, 
			  		 float r, float g, float b, float a )
	{
		
		line( x1, y1, x1, y2, r, g, b, a );
		line( x1, y2, x2, y2, r, g, b, a );
		line( x2, y2, x2, y1, r, g, b, a );
		line( x2, y1, x1, y1, r, g, b, a );
	}


	public void render( GL10 gl )
	{
		
		if( numLines < 1 ) return;
		
		gl.glBindTexture( GL11.GL_TEXTURE_2D, 0 );
		
	
		fillBuffers();
		
		gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		gl.glEnableClientState( GL10.GL_COLOR_ARRAY );
		gl.glColorPointer(4, GL10.GL_FLOAT, 4 << 2, colorBuffer );
		gl.glVertexPointer( 2, GL10.GL_FLOAT, 2 << 2, vertexBuffer );   
		gl.glDrawArrays( GL10.GL_LINES, 0, numLines * 2 );   
		gl.glDisableClientState( GL10.GL_COLOR_ARRAY );
		gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		
	    this.index = 0;
	    this.cindex = 0;
		this.numLines = 0;
		
		vertexBuffer.clear();
		colorBuffer.clear();
		
	}

	private void fillBuffers()
	{
		
		
		int size = numLines * 2 * 2; 
	    for( int i = 0; i < size; i++ )
	    {
	        tempBuffer[i] = Float.floatToRawIntBits(vertices[i]); 
	    }
	    
	    vertexBuffer.put( tempBuffer, 0, numLines * 4 );
		vertexBuffer.flip();
		
		size = numLines * 4 * 2; 
	    for( int i = 0; i < size; i++ )
	    {
	        tempBuffer[i] = Float.floatToRawIntBits(colors[i]); 
	    }
		    
	    colorBuffer.put( tempBuffer, 0, numLines * 8 );
		colorBuffer.flip();
		
	}
		
		

}   // end class
