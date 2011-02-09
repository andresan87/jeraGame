package br.com.jera.graphic;

import android.test.AndroidTestCase;
import br.com.jera.util.CommonMath.Vector2;
import br.com.jera.util.CommonMath.Vector3;
import br.com.jera.util.CommonMath.Vector4;
import br.com.jera.util.CommonMath.Vertex;

public class VertexTest extends AndroidTestCase {

	public void testVertex() {
		{
			Vertex[] vertices = {
					new Vertex(new Vector3(1, 1, 1), new Vector2(1, 1)),
					new Vertex(new Vector3(1, 1, 1), new Vector2(1, 1)),
					new Vertex(new Vector3(1, 1, 1), new Vector2(1, 1)),
				};
			
			for (int t = 0; t < vertices.length; t++) {
				assertNotNull("position object can't be null", vertices[t].pos);
				assertNotNull("texCoord object can't be null", vertices[t].texCoord);
				assertNull("color must be null", vertices[t].color);
			}
		}
		{
			Vertex[] vertices = {
					new Vertex(new Vector3(1, 1, 1), new Vector4(1, 1, 1, 1)),
					new Vertex(new Vector3(1, 1, 1), new Vector4(1, 1, 1, 1)),
					new Vertex(new Vector3(1, 1, 1), new Vector4(1, 1, 1, 1)),
				};
			
			for (int t = 0; t < vertices.length; t++) {
				assertNotNull("position object can't be null", vertices[t].pos);
				assertNotNull("color object can't be null", vertices[t].color);
				assertNull("texCoord must be null", vertices[t].texCoord);
			}
		}
	}
}
