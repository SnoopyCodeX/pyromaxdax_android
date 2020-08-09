''*****************************************************************************
''
''
''	Pyromax Dax Watcher Class
''	Richard Eric M. Lope
''	http://rel.phatcode.net
''
''	
''
''*****************************************************************************

#include once "Watcher.bi"

const as integer MAX_IDLE_COUNTER = 60 * 5
const as integer MAX_FIRE_COUNTER = 60

constructor Watcher()

	Active = FALSE
	Counter = 0 
	FireCounter = MAX_FIRE_COUNTER
	IdleCounter = MAX_IDLE_COUNTER
		
	Orientation = O_LEFT
	State = STATE_IDLE
	
	Speed = 0.5	
	x = 0
	y = 0
	Dx = 0
	Dy = 0
	OldDy = Dy

	Frame = 0
	BaseFrame = 193
	NumFrames = 1
	
	Wid = 24
	Hei	= 68
	
	BoxNormal.Init( x, y, wid, Hei)
	
End Constructor

destructor Watcher()

End Destructor


property Watcher.IsActive() as integer
	property = Active
End Property
		
property Watcher.GetX() as single
	property = x
End Property

property Watcher.GetY() as single
	property = y
End Property

property Watcher.GetDrawFrame() as integer
	property = (BaseFrame + Frame)
End Property

property Watcher.GetBox() as AABB
	property = BoxNormal
End Property

''*****************************************************************************
''
''*****************************************************************************
sub Watcher.ActionIdle( Map() as TileType )
	
	if( Dy < 0 ) then
		if( MapUtil.GetTile(x, y - Hei\2, Map() ) >= TILE_SOLID  ) then
			Dy = -Dy
		else
			y += Dy
		endif
	else
		if( MapUtil.GetTile(x, y + Hei\2, Map() ) >= TILE_SOLID  ) then
			Dy = -Dy
		else
			y += Dy
		endif

	endif
	
	IdleCounter -= 1
	if( IdleCounter <= 0 ) then
		IdleCounter = MAX_IDLE_COUNTER
		State = STATE_SCAN
		ResolveAnimationParameters()
		Dy = OldDy
	endif
	
	
end sub

sub Watcher.ActionScan( Map() as TileType )
	
	if( Dy < 0 ) then
		if( MapUtil.GetTile(x, y - Hei\2, Map() ) >= TILE_SOLID  ) then
			Dy = -Dy
		else
			y += Dy * 1.5
		endif
	else
		if( MapUtil.GetTile(x, y + Hei\2, Map() ) >= TILE_SOLID  ) then
			Dy = -Dy
		else
			y += Dy * 1.5
		endif
	endif
	
	
end sub

sub Watcher.ActionAttack( byref Bullets as BulletFactory, Map() as TileType )
	
	Dx = 0
	Dy = 0
	
	FireCounter -= 1
	if( ((Firecounter and 7) = 0) ) then
		Sound.PlaySFX( Sound.SFX_ENEMY_SHOT_02 )
		if( Orientation = O_LEFT ) then
			Bullets.Spawn( x, y - HEI\2 - 4, 180, 5.5, Bullet.STATE_NORMAL, Bullet.ID_MISSILE )
			Bullets.Spawn( x, y + HEI\2 + 4, 180, 5.5, Bullet.STATE_NORMAL, Bullet.ID_MISSILE )
		else
			Bullets.Spawn( x, y - HEI\2 - 4,   0, 5.5, Bullet.STATE_NORMAL, Bullet.ID_MISSILE )
			Bullets.Spawn( x, y + HEI\2 + 4,   0, 5.5, Bullet.STATE_NORMAL, Bullet.ID_MISSILE )
		endif
		
	endif
	
	if( FireCounter <= 0 ) then
		FireCounter = MAX_FIRE_COUNTER
		State = STATE_IDLE
		ResolveAnimationParameters()
		Dy = OldDy
	endif
	
end sub


	
sub Watcher.Spawn( byval ix as integer, byval iy as integer, byval iOrientation as integer )
	
	Active = TRUE
	Counter = 0 
	FireCounter = MAX_FIRE_COUNTER
	IdleCounter = MAX_IDLE_COUNTER

	BlinkCounter = -1
	Hp = 100
		
	State = STATE_IDLE

	Speed = 0.5
	x = ix + TILE_SIZE\2
	y = iy + TILE_SIZE\2
		
	Dx = 0
	if( iOrientation = 0 ) then
		Dy = -Speed
	else
		Dy = Speed
	endif
	
	OldDy = Dy
	
	Frame = 0
	BaseFrame = 193
	NumFrames = 1
	
	Wid = 24
	Hei	= 32

	ResolveAnimationParameters()
	
	BoxNormal.Init( x, y, wid, Hei)
	
End Sub


sub Watcher.Update( byref Snipe as Player, byref Bullets as BulletFactory, Map() as TileType )
	
	if( (abs(x - (Snipe.GetCameraX + SCREEN_WIDTH\2)) \ TILE_SIZE) > 12  ) then return
	
	Counter + = 1
	
	if( (Counter and 15) = 0 ) then
		Frame = ( Frame + 1 ) mod NumFrames
	endif	
	
	if( BlinkCounter > 0 ) then
		BlinkCounter -= 1
	endif
	
	if( Snipe.GetX > x ) then
		Orientation = O_RIGHT
		Flipmode = GL2D.FLIP_H
	else
		Orientation = O_LEFT
		Flipmode = GL2D.FLIP_NONE
	endif
	
	select case State
		case STATE_IDLE:
			ActionIdle( Map() )
		case STATE_SCAN:
			ActionScan( Map() )
			dim as single Dist = ((Snipe.GetX - x) ^ 2)  + ((Snipe.GetY - y) ^ 2)
			if( (abs((Snipe.Gety + Snipe.GetHei\2) - y) <= 8) and (Dist <= (( TILE_SIZE * 6) ^ 2)) ) then
				State = STATE_ATTACK
				ResolveAnimationParameters()
				OldDy = Dy
			endif
		case STATE_ATTACK:
			ActionAttack( Bullets, Map() )
	end select	
	
	BoxNormal.Init( x - Wid\2, y - Hei\2, Wid, Hei)
 
			
end sub

sub Watcher.ResolveAnimationParameters()
	
	select case State
		case STATE_IDLE:
			Frame = 0
			BaseFrame = 193
			NumFrames = 1		
			Hei = 28	
		case STATE_SCAN:
			Frame = 0
			BaseFrame = 193
			NumFrames = 2
			Hei = 60
		case STATE_ATTACK:
			Frame = 0
			BaseFrame = 194
			NumFrames = 1
			Hei = 60
	end select	
	
end sub

sub Watcher.Explode()
	
	Explosion.SpawnMulti( Vector3D(x + Wid\2, y + Hei\2, 2), 2, rnd * 360, Explosion.BIG_YELLOW, Explosion.MEDIUM_YELLOW_02, 8 )	
	
	Kill()
	
End Sub

sub Watcher.Kill()
	
	Active = FALSE
	x = 0
	y = 0
	
	BoxNormal.Init( -10000, -1000, wid, Hei)
	
End Sub

sub Watcher.Draw( SpriteSet() as GL2D.IMAGE ptr )
	
	if( (BlinkCounter > 0)  and ((BlinkCounter and 3) = 0) ) then
		GL2D.EnableSpriteStencil( TRUE, GL2D_RGBA(255,255,255,255), GL2D_RGBA(255,255,255,255) )
		GL2D.SpriteRotateScaleXY3D( x, y, -4, 0, 1, 1, FlipMode, SpriteSet( BaseFrame + Frame ) )
		GL2D.EnableSpriteStencil( FALSE )
	else
		GL2D.SpriteRotateScaleXY3D( x, y, -4, 0, 1, 1, FlipMode, SpriteSet( BaseFrame + Frame ) )
	endif

End Sub

sub Watcher.DrawAABB()
	
	BoxNormal.Draw( 4, GL2D_RGB( 255, 0, 255 ) )
	
end sub


sub Watcher.CollideWithPlayer( byref Snipe as Player )
	
	if( (not Snipe.IsDead) and (not Snipe.IsInvincible) ) then	
		dim as AABB Box = Snipe.GetBoxSmall
		if( BoxNormal.Intersects(Box) ) then
			Snipe.HitAnimation( x, 65 )
		endif		
	endif
	
	dim as integer AttackEnergy = 0
	
	AttackEnergy = Snipe.CollideShots( BoxNormal )
	if( AttackEnergy ) then
		Hp -= 36
		BlinkCounter = MAX_ENEMY_BLINK_COUNTER
		if( Hp <= 0 ) then
			Explode()
			Snipe.AddToScore( 305 )
		endif	
	endif
	
	AttackEnergy = Snipe.CollideBombs( BoxNormal )
	if( AttackEnergy ) then
		Explode()
		Snipe.AddToScore( 305 )
	endif
	
	AttackEnergy = Snipe.CollideDynamites( BoxNormal )
	if( AttackEnergy ) then
		Explode()
		Snipe.AddToScore( 305 )
	endif
	
	AttackEnergy = Snipe.CollideMines( BoxNormal )
	if( AttackEnergy ) then
		Explode()
		Snipe.AddToScore( 305 )
	endif
	
End Sub

''*****************************************************************************
'' Collides the object box with the tiles on the y-axis
''*****************************************************************************



function Watcher.CollideWithAABB( byref Box as const AABB ) as integer

	if( BoxNormal.Intersects( Box ) ) then
		return TRUE
	EndIf

	return FALSE

End Function



''*****************************************************************************
''
'' WatcherFactory
''
''*****************************************************************************

constructor WatcherFactory()

	ActiveEntities = 0
	
	for i as integer = 0 to ubound(Watchers)
		Watchers(i).Kill()
	Next
	
End Constructor

destructor WatcherFactory()

End Destructor

property WatcherFactory.GetActiveEntities() as integer
	property = ActiveEntities
end property 

property WatcherFactory.GetMaxEntities() as integer
	property = ubound(Watchers)
end property 

sub WatcherFactory.UpdateEntities( byref Snipe as Player, byref Bullets as BulletFactory, Map() as TileType )
	
	ActiveEntities = 0
	for i as integer = 0 to ubound(Watchers)
		if( Watchers(i).IsActive ) then
			Watchers(i).Update( Snipe, Bullets, Map() )
			ActiveEntities += 1
		EndIf
	Next
	
end sub

sub WatcherFactory.DrawEntities( SpriteSet() as GL2D.IMAGE ptr )
	
	for i as integer = 0 to ubound(Watchers)
		if( Watchers(i).IsActive ) then
			Watchers(i).Draw( SpriteSet() )
		EndIf
	Next
	
end sub

sub WatcherFactory.DrawCollisionBoxes()
	
	for i as integer = 0 to ubound(Watchers)
		if( Watchers(i).IsActive ) then
			Watchers(i).DrawAABB()
		EndIf
	Next
	
end sub

sub WatcherFactory.KillAllEntities()
	
	for i as integer = 0 to ubound(Watchers)
		if( Watchers(i).IsActive ) then
			Watchers(i).Kill()
		EndIf
	Next
	ActiveEntities = 0
	
end sub

sub WatcherFactory.HandleCollisions( byref Snipe as Player )
	
	for i as integer = 0 to ubound(Watchers)
		
		if( Watchers(i).IsActive ) then
			Watchers(i).CollideWithPlayer( Snipe )
		endif
		
	Next i
	
	
end sub

sub WatcherFactory.Spawn( byval ix as integer, byval iy as integer, byval iOrientation as integer )

	
	for i as integer = 0 to ubound(Watchers)
		if( Watchers(i).IsActive = FALSE ) then
			Watchers(i).Spawn( ix, iy, iOrientation )
			exit for
		EndIf
	Next
	
end sub

function WatcherFactory.GetAABB( byval i as integer ) as AABB
	
	return Watchers(i).GetBox
	
End Function
	