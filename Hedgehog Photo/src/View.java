package se.cth.hedgehogphoto;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import java.awt.Panel;
import java.awt.FlowLayout;
import javax.swing.JTextPane;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.ScrollPane;

public class View {

	private JFrame frame;
	private JTextField searchField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					View window = new View();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public View() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame =  new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel bottomPanel = new JPanel();
		frame.getContentPane().add(bottomPanel, BorderLayout.SOUTH);
		
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
