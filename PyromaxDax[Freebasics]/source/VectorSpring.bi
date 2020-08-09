''*****************************************************************************
''
''
''	Pyromax Dax VectorSpring(No SquareRoot) Class
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


type VectorSpring

public:	
	declare constructor()
	declare destructor()
	
	declare property GetX() as single
	declare property GetY() as single
	declare property GetDx() as single
	declare property GetDy() as single
	
	declare property SetX( byval v as single )
	declare property SetY( byval v as single )
	declare property SetDx( byval v as single )
	declare property SetDy( byval v as single )
	declare property SetOx( byval v as single )
	declare property SetOy( byval v as single )

	declare sub Update()
	declare sub SetParameters( byval imass as single, byval iforce as single, byval ifriction as single, byval imagnitude as single )
	
private:

	as single x
	as single y
	as single Dx
	as single Dy
	as single Ox
	as single Oy
	
	as single Mass
	as single Force
	as single Frict
	as single Magnitude
	as single PullAccel
		
end type
