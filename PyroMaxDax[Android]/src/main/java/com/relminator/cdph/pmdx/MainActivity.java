package com.relminator.cdph.pmdx;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity
{
	private PyromaxDax mainGame;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Holo_NoActionBar_Fullscreen);
		
		mainGame = new PyromaxDax(this);
        setContentView(mainGame);
    }

	@Override
	protected void onResume()
	{
		super.onResume();
		mainGame.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		mainGame.onPause();
	}
} 
