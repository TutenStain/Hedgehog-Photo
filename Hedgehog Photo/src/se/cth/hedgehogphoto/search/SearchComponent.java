package se.cth.hedgehogphoto.search;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.ImageIcon;

import se.cth.hedgehogphoto.FileObject;

public class SearchComponent extends JPanel{
	public SearchComponent(FileObject fo) {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		JLabel image = new JLabel("");
		image.setMinimumSize(new Dimension(50, 50));
		image.setMaximumSize(new Dimension(50, 50));
		image.setPreferredSize(new Dimension(50, 50));
		//TODO Change path to fo.getImagePath();
		image.setIcon(new ImageIcon("/home/tutenstain/Dropbox/dylan.jpg"));
		image.setAlignmentY(Component.LEFT_ALIGNMENT);
		add(image);
		
		JLabel comment = new JLabel(fo.getComment());
		comment.setAlignmentY(Component.LEFT_ALIGNMENT);
		add(comment);
		
		//TODO Maybe a better implementation?
		String str = "";
		Iterator<String> itr = fo.getTags().iterator();
		while(itr.hasNext()){
			str += itr.next() + " "; 
		}
		
		JLabel tags = new JLabel(str);
		comment.setPreferredSize(new Dimension(50, 50));
		comment.setMinimumSize(new Dimension(50, 50));
		comment.setMaximumSize(new Dimension(50, 50));
		comment.setAlignmentY(Component.LEFT_ALIGNMENT);
		add(tags);
	}
	
}
