package se.cth.hedgehogphoto.map.geocoding.view;

/* Source code derivative work from:
 * http://stackoverflow.com/questions/7949363/java-layout-for-displaying-panels-dynamically-with-scroll-bar 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.EmptyBorder;

import se.cth.hedgehogphoto.objects.LocationObject;

/** This lays out components in a column that is constrained to the
top of an area, like the entries in a list or table.  It uses a GridLayout
for the main components, thus ensuring they are each of the same size.
For variable height components, a BoxLayout would be better. */
public class ConstrainedGrid extends JPanel {
	private JPanel componentPanel;
	private MouseAdapter mouseListener;

    public ConstrainedGrid() {
        this.setLayout(new BorderLayout(5,5));
        this.setBorder(new EmptyBorder(3,3,3,3));
        this.setBackground(Color.WHITE);

        this.componentPanel = new JPanel(new GridLayout(0,1,3,3));
        
//        JPanel scrollPanel = new JPanel(new BorderLayout(2,2));
//        scrollPanel.setBackground(Color.green);
//        scrollPanel.add(new JLabel("Center"), BorderLayout.CENTER);
        JScrollPane scrollPane = new JScrollPane(this.componentPanel);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.add(scrollPane, BorderLayout.CENTER);

//        this.componentPanel = new JPanel(new GridLayout(0,1,3,3));
//        scrollPanel.add(this.componentPanel, BorderLayout.NORTH);
        Dimension d = this.getPreferredSize();
        d = new Dimension(d.width, d.height+100);
        this.setPreferredSize(d);
    }
    
    public void addComponent(JComponent component) {
    	this.componentPanel.add(component);
    	this.validate();
    }
    
    public void addLocations(List<LocationObject> locations) {
    	if (locations == null)
    		return;
    	this.componentPanel.removeAll();
    	GeoLocationPanel.resetColorScale();
    	for (LocationObject location : locations) {
    		GeoLocationPanel panel = new GeoLocationPanel(location);
    		if (this.mouseListener != null) 
    			panel.addMouseListener(this.mouseListener);
    		addComponent(panel);
    	}
    }
    
    /**
     * Doesn't work
     * @return
     */
    public int getPreferredWidth() {
    	return this.componentPanel.getPreferredSize().width;
    }
    
    public void setMouseAdapter(MouseAdapter adapter) {
    	this.mouseListener = adapter;
    }
}
