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
		setImageIcon(new ImageIcon("Pictures/markers/marker.png")); //19x19
	}
	
	@Override
	int getXOffset() {
		return getIconWidth() / 2;
	}

	@Override
	int getYOffset() {
		return getIconHeight() / 2;
	}

	@Override
	MouseListener getMouseListener() {
		// TODO Auto-generated method stub
		return null;
	}

}
