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
												implements PropertyChangeListener { //doesn't need the propertychangelistener, but the propertyChange-method
	private int componentWidth;
	private int componentHeight;
	private Point position;
	private boolean isVisible;
	
	public void initialize() {
		setPosition(0, 0);
	}
	
	/** Moves the position by the given length. */
	public void move(int dx, int dy) {
		int x = getXPosition() + dx;
		int y = getYPosition() + dy;
		setPosition(x, y);
	}
	
	/** Returns the position of this label, ie the position of 
	 *  the upper left corner. */
	public Point getPosition() {
		return new Point(getXPosition(), getYPosition());
	}
	
	/** Returns the position of this label, ie the position to 
	 *  where it points. */
	public Point getPointerPosition() {
		return new Point(getXPointerPosition(), getYPointerPosition());
	}
	
	/** Returns the x-position of the components 
	 *  top left corner. */
	public int getXPosition() {
		return this.position.x;
	}
	
	/** Returns the y-position of the components 
	 *  top left corner. */
	public int getYPosition() {
		return this.position.y;
	}
	
	/** Returns the x-position of where the components 
	 *  graphical representation points. */
	public int getXPointerPosition() {
		return getXPosition() + getXOffset();
	}
	
	/** Returns the y-position of where the components
	 *  graphical representation points. */
	public int getYPointerPosition() {
		return getYPosition() + getYOffset();
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
		notifyObservers(Global.POSITION_UPDATE);
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
	
	/** Sets the new position in case of a zoom. */
	protected abstract void handleZoom();
	
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
		if (property.equals(Global.DRAG_EVENT) && isVisible()) { //don't have to move invisible markers
			handleDrag(event.getOldValue(), event.getNewValue());
		} else if (property.startsWith(Global.ZOOM)){
			handleZoom();
		}
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
			notifyObservers(Global.COMPONENT_SIZE_UPDATE);
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
		notifyObservers(Global.VISIBILITY_UPDATE);
	}
	
	public boolean isVisible() {
		return this.isVisible;
	}
	
	public boolean intersects(AbstractComponentModel model) {
		Rectangle thisRectangle = this.getRectangle();
		Rectangle otherRectangle = model.getRectangle();
		return thisRectangle.intersects(otherRectangle) || otherRectangle.intersects(thisRectangle);
	}
	
	public Rectangle getRectangle() {
		return new Rectangle(getXPosition(), getYPosition(), 
								getComponentWidth(), getComponentHeight());
	}
	
	public Dimension getSize() {
		return new Dimension(getComponentWidth(), getComponentHeight());
	}
	
	public Integer getLayer() {
		return new Integer(position.y + getComponentHeight());
	}
}
