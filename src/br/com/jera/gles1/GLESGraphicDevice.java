package br.com.jera.gles1;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;
import br.com.jera.graphic.GraphicDevice;
import br.com.jera.graphic.Math.PRIMITIVE_TYPE;
import br.com.jera.graphic.Math.Vector4;
import br.com.jera.graphic.Math.Vertex;
import br.com.jera.graphic.Texture;
import br.com.jera.graphic.VertexArray;

public class GLESGraphicDevice implements GraphicDevice {

	Context context;
	Vector4 backgroundColor;
	GL10 glDevice;
	TEXTURE_FILTER textureFilter;

	public GLESGraphicDevice(GL10 gl, Context context) {
		glDevice = gl;
		this.context = context;
		assert(glDevice != null);
		assert(this.context != null);
	}

	
	public Context getContext() {
		return context;
	}

	public GL10 getGlDevice() {
		return glDevice;
	}

	public Vector4 getBackgroundColor() {
		return backgroundColor;
	}

	public void setup3DView(int width, int height) {
		glDevice.glViewport(0, 0, width, height);
		glDevice.glMatrixMode(GL10.GL_PROJECTION);
		glDevice.glLoadIdentity();
		GLU.gluPerspective(glDevice, 45.0f, (float) width / (float) height,
				0.1f, 100.0f);
		glDevice.glFrontFace(GL10.GL_CW);
		setCullingMode(CULLING_MODE.CULL_CCW);
	}

	public void beginScene() {
		glDevice.glClearColor(backgroundColor.x, backgroundColor.y,
				backgroundColor.z, backgroundColor.w);
		glDevice.glClear(GL10.GL_DEPTH_BUFFER_BIT | GL10.GL_COLOR_BUFFER_BIT);

		glDevice.glMatrixMode(GL10.GL_MODELVIEW);
		glDevice.glLoadIdentity();
	}

	public void endScene() {
	}

	public void setBackgroundColor(Vector4 color) {
		backgroundColor = color;
	}

	public void setCullingMode(CULLING_MODE mode) {
		switch (mode) {
		case CULL_CCW:
			glDevice.glEnable(GL10.GL_CULL_FACE);
			glDevice.glCullFace(GL10.GL_BACK);
			break;
		case CULL_CW:
			glDevice.glEnable(GL10.GL_CULL_FACE);
			glDevice.glCullFace(GL10.GL_FRONT);
			break;
		case CULL_NONE:
			glDevice.glDisable(GL10.GL_CULL_FACE);
			// mGl.glCullFace(GL10.GL_FRONT_AND_BACK);
			break;
		}
	}

	@Override
	public VertexArray createVertexArray(Vertex[] vertices, PRIMITIVE_TYPE type) {
		return new GLESVertexArray(glDevice, vertices, type);
	}

	@Override
	public Texture createStaticTexture(int resourceId) {
		return new GLESStaticTexture(glDevice, context, resourceId);
	}

	@Override
	public void setTextureFilter(TEXTURE_FILTER filter) {
		switch (filter) {
		case LINEAR:
			glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
			glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);
			break;
		case POINT:
		default:
			glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
			glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_NEAREST);
			break;
		}
		textureFilter = filter;
	}
	
	public TEXTURE_FILTER getTextureFilter() {
		return textureFilter;
	}
}
