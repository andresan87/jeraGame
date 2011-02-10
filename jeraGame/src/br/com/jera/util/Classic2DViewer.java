package br.com.jera.util;

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

	public void scroll(Vector2 scroll, Vector2 screenSize) {
		scrollTo(getOrthogonalViewerPos().add(scroll));
	}

	@Override
	public Vector2 getOrthogonalViewerPos() {
		return new Vector2(scrollOrigin);
	}

	@Override
	public Vector3 getEuclideanViewerPos() {
		return new Vector3(new Vector2(scrollOrigin), 0);
	}

	public void setScrollBounds(Vector2 min, Vector2 max, Vector2 screenSize) {
		minScroll = min;
		maxScroll.x = max.x - screenSize.x;
		maxScroll.y = max.y - screenSize.y;
		// maxScroll.x = Math.max(screenSize.x, max.x);
		// maxScroll.y = Math.max(screenSize.y, max.y);
	}
	
	public Vector2 computeAbsolutePosition(Vector2 v) {
		return v.sub(scrollOrigin);
	}

	private Vector2 scrollOrigin = new Vector2();
	private Vector2 minScroll = new Vector2();
	private Vector2 maxScroll = new Vector2();
}
