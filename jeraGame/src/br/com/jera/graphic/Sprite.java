package br.com.jera.graphic;

import br.com.jera.graphic.Math.Vector2;
import br.com.jera.graphic.Math.Vector3;
import br.com.jera.graphic.Math.Vector4;
import br.com.jera.graphic.Math.Vertex;

public class Sprite {
	
	public Sprite(GraphicDevice graphicDevice, int resourceId, int rows, int columns) {
		this.graphicDevice = graphicDevice;
		texture = graphicDevice.createStaticTexture(resourceId);
		this.rows = rows;
		this.columns = columns;
		
		Vertex[] vertices = {
				new Vertex(new Vector3(0, 0, 0), new Vector2(0, 0)),
				new Vertex(new Vector3(1, 0, 0), new Vector2(1, 0)),
				new Vertex(new Vector3(1, 1, 0), new Vector2(1, 1)),
				new Vertex(new Vector3(0, 1, 0), new Vector2(0, 1))
		};
	}
	
	public int getRows() {
		return rows;
	}

	public int getColumns() {
		return columns;
	}
	
	public int getNumFrames() {
		return rows*columns;
	}

	public Vector2 getBitmapSize() {
		return texture.getBitmapSize();
	}
	
	public void draw(Vector2 pos, Vector2 size, float angle, Vector4 color, Vector2 normalizedOrigin) {
		// TODO
	}

	int rows, columns;
	VertexArray[] vertexArrays;
	private Texture texture;
	private GraphicDevice graphicDevice;
}
