package se.cth.hedgehogphoto.map.view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.map.model.AbstractMarkerModel;
import se.cth.hedgehogphoto.map.model.Global;
import se.cth.hedgehogphoto.map.model.MapModel;
import se.cth.hedgehogphoto.map.model.MarkerModel;
import se.cth.hedgehogphoto.map.model.MultipleMarkerModel;

public class MapView extends JPanel implements Observer {
	private JLayeredPane mainPane;
	private List<AbstractJOverlayMarker> locationMarkers;
	private MapModel model;

	public MapView(MapModel model) {
		setModel(model);
		setLayout(new BorderLayout());
		initialize();
	}
	
	public void setModel(MapModel model) {
		this.model = model;
		this.model.addObserver(this);
	}
	
	protected void initialize() {
		setPreferredSize(model.getSize());
		this.setBorder(BorderFactory.createEmptyBorder());
		addRootJLayeredPane();
		addMap();
		addLocationMarkers();
	}
	
	private void addRootJLayeredPane() {
		this.mainPane = new JLayeredPane();
		this.mainPane.setPreferredSize(model.getSize());
		add(this.mainPane, BorderLayout.CENTER);
	}
	
	/** Adds the map to the pane. */
	private void addMap() {
		if (this.mainPane == null) {
			addRootJLayeredPane(); //unneccessary check? goto: line 38
		}
		this.mainPane.add(model.getMapPanel(), JLayeredPane.FRAME_CONTENT_LAYER, new Integer(2));
	}
	
	private void createMarkerGUIs(List<AbstractMarkerModel> markerModels) {
		int nbrOfModels = markerModels.size();
		for (int index = 0; index < nbrOfModels; index++) {
			AbstractMarkerModel abstractModel = markerModels.get(index);
			if (abstractModel.countObservers() > 0) {
//				try { //Reset the layer
//					AbstractJOverlayMarker marker = getMarkerGUI(abstractModel);
//					this.mainPane.setLayer(marker, JLayeredPane.DRAG_LAYER, 0);
//					this.mainPane.moveToFront(marker);
//				} catch (NoMatchingGUIException e) { } 
//				
				continue; //goto next step in loop, marker already has graphical representation
			} else if (abstractModel instanceof MultipleMarkerModel) {
				MultipleMarkerModel model = (MultipleMarkerModel) abstractModel;
				JMultipleMarker marker = new JMultipleMarker(model);
				this.locationMarkers.add(marker);
				createMarkerGUIs(model.getMarkerModels()); //add gui to the contained markers
			} else if (abstractModel instanceof MarkerModel) {
				MarkerModel model = (MarkerModel) abstractModel;
				JMarker marker = new JMarker(model);
				this.locationMarkers.add(marker);
			}
		}
	}
	
	private void addLocationMarkers() {
		if (this.locationMarkers == null)
			this.locationMarkers = new LinkedList<AbstractJOverlayMarker>();
		createMarkerGUIs(model.getMarkerModels());
		for (AbstractJOverlayMarker marker : this.locationMarkers) {
//			if (marker.isVisible())
				this.mainPane.add(marker, JLayeredPane.DRAG_LAYER, 0);
//			else if (marker instanceof JMultipleMarker) {
//				JMultipleMarker mMarker = (JMultipleMarker) marker;
//				AbstractMarkerModel modelOne = mMarker.getModel().getMarkerModels().get(0);
//				AbstractMarkerModel modelTwo = mMarker.getModel().getMarkerModels().get(1);
//				try {
//					if (modelOne.isVisible()) 
//						this.mainPane.add(getMarkerGUI(modelOne), JLayeredPane.DRAG_LAYER, new Integer(-1));
//					if (modelTwo.isVisible())
//						this.mainPane.add(getMarkerGUI(modelTwo),  JLayeredPane.DRAG_LAYER, new Integer(-1));
//				} catch (NoMatchingGUIException e) {}
//			}
		}
//		this.mainPane.moveToBack(model.getMapPanel());
		this.validate();
	}
	
	private AbstractJOverlayMarker getMarkerGUI(AbstractMarkerModel model) 
													throws NoMatchingGUIException {
		int nbrOfModels = this.locationMarkers.size();
		for (int index = 0; index < nbrOfModels; index++) {
			AbstractJOverlayMarker marker = this.locationMarkers.get(index);
			if (marker.getModel() == model)
				return marker;
		}
		throw new NoMatchingGUIException();
	}
	
	private class NoMatchingGUIException extends Exception {
		public NoMatchingGUIException() {
			super("No GUI with that model found.");
		}
	}
	
	public void addListener(MouseAdapter mouseAdapter) {
		int nbrOfMarkers = this.locationMarkers.size();
		for (int index = 0; index < nbrOfMarkers; index++) {
			AbstractJOverlayMarker marker = this.locationMarkers.get(index);
			if (marker.getMouseListeners().length == 0) {
				marker.addMouseListener(mouseAdapter);
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		// What happens when the files/new markers are updated
		if (arg1.toString().equals(Global.MARKERS_UPDATE)) {
			int oldValue = this.locationMarkers.size();
			addLocationMarkers(); //TODO move down after second if case
			int newValue = this.locationMarkers.size();
			firePropertyChange(Global.MARKERS_UPDATE, oldValue, newValue);
//			this.mainPane.removeAll();
//			addMap();
//			addLocationMarkers();
		} else if (arg1.toString().equals(Global.FILES_UPDATE)) {
			this.mainPane.removeAll();
			this.locationMarkers = new LinkedList<AbstractJOverlayMarker>();
			addMap();
			addLocationMarkers();
		}
		
		
	}
	
	

}
