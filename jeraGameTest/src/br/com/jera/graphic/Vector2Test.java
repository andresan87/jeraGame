package br.com.jera.graphic;

import android.test.AndroidTestCase;
import br.com.jera.util.CommonMath;
import br.com.jera.util.CommonMath.Vector2;

public class Vector2Test extends AndroidTestCase {

	public void testVector2() {
		Vector2 v = new Vector2(1, 1);
		assertEquals("v.x + v.y should match", 2.0f, v.x + v.y);
	}

	public void testAdd() {
		Vector2 a = new Vector2(3, 3);
		Vector2 b = new Vector2(4, 4);
		Vector2 c = a.add(b);
		assertEquals("c.x should match", 7.0f, c.x);
		assertEquals("c.y should match", 7.0f, c.y);
	}

	public void testSub() {
		Vector2 a = new Vector2(3, 3);
		Vector2 b = new Vector2(3, 3);
		Vector2 c = a.sub(b);
		assertEquals("c.x should match", 0.0f, c.x);
		assertEquals("c.y should match", 0.0f, c.y);
	}

	public void testLength() {
		assertEquals("length should match", 10.0f, new Vector2(8, 6).length());
	}

	public void testDistance() {
		Vector2 a = new Vector2(-8, -6);
		assertEquals("distance should match", 20.0f, a.distance(new Vector2(8, 6)));
	}

	public void testNormalize() {
		Vector2 a = new Vector2(-112, 0).normalize();
		assertEquals("a.x should match", -1.0f, a.x);
		assertEquals("a.y should match", 0.0f, a.y);
	}

	public void testMutlplyVector2() {
		Vector2 a = new Vector2(1, 1);
		Vector2 b = new Vector2(2, 3);
		Vector2 c = a.multiply(b);
		assertEquals("c.x should match", 2.0f, c.x);
		assertEquals("c.y should match", 3.0f, c.y);
	}

	public void testMultiplyFloat() {
		Vector2 a = new Vector2(1.5f, 1.5f);
		a = a.multiply(2.0f);
		assertEquals("a.x should match", 3.0f, a.x);
		assertEquals("a.y should match", 3.0f, a.y);
	}

	public void textComputeSpriteOriginOffset() {
		Vector2 v = CommonMath.computeSpriteOriginOffset(new Vector2(100, 50), new Vector2(0.5f, 0.5f));
		assertEquals("v.x should match", -50.0f, v.x);
		assertEquals("v.y should match", -25.0f, v.y);
	}
}
