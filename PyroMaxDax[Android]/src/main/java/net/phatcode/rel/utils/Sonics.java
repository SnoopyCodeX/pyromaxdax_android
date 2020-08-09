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

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.util.SparseIntArray;

public class Sonics
{
	
	
	private static Sonics instance = new Sonics();

	int numSfxs = 0;
	int numBgms = 0;
	
	MediaPlayer music;
	
	SoundPool soundPool;
	
	private SparseIntArray effects;

	private Sonics()
	{
		effects = new SparseIntArray(32);
		this.soundPool = new SoundPool( 20, AudioManager.STREAM_MUSIC, 0 );
	}
	
	public static Sonics getInstance()
	{
		return instance;
	}
	
	public void loadMusic( Context context, int resID )
	{
		numBgms++;
		music = MediaPlayer.create(context, resID );
		music.setLooping( true );
		music.start();
				
	}
	
	public void loadEffect( Context context, int resID, int soundID )
	{
		numSfxs++;
		effects.put( soundID, soundPool.load(context, resID, 0 ) );
	}
	
	public void stopMusic()
	{
		music.stop();
	}
	
	public void playEffect( int soundID )
	{
		soundPool.play( effects.get(soundID), 1.0f, 1.0f, 0, 0, 1 ); 
	}
	
	public void shutDown()
	{
		
		music.stop();
		music.release();
		music = null;
		
		soundPool.release();
		
	}

	public int getNumSfxs()
	{
		return numSfxs;
	}

	public int getNumBgms()
	{
		return numBgms;
	}
	
	
}
