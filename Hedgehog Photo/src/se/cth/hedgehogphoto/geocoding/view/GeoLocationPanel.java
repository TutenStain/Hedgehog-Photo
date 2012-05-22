package se.cth.hedgehogphoto.geocoding.view;

import java.awt.Color;
import java.awt.GridLayout;
import java.text.DecimalFormat;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import se.cth.hedgehogphoto.objects.LocationGPSObject;

/**
 * GUI-component used in the geocoding-system,
 * representing a location.
 * @author Florian Minges
 */
@SuppressWarnings("serial")
public class GeoLocationPanel extends JPanel {
	private JLabel nameLabel;
	private JLabel longitudeLabel, latitudeLabel;
	private LocationGPSObject location;
	private Color defaultColor;
	private boolean isSelected;
	private static Color color;
	private static Color selectedPanelColor = new Color(255,150,95);
	private static int colorScale;
	
	public static GeoLocationPanel selectedPanel;
	
	public GeoLocationPanel(LocationGPSObject location) {
		//set instance variables
		this.location = location;
		this.nameLabel = new JLabel(location.getLocation());
		
		//round doubles (gps-coords) /just the visible part/
		DecimalFormat df = new DecimalFormat("#.#####");
		String lon = df.format(location.getLongitude());
		String lat = df.format(location.getLatitude());

		this.longitudeLabel = new JLabel("Longitude: " + lon);
		this.latitudeLabel = new JLabel("Latitude: " + lat);
		
		//align left
		this.nameLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		this.longitudeLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		this.latitudeLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		this.longitudeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.latitudeLabel.setHorizontalAlignment(SwingConstants.LEFT);
		
		//add margins
		final int margin = 10;
		this.nameLabel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
		this.longitudeLabel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
		this.latitudeLabel.setBorder(BorderFactory.createEmptyBorder(margin, margin, margin, margin));
		
		//set layout
		this.setLayout(new GridLayout(2,1));
		JPanel helpPanel = new JPanel();
		helpPanel.setLayout(new GridLayout(1,2));
		
		//add border
		this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2, true));
		
		//add components
		helpPanel.add(this.longitudeLabel);
		helpPanel.add(this.latitudeLabel);
		this.add(this.nameLabel);
		this.add(helpPanel);
		
		this.setPreferredSize(this.getPreferredSize());
		
		//set background color
		this.setBackground(color);
		helpPanel.setBackground(Color.WHITE);
		this.defaultColor = color;
		incrementColorScale();
		this.isSelected = false;
	}
	
	public LocationGPSObject getLocationObjectOther() {
		return this.location;
	}
	
	public static void resetColorScale() {
		color = new Color(255,240,95); 
		colorScale = 1;
	}
	
	private void incrementColorScale() {
		colorScale++;
		int red = 255 - colorScale*4 >= 0 ? 255 - colorScale*4: 0;
		int green = 255 - colorScale*5 >= 0 ? 255 - colorScale*5: 0;
		int blue = 105 + colorScale*15 <= 255 ? 95 + colorScale*15: 255;
		color = new Color(red,green,blue);
	}
	
	public void defaultColor() {
		if (isSelected()) {
			this.setBackground(selectedPanelColor);
		} else {
			this.setBackground(this.defaultColor);
		}
	}
	
	public void brighter() {
		this.setBackground(this.getBackground().brighter());
	}
	
	public void darker() {
		this.setBackground(this.getBackground().darker());
	}
	
	public void toggleSelection() {
		setSelected(!isSelected());
	}
	
	public void setSelected(boolean selected) {
		this.isSelected = selected;
		if (GeoLocationPanel.selectedPanel != this && isSelected()) {
			if (GeoLocationPanel.selectedPanel != null)
				GeoLocationPanel.selectedPanel.toggleSelection();
			
			GeoLocationPanel.selectedPanel = this;
		} else if (GeoLocationPanel.selectedPanel == this && !isSelected()) 
			GeoLocationPanel.selectedPanel = null;

		defaultColor();
	}
	
	public boolean isSelected() {
		return this.isSelected;
	}
	
	public void mouseEntered() {
		this.brighter();
	}
	
	public void mouseExited() {
		this.defaultColor();
	}

}
