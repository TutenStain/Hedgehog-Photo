package se.cth.hedgehogphoto.map;

import javax.swing.ImageIcon;

import se.cth.hedgehogphoto.ImageObject;

/**
 * TODO: Add class-description here.
 * @author Florian
 */
public class JMarker extends JOverlayLabel {
	private ImageObject image;
	
	/* TODO: Write constructor. */
	public JMarker() {
		setImageIcon(new ImageIcon("Pictures/markers/marker2.png"));
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

	@Override
	int labelPointsFromLeft() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	int labelPointsFromTop() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	MouseListener getMouseListener() {
		// TODO Auto-generated method stub
		return null;
	}

}
