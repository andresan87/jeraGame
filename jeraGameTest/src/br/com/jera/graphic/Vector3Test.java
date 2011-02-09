package br.com.jera.graphic;

import android.test.AndroidTestCase;
import br.com.jera.util.CommonMath.Vector2;
import br.com.jera.util.CommonMath.Vector3;

public class Vector3Test extends AndroidTestCase {
	
	public void testVector3() {
		{
			Vector3 v = new Vector3(1,1,1);
			assertEquals("v.x + v.y + v.z should match", 3.0f, v.x+v.y+v.z);
		}
		{
			Vector3 v = new Vector3(new Vector2(1,1), 1); 
			assertEquals("v.x + v.y + v.z should match", 3.0f, v.x+v.y+v.z);
		}
	}

}
