package br.com.jera.util;

import br.com.jera.audio.AudioPlayer;
import br.com.jera.graphic.GraphicDevice;
import br.com.jera.input.InputListener;

public interface BaseApplication {
	
	public enum STATE {
		CONTINUE, EXIT
	}

	public void create(GraphicDevice device, InputListener input, AudioPlayer player);
	public void loadResources();
	public void resetFrameBuffer(int width, int height);
	public STATE update(final long lastFrameDeltaTimeMS);
	public void draw();
	public String getStateName();
}
