package net.phatcode.rel.math;

public class Vector3D
{
    public static final float EPSILON = 0.0000000001f;
	public float x, y, z;
    
    public Vector3D()
    {}
    
    public Vector3D(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public Vector3D(float pos)
    {
        this.x = this.y = this.z = pos;
    }
    
    public Vector3D(Vector3D v)
    {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }
    
    public Vector3D(float[] pos)
    {
        this.x = pos[0];
        this.y = pos[1];
        this.z = pos[2];
    }
    
    public Vector3D set(Vector3D v)
    {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
        return this;
    }
    
    public Vector3D set(float val)
    {
        this.x = this.y = this.z = val;
        return this;
    }
    
    public Vector3D mul(float val)
    {
        this.x *= val;
        this.y *= val;
        this.z *= val;
        return this;
    }
    
    public Vector3D mul(Vector3D v)
    {
        this.x *= v.x;
        this.y *= v.y;
        this.z *= v.z;
        return this;
    }
    
    public Vector3D add(float val)
    {
        this.x += val;
        this.y += val;
        this.z += val;
        return this;
    }
    
    public Vector3D add(Vector3D v)
    {
        this.x += v.x;
        this.y += v.y;
        this.z += v.z;
        return this;
    }
    
    public Vector3D sub(float val)
    {
        this.x -= val;
        this.y -= val;
        this.z -= val;
        return this;
    }
    
    public Vector3D sub(Vector3D v)
    {
        this.x -= v.x;
        this.y -= v.y;
        this.z -= v.z;
        return this;
    }
    
    public Vector3D div(float val)
    {
        this.x /= val;
        this.y /= val;
        this.z /= val;
        return this;
    }
    
    public Vector3D div(Vector3D v)
    {
        this.x /= v.x;
        this.y /= v.y;
        this.z /= v.z;
        return this;
    }
    
    public float magnitude()
    {
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }
    
    public float magnitudeSquared()
    {
        return ((x * x) + (y * y) + (z * z));
    }
    
    public float dot(Vector3D v)
    {
        return ((x * v.x) + (y * v.y) + (z * v.z));
    }
    
    public Vector3D normalize()
    {
        float length = magnitude();
        float invMag = 1.0f / length;
        
        if(length > 0)
        {
        	x *= invMag;
            y *= invMag;
            z *= invMag;
        }
        
        return this;
    }
    
    public Vector3D scale(float a)
    {
        this.x *= a;
        this.y *= a;
        this.z *= a;
        return this;
    }
    
    public Vector3D cross(Vector3D v)
    {
        return new Vector3D(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }
    
    public Vector3D reflect(Vector3D n)
    {
        float dot = (x * n.x) + (y * n.y) + (z * n.z);
        Vector3D normal = new Vector3D(n.x * -2.0f, n.y * -2.0f, n.z * -2.0f);
        normal.scale(dot);
        normal.x += x;
        normal.y += y;
        normal.z += z;
        
        return normal;
    }
    
    public static final Vector3D add(Vector3D a, Vector3D b)
    {
        return new Vector3D(a.x + b.x, a.y + b.y, a.z + b.z);
    }
    
    public static final Vector3D sub(Vector3D a, Vector3D b)
    {
        return new Vector3D(a.x - b.x, a.y - b.y, a.z - b.z);
    }
    
    public static final Vector3D mul(Vector3D a, Vector3D b)
    {
        return new Vector3D(a.x * b.x, a.y * b.y, a.z * b.z);
    }
    
    public static final Vector3D div(Vector3D a, Vector3D b)
    {
        return new Vector3D(a.x / b.x, a.y / b.y, a.z / b.z);
    }
    
    public static final Vector3D cross(Vector3D a, Vector3D b)
    {
        return new Vector3D(a.y * b.z - a.z * b.y, a.z * b.x - a.x * b.z, a.x * b.y - a.y * b.x);
    }
    
    public static final float dot(Vector3D a, Vector3D b)
    {
        return (a.x * b.x) + (a.y * b.y) + (a.z * b.z);
    }
    
    public static final float magnitude(Vector3D v)
    {
        return v.magnitude();
    }
    
    public static final float magnitudeSquared(Vector3D v)
    {
        return v.magnitudeSquared();
    }
    
    public static final Vector3D normalize(Vector3D v)
    {
        float length = v.magnitude();
        float invMag = 1.0f / length;
        
        if(length > 0)
        {
            v.x *= invMag;
            v.y *= invMag;
            v.z *= invMag;
        }
        
        return v;
    }
    
    public static final Vector3D projection(Vector3D u, Vector3D v)
    {
        float dotUV = dot(u, v);
        float dotUU = dot(u, u);
        return u.scale(dotUV / dotUU);
    }
    
    public static final float distance(Vector3D a, Vector3D b)
    {
        Vector3D dist = new Vector3D(a.x - b.x, a.y - b.y, a.z - b.z);
        return dist.magnitude();
    }
    
    public static final float angleBetween(Vector3D a, Vector3D b)
    {
        return 2.0f * (a.sub(b).magnitude() / a.add(b).magnitude());
    }
    
    public static final Vector3D reflect(Vector3D a, Vector3D b)
    {
        float dot = dot(a, b);
        Vector3D normal = new Vector3D(b.x * -2.0f, b.y * -2.0f, b.z * -2.0f);
        normal.scale(dot);
        normal.x += a.x;
        normal.y += a.y;
        normal.z += a.z;
        
        return normal;
    }
    
    public static final Vector3D midpoint(Vector3D a, Vector3D b)
    {
        return new Vector3D((a.x + b.x) / 2.0f, (a.y + b.y) / 2.0f, (a.z + b.z) / 2.0f);
    }
    
    public static final Vector3D randomVector(float maxX, float maxY, float maxZ)
    {
        return new Vector3D((float) Math.random() * maxX, (float) Math.random() * maxY, (float) Math.random() * maxZ);
    }
}
