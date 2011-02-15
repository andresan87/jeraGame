package br.com.jera.graphic;

import android.test.AndroidTestCase;
import br.com.jera.util.CommonMath.Rectangle2D;
import br.com.jera.util.CommonMath.Vector2;

public class Rectangle2DTest extends AndroidTestCase {

	public void testRectangle2D() {
		Rectangle2D rect = new Rectangle2D(new Vector2(20, 40), new Vector2(40, 80));
		Vector2 m = rect.getCenter();
		assertEquals("x should match", 30, m.x);
		assertEquals("y should match", 60, m.y);
	}
}
