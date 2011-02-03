package br.com.jera.gles1;

import java.io.IOException;
import java.io.InputStream;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import br.com.jera.graphic.Math.Vector2;
import br.com.jera.graphic.Texture;

public class GLESStaticTexture implements Texture {

	private int[] texture = new int[1];
	private GL10 glDevice;
	private Vector2 bitmapSize;
	
	public Vector2 getBitmapSize() {
		return new Vector2(bitmapSize);
	}

	protected GLESStaticTexture(GL10 gl, Context context, int resourceId) {
		
		InputStream is;
		glDevice = gl;
		is = context.getResources().openRawResource(resourceId);

		glDevice.glEnable(GL10.GL_TEXTURE_2D);
		Bitmap bitmap = null;
		try {
			bitmap = BitmapFactory.decodeStream(is);
		} finally {
			try {
				is.close();
				is = null;
			} catch (IOException e) {
			}
		}

		glDevice.glGenTextures(1, texture, 0);

		glDevice.glBindTexture(GL10.GL_TEXTURE_2D, texture[0]);

		glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);
		bitmapSize = new Vector2((float)bitmap.getHeight(), (float)bitmap.getHeight());

		bitmap.recycle();
		glDevice.glDisable(GL10.GL_TEXTURE_2D);
	}

	public void bindTexture() {
		glDevice.glBindTexture(GL10.GL_TEXTURE_2D, texture[0]);
		glDevice.glEnable(GL10.GL_TEXTURE_2D);
	}

	public void unbindTexture() {
		glDevice.glDisable(GL10.GL_TEXTURE_2D);
	}
}
