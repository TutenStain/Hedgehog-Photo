package se.cth.hedgehogphoto.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Test {
    private JFrame frame = new JFrame();
    private JLayeredPane lpane = new JLayeredPane();
    private JPanel panelBlue = new JPanel() { 
    	@Override
    	public String toString() {
    		return "blue Panel";
    	}
    };
    private JPanel panelGreen = new JPanel() { 
    	@Override
    	public String toString() {
    		return "green Panel";
    	}
    };
    private JPanel panelYellow = new JPanel() { 
    	@Override
    	public String toString() {
    		return "yellow Panel";
    	}
    };
    private JPanel map = Main.getMapPanel();
    
    public Test()
    {
        frame.setPreferredSize(new Dimension(500, 800));
        frame.setLayout(new BorderLayout());
        frame.add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, 600, 400);
        panelBlue.setBackground(Color.BLUE);
        panelBlue.setBounds(109, 185, 7, 12);
        panelBlue.setOpaque(true);
        panelGreen.setBackground(Color.GREEN);
        panelGreen.setBounds(200, 350, 7, 12);
        panelGreen.setOpaque(true);
        panelYellow.setBackground(Color.YELLOW);
        panelYellow.setBounds(291, 511, 7, 12);
        panelYellow.setOpaque(true);
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
				System.out.println(arg0.getSource().toString());
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {}
        	
        };
        
        panelBlue.addMouseListener(listener);
        panelGreen.addMouseListener(listener);
        panelYellow.addMouseListener(listener);
        
        map.setBounds(0, 0, 400, 700);
        map.setOpaque(true);
        lpane.add(panelBlue, new Integer(1), 0);
        lpane.add(panelGreen, new Integer(1), 0);
        lpane.add(panelYellow, new Integer(1), 0);
        lpane.add(map, new Integer(0), 0);
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
