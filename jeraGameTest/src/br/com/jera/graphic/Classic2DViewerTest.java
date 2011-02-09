package br.com.jera.graphic;

import android.test.AndroidTestCase;
import br.com.jera.util.Classic2DViewer;
import br.com.jera.util.CommonMath.Vector2;

public class Classic2DViewerTest extends AndroidTestCase {

	public void testClassic2DViewerBigScenario() {
		final Vector2 screenSize = new Vector2(400, 600);
		Classic2DViewer viewer = new Classic2DViewer(new Vector2(0, 0));
		viewer.setScrollBounds(new Vector2(-1000, -800), new Vector2(1000, 800), screenSize);

		viewer.scrollTo(new Vector2(4000, 8000));
		assertEquals("Scroll clamp x should match", 600.0f, viewer.getOrthogonalViewerPos().x);
		assertEquals("Scroll clamp y should match", 200.0f, viewer.getOrthogonalViewerPos().y);

		viewer.scrollTo(new Vector2(-4000, -8000));
		assertEquals("Scroll clamp x should match", -1000.0f, viewer.getOrthogonalViewerPos().x);
		assertEquals("Scroll clamp y should match", -800.0f, viewer.getOrthogonalViewerPos().y);
	}

	public void testClassic2DViewerSmallScenario() {
		final Vector2 screenSize = new Vector2(400, 600);
		Classic2DViewer viewer = new Classic2DViewer(new Vector2(0, 0));
		viewer.setScrollBounds(new Vector2(-10, -80), new Vector2(10, 80), screenSize);

		viewer.scrollTo(new Vector2(4000, 8000));
		assertEquals("Scroll clamp x should match", -10.0f, viewer.getOrthogonalViewerPos().x);
		assertEquals("Scroll clamp y should match", -80.0f, viewer.getOrthogonalViewerPos().y);

		viewer.scrollTo(new Vector2(-4000, -8000));
		assertEquals("Scroll clamp x should match", -10.0f, viewer.getOrthogonalViewerPos().x);
		assertEquals("Scroll clamp y should match", -80.0f, viewer.getOrthogonalViewerPos().y);
	}

}
