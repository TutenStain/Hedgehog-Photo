package se.cth.hedgehogphoto.map;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LocationMarker extends JLabel {
	private ImageIcon icon = new ImageIcon("marker.png");
	private final int WIDTH = icon.getIconWidth();
	private final int HEIGHT = icon.getIconHeight();
	private final MouseListener mouseListener = new MouseListener() {

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
	
	public LocationMarker(Point p) {
		int fromLeft = p.x - WIDTH / 2;
		int fromTop = p.y - HEIGHT / 2;
		setIcon(icon);
		setOpaque(true);
		setBounds(fromLeft, fromTop, WIDTH, HEIGHT);
		addMouseListener(mouseListener);
	}
	
}
