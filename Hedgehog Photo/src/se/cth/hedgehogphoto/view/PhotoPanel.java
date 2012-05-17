package se.cth.hedgehogphoto.view;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import se.cth.hedgehogphoto.database.DaoFactory;
import se.cth.hedgehogphoto.database.TagObject;
import se.cth.hedgehogphoto.objects.FileObject;

public class PhotoPanel extends JPanel {

	private JTextField comment = new 	JTextField();
	private JTextField  tags = new 	JTextField();
	private JTextField  location = new 	JTextField();
	private JLabel commentLabel = new JLabel("Comments");
	private JLabel tagsLabel = new JLabel("Tags:");
	private JLabel locationLabel = new JLabel("Location:");
	private JLabel iconLabel = new JLabel("");
	private ImageIcon icon;
	private JLabel name = new JLabel("Name");
	private JTextField photoName = new 	JTextField();

	/**
	 * @wbp.parser.constructor
	 */
	public PhotoPanel(FileObject f) {
		this(f.getFilePath());
	}

	public PhotoPanel(String path) {
		//	photoName = new JLabel(DaoFactory.getInstance().getJpaPictureDao().findById(path).getName());

		photoName.setBackground(Color.LIGHT_GRAY);
		setName(DaoFactory.getInstance().getJpaPictureDao().findById(path).getName());
		PhotoPanelController ppc = new PhotoPanelController(path);
		comment.addActionListener(ppc);
		tags.addActionListener(ppc);
		location.addActionListener(ppc);
		photoName.addActionListener(ppc);
		photoName.setActionCommand("name");
		comment.setActionCommand("comment");
		tags.setActionCommand("tags");
		location.setActionCommand("location");
		comment.setPreferredSize(new Dimension(100,20));
		commentLabel.setPreferredSize(new Dimension(100,10));
		location.setPreferredSize(new Dimension(100,20));
		locationLabel.setPreferredSize(new Dimension(100,10));
		tags.setPreferredSize(new Dimension(100,20));
		tagsLabel.setPreferredSize(new Dimension(100,10));
		photoName.setPreferredSize(new Dimension(100,20));
		name.setPreferredSize(new Dimension(50,10));

		setLayout(new BorderLayout(0, 0));
		this.setVisible(true);



		//	JPanel tagsPanel = new JPanel();
		//tagsPanel.setLayout(new BoxLayout(getRootPane(),BoxLayout.X_AXIS));
		/*for(JTextField jt: tagsField){
			tagsPanel.add(jt);
			//jt.setSize(20, 20);
			//tagsPanel.setSize(new Dimension(30,20));
		}
		add(tagsPanel, BorderLayout.SOUTH);

		 */JPanel commentsPanel = new JPanel();
		 /*add(commentsPanel, BorderLayout.CENTER);
		  */	commentsPanel.setLayout(new BorderLayout(0, 0));



		  GridBagLayout gBig = new GridBagLayout();
		  setLayout(gBig);
		  GridBagConstraints nameBag = new GridBagConstraints();
		  nameBag.gridx =1;
		  nameBag .gridy =1;
		  nameBag.gridheight =1;
		  nameBag.gridwidth = 1;

		  gBig.setConstraints(name , nameBag);
		  add(name );
		  GridBagConstraints photoNameBag = new GridBagConstraints();
		  photoNameBag.gridx =2;
		  photoNameBag .gridy =1;
		  photoNameBag.gridheight =1;
		  photoNameBag.gridwidth = 1;
		  gBig.setConstraints(photoName , photoNameBag);
		  add(photoName);

		  GridBagConstraints con = new GridBagConstraints();
		  con.gridx = 1;
		  con.gridy = 2;
		  con.gridheight = 3;
		  con.gridwidth = 2;

		  icon = new ImageIcon(path);
		  iconLabel.setIcon(icon);
		  gBig.setConstraints(iconLabel, con);
		  add(iconLabel);
		  //commentsPanel.add(iconLabel, BorderLayout.WEST);
		  //commentsPanel.add(commentField, BorderLayout.CENTER);
		  //commentsPanel.add(locationField, BorderLayout.CENTER);

		  location.setBackground(Color.LIGHT_GRAY);
		  GridBagConstraints loc = new GridBagConstraints();
		  loc.gridx =2;
		  loc.gridy =6;
		  loc.gridheight =1;
		  loc.gridwidth = 1;
		  gBig.setConstraints(location, loc);
		  add(location);
		  GridBagConstraints locLabel = new GridBagConstraints();
		  locLabel.gridx =1;
		  locLabel.gridy =6;
		  locLabel.gridheight =1;
		  locLabel.gridwidth = 1;
		  gBig.setConstraints(locationLabel, locLabel);
		  add(locationLabel);

		  GridBagConstraints comLabel = new GridBagConstraints();
		  comLabel.gridx =3;
		  comLabel.gridy =3;
		  //		comLabel.gridheight =1;
		  //		comLabel.gridwidth = 1;
		  gBig.setConstraints(commentLabel, comLabel);
		  add(commentLabel);
		  comment.setBackground(Color.LIGHT_GRAY);
		  GridBagConstraints com = new GridBagConstraints();
		  com.gridx = 3;
		  com.gridy = 4;
		  //com.gridheight = 1;

		  gBig.setConstraints(comment, com);
		  add(comment);


		  GridBagConstraints tagLabel = new GridBagConstraints();
		  tagLabel.gridx =1;
		  tagLabel.gridy =5;
		  tagLabel.gridheight =1;
		  tagLabel.gridwidth = 1;
		  gBig.setConstraints(tagsLabel,  tagLabel);
		  add(tagsLabel);
		  int x= 0;
		  //int y= 4;
		  //	for(JLabel jt: tagsField){
		  tags.setBackground(Color.LIGHT_GRAY);
		  GridBagConstraints tag = new GridBagConstraints();
		  tag.gridx = x + 2;
		  tag.gridy = 5;
		  tag.gridheight =1;
		  tag.gridwidth = 1;

		  gBig.setConstraints(tags,tag);
		  add(tags);
		  //}

		  /*JPanel tagsLocationsPanel = new JPanel();
		add(tagsLocationsPanel, BorderLayout.SOUTH);

		GroupLayout gl_tagsLocationsPanel = new GroupLayout(tagsLocationsPanel);
		gl_tagsLocationsPanel.setHorizontalGroup(
			gl_tagsLocationsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tagsLocationsPanel.createSequentialGroup()
					.addGroup(gl_tagsLocationsPanel.createParallelGroup(Alignment.LEADING)
						//.addComponent(tagsField)
						.addComponent(locationField))
					.addContainerGap(405, Short.MAX_VALUE))
		);
		gl_tagsLocationsPanel.setVerticalGroup(
			gl_tagsLocationsPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tagsLocationsPanel.createSequentialGroup()
					//.addComponent(tagsField)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(locationField))
		);
		tagsLocationsPanel.setLayout(gl_tagsLocationsPanel);

		JPanel commentsPanel = new JPanel();
		add(commentsPanel, BorderLayout.CENTER);
		commentsPanel.setLayout(new BorderLayout(0, 0));

		icon = new ImageIcon(path);
		iconLabel.setIcon(icon);
		commentsPanel.add(iconLabel, BorderLayout.WEST);
		commentsPanel.add(commentField, BorderLayout.CENTER);

		commentField.setBackground(Color.LIGHT_GRAY);

		   */}
	public void setName(String name){
		this.photoName.setText(name);

	}
	public void setTags(List<? extends TagObject> taggs){
		String output = "";
		for(TagObject t : taggs){
			System.out.print(t);
			output += t.getTag() + ";";
			/*	JLabel tagsLabel = new JLabel();
			tagsLabel.setText(t.getTag() +"");
			tagsLabel.setVisible(true);
			tagsField.add(tagsLabel);
			 */}
		tags.setText(output);
	}
	public void setLocation(String location){
		this.location.setText(location);
	}

	public void setComment(String comment){
		this.comment.setText(comment);
	}

	public ImageIcon getIcon(){
		return this.icon;
	}

	public void setIcon(ImageIcon icon){
		this.iconLabel.setIcon(icon);
	}

	public void displayComments(Boolean b){
		this.comment.setVisible(b);
	}
	public boolean isVisibleComments(){
		return this.comment.isVisible();
	}
	public void displayTags(Boolean b){
		this.tags.setVisible(b);
	}
	public boolean isVisibleTags(){
		return this.tags.isVisible();
	}
	public void displayLocation(Boolean b){
		this.location.setVisible(b);
	}
	public boolean isVisibleLocation(){
		return this.location.isVisible();
	}
}
