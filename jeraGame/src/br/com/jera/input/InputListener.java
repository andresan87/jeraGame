package br.com.jera.input;

import br.com.jera.util.CommonMath.Vector2;

public interface InputListener {
	
	public Vector2 getLastTouch();
	public Vector2 getCurrentTouch();
	public Vector2 getTouchMove();

	public Vector2 getLastTouch(final int t);
	public Vector2 getCurrentTouch(final int t);
	public Vector2 getTouchMove(final int t);

	public int getTouchCount();
	public int getMaximumTouches();
	public boolean hasTouchOn(final Vector2 pos, final Vector2 area);
}
