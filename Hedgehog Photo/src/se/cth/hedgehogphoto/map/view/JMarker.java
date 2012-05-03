package se.cth.hedgehogphoto.map.view;

import se.cth.hedgehogphoto.map.model.MarkerModel;

/**
 * Graphical representation of a single marker.
 * @author Florian
 */
public class JMarker extends AbstractJOverlayMarker {
	
	public JMarker(MarkerModel model) {
		setModel(model);
		initialize();
	}
	
	@Override
	protected MarkerModel getModel() {
		return (MarkerModel) model;
	}
}
