package br.com.jera.platform.android;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;
import br.com.jera.input.InputListener;
import br.com.jera.util.Math.Vector2;

public class AndroidSurfaceView extends GLSurfaceView implements InputListener {

	private Vector2 lastTouch;
	private Vector2 currentTouch;
	private Vector2 touchMove = new Vector2();
	private Vector2 previousTouch = new Vector2();
	
	public AndroidSurfaceView(Context context) {
		super(context);
	}
	
	public Vector2 getLastTouch() {
		if (lastTouch != null)
			return new Vector2(lastTouch);
		else
			return null;
	}
	
	private void resetTouchMove() {
		touchMove.x = 0;
		touchMove.y = 0;
	}

	@Override
	public boolean onTouchEvent(final MotionEvent event) {
		final float x = event.getX();
		final float y = event.getY();
		resetTouchMove();
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			lastTouch = new Vector2(x, y);
			currentTouch = new Vector2(x, y);
			break;

		case MotionEvent.ACTION_UP:
			lastTouch = null;
			currentTouch = null;
			break;

		case MotionEvent.ACTION_MOVE:
			touchMove = new Vector2(x-previousTouch.x, y-previousTouch.y);
			currentTouch = new Vector2(x, y);
			break;
		};
		previousTouch.x = x;
		previousTouch.y = y;
		return true;
	}

	@Override
	public Vector2 getCurrentTouch() {
		if (currentTouch != null)
			return new Vector2(currentTouch);
		else
			return null;
	}

	@Override
	public Vector2 getTouchMove() {
		if (touchMove != null)
			return new Vector2(touchMove);
		else
			return null;
	}
}
