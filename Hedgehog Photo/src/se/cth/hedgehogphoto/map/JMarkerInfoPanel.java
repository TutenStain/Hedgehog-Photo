package se.cth.hedgehogphoto.map;

/**
 * Panel which displays info about the images of a certain marker.
 * @author Florian
 */
public class JMarkerInfoPanel extends AbstractJOverlayPanel {

	@Override
	int getXOffset() {
		return getComponentWidth() / 2;
	}

	@Override
	int getYOffset() {
		return getComponentHeight() / 2;
	}

	@Override
	public int getProperComponentWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getProperComponentHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

}
