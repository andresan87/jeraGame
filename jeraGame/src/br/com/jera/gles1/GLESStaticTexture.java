package br.com.jera.gles1;

import java.io.IOException;
import java.io.InputStream;
import java.nio.IntBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import br.com.jera.graphic.Texture;
import br.com.jera.util.CommonMath.Vector2;
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
		
		glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_CLAMP_TO_EDGE);
		glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_CLAMP_TO_EDGE);

		glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		glDevice.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		
		bitmapSize = new Vector2((float)width, (float)height);

		{
			int w = width, h = height;
			while ((w & (w-1)) != 0)
				w++;
			while ((h & (h-1)) != 0)
				h++;
			width = w;
			height = h;
		}

		Bitmap pow2Bmp = Bitmap.createScaledBitmap(bitmap, width, height, true);
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, pow2Bmp, 0);

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

	public void delete() {
		glDevice.glDeleteTextures(1, IntBuffer.wrap(texture));
	}
}
