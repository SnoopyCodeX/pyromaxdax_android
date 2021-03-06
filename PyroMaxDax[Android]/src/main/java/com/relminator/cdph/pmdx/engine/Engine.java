package com.relminator.cdph.pmdx.engine;

import android.content.Context;
import javax.microedition.khronos.opengles.GL10;

import com.relminator.cdph.pmdx.R;
import com.relminator.cdph.pmdx.entity.Player;
import com.relminator.cdph.pmdx.object.Camera;
import com.relminator.cdph.pmdx.object.Globals;
import com.relminator.cdph.pmdx.object.Map;
import com.relminator.cdph.pmdx.object.Particles;
import com.relminator.cdph.pmdx.object.Tile3D;

import net.phatcode.rel.utils.AABB;
import net.phatcode.rel.utils.AndroidFileIO;
import net.phatcode.rel.utils.ImageAtlas;
import net.phatcode.rel.utils.Sonics;
import net.phatcode.rel.utils.SpriteBatcher;
import net.phatcode.rel.utils.SpriteFont;
import net.phatcode.rel.utils.SpriteGL;
import net.phatcode.rel.utils.TouchHandler;
import net.phatcode.rel.math.Utils;
import net.phatcode.rel.assets.ImageTextureDataDefault;

public class Engine
{
	public static final int BGM_BOSS = 0;
	public static final int BGM_COMPLETE = 1;
	public static final int BGM_CREDITS = 2;
	public static final int BGM_END = 3;
	public static final int BGM_GAMEOVER = 4;
	public static final int BGM_INTERMISSION = 5;
	public static final int BGM_INTRO = 6;
	public static final int BGM_TITLE = 7;
	public static final int BGM_LEVEL01 = 8;
	public static final int BGM_LEVEL02 = 9;
	public static final int BGM_LEVEL03 = 10;
	public static final int BGM_LEVEL04 = 11;
	
	public static final int SFX_ATTACK = 20;
	public static final int SFX_BOUNCE = 21;
	public static final int SFX_CLICK = 23;
	public static final int SFX_COIN = 24;
	public static final int SFX_DYNAMITE_LAUNCH = 25;
	public static final int SFX_ENEMY_SHOT01 = 26;
	public static final int SFX_ENEMY_SHOT02 = 27;
	public static final int SFX_EXPLODE = 28;
	public static final int SFX_GO = 29;
	public static final int SFX_HURT = 30;
	public static final int SFX_ICE_HIT = 31;
	public static final int SFX_JUMP = 32;
	public static final int SFX_LEVEL_COMPLETE = 33;
	public static final int SFX_MENU_OK = 34;
	public static final int SFX_METAL_HIT = 35;
	public static final int SFX_MINE_ACTIVE = 36;
	public static final int SFX_POWER_UP = 37;
	public static final int SFX_UP = 38;
	public static final int SFX_YAHOO = 39;
	
	private TouchHandler touchHandler = new TouchHandler();    // handles all touch
	private AndroidFileIO fileIO;
	private Context context;
	
	private ImageAtlas fontImages = new ImageAtlas();
	private ImageAtlas snipeImages = new ImageAtlas();
	private ImageAtlas tilesImages = new ImageAtlas();
	private ImageAtlas incendiariesImages = new ImageAtlas();
	private ImageAtlas guiImages = new ImageAtlas();
	private ImageAtlas seasonsImages = new ImageAtlas();
	private ImageAtlas splashesImages = new ImageAtlas();
	private ImageAtlas enemiesImages = new ImageAtlas();
	
	private SpriteFont fontSprite = new SpriteFont();
	private SpriteBatcher snipeSprite = new SpriteBatcher();
	private SpriteBatcher tilesSprite = new SpriteBatcher();
	private SpriteBatcher incendiariesSprite = new SpriteBatcher();
	private SpriteBatcher guiSprite = new SpriteBatcher();
	private SpriteBatcher seasonsSprite = new SpriteBatcher();
	private SpriteBatcher splashesSprite = new SpriteBatcher();
	private SpriteBatcher enemiesSprite = new SpriteBatcher();
	
	public enum GameState {
		STATE_PLAY, 
		STATE_PAUSE,
		STATE_START,
		STATE_END,
		STATE_WARP,
		STATE_STAGE_BOSS_COMPLETE,
		STATE_STAGE_BOSS_FAIL,
		STATE_GAME_OVER,
		STATE_OPTIONS,
		STATE_CREDITS,
		STATE_TITLE,
		STATE_YES_OR_NO,
		STATE_DIALOG,
		STATE_MOVE_TO_SPAWN_AREA,
		STATE_RESPAWN_PLAYER,
		STATE_INTERMISSION,
		STATE_RECORDS,
		STATE_STORY,
		STATE_SPLASH,
		STATE_EXIT
	}
	
	public enum Choices {
		CHOICE_START_GAME,
		CHOICE_OPTIONS,
		CHOICE_RECORDS,
		CHOICE_CREDITS,
		CHOICE_EXIT
	}
	
	public enum Seasons {
		SEASON_SUMMER,
		SEASON_FALL,
		SEASON_WINTER,
		SEASON_SPRING
	}
	
	public class WarpInfo
	{
		public int SpawnTileX;
		public int SpawnTileY;
		public int LevelNum;
		public Seasons SeasonType;
	}
	
	public class HighScore
	{
		public String name;
		public int score;
	}
	
	private GameState state;
	private GameState prevState;
	private GameState nextState;
	private Seasons currentSeason;
	private int currentLevel;
	private boolean isBossStage;
	private boolean bossActive;
	private int windDirection;
	private int windFrame;
	private int frame;
	private float secondsElapsed;
	private double fps;
	private double dt;
	private double accumulator;
	private Camera cam;
	private int spawnX;
	private int soawnY;
	private int oldPlayerX;
	private int oldPlayerY;
	private int bossSpawnX;
	private int bossSpawnY;
	private int bossDieX;
	private int bossDieY;
	private int mapWidth;
	private int mapHeight;
	private boolean showFps;
	private boolean noFrame;
	private int physicalScreenWidth;
	private int physicalScreenHeight;
	private boolean showDialogs;
	private int currentDialogID;
	private int currentWarpID;
	private int incendiaryMenuAngle;
	private int incendiaryMenuAnimate;
	private int pressedRight;
	private int pressedLeft;
	private int pressedUp;
	private int pressedDown;
	private int activeIncendiary;
	private int masterVolumeBGM;
	private int masterVolumeSFX;
	private int hiScore;
	private boolean debugMode;
	private boolean drawHitBoxes; 
	
	public Engine(Context ctx)
	{
		this.context = ctx;
		
		state = GameState.STATE_TITLE;
		currentLevel = 0;
		currentSeason = Seasons.SEASON_SUMMER;
		
		isBossStage = false;
		bossActive = false;
		
		frame = 0;
		secondsElapsed = 0;
		fps = 60;
		dt = 0;
		accumulator = 0;
		
		showFps = false;
		showDialogs = true;
		noFrame = false;
		physicalScreenWidth = Globals.SCREEN_WIDTH;
		physicalScreenHeight = Globals.SCREEN_HEIGHT;
		
		currentDialogID = 0;
		currentWarpID = 0;
		
		incendiaryMenuAngle = 0;
		incendiaryMenuAnimate = 0;
		
		masterVolumeBGM = 128;
		masterVolumeSFX = 200;
		
		debugMode = false;
		drawHitBoxes = false;
		
		this.initialize();
	}
	
	private void initialize()
	{
		state = GameState.STATE_SPLASH;
		prevState = GameState.STATE_TITLE;
		nextState = GameState.STATE_TITLE;
		
		currentLevel = 0;
		currentSeason = Seasons.SEASON_SUMMER;
		
		hiScore = 0;
		isBossStage = false;
		bossActive = false;
		windDirection = 0;
		windFrame = 0;
		
		frame = 0;
		secondsElapsed = 0;
		fps = 60;
		dt = 0;
		accumulator = 0;
		showDialogs = true;
		
		fileIO = new AndroidFileIO(context);
		Sonics.getInstance().loadEffect(context, R.raw.attack, SFX_ATTACK);
		Sonics.getInstance().loadEffect(context, R.raw.bounce, SFX_BOUNCE);
		Sonics.getInstance().loadEffect(context, R.raw.click, SFX_CLICK);
		Sonics.getInstance().loadEffect(context, R.raw.coin_up, SFX_COIN);
		Sonics.getInstance().loadEffect(context, R.raw.dynamite_launch, SFX_DYNAMITE_LAUNCH);
		Sonics.getInstance().loadEffect(context, R.raw.enemy_shot_01, SFX_ENEMY_SHOT01);
		Sonics.getInstance().loadEffect(context, R.raw.enemy_shot_02, SFX_ENEMY_SHOT02);
		Sonics.getInstance().loadEffect(context, R.raw.explode, SFX_EXPLODE);
		Sonics.getInstance().loadEffect(context, R.raw.go, SFX_GO);
		Sonics.getInstance().loadEffect(context, R.raw.hurt, SFX_HURT);
		Sonics.getInstance().loadEffect(context, R.raw.ice_hit, SFX_ICE_HIT);
		Sonics.getInstance().loadEffect(context, R.raw.jump, SFX_JUMP);
		Sonics.getInstance().loadEffect(context, R.raw.level_complete, SFX_LEVEL_COMPLETE);
		Sonics.getInstance().loadEffect(context, R.raw.menu_ok, SFX_MENU_OK);
		Sonics.getInstance().loadEffect(context, R.raw.metal_hit, SFX_METAL_HIT);
		Sonics.getInstance().loadEffect(context, R.raw.mine_active, SFX_MINE_ACTIVE);
		Sonics.getInstance().loadEffect(context, R.raw.power_up, SFX_POWER_UP);
		Sonics.getInstance().loadEffect(context, R.raw.up, SFX_UP);
		Sonics.getInstance().loadEffect(context, R.raw.yahoo, SFX_YAHOO);
	}
	
	public void setState(GameState state)
	{
		this.state = state;
	}
	
	public boolean update(GL10 gl)
	{
		boolean done = false;
		
		if(state == GameState.STATE_SPLASH)
			done = stateSplash(gl);
		//else if(state == GameState.STATE_TITLE)
			
		
		return done;
	}
	
	public void drawBatchers(GL10 gl)
	{
		
	}
	
	public void loadTextures(GL10 gl)
	{
		fontImages.loadTexture(gl, context, R.drawable.kroma256, new ImageTextureDataDefault(), 16, 16, GL10.GL_NEAREST);
		snipeImages.loadTexture(gl, context, R.drawable.snipe, new ImageTextureDataDefault(), 32, 32, GL10.GL_NEAREST);
		tilesImages.loadTexture(gl, context, R.drawable.tiles, new ImageTextureDataDefault(), 32, 32, GL10.GL_NEAREST);
		incendiariesImages.loadTexture(gl, context, R.drawable.incendiaries, new ImageTextureDataDefault(), 16, 16, GL10.GL_NEAREST);
		guiImages.loadTexture(gl, context, R.drawable.gui_sprite, new ImageTextureDataDefault(), GL10.GL_NEAREST);
		seasonsImages.loadTexture(gl, context, R.drawable.seasons_sprite, new ImageTextureDataDefault(), GL10.GL_NEAREST);
		splashesImages.loadTexture(gl, context, R.drawable.splashes, new ImageTextureDataDefault(), GL10.GL_NEAREST);
		enemiesImages.loadTexture(gl, context, R.drawable.enemies_sprite, new ImageTextureDataDefault(), GL10.GL_NEAREST);
		
		fontSprite.loadAtlas(fontImages, 32);
	}
	
	public void shutDown(GL10 gl)
	{
		//Shutdown bgm and sfx
		Sonics.getInstance().shutDown();
		
		//Shutdown gfx and textures
		fontImages.shutDown(gl);
		snipeImages.shutDown(gl);
		tilesImages.shutDown(gl);
		incendiariesImages.shutDown(gl);
		guiImages.shutDown(gl);
		seasonsImages.shutDown(gl);
		splashesImages.shutDown(gl);
		enemiesImages.shutDown(gl);
		fontSprite.shutDown(gl);
	}
	
	public TouchHandler getTouchHandler()
	{
		return touchHandler;
	}
	
	public boolean stateSplash(GL10 gl)
	{
		boolean animate = true;
		boolean done = false;
		int delay = 60 * 5;
		float t1 = 0;
		float t2 = 0;
		int numImages = 4;
		int myFrame = 0;
		int currentImage = 0;
		
		dt = Utils.getDeltaTime(fps, Utils.getSystemSeconds());
		
		do {
			dt = Utils.getDeltaTime(fps, Utils.getSystemSeconds());
			dt = (dt > Globals.FIXED_TIME_STEP) ? Globals.FIXED_TIME_STEP : dt;
			accumulator += dt;
			secondsElapsed += dt;
			
			while(accumulator >= Globals.FIXED_TIME_STEP)
			{
				frame += 1;
				myFrame += 1;
				
				if((myFrame % delay) == 0)
				{
					if(currentImage < 4)
					{
						currentImage += 1;
						animate = true;
					}
					else
						done = true;
				}
				
				if(animate)
				{
					t1 += 0.01;
					if(t1 >= 1)
					{
						animate = false;
						t1 = 0;
						done = (currentImage > 3);
					}
				}
				accumulator -= Globals.FIXED_TIME_STEP;
				
				SpriteBatcher.Begin2D(gl);
				
				
				
				SpriteBatcher.End2D(gl);
			}
		} while(done);
		
		return done;
	}
}
