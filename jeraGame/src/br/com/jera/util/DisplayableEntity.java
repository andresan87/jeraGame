package br.com.jera.util;

import br.com.jera.util.CommonMath.Vector2;

public interface DisplayableEntity {

	public void draw(final SceneViewer viewer);
	public Vector2 getMin();
	public Vector2 getMax();
}
