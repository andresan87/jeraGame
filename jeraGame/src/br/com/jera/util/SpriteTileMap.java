package br.com.jera.util;

import br.com.jera.graphic.GraphicDevice;
import br.com.jera.graphic.Sprite;
import br.com.jera.util.CommonMath.Vector2;

public class SpriteTileMap implements DisplayableEntity {

	public static class Tile {
		public Tile(final int tile, final Vector2 pos) {
			this.pos = pos;
			this.tile = tile;
		}

		public Vector2 pos;
		public int tile;
	}

	public static class TileMap {
		public TileMap(final int width, final int height, final int[] tileIndices, final Vector2 tileSize) {
			final int numTiles = width * height;
			assert (numTiles == tileIndices.length);
			this.width = width;
			this.height = height;
			tiles = new Tile[numTiles];

			int index = 0;
			for (float y = 0; y < (float) height; y += 1.0f) {
				for (float x = 0; x < (float) width; x += 1.0f) {
					if (tileIndices[index] >= 0) {
						tiles[index] = new Tile(tileIndices[index], new Vector2(x * tileSize.x, y * tileSize.y));
					}
					index++;
				}
			}
		}

		public int getWidth() {
			return width;
		}

		public int getHeight() {
			return height;
		}

		final public Tile getTile(final int idx) {
			assert (idx < width * height);
			return tiles[idx];
		}

		public int getTileCount() {
			return tiles.length;
		}

		private int width, height;
		private Tile[] tiles;
	}

	public SpriteTileMap(GraphicDevice device, Sprite tileSet, TileMap tileMap) {
		this.device = device;
		this.tileMap = tileMap;
		this.tileSet = tileSet;
	}

	public void setOrigin(final Vector2 pos) {
		origin = pos;
	}

	@Override
	public void draw(SceneViewer viewer) {
		final int tileCount = tileMap.getTileCount();
		for (int t = 0; t < tileCount; t++) {
			tileSet.draw(origin.add(viewer.getOrthogonalViewerPos()).add(tileMap.getTile(t).pos),
					tileSet.getFrameSize(), 0, new Vector2(0,
					0), tileMap.getTile(t).tile, true);
		}
	}

	Sprite tileSet;
	Vector2 origin = new Vector2();
	GraphicDevice device;
	TileMap tileMap;
	@Override
	public Vector2 getMin() {
		return new Vector2(origin);
	}

	@Override
	public Vector2 getMax() {
		return new Vector2(getMin().add(
				new Vector2((float)tileMap.getWidth()*tileSet.getFrameSize().x, (float)tileMap.getHeight()*tileSet.getFrameSize().y)));
	}
}
