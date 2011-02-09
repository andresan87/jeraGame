package br.com.jera.graphic;

import br.com.jera.util.CommonMath;
import br.com.jera.util.CommonMath.Vector2;
import br.com.jera.util.CommonMath.Vector4;
import br.com.jera.util.CommonMath.Vertex;

public interface GraphicDevice {

	public void setup3DView(int width, int height);
	public void setup2DView(int width, int height);
	public void setup3DView();
	public void setup2DView();
	
	public Vector2 getScreenSize();
	
	public void beginScene();
	public void endScene();
	
	public enum ALPHA_MODE {
		ALPHA_TEST_ONLY, DEFAULT, ADD, MODULATE, NONE
	}
	public void setAlphaMode(ALPHA_MODE mode);
	public ALPHA_MODE getAlphaMode();
	
	public enum CULLING_MODE {
		CULL_CCW, CULL_CW, CULL_NONE
	}
	public void setCullingMode(CULLING_MODE mode);
	public CULLING_MODE getCullingMode();

	public enum TEXTURE_FILTER {
		LINEAR, POINT
	}
	public void setTextureFilter(TEXTURE_FILTER filter);
	public TEXTURE_FILTER getTextureFilter();
	
	public void setBackgroundColor(Vector4 color);
	
	public VertexArray createVertexArray(Vertex[] vertices, CommonMath.PRIMITIVE_TYPE type);
	public Texture createStaticTexture(int resourceId);
	
	public void setTextureWrap(boolean enable);
	public boolean getTextureWrap();
	
	public void setDepthTest(boolean enable);
	public boolean getDepthTest();
}
