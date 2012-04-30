package se.cth.hedgehogphoto.map;

import java.awt.event.MouseListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import se.cth.hedgehogphoto.database.Picture;

/**
 * Represents a label containing multiple other JOverlayLabels.
 * @author Florian
 */
public class JMultipleMarker extends AbstractJOverlayMarker {
	private List<AbstractJOverlayMarker> overlayMarkers; /* Will currently ALWAYS hold 2 markers. */
	
	/* TODO: Write constructor. */
	public JMultipleMarker(AbstractJOverlayMarker markerOne, AbstractJOverlayMarker markerTwo) {
		this.overlayMarkers = new ArrayList<AbstractJOverlayMarker>();
		this.overlayMarkers.add(markerOne);
		this.overlayMarkers.add(markerTwo);
		setImageIcon(new ImageIcon("Pictures/markers/marker.png")); //19x19
		init();
		setProperBounds();
		handleZoom();
//		System.out.println("New JMultipleMarker ("+ getXPosition() + "," + getYPosition() +") created!");
//		System.out.println(markerOne.getClass().getSimpleName() + ": ("+ markerOne.getXPosition() + "," + markerOne.getYPosition() +")");
//		System.out.println(markerTwo.getClass().getSimpleName() + ": ("+ markerTwo.getXPosition() + "," + markerTwo.getYPosition() +")");
	}
	
	@Deprecated
	public JMultipleMarker(List<AbstractJOverlayMarker> markers) {
		this.overlayMarkers = markers;
		setImageIcon(new ImageIcon("Pictures/markers/marker.png")); //19x19
		init(); //TODO set the correct coordinates.
		setProperBounds();
	}
	
	private void setProperBounds() {
		this.setPixelPosition(computeXPosition(), computeYPosition());
	}
	
	/* TODO: Add handling for 0 markers. ArithmeticException */
	private int computeXPosition() {
		int xPosition = 0;
		int nbrOfMarkers = overlayMarkers.size();
		for (int index = 0; index < nbrOfMarkers; index++) {
			AbstractJOverlayMarker marker = overlayMarkers.get(index);
			xPosition += marker.getXPosition() * marker.getNumberOfPictures();
		}
		return xPosition / this.getNumberOfPictures();
	}
	
	/* TODO: Add handling for 0 markers. ArithmeticException */
	private int computeYPosition() {
		int yPosition = 0;
		int nbrOfMarkers = overlayMarkers.size();
		for (int index = 0; index < nbrOfMarkers; index++) {
			AbstractJOverlayMarker marker = overlayMarkers.get(index);
			yPosition += marker.getYPosition() * marker.getNumberOfPictures();
		}
		return yPosition / this.getNumberOfPictures();
	}
	
	public void handleZoom() {
		AbstractJOverlayMarker markerOne = overlayMarkers.get(0);
		AbstractJOverlayMarker markerTwo = overlayMarkers.get(1);
		if (markerOne.intersects(markerTwo)) {
			markerOne.setVisible(false);
			markerTwo.setVisible(false);
			this.setVisible(true);
		} else {
			markerOne.setVisible(true);
			markerTwo.setVisible(true);
			this.setVisible(false);
		}
	}
	
	/** Responds to map-actions like drag and zoom. */
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		super.propertyChange(event);
		overlayMarkers.get(0).propertyChange(event);
		overlayMarkers.get(1).propertyChange(event);
		if (event.getPropertyName().startsWith("zoom")) {
			handleZoom();
		}
	}
	
	@Override
	public void addMouseListener(MouseListener mouseAdapter) {
		super.addMouseListener(mouseAdapter);
		overlayMarkers.get(0).addMouseListener(mouseAdapter);
		overlayMarkers.get(1).addMouseListener(mouseAdapter);
	}
	
	@Override
	int getXOffset() {
		return getWidth() / 2;
	}

	@Override
	int getYOffset() {
		return getHeight() / 2;
	}

	@Override
	public List<Picture> getPictures(List<Picture> pictures) {
		int nbrOfMarkers = overlayMarkers.size();
		for (int index = 0; index < nbrOfMarkers; index++) {
			pictures = overlayMarkers.get(index).getPictures(pictures);
		}
		return pictures;
	}

	@Override
	int computeNumberOfPictures() {
		int nbrOfPictures = 0;
		int nbrOfMarkers = overlayMarkers.size();
		for (int index = 0; index < nbrOfMarkers; index++) {
			nbrOfPictures += overlayMarkers.get(index).computeNumberOfPictures();
		}
		return nbrOfPictures;
	}
	
	@Override
	public void setVisible(boolean visible) {
		if (overlayMarkers.get(0).isVisible() || 
				overlayMarkers.get(1).isVisible()) {
			super.setVisible(false);
		} else {
			super.setVisible(visible);
		}
	}

}
