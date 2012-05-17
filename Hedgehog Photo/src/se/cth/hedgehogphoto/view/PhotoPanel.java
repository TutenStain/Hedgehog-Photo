package se.cth.hedgehogphoto.view;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;

import se.cth.hedgehogphoto.database.TagI;
import se.cth.hedgehogphoto.database.TagObject;
import se.cth.hedgehogphoto.objects.FileObject;

//TODO This class need to be fixed, photopanels gets a incorrect dimension that does
//not wrap tight around the picture when only the pictures are shown. Did not check if they 
//get the correct size from the begining with all the tags, comments and location.
public class PhotoPanel extends JPanel {

	JLabel commentsLabel = new JLabel("Comments");
	JLabel tagsLabel = new JLabel("Tags:");
	JLabel locationLabel = new JLabel("Location:");
	JLabel iconLabel = new JLabel("");
	ImageIcon icon;
	
	/**
	 * @wbp.parser.constructor
	 */
	public PhotoPanel(FileObject f) {
		this(f.getFilePath());
	}
	
	public PhotoPanel(String path) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel tagsLocationsPanel = new JPanel();
		add(tagsLocationsPanel, BorderLayout.SOUTH);
	
		GroupLayout gl_tagsLocationsPanel = new GroupLayout(tagsLocationsPanel);
		gl_tagsLocationsPanel.setHorizontalGroup(
			gl_tagsLocationsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tagsLocationsPanel.createSequentialGroup()
					.addGroup(gl_tagsLocationsPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(tagsLabel)
						.addComponent(locationLabel))
					.addContainerGap(405, Short.MAX_VALUE))
		);
		gl_tagsLocationsPanel.setVerticalGroup(
			gl_tagsLocationsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tagsLocationsPanel.createSequentialGroup()
					.addComponent(tagsLabel)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(locationLabel))
		);
		tagsLocationsPanel.setLayout(gl_tagsLocationsPanel);
		
		JPanel commentsPanel = new JPanel();
		add(commentsPanel, BorderLayout.CENTER);
		commentsPanel.setLayout(new BorderLayout(0, 0));
		
		icon = new ImageIcon(path);
		iconLabel.setIcon(icon);
		commentsPanel.add(iconLabel, BorderLayout.WEST);
		commentsPanel.add(commentsLabel, BorderLayout.CENTER);
		
	}
	public void setTags(List<? extends TagI> list){
		String output = "";
		for(TagObject t : list){
			output += t.getTag() + " ";
		}
		this.tagsLabel.setText(output);
	}
	public void setLocation(String location){
		this.locationLabel.setText(location);
	}
	
	public void setComment(String comment){
		this.commentsLabel.setText(comment);
	}

	public ImageIcon getIcon(){
		return this.icon;
	}
	
	public void setIcon(ImageIcon icon){
		this.iconLabel.setIcon(icon);
	}
	
	public void displayComments(Boolean b){
		this.commentsLabel.setVisible(b);
	}
	public boolean isVisibleComments(){
		return this.commentsLabel.isVisible();
	}
	public void displayTags(Boolean b){
		this.tagsLabel.setVisible(b);
	}
	public boolean isVisibleTags(){
		return this.tagsLabel.isVisible();
	}
	public void displayLocation(Boolean b){
		this.locationLabel.setVisible(b);
	}
	public boolean isVisibleLocation(){
		return this.locationLabel.isVisible();
	}
}
