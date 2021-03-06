package se.cth.hedgehogphoto.map.model;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.awt.Dimension;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import se.cth.hedgehogphoto.database.Location;
import se.cth.hedgehogphoto.database.Picture;
import se.cth.hedgehogphoto.database.PictureObject;

public class MarkerModelTest {
	private MarkerModel model;
	private Picture picture;
	private Location location;
	private int xPos = 50;
	private int yPos = 70;

	@Before
	public void setUp() throws Exception {
		this.picture = new Picture();
		this.picture.setName("Bild");
		this.picture.setPath(Global.ZOOM); //just a string
		this.location = new Location();
		this.location.setLocation("Sverige");
		this.location.setLongitude(13);
		this.location.setLatitude(37);
		this.picture.setLocation(this.location);
		this.model = new MarkerModel(this.picture);
		this.model.setPosition(xPos, yPos);
	}

	@Test
	public void testGetXOffset() {
		int x = model.getXOffset();
		assertTrue(x == 8); //17x22-icon
		
		model.setIconPath("Pictures/markers/marker.png"); //change icon to one sized 19x19
		x = model.getXOffset();
		assertTrue(x == 9);
	}

	@Test
	public void testGetYOffset() {
		int y = model.getYOffset();
		assertTrue(y == 22); //17x22-icon
		
		model.setIconPath("Pictures/markers/marker.png"); //change icon to one sized 19x19
		y = model.getYOffset();
		assertTrue(y == 19);
	}

	@Test
	public void testGetPictures() {
		List<PictureObject> pictures = new LinkedList<PictureObject>();
		assertTrue(pictures.size() == 0);
		assertTrue(model.getPictures(pictures).size() == 1);
		assertTrue(pictures.size() == 1);
		assertTrue(pictures.get(0) == picture);
	}

	@Test
	public void testComputeNumberOfLocations() {
		assertTrue(model.computeNumberOfLocations() == 1); //markermodel always has one picture only
	}

	@Test
	public void testHandleZoom() {
		model.handleZoom();
		assertTrue(model.isVisible()); 
	}

	@Test
	public void testMarkerModel() {
		fail("Not yet implemented"); //Constructor-testing
	}

	@Test
	public void testGetPicture() {
		assertTrue(model.getPicture().equals(this.picture)); //equals
		assertTrue(model.getPicture() == this.picture); //same reference
		
		this.picture = new Picture();
		this.picture.setName("Crazyness");
		this.picture.setPath(Global.ICON_UPDATE); //just a string
		this.picture.setLocation(this.location);
		assertTrue(this.model.getPicture() != this.picture);
		this.model = new MarkerModel(this.picture);
		assertTrue(this.model.getPicture() == this.picture);
	}

	@Test
	public void testGetLongitude() {
		this.picture.setLocation(this.location);
		model = new MarkerModel(this.picture);
		assertTrue(model.getLongitude() == location.getLongitude());
		this.location.setLongitude(449.2);
		assertTrue(model.getLongitude() == 449.2);
	}

	@Test
	public void testGetLatitude() {
		this.picture.setLocation(this.location);
		model = new MarkerModel(this.picture);
		assertTrue(model.getLatitude() == location.getLatitude());
		this.location.setLatitude(99.999);
		assertTrue(model.getLatitude() == 99.999);
	}

	@Test
	public void testInitialize() {
		this.model.initialize();
		assertTrue(this.model.getPosition().equals(new Point(0,0)));
	}

	@Test
	public void testGetProperComponentWidth() {
		assertTrue(model.getProperComponentWidth() == 17); //bad test
	}

	@Test
	public void testGetProperComponentHeight() {
		assertTrue(model.getProperComponentHeight() == 22); //bad test
	}

	@Test
	public void testGetNumberOfPictures() {
		assertTrue(model.getNumberOfLocations() == 1); //SHOULD always be 1
	}

	@Test
	public void testSetNumberOfPictures() {
		model.setNumberOfLocations(9);
		assertTrue(model.getNumberOfLocations() != 9);
		model.setNumberOfLocations(-2);
		assertTrue(model.getNumberOfLocations() != -2); 
		model.setNumberOfLocations(1);
		assertTrue(model.getNumberOfLocations() == 1);
	}

	@Test
	public void testGetIconPath() {
		assertTrue(model.getIconPath().equals(Global.MARKER_ICON_PATH));
	}

	@Test
	public void testSetIconPath() {
		model.setIconPath(Global.DRAG_EVENT);
		assertTrue(model.getIconPath().equals(Global.DRAG_EVENT));
	}

	@Test
	public void testMove() {
		Point p = model.getPosition();
		int x = 3;
		int y = 2;
		p.x = p.x + x;
		p.y = p.y + y;
		model.move(x, y);
		assertTrue(p.equals(model.getPosition()));
		
		x = -1;
		y = 5;
		p.x = p.x + x;
		p.y = p.y + y;
		model.move(x, y);
		assertTrue(p.equals(model.getPosition()));
		
		x = -4;
		y = -5;
		p.x = p.x + x;
		p.y = p.y + y;
		model.move(x, y);
		assertTrue(p.equals(model.getPosition()));
		
		x = 0;
		y = 0;
		p.x = p.x + x;
		p.y = p.y + y;
		model.move(x, y);
		assertTrue(p.equals(model.getPosition()));
	}

	@Test
	public void testGetPosition() {
		assertTrue(model.getPosition().equals(new Point(xPos, yPos)));
	}

	@Test
	public void testGetXPosition() {
		assertTrue(xPos == model.getXPosition());
		model.setPosition(44, 33);
		assertTrue(44 == model.getXPosition());
	}

	@Test
	public void testGetYPosition() {
		assertTrue(yPos == model.getYPosition());
		model.setPosition(44, 33);
		assertTrue(33 == model.getYPosition());
	}

	@Test
	public void testSetPositionPoint() {
		Point p = new Point(33, 44);
		model.setPosition(33,44);
		assertTrue(model.getPosition().equals(p));
	}

	@Test
	public void testSetPositionIntInt() {
		Point p = model.getPosition();
		assertTrue(p.equals(new Point(50, 70)));
		p = new Point(33, 44);
		model.setPosition(p);
		assertTrue(model.getPosition().equals(p));
	}

	@Test
	public void testSetPointerPositionPoint() {
		Point p = model.getPosition();
		model.setPointerPosition(new Point(xPos, yPos));
		assertTrue(!p.equals(model.getPosition()));
		assertTrue(!model.getPosition().equals(new Point(xPos, yPos)));
		model.setPointerPosition(new Point(xPos +4, yPos +2));
		int x = xPos+4 - model.getXOffset();
		int y = yPos+2 - model.getYOffset();
		assertTrue(model.getXPosition() == x);
		assertTrue(model.getYPosition() == y);
	}

	@Test
	public void testSetPointerPositionIntInt() {
		Point p = model.getPosition();
		model.setPointerPosition(xPos, yPos);
		assertTrue(!p.equals(model.getPosition()));
		assertTrue(!model.getPosition().equals(new Point(xPos, yPos)));
	}

	@Test
	public void testPropertyChange() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetComponentWidth() {
		assertTrue(this.model.getComponentWidth() == 17); //bad test
	}

	@Test
	public void testSetComponentWidth() {
		this.model.setComponentWidth(13);
		assertTrue(this.model.getComponentWidth() == 13); //do we really want this?
		
		this.model.setComponentWidth(0);
		assertTrue(this.model.getComponentWidth() != 0);
		
		this.model.setComponentWidth(-5);
		assertTrue(this.model.getComponentWidth() != -5);
	}

	@Test
	public void testGetComponentHeight() {
		assertTrue(this.model.getComponentHeight() == 22); //bad test
	}

	@Test
	public void testSetComponentHeight() {
		this.model.setComponentHeight(13);
		assertTrue(this.model.getComponentHeight() == 13); //do we really want this?
		
		this.model.setComponentHeight(0);
		assertTrue(this.model.getComponentHeight() != 0);
		
		this.model.setComponentHeight(-5);
		assertTrue(this.model.getComponentHeight() != -5);
	}

	@Test
	public void testSetProperComponentSize() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetVisible() {
		fail("Not yet implemented");
	}

	@Test
	public void testIsVisible() {
		fail("Not yet implemented");
	}

	@Test
	public void testIntersects() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetRectangle() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetSize() {
		Dimension size = new Dimension(this.model.getComponentWidth(), this.model.getComponentHeight());
		assertTrue(this.model.getSize().equals(size));
		
		
	}

	@Test
	public void testGetLayer() {
		int layer = this.model.getYPointerPosition();
		assertTrue(layer == this.model.getLayer());
		
		this.model.setPointerPosition(33, 92);
		layer = this.model.getYPointerPosition();
		assertTrue(layer == this.model.getLayer());
	}

}
