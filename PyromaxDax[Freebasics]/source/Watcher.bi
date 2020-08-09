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

#include once "FBGFX.bi"
#include once "FBGL2D7.bi"     	'' We're gonna use Hardware acceleration
#include once "UTIL.bi"
#include once "Vector2D.bi"
#include once "Vector3D.bi"
#include once "Globals.bi"
#include once "AABB.bi"
#include once "Map.bi"
#include once "Particles.bi"
#include once "Explosion.bi"
#include once "Sound.bi"
#include once "Player.bi"
#include once "Bullet.bi"


#define MAX_WATCHERS 15

type Watcher
	
	
	enum E_STATE
		STATE_IDLE = 0,
		STATE_SCAN,
		STATE_ATTACK,
	end enum
	
	enum
		O_LEFT = 0,
		O_RIGHT = 1
	end enum
	
	declare constructor()
	declare destructor()
	
	declare property IsActive() as integer
	declare property GetX() as single
	declare property GetY() as single
	declare property GetDrawFrame() as integer
	declare property GetBox() as AABB
	
	declare sub Spawn( byval ix as integer, byval iy as integer, byval iOrientation as integer )
	declare sub Update( byref Snipe as Player, byref Bullets as BulletFactory, Map() as TileType )
	declare sub Explode()
	declare sub Kill()
	declare sub Draw( SpriteSet() as GL2D.IMAGE ptr )
	declare sub CollideWithPlayer( byref Snipe as Player )
	declare function CollideWithAABB( byref Box as const AABB ) as integer
	declare sub DrawAABB()
		
private:
	
	declare Sub ActionIdle( Map() as TileType )
	declare Sub ActionScan( Map() as TileType )
	declare Sub ActionAttack( byref Bullets as BulletFactory, Map() as TileType )
	declare sub ResolveAnimationParameters()

	as integer Active
	as integer Counter
	as integer IdleCounter
	as integer FireCounter
	as integer Orientation
	as integer State
	
	as integer BlinkCounter 
	as integer Hp
	
	as single Speed		
	as single x
	as single y
	
	as single Dx
	as single Dy
	as single OldDy
	
	as integer Frame
	as integer BaseFrame
	as integer NumFrames
	as integer FlipMode 
	
	as integer Wid
	as integer Hei	
	
	as AABB BoxNormal
	
	
End Type


type WatcherFactory

public:

	declare constructor()
	declare destructor()
	
	declare property GetActiveEntities() as Integer
	declare property GetMaxEntities() as Integer
	
	declare sub UpdateEntities( byref Snipe as Player, byref Bullets as BulletFactory, Map() as TileType )
	declare sub DrawEntities( SpriteSet() as GL2D.IMAGE ptr )
	declare sub DrawCollisionBoxes()
	declare sub KillAllEntities()
	declare sub HandleCollisions( byref Snipe as Player )
		
	declare sub Spawn( byval ix as integer, byval iy as integer, byval iOrientation as integer )
	declare function GetAABB( byval i as integer ) as AABB
	
private:

	as integer ActiveEntities
	as Watcher Watchers(MAX_WATCHERS)
	
End Type
