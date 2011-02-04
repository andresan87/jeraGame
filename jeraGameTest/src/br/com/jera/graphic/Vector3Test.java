package br.com.jera.graphic;

import android.test.AndroidTestCase;
import br.com.jera.graphic.Math.Vector3;

public class Vector3Test extends AndroidTestCase {
	
	public void testVector3() {
		Vector3 v = new Vector3(1,1,1);
		assertEquals("v.x + v.y + v.z must equal 3", 3.0f, v.x+v.y+v.z);
	}

}
