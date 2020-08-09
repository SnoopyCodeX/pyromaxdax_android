package net.phatcode.rel.utils;

public class Array 
{
    public static final int ubound(Object[][] arr, int dimension)
	{
		if(arr.length > 1)
			return arr[dimension-1].length;
		return arr.length;
	}
	
	public static final int ubound(Object[][] arr)
	{
		return ubound(arr, 1);
	}
}
