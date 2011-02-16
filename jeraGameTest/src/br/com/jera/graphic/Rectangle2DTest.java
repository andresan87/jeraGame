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

		assertEquals("min.x should match", -30.0f, rect.getMin().x);
		assertEquals("min.y should match", -60.0f, rect.getMin().y);
		assertEquals("max.x should match", 70.0f, rect.getMax().x);
		assertEquals("max.y should match", 40.0f, rect.getMax().y);

		Rectangle2D rect0 = new Rectangle2D(new Vector2(30, 60), new Vector2(70, 10));
		Rectangle2D rect1 = new Rectangle2D(new Vector2(0, 0), new Vector2(70, 70));
		assertEquals("return value should match", true, rect0.isIntersecting(rect1));
		
		rect0 = new Rectangle2D(new Vector2(30, 60), new Vector2(70, 10));
		rect1 = new Rectangle2D(new Vector2(0, 0), new Vector2(10, 10));
		assertEquals("return value should match", false, rect0.isIntersecting(rect1));
	}
}
