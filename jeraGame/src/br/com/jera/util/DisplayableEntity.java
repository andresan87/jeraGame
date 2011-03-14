package br.com.jera.util;

import br.com.jera.audio.AudioPlayer;
import br.com.jera.util.CommonMath.Rectangle2D;
import br.com.jera.util.CommonMath.Vector2;
import br.com.jera.util.CommonMath.Vector3;

public interface DisplayableEntity extends Comparable<DisplayableEntity> {

	public void draw(final SceneViewer viewer, SpriteResourceManager res);
	public Vector2 getMin(SpriteResourceManager res);
	public Vector2 getMax(SpriteResourceManager res);
	public Vector3 getPos();
	public void update(long lastFrameDeltaTimeMS, AudioPlayer audioPlayer);
	public boolean isVisible(final SceneViewer viewer, Rectangle2D clientRect);
}
