package br.com.jera.gles1;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import br.com.jera.graphic.Math;
import br.com.jera.graphic.Math.Vector3;
import br.com.jera.graphic.VertexArray;

public class GLESVertexArray extends VertexArray {

	private GL10 glDevice;
	private FloatBuffer positionsBuffer;
	private FloatBuffer colorsBuffer;
	private FloatBuffer texCoordsBuffer;
	private int vertexCount;
	private Math.PRIMITIVE_TYPE primitiveType;
	
	public int getPositionBufferLength() {
		return positionsBuffer.array().length;
	}

	public int getColorsBufferLength() {
		return colorsBuffer.array().length;
	}

	public int getTexCoordsBufferLength() {
		return texCoordsBuffer.array().length;
	}
	
	public int getVertexCount() {
		return vertexCount;
	}
	
	public Math.PRIMITIVE_TYPE getPrimitiveType() {
		return primitiveType;
	}

	protected GLESVertexArray(GL10 gl, Math.Vertex[] vertices, Math.PRIMITIVE_TYPE type) {

		glDevice = gl;
		primitiveType = type;
		vertexCount = vertices.length;

		float[] positions = new float[vertices.length * 3];
		float[] colors = new float[vertices.length * 4];
		float[] texCoords = new float[vertices.length * 2];

		int nColors = 0, nPositions = 0, nTexCoords = 0;
		for (int v = 0; v < vertices.length; v++) {
			if (vertices[v].pos != null) {
				positions[v * 3 + 0] = vertices[v].pos.x;
				positions[v * 3 + 1] = vertices[v].pos.y;
				positions[v * 3 + 2] = vertices[v].pos.z;
				nPositions++;
			}
			if (vertices[v].color != null) {
				colors[v * 4 + 0] = vertices[v].color.x;
				colors[v * 4 + 1] = vertices[v].color.y;
				colors[v * 4 + 2] = vertices[v].color.z;
				colors[v * 4 + 3] = vertices[v].color.w;
				nColors++;
			}
			if (vertices[v].texCoord != null) {
				texCoords[v * 2 + 0] = vertices[v].texCoord.x;
				texCoords[v * 2 + 1] = vertices[v].texCoord.y;
				nTexCoords++;
			}
		}

		if (nColors == vertices.length)
			colorsBuffer = FloatBuffer.wrap(colors);
		if (nPositions == vertices.length)
			positionsBuffer = FloatBuffer.wrap(positions);
		if (nTexCoords == vertices.length)
			texCoordsBuffer = FloatBuffer.wrap(texCoords);
	}

	@Override
	public void drawGeometry(Vector3 pos, Vector3 rot, Vector3 scale) {

		glDevice.glMatrixMode(GL10.GL_MODELVIEW);
		glDevice.glLoadIdentity();
		glDevice.glScalef(scale.x, scale.y, scale.z);
		glDevice.glTranslatef(pos.x, pos.y, pos.z);
		glDevice.glRotatef(rot.x, 1, 0, 0);
		glDevice.glRotatef(rot.y, 0, 1, 0);
		glDevice.glRotatef(rot.z, 0, 0, 1);

		if (positionsBuffer != null)
			glDevice.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		if (colorsBuffer != null)
			glDevice.glEnableClientState(GL10.GL_COLOR_ARRAY);
		if (texCoordsBuffer != null)
			glDevice.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

		if (positionsBuffer != null)
			glDevice.glVertexPointer(3, GL10.GL_FLOAT, 0, positionsBuffer);
		if (colorsBuffer != null)
			glDevice.glColorPointer(4, GL10.GL_FLOAT, 0, colorsBuffer);
		if (texCoordsBuffer != null)
			glDevice.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texCoordsBuffer);

		glDevice.glDrawArrays(getGLPrimitiveType(primitiveType), 0, vertexCount);

		if (positionsBuffer != null)
			glDevice.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if (colorsBuffer != null)
			glDevice.glDisableClientState(GL10.GL_COLOR_ARRAY);
		if (texCoordsBuffer != null)
			glDevice.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	}

	private int getGLPrimitiveType(Math.PRIMITIVE_TYPE type) {
		switch (type) {
		case TRIANGLE_FAN:
			return GL10.GL_TRIANGLE_FAN;
		case TRIANGLE_STRIP:
			return GL10.GL_TRIANGLE_STRIP;
		case TRIANGLE_LIST:
		default:
			return GL10.GL_TRIANGLES;
			// TODO lines, points...
		}
	}
}
