package br.com.jera.graphic;

import android.test.AndroidTestCase;
import br.com.jera.util.CommonMath.Rectangle2D;
import br.com.jera.util.CommonMath.Vector2;

public class Rectangle2DTest extends AndroidTestCase {

	public void testRectangle2D() {
		Rectangle2D rect = new Rectangle2D(new Vector2(20, 40), new Vector2(40, 80));
		Vector2 m = rect.getCenter();
		assertEquals("x should match", 40.0f, m.x);
		assertEquals("y should match", 80.0f, m.y);
		
		rect = new Rectangle2D(new Vector2(0, 0), new Vector2(100, 100), new Vector2(0.4f, 0.5f));
		assertEquals("pos.x should match", -40.0f, rect.pos.x);
		assertEquals("pos.y should match", -50.0f, rect.pos.y);
		assertEquals("pos.x should match", 100.0f, rect.size.x);
		assertEquals("pos.y should match", 100.0f, rect.size.y);

		rect = new Rectangle2D(new Vector2(10, -10), new Vector2(100, 100), new Vector2(0.4f, 0.5f));
		assertEquals("pos.x should match", -30.0f, rect.pos.x);
		assertEquals("pos.y should match", -60.0f, rect.pos.y);
		assertEquals("pos.x should match", 100.0f, rect.size.x);
		assertEquals("pos.y should match", 100.0f, rect.size.y);
	}
}
