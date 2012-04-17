package se.cth.hedgehogphoto.search;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Iterator;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import se.cth.hedgehogphoto.FileObject;

/**
 * @author Barnabas Sapan
 */

public class SearchComponentView extends JPanel{
	private JTextArea comment;
	private JTextArea tags;
	
	public SearchComponentView(final FileObject fo) {
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(10);
		setLayout(flowLayout);
		
		JLabel image = new JLabel();
		image.setMinimumSize(new Dimension(50, 50));
		image.setMaximumSize(new Dimension(50, 50));
		image.setPreferredSize(new Dimension(50, 50));
		image.setIcon(new ImageIcon(fo.getFilePath()));
		add(image);
		
		//TODO Maybe a better implementation?
		String str = "";
		Iterator<String> itr = fo.getTags().iterator();
		while(itr.hasNext()){
			str += itr.next() + " "; 
		}
		
		tags = new JTextArea(str);
	    tags.setEditable(false);  
	    tags.setCursor(null);  
	    tags.setOpaque(false);  
	    tags.setFocusable(false); 
	    tags.setWrapStyleWord(true);
	    tags.setLineWrap(true);
	    tags.setAutoscrolls(false);
		tags.setFont(new Font("Serif", Font.BOLD, 13));
		tags.setMinimumSize(new Dimension(60, 50));
		tags.setMaximumSize(new Dimension(60, 50));
		tags.setPreferredSize(new Dimension(60, 50));
		add(tags);
		
		comment = new JTextArea(fo.getComment());
	    comment.setEditable(false);  
	    comment.setCursor(null);  
	    comment.setOpaque(false);  
	    comment.setFocusable(false); 
		comment.setWrapStyleWord(true);
		comment.setLineWrap(true);
		comment.setAutoscrolls(false);
		comment.setFont(new Font("Serif", Font.PLAIN, 13));
		comment.setMinimumSize(new Dimension(70, 50));
		comment.setMaximumSize(new Dimension(70, 50));
		comment.setPreferredSize(new Dimension(70, 50));
		add(comment);
	}
	
	@Override
	public void addMouseListener(MouseListener l){
		super.addMouseListener(l);
		tags.addMouseListener(l);
		comment.addMouseListener(l);
	}
}
