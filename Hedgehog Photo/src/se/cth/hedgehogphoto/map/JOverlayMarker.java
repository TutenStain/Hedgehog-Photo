package se.cth.hedgehogphoto.map;

import java.awt.Point;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;

import se.cth.hedgehogphoto.database.Picture;

/**
 * TODO: Add class-description here.
 * @author Florian
 */
public class JOverlayMarker extends AbstractJOverlayLabel {
	private Picture picture;
	
	public JOverlayMarker(Picture picture) {
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
		return getIconWidth() / 2;
	}

	@Override
	int getYOffset() {
		return getIconHeight();
	}

	@Override
	MouseListener getMouseListener() {
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/* TODO: Add event-handling. */
				System.out.println("x: " + getXPosition() + "\t\ty: " + getYPosition());
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				/* TODO: Add event-handling. */
			}
		};
	}

}
