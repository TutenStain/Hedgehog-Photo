package se.cth.hedgehogphoto.map;

import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import se.cth.hedgehogphoto.database.Picture;

public abstract class AbstractJOverlayMarker extends AbstractJOverlayPanel {
	private ImageIcon imageIcon;
	private JLabel iconContainer;
	int numberOfPictures;
	
	@Override
	public void init() {
		super.init();
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
	
	private void initIconContainer() {
		this.iconContainer = new JLabel();
		this.removeAll();
		this.add(this.iconContainer);
	}
	
	public ImageIcon getImageIcon() {
		return this.imageIcon;
	}

	/** Sets the imageIcon and the proper icon size. */
	public void setImageIcon(ImageIcon imageIcon) {
		this.imageIcon = imageIcon;
		setIcon(imageIcon);
		setProperComponentSize();
		this.iconContainer.setBounds(0, 0, getComponentWidth(), getComponentHeight());
	}
	
	private void setIcon(ImageIcon icon) {
		if (this.iconContainer == null) {
			this.initIconContainer();
		}
		this.iconContainer.setIcon(icon);
	}
	
	public int getProperComponentWidth() {
		return getImageIcon().getIconWidth();
	}
	
	public int getProperComponentHeight() {
		return getImageIcon().getIconHeight();
	}

	@Override
	int getXOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int getYOffset() {
		// TODO Auto-generated method stub
		return 0;
	}

}
