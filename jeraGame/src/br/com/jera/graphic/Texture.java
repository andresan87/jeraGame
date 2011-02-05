package br.com.jera.graphic;

import br.com.jera.util.Math.Vector2;
public interface Texture {

	void bindTexture();
	void unbindTexture();
	Vector2 getBitmapSize();
}
