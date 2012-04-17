package se.cth.hedgehogphoto.map;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class Test {
    private JFrame frame = new JFrame();
    private JLayeredPane map;
    
    public Test(List<Point> locationPoints)
    {
        frame.setPreferredSize(new Dimension(500, 800));
        frame.setLayout(new BorderLayout());
        
        map = new MapWrapper(locationPoints);
        
        frame.add(map, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    	ArrayList<Point> points = new ArrayList<Point>();
    	points.add(new Point(109, 185));
    	points.add(new Point(200, 350));
    	points.add(new Point(291, 511));
        new Test(points);
    }

}
