package br.com.jera.util;

import br.com.jera.util.CommonMath.Rectangle2D;
import br.com.jera.util.CommonMath.Vector2;
import br.com.jera.util.CommonMath.Vector3;

public class Classic2DViewer implements SceneViewer {

	public Classic2DViewer(Vector2 pos) {
		scrollOrigin = pos;
	}

	public void scrollTo(Vector2 origin) {
		scrollOrigin.x = Math.max(Math.min(origin.x, maxScroll.x), minScroll.x);
		scrollOrigin.y = Math.max(Math.min(origin.y, maxScroll.y), minScroll.y);
	}

	public void scroll(Vector2 scroll) {
		scrollTo(getOrthogonalViewerPos().add(scroll));
	}

	public Vector2 getOrthogonalViewerPos() {
		return new Vector2(scrollOrigin);
	}

	public Vector3 getEuclideanViewerPos() {
		return new Vector3(new Vector2(scrollOrigin), 0);
	}

	// TODO se a dimensão do cenário for menor que da tela, sempre centralizar o cenario
	public void setScrollBounds(Vector2 min, Vector2 max, Vector2 screenSize) {
		minScroll = min;
		maxScroll.x = max.x - screenSize.x;
		maxScroll.y = max.y - screenSize.y;
		// maxScroll.x = Math.max(screenSize.x, max.x);
		// maxScroll.y = Math.max(screenSize.y, max.y);
	}

	public boolean isPointOnBorder(Vector2 p, final float width, Rectangle2D clientRect) {
		if (p.x < clientRect.pos.x || p.y < clientRect.pos.y
			|| p.x > clientRect.pos.x+clientRect.size.x || p.y > clientRect.pos.y+clientRect.size.y) {
			return false;
		}
		final Vector2 widthVector = new Vector2(width, width);
		final Vector2 min = widthVector.add(clientRect.pos);
		final Vector2 max = clientRect.pos.add(clientRect.size).sub(widthVector);
		return !CommonMath.isPointInRect(min, max.sub(min), p);
	}

	public Vector2 computeAbsolutePosition(Vector2 v) {
		return v.sub(scrollOrigin);
	}

	public Vector2 computeRelativePosition(Vector2 v) {
		return v.add(scrollOrigin);
	}

	private Vector2 scrollOrigin = new Vector2();
	private Vector2 minScroll = new Vector2();
	private Vector2 maxScroll = new Vector2();
}
