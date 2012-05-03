package se.cth.hedgehogphoto.map.model;

import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.ImageIcon;

import se.cth.hedgehogphoto.database.Picture;

/**
 * Abstract logical representation of a marker
 * which shall be used in a JLayeredPane.
 * @author Florian
 */
public abstract class AbstractMarkerModel extends AbstractComponentModel {
	private String iconPath;
	int numberOfPictures;
	
	@Override
	public void initialize() {
		super.initialize();
		setNumberOfPictures(computeNumberOfPictures());
	}
	
	public abstract List<Picture> getPictures(List<Picture> pictures);
	abstract int computeNumberOfPictures();

	public int getNumberOfPictures() {
		return this.numberOfPictures;
	}
	
	public void setNumberOfPictures(int numberOfPictures) {
		this.numberOfPictures = numberOfPictures;
	}
	
	public String getIconPath() {
		return this.iconPath;
	}
	
	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
		setProperComponentSize(); 
		/* TODO: Setting the proper component size is essential 
		 * for calculations, but with only the string it is hard to check.
		 * One might need to hard-code the size of certain icons. */
	}
	
	public int getProperComponentWidth() {
		ImageIcon icon = new ImageIcon(getIconPath()); //temporary solution
		return icon.getIconWidth();
	}
	
	public int getProperComponentHeight() {
		ImageIcon icon = new ImageIcon(getIconPath()); //temporary solution
		return icon.getIconHeight();
	}
	
	/** Passing it on further down in the hierarchy. */
	abstract int getXOffset(); //not necessary to define again?
	abstract int getYOffset(); //not necessary to define again?
	
	
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub

	}

}
