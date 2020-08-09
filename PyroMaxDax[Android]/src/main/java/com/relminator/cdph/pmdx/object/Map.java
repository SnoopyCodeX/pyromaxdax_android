package com.relminator.cdph.pmdx.object;

public class Map
{
	public enum TileType {
		TILE_VINE_SHORT_RIGHT,
		TILE_VINE_SHORT_LEFT,
		TILE_VINE_LONG_RIGHT,
		TILE_VINE_LONG_LEFT,
		TILE_VINE_SHORT,
		TILE_VINE_LONG,
		TILE_FENCE_TOP,
		TILE_FENCE,
		TILE_SIGN,
		TILE_LEFT_RIGHT_WATER,
		TILE_LEFT_WATER,
		TILE_RIGHT_WATER,
		TILE_TOP_WATER,
		TILE_WATER,
		TILE_NONE,
		TILE_MUD,
		TILE_TRIGGER,
		TILE_SOLID,
		TILE_ICE,
		TILE_SEMI_ICE,
		TILE_SPIKE_CEILING,
		TILE_SPIKE_FLOOR,
		TILE_RUBBER,
		TILE_SOFT_BRICK,
		TILE_SOFT_ICE,
		TILE_PLATFORM_COLLISION
	}
	
	public Tile3D.TileType Collision;
	public int Index;
	
	public final class MapUtil
	{
		public Tile3D.TileType getTile(int x, int y, Map[][] map)
		{
			return map[x / Globals.TILE_SIZE][y / Globals.TILE_SIZE].Collision;
		}
	}
}
