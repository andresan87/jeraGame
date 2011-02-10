package br.com.jera.util;

import br.com.jera.graphic.GraphicDevice;
import br.com.jera.input.InputListener;

public interface BaseApplication {

	public void create(GraphicDevice device, InputListener input);
	public void loadResources();
	public void resetFrameBuffer(int width, int height);
	public void update(final long lastFrameDeltaTimeMS);
	public void draw();
}
