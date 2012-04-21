package se.cth.hedgehogphoto.map;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import se.cth.hedgehogphoto.Constants;
import se.cth.hedgehogphoto.LocationObject;

public class LocationMarker extends JLabel implements PropertyChangeListener {
	private ImageIcon icon = new ImageIcon("marker2.png");
	private int WIDTH = icon.getIconWidth();
	private int HEIGHT = icon.getIconHeight();
	private LocationObject locations;
	/* TODO: Add implementation of the LocationObject. */
	
	private final MouseListener mouseListener = new MouseListener() {

		@Override
		public void mouseClicked(MouseEvent arg0) {}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			arg0.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			arg0.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			System.out.println("x: " + getXCoordinate() + "\t\ty: " + getYCoordinate());
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {}
    	
    };
	
	public LocationMarker(Point p) {
		setIcon(icon);
		setOpaque(false);
		int fromLeft = p.x - WIDTH / 2;
		int fromTop = p.y - HEIGHT;
		
		setBounds(fromLeft, fromTop, WIDTH, HEIGHT);
		addMouseListener(mouseListener);
	}
	
	/** Moves the marker by a given length. */
	public void moveAround(int dx, int dy) {
		Point p = getLocation();
		p.x = p.x + dx;
		p.y = p.y + dy;
		setBounds(p.x, p.y, WIDTH, HEIGHT);
		revalidate();
	}
	
	public Point getPoint() {
		return new Point(getXCoordinate(), getYCoordinate());
	}
	
	/** Returns the x-coordinate of where the marker points. */
	public int getXCoordinate() {
		Point p = getLocation();
		return p.x + WIDTH / 2;
	}
	
	/** Returns the y-coordinate of where the marker points. */
	public int getYCoordinate() {
		Point p = getLocation();
		return p.y + HEIGHT;
	}
	
	/** Places the marker so that it points to the given coordinates. */
	void setPixelCoordinates(int x, int y) {
		x = x - WIDTH / 2;
		y = y - HEIGHT;
		setBounds(x, y, WIDTH, HEIGHT);
		/* TODO: Make more general - ie doesn't always have to point to the mid-bottom, might wanna be mid-mid sometimes of left- bot. */
	}
	
	/** Handles input "zoomIn" and "zoomOut" only. 
	 *  Won't do anything if other input is given. */
	@Deprecated
	private void handleZoom(String zoomType) {
		if (zoomType.equals("zoomIn")) {
			handleZoomIn();
		} else if (zoomType.equals("zoomOut")) {
			handleZoomOut();
		}
	}
	
	/** New formula for calculating how to handle the zoom. */
	private void handleZoom(double zoomMultiplier) {
		int x = (int) ((1 - zoomMultiplier) * getCenterXPixel() + zoomMultiplier * getXCoordinate());
		int y = (int) ((1 - zoomMultiplier) * getCenterYPixel() + zoomMultiplier * getYCoordinate());
		setPixelCoordinates(x, y);
	}
	
	@Deprecated
	private void handleZoomIn() {
		int x = 2 * getXCoordinate() - getCenterXPixel();
		int y = 2 * getYCoordinate() - getCenterYPixel();
		setPixelCoordinates(x, y);
	}
	
	@Deprecated
	private void handleZoomOut() {
		int x = (3 * getXCoordinate() - getCenterXPixel()) / 2;
		int y = (3 * getYCoordinate() - getCenterYPixel()) / 2;
		setPixelCoordinates(x, y);
	}
	
	/** If the map moves, the locationmarkers move with it! */
	private void handleDrag(Object oldValue, Object newValue) {
		Point oldPoint = (Point) oldValue;
		Point newPoint = (Point) newValue;
		int dx = oldPoint.x - newPoint.x;
		int dy = oldPoint.y - newPoint.y;
		moveAround(dx, dy);
	}
	
	/** Returns the centers x-coordinate in pixels. */
	private int getCenterXPixel() {
		return Constants.PREFERRED_MODULE_WIDTH / 2;
	}
	
	/** Returns the centers y-coordinate in pixels. */
	private int getCenterYPixel() {
		return Constants.PREFERRED_MODULE_HEIGHT / 2;
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getPropertyName();
		if (property.equals("mapPosition")) {
			handleDrag(event.getOldValue(), event.getNewValue());
		} else if (property.startsWith("zoom")){
			double zoomMultiplier = (double) ((Dimension) event.getNewValue()).width / (double) ((Dimension) event.getOldValue()).width;
			handleZoom(zoomMultiplier);
		}
		
		/* Keep this code for now. Though it probably will be removed some time soon.
		 * Might be useful for a different implemenation of the propertyChange. */
//		if (property.equals("zoom")) {
//			/* handle zoom */
//			Dimension oldValue = (Dimension) event.getOldValue();
//			Dimension newValue = (Dimension) event.getNewValue();
//			boolean zoomOut = (newValue.width < oldValue.width && newValue.height < oldValue.height);
//			if (zoomOut) {
//				handleZoomOu
//			}
//			double widthRatio = (double) newValue.width / (double) oldValue.width;
//			double heightRatio = (double) newValue.height / (double) oldValue.height;
//			int xDistanceToMid = Constants.PREFERRED_MODULE_WIDTH / 2 - getXCoordinate();
//			int yDistanceToMid = Constants.PREFERRED_MODULE_HEIGHT / 2 - getYCoordinate();
//			System.out.println(xDistanceToMid + ", " + yDistanceToMid);
//			if (zoomOut) {
//				widthRatio = 1.0 / widthRatio;
//				heightRatio = 1.0 / heightRatio;
//			} 
//			
//			int dx = (int) ((1 - widthRatio) * xDistanceToMid);
//			int dy = (int) ((1 - heightRatio) * yDistanceToMid);
//			System.out.println("\n-----------------------\n");
//			moveAround(dx, dy);
//		}
		
	}
	
	@Override
	public void setIcon(Icon icon) {
		super.setIcon(icon);
		setProperIconSize();
	}
	
	public void setProperIconSize() {
		WIDTH = this.getIcon().getIconWidth();
		HEIGHT = this.getIcon().getIconHeight();
	}
	
	public boolean intersects(JLabel label) {
		Rectangle r = label.getBounds();
		boolean intersects = false;
		/* TODO: Add calculation of when two labels intersect! */
		
		return intersects;
	}
	
}
