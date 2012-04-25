package se.cth.hedgehogphoto.map;

import static org.junit.Assert.assertTrue;

import java.awt.Point;

import javax.swing.ImageIcon;

import org.junit.Before;
import org.junit.Test;

public class JMarkerTest {
	private JOverlayLabel label;
	private int xPos = 50;
	private int yPos = 70;

	@Before
	public void setUp() throws Exception {
		label = new JMarker(new Point(xPos, yPos));
	}

	@Test
	public void testGetXOffset() {
		int x = label.getXOffset();
		assertTrue(x == 13); //19x19-icon
		
		label.setImageIcon(new ImageIcon("Pictures/markers/marker.png")); //change icon to one sized 19x19
		x = label.getXOffset();
		assertTrue(x == 9);
	}

	@Test
	public void testGetYOffset() {
		int y = label.getYOffset();
		assertTrue(y == 26); //26x26-icon
		
		label.setImageIcon(new ImageIcon("Pictures/markers/marker.png")); //change icon to one sized 19x19
		y = label.getYOffset();
		assertTrue(y == 19);
	}

	@Test
	public void testGetMouseListener() {
		/* Not testable? */
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
		
		x = -1;
		y = 5;
		p.x = p.x + x;
		p.y = p.y + y;
		label.move(x, y);
		assertTrue(p.equals(label.getLocation()));
		
		x = -4;
		y = -5;
		p.x = p.x + x;
		p.y = p.y + y;
		label.move(x, y);
		assertTrue(p.equals(label.getLocation()));
		
		x = 0;
		y = 0;
		p.x = p.x + x;
		p.y = p.y + y;
		label.move(x, y);
		assertTrue(p.equals(label.getLocation()));
	}

	@Test
	public void testInit() {
		Point p = new Point(xPos, yPos);
		assertTrue(!label.getLocation().equals(p));
		p.x -= label.getXOffset();
		p.y -= label.getYOffset();
		assertTrue(label.getLocation().equals(p));
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
