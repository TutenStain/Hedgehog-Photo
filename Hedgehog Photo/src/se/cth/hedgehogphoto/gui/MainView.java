package se.cth.hedgehogphoto.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import se.cth.hedgehogphoto.Files;
import se.cth.hedgehogphoto.FileObject;
import se.cth.hedgehogphoto.MainModel;

public class MainView implements Observer {

	private JFrame frame;
	private JPanel photoViewPanel;
	private	JPanel leftPanelView;
	private JPanel topPanel;
	List<PhotoPanel> photoPanels = new ArrayList<PhotoPanel>();

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

		JSlider slider = new JSlider();

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
		frame.getContentPane().add(leftPanelView, BorderLayout.WEST);
		leftPanelView.setLayout(new GridLayout(3, 0, 0, 0));

		JButton btnMaps = new JButton("Maps");
		leftPanelView.add(btnMaps);

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
		
		frame.setVisible(true);
	}
	
	public void addToLeftPanel(JPanel panel){
		leftPanelView.add(panel);
	}
	
	public void addToTopPanel(JPanel panel, String orientation){
		topPanel.add(panel, orientation);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		if(arg1 instanceof MainModel) {
			MainModel model = (MainModel)arg1;
			List<FileObject> images = model.getImages();
			photoViewPanel.removeAll();
			for(int i = 0; i<10; i++) {
				PhotoPanel pp = new PhotoPanel(images.get(i));
				photoPanels.add(i, pp);
				photoViewPanel.add(pp);
				frame.revalidate();
			}
		}
		
		if(arg1 instanceof Files){
			List<FileObject> images = Files.getInstance().getList();
			photoViewPanel.removeAll();
			for(int i = 0; i<10; i++) {
				// pp = new PhotoPanel(images.get(i));
		//		photoPanels.add(i, pp);
			//	photoViewPanel.add(pp);
				frame.revalidate();
			}
		}
	}
}
