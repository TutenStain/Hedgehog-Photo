package se.cth.hedgehogphoto.map;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class Test {
    private JFrame frame = new JFrame();
    private JLayeredPane lpane = new JLayeredPane();
    private JPanel panelBlue = new JPanel();
    private JPanel panelGreen = new JPanel();
    private JPanel panelYellow = new JPanel();
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
