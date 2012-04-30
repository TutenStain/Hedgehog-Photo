package se.cth.hedgehogphoto.map;

import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ImageIcon;

import se.cth.hedgehogphoto.database.Picture;

/**
 * TODO: Add class-description here.
 * @author Florian
 */
public class JMarker extends AbstractJOverlayMarker {
	private Picture picture;
	
	public JMarker(Picture picture) {
		this.picture = picture;
		setImageIcon(new ImageIcon("Pictures/markers/marker2.png")); //26x26
		init();
	}
	
	public double getLatitude() {
		return picture.getLocation().getLatitude();
	}
	
	public Picture getPicture() {
		return this.picture;
	}
	
	@Override
	int getXOffset() {
		return getWidth() / 2;
	}

	@Override
	int getYOffset() {
		return getHeight();
	}

	@Override
	public List<Picture> getPictures(List<Picture> pictures) {
		pictures.add(this.getPicture());
		return pictures;
	}

	@Override
	int computeNumberOfPictures() {
		return 1;
	}

}
