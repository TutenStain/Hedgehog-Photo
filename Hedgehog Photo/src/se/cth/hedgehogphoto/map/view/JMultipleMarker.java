package se.cth.hedgehogphoto.map.view;

import se.cth.hedgehogphoto.map.model.MultipleMarkerModel;

/**
 * Graphical representation of a "multiple marker".
 * @author Florian Minges
 */
@SuppressWarnings("serial")
public class JMultipleMarker extends AbstractJOverlayMarker {

	public JMultipleMarker(MultipleMarkerModel model) {
		setModel(model);
		initialize();
	}
	
	@Override
	public MultipleMarkerModel getModel() {
		return (MultipleMarkerModel) model;
	}
}
