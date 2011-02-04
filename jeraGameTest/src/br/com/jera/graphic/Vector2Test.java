package br.com.jera.graphic;

import android.test.AndroidTestCase;
import br.com.jera.graphic.Math.Vector2;

public class Vector2Test extends AndroidTestCase {
	
	public void testVector2() {
		Vector2 v = new Vector2(1,1);
		assertEquals("v.x + v.y must equal 2", 2.0f, v.x+v.y);
	}

}
