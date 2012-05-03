package se.cth.hedgehogphoto.map.view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;

import se.cth.hedgehogphoto.map.model.AbstractComponentModel;

/**
 * Abstract graphical representation of a JPanel that
 * should be displayed in a JLayeredPane.
 * @author Florian
 */
public abstract class AbstractJOverlayPanel extends JPanel 
											implements Observer {
	protected AbstractComponentModel model;
	
	/** Subclasses may use typecasting to return an instance
	 *  of the AbstractComponentModel-class. Through override 
	 *  they are capable of having the returntype of a class that
	 *  extends the AbstractComponentModel-class. That way they
	 *  can access each submodels specific methods, without having
	 *  to use the parameters in the update-method. This is an easy
	 *  and convenient way to access submodels specific methods, but
	 *  recquires ie that a JMarker has a MarkerModel, and not some 
	 *  kind of superclass. */
	protected abstract AbstractComponentModel getModel();
	
	protected void setModel(AbstractComponentModel model) {
		this.model = model;
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		forceProperSize(); //dunno if this is necessary
		setProperBounds();
		setProperVisibility();
	}
	
	protected void initialize() {
		setOpaque(false);
		setVisible(true);
	}
	
	protected void setProperBounds() {
		setBounds(model.getRectangle());
	}
	
	protected void setProperVisibility() {
		setVisible(model.isVisible());
	}
	
	protected void forceProperSize() {
		Dimension dimension = model.getSize();
		setPreferredSize(dimension);
		setMinimumSize(dimension);
		setMaximumSize(dimension);
		setSize(dimension);
	}
}
