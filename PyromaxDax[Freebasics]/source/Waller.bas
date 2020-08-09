''*****************************************************************************
''
''
''	Pyromax Dax Waller Class
''	Richard Eric M. Lope
''	http://rel.phatcode.net
''
''	
''
''*****************************************************************************

#include once "Waller.bi"

#define MAX_TURN_COUNTER 8

constructor Waller()

	Active = FALSE
	Counter = 0 
	FlipMode = GL2D.FLIP_NONE
	Orientation = NORMAL
	TurnCounter = MAX_TURN_COUNTER 
	State = STATE_FALLING
	
	Speed = 1	
	x = 0
	y = 0
	Dx = 0
	Dy = 1
	

	Frame = 0
	BaseFrame = 129
	NumFrames = 4
	
	Wid = 24
	Hei	= 24
	
	BoxNormal.Init( x, y, wid, Hei)
	
End Constructor

destructor Waller()

End Destructor


property Waller.IsActive() as integer
	property = Active
End Property
		
property Waller.GetX() as single
	property = x
End Property

property Waller.GetY() as single
	property = y
End Property

property Waller.GetDrawFrame() as integer
	property = (BaseFrame + Frame)
End Property

property Waller.GetBox() as AABB
	property = BoxNormal
End Property

''*****************************************************************************
''
''*****************************************************************************
sub Waller.ActionFalling( Map() as TileType )
	
	dim as integer TileX, TileY
	if( CollideFloors( int(x), int(y + Dy + Hei), TileY, Map() ) ) then	
		y = ( TileY ) * TILE_SIZE - Hei - 1
		if( Orientation = NORMAL ) then						
			Dy = 0
			Dx = -Speed
			State = STATE_MOVE_LEFT
			TurnCounter = MAX_TURN_COUNTER
		else
			Dy = 0
			Dx = Speed
			State = STATE_MOVE_RIGHT
			TurnCounter = MAX_TURN_COUNTER
		endif
	else
		y += Dy												
		Dy += GRAVITY
	endif
	
	Rotation = 0
		
end sub

sub Waller.ActionMoveRightNormal( Map() as TileType )
	
	dim as integer TileX, TileY
	if( CollideWalls( int(x + Dx + Wid), int(y), TileX, Map() ) ) then
		''x = TileX * TILE_SIZE - Wid
		Dx = 0
		Dy = Speed
		State = STATE_MOVE_DOWN
		TurnCounter = MAX_TURN_COUNTER							
	else
		x += Dx
		if( not CollideFloors( int(x), int(y - Speed), TileY, Map() ) ) then
			Dx = 0
			Dy = -Speed
			State = STATE_MOVE_UP
			FallDown()   '' if there is no adjacent tiles then fall
			'CheckDiagonalTiles( -TILE_SIZE\2, -TILE_SIZE\2, Map() )   '' Check for adjacent tiles so we fall down when the tile is destroyed
			TurnCounter = MAX_TURN_COUNTER
		endif
	endif

	Rotation = 180
end sub

sub Waller.ActionMoveLeftNormal( Map() as TileType )
	
	dim as integer TileX, TileY
	if( CollideWalls( int(x + Dx), int(y), TileX, Map() ) ) then
		''x = ( TileX + 1 ) * TILE_SIZE
		Dx = 0
		Dy = -Speed
		State = STATE_MOVE_UP
		TurnCounter = MAX_TURN_COUNTER
	else
		x += Dx
		if( not CollideFloors( int(x), (y + Speed + Hei), TileY, Map() ) ) then	
			Dx = 0
			Dy = Speed
			State = STATE_MOVE_DOWN
			TurnCounter = MAX_TURN_COUNTER
		endif 	
		
	endif
	
	Rotation = 0
		
end sub

sub Waller.ActionMoveUpNormal( Map() as TileType )

	dim as integer TileX, TileY
	if( CollideFloors( int(x), int(y + Dy), TileY, Map() ) ) then
		''y = ( TileY + 1 ) * TILE_SIZE						
		Dy = 0
		Dx = Speed
		State = STATE_MOVE_RIGHT
		TurnCounter = MAX_TURN_COUNTER    												
	else
		y += Dy
		if( not CollideWalls( int(x - Speed), int(y), TileX, Map() ) ) then
			Dx = -Speed
			Dy = 0
			State = STATE_MOVE_LEFT
			TurnCounter = MAX_TURN_COUNTER
		endif													
	endif
	
	Rotation = 90
	
end sub

sub Waller.ActionMoveDownNormal( Map() as TileType )
	
	dim as integer TileX, TileY
	if( CollideFloors( int(x), int(y + Dy + Hei), TileY, Map() ) ) then	
		''y = ( TileY ) * TILE_SIZE - Hei						
		Dy = 0
		Dx = -Speed
		State = STATE_MOVE_LEFT
		TurnCounter = MAX_TURN_COUNTER
	else
		y += Dy
		if( not CollideWalls( int(x + Speed + Wid), int(y), TileX, Map() ) ) then
			Dx = Speed
			Dy = 0
			State = STATE_MOVE_RIGHT
			TurnCounter = MAX_TURN_COUNTER
		endif												
	endif
	
	Rotation = 270
	
end sub

''*****************************************************************************
''
''*****************************************************************************
sub Waller.ActionMoveRightReverse( Map() as TileType )
	
	dim as integer TileX, TileY
	if( CollideWalls( int(x + Dx + Wid), int(y), TileX, Map() ) ) then
		''x = TileX * TILE_SIZE - Wid
		Dx = 0
		Dy = -Speed
		State = STATE_MOVE_UP
		TurnCounter = MAX_TURN_COUNTER							
	else
		x += Dx
		if( not CollideFloors( int(x), (y + Speed + Hei), TileY, Map() ) ) then
			Dx = 0
			Dy = Speed
			State = STATE_MOVE_DOWN
			FallDown()   '' if there is no adjacent tiles then fall
			'CheckDiagonalTiles( -TILE_SIZE\2, TILE_SIZE\2, Map() )   '' Check for adjacent tiles so we fall down when the tile is destroyed
			TurnCounter = MAX_TURN_COUNTER
		endif
	endif

	Rotation = 0
end sub

sub Waller.ActionMoveLeftReverse( Map() as TileType )
	
	dim as integer TileX, TileY
	if( CollideWalls( int(x + Dx), int(y), TileX, Map() ) ) then
		''x = ( TileX + 1 ) * TILE_SIZE
		Dx = 0
		Dy = Speed
		State = STATE_MOVE_DOWN
		TurnCounter = MAX_TURN_COUNTER
	else
		x += Dx
		if( not CollideFloors( int(x), int(y - Speed), TileY, Map() ) ) then	
			Dx = 0
			Dy = -Speed
			State = STATE_MOVE_UP
			TurnCounter = MAX_TURN_COUNTER
		endif 												
	endif
	
	Rotation = 180
		
end sub

sub Waller.ActionMoveUpReverse( Map() as TileType )

	dim as integer TileX, TileY
	if( CollideFloors( int(x), int(y + Dy), TileY, Map() ) ) then
		''y = ( TileY + 1 ) * TILE_SIZE						
		Dy = 0
		Dx = -Speed
		State = STATE_MOVE_LEFT    												
		TurnCounter = MAX_TURN_COUNTER
	else
		y += Dy
		if( not CollideWalls( int(x + Speed + Wid), int(y), TileX, Map() ) ) then
			Dx = Speed
			Dy = 0
			State = STATE_MOVE_RIGHT
			TurnCounter = MAX_TURN_COUNTER
		endif													
	endif
	
	Rotation = 270
	
end sub

sub Waller.ActionMoveDownReverse( Map() as TileType )
	
	dim as integer TileX, TileY
	if( CollideFloors( int(x), int(y + Dy + Hei), TileY, Map() ) ) then	
		''y = ( TileY ) * TILE_SIZE - Hei						
		Dy = 0
		Dx = Speed
		State = STATE_MOVE_RIGHT
		TurnCounter = MAX_TURN_COUNTER
	else
		y += Dy
		if( not CollideWalls( int(x - Speed - Wid), int(y), TileX, Map() ) ) then
			Dx = -Speed
			Dy = 0
			State = STATE_MOVE_LEFT
			TurnCounter = MAX_TURN_COUNTER
		endif												
	endif
	
	Rotation = 90
	
end sub

	
sub Waller.Spawn( byval ix as integer, byval iy as integer, byval iOrientation as integer )
	
	Active = TRUE
	Counter = 0 
	Rotation = 0
	TurnCounter = MAX_TURN_COUNTER
	Orientation = iOrientation
	State = STATE_FALLING
	if( Orientation = NORMAL ) then
		FlipMode = GL2D.FLIP_NONE
	else
		FlipMode = GL2D.FLIP_H
	EndIf
	
	Speed = 1.4
	
	x = ix
	y = iy
	
	Dx = 0
	Dy = 1
	
	Frame = 0
	BaseFrame = 129
	NumFrames = 4
	
	Wid = 24
	Hei	= 24
	
	BoxNormal.Init( x, y, wid, Hei)
	
End Sub


sub Waller.Update( byref Snipe as Player,  Map() as TileType )
	
	if( (abs(x - (Snipe.GetCameraX + SCREEN_WIDTH\2)) \ TILE_SIZE) > 20  ) then return
	
	Counter + = 1
	TurnCounter -= 1
	
	if( (Counter and 3) = 0 ) then
		Frame = ( Frame + 1 ) mod NumFrames
	endif	
	
	if( Orientation = NORMAL ) then
		select case State
			case STATE_FALLING:
				ActionFalling( Map() )
			case STATE_MOVE_RIGHT:
				ActionMoveRightNormal( Map() )
			case STATE_MOVE_LEFT:
				ActionMoveLeftNormal( Map() )
			case STATE_MOVE_UP:
				ActionMoveUpNormal( Map() )
			case STATE_MOVE_DOWN:
				ActionMoveDownNormal( Map() )
			case else
		end select	
		
	else
			select case State
			case STATE_FALLING:
				ActionFalling( Map() )
			case STATE_MOVE_RIGHT:
				ActionMoveRightReverse( Map() )
			case STATE_MOVE_LEFT:
				ActionMoveLeftReverse( Map() )
			case STATE_MOVE_UP:
				ActionMoveUpReverse( Map() )
			case STATE_MOVE_DOWN:
				ActionMoveDownReverse( Map() )
			case else
		end select	
	
	endif
	

	BoxNormal.Init( x, y, wid, Hei)
	 
			
End Sub


sub Waller.Explode()
	
	Explosion.Spawn( Vector3D(x + Wid\2, y + Hei\2, 2), Vector3D(0, 0, 0), Explosion.MEDIUM_YELLOW_01 )
	
	Kill()
	
End Sub

sub Waller.Kill()
	
	Active = FALSE
	x = 0
	y = 0
	
	BoxNormal.Init( -10000, -1000, wid, Hei)
	
End Sub

sub Waller.Draw( SpriteSet() as GL2D.IMAGE ptr )
	
	GL2D.SpriteRotateScaleXY3D( x + Wid/2,_
							    y + Hei/2,_
							    -4,_
							    Rotation,_
							    1,_
							    1,_
							    FlipMode,_
							    SpriteSet( BaseFrame + Frame ) )
	
End Sub

sub Waller.DrawAABB()
	
	BoxNormal.Draw( 4, GL2D_RGB( 255, 0, 255 ) )
	
end sub


sub Waller.CollideWithPlayer( byref Snipe as Player )
	
	if( (not Snipe.IsDead) and (not Snipe.IsInvincible) ) then	
		dim as AABB Box = Snipe.GetBoxSmall
		if( BoxNormal.Intersects(Box) ) then
			Snipe.HitAnimation( x, 65 )
		endif		
	endif
	
	dim as integer AttackEnergy = 0
	
	AttackEnergy = Snipe.CollideShots( BoxNormal )
	'if( AttackEnergy ) then
	'	Explode()
	'endif
	
	AttackEnergy = Snipe.CollideBombs( BoxNormal )
	if( AttackEnergy ) then
		Explode()
		Snipe.AddToScore( 705 )
	endif
	
	AttackEnergy = Snipe.CollideDynamites( BoxNormal )
	if( AttackEnergy ) then
		Explode()
		Snipe.AddToScore( 705 )
	endif
	
	AttackEnergy = Snipe.CollideMines( BoxNormal )
	if( AttackEnergy ) then
		Explode()
		Snipe.AddToScore( 705 )
	endif
	
End Sub

''*****************************************************************************
'' Collides the object box with the tiles on the y-axis
''*****************************************************************************
function Waller.CollideWalls(byval ix as integer, byval iy as integer, byref iTileX as integer, Map() as TileType ) as integer
	
	dim as integer TileYpixels = iy - (iy mod TILE_SIZE)   '' Pixel of the player's head snapped to map grid
	dim as integer TestEnd = (iy + hei)\TILE_SIZE		   '' Foot of the player
	
	iTileX = ix\TILE_SIZE								   '' Current X map coord the player is on + x-velocity(+ width when moving right)
	
	dim as integer iTileY = TileYpixels\TILE_SIZE		   '' Current Y map coord of the player's head
	
	'' Scan downwards from head to foot if we collided with a tile on the right or left
	while( iTileY <= TestEnd )
		if( Map(iTileX, iTileY).Collision >= TILE_SOLID )	then return TRUE	   '' Found a tile
		iTileY += 1										   '' Next tile downward
	Wend
	
	return FALSE
	
End Function

''*****************************************************************************
'' Collides the object box with the tiles on the y-axis
''*****************************************************************************
function Waller.CollideFloors(byval ix as integer, byval iy as integer, byref iTileY as integer, Map() as TileType ) as integer
	
	dim as integer TileXpixels = ix - (ix mod TILE_SIZE)
	dim as integer TestEnd = (ix + wid)\TILE_SIZE
	
	iTileY = iy\TILE_SIZE
	
	dim as integer iTileX = TileXpixels\TILE_SIZE
	
	while( iTileX <= TestEnd )
		if( Map(iTileX, iTileY).Collision >= TILE_SOLID )	then return TRUE	
		iTileX += 1
	Wend
	
	return FALSE
	
End Function



function Waller.CollideWithAABB( byref Box as const AABB ) as integer

	if( BoxNormal.Intersects( Box ) ) then
		return TRUE
	EndIf

	return FALSE

End Function

sub Waller.FallDown( )

	if( TurnCounter >= 0 ) then
		Dx = 0
		Dy = 1
		State = STATE_FALLING	
	endif	

end sub

function Waller.CheckDiagonalTiles( byval xOffset as integer, byval yOffset as integer, Map() as TileType ) as integer  
	
	
	dim as integer Cx = x + Wid\2
	dim as integer Cy = y + Hei\2

	dim as integer T = MapUtil.GetTile( Cx + xOffset, Cy + yOffset, Map() )   
	
	if( (T = TILE_NONE) or (TurnCounter >= 0) ) then
		Dx = 0
		Dy = 1
		State = STATE_FALLING	
		return TRUE		
	endif	
	
	return FALSE
	
End Function

		


''*****************************************************************************
''
'' WallerFactory
''
''*****************************************************************************

constructor WallerFactory()

	ActiveEntities = 0
	
	for i as integer = 0 to ubound(Wallers)
		Wallers(i).Kill()
	Next
	
End Constructor

destructor WallerFactory()

End Destructor

property WallerFactory.GetActiveEntities() as integer
	property = ActiveEntities
end property 

property WallerFactory.GetMaxEntities() as integer
	property = ubound(Wallers)
end property 

sub WallerFactory.UpdateEntities( byref Snipe as Player, Map() as TileType )
	
	ActiveEntities = 0
	for i as integer = 0 to ubound(Wallers)
		if( Wallers(i).IsActive ) then
			Wallers(i).Update( Snipe, Map() )
			ActiveEntities += 1
		EndIf
	Next
	
end sub

sub WallerFactory.DrawEntities( SpriteSet() as GL2D.IMAGE ptr )
	
	for i as integer = 0 to ubound(Wallers)
		if( Wallers(i).IsActive ) then
			Wallers(i).Draw( SpriteSet() )
		EndIf
	Next
	
end sub

sub WallerFactory.DrawCollisionBoxes()
	
	for i as integer = 0 to ubound(Wallers)
		if( Wallers(i).IsActive ) then
			Wallers(i).DrawAABB()
		EndIf
	Next
	
end sub

sub WallerFactory.KillAllEntities()
	
	for i as integer = 0 to ubound(Wallers)
		if( Wallers(i).IsActive ) then
			Wallers(i).Kill()
		EndIf
	Next
	ActiveEntities = 0
	
end sub

sub WallerFactory.HandleCollisions( byref Snipe as Player )
	
	for i as integer = 0 to ubound(Wallers)
		
		if( Wallers(i).IsActive ) then
			Wallers(i).CollideWithPlayer( Snipe )
		endif
		
	Next i
	
	
end sub

sub WallerFactory.Spawn( byval ix as integer, byval iy as integer, byval iOrientation as integer )

	
	for i as integer = 0 to ubound(Wallers)
		if( Wallers(i).IsActive = FALSE ) then
			Wallers(i).Spawn( ix, iy, iOrientation )
			exit for
		EndIf
	Next
	
end sub

function WallerFactory.GetAABB( byval i as integer ) as AABB
	
	return Wallers(i).GetBox
	
End Function
	