package se.cth.hedgehogphoto.search.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import se.cth.hedgehogphoto.database.PictureObject;
import se.cth.hedgehogphoto.database.TagObject;
import se.cth.hedgehogphoto.view.ImageUtils;

/**
 * @author Barnabas Sapan
 */

@SuppressWarnings("serial")
public class JPopupListItem extends JPanel {
	private JTextArea comment;
	private JTextArea tags;
	private PictureObject picture;
	
	public JPopupListItem(PictureObject pic) {
		this.picture = pic;
		FlowLayout flowLayout = new FlowLayout();
		flowLayout.setVgap(0);
		flowLayout.setHgap(10);
		setLayout(flowLayout);
		
		JLabel image = new JLabel();
		image.setMinimumSize(new Dimension(65, 50));
		image.setMaximumSize(new Dimension(65, 50));
		image.setPreferredSize(new Dimension(65, 50));
		
		BufferedImage bi = ImageUtils.resize(new ImageIcon(pic.getPath()).getImage(), 65, 50);
		image.setIcon(new ImageIcon(bi));
		add(image);

		StringBuilder sb = new StringBuilder("");
		for(TagObject t : pic.getTags()){
			sb.append(t.getTag());
			sb.append(' ');
		}
		
		this.tags = new JTextArea(sb.toString());
		initJTextArea(this.tags);
		this.tags.setFont(new Font("Serif", Font.BOLD, 13));
		this.tags.setMinimumSize(new Dimension(60, 50));
		this.tags.setMaximumSize(new Dimension(60, 50));
		this.tags.setPreferredSize(new Dimension(60, 50));
		add(this.tags);
		
		this.comment = new JTextArea(pic.getComment().getComment());
	    initJTextArea(this.comment);
		this.comment.setFont(new Font("Serif", Font.PLAIN, 13));
		this.comment.setMinimumSize(new Dimension(70, 50));
		this.comment.setMaximumSize(new Dimension(70, 50));
		this.comment.setPreferredSize(new Dimension(70, 50));
		add(this.comment);
	}
	
	/** Mutates the component that gets passed in to this method. */
	private void initJTextArea(JTextArea textArea) {
		textArea.setEditable(false);  
		textArea.setCursor(null);  
		textArea.setOpaque(false);  
		textArea.setFocusable(false); 
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setAutoscrolls(false);
	}
	
	public void setPicture(PictureObject pic) {
		this.picture = pic;
	}
	
	public PictureObject getPicture() {
		return this.picture;
	}
	
	@Override
	public void addMouseListener(MouseListener l){
		super.addMouseListener(l);
		this.tags.addMouseListener(l);
		this.comment.addMouseListener(l);
	}
}
