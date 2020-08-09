''*****************************************************************************
''
''
''	Pyromax Dax Warp Class
''	Richard Eric M. Lope
''	http://rel.phatcode.net
''
''	
''
''*****************************************************************************

#include once "Warp.bi"


constructor Warp()

	Active = FALSE
	Counter = 0 
	FlipMode = GL2D.FLIP_NONE
	State = STATE_FALLING
	
	Speed = 1	
	x = 0
	y = 0
	Dx = 0
	Dy = 1
	

	Frame = 0
	BaseFrame = 47
	NumFrames = 1
	
	Wid = 64
	Hei	= 64
	
	BoxNormal.Init( x - Wid\2, y - Hei\2, wid, Hei)
	BoxSmall.Init( x + Wid\2 - 16, y + Hei\2 - 32, 32, 64)
	
End Constructor

destructor Warp()

End Destructor


property Warp.IsActive() as integer
	property = Active
End Property

property Warp.GetID() as integer
	property = ID
End Property
		
property Warp.GetX() as single
	property = x
End Property

property Warp.GetY() as single
	property = y
End Property

property Warp.GetDrawFrame() as integer
	property = (BaseFrame + Frame)
End Property

property Warp.GetBox() as AABB
	property = BoxNormal
End Property


sub Warp.ActionFalling( Map() as TileType )
	
	dim as integer TileX, TileY
	if( CollideFloors( int(x), int(y + Dy + Hei), TileY, Map() ) ) then	
		y = ( TileY ) * TILE_SIZE - Hei - 1
		Dy = 1
		Dx = 0
	else
		y += Dy												
		Dy += GRAVITY
	endif
	
		
end sub

	
sub Warp.Spawn( byval ix as integer, byval iy as integer, byval iOrientation as integer )
	
	Active = TRUE
	Counter = 0 
	Orientation = iOrientation
	State = STATE_FALLING
	Flipmode = GL2D.FLIP_NONE
	
	
	x = ix
	y = iy
	
	Dx = 0
	Dy = 1
	
	Frame = 0
	BaseFrame = 47
	NumFrames = 1
	
	Wid = 64
	Hei	= 64
	
	BoxNormal.Init( x, y, Wid, Hei)
	BoxSmall.Init( x + Wid\2 - 16, y + Hei\2 - 32, 32, 64)
	
End Sub


sub Warp.Update( byref Snipe as Player, Map() as TileType )
	
	if( (abs(x - (Snipe.GetCameraX + SCREEN_WIDTH\2)) \ TILE_SIZE) > 12  ) then return
	
	Counter + = 1
	
	if( (Counter and 3) = 0 ) then
		Frame = ( Frame + 1 ) mod NumFrames
	endif	
	
	select case State
		case STATE_FALLING:
			ActionFalling( Map() )
		case else
	end select	
	

	BoxNormal.Init( x, y, Wid, Hei)
	BoxSmall.Init( x + Wid\2 - 8, y + Hei\2 + 8, 16, 24)
	 
			
End Sub


sub Warp.Explode()
	
	Explosion.Spawn( Vector3D(x, y, 2), Vector3D(0, 0, 0), Explosion.TWINKLE )
	
	Kill()
	
End Sub

sub Warp.Kill()
	
	Active = FALSE
	x = 0
	y = 0
	
	BoxNormal.Init( -10000, -1000, wid, Hei)
	
End Sub

sub Warp.Draw( SpriteSet() as GL2D.IMAGE ptr )
	GL2D.Sprite3D( x, y, -2, FlipMode, SpriteSet( BaseFrame + Frame ) )
	GL2D.Sprite3D( x, y,  4, FlipMode, SpriteSet( BaseFrame + Frame + 1 ) )
End Sub

sub Warp.DrawAABB()
	
	BoxNormal.Draw( 4, GL2D_RGB( 255, 0, 255 ) )
	BoxSmall.Draw( 4, GL2D_RGB( 0, 255, 255 ) )
	
end sub


function Warp.CollideWithPlayer( byref Snipe as Player ) as integer
	
	
	if( (Snipe.GetState <> Player.DIE) ) then	
		dim as AABB Box = Snipe.GetBoxSmall
		if( BoxSmall.Intersects(Box) ) then
			return TRUE
		endif		
	endif
	
	return FALSE
	
end function


''*****************************************************************************
'' Collides the object box with the tiles on the y-axis
''*****************************************************************************
function Warp.CollideFloors(byval ix as integer, byval iy as integer, byref iTileY as integer, Map() as TileType ) as integer
	
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



function Warp.CollideWithAABB( byref Box as const AABB ) as integer

	if( BoxNormal.Intersects( Box ) ) then
		return TRUE
	EndIf

	return FALSE

End Function



''*****************************************************************************
''
'' WarpFactory
''
''*****************************************************************************

constructor WarpFactory()

	ActiveEntities = 0
	
	for i as integer = 0 to ubound(Warps)
		Warps(i).Kill()
	Next
	
End Constructor

destructor WarpFactory()

End Destructor

property WarpFactory.GetActiveEntities() as integer
	property = ActiveEntities
end property 

property WarpFactory.GetMaxEntities() as integer
	property = ubound(Warps)
end property 

sub WarpFactory.UpdateEntities( byref Snipe as Player, Map() as TileType )
	
	ActiveEntities = 0
	for i as integer = 0 to ubound(Warps)
		if( Warps(i).IsActive ) then
			Warps(i).Update( Snipe, Map() )
			ActiveEntities += 1
		EndIf
	Next
	
end sub

sub WarpFactory.DrawEntities( SpriteSet() as GL2D.IMAGE ptr )
	
	for i as integer = 0 to ubound(Warps)
		if( Warps(i).IsActive ) then
			Warps(i).Draw( SpriteSet() )
		EndIf
	Next
	
end sub

sub WarpFactory.DrawCollisionBoxes()
	
	for i as integer = 0 to ubound(Warps)
		if( Warps(i).IsActive ) then
			Warps(i).DrawAABB()
		EndIf
	Next
	
end sub

sub WarpFactory.KillAllEntities()
	
	for i as integer = 0 to ubound(Warps)
		if( Warps(i).IsActive ) then
			Warps(i).Kill()
		EndIf
	Next
	ActiveEntities = 0
	
end sub

function WarpFactory.HandleCollisions( byref Snipe as Player ) as integer
	
	for i as integer = 0 to ubound(Warps)
		
		if( Warps(i).IsActive ) then
			if( Warps(i).CollideWithPlayer( Snipe ) ) then
				return (i+1)
			EndIf
		endif
		
	Next i
	
	return 0
	
end function

sub WarpFactory.SortEntities()
	
	'' Hell yeah! Bubble sort!
	for i as integer = 0 to ubound(Warps)
		if( Warps(i).IsActive ) then
			for j as integer = 0 to ubound(Warps) - 1
				if( Warps(j).IsActive ) then
					if( Warps(i).GetX < Warps(j).GetX ) then
						swap Warps(i), Warps(j)
					endif
				endif
			next
		endif
	next
	
end sub

sub WarpFactory.Spawn( byval ix as integer, byval iy as integer, byval iOrientation as integer )

	
	for i as integer = 0 to ubound(Warps)
		if( Warps(i).IsActive = FALSE ) then
			Warps(i).Spawn( ix, iy, iOrientation )
			exit for
		EndIf
	Next
	
end sub

function WarpFactory.GetAABB( byval i as integer ) as AABB
	
	return Warps(i).GetBox
	
End Function

function WarpFactory.GetID( byval i as integer ) as integer
	return Warps(i).GetID
End Function
			