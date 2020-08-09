/**
 *
 * @author Richard Eric M. Lope (Relminator)
 * @version 1.00 2014/29/03
 * 
 * Http://rel.phatcode.net
 * 
 * License: GNU LGPLv2 or later
 * 
 */

package net.phatcode.rel.utils;

import java.util.ArrayList;
import java.util.List;


public class Recycler<T>
{
	private List<T> elements = new ArrayList<T>();
	
	private int size;
	private int max;
	private int activeElements = 0;
	
	public Recycler( int size, T e )
	{
		this.max = size;
		this.size = 0;
		this.activeElements = 0;
		
		for( int i = 0; i < (max+1); i++ )
		{
			elements.add(e);
		}
		
	}
	
	public boolean add( T e )
	{
		if( size < max )
		{
			elements.set( size, e );
			size++;
			return true;
		}
		return false;
	}
	
	public boolean remove( int index )
	{
		if( index < size )
		{
			// t = a
			// a = b
			// b = t
			size--;
			elements.set( max, elements.get(index) );  // swap delete index to max
			elements.set( index, elements.get(size) );  // size-1 is ensured to be the last element
			elements.set( size, elements.get(max) );  // swap back so that we still get unique references
			return true;
		}
		return false;
	}
	
	
	public void fill(T e )
	{
		for( int i = 0; i < (max+1); i++ )
		{
			elements.set( i, e );
		}
		
	}
	
	public void clear()
	{
		size = 0;
	}
	
	public T getFreeElement()
	{
		if( size < max )
		{
			T e = elements.get( size );
			size++;
			return e;
		}
		return null;
	}
	
	public T get( int index )
	{
		return elements.get( index );
	}
	
	public int getSize()
	{
		return size;
	}
	
	public int getActiveElements()
	{
		return activeElements;
	}
	
}
