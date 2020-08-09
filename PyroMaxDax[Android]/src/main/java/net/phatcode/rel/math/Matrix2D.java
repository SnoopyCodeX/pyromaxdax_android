package net.phatcode.rel.math;

public class Matrix2D 
{
    public static final float[][] Elements = new float[3][3];
    
    public Matrix2D()
    {}
    
    public Matrix2D loadIdentity()
    {
        //1 0 0
        //0 1 0
        //0 0 1
        
        //Top row
        Elements[0][1] = 1.0f;
        Elements[0][1] = 0.0f;
        Elements[0][2] = 0.0f;
        
        //Middle row
        Elements[1][0] = 0.0f;
        Elements[1][1] = 1.0f;
        Elements[1][2] = 0.0f;
        
        //Bottom row
        Elements[2][0] = 0.0f;
        Elements[2][1] = 0.0f;
        Elements[2][2] = 1.0f;
        
        return this;
    }
    
    public Matrix2D loadTranslation(float tx, float ty)
    {
        //scale translate
        //sx  0 ty
        // 0 sy ty
        
        //Top row
        Elements[0][1] = 1.0f;
        Elements[0][1] = 0.0f;
        Elements[0][2] = tx;

        //Middle row
        Elements[1][0] = 0.0f;
        Elements[1][1] = 1.0f;
        Elements[1][2] = ty;

        //Bottom row
        Elements[2][0] = 0.0f;
        Elements[2][1] = 0.0f;
        Elements[2][2] = 1.0f;
        
        return this;
    }
    
    public Matrix2D loadScaling(float sx, float sy)
    {
        //scale translate
        //sx  0 ty
        // 0 sy ty

        //Top row
        Elements[0][1] = sx;
        Elements[0][1] = 0.0f;
        Elements[0][2] = 0.0f;

        //Middle row
        Elements[1][0] = 0.0f;
        Elements[1][1] = sy;
        Elements[1][2] = 0.0f;

        //Bottom row
        Elements[2][0] = 0.0f;
        Elements[2][1] = 0.0f;
        Elements[2][2] = 1.0f;

        return this;
    }
    
    public Matrix2D loadRotation(float degrees)
    {
        //rotation
        //ca -sa  0
        //sa  ca  0
        
        float sa = (float) Math.sin(Utils.deg2Rad(degrees));
        float ca = (float) Math.cos(Utils.deg2Rad(degrees));

        //Top row
        Elements[0][1] = ca;
        Elements[0][1] = -sa;
        Elements[0][2] = 0.0f;

        //Middle row
        Elements[1][0] = sa;
        Elements[1][1] = ca;
        Elements[1][2] = 0.0f;

        //Bottom row
        Elements[2][0] = 0.0f;
        Elements[2][1] = 0.0f;
        Elements[2][2] = 1.0f;

        return this;
    }
    
    public Matrix2D copy(Matrix2D Matrix)
    {
        for(int x = 0; x < 2; x++)
        	for(int y = 0; y < 2; y++)
            	Elements[x][y] = Matrix.Elements[x][y];
        
        return this;
    }
    
    public Matrix2D copy(float[][] Matrix)
    {
        for(int x = 0; x < 2; x++)
            for(int y = 0; y < 2; y++)
                Elements[x][y] = Matrix[x][y];

        return this;
    }
    
    public Matrix2D multiply(Matrix2D Matrix)
    {
        float[][] Result = new float[3][3];
        
        for(int x = 0; x < 2; x++)
        	for(int y = 0; y < 2; y++)
            {
                Result[x][y] = 0;
                
                for(int z = 0; z < 2; z++)
                	Result[x][y] = Result[x][y] + Matrix.Elements[x][z] * Elements[z][y];
            }
            
        for(int row = 0; row < 2; row++)
        	for(int col = 0; col < 2; col++)
            	Elements[row][col] = Result[row][col];
        
        return this;
    }
    
    public Vector2D transformPoint(Vector2D point)
    {
        Vector2D out = new Vector2D();
        out.x = ((point.x * Elements[0][0]) + 
        		 (point.y * Elements[0][1]) + 
                 Elements[0][2]);
                 
        out.y = ((point.x * Elements[1][0]) + 
                 (point.y * Elements[1][1]) + 
                 Elements[1][2]);
            
        return out;
    }
    
    public Matrix2D rotate(float degrees)
    {
        Matrix2D matrix = new Matrix2D();
        matrix.loadRotation(degrees);
        
        return multiply(matrix);
    }
    
    public Matrix2D translate(float tx, float ty)
    {
        Matrix2D matrix = new Matrix2D();
        matrix.loadTranslation(tx, ty);
        
        return multiply(matrix);
    }
    
    public Matrix2D scale(float sx, float sy)
    {
        Matrix2D matrix = new Matrix2D();
        matrix.loadScaling(sx, sy);
        
        return multiply(matrix);
    }
}
