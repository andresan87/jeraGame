package br.com.jera.util;

import java.util.LinkedList;
import java.util.ListIterator;

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
			tiles = new LinkedList<Tile>();

			int index = 0;
			for (float y = 0; y < (float) height; y += 1.0f) {
				for (float x = 0; x < (float) width; x += 1.0f) {
					if (tileIndices[index] >= 0) {
						Tile tile = new Tile(tileIndices[index], new Vector2(x * tileSize.x, y * tileSize.y));
						tiles.add(tile);
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

		public int getTileCount() {
			return tiles.size();
		}

		private int width, height;
		LinkedList<Tile> tiles;
	}

	public SpriteTileMap(final int tileSetId, TileMap tileMap) {
		this.tileMap = tileMap;
		this.tileSetId = tileSetId;
	}

	public void setOrigin(final Vector2 pos) {
		origin = pos;
	}

	@Override
	public void draw(SceneViewer viewer, SpriteResourceManager res) {
		Sprite sprite = res.getSprite(tileSetId);
		ListIterator<Tile> iter = tileMap.tiles.listIterator();
		while (iter.hasNext()) {
			Tile tile = iter.next();
			sprite.draw((origin.sub(viewer.getOrthogonalViewerPos()).add(tile.pos)), sprite.getFrameSize(), 0, new Vector2(0, 0),
					tile.tile, true);
		}
	}

	private int tileSetId;
	private Vector2 origin = new Vector2();
	private TileMap tileMap;

	@Override
	public Vector2 getMin(SpriteResourceManager res) {
		return new Vector2(origin);
	}

	@Override
	public Vector2 getMax(SpriteResourceManager res) {
		Sprite sprite = res.getSprite(tileSetId);
		return new Vector2(getMin(res).add(
				new Vector2((float) tileMap.getWidth() * sprite.getFrameSize().x, (float) tileMap.getHeight() * sprite.getFrameSize().y)));
	}

	@Override
	public void update(long lastFrameDeltaTimeMS) {
		// dummy
	}
}
