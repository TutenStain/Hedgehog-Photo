package se.cth.hedgehogphoto.map;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Test {
    private JFrame frame = new JFrame();
    private JLayeredPane lpane = new JLayeredPane();
    private JLabel loc1 = new JLabel(new ImageIcon("marker.png"));
    private JLabel loc2 = new JLabel(new ImageIcon("marker.png"));
    private JLabel loc3 = new JLabel(new ImageIcon("marker.png"));
//    private JPanel panelBlue = new JPanel() { 
//    	@Override
//    	public String toString() {
//    		return "blue Panel";
//    	}
//    };
//    private JPanel panelGreen = new JPanel() { 
//    	@Override
//    	public String toString() {
//    		return "green Panel";
//    	}
//    };
//    private JPanel panelYellow = new JPanel() { 
//    	@Override
//    	public String toString() {
//    		return "yellow Panel";
//    	}
//    };
    private JPanel map = Main.getMapPanel();
    
    public Test()
    {
        frame.setPreferredSize(new Dimension(500, 800));
        frame.setLayout(new BorderLayout());
        frame.add(lpane, BorderLayout.CENTER);
//        lpane.setBounds(0, 0, 600, 400);
        MouseListener listener = new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				arg0.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				arg0.getComponent().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				Component c = arg0.getComponent();
				Rectangle r = c.getBounds();
				int x = r.x + r.width / 2;
				int y = r.y + r.height / 2;
				System.out.println("x: " + x + "\t\ty: " + y);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
        	
        };
        
        
        map.setBounds(0, 0, 400, 700);
        map.setOpaque(true);
        lpane.add(map, new Integer(0), 0);
        
        loc1.setBounds(100, 176, 19, 19); //x-9, y-9, 19, 19
        loc2.setBounds(191, 341, 19, 19);
        loc3.setBounds(282, 502, 19, 19);
        loc1.setOpaque(true);
        loc2.setOpaque(true);
        loc3.setOpaque(true);
        
        loc1.addMouseListener(listener);
        loc2.addMouseListener(listener);
        loc3.addMouseListener(listener);
        
        lpane.add(loc1, new Integer(2), 0);
        lpane.add(loc2, new Integer(2), 0);
        lpane.add(loc3, new Integer(2), 0);
        
        frame.pack();
        frame.setVisible(true);
    }


    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new Test();
    }

}
