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

#include "VectorSpring.bi"


const as single CFORCE = 10
const as single CFRICTION = 0.849996
const as single CMAGNITUDE = 15


constructor VectorSpring()

	x = 0
	y = 0
	Dx = 0
	Dy = 0
	Ox = 0
	Oy = 0
	Force = CFORCE
	Frict = CFRICTION
	Magnitude = CMAGNITUDE
	Mass = 400
	PullAccel = Force / Mass
		
end constructor

destructor VectorSpring()

end destructor

property VectorSpring.GetX() as single
	property = x
end property

property VectorSpring.GetY() as single
	property = y
end property

property VectorSpring.GetDx() as single
	property = Dx
end property

property VectorSpring.GetDy() as single
	property = Dy
end property

property VectorSpring.SetX( byval v as single )
	x = v
end property

property VectorSpring.SetY( byval v as single )
	y = v
end property

property VectorSpring.SetDx( byval v as single )
	Dx = v
end property

property VectorSpring.SetDy( byval v as single )
	Dy = v
end property

property VectorSpring.SetOx( byval v as single )
	Ox = v
end property

property VectorSpring.SetOy( byval v as single )
	Oy = v
end property

sub VectorSpring.Update()
	
	dim as single ix = x - Ox
	dim as single iy = y - Oy
	dim as single PullForce = PullAccel * Magnitude
	Dx -= (ix * PullForce)
	Dy -= (iy * PullForce)
	Dx *= Frict
	Dy *= Frict
	x += Dx
	y += Dy
			
end sub

sub VectorSpring.SetParameters( byval imass as single, byval iforce as single, byval ifriction as single, byval imagnitude as single )
	
	Force = iforce
	Mass = imass
	Frict = ifriction
	Magnitude = imagnitude
	PullAccel = Force / Mass
	
end sub
