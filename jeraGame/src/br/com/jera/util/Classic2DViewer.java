package br.com.jera.util;

import br.com.jera.util.CommonMath.Vector2;
import br.com.jera.util.CommonMath.Vector3;

public class Classic2DViewer implements SceneViewer {

	public Classic2DViewer(final Vector2 pos) {
		scrollOrigin = pos;
	}

	void setScrollOrigin(final Vector2 origin) {
		scrollOrigin = origin;
	}

	@Override
	public Vector2 getOrthogonalViewerPos() {
		return new Vector2(scrollOrigin);
	}

	@Override
	public Vector3 getEuclideanViewerPos() {
		return new Vector3(new Vector2(scrollOrigin), 0);
	}

	private Vector2 scrollOrigin = new Vector2();
}
