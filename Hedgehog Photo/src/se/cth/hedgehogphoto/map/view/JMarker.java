package se.cth.hedgehogphoto.map.view;

import se.cth.hedgehogphoto.map.model.MarkerModel;

/**
 * Graphical representation of a single marker.
 * @author Florian
 */
public class JMarker extends AbstractJOverlayMarker {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1489041564249003411L;

	public JMarker(MarkerModel model) {
		setModel(model);
		initialize();
	}
	
	@Override
	public MarkerModel getModel() {
		return (MarkerModel) model;
	}
}
