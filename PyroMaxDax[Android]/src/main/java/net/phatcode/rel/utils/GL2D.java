package net.phatcode.rel.utils;



public class GL2D
{
	public int current_texture = 0;
	public int font_list_base = 0;
	public int font_list_offset = 0;
	public int font_textureID = 0;
	public int glow_textureID = 0;
	public int font_numGlyphs = 0;
	
	public float CubeVectors[] = {
		(-0.5f), (-0.5f), ( 0.5f),
		( 0.5f), (-0.5f), ( 0.5f),
		( 0.5f), (-0.5f), (-0.5f),
		(-0.5f), (-0.5f), (-0.5f),
		(-0.5f), ( 0.5f), ( 0.5f), 
		( 0.5f), ( 0.5f), ( 0.5f),
		( 0.5f), ( 0.5f), (-0.5f),
		(-0.5f), ( 0.5f), (-0.5f) 
	};
	
	public float CubeFaces[] = {
		3, 2, 1, 0,
		0, 1, 5, 4,
		1, 2, 6, 5,
		2, 3, 7, 6,
		3, 0, 4, 7,
		4, 5, 6, 7
	};
	
	private int loadGlowImage()
	{
		final int IMAGE_WIDTH = 32, IMAGE_HEIGHT = 32, IMAGE_BITDEPTH = 24;
		
		int image_array[] = {
			0x00000007, 0x00000004, 0x00000020, 0x00000020, 0x00000080, 0x00000000, 
			0x00000000, 0x00000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF010101, 0xFF010101, 0xFF020202, 
			0xFF020202, 0xFF030303, 0xFF030303, 0xFF030303, 0xFF020202, 0xFF020202, 
			0xFF010101, 0xFF010101, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF010101, 0xFF020202, 0xFF030303, 
			0xFF040404, 0xFF050505, 0xFF060606, 0xFF060606, 0xFF060606, 0xFF060606, 
			0xFF060606, 0xFF050505, 0xFF040404, 0xFF030303, 0xFF020202, 0xFF010101, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF010101, 0xFF020202, 0xFF040404, 
			0xFF050505, 0xFF070707, 0xFF080808, 0xFF090909, 0xFF0A0A0A, 0xFF0B0B0B, 
			0xFF0B0B0B, 0xFF0B0B0B, 0xFF0A0A0A, 0xFF090909, 0xFF080808, 0xFF070707, 
			0xFF050505, 0xFF040404, 0xFF020202, 0xFF010101, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF010101, 0xFF030303, 
			0xFF050505, 0xFF070707, 0xFF090909, 0xFF0B0B0B, 0xFF0C0C0C, 0xFF0E0E0E, 
			0xFF0F0F0F, 0xFF0F0F0F, 0xFF101010, 0xFF0F0F0F, 0xFF0F0F0F, 0xFF0E0E0E, 
			0xFF0C0C0C, 0xFF0B0B0B, 0xFF090909, 0xFF070707, 0xFF050505, 0xFF030303, 
			0xFF010101, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF010101, 
			0xFF030303, 0xFF060606, 0xFF080808, 0xFF0B0B0B, 0xFF0D0D0D, 0xFF0F0F0F, 
			0xFF111111, 0xFF131313, 0xFF141414, 0xFF151515, 0xFF151515, 0xFF151515, 
			0xFF141414, 0xFF131313, 0xFF111111, 0xFF0F0F0F, 0xFF0D0D0D, 0xFF0B0B0B, 
			0xFF080808, 0xFF060606, 0xFF030303, 0xFF010101, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF010101, 0xFF030303, 0xFF060606, 0xFF090909, 0xFF0C0C0C, 0xFF0E0E0E, 
			0xFF121212, 0xFF141414, 0xFF161616, 0xFF181818, 0xFF191919, 0xFF1A1A1A, 
			0xFF1B1B1B, 0xFF1A1A1A, 0xFF191919, 0xFF181818, 0xFF161616, 0xFF141414, 
			0xFF121212, 0xFF0E0E0E, 0xFF0C0C0C, 0xFF090909, 0xFF060606, 0xFF030303, 
			0xFF010101, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF010101, 0xFF030303, 0xFF060606, 0xFF090909, 0xFF0C0C0C, 
			0xFF0F0F0F, 0xFF131313, 0xFF161616, 0xFF191919, 0xFF1B1B1B, 0xFF1E1E1E, 
			0xFF1F1F1F, 0xFF202020, 0xFF212121, 0xFF202020, 0xFF1F1F1F, 0xFF1E1E1E, 
			0xFF1B1B1B, 0xFF191919, 0xFF161616, 0xFF131313, 0xFF0F0F0F, 0xFF0C0C0C, 
			0xFF090909, 0xFF060606, 0xFF030303, 0xFF010101, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF020202, 0xFF050505, 0xFF080808, 
			0xFF0C0C0C, 0xFF0F0F0F, 0xFF131313, 0xFF171717, 0xFF1B1B1B, 0xFF1E1E1E, 
			0xFF212121, 0xFF232323, 0xFF252525, 0xFF262626, 0xFF272727, 0xFF262626, 
			0xFF252525, 0xFF232323, 0xFF212121, 0xFF1E1E1E, 0xFF1B1B1B, 0xFF171717, 
			0xFF131313, 0xFF0F0F0F, 0xFF0C0C0C, 0xFF080808, 0xFF050505, 0xFF020202, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF010101, 0xFF040404, 
			0xFF070707, 0xFF0B0B0B, 0xFF0E0E0E, 0xFF131313, 0xFF171717, 0xFF1B1B1B, 
			0xFF1F1F1F, 0xFF232323, 0xFF262626, 0xFF292929, 0xFF2B2B2B, 0xFF2C2C2C, 
			0xFF2C2C2C, 0xFF2C2C2C, 0xFF2B2B2B, 0xFF292929, 0xFF262626, 0xFF232323, 
			0xFF1F1F1F, 0xFF1B1B1B, 0xFF171717, 0xFF131313, 0xFF0E0E0E, 0xFF0B0B0B, 
			0xFF070707, 0xFF040404, 0xFF010101, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF020202, 0xFF050505, 0xFF090909, 0xFF0D0D0D, 0xFF121212, 0xFF161616, 
			0xFF1B1B1B, 0xFF1F1F1F, 0xFF242424, 0xFF282828, 0xFF2B2B2B, 0xFF2E2E2E, 
			0xFF313131, 0xFF323232, 0xFF323232, 0xFF323232, 0xFF313131, 0xFF2E2E2E, 
			0xFF2B2B2B, 0xFF282828, 0xFF242424, 0xFF1F1F1F, 0xFF1B1B1B, 0xFF161616, 
			0xFF121212, 0xFF0D0D0D, 0xFF090909, 0xFF050505, 0xFF020202, 0xFF000000, 
			0xFF000000, 0xFF010101, 0xFF030303, 0xFF070707, 0xFF0B0B0B, 0xFF0F0F0F, 
			0xFF141414, 0xFF191919, 0xFF1E1E1E, 0xFF232323, 0xFF282828, 0xFF2C2C2C, 
			0xFF303030, 0xFF333333, 0xFF363636, 0xFF383838, 0xFF383838, 0xFF383838, 
			0xFF363636, 0xFF333333, 0xFF303030, 0xFF2C2C2C, 0xFF282828, 0xFF232323, 
			0xFF1E1E1E, 0xFF191919, 0xFF141414, 0xFF0F0F0F, 0xFF0B0B0B, 0xFF070707, 
			0xFF030303, 0xFF010101, 0xFF000000, 0xFF010101, 0xFF040404, 0xFF080808, 
			0xFF0C0C0C, 0xFF111111, 0xFF161616, 0xFF1B1B1B, 0xFF212121, 0xFF262626, 
			0xFF2B2B2B, 0xFF303030, 0xFF343434, 0xFF383838, 0xFF3B3B3B, 0xFF3D3D3D, 
			0xFF3D3D3D, 0xFF3D3D3D, 0xFF3B3B3B, 0xFF383838, 0xFF343434, 0xFF303030, 
			0xFF2B2B2B, 0xFF262626, 0xFF212121, 0xFF1B1B1B, 0xFF161616, 0xFF111111, 
			0xFF0C0C0C, 0xFF080808, 0xFF040404, 0xFF010101, 0xFF000000, 0xFF020202, 
			0xFF050505, 0xFF090909, 0xFF0E0E0E, 0xFF131313, 0xFF181818, 0xFF1E1E1E, 
			0xFF232323, 0xFF292929, 0xFF2E2E2E, 0xFF333333, 0xFF383838, 0xFF3C3C3C, 
			0xFF3F3F3F, 0xFF424242, 0xFF424242, 0xFF424242, 0xFF3F3F3F, 0xFF3C3C3C, 
			0xFF383838, 0xFF333333, 0xFF2E2E2E, 0xFF292929, 0xFF232323, 0xFF1E1E1E, 
			0xFF181818, 0xFF131313, 0xFF0E0E0E, 0xFF090909, 0xFF050505, 0xFF020202, 
			0xFF000000, 0xFF020202, 0xFF060606, 0xFF0A0A0A, 0xFF0F0F0F, 0xFF141414, 
			0xFF191919, 0xFF1F1F1F, 0xFF252525, 0xFF2B2B2B, 0xFF313131, 0xFF363636, 
			0xFF3B3B3B, 0xFF3F3F3F, 0xFF434343, 0xFF454545, 0xFF474747, 0xFF454545, 
			0xFF434343, 0xFF3F3F3F, 0xFF3B3B3B, 0xFF363636, 0xFF313131, 0xFF2B2B2B, 
			0xFF252525, 0xFF1F1F1F, 0xFF191919, 0xFF141414, 0xFF0F0F0F, 0xFF0A0A0A, 
			0xFF060606, 0xFF020202, 0xFF000000, 0xFF030303, 0xFF060606, 0xFF0B0B0B, 
			0xFF0F0F0F, 0xFF151515, 0xFF1A1A1A, 0xFF202020, 0xFF262626, 0xFF2C2C2C, 
			0xFF323232, 0xFF383838, 0xFF3D3D3D, 0xFF424242, 0xFF454545, 0xFFAEAEAE, 
			0xFFD3D3D3, 0xFF454545, 0xFF454545, 0xFF424242, 0xFF3D3D3D, 0xFF383838, 
			0xFF323232, 0xFF2C2C2C, 0xFF262626, 0xFF202020, 0xFF1A1A1A, 0xFF151515, 
			0xFF0F0F0F, 0xFF0B0B0B, 0xFF060606, 0xFF030303, 0xFF000000, 0xFF030303, 
			0xFF060606, 0xFF0B0B0B, 0xFF101010, 0xFF151515, 0xFF1B1B1B, 0xFF212121, 
			0xFF272727, 0xFF2C2C2C, 0xFF323232, 0xFF383838, 0xFF3D3D3D, 0xFF424242, 
			0xFF474747, 0xFFD3D3D3, 0xFFFFFFFF, 0xFF454545, 0xFF474747, 0xFF424242, 
			0xFF3D3D3D, 0xFF383838, 0xFF323232, 0xFF2C2C2C, 0xFF272727, 0xFF212121, 
			0xFF1B1B1B, 0xFF151515, 0xFF101010, 0xFF0B0B0B, 0xFF060606, 0xFF030303, 
			0xFF000000, 0xFF030303, 0xFF060606, 0xFF0B0B0B, 0xFF0F0F0F, 0xFF151515, 
			0xFF1A1A1A, 0xFF202020, 0xFF262626, 0xFF2C2C2C, 0xFF323232, 0xFF383838, 
			0xFF3D3D3D, 0xFF424242, 0xFF454545, 0xFF454545, 0xFF454545, 0xFF454545, 
			0xFF454545, 0xFF424242, 0xFF3D3D3D, 0xFF383838, 0xFF323232, 0xFF2C2C2C, 
			0xFF262626, 0xFF202020, 0xFF1A1A1A, 0xFF151515, 0xFF0F0F0F, 0xFF0B0B0B, 
			0xFF060606, 0xFF030303, 0xFF000000, 0xFF020202, 0xFF060606, 0xFF0A0A0A, 
			0xFF0F0F0F, 0xFF141414, 0xFF191919, 0xFF1F1F1F, 0xFF252525, 0xFF2B2B2B, 
			0xFF313131, 0xFF363636, 0xFF3B3B3B, 0xFF3F3F3F, 0xFF434343, 0xFF454545, 
			0xFF474747, 0xFF454545, 0xFF434343, 0xFF3F3F3F, 0xFF3B3B3B, 0xFF363636, 
			0xFF313131, 0xFF2B2B2B, 0xFF252525, 0xFF1F1F1F, 0xFF191919, 0xFF141414, 
			0xFF0F0F0F, 0xFF0A0A0A, 0xFF060606, 0xFF020202, 0xFF000000, 0xFF020202, 
			0xFF050505, 0xFF090909, 0xFF0E0E0E, 0xFF131313, 0xFF181818, 0xFF1E1E1E, 
			0xFF232323, 0xFF292929, 0xFF2E2E2E, 0xFF333333, 0xFF383838, 0xFF3C3C3C, 
			0xFF3F3F3F, 0xFF424242, 0xFF424242, 0xFF424242, 0xFF3F3F3F, 0xFF3C3C3C, 
			0xFF383838, 0xFF333333, 0xFF2E2E2E, 0xFF292929, 0xFF232323, 0xFF1E1E1E, 
			0xFF181818, 0xFF131313, 0xFF0E0E0E, 0xFF090909, 0xFF050505, 0xFF020202, 
			0xFF000000, 0xFF010101, 0xFF040404, 0xFF080808, 0xFF0C0C0C, 0xFF111111, 
			0xFF161616, 0xFF1B1B1B, 0xFF212121, 0xFF262626, 0xFF2B2B2B, 0xFF303030, 
			0xFF343434, 0xFF383838, 0xFF3B3B3B, 0xFF3D3D3D, 0xFF3D3D3D, 0xFF3D3D3D, 
			0xFF3B3B3B, 0xFF383838, 0xFF343434, 0xFF303030, 0xFF2B2B2B, 0xFF262626, 
			0xFF212121, 0xFF1B1B1B, 0xFF161616, 0xFF111111, 0xFF0C0C0C, 0xFF080808, 
			0xFF040404, 0xFF010101, 0xFF000000, 0xFF010101, 0xFF030303, 0xFF070707, 
			0xFF0B0B0B, 0xFF0F0F0F, 0xFF141414, 0xFF191919, 0xFF1E1E1E, 0xFF232323, 
			0xFF282828, 0xFF2C2C2C, 0xFF303030, 0xFF333333, 0xFF363636, 0xFF383838, 
			0xFF383838, 0xFF383838, 0xFF363636, 0xFF333333, 0xFF303030, 0xFF2C2C2C, 
			0xFF282828, 0xFF232323, 0xFF1E1E1E, 0xFF191919, 0xFF141414, 0xFF0F0F0F, 
			0xFF0B0B0B, 0xFF070707, 0xFF030303, 0xFF010101, 0xFF000000, 0xFF000000, 
			0xFF020202, 0xFF050505, 0xFF090909, 0xFF0D0D0D, 0xFF121212, 0xFF161616, 
			0xFF1B1B1B, 0xFF1F1F1F, 0xFF242424, 0xFF282828, 0xFF2B2B2B, 0xFF2E2E2E, 
			0xFF313131, 0xFF323232, 0xFF323232, 0xFF323232, 0xFF313131, 0xFF2E2E2E, 
			0xFF2B2B2B, 0xFF282828, 0xFF242424, 0xFF1F1F1F, 0xFF1B1B1B, 0xFF161616, 
			0xFF121212, 0xFF0D0D0D, 0xFF090909, 0xFF050505, 0xFF020202, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF010101, 0xFF040404, 0xFF070707, 0xFF0B0B0B, 
			0xFF0E0E0E, 0xFF131313, 0xFF171717, 0xFF1B1B1B, 0xFF1F1F1F, 0xFF232323, 
			0xFF262626, 0xFF292929, 0xFF2B2B2B, 0xFF2C2C2C, 0xFF2C2C2C, 0xFF2C2C2C, 
			0xFF2B2B2B, 0xFF292929, 0xFF262626, 0xFF232323, 0xFF1F1F1F, 0xFF1B1B1B, 
			0xFF171717, 0xFF131313, 0xFF0E0E0E, 0xFF0B0B0B, 0xFF070707, 0xFF040404, 
			0xFF010101, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF020202, 
			0xFF050505, 0xFF080808, 0xFF0C0C0C, 0xFF0F0F0F, 0xFF131313, 0xFF171717, 
			0xFF1B1B1B, 0xFF1E1E1E, 0xFF212121, 0xFF232323, 0xFF252525, 0xFF262626, 
			0xFF272727, 0xFF262626, 0xFF252525, 0xFF232323, 0xFF212121, 0xFF1E1E1E, 
			0xFF1B1B1B, 0xFF171717, 0xFF131313, 0xFF0F0F0F, 0xFF0C0C0C, 0xFF080808, 
			0xFF050505, 0xFF020202, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF010101, 0xFF030303, 0xFF060606, 0xFF090909, 0xFF0C0C0C, 
			0xFF0F0F0F, 0xFF131313, 0xFF161616, 0xFF191919, 0xFF1B1B1B, 0xFF1E1E1E, 
			0xFF1F1F1F, 0xFF202020, 0xFF212121, 0xFF202020, 0xFF1F1F1F, 0xFF1E1E1E, 
			0xFF1B1B1B, 0xFF191919, 0xFF161616, 0xFF131313, 0xFF0F0F0F, 0xFF0C0C0C, 
			0xFF090909, 0xFF060606, 0xFF030303, 0xFF010101, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF010101, 0xFF030303, 
			0xFF060606, 0xFF090909, 0xFF0C0C0C, 0xFF0E0E0E, 0xFF121212, 0xFF141414, 
			0xFF161616, 0xFF181818, 0xFF191919, 0xFF1A1A1A, 0xFF1B1B1B, 0xFF1A1A1A, 
			0xFF191919, 0xFF181818, 0xFF161616, 0xFF141414, 0xFF121212, 0xFF0E0E0E, 
			0xFF0C0C0C, 0xFF090909, 0xFF060606, 0xFF030303, 0xFF010101, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF010101, 0xFF030303, 0xFF060606, 0xFF080808, 0xFF0B0B0B, 
			0xFF0D0D0D, 0xFF0F0F0F, 0xFF111111, 0xFF131313, 0xFF141414, 0xFF151515, 
			0xFF151515, 0xFF151515, 0xFF141414, 0xFF131313, 0xFF111111, 0xFF0F0F0F, 
			0xFF0D0D0D, 0xFF0B0B0B, 0xFF080808, 0xFF060606, 0xFF030303, 0xFF010101, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF010101, 0xFF030303, 
			0xFF050505, 0xFF070707, 0xFF090909, 0xFF0B0B0B, 0xFF0C0C0C, 0xFF0E0E0E, 
			0xFF0F0F0F, 0xFF0F0F0F, 0xFF101010, 0xFF0F0F0F, 0xFF0F0F0F, 0xFF0E0E0E, 
			0xFF0C0C0C, 0xFF0B0B0B, 0xFF090909, 0xFF070707, 0xFF050505, 0xFF030303, 
			0xFF010101, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF010101, 0xFF020202, 0xFF040404, 0xFF050505, 0xFF070707, 
			0xFF080808, 0xFF090909, 0xFF0A0A0A, 0xFF0B0B0B, 0xFF0B0B0B, 0xFF0B0B0B, 
			0xFF0A0A0A, 0xFF090909, 0xFF080808, 0xFF070707, 0xFF050505, 0xFF040404, 
			0xFF020202, 0xFF010101, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF010101, 
			0xFF020202, 0xFF030303, 0xFF040404, 0xFF050505, 0xFF060606, 0xFF060606, 
			0xFF060606, 0xFF060606, 0xFF060606, 0xFF050505, 0xFF040404, 0xFF030303, 
			0xFF020202, 0xFF010101, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF010101, 0xFF010101, 0xFF020202, 
			0xFF020202, 0xFF030303, 0xFF030303, 0xFF030303, 0xFF020202, 0xFF020202, 
			0xFF010101, 0xFF010101, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 
			0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000, 0xFF000000
		};
		
		int textureID = 0; //LoadImage(@image_array(0), GL_LINEAR)
		return textureID;
	}
	
	
}
