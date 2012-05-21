package se.cth.hedgehogphoto.map.view;

import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.map.model.AbstractComponentModel;
import se.cth.hedgehogphoto.map.model.Global;

/**
 * Abstract graphical representation of a JPanel that
 * should be displayed in a JLayeredPane.
 * @author Florian Minges
 */
@SuppressWarnings("serial")
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
	public abstract AbstractComponentModel getModel();
	
	/** Sets the model and assigns the view as an observer to it. */
	protected void setModel(AbstractComponentModel model) {
		this.model = model;
		this.model.addObserver(this);
	}
	
	@Override
	public void update(Observable arg0, Object arg1) {
		initialize(); 
	}
	
	protected void update(String updateType) {
		switch (updateType) {
			case Global.POSITION_UPDATE: setProperBounds(); break;
			case Global.VISIBILITY_UPDATE: setProperVisibility(); break;
			case Global.COMPONENT_SIZE_UPDATE: forceProperSize(); break;
			default: break;
		}
	}
	
	protected void initialize() {
		this.setBorder(BorderFactory.createEmptyBorder());
		setProperBounds();
		forceProperSize(); 
		setProperVisibility();
		setOpaque(false);
	}
	
	protected void setProperBounds() {
		setBounds(model.getRectangle());
	}
	
	protected void setProperVisibility() {
		setVisible(model.isVisible());
	}
	
	protected void forceProperSize() {
		Dimension dimension = this.getPreferredSize();
		setSize(dimension);
	}
	
	public int getLayer() {
		return model.getLayer();
	}
}
