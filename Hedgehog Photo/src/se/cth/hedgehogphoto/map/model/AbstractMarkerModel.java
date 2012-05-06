package se.cth.hedgehogphoto.map.model;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ImageIcon;

import se.cth.hedgehogphoto.database.Location;
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
		setChanged();
		notifyObservers(Global.ICON_UPDATE);
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
	
	/** Returns the x-difference between the top-left corner and the
	 *  position this marker points at. */
	abstract int getXOffset(); 
	
	/** Returns the y-difference between the top-left corner and the
	 *  position this marker points at. */
	abstract int getYOffset(); 
	
	abstract void handleVisibility();
	abstract Point.Double getLonglat();
	
	@Override
	protected void handleZoom() { 
		Point.Double p = this.getLonglat();
		setPointerPosition(MapPanel.computePixelPositionOnMap(p.x, p.y));
	}
	
	public void print() {
		String visible = this.isVisible() ? "VISIBLE      <----- wow!." : "not visible.";
		String className = this.getClass().toString().substring(this.getClass().toString().indexOf("M"), this.getClass().toString().length());
		String position = this.getPosition().toString().substring(this.getPosition().toString().indexOf("["), this.getPosition().toString().length());
		System.out.println(className +": " + position + " is " + visible);
	}
}
