''*****************************************************************************
''
''
''	Pyromax Dax Vector2D Class
''	Richard Eric M. Lope
''	http://rel.phatcode.net
''
''	
''
''*****************************************************************************


#ifndef VECTOR2D_BI
#define VECTOR2D_BI

#ifndef FALSE
	#define FALSE 0
	#define TRUE 1
#endif

type Vector2D
	
public:

	declare constructor()
	declare constructor( byval a as single )
	declare constructor( byval a as single, byval b as single )
	declare constructor( byref v as const Vector2D )
	declare constructor( v() as single )
	declare destructor()	

	declare operator Let( byval a as single )		
	declare operator Let( byref v as const Vector2D )		
	
	declare operator *=( byval a as single )
	declare operator /=( byval a as single )
	
	declare operator +=( byref v as const Vector2D )
	declare operator -=( byref v as const Vector2D )
	
	declare property Magnitude() as single
	declare property MagnitudeSquared() as single
	
	declare function Dot( byref v as const Vector2D ) as single
	declare function Cross( byref v as const Vector2D ) as Vector2D
	declare sub Normalize()
	declare sub Scale( byval s as single )
	declare function GetNormal( byval side as integer = 0 ) as Vector2D
	declare function AngleBetween( byref v as const Vector2D ) as single
	declare function Reflect( byref n as const Vector2D ) as Vector2D

	union
	
		type 
			x		as Single
			y		as Single
		end type
			
			v(1) as single
			
	End Union
	
End Type

'' Arithmetic
declare operator + ( byref v1 as Vector2D, byref v2 as Vector2D ) as Vector2D
declare operator - ( byref v1 as Vector2D, byref v2 as Vector2D ) as Vector2D
declare operator * ( byref v1 as Vector2D, byref v2 as Vector2D ) as single		'' dot
declare operator ^ ( byref v1 as Vector2D, byref v2 as Vector2D ) as Vector2D	'' cross
declare operator + ( byref v as Vector2D, byval a as single ) as Vector2D
declare operator - ( byref v as Vector2D, byval a as single ) as Vector2D
declare operator * ( byref v as Vector2D, byval a as single ) as Vector2D
declare operator / ( byref v as Vector2D, byval a as single ) as Vector2D


'' Unary
declare operator +( byref v as const Vector2D ) as Vector2D
declare operator -( byref v as const Vector2D ) as Vector2D

'' Binary
declare operator = ( byref v1 as Vector2D, byref v2 as Vector2D ) as integer
declare operator <> ( byref v1 as Vector2D, byref v2 as Vector2D ) as integer




''*****************************************************************************
''
'' Subs and Functions
''
''*****************************************************************************
namespace UtilVector2D
	
declare function Dot( byref v1 as Vector2D, byref v2 as Vector2D ) as single

declare function Cross( byref v1 as Vector2D, byref v2 as Vector2D ) as Vector2D

declare function Normalize( byref v as Vector2D ) as Vector2D

declare function Distance( byref a as Vector2D, byref b as Vector2D ) as single

declare function Magnitude( byref v as Vector2D ) as single

declare function MagnitudeSquared( byref v as Vector2D ) as single

declare function GetNormal( byref v1 as Vector2D, byref v2 as Vector2D, byval side as integer = 0 ) as Vector2D

declare function AngleBetween( byref v1 as const Vector2D, byref v2 as const Vector2D ) as single

declare function Angle( byref v as const Vector2D ) as single

declare function Reflect( byref v as Vector2D, byref n as Vector2D ) as Vector2D

declare function Projection( byref u as Vector2D, byref v as Vector2D ) as Vector2D

declare function MidPoint overload( byref a as Vector2D, byref b as Vector2D ) as Vector2D

declare function MidPoint overload( byval x1 as single, byval y1 as single, byval x2 as single, byval y2 as single ) as Vector2D


End Namespace

	

#endif