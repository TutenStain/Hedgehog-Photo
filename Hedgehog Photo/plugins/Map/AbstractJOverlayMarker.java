import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 * Abstract graphical representation of a marker
 * which shall be used in a JLayeredPane.
 * @author Florian Minges
 */
@SuppressWarnings("serial")
public class AbstractJOverlayMarker extends AbstractJOverlayPanel {
	private JLabel iconContainer;
	
	protected void initialize() {
		initializeIconContainer();
		setProperIcon();
		super.initialize();
		Dimension d = this.getPreferredSize();
		this.iconContainer.setSize(d);
		this.iconContainer.setPreferredSize(d);
		this.iconContainer.setMinimumSize(d);
		this.iconContainer.setMaximumSize(d);
			
	}
	
	private void initializeIconContainer() {
		if (this.iconContainer == null) {
			this.iconContainer = new JLabel();
			/* Important to setBounds on all components, otherwise they
			   might not show up in the view. */
			this.iconContainer.setBounds(0, 0, model.getComponentWidth(), 
												model.getComponentHeight());
			
			add(this.iconContainer);
		}
	}

	/** Sets the imageIcon. Proper size should already 
	 * 	have been fixed by the model. */
	protected void setProperIcon() {
		ImageIcon icon = new ImageIcon(getModel().getIconPath());
		setIcon(icon);
	}
	
	private void setIcon(ImageIcon icon) {
		if (this.iconContainer == null)
			initializeIconContainer();
		this.iconContainer.setIcon(icon);
	}
	
	@Override
	protected void update(String updateType) {
		super.update(updateType);
		if (updateType.equals(Global.ICON_UPDATE)) {
			setProperIcon();
		}
	}
	
	@Override
	public AbstractMarkerModel getModel() {
		return (AbstractMarkerModel) model;
	}
	
	public String toString() {
		String string = super.toString();
		return string + this.iconContainer.toString();
	}
	
}
