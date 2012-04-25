package se.cth.hedgehogphoto.map;

import java.util.List;

import javax.swing.ImageIcon;

/**
 * Represents a label containing multiple other JOverlayLabels.
 * @author Florian
 */
public class JMultipleMarker extends JOverlayLabel {
	private List<JOverlayLabel> overlayLabels;
	
	/* TODO: Write constructor. */
	public JMultipleMarker() {
		setImageIcon(new ImageIcon("Pictures/markers/marker.png"));
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
