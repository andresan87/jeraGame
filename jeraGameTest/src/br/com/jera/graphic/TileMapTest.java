package br.com.jera.graphic;

import android.test.AndroidTestCase;
import br.com.jera.util.CommonMath.Vector2;
import br.com.jera.util.SpriteTileMap.TileMap;

public class TileMapTest extends AndroidTestCase {

	public void testTileMap() {
		final int[] tiles = new int[] { -1, -1, 2, -1, 3, -1, 0, 0, 0, -1, 0, 0, };

		TileMap tileMap = new TileMap(6, 2, tiles, new Vector2(32, 32));
		assertEquals("Tile count should match", 7, tileMap.getTileCount());
	}

}
