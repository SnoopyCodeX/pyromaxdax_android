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

package net.phatcode.rel;

import net.phatcode.rel.utils.ImageTextureData;

public class ImageTextureDataLevelImages implements ImageTextureData
{
	
	@Override
	public int[] getArray()
	{
		return textureCoords;
	}

	@Override
	public int getNumImages()
	{
		return textureCoords.length/4;
	}

	private int textureCoords[] = 
	{
			 0, 418, 512, 20,				// 0
			 0, 392, 512, 26,				// 1
			 0, 260, 512, 66,				// 2
			 0, 326, 512, 66,				// 3
			 0, 0, 512, 130,				// 4
			 0, 130, 512, 130,				// 5
	}; 
	
}
