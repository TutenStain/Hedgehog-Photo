package se.cth.hedgehogphoto.map.model;

/**
 * Constants used within the map-package.
 * @author Florian Minges
 */
public interface Global {

	public final String POSITION_UPDATE = "positionUpdate";
	public final String COMPONENT_SIZE_UPDATE = "componentSizeUpdate";
	public final String VISIBILITY_UPDATE = "visibilityUpdate";
	public final String ICON_UPDATE = "iconUpdate";
	public final String FILES_UPDATE = "filesUpdated";
	public final String MARKERS_UPDATE = "organizeMarkers";
	
	public final String ZOOM_OUT_EVENT = "zoomOut";
	public final String ZOOM_IN_EVENT = "zoomIn";
	public final String ZOOM = "zoom";
	public final String DRAG_EVENT = "mapPosition";
	
	public final String MULTIPLE_MARKER_ICON_PATH = "Pictures/markers/marker.png";
	public final String MARKER_ICON_PATH = "Pictures/markers/ultimateSingleMarker.png";
}
