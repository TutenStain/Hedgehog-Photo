package se.cth.hedgehogphoto.search;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import se.cth.hedgehogphoto.FileObject;

public class SearchComponent extends JPanel{
	
	public SearchComponent(final FileObject fo) {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		JLabel image = new JLabel("");
		image.setMinimumSize(new Dimension(50, 50));
		image.setMaximumSize(new Dimension(50, 50));
		image.setPreferredSize(new Dimension(50, 50));
		//TODO Change path to fo.getImagePath();
		image.setIcon(new ImageIcon("/home/tutenstain/Dropbox/dylan.jpg"));
		image.setAlignmentY(Component.LEFT_ALIGNMENT);
		add(image);
		
		//TODO A kind of bad implementation? Maybe make a controller class for this later.
		addMouseListener( new MouseListener() {     
			@Override
			public void mouseClicked(MouseEvent e) {
	            Rectangle r = new Rectangle(e.getLocationOnScreen().x, e.getLocationOnScreen().y, getBounds().width, getBounds().height);
	            if(r.contains(e.getLocationOnScreen())){
	            	System.out.println(fo.getImagePath());
	            	System.out.println("Clicked!!!!");
	             } else {
	            	System.out.println("NOTHING CLICKED");
	            }
	        }

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		add(Box.createRigidArea(new Dimension(3, 0)));
		
		JTextArea comment = new JTextArea(fo.getComment());
		comment.setEnabled(false);
		comment.setBackground(this.getBackground());
		comment.setWrapStyleWord(true);
		comment.setLineWrap(true);
		comment.setAutoscrolls(false);
		comment.setFont(new Font("Serif", Font.PLAIN, 13));
		comment.setDisabledTextColor(Color.BLACK);
		comment.setAlignmentY(Component.LEFT_ALIGNMENT);
		comment.setPreferredSize(new Dimension(90, 50));
		comment.setMinimumSize(new Dimension(90, 50));
		comment.setMaximumSize(new Dimension(90, 50));
		add(comment);
		
		add(Box.createRigidArea(new Dimension(3, 0)));
		
		//TODO Maybe a better implementation?
		String str = "";
		Iterator<String> itr = fo.getTags().iterator();
		while(itr.hasNext()){
			str += itr.next() + " "; 
		}
		
		JTextArea tags = new JTextArea(str);
		tags.setEnabled(false);
		tags.setBackground(this.getBackground());
		tags.setDisabledTextColor(Color.BLACK);
		tags.setFont(new Font("Serif", Font.BOLD, 13));
		tags.setWrapStyleWord(true);
		tags.setLineWrap(true);
		tags.setAutoscrolls(false);
		tags.setPreferredSize(new Dimension(90, 50));
		tags.setMinimumSize(new Dimension(90, 50));
		tags.setMaximumSize(new Dimension(90, 50));
		tags.setAlignmentY(Component.LEFT_ALIGNMENT);
		add(tags);
	}
	
}
