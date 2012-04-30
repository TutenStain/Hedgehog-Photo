package se.cth.hedgehogphoto.map;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;

import se.cth.hedgehogphoto.Constants;

/**
 * TODO: Add class-description here.
 * @author Florian
 */
public abstract class AbstractJOverlayPanel extends JComponent implements PropertyChangeListener {
	/* No modifier - visible to subclasses. */
	int componentWidth;
	int componentHeight;
	
	void init() {
		setOpaque(false);
		setBounds(0, 0);
		setVisible(true);
	}
	
	@Deprecated
	void init(Point p) {
		setOpaque(false);
		int xPos = p.x - getXOffset();
		int yPos = p.y - getYOffset();
		setBounds(xPos, yPos, componentWidth, componentHeight);
		setVisible(true);
	}
	
	/** Moves the label by a given length. MIGHT want to use 
	 *  another name for the method if errors occur. */
	@Override
	public void move(int dx, int dy) {
		Point p = getLocation();
		p.x = p.x + dx;
		p.y = p.y + dy;
		setBounds(p.x, p.y, componentWidth, componentHeight);
		revalidate();
	}
	
	/** Returns the position of this label, ie the position to 
	 *  where it points. */
	public Point getPoint() {
		return new Point(getXPosition(), getYPosition());
	}
	
	/** Returns the x-position of where the label points. */
	public int getXPosition() {
		Point p = getLocation();
		return p.x + getXOffset();
	}
	
	/** Returns the y-position of where the label points. */
	public int getYPosition() {
		Point p = getLocation();
		return p.y + getYOffset();
	}
	
	/** Returns the distance from the labels top left corner to
	 * the X and Y position that the label points at. */
	abstract int getXOffset();
	abstract int getYOffset();
	
	/** Places the marker so that it points to the given coordinates. */
	public void setPixelPosition(Point p) {
		setPixelPosition(p.x, p.y);
	}
	
	/** Places the marker so that it points to the given coordinates. */
	public void setPixelPosition(int x, int y) {
		x = x - getXOffset();
		y = y - getYOffset();
		setBounds(x, y);
		setVisible(true);
	}
	
	/** Sets the new position in case of a zoom. 
	 *  @param zoomMultiplier a value of 2 corresponds to a 'zoomIn'-event
	 *  and a value of 0.5 represents a 'zoomOut'-event. */
	private void handleZoom(double zoomMultiplier) {
		int factor = zoomMultiplier < 1 ? -2 : 4;
		move(se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_WIDTH / factor, se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_HEIGHT / factor);
		int x = (int) ((1 - zoomMultiplier) * getCenterXPixel() + zoomMultiplier * getXPosition());
		int y = (int) ((1 - zoomMultiplier) * getCenterYPixel() + zoomMultiplier * getYPosition());
		setPixelPosition(x, y);
	}
	
	/** If the map moves, the overlayLabels move with it! 
	 *  The parameters suppose the use of a firePropertyChange. */
	private void handleDrag(Object oldValue, Object newValue) {
		Point oldPoint = (Point) oldValue;
		Point newPoint = (Point) newValue;
		int dx = oldPoint.x - newPoint.x;
		int dy = oldPoint.y - newPoint.y;
		move(dx, dy);
	}
	
	/** Responds to map-actions like drag and zoom. */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String property = event.getPropertyName();
		if (property.equals("mapPosition")) {
			handleDrag(event.getOldValue(), event.getNewValue());
		} else if (property.startsWith("zoom")){
			double zoomMultiplier = (double) ((Dimension) event.getNewValue()).width / (double) ((Dimension) event.getOldValue()).width;
			handleZoom(zoomMultiplier);
		}
	}
	
	/** Returns the centers x-coordinate in pixels.
	 *  Fetches it from a global accessible class. */
	private int getCenterXPixel() {
		return Constants.PREFERRED_MODULE_WIDTH / 2;
	}
	
	/** Returns the centers y-coordinate in pixels.
	 *  Fetches it from a global accessible class. */
	private int getCenterYPixel() {
		return Constants.PREFERRED_MODULE_HEIGHT / 2;
	}
	
	public int getComponentWidth() {
		return this.componentWidth;
	}

	public void setComponentWidth(int componentWidth) {
		this.componentWidth = componentWidth;
	}

	public int getComponentHeight() {
		return this.componentHeight;
	}

	public void setComponentHeight(int componentHeight) {
		this.componentHeight = componentHeight;
	}
	
	public void setProperComponentSize() {
		setComponentWidth(getProperComponentWidth());
		setComponentHeight(getProperComponentHeight());
		setSize(this.componentWidth, this.componentHeight);
	}
	
	public abstract int getProperComponentWidth();
	public abstract int getProperComponentHeight();
	
	/** Checks if this label intersects with a given JComponent. */
	public boolean intersects(JComponent component) {
		Rectangle r = component.getBounds();	
		return r.intersects(this.getBounds());
		/* TODO: Add a method intersects which takes a rectangle as parameter. 
		 * Why? We don't want to create 100 JMultipleMarkers if 100 JMarkers 
		 * which intersect exists. */
	}
	
	/** Sets the bounds of this component, using the given params
	 *  and the stored componentWidth and componentHeight-values. */
	public void setBounds(int x, int y) {
		setBounds(x, y, componentWidth, componentHeight);
	}
}
