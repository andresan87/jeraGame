package br.com.jera.graphic;

import br.com.jera.graphic.Math.Vector2;

public interface GraphicDevice {

	public void setup3DView(int width, int height);
	public void setup2DView(int width, int height);
	public void setup3DView();
	public void setup2DView();
	
	public Vector2 getScreenSize();
	
	public void beginScene();
	public void endScene();
	
	enum CULLING_MODE {
		CULL_CCW, CULL_CW, CULL_NONE
	}
	public void setCullingMode(CULLING_MODE mode);
	public CULLING_MODE getCullingMode();

	enum TEXTURE_FILTER {
		LINEAR, POINT
	}
	public void setTextureFilter(TEXTURE_FILTER filter);
	public TEXTURE_FILTER getTextureFilter();
	
	public void setBackgroundColor(Math.Vector4 color);
	
	public VertexArray createVertexArray(Math.Vertex[] vertices, Math.PRIMITIVE_TYPE type);
	public Texture createStaticTexture(int resourceId);
	
	public void setTextureWrap(boolean enable);
	public boolean getTextureWrap();
	
	public void setDepthTest(boolean enable);
	public boolean getDepthTest();
}
