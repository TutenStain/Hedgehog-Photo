package se.cth.hedgehogphoto;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.ScrollPane;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

public class View {

	private JFrame frame;
	private JTextField searchField;

	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame =  new JFrame("Hedgehog Photo");
		frame.setSize(1100, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		frame.setVisible(true);
		
		JCheckBox checkboxTag = new JCheckBox("Show/hide tags");
		
		JCheckBox checkboxText = new JCheckBox("Show/hide text");
		bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		bottomPanel.add(checkboxTag);
		bottomPanel.add(checkboxText);
		
		JSlider slider = new JSlider();
		bottomPanel.add(slider);
		
		JPanel topPanel = new JPanel();
		frame.getContentPane().add(topPanel, BorderLayout.NORTH);
		
		searchField = new JTextField();
		searchField.setText("Search..");
		searchField.setColumns(10);
		
		JLabel lblAlbum = new JLabel("Album");
		
		JLabel lblicon = new JLabel("Igelkottsbild");
		GroupLayout gl_topPanel = new GroupLayout(topPanel);
		gl_topPanel.setHorizontalGroup(
			gl_topPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_topPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblicon)
					.addGap(38)
					.addComponent(lblAlbum)
					.addPreferredGap(ComponentPlacement.RELATED, 164, Short.MAX_VALUE)
					.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(44))
		);
		gl_topPanel.setVerticalGroup(
			gl_topPanel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_topPanel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_topPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(searchField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAlbum)
						.addComponent(lblicon)))
		);
		topPanel.setLayout(gl_topPanel);
		
		JPanel leftPanelView = new JPanel();
		frame.getContentPane().add(leftPanelView, BorderLayout.WEST);
		leftPanelView.setLayout(new GridLayout(3, 0, 0, 0));
		
		JButton btnMaps = new JButton("Maps");
		leftPanelView.add(btnMaps);
		
		JButton btnTags = new JButton("Tags");
		leftPanelView.add(btnTags);
		
		JButton btnCalender = new JButton("Calender");
		leftPanelView.add(btnCalender);
		
		ScrollPane photoView = new ScrollPane();
		frame.getContentPane().add(photoView, BorderLayout.CENTER);
		
		JPanel photoViewPanel = new JPanel();
		photoView.add(photoViewPanel);
		
		PhotoPanel photoPanel = new PhotoPanel();
		photoViewPanel.add(photoPanel);
	}
}
