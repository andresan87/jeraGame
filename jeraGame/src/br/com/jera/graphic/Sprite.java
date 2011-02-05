package br.com.jera.graphic;

import br.com.jera.graphic.Math.GridCutter;
import br.com.jera.graphic.Math.PRIMITIVE_TYPE;
import br.com.jera.graphic.Math.Rectangle2D;
import br.com.jera.graphic.Math.Vector2;
import br.com.jera.graphic.Math.Vector3;
import br.com.jera.graphic.Math.Vertex;

public class Sprite {
	
	public Sprite(GraphicDevice graphicDevice, int resourceId, int columns, int rows) {
		//this.graphicDevice = graphicDevice;
		texture = graphicDevice.createStaticTexture(resourceId);
		this.rows = rows;
		this.columns = columns;
		
		GridCutter cutter = new GridCutter(columns, rows);
		Rectangle2D[] rects = cutter.rects;
		
		vertexArrays = new VertexArray[rects.length];
		for (int t=0; t<rects.length; t++) {
			Vertex[] vertices = {
					new Vertex(new Vector3(0, 0, 0), new Vector2(rects[t].pos.x, rects[t].pos.y)),
					new Vertex(new Vector3(1, 0, 0), new Vector2(rects[t].pos.x+rects[t].size.x, rects[t].pos.y)),
					new Vertex(new Vector3(1, 1, 0), new Vector2(rects[t].pos.x+rects[t].size.x, rects[t].pos.y+rects[t].size.y)),
					new Vertex(new Vector3(0, 1, 0), new Vector2(rects[t].pos.x, rects[t].pos.y+rects[t].size.y))
			};
			vertexArrays[t] = graphicDevice.createVertexArray(vertices, PRIMITIVE_TYPE.TRIANGLE_FAN);
		}
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

	public void draw(Vector2 pos, Vector2 size, float angle, Vector2 normalizedOrigin, int frame) {
		assert(frame < getNumFrames() && frame >= 0);

		texture.bindTexture();

		Vector2 bitmapSize = getBitmapSize();
		Vector2 originOffset = Math.computeSpriteOriginOffset(bitmapSize, normalizedOrigin);
		originOffset = originOffset.add(new Vector2(0.375f,0.375f));
		float[] vertices = {
				originOffset.x, originOffset.y, 0,
				size.x+originOffset.x, originOffset.y, 0,
				size.x+originOffset.x, size.y+originOffset.y, 0,
				originOffset.x, size.y+originOffset.y, 0
		};
		vertexArrays[frame].setVertices(vertices);
		vertexArrays[frame].drawGeometry(new Vector3(pos, 0), new Vector3(0,0,angle),
				new Vector3(new Vector2(1,-1), 1));
		texture.unbindTexture();
	}

	int rows, columns;
	VertexArray[] vertexArrays;
	private Texture texture;
	//private GraphicDevice graphicDevice;
}