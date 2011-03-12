package br.com.jera.util;

import java.util.HashMap;

import br.com.jera.graphic.GraphicDevice;
import br.com.jera.graphic.Sprite;

public class SpriteResourceManager {

	private HashMap<Integer, Sprite> map = new HashMap<Integer, Sprite>();
	private GraphicDevice device;

	public SpriteResourceManager(GraphicDevice device) {
		this.device = device;
	}
	
	public GraphicDevice getGraphicDevice() {
		return device;
	}

	public void loadResource(final int id, final int columns, final int rows) {
		Sprite sprite;
		if ((sprite = map.get(id)) != null) {
			sprite.delete();
		}
		sprite = new Sprite(device, id, columns, rows);
		map.put(new Integer(id), sprite);
	}
	
	public Sprite getSprite(final int id) {
		return map.get(new Integer(id));
	}
}
