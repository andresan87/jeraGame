package br.com.jera.graphic;

public interface GraphicDevice {

	public void setup3DView(int width, int height);
	public void beginScene();
	public void endScene();
	
	enum CULLING_MODE {
		CULL_CCW, CULL_CW, CULL_NONE
	}
	public void setCullingMode(CULLING_MODE mode);

	enum TEXTURE_FILTER {
		LINEAR, POINT
	}
	public void setTextureFilter(TEXTURE_FILTER filter);
	
	public void setBackgroundColor(Math.Vector4 color);
	
	public VertexArray createVertexArray(Math.Vertex[] vertices, Math.PRIMITIVE_TYPE type);
	public Texture createStaticTexture(int resourceId);
}
