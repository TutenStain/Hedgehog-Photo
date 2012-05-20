/**
 * Graphical representation of a single marker.
 * @author Florian Minges
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
