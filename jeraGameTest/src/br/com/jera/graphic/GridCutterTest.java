package br.com.jera.graphic;

import android.test.AndroidTestCase;

public class GridCutterTest extends AndroidTestCase {
	
	public void testGridCutter() {
		Math math = new Math();
		Math.GridCutter gc = new Math.GridCutter(4,2);
		/*
		for (int t=0; t<gc.rects.length; t++) {
			assertEquals("SizeX must be 0.25", 0.25f, gc.rects[t].size.x); 
			assertEquals("SizeY must be 0.5", 0.5f, gc.rects[t].size.y); 
		}
		assertEquals("Position assert 1 failed", 0.25f, gc.rects[1].pos.x);
		assertEquals("Position assert 2 failed", 1.0f, gc.rects[4].pos.y);
		assertEquals("Position assert 3 failed", 0.75f, gc.rects[7].pos.x);
		assertEquals("Position assert 4 failed", 0.5f, gc.rects[7].pos.y);*/
	}

}
