package se.cth.hedgehogphoto.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.cth.hedgehogphoto.Constants;
import se.cth.hedgehogphoto.database.Files;
import se.cth.hedgehogphoto.database.Picture;
import se.cth.hedgehogphoto.model.MainModel;
import se.cth.hedgehogphoto.plugin.PluginArea;

public class MainView implements Observer {

	private JFrame frame;
	private JPanel photoViewPanel;
	private	JPanel leftPanelView;
	private JPanel topPanel;
	private static List<PhotoPanel> photoPanels;

	/**
	 * Create the application.
	 */
	public MainView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame =  new JFrame();

		//TODO Minimum size?
		frame.setExtendedState(frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);

		JButton btnShowhideTags = new JButton("Show/Hide tags");


		JButton btnShowhideLocation = new JButton("Show/Hide location");

		JSlider slider = new JSlider(50, 200);

		JButton showHideComments = new JButton("Show/Hide comments");


		GroupLayout gl_bottomPanel = new GroupLayout(bottomPanel);
		gl_bottomPanel.setHorizontalGroup(
				gl_bottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
						.addGap(100)
						.addComponent(btnShowhideTags)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(btnShowhideLocation)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(showHideComments)
						.addGap(202)
						.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(59, Short.MAX_VALUE))
				);
		gl_bottomPanel.setVerticalGroup(
				gl_bottomPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_bottomPanel.createSequentialGroup()
						.addGap(5)
						.addGroup(gl_bottomPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(slider, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_bottomPanel.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnShowhideLocation)
										.addComponent(btnShowhideTags)
										.addComponent(showHideComments)))
										.addContainerGap())
				);
		bottomPanel.setLayout(gl_bottomPanel);

		topPanel = new JPanel();
		topPanel.setLayout(new BorderLayout());
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);

		leftPanelView = new JPanel();
		Dimension d = new Dimension(Constants.PREFERRED_MODULE_WIDTH, Constants.PREFERRED_MODULE_HEIGHT);
		leftPanelView.setPreferredSize(d);
		leftPanelView.setMinimumSize(d);
		leftPanelView.setSize(d);
		frame.getContentPane().add(leftPanelView, BorderLayout.WEST);
		leftPanelView.setLayout(new GridLayout(3, 0, 0, 0));

		//		JButton btnMaps = new JButton("Maps");

		JButton btnCalender = new JButton("Calender");
		leftPanelView.add(btnCalender);

		JScrollPane photoView = new JScrollPane(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		frame.getContentPane().add(photoView, BorderLayout.CENTER);

		photoViewPanel = new JPanel();
		photoView.setViewportView(photoViewPanel);

		photoViewPanel.setLayout(new WrapLayout(FlowLayout.LEFT));

		showHideComments.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<photoPanels.size();i++){
					photoPanels.get(i).displayComments(!photoPanels.get(i).isVisibleComments());
				}
			}
		});
		btnShowhideTags.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				for(int i=0;i<photoPanels.size();i++){
					photoPanels.get(i).displayTags(!photoPanels.get(i).isVisibleTags());
				}
			}
		});
		btnShowhideLocation.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0;i<photoPanels.size();i++){
					photoPanels.get(i).displayLocation(!photoPanels.get(i).isVisibleLocation());
				}


			}
		});
		slider.addChangeListener(new ChangeListener(){

			public void stateChanged(ChangeEvent arg0) {
				JSlider slider = (JSlider)arg0.getSource();
				float scale = (float)slider.getValue()/100;

				for(int i=0;i<photoPanels.size();i++){
					Image image = photoPanels.get(i).getIcon().getImage();
					BufferedImage bi = resize(image, Math.round(image.getWidth(null)*scale),
							Math.round(image.getHeight(null)*scale));
					ImageIcon icon2 = new ImageIcon(bi);

					photoPanels.get(i).setIcon(icon2);
				}
			}

		});

		frame.setVisible(true);
	}

	private static BufferedImage resize(Image image, int width, int height) {
		//TODO added to skip the picture with an illegal name
		if(width <= 0 || height <= 0){
			return new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
		}
		BufferedImage resizedImage = new BufferedImage(width, height,
				BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
		return resizedImage;
	}

	//TODO This method should be replaced by addPlugin
	@Deprecated
	public void addToLeftPanel(JPanel panel){
		leftPanelView.add(panel);
	}
	//TODO This method should be replaced by addPlugin
	@Deprecated
	public void addToTopPanel(JPanel panel, String orientation){
		topPanel.add(panel, orientation);
	}

	//TODO Fix this method.
	public void addPlugin(JPanel panel, PluginArea placement){
		if(panel != null){
			if(placement == PluginArea.LEFT_TOP){
				//TODO Add plugin view to LEFT_TOP
			}

			if(placement == PluginArea.LEFT_MIDDLE){
				//TODO Add plugin view to LEFT_MIDDLe
			}

			if(placement == PluginArea.LEFT_BOTTOM){
				//TODO Add plugin view to LEFT_BOTTOM
			}
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		photoPanels = new ArrayList<PhotoPanel>();
		List<Picture> images;

		if(arg1 instanceof MainModel) {
			MainModel model = (MainModel)arg1;
			images = model.getImages();
			photoViewPanel.removeAll();
			for(int i = 0; i<images.size(); i++) {
				PhotoPanel pp = new PhotoPanel(images.get(i).getPath());
				/*pp.setComment(images.get(i).getComment().getComment());
				pp.setTags(images.get(i).getTags().get(i).getTag());
				pp.setLocation(images.get(i).getLocation().getLocation());*/
				photoPanels.add(i, pp);
				photoViewPanel.add(pp);
				frame.revalidate();
			}
		}

		if(arg1 instanceof Files){
			images = Files.getInstance().getPictureList();
			//TODO Maybe refresh in another way than removing the PhotoPanels?
			photoViewPanel.removeAll();
			for(int i = 0; i<images.size(); i++) {
				PhotoPanel pp = new PhotoPanel(images.get(i).getPath());
				/*pp.setComment(images.get(i).getComment().getComment());
				pp.setTags(images.get(i).getTags().get(i).getTag());
				pp.setLocation(images.get(i).getLocation().getLocation());*/
				photoPanels.add(i, pp);
				photoViewPanel.add(pp);
				frame.revalidate();
			}
		}
	}
}
