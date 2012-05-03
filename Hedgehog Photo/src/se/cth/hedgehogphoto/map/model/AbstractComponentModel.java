package se.cth.hedgehogphoto.map.model;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;

import se.cth.hedgehogphoto.Constants;

/**
 * Abstract logical representation of a JComponent that
 * should be displayed in a JLayeredPane.
 * @author Florian
 */
public abstract class AbstractComponentModel extends Observable
												implements PropertyChangeListener {
	/* No modifier - visible to subclasses. */
	int componentWidth;
	int componentHeight;
	private Point position;
	private boolean isVisible;
	
	public void initialize() {
		setPosition(0, 0);
	}
	
	/** Moves the position by the given length. */
	public void move(int dx, int dy) {
		int x = this.position.x + dx;
		int y = this.position.y + dy;
		setPosition(x, y);
	}
	
	/** Returns the position of this label, ie the position to 
	 *  where it points. */
	public Point getPosition() {
		return new Point(getXPosition(), getYPosition());
	}
	
	/** Returns the x-position of where the components 
	 *  graphical representation points. */
	public int getXPosition() {
		return this.position.x + getXOffset();
	}
	
	/** Returns the y-position of where the components
	 *  graphical representation points. */
	public int getYPosition() {
		return this.position.y + getYOffset();
	}
	
	/** Returns the distance from the stored position to
	 * the X and Y position that it actually points at. */
	abstract int getXOffset();
	abstract int getYOffset();
	
	/** Sets the position. */
	public void setPosition(Point p) {
		setPosition(p.x, p.y);
	}
	
	/** Sets the position. */
	public void setPosition(int x, int y) {
		this.position = new Point(x, y);
		setChanged();
		notifyObservers();
	}
	
	/** Sets the stored position so that it's graphical
	 *  representation points towards the given position. */
	public void setPointerPosition(Point p) {
		setPointerPosition(p.x, p.y);
	}
	
	/** Sets the stored position so that it's graphical
	 *  representation points towards the given position. */
	public void setPointerPosition(int x, int y) {
		x -= getXOffset();
		y -= getYOffset();
		setPosition(x, y);
	}
	
	/** Sets the new position in case of a zoom. 
	 *  @param zoomMultiplier a value of 2 corresponds to a 'zoomIn'-event
	 *  and a value of 0.5 represents a 'zoomOut'-event. */
	private void handleZoom(double zoomMultiplier) {
		int factor = zoomMultiplier < 1 ? -2 : 4;
		move(se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_WIDTH / factor, se.cth.hedgehogphoto.Constants.PREFERRED_MODULE_HEIGHT / factor);
		int x = (int) ((1 - zoomMultiplier) * getCenterXPixel() + zoomMultiplier * getXPosition());
		int y = (int) ((1 - zoomMultiplier) * getCenterYPixel() + zoomMultiplier * getYPosition());
		setPointerPosition(x, y);
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
	
	/** Returns the mapcenters x-coordinate in pixels.
	 *  Fetches it from a global accessible class. */
	private int getCenterXPixel() {
		return Constants.PREFERRED_MODULE_WIDTH / 2;
	}
	
	/** Returns the mapcenters y-coordinate in pixels.
	 *  Fetches it from a global accessible class. */
	private int getCenterYPixel() {
		return Constants.PREFERRED_MODULE_HEIGHT / 2;
	}
	
	public int getComponentWidth() {
		return this.componentWidth;
	}

	public void setComponentWidth(int componentWidth) {
		this.componentWidth = componentWidth;
		waitForSecondChangeBeforeNotification();
	}

	public int getComponentHeight() {
		return this.componentHeight;
	}

	public void setComponentHeight(int componentHeight) {
		this.componentHeight = componentHeight;
		waitForSecondChangeBeforeNotification();
	}
	
	/** As one don't want to notify Observer two times when the component
	 *  size is updated (width and height), this method only notifies observers 
	 *  if property 'hasChanged' already was set to true. */
	private void waitForSecondChangeBeforeNotification() {
		if (hasChanged())
			notifyObservers();
		else
			setChanged();
	}
	
	public void setProperComponentSize() {
		setComponentWidth(getProperComponentWidth());
		setComponentHeight(getProperComponentHeight());
	}
	
	public abstract int getProperComponentWidth();
	public abstract int getProperComponentHeight();
	
	public void setVisible(boolean visible) {
		this.isVisible = visible;
		setChanged();
		notifyObservers();
	}
	
	public boolean isVisible() {
		return this.isVisible;
	}
	
	public boolean intersects(AbstractComponentModel model) {
		Rectangle thisRectangle = this.getRectangle();
		Rectangle otherRectangle = model.getRectangle();
		return thisRectangle.intersects(otherRectangle);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(this.position.x, this.position.y, 
								getComponentWidth(), getComponentHeight());
	}
	
	public Dimension getSize() {
		return new Dimension(getComponentWidth(), getComponentHeight());
	}
}
