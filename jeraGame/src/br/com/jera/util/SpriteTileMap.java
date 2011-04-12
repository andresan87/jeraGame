package br.com.jera.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.ListIterator;

import br.com.jera.audio.AudioPlayer;
import br.com.jera.graphic.Sprite;
import br.com.jera.util.CommonMath.Rectangle2D;
import br.com.jera.util.CommonMath.Vector2;
import br.com.jera.util.CommonMath.Vector3;

public class SpriteTileMap implements DisplayableEntity {

	public static class Tile implements Comparable<Tile> {
		public Tile(final int tile, final Vector2 pos, Vector2 size) {
			this.pos = pos;
			this.tile = tile;
			this.size = size;
		}

		public int compareTo(Tile another) {
			return tile - another.tile;
		}

		public Vector2 getTileCenter() {
			return pos.add(size.multiply(0.5f));
		}

		public Vector2 pos, size;
		public int tile;
	}
	
	public Vector2 get2DPos() {
		return new Vector2(origin);
	}

	public Vector3 getPos() {
		return new Vector3(origin, 0);
	}

	public static class TileMap {
		public TileMap(final int width, final int height, final int[] tileIndices, final Vector2 tileSize) {
			final int numTiles = width * height;
			assert (numTiles == tileIndices.length);
			this.width = width;
			this.height = height;
			tiles = new ArrayList<Tile>();

			int index = 0;
			for (float y = 0; y < (float) height; y += 1.0f) {
				for (float x = 0; x < (float) width; x += 1.0f) {
					if (tileIndices[index] >= 0) {
						Tile tile = new Tile(tileIndices[index], new Vector2(x * tileSize.x, y * tileSize.y), tileSize);
						tiles.add(tile);
					}
					index++;
				}
			}
			Collections.sort(tiles);
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

		public boolean isPointOnRoad(Vector2 p) {
			ListIterator<Tile> iter = tiles.listIterator();
			while (iter.hasNext()) {
				Tile tile = iter.next();
				if (CommonMath.isPointInRect(tile.pos, tile.size, p)) {
					return true;
				}
			}
			return false;
		}

		private int width, height;
		public ArrayList<Tile> tiles;
	}

	public boolean isPointOnRoad(Vector2 p) {
		return tileMap.isPointOnRoad(p);
	}

	public SpriteTileMap(final int tileSetId, TileMap tileMap) {
		this.tileMap = tileMap;
		this.tileSetId = tileSetId;
	}

	public void setOrigin(final Vector2 pos) {
		origin = pos;
	}

	public void draw(SceneViewer viewer, SpriteResourceManager res) {
		ListIterator<Tile> iter = tileMap.tiles.listIterator();
		Sprite sprite = res.getSprite(tileSetId);
		while (iter.hasNext()) {
			Tile tile = iter.next();
			sprite.draw((origin.sub(viewer.getOrthogonalViewerPos()).add(tile.pos)), sprite.getFrameSize(), 0, new Vector2(0, 0),
					tile.tile, true);
		}
	}

	private int tileSetId;
	private Vector2 origin = new Vector2();
	private TileMap tileMap;

	public Vector2 getMin(SpriteResourceManager res) {
		return new Vector2(origin);
	}

	public Vector2 getMax(SpriteResourceManager res) {
		Sprite sprite = res.getSprite(tileSetId);
		return new Vector2(getMin(res).add(
				new Vector2((float) tileMap.getWidth() * sprite.getFrameSize().x, (float) tileMap.getHeight() * sprite.getFrameSize().y)));
	}

	public void update(long lastFrameDeltaTimeMS, AudioPlayer audioPlayer) {
		// dummy
	}

	public int compareTo(DisplayableEntity another) {
		return 0;
	}

	public boolean isVisible(SceneViewer viewer, Rectangle2D clientRect) {
		return true;
	}
}
