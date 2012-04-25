package se.cth.hedgehogphoto.map;

import java.awt.event.MouseEvent;

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
		init();
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
		return new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				/* TODO: Add event-handling. */
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				/* TODO: Add event-handling. */
			}
		};
	}

}
