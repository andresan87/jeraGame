package br.com.jera.platform.android;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import br.com.jera.gles1.GLESGraphicDevice;
import br.com.jera.graphic.GraphicDevice;
import br.com.jera.input.InputListener;
import br.com.jera.util.BaseApplication;
import br.com.jera.util.CommonMath.Vector2;

public class AndroidSurfaceView extends GLSurfaceView implements InputListener {

	private static final int MAXIMUM_TOUCHES = 5;
	private int touchCont;

	private Vector2[] lastTouch = new Vector2[MAXIMUM_TOUCHES];
	private Vector2[] currentTouch = new Vector2[MAXIMUM_TOUCHES];
	private Vector2[] touchMove = new Vector2[MAXIMUM_TOUCHES];
	private Vector2[] previousTouch = new Vector2[MAXIMUM_TOUCHES];

	private Renderer renderer;

	public AndroidSurfaceView(Context context, BaseApplication app) {
		super(context);
		assert (app != null);
		renderer = new Renderer(context, this, app);
		setRenderer(renderer);
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);

		for (int t = 0; t < MAXIMUM_TOUCHES; t++) {
			touchMove[t] = new Vector2();
			previousTouch[t] = new Vector2();
		}
	}

	public void setApp(BaseApplication app) {
		renderer.setApp(app);
	}

	public int getMaximumTouches() {
		return MAXIMUM_TOUCHES;
	}

	public Vector2 getLastTouch(final int t) {
		assert (t < MAXIMUM_TOUCHES);
		assert (lastTouch != null);
		if (lastTouch[t] != null)
			return new Vector2(lastTouch[t]);
		else
			return null;
	}

	private void resetTouchMove(final int t) {
		assert (t < MAXIMUM_TOUCHES);
		assert (lastTouch != null);
		touchMove[t].x = 0;
		touchMove[t].y = 0;
	}

	@Override
	public Vector2 getTouchMove(final int t) {
		assert (t < MAXIMUM_TOUCHES);
		assert (lastTouch != null);
		if (touchMove[t] != null)
			return new Vector2(touchMove[t]);
		else
			return null;
	}

	@Override
	public Vector2 getCurrentTouch(final int t) {
		if (currentTouch[t] != null)
			return new Vector2(currentTouch[t]);
		else
			return null;
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {

		touchCont = Math.min(MAXIMUM_TOUCHES, event.getPointerCount());
		for (int t = 0; t < touchCont; t++) {
			final float x = event.getX(t);
			final float y = event.getY(t);
			resetTouchMove(t);
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				lastTouch[t] = new Vector2(x, y);
				currentTouch[t] = new Vector2(x, y);
				break;

			case MotionEvent.ACTION_UP:
				lastTouch[t] = null;
				currentTouch[t] = null;
				break;

			case MotionEvent.ACTION_MOVE:
				touchMove[t] = new Vector2(x - previousTouch[t].x, y - previousTouch[t].y);
				currentTouch[t] = new Vector2(x, y);
				break;
			}
			;
			previousTouch[t].x = x;
			previousTouch[t].y = y;
		}
		return true;
	}

	public static class Renderer implements android.opengl.GLSurfaceView.Renderer {

		public Renderer(Context context, InputListener input, BaseApplication app) {
			this.app = app;
			this.input = input;
			this.context = context;
		}

		public void onDrawFrame(GL10 gl) {
			device.setTextureFilter(GraphicDevice.TEXTURE_FILTER.LINEAR);
			final long delta = System.currentTimeMillis() - lastDrawTime;
			lastDrawTime = System.currentTimeMillis();
			app.update(delta);
			app.draw();
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {
			app.resetFrameBuffer(width, height);
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			device = new GLESGraphicDevice(gl, context);
			app.create(device, input);
			app.loadResources();
			lastDrawTime = System.currentTimeMillis();
		}

		public void setApp(BaseApplication app) {
			this.app = app;
		}

		private Context context;
		private GraphicDevice device;
		private BaseApplication app;
		private InputListener input;
		private long lastDrawTime;
	}

	@Override
	public Vector2 getLastTouch() {
		return getLastTouch(0);
	}

	@Override
	public Vector2 getCurrentTouch() {
		return getCurrentTouch(0);
	}

	@Override
	public Vector2 getTouchMove() {
		return getTouchMove(0);
	}

	@Override
	public int getTouchCount() {
		return touchCont;
	}
}
