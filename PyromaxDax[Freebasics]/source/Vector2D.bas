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

#include once "Vector2D.bi"

#ifndef EPSILON
	#define EPSILON  (0.00000001f)
#endif		
''*****************************************************************************
''
''
''
''*****************************************************************************
constructor Vector2D()
End Constructor

constructor Vector2D( byval a as single )
	x = a
	y = a
End Constructor

constructor Vector2D( byval a as single, byval b as single )
	x = a
	y = b
End Constructor

constructor Vector2D( byref v as const Vector2D )
	x = v.x
	y = v.y
End Constructor

constructor Vector2D( v() as single )
	x = v(0)
	y = v(1)
End Constructor

destructor Vector2D()	

End Destructor


operator Vector2D.Let( byref v as const Vector2D )		
	x = v.x
	y = v.y
End Operator

operator Vector2D.Let( byval a as single )		
	x = a
	y = a
End Operator

operator Vector2D.*=( byval a as single )
	x *= a
	y *= a
End Operator
			
operator Vector2D./=( byval a as single )
	x /= a
	y /= a
End Operator

operator Vector2D.+=( byref v as const Vector2D )
	x += v.x
	y += v.y
End Operator

operator Vector2D.-=( byref v as const Vector2D )
	x -= v.x
	y -= v.y
End Operator


''*****************************************************************************
''
'' Properties
''
''*****************************************************************************

property Vector2D.Magnitude() as single
	property = sqr( x * x + y * y )
End property

property Vector2D.MagnitudeSquared() as single
	property = ( x * x + y * y )
End property

''*****************************************************************************
''
'' Methods
''
''*****************************************************************************

function Vector2D.Dot( byref v as const Vector2D ) as single
	return ( x * v.x + y * v.y )
End Function

function Vector2D.Cross( byref v as const Vector2D ) as Vector2D
	return type<Vector2D>( x - v.y, y - v.x )
End Function
			
sub Vector2D.Normalize()
	
	dim as single Length = Magnitude
	
	if( Length > 0 ) then
		dim as single InvMag = 1/Length 
		x *= InvMag
		y *= InvMag
	EndIf
		
End Sub

sub Vector2D.Scale( byval s as single )
	this *= s
End Sub

function Vector2D.GetNormal( byval side as integer = 0 ) as Vector2D
	
	if( side ) then
		return type<Vector2D>( -y,  x )
	else
		return type<Vector2D>(  y, -x )
	EndIf
	
End Function

function Vector2D.AngleBetween( byref v as const Vector2D ) as single
	return atan2( y - v.y, x - v.x )
End Function

function Vector2D.Reflect( byref n as const Vector2D ) as Vector2D
	'' Assumes normal(n) is normalized
	'' -2 * (V dot N) * N + V
		
	'' Or
	'' -2 * (V dot N)/|N| *N + V
	
	Dim as Vector2D Normal = n
	Normal *= (-2.0 * this.Dot(n))
	Normal += this	
	
	return Normal
	
End Function

			
''*****************************************************************************
''
'' Arithmetic
''
''*****************************************************************************
'' Add
operator + ( byref v1 as Vector2D, byref v2 as Vector2D ) as Vector2D
	return type<Vector2D>( v1.x + v2.x, v1.y + v2.y )
End Operator
'' Subtract
operator - ( byref v1 as Vector2D, byref v2 as Vector2D ) as Vector2D
	return type<Vector2D>( v1.x - v2.x, v1.y - v2.y )
End Operator
'' Dot
operator * ( byref v1 as Vector2D, byref v2 as Vector2D ) as single
	return ( v1.x * v2.x + v1.y * v2.y )
End Operator
'' Cross
operator ^ ( byref v1 as Vector2D, byref v2 as Vector2D ) as Vector2D
	return type<Vector2D>( v1.x - v2.y, v1.y - v2.x )
End Operator

operator + ( byref v as Vector2D, byval a as single ) as Vector2D
	return type<Vector2D>( v.x + a, v.y + a )
End Operator

operator - ( byref v as Vector2D, byval a as single ) as Vector2D
	return type<Vector2D>( v.x - a, v.y - a )
End Operator

operator * ( byref v as Vector2D, byval a as single ) as Vector2D
	return type<Vector2D>( v.x * a, v.y * a )
End Operator

operator / ( byref v as Vector2D, byval a as single ) as Vector2D
	return type<Vector2D>( v.x / a, v.y / a )
End Operator

''*****************************************************************************
''
'' Unary
''
''*****************************************************************************
operator +( byref v as const Vector2D ) as Vector2D
	return type<Vector2D>( v.x, v.y )
End Operator

operator -( byref v as const Vector2D ) as Vector2D
	return type<Vector2D>( -v.x, -v.y )
End Operator

''*****************************************************************************
''
'' Binary
''
''*****************************************************************************

operator <> ( byref v1 as Vector2D, byref v2 as Vector2D ) as integer

		if( (abs(v1.x - v2.x) > EPSILON) or (abs(v1.y - v2.y) > EPSILON) ) then
			return TRUE
		endif

		return FALSE
		
End Operator

operator = ( byref v1 as Vector2D, byref v2 as Vector2D ) as integer
		return not(v1 <> v2 )
End Operator




''*****************************************************************************
''
'' Subs and Functions
''
''*****************************************************************************
namespace UtilVector2D
	
function Dot( byref v1 as Vector2D, byref v2 as Vector2D ) as single
	return v1 * v2
end Function

function Cross( byref v1 as Vector2D, byref v2 as Vector2D ) as Vector2D
	return v1 ^ v2
End Function

function Normalize( byref v as Vector2D ) as Vector2D
	
	dim as single Length = v.Magnitude
	
	if( Length > 0 ) then
		dim as single InvMag = 1/Length 
		v.x *= InvMag
		v.y *= InvMag
	EndIf
	
	return v
	
End Function

function Distance( byref a as Vector2D, byref b as Vector2D ) as single
	
	dim as Vector2D D = type<Vector2D>( a - b )
	return D.Magnitude

End Function

function Magnitude( byref v as Vector2D ) as single
	return v.Magnitude
End function 

function MagnitudeSquared( byref v as Vector2D ) as single
	return v.MagnitudeSquared
End function


function GetNormal( byref v1 as Vector2D, byref v2 as Vector2D, byval side as integer = 0 ) as Vector2D
	
	dim as Vector2D v = type<Vector2D>( v2 - v1 )
	if( side ) then
		return type<Vector2D>( -v.y,  v.x )
	else
		return type<Vector2D>(  v.y, -v.x )
	EndIf
	
End Function

function AngleBetween( byref v1 as const Vector2D, byref v2 as const Vector2D ) as single
	return atan2( v2.y - v1.y, v2.x - v1.x)
End Function

function Angle( byref v as const Vector2D ) as single
	return atan2( v.y, v.x )
End Function

function Reflect( byref v as Vector2D, byref n as Vector2D ) as Vector2D
	
	Dim as Vector2D Normal = n
	Normal *= (-2.0 * v.Dot(n))
	Normal += v	
	
	return Normal
	
End Function

function Projection( byref u as Vector2D, byref v as Vector2D ) as Vector2D
	return u * ( Dot( u, v ) / Dot( u, u ) )
End Function


function MidPoint overload( byref a as Vector2D, byref b as Vector2D ) as Vector2D
	return (a + b)/2
End Function

function MidPoint overload( byval x1 as single, byval y1 as single, byval x2 as single, byval y2 as single ) as Vector2D
	return type<Vector2D>( (x2 - x1) / 2, (y2 - y1) / 2 )
End Function

End Namespace

	

