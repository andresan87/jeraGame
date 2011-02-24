package br.com.jera.platform.android;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import android.view.WindowManager;
import br.com.jera.audio.AudioPlayer;
import br.com.jera.gles1.GLESGraphicDevice;
import br.com.jera.graphic.GraphicDevice;
import br.com.jera.input.InputListener;
import br.com.jera.util.BaseApplication;
import br.com.jera.util.CommonMath.Vector2;

public class AndroidSurfaceView extends GLSurfaceView implements InputListener {

	private static final int MAXIMUM_TOUCHES = 5;
	private int touchCount;

	private int[] touchStepCount = new int[MAXIMUM_TOUCHES];
	private KEY_STATE[] touchState = new KEY_STATE[MAXIMUM_TOUCHES];
	private Vector2[] lastTouch = new Vector2[MAXIMUM_TOUCHES];
	private Vector2[] currentTouch = new Vector2[MAXIMUM_TOUCHES];
	private Vector2[] touchMove = new Vector2[MAXIMUM_TOUCHES];
	private Vector2[] previousTouch = new Vector2[MAXIMUM_TOUCHES];

	private Renderer renderer;

	public AndroidSurfaceView(Activity activity, BaseApplication app) {
		super(activity);
		renderer = new Renderer(activity, this, new AndroidAudioPlayer(activity), app);
		setRenderer(renderer);
		setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

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
		Vector2 lastTouchRef = lastTouch[t]; 
		if (lastTouchRef != null)
			return new Vector2(lastTouchRef);
		else
			return null;
	}

	private void resetTouchMove(final int t) {
		touchMove[t].x = 0;
		touchMove[t].y = 0;
	}

	@Override
	public Vector2 getTouchMove(final int t) {
		Vector2 touchMoveRef = touchMove[t];
		if (touchMoveRef != null)
			return new Vector2(touchMoveRef);
		else
			return null;
	}

	@Override
	public Vector2 getCurrentTouch(final int t) {
		Vector2 currentTouchRef = currentTouch[t]; 
		if (currentTouchRef != null)
			return new Vector2(currentTouchRef);
		else
			return null;
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {

		touchCount = Math.min(MAXIMUM_TOUCHES, event.getPointerCount());
		for (int t = 0; t < touchCount; t++) {
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
			previousTouch[t].x = x;
			previousTouch[t].y = y;
		}
		return true;
	}

	@Override
	public void update() {
		for (int t = 0; t < MAXIMUM_TOUCHES; t++) {
			Vector2 currentTouchRef = currentTouch[t];
			if (currentTouchRef != null) {
				touchStepCount[t]++;
				if (touchStepCount[t] == 1) {
					touchState[t] = KEY_STATE.HIT;
				} else {
					touchState[t] = KEY_STATE.DOWN;
				}
			} else {
				if (touchStepCount[t] != 0) {
					touchState[t] = KEY_STATE.RELEASE;
				} else {
					touchState[t] = KEY_STATE.UP;
				}
				touchStepCount[t] = 0;
			}
		}		
	}

	public static class Renderer implements android.opengl.GLSurfaceView.Renderer {

		public Renderer(Activity activity, InputListener input, AudioPlayer player, BaseApplication app) {
			this.app = app;
			this.input = input;
			this.activity = activity;
			this.player = player;
		}

		public void onDrawFrame(GL10 gl) {
			device.setTextureFilter(GraphicDevice.TEXTURE_FILTER.LINEAR);
			input.update();
			final long delta = Math.min(System.currentTimeMillis() - lastDrawTime, MAX_DELTA_TIME);
			lastDrawTime = System.currentTimeMillis();
			if (app.update(delta) == BaseApplication.STATE.EXIT) {
				activity.finish();
			}
			app.draw();
		}

		public void onSurfaceChanged(GL10 gl, int width, int height) {
			app.resetFrameBuffer(width, height);
		}

		public void onSurfaceCreated(GL10 gl, EGLConfig config) {
			device = new GLESGraphicDevice(gl, activity);
			app.create(device, input, player);
			app.loadResources();
			lastDrawTime = System.currentTimeMillis();
		}

		public void setApp(BaseApplication app) {
			this.app = app;
		}

		private Activity activity;
		private GraphicDevice device;
		private BaseApplication app;
		private InputListener input;
		private long lastDrawTime;
		private final long MAX_DELTA_TIME = 1000;
		private AudioPlayer player;
	}

	@Override
	public KEY_STATE getTouchState(int t) {
		return touchState[t];
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
		return touchCount;
	}

	@Override
	public boolean hasTouchOn(final Vector2 pos, final Vector2 area) {
		for (int t = 0; t < touchCount; t++) {
			final Vector2 current = currentTouch[t];
			if (current != null) {
				if (!(current.x < pos.x || current.y < pos.y
					||current.x > pos.x+area.x || current.y > pos.y+area.y)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean isLastTouchOn(final Vector2 pos, final Vector2 area) {
		for (int t = 0; t < touchCount; t++) {
			final Vector2 last = lastTouch[t];
			if (last != null) {
				if (!(last.x < pos.x || last.y < pos.y
					||last.x > pos.x+area.x || last.y > pos.y+area.y)) {
					return true;
				}
			}
		}
		return false;
	}
}
