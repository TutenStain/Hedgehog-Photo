package se.cth.hedgehogphoto.map;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import se.cth.hedgehogphoto.database.Location;

@Deprecated
public class Test {
    private JFrame frame = new JFrame();
    private JPanel map;
    
    public Test(List<Location> locations)
    {
        frame.setPreferredSize(new Dimension(600, 600));
        frame.getContentPane().setLayout(new BorderLayout());
        JLabel loading = new JLabel("loading map...");
        JPanel mainPane = new JPanel();
        mainPane.setLayout(new BorderLayout());
        mainPane.add(loading, BorderLayout.CENTER);
        frame.setContentPane(mainPane);
        frame.pack();
        frame.setVisible(true);
        JPanel map = new MapView(locations);
        mainPane.remove(loading);
        mainPane.add(map, BorderLayout.CENTER);
        frame.pack();
        System.out.println("Constructor done");
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
    	Location temp1 = new Location();
    	temp1.setLongitude(15.0);
    	temp1.setLatitude(40.0);
    	locations.add(temp1);
    	Location temp2 = new Location();
    	temp2.setLongitude(10.0);
    	temp.setLatitude(20.0);
    	locations.add(temp2);
        new Test(locations);
    }

}
