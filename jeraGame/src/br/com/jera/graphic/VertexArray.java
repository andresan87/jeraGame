package br.com.jera.graphic;

import br.com.jera.util.CommonMath.Vector3;
import br.com.jera.util.CommonMath.Vector4;

public interface VertexArray {

	public void setVertices(float[] vertices);
	public void setColor(Vector4 color);
	public int getNumVertices();
	public void drawGeometry(Vector3 pos, Vector3 rot, Vector3 scale); 
}
