package br.com.jera.input;

import br.com.jera.util.Math.Vector2;

public interface InputListener {
	
	public Vector2 getLastTouch();
	public Vector2 getCurrentTouch();
	public Vector2 getTouchMove();

}
