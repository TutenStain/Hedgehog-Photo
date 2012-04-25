package se.cth.hedgehogphoto.map;

import static org.junit.Assert.assertTrue;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class JMarkerTest {
	private JOverlayLabel label;

	@Before
	public void setUp() throws Exception {
		label = new JMarker();
	}

	@Test
	public void testGetXOffset() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetYOffset() {
		//fail("Not yet implemented");
	}

	@Test
	public void testLabelPointsFromLeft() {
		//fail("Not yet implemented");
	}

	@Test
	public void testLabelPointsFromTop() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetMouseListener() {
		//fail("Not yet implemented");
	}

	@Test
	public void testJMarker() {
		assertTrue(label.getImageIcon() != null);
		assertTrue(label.getIcon() != null);
		assertTrue(label.getIconWidth() == label.getImageIcon().getIconWidth());
	}

	@Test
	public void testMove() {
		Point p = label.getLocation();
		int x = 3;
		int y = 2;
		p.x = p.x + x;
		p.y = p.y + y;
		label.move(x, y);
		assertTrue(p.equals(label.getLocation()));
	}

	@Test
	public void testInit() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetPoint() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetXPosition() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetYPosition() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetPixelPosition() {
		//fail("Not yet implemented");
	}

	@Test
	public void testPropertyChange() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetImageIcon() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetImageIcon() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetIconHeight() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetIconHeight() {
		//fail("Not yet implemented");
	}

	@Test
	public void testGetIconWidth() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetIconWidth() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetProperIconSize() {
		//fail("Not yet implemented");
	}

	@Test
	public void testIntersects() {
		//fail("Not yet implemented");
	}

	@Test
	public void testSetBoundsIntInt() {
		//fail("Not yet implemented");
	}

}
