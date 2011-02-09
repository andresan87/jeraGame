package br.com.jera.util;

import br.com.jera.util.CommonMath.Vector2;
import br.com.jera.util.CommonMath.Vector3;

public interface SceneViewer {
	public Vector2 getOrthogonalViewerPos();
	public Vector3 getEuclideanViewerPos();
}
