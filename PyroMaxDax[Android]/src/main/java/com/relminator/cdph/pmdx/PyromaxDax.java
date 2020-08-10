package com.relminator.cdph.pmdx;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.opengl.GLSurfaceView;
import android.opengl.GLSurfaceView.Renderer;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import com.relminator.cdph.pmdx.engine.Engine;
import com.relminator.cdph.pmdx.object.Globals;
import net.phatcode.rel.utils.VirtualViewport;

public class PyromaxDax extends GLSurfaceView implements Renderer
{
	private VirtualViewport viewport = new VirtualViewport();
	private Engine engine;
	private Context context;
	private GL10 glContext;
	
	public PyromaxDax(Context ctx)
	{
		super(ctx);
		
		this.initSelf(ctx);
	}
	
	public PyromaxDax(Context ctx, AttributeSet attrs)
	{
		super(ctx, attrs);
		
		this.initSelf(ctx);
	}

	@Override
	public void onDrawFrame(GL10 gl)
	{
		engine.update(gl);
		engine.drawBatchers(gl);
	}

	@Override
	public void onSurfaceCreated(GL10 gl, EGLConfig config)
	{
		glContext = gl;
		
		engine.loadTextures(gl);
		
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glShadeModel(GL10.GL_SMOOTH); 			
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f); 	
		gl.glClearDepthf(1.0f); 					
		gl.glDisable(GL10.GL_DEPTH_TEST); 			
		gl.glDepthFunc(GL10.GL_LEQUAL); 			
		gl.glEnable(GL10.GL_BLEND);
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST); 
	}

	@Override
	public void onSurfaceChanged(GL10 gl, int width, int height)
	{
		height = (height == 0) ? 1 : height;
		
		engine.getTouchHandler().init(width, height);
		viewport.initialize(Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT, width, height);
		gl.glViewport(viewport.getCropX(), viewport.getCropY(), viewport.getWidth(), viewport.getHeight());
		gl.glMatrixMode(GL10.GL_PROJECTION);
		gl.glLoadIdentity(); 					
		gl.glOrthof(0, Globals.SCREEN_WIDTH, Globals.SCREEN_HEIGHT, 0, 1, -1);
		gl.glMatrixMode(GL10.GL_MODELVIEW); 	
		gl.glLoadIdentity(); 	
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		return true;
	}
	
	private void initSelf(Context ctx)
	{
		this.engine = new Engine(ctx);
		this.setRenderer(this);
		this.requestFocus();
		this.setFocusableInTouchMode(true);
		this.setOnTouchListener(engine.getTouchHandler());
		
		this.context = ctx;
	}
	
	public void shutDown()
	{
		engine.shutDown(glContext);
	}
}
