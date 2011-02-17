package br.com.jera.util;

import br.com.jera.graphic.GraphicDevice;
import br.com.jera.graphic.Sprite;
import br.com.jera.util.CommonMath.Vector2;
import br.com.jera.util.CommonMath.Vector4;

public class BitmapFont extends Sprite {

	public BitmapFont(GraphicDevice graphicDevice, int resourceId, float offset, float height) {
		super(graphicDevice, resourceId, 16, 16);
		this.offset = offset;
		this.height = height;
	}

	public void draw(Vector2 pos, String ansiStr, Vector4 color) {
		Vector2 cursor = new Vector2(pos);
		final int length = ansiStr.length();
		final Vector2 frameSize = super.getFrameSize();
		final Vector2 origin = new Vector2(0, 0);

		super.setColor(color);

		for (int t = 0; t < length; t++) {
			final char currentChar = ansiStr.charAt(t);
			if (currentChar == ' ') {
				cursor.x += offset;
				continue;
			}
			if (currentChar == '\n') {
				cursor.x = pos.x;
				cursor.y += height;
				continue;
			}
			super.draw(cursor, frameSize, 0, origin, currentChar, true);
			cursor.x += offset;
		}
	}

	private float offset, height;
}
