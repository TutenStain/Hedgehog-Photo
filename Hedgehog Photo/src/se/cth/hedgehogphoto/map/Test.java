package se.cth.hedgehogphoto.map;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.Constants;
import se.cth.hedgehogphoto.database.Location;

@Deprecated
public class Test {
    private JFrame frame = new JFrame();
    private JPanel map;
    
    public Test(List<Location> locations)
    {
        frame.getContentPane().setLayout(new BorderLayout());
        JLabel loading = new JLabel("loading map...");
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        mainPane.add(loading, BorderLayout.CENTER);
        frame.setContentPane(mainPane);
        frame.pack();
        frame.setVisible(true);
        MapView map = new MapView(locations);
        MapController controller = new MapController(map);
        mainPane.remove(loading);
        mainPane.add(map, BorderLayout.CENTER);
        frame.setPreferredSize(new Dimension(Constants.PREFERRED_MODULE_WIDTH + 20, Constants.PREFERRED_MODULE_HEIGHT+50));
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	ArrayList<Location> locations = new ArrayList<Location>();
    	Location temp = new Location();
    	temp.setLongitude(20.0);
    	temp.setLatitude(30.0);
    	locations.add(temp);
    	Location temp0 = new Location();
    	temp0.setLongitude(20.0);
    	temp0.setLatitude(30.0);
    	locations.add(temp0);
    	Location temp1 = new Location();
    	temp1.setLongitude(15.0);
    	temp1.setLatitude(40.0);
    	locations.add(temp1);
    	Location temp2 = new Location();
    	temp2.setLongitude(20.0);
    	temp2.setLatitude(30.1);
    	locations.add(temp2);
    	Location temp3 = new Location();
    	temp3.setLongitude(22.0);
    	temp3.setLatitude(33.1);
    	locations.add(temp3);
    	Location temp4 = new Location();
    	temp4.setLongitude(22.1);
    	temp4.setLatitude(33.0);
    	locations.add(temp4);
    	Location temp5 = new Location();
    	temp5.setLongitude(22.1);
    	temp5.setLatitude(33.2);
    	locations.add(temp5);
        new Test(locations);
    }

}
