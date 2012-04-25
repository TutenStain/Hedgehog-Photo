package se.cth.hedgehogphoto.map;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import se.cth.hedgehogphoto.Constants;

/**
 * TODO: Add class-description here.
 * @author Florian
 */
public abstract class JOverlayLabel extends JLabel implements PropertyChangeListener {
	private ImageIcon imageIcon;
	private int iconWidth;
	private int iconHeight;

	void init() {
		setIcon(imageIcon);
		setOpaque(false);
		setBounds(labelPointsFromLeft(), labelPointsFromTop(), iconWidth, iconHeight);
		addMouseListener(getMouseListener());
	}
	
	/** Moves the label by a given length. MIGHT want to use 
	 *  another name for the method if errors occur. */
	@Override
	public void move(int dx, int dy) {
		Point p = getLocation();
		p.x = p.x + dx;
		p.y = p.y + dy;
		setBounds(p.x, p.y, iconWidth, iconHeight);
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
	public void setPixelPosition(int x, int y) {
		x = x - getXOffset();
		y = y - getYOffset();
		setBounds(x, y);
	}
	
	/** Returns the distance in pixels from the top left
	 *  corner of this label, to the point where the label
	 *  'points' towards, ie where the marker points towards. */
	abstract int labelPointsFromLeft();
	abstract int labelPointsFromTop();
	
	/** Sets the new position in case of a zoom. 
	 *  @param zoomMultiplier a value of 2 corresponds to a 'zoomIn'-event
	 *  and a value of 0.5 represents a 'zoomOut'-event. */
	private void handleZoom(double zoomMultiplier) {
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
	
	/** Returns each subclass' own implementation of their
	 *  MouseListener. */
	abstract MouseListener getMouseListener();

	public ImageIcon getImageIcon() {
		return imageIcon;
	}

	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
	}

	public int getIconHeight() {
		return iconHeight;
	}

	public void setIconHeight(int iconHeight) {
		this.iconHeight = iconHeight;
	}

	public int getIconWidth() {
		return iconWidth;
	}

	public void setIconWidth(int iconWidth) {
		this.iconWidth = iconWidth;
	}
	
	/** In case the iconWidth and iconHeight-instance variables
	 *  haven't been updated after the change of the icon, this
	 *  is the method to call. */
	public void setProperIconSize() {
		setIconWidth(getIconWidth());
		setIconHeight(getIconHeight());
	}
	
	/** Checks if this label intersects with a given JComponent. */
	public boolean intersects(JComponent component) {
		Rectangle r = component.getBounds();	
		return r.intersects(this.getBounds());
		/* TODO: Add a method intersects which takes a rectangle as parameter. 
		 * Why? We don't want to create 100 JMultipleMarkers if 100 JMarkers 
		 * which intersect exists. */
	}
	
	/** Sets the bounds of this component, using the given params
	 *  and the stored iconWidth and iconHeight-values. */
	public void setBounds(int x, int y) {
		setBounds(x, y, iconWidth, iconHeight);
	}
	
	/** Abstract MouseListener-class.
	 *  'Empty' Modifier - subclasses may instantiate it. */
	abstract class MouseListener extends MouseAdapter {

		@Override
		public void mouseReleased(MouseEvent arg0) {}
		public abstract void mouseClicked(MouseEvent arg0);
		public abstract void mousePressed(MouseEvent arg0);

		/* IF POSSIBLE: Might want to Override below methods as well. We'll see. */
		@Override
		public void mouseEntered(MouseEvent arg0) {
			arg0.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			arg0.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
		}
    }
}
