package br.com.jera.gles1;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLU;
import br.com.jera.graphic.GraphicDevice;
import br.com.jera.graphic.Texture;
import br.com.jera.graphic.VertexArray;
import br.com.jera.util.CommonMath.PRIMITIVE_TYPE;
import br.com.jera.util.CommonMath.Vector2;
import br.com.jera.util.CommonMath.Vector4;
import br.com.jera.util.CommonMath.Vertex;

public class GLESGraphicDevice implements GraphicDevice {

	Context context;
	Vector4 backgroundColor;
	GL10 glDevice;
	TEXTURE_FILTER textureFilter;
	CULLING_MODE cullingMode;
	ALPHA_MODE alphaMode;
	boolean depthTestEnabled;
	int screenWidth;
	int screenHeight;
	boolean textureWrap;
	final float ALPHAREF = 0.05f;

	public GLESGraphicDevice(GL10 gl, Context context) {
		glDevice = gl;
		this.context = context;
		assert(glDevice != null);
		assert(this.context != null);
		setDepthTest(false);
		setTextureFilter(TEXTURE_FILTER.LINEAR);
		setTextureWrap(false);
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
	
	private void setupViewport(int width, int height) {
		glDevice.glViewport(0, 0, width, height);
	}

	public void setup3DView(int width, int height) {
		screenWidth = width;
		screenHeight = height;
		setupViewport(width, height);
		glDevice.glMatrixMode(GL10.GL_PROJECTION);
		glDevice.glLoadIdentity();
		GLU.gluPerspective(glDevice, 45.0f, (float) width / (float) height,
				0.1f, 100.0f);
		glDevice.glFrontFace(GL10.GL_CW);
		setCullingMode(CULLING_MODE.CULL_CCW);
	}

	@Override
	public void setup2DView(int width, int height) {
		screenWidth = width;
		screenHeight = height;
		setupViewport(width, height);
		glDevice.glMatrixMode(GL10.GL_PROJECTION);
		glDevice.glLoadIdentity();

		final float zn =-1;
		final float zf = 1;
		float[] matrix = {
				2.0f/(float)width, 0, 0, 0,
				0, 2.0f/(float)height, 0, 0,
				0, 0, 1.0f/(zf-zn), 0,
				0, 0, zn/(zn-zf),1
		};
		glDevice.glMultMatrixf(FloatBuffer.wrap(matrix));
		setCullingMode(CULLING_MODE.CULL_NONE);
		setTextureWrap(false);
	}

	public void beginScene() {
		glDevice.glClearColor(backgroundColor.x, backgroundColor.y,
				backgroundColor.z, backgroundColor.w);
		glDevice.glClearDepthf(1.0f);
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
		cullingMode = mode;
	}

	@Override
	public CULLING_MODE getCullingMode() {
		return cullingMode;
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
	
	@Override
	public TEXTURE_FILTER getTextureFilter() {
		return textureFilter;
	}

	@Override
	public void setDepthTest(boolean enable) {
		if (enable) {
			glDevice.glEnable(GL10.GL_DEPTH_TEST);
			glDevice.glDepthFunc(GL10.GL_LEQUAL);
		} else {
			glDevice.glDisable(GL10.GL_DEPTH_TEST);
		}
		depthTestEnabled = enable;
	}

	@Override
	public boolean getDepthTest() {
		return depthTestEnabled;
	}

	@Override
	public void setup3DView() {
		setup3DView(screenWidth, screenHeight);
	}

	@Override
	public void setup2DView() {
		setup2DView(screenWidth, screenHeight);
	}

	@Override
	public Vector2 getScreenSize() {
		return new Vector2((float)screenWidth, (float)screenHeight);
	}

	@Override
	public void setTextureWrap(boolean enable) {
		textureWrap = enable;
		if (enable) {
			glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
			glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);
		} else {
			glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
			glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);
		}
	}

	@Override
	public boolean getTextureWrap() {
		return textureWrap;
	}

	@Override
	public void setAlphaMode(ALPHA_MODE mode) {
		switch(mode)
		{
		case DEFAULT:
			glDevice.glEnable(GL10.GL_BLEND);
			glDevice.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
			glDevice.glAlphaFunc(GL10.GL_GREATER, ALPHAREF);
			glDevice.glEnable(GL10.GL_ALPHA_TEST);
			break;
		case ADD:
			glDevice.glEnable(GL10.GL_BLEND);
			glDevice.glBlendFunc(GL10.GL_ONE, GL10.GL_ONE);
			glDevice.glAlphaFunc(GL10.GL_GREATER, ALPHAREF);
			glDevice.glEnable(GL10.GL_ALPHA_TEST);
			break;
		case ALPHA_TEST_ONLY:
			glDevice.glAlphaFunc(GL10.GL_GREATER, ALPHAREF);
			glDevice.glEnable(GL10.GL_ALPHA_TEST);
			glDevice.glDisable(GL10.GL_BLEND);
		break;
		case MODULATE:
			glDevice.glEnable(GL10.GL_BLEND);
			glDevice.glBlendFunc(GL10.GL_ZERO, GL10.GL_SRC_COLOR);
			glDevice.glAlphaFunc(GL10.GL_GREATER, ALPHAREF);
			glDevice.glEnable(GL10.GL_ALPHA_TEST);
		break;
		case NONE:
		default:
			glDevice.glDisable(GL10.GL_BLEND);
			glDevice.glDisable(GL10.GL_ALPHA_TEST);
			break;
		};
		alphaMode = mode;
	}

	@Override
	public ALPHA_MODE getAlphaMode() {
		return alphaMode;
	}
}
