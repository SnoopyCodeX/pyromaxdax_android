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

#include once "Vector3D.bi"

#ifndef EPSILON
	#define EPSILON  (0.00000001f)
#endif		
''*****************************************************************************
''
''
''
''*****************************************************************************
constructor Vector3D()
End Constructor

constructor Vector3D( byval a as single )
	x = a
	y = a
	z = a
End Constructor

constructor Vector3D( byval a as single, byval b as single, byval c as single )
	x = a
	y = b
	z = c
End Constructor

constructor Vector3D( byref v as const Vector3D )
	x = v.x
	y = v.y
	z = v.z
End Constructor

constructor Vector3D( v() as single )
	x = v(0)
	y = v(1)
	z = v(2)
End Constructor

destructor Vector3D()	

End Destructor


operator Vector3D.Let( byref v as const Vector3D )		
	x = v.x
	y = v.y
	z = v.z
End Operator

operator Vector3D.Let( byval a as single )		
	x = a
	y = a
	z = a
End Operator

operator Vector3D.*=( byval a as single )
	x *= a
	y *= a
	z *= a
End Operator
			
operator Vector3D./=( byval a as single )
	x /= a
	y /= a
	z /= a
End Operator

operator Vector3D.+=( byref v as const Vector3D )
	x += v.x
	y += v.y
	z += v.z
End Operator

operator Vector3D.-=( byref v as const Vector3D )
	x -= v.x
	y -= v.y
	z -= v.z
End Operator


''*****************************************************************************
''
'' Properties
''
''*****************************************************************************

property Vector3D.Magnitude() as single
	property = sqr( x * x + y * y + z * z )
End property

property Vector3D.MagnitudeSquared() as single
	property = ( x * x + y * y + z * z)
End property

''*****************************************************************************
''
'' Methods
''
''*****************************************************************************

function Vector3D.Dot( byref v as const Vector3D ) as single
	return ( x * v.x + y * v.y + z * v.z )
End Function

function Vector3D.Cross( byref v as const Vector3D ) as Vector3D
	return type<Vector3D>( y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x )
End Function
			
sub Vector3D.Normalize()
	
	dim as single Length = Magnitude
	
	if( Length > 0 ) then
		dim as single InvMag = 1/Length 
		x *= InvMag
		y *= InvMag
		z *= Invmag 
	EndIf
		
End Sub

sub Vector3D.Scale( byval s as single )
	this *= s
End Sub

function Vector3D.Reflect( byref n as const Vector3D ) as Vector3D
	'' Assumes normal(n) is normalized
	'' -2 * (V dot N) * N + V
		
	'' Or
	'' -2 * (V dot N)/|N| *N + V
	
	Dim as Vector3D Normal = n
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
operator + ( byref v1 as Vector3D, byref v2 as Vector3D ) as Vector3D
	return type<Vector3D>( v1.x + v2.x, v1.y + v2.y, v1.z + v2.z )
End Operator
'' Subtract
operator - ( byref v1 as Vector3D, byref v2 as Vector3D ) as Vector3D
	return type<Vector3D>( v1.x - v2.x, v1.y - v2.y, v1.z - v2.z )
End Operator
'' Dot
operator * ( byref v1 as Vector3D, byref v2 as Vector3D ) as single
	return ( v1.x * v2.x + v1.y * v2.y + v1.z * v2.z  )
End Operator
'' Cross
operator ^ ( byref v1 as Vector3D, byref v2 as Vector3D ) as Vector3D
	return type<Vector3D>( v1.y * v2.z - v1.z * v2.y, v1.z * v2.x - v1.x * v2.z, v1.x * v2.y - v1.y * v2.x )
End Operator

operator + ( byref v as Vector3D, byval a as single ) as Vector3D
	return type<Vector3D>( v.x + a, v.y + a, v.z + a )
End Operator

operator - ( byref v as Vector3D, byval a as single ) as Vector3D
	return type<Vector3D>( v.x - a, v.y - a, v.z - a )
End Operator

operator * ( byref v as Vector3D, byval a as single ) as Vector3D
	return type<Vector3D>( v.x * a, v.y * a, v.z * a )
End Operator

operator / ( byref v as Vector3D, byval a as single ) as Vector3D
	return type<Vector3D>( v.x / a, v.y / a, v.z /a )
End Operator

''*****************************************************************************
''
'' Unary
''
''*****************************************************************************
operator +( byref v as const Vector3D ) as Vector3D
	return type<Vector3D>( v.x, v.y, v.z )
End Operator

operator -( byref v as const Vector3D ) as Vector3D
	return type<Vector3D>( -v.x, -v.y, -v.z )
End Operator

''*****************************************************************************
''
'' Binary
''
''*****************************************************************************

operator <> ( byref v1 as Vector3D, byref v2 as Vector3D ) as integer

		if( (abs(v1.x - v2.x) > EPSILON) or (abs(v1.y - v2.y) > EPSILON) or (abs(v1.z - v2.z) > EPSILON) ) then
			return TRUE
		endif

		return FALSE
		
End Operator

operator = ( byref v1 as Vector3D, byref v2 as Vector3D ) as integer
		return not(v1 <> v2 )
End Operator




''*****************************************************************************
''
'' Subs and Functions
''
''*****************************************************************************
namespace UtilVector3D
	
function Dot( byref v1 as Vector3D, byref v2 as Vector3D ) as single
	return v1 * v2
end Function

function Cross( byref v1 as Vector3D, byref v2 as Vector3D ) as Vector3D
	return v1 ^ v2
End Function

function Normalize( byref v as Vector3D ) as Vector3D
	
	dim as single Length = v.Magnitude
	
	if( Length > 0 ) then
		dim as single InvMag = 1/Length 
		v.x *= InvMag
		v.y *= InvMag
		v.z *= Invmag
	EndIf
	
	return v
	
End Function

function Distance( byref a as Vector3D, byref b as Vector3D ) as single
	
	dim as Vector3D D = type<Vector3D>( a - b )
	return D.Magnitude

End Function

function Magnitude( byref v as Vector3D ) as single
	return v.Magnitude
End function 

function MagnitudeSquared( byref v as Vector3D ) as single
	return v.MagnitudeSquared
End function

function Reflect( byref v as Vector3D, byref n as Vector3D ) as Vector3D
	
	Dim as Vector3D Normal = n
	Normal *= (-2.0 * v.Dot(n))
	Normal += v	
	
	return Normal
	
End Function

function Projection( byref u as Vector3D, byref v as Vector3D ) as Vector3D
	return u * ( Dot( u, v ) / Dot( u, u ) )
End Function


function MidPoint overload( byref a as Vector3D, byref b as Vector3D ) as Vector3D
	return (a + b)/2
End Function

function MidPoint overload( byval x1 as single, byval y1 as single, byval z1 as single, byval x2 as single, byval y2 as single, byval z2 as single ) as Vector3D
	return type<Vector3D>( (x2 - x1) / 2, (y2 - y1) / 2, (z2 - z1) / 2 )
End Function

End Namespace

	

