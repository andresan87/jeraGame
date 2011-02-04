package br.com.jera.graphic;

import android.test.AndroidTestCase;
import br.com.jera.graphic.Math.Vector4;

public class Vector4Test extends AndroidTestCase {

	public void testVector4() {
		Vector4 v = new Vector4(1,1,1,1);
		assertEquals("v.x + v.y + v.z + v.w must equal 4", 4.0f, v.x+v.y+v.z+v.w);
	}
	
}
