package se.cth.hedgehogphoto.map;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.LocationObject;

@Deprecated
public class Test {
    private JFrame frame = new JFrame();
    private JLayeredPane map;
    
    public Test(List<LocationObject> locations)
    {
        frame.setPreferredSize(new Dimension(600, 600));
        frame.setLayout(new BorderLayout());
        JLabel loading = new JLabel("loading map...");
        
        JPanel panel = new JPanel();
        
        panel.add(loading, BorderLayout.CENTER);
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        map = new MapWrapper(locations);
//        frame.removeAll();
        panel.remove(loading);
        frame.setLayout(new BorderLayout());
        panel.add(map, BorderLayout.CENTER);
        map.setVisible(true);
        frame.pack();
        frame.revalidate();
        frame.repaint();
        System.out.println("Constructor done");
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	ArrayList<LocationObject> locations = new ArrayList<LocationObject>();
    	locations.add(new LocationObject(20.0, 30.0));
    	locations.add(new LocationObject(15.0, 40.0));
    	locations.add(new LocationObject(10.0, 20.0));
        new Test(locations);
    }

}
