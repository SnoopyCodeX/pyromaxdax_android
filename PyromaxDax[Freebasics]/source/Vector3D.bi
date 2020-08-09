''*****************************************************************************
''
''
''	Pyromax Dax Vector3D Class
''	Richard Eric M. Lope
''	http://rel.phatcode.net
''
''	
''
''*****************************************************************************


#ifndef VECTOR3D_BI
#define VECTOR3D_BI

#ifndef FALSE
	#define FALSE 0
	#define TRUE 1
#endif

type Vector3D
	
public:

	declare constructor()
	declare constructor( byval a as single )
	declare constructor( byval a as single, byval b as single, byval c as single )
	declare constructor( byref v as const Vector3D )
	declare constructor( v() as single )
	declare destructor()	

	declare operator Let( byval a as single )		
	declare operator Let( byref v as const Vector3D )		
	
	declare operator *=( byval a as single )
	declare operator /=( byval a as single )
	
	declare operator +=( byref v as const Vector3D )
	declare operator -=( byref v as const Vector3D )
	
	declare property Magnitude() as single
	declare property MagnitudeSquared() as single
	
	declare function Dot( byref v as const Vector3D ) as single
	declare function Cross( byref v as const Vector3D ) as Vector3D
	declare sub Normalize()
	declare sub Scale( byval s as single )
	declare function Reflect( byref n as const Vector3D ) as Vector3D

	union
	
		type 
			x		as Single
			y		as Single
			z		as single
		end type
			
			v(2) as single
			
	End Union
	
End Type

'' Arithmetic
declare operator + ( byref v1 as Vector3D, byref v2 as Vector3D ) as Vector3D
declare operator - ( byref v1 as Vector3D, byref v2 as Vector3D ) as Vector3D
declare operator * ( byref v1 as Vector3D, byref v2 as Vector3D ) as single		'' dot
declare operator ^ ( byref v1 as Vector3D, byref v2 as Vector3D ) as Vector3D	'' cross
declare operator + ( byref v as Vector3D, byval a as single ) as Vector3D
declare operator - ( byref v as Vector3D, byval a as single ) as Vector3D
declare operator * ( byref v as Vector3D, byval a as single ) as Vector3D
declare operator / ( byref v as Vector3D, byval a as single ) as Vector3D


'' Unary
declare operator +( byref v as const Vector3D ) as Vector3D
declare operator -( byref v as const Vector3D ) as Vector3D

'' Binary
declare operator = ( byref v1 as Vector3D, byref v2 as Vector3D ) as integer
declare operator <> ( byref v1 as Vector3D, byref v2 as Vector3D ) as integer




''*****************************************************************************
''
'' Subs and Functions
''
''*****************************************************************************
namespace UtilVector3D
	
declare function Dot( byref v1 as Vector3D, byref v2 as Vector3D ) as single

declare function Cross( byref v1 as Vector3D, byref v2 as Vector3D ) as Vector3D

declare function Normalize( byref v as Vector3D ) as Vector3D

declare function Distance( byref a as Vector3D, byref b as Vector3D ) as single

declare function Magnitude( byref v as Vector3D ) as single

declare function MagnitudeSquared( byref v as Vector3D ) as single

declare function Reflect( byref v as Vector3D, byref n as Vector3D ) as Vector3D

declare function Projection( byref u as Vector3D, byref v as Vector3D ) as Vector3D

declare function MidPoint overload( byref a as Vector3D, byref b as Vector3D ) as Vector3D

declare function MidPoint overload( byval x1 as single, byval y1 as single, byval z1 as single, byval x2 as single, byval y2 as single, byval z2 as single ) as Vector3D


End Namespace

	

#endif