package br.com.jera.graphic;

import br.com.jera.util.CommonMath.Vector3;

public interface VertexArray {

	public void setVertices(float[] vertices);
	public int getNumVertices();
	public void drawGeometry(Vector3 pos, Vector3 rot, Vector3 scale); 
}
