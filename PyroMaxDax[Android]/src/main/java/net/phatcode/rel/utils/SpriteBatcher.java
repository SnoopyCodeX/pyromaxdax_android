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
import java.nio.ShortBuffer;
import javax.microedition.khronos.opengles.GL10;
import com.relminator.cdph.pmdx.object.Globals;

//CCW
//6 verts per quad
//Top First
//TOP LEFT first
//6
//1       5
//|
//|       4
//2-------3
public class SpriteBatcher
{
	
	public static float DEG_TO_RAD = (float)Math.PI / 180.0f ; 
	public static float RAD_TO_DEG = 180 / (float) Math.PI;
	
	public float CubeVectors[] = {
		(-0.5f), (-0.5f), ( 0.5f),
		( 0.5f), (-0.5f), ( 0.5f),
		( 0.5f), (-0.5f), (-0.5f),
		(-0.5f), (-0.5f), (-0.5f),
		(-0.5f), ( 0.5f), ( 0.5f), 
		( 0.5f), ( 0.5f), ( 0.5f),
		( 0.5f), ( 0.5f), (-0.5f),
		(-0.5f), ( 0.5f), (-0.5f) 
	};

	public float CubeFaces[] = {
		3, 2, 1, 0,
		0, 1, 5, 4,
		1, 2, 6, 5,
		2, 3, 7, 6,
		3, 0, 4, 7,
		4, 5, 6, 7
	};

	private int current_texture;
	
	private IntBuffer vertexBuffer;	
	private ShortBuffer indexBuffer;
	
	
	private int numSprites;
	private int maxSize;
	private int index;
	
	private float[] vertices; 
	
	private int[] tempBuffer;
	
	private ByteBuffer vertexByteBuffer;
	private final int vertexStride = 8 * 4; // (2 v + 4c + 2 u) * 4 verts
	
	public SpriteBatcher()
	{
		this( 1024 );
	}

	public SpriteBatcher( int maxSprites )
	{
		this.index = 0;
		this.numSprites = 0;
		this.maxSize = maxSprites * vertexStride;   // (2 v + 4c + 2 u) * 4 verts
		
		this.vertices = new float[this.maxSize];
			
		vertexByteBuffer = ByteBuffer.allocateDirect(maxSize * Float.SIZE / 8);
		vertexByteBuffer.order(ByteOrder.nativeOrder());
		vertexBuffer = vertexByteBuffer.asIntBuffer();
		
		tempBuffer = new int[maxSprites * vertexStride];
		
		vertexByteBuffer = ByteBuffer.allocateDirect( (maxSprites * 6) * Short.SIZE / 8 );
		vertexByteBuffer.order(ByteOrder.nativeOrder());
		vertexByteBuffer.position(0);
		
		short[] indices = new short[maxSprites * 6]; 
        int j = 0; 
        for( int i = 0; i < indices.length; ) 
		{ 
            indices[i++] = (short)(j + 0); 
            indices[i++] = (short)(j + 1); 
            indices[i++] = (short)(j + 2);  
            indices[i++] = (short)(j + 2); 
            indices[i++] = (short)(j + 3); 
            indices[i++] = (short)(j + 0); 
            j += 4;
		} 
    	
		indexBuffer = vertexByteBuffer.asShortBuffer();
		indexBuffer.put( indices, 0, indices.length );
		indexBuffer.position(0);
		
		
	}
	
	public void sprite( float x, float y, int flipmode, SpriteGL sprite )
	{
			
		float x1 = x;
		float y1 = y;
		float x2 = x + sprite.width;
		float y2 = y + sprite.height;
		
		
		float u1 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u1 : sprite.u2;
		float v1 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v1 : sprite.v2;
		float u2 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u2 : sprite.u1;
		float v2 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v2 : sprite.v1;
		
		vertices[index++] = x1;
		vertices[index++] = y1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = u1;
		vertices[index++] = v1;
		
		
		vertices[index++] = x1;
		vertices[index++] = y2;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = u1;
		vertices[index++] = v2;
		
		vertices[index++] = x2;
		vertices[index++] = y2;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = u2;
		vertices[index++] = v2;
		
		vertices[index++] = x2;
		vertices[index++] = y1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = u2;
		vertices[index++] = v1;
		
		numSprites++; 
	
	}

	public void sprite( float x, 
						float y, 
						float r, 
						float g, 
						float b, 
						float a, 
						int flipmode, 
						SpriteGL sprite )
	{
			
		float x1 = x;
		float y1 = y;
		float x2 = x + sprite.width;
		float y2 = y + sprite.height;
		
		
		float u1 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u1 : sprite.u2;
		float v1 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v1 : sprite.v2;
		float u2 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u2 : sprite.u1;
		float v2 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v2 : sprite.v1;
		
		vertices[index++] = x1;
		vertices[index++] = y1;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v1;
		
		
		vertices[index++] = x1;
		vertices[index++] = y2;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v2;
		
		vertices[index++] = x2;
		vertices[index++] = y2;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v2;
		
		vertices[index++] = x2;
		vertices[index++] = y1;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v1;
		
		numSprites++; 
	
	}

	public void sprite( float x, 
						float y, 
						float[] vertexColors, 
						int flipmode, 
						SpriteGL sprite )
	{
	
		float x1 = x;
		float y1 = y;
		float x2 = x + sprite.width;
		float y2 = y + sprite.height;
		
		
		float u1 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u1 : sprite.u2;
		float v1 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v1 : sprite.v2;
		float u2 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u2 : sprite.u1;
		float v2 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v2 : sprite.v1;
		
		vertices[index++] = x1;
		vertices[index++] = y1;
		vertices[index++] = vertexColors[0];
		vertices[index++] = vertexColors[1];
		vertices[index++] = vertexColors[2];
		vertices[index++] = vertexColors[3];
		vertices[index++] = u1;
		vertices[index++] = v1;
		
		
		vertices[index++] = x1;
		vertices[index++] = y2;
		vertices[index++] = vertexColors[4];
		vertices[index++] = vertexColors[5];
		vertices[index++] = vertexColors[6];
		vertices[index++] = vertexColors[7];
		vertices[index++] = u1;
		vertices[index++] = v2;
		
		vertices[index++] = x2;
		vertices[index++] = y2;
		vertices[index++] = vertexColors[8];
		vertices[index++] = vertexColors[9];
		vertices[index++] = vertexColors[10];
		vertices[index++] = vertexColors[11];
		vertices[index++] = u2;
		vertices[index++] = v2;
		
		vertices[index++] = x2;
		vertices[index++] = y1;
		vertices[index++] = vertexColors[12];
		vertices[index++] = vertexColors[13];
		vertices[index++] = vertexColors[14];
		vertices[index++] = vertexColors[15];
		vertices[index++] = u2;
		vertices[index++] = v1;
		
		numSprites++; 
	
	}

	public void spriteRotateScale( float x, 
								   float y, 
								   float rotation,
								   float scalex,
								   float scaley, 
								   int flipmode, 
								   SpriteGL sprite )
	{
	
		float hx = (sprite.width/2) * scalex;
		float hy = (sprite.height/2) * scaley;
		
		float hx1 = -hx;    // top left
		float hy1 = -hy;
		float hx2 = -hx;	// bottom left
		float hy2 =  hy;
		float hx3 =  hx;	// bottom right
		float hy3 =  hy;
		float hx4 =  hx;	// top right
		float hy4 = -hy;
		
		float cz = (float) Math.cos(rotation);
		float sz = (float) Math.sin(rotation);
		
		float x1 = cz * hx1 - sz * hy1;
		float y1 = sz * hx1 + cz * hy1;
		float x2 = cz * hx2 - sz * hy2;
		float y2 = sz * hx2 + cz * hy2;
		float x3 = cz * hx3 - sz * hy3;
		float y3 = sz * hx3 + cz * hy3;
		float x4 = cz * hx4 - sz * hy4;
		float y4 = sz * hx4 + cz * hy4;
		
		float u1 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u1 : sprite.u2;
		float v1 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v1 : sprite.v2;
		float u2 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u2 : sprite.u1;
		float v2 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v2 : sprite.v1;
		
		vertices[index++] = x1 + x;
		vertices[index++] = y1 + y;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = u1;
		vertices[index++] = v1;
		
		vertices[index++] = x2 + x;
		vertices[index++] = y2 + y;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = u1;
		vertices[index++] = v2;
		
		vertices[index++] = x3 + x;
		vertices[index++] = y3 + y;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = u2;
		vertices[index++] = v2;
		
		vertices[index++] = x4 + x;
		vertices[index++] = y4 + y;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = 1;
		vertices[index++] = u2;
		vertices[index++] = v1;
		
		numSprites++; 
		
	}

	public void spriteRotateScale( float x, 
			   					   float y,
			   					   float r, 
			   					   float g, 
			   					   float b, 
			   					   float a, 
			   					   float rotation,
			   					   float scalex,
			   					   float scaley, 
			   					   int flipmode, 
			   					   SpriteGL sprite )
	{
	
		float hx = (sprite.width/2) * scalex;
		float hy = (sprite.height/2) * scaley;
		
		float hx1 = -hx;    // top left
		float hy1 = -hy;
		float hx2 = -hx;	// bottom left
		float hy2 =  hy;
		float hx3 =  hx;	// bottom right
		float hy3 =  hy;
		float hx4 =  hx;	// top right
		float hy4 = -hy;
		
		float cz = (float) Math.cos(rotation);
		float sz = (float) Math.sin(rotation);
		
		float x1 = cz * hx1 - sz * hy1;
		float y1 = sz * hx1 + cz * hy1;
		float x2 = cz * hx2 - sz * hy2;
		float y2 = sz * hx2 + cz * hy2;
		float x3 = cz * hx3 - sz * hy3;
		float y3 = sz * hx3 + cz * hy3;
		float x4 = cz * hx4 - sz * hy4;
		float y4 = sz * hx4 + cz * hy4;
		
		float u1 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u1 : sprite.u2;
		float v1 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v1 : sprite.v2;
		float u2 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u2 : sprite.u1;
		float v2 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v2 : sprite.v1;
		
		vertices[index++] = x1 + x;
		vertices[index++] = y1 + y;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v1;
		
		vertices[index++] = x2 + x;
		vertices[index++] = y2 + y;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v2;
		
		vertices[index++] = x3 + x;
		vertices[index++] = y3 + y;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v2;
		
		vertices[index++] = x4 + x;
		vertices[index++] = y4 + y;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v1;
		
		numSprites++; 
	
	}

	public void spriteRotateScale( float x, 
								   float y,
								   float[] vertexColors, 
								   float rotation,
								   float scalex,
								   float scaley, 
								   int flipmode, 
								   SpriteGL sprite )
	{
	
		float hx = (sprite.width/2) * scalex;
		float hy = (sprite.height/2) * scaley;
		
		float hx1 = -hx;    // top left
		float hy1 = -hy;
		float hx2 = -hx;	// bottom left
		float hy2 =  hy;
		float hx3 =  hx;	// bottom right
		float hy3 =  hy;
		float hx4 =  hx;	// top right
		float hy4 = -hy;
		
		float cz = (float) Math.cos(rotation);
		float sz = (float) Math.sin(rotation);
		
		float x1 = cz * hx1 - sz * hy1;
		float y1 = sz * hx1 + cz * hy1;
		float x2 = cz * hx2 - sz * hy2;
		float y2 = sz * hx2 + cz * hy2;
		float x3 = cz * hx3 - sz * hy3;
		float y3 = sz * hx3 + cz * hy3;
		float x4 = cz * hx4 - sz * hy4;
		float y4 = sz * hx4 + cz * hy4;
		
		float u1 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u1 : sprite.u2;
		float v1 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v1 : sprite.v2;
		float u2 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u2 : sprite.u1;
		float v2 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v2 : sprite.v1;
		
		vertices[index++] = x1 + x;
		vertices[index++] = y1 + y;
		vertices[index++] = vertexColors[0];
		vertices[index++] = vertexColors[1];
		vertices[index++] = vertexColors[2];
		vertices[index++] = vertexColors[3];
		vertices[index++] = u1;
		vertices[index++] = v1;
		
		vertices[index++] = x2 + x;
		vertices[index++] = y2 + y;
		vertices[index++] = vertexColors[4];
		vertices[index++] = vertexColors[5];
		vertices[index++] = vertexColors[6];
		vertices[index++] = vertexColors[7];
		vertices[index++] = u1;
		vertices[index++] = v2;
		
		vertices[index++] = x3 + x;
		vertices[index++] = y3 + y;
		vertices[index++] = vertexColors[8];
		vertices[index++] = vertexColors[9];
		vertices[index++] = vertexColors[10];
		vertices[index++] = vertexColors[11];
		vertices[index++] = u2;
		vertices[index++] = v2;
		
		vertices[index++] = x4 + x;
		vertices[index++] = y4 + y;
		vertices[index++] = vertexColors[12];
		vertices[index++] = vertexColors[13];
		vertices[index++] = vertexColors[14];
		vertices[index++] = vertexColors[15];
		vertices[index++] = u2;
		vertices[index++] = v1;
		
		numSprites++; 
	
	}

	public void spriteStretch( float x, 
							   float y, 
							   float width, 
							   float height, 
							   float r, 
							   float g, 
							   float b, 
							   float a,
							   SpriteGL sprite )
	{
	
		float hw = sprite.width/2;
		float hh = sprite.height/2;
		
		float uoff = sprite.u1;
		float voff = sprite.v1;
		float uwidth = sprite.u2 - sprite.u1;
		float vheight = sprite.v2 - sprite.v1;
		
		float su = uwidth/2;
		float tv = vheight/2;
				
		float x1 = x;
		float y1 = y;
		float x2 = x + width;
		float y2 = y + height;
		
		float xh1 = x1 + hw;
		float yh1 = y1 + hh;
		float xh2 = x2 - hw;
		float yh2 = y2 - hh;
		
		// top left
		spriteOnBox( x1, 
			 y1, 
			 xh1, 
			 yh1, 
			 uoff, 
			 voff, 
			 uoff + su, 
			 voff + tv, 
			 r,g,b,a );
		// top right
		spriteOnBox( xh2, 
			 y1, 
			 x2, 
			 yh1, 
			 uoff + su, 
			 voff, 
			 uoff + uwidth, 
			 voff + tv, 
			 r,g,b,a );
		
		// bottom left
		spriteOnBox( x1, 
			 yh2, 
			 xh1, 
			 y2, 
			 uoff, 
			 voff + tv, 
			 uoff + su, 
			 voff + vheight, 
			 r,g,b,a );
		
		// bottom right
		spriteOnBox( xh2, 
			 yh2, 
			 x2, 
			 y2, 
			 uoff + su, 
			 voff + tv, 
			 uoff + uwidth, 
			 voff + vheight, 
			 r,g,b,a );
		
		// top border
		spriteOnBox( xh1, 
			 y1, 
			 xh2, 
			 yh1, 
			 uoff + su, 
			 voff, 
			 uoff + su, 
			 voff + tv, 
			 r,g,b,a );
		
		// bottom border
		spriteOnBox( xh1, 
			 yh2, 
			 xh2, 
			 y2, 
			 uoff + su, 
			 voff + tv, 
			 uoff + su, 
			 voff + vheight, 
			 r,g,b,a );
		
		// left border
		spriteOnBox( x1, 
			 yh1, 
			 xh1, 
			 yh2, 
			 uoff, 
			 voff + tv, 
			 uoff + su, 
			 voff + tv, 
			 r,g,b,a );
		
		// right border
		spriteOnBox( xh2, 
			 yh1, 
			 x2, 
			 yh2, 
			 uoff + su, 
			 voff + tv, 
			 uoff + uwidth, 
			 voff + tv, 
			 r,g,b,a );
		
		// center
		spriteOnBox( xh1, 
			 yh1, 
		 	 xh2, 
		 	 yh2, 
		 	 uoff + su, 
		 	 voff + tv, 
		 	 uoff + su, 
		 	 voff + tv, 
		 	 r,g,b,a );
	
	}


	public void spriteOnLine( float x1,
							  float y1,
							  float x2,
							  float y2,
							  float thickness,
							  float r,
							  float g,
							  float b,
							  float a,
							  SpriteGL sprite )
	{
	
		float nx = -(y2 - y1);
		float ny =  (x2 - x1);
		
		float invLength = 1 / ( (float) Math.sqrt(nx * nx + ny * ny ) );
		
		nx *= invLength;
		ny *= invLength;
		
		nx *= thickness / 2;
		ny *= thickness / 2;
		
		float vx = (x2 - x1);
		float vy = (y2 - y1);
		invLength = 1 / ( (float) Math.sqrt(vx * vx + vy * vy ) );
		vx *= invLength;
		vy *= invLength;
		
		vx *= thickness / 2;
		vy *= thickness / 2;
		
		
		float lx1 = x2 + nx;
		float ly1 = y2 + ny;
		float lx2 = x2 - nx;
		float ly2 = y2 - ny;                      
		float lx3 = x1 - nx;
		float ly3 = y1 - ny;
		float lx4 = x1 + nx;
		float ly4 = y1 + ny;
		float lx5 = lx1 + vx;
		float ly5 = ly1 + vy;
		float lx6 = lx2 + vx;
		float ly6 = ly2 + vy;
		float lx7 = lx4 - vx;
		float ly7 = ly4 - vy;
		float lx8 = lx3 - vx;
		float ly8 = ly3 - vy;
		
		float u1 = sprite.u1;
		float v1 = sprite.v1;
		float u2 = sprite.u2;
		float v2 = sprite.v2;
		float su = (u1 + u2) / 2;
		
		// center
		spriteOnQuad( lx1, ly1, 
			  lx2, ly2, 
			  lx3, ly3, 
			  lx4, ly4, 
			  su, v2, 
			  su, v1, 
			  r, g, b, a  );
		
		// right
		spriteOnQuad( lx5, ly5, 
			  lx6, ly6, 
			  lx2, ly2, 
			  lx1, ly1, 
			  u2, v2, 
			  su, v1, 
			  r, g, b, a  );
		
		// left
		spriteOnQuad( lx8, ly8, 
			  lx7, ly7, 
			  lx4, ly4, 
			  lx3, ly3,
			  u1, v1, 
			  su, v2, 
			  r, g, b, a  );
	
	}

	
	public void spriteOnBox( float x, 
							 float y, 
							 float width, 
							 float height, 
							 float r, 
							 float g, 
							 float b, 
							 float a,
							 int flipmode, 
							 SpriteGL sprite )
	{
	
		float x1 = x;
		float y1 = y;
		float x2 = x + width;
		float y2 = y + height;
		
		float u1 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u1 : sprite.u2;
		float v1 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v1 : sprite.v2;
		float u2 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u2 : sprite.u1;
		float v2 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v2 : sprite.v1;
		
		vertices[index++] = x1;
		vertices[index++] = y1;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v1;
		
		
		vertices[index++] = x1;
		vertices[index++] = y2;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v2;
		
		vertices[index++] = x2;
		vertices[index++] = y2;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v2;
		
		vertices[index++] = x2;
		vertices[index++] = y1;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v1;
		
		numSprites++; 
	
	}
	
	

	public void spriteOnBoxOffset( float x, 
								   float y, 
								   float width, 
								   float height, 
								   float r, 
								   float g, 
								   float b, 
								   float a,
								   float u,
								   float v,
								   float uWidth,
								   float vHeight,
								   int flipmode, 
								   SpriteGL sprite )
	{
	
		float x1 = x;
		float y1 = y;
		float x2 = x + width;
		float y2 = y + height;
		
		float u1 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u1 : sprite.u2;
		float v1 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v1 : sprite.v2;
		float u2 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u2 : sprite.u1;
		float v2 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v2 : sprite.v1;
		
		u2 = u1 + uWidth;
		v2 = v1 + vHeight; 
		
		vertices[index++] = x1;
		vertices[index++] = y1;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1 + u;
		vertices[index++] = v1 + v;
		
		
		vertices[index++] = x1;
		vertices[index++] = y2;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1 + u;
		vertices[index++] = v2 + v;
		
		vertices[index++] = x2;
		vertices[index++] = y2;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2 + u;
		vertices[index++] = v2 + v;
		
		vertices[index++] = x2;
		vertices[index++] = y1;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2 + u;
		vertices[index++] = v1 + v;
		
		numSprites++; 
	
	}
	
	
	public void spriteOnQuad( float x1, 
						   	  float y1, 
							  float x2, 
							  float y2,
							  float x3, 
							  float y3,
							  float x4, 
							  float y4,
							  float r, 
							  float g, 
							  float b, 
							  float a,
							  int flipmode, 
							  SpriteGL sprite )
	{
	
		float u1 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u1 : sprite.u2;
		float v1 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v1 : sprite.v2;
		float u2 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u2 : sprite.u1;
		float v2 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v2 : sprite.v1;
		
		vertices[index++] = x1;
		vertices[index++] = y1;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v1;
		
		
		vertices[index++] = x2;
		vertices[index++] = y2;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v2;
		
		vertices[index++] = x3;
		vertices[index++] = y3;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v2;
		
		vertices[index++] = x4;
		vertices[index++] = y4;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v1;
		
		numSprites++; 
	
	}
	
	public void spriteOnQuadClipped( float x1, 
							  		 float y1, 
							  		 float x2, 
							  		 float y2,
							  		 float x3, 
							  		 float y3,
							  		 float x4, 
							  		 float y4,
							  		 float cu1,
							  		 float cv1,
							  		 float cu2,
							  		 float cv2,
							  		 float r, 
							  		 float g, 
							  		 float b, 
							  		 float a,
							  		 int flipmode, 
									 SpriteGL sprite )
	{
	
		float u1 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u1 : sprite.u2;
		float v1 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v1 : sprite.v2;
		float u2 = ( (flipmode & SpriteGL.FLIP_H) == 0 ) ? sprite.u2 : sprite.u1;
		float v2 = ( (flipmode & SpriteGL.FLIP_V) == 0 ) ? sprite.v2 : sprite.v1;
		
		u1 += cu1;
		v1 += cv1;
		
		u2 += cu2;
		v2 += cv2;
		
		vertices[index++] = x1;
		vertices[index++] = y1;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v1;
		
		
		vertices[index++] = x2;
		vertices[index++] = y2;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v2;
		
		vertices[index++] = x3;
		vertices[index++] = y3;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v2;
		
		vertices[index++] = x4;
		vertices[index++] = y4;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v1;
		
		numSprites++; 
	
	}
	
	public void sprite3D(float x, float y, float z, int flipMode, SpriteGL sprite)
	{
		//spriteOnQuad({x,y}, {x,y}, {x,y}, {x,y}, {r,g,b,a}, flipMode, sprite);
		float x1 = x;
		float y1 = y;
		float x2 = x + sprite.width;
		float y2 = y + sprite.height;
		
		spriteOnQuad(x1, y1, 
					 x1, y2, 
					 x2, y2, 
					 x2, y1, 
					 255, 255, 255, 255, 
					 flipMode, 
					 sprite);
	}
	
	public void spriteRotateScaleXY3D(int x, int y, int z, int angle, float scaleX, float scaleY, int flipMode, SpriteGL sprite)
	{
		int sHalfX = sprite.width / 2;
		int sHalfY = sprite.height / 2;
		int x1 = -sHalfX;
		int y1 = -sHalfY;
		int x2 = sHalfX;
		int y2 = sHalfY;
		
		
	}
	
	public void render(GL10 gl, int textureID)
	{
		if(numSprites < 1) 
			return;
		
		gl.glBindTexture( GL10.GL_TEXTURE_2D, textureID );
		fillBuffers();
		
		gl.glEnableClientState( GL10.GL_VERTEX_ARRAY );
		vertexBuffer.position(0);
		gl.glVertexPointer( 2, GL10.GL_FLOAT, 8 << 2, vertexBuffer );
		
		gl.glEnableClientState( GL10.GL_COLOR_ARRAY );
		vertexBuffer.position(2);
		gl.glColorPointer( 4, GL10.GL_FLOAT, 8 << 2, vertexBuffer );
		
		gl.glEnableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
		vertexBuffer.position(6);
		gl.glTexCoordPointer( 2, GL10.GL_FLOAT, 8 << 2, vertexBuffer );
		
		vertexBuffer.position(0);
		indexBuffer.position(0);
		
		gl.glDrawElements( GL10.GL_TRIANGLES, numSprites * 6, GL10.GL_UNSIGNED_SHORT, indexBuffer );
		gl.glDisableClientState( GL10.GL_TEXTURE_COORD_ARRAY );
		gl.glDisableClientState( GL10.GL_COLOR_ARRAY );
		gl.glDisableClientState( GL10.GL_VERTEX_ARRAY );
		
	    this.index = 0;
	    this.numSprites = 0;
		
		vertexBuffer.clear();
	}
	
	public static void ClearScreen(GL10 gl)
	{
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
	}
	
	public static void Begin2D(GL10 gl)
	{
		gl.glDisable(GL10.GL_DEPTH_TEST);
		gl.glDisable (GL10.GL_CULL_FACE);

		gl.glEnable(GL10.GL_BLEND);   	    
		gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);

		gl.glDisable( GL10.GL_DEPTH_TEST );

		gl.glEnable( GL10.GL_ALPHA_TEST );
		gl.glAlphaFunc(GL10.GL_GREATER, 0);


		gl.glDisable(GL10.GL_STENCIL_TEST);
		gl.glDisable(GL10.GL_TEXTURE);
		gl.glDisable(GL10.GL_LIGHTING);
		//gl.glDisable(GL10.GL_LOGIC_OP);
		gl.glDisable(GL10.GL_DITHER);
		gl.glDisable(GL10.GL_FOG);

		gl.glHint(GL10.GL_POINT_SMOOTH_HINT, GL10.GL_FASTEST);
		gl.glHint(GL10.GL_LINE_SMOOTH_HINT , GL10.GL_FASTEST);

		gl.glPointSize( 1 );
		gl.glLineWidth( 1 );

		gl.glMatrixMode(GL10.GL_PROJECTION); 
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glOrthof(0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT, 0, -1, 1);
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glPushMatrix();
		gl.glLoadIdentity();
		gl.glTranslatef(0.375f, 0.375f, 0f);
	}
	
	public static void End2D(GL10 gl)
	{
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glPopMatrix();
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		gl.glPopMatrix();

		SetBlendMode(gl, GLBlendMode.BLEND_SOLID);
		gl.glColor4x(255,255,255,255);
	}
	
	public static void SetBlendMode(GL10 gl, GLBlendMode blendMode)
	{
		if(blendMode == GLBlendMode.BLEND_TRANS)
		{
			gl.glDisable(GL10.GL_BLEND);
			gl.glEnable(GL10.GL_ALPHA_TEST);
		}
		else if(blendMode == GLBlendMode.BLEND_SOLID)
		{
			gl.glDisable(GL10.GL_BLEND);
			gl.glDisable(GL10.GL_ALPHA_TEST);
		}
		else if(blendMode == GLBlendMode.BLEND_BLENDED)
		{
			gl.glEnable(GL10.GL_BLEND);
			gl.glEnable(GL10.GL_ALPHA_TEST);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		}
		else if(blendMode == GLBlendMode.BLEND_GLOW)
		{
			gl.glEnable(GL10.GL_BLEND);
			gl.glEnable(GL10.GL_ALPHA_TEST);
			gl.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
		}
		else if(blendMode == GLBlendMode.BLEND_ALPHA)
		{
			gl.glEnable(GL10.GL_BLEND);
			gl.glEnable(GL10.GL_ALPHA_TEST);
			gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		}
		else if(blendMode == GLBlendMode.BLEND_BLACK)
		{
			gl.glEnable(GL10.GL_BLEND);
			gl.glEnable(GL10.GL_ALPHA_TEST);
			gl.glBlendFunc(GL10.GL_ZERO, GL10.GL_ONE_MINUS_SRC_ALPHA);
		}
		else
		{
			gl.glDisable(GL10.GL_BLEND);
			gl.glEnable(GL10.GL_ALPHA_TEST);
		}
	}

	public int getNumSprites()
	{
		return numSprites;
	}

	public void setNumSprites(int numSprites)
	{
		this.numSprites = numSprites;
	}
	
	public enum GLBlendMode {
		BLEND_TRANS, 
		BLEND_SOLID,
		BLEND_BLENDED,
		BLEND_GLOW,
		BLEND_ALPHA,
		BLEND_BLACK
	}

	// ************************************************************************
	// Privates
	// ************************************************************************
	
	private void spriteOnBox( float x1, 
							  float y1, 
							  float x2, 
							  float y2,
							  float u1,
							  float v1,
							  float u2,
							  float v2,
							  float r, 
							  float g, 
							  float b, 
							  float a )
	{
	
		vertices[index++] = x1;
		vertices[index++] = y1;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v1;
		
		
		vertices[index++] = x1;
		vertices[index++] = y2;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v2;
		
		vertices[index++] = x2;
		vertices[index++] = y2;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v2;
		
		vertices[index++] = x2;
		vertices[index++] = y1;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v1;

		numSprites++; 
	
	}

	private void spriteOnQuad( float x1, 
							   float y1, 
							   float x2, 
							   float y2,
							   float x3, 
							   float y3,
							   float x4, 
							   float y4,
							   float u1,
							   float v1,
							   float u2,
							   float v2,
							   float r, 
							   float g, 
							   float b, 
							   float a )
	{
	
		vertices[index++] = x1;
		vertices[index++] = y1;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v1;
		
		
		vertices[index++] = x2;
		vertices[index++] = y2;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u1;
		vertices[index++] = v2;
		
		vertices[index++] = x3;
		vertices[index++] = y3;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v2;
		
		vertices[index++] = x4;
		vertices[index++] = y4;
		vertices[index++] = r;
		vertices[index++] = g;
		vertices[index++] = b;
		vertices[index++] = a;
		vertices[index++] = u2;
		vertices[index++] = v1;
		
		numSprites++; 
		
	}

	private void fillBuffers()
	{
		int size = numSprites * vertexStride; 
	    for( int i = 0; i < size; i++ )
	    {
	        tempBuffer[i] = Float.floatToRawIntBits(vertices[i]); 
	    }
	    vertexBuffer.put( tempBuffer, 0, numSprites * vertexStride );
		vertexBuffer.flip();
			
	}
		
		

}   // end class
