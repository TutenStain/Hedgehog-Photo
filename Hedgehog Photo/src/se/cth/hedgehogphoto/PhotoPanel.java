package se.cth.hedgehogphoto;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Label;

public class PhotoPanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public PhotoPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		
		JLabel lblTags = new JLabel("Tags");
		
		JLabel lblLocations = new JLabel("Locations");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTags)
						.addComponent(lblLocations))
					.addContainerGap(395, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(lblTags)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblLocations))
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		add(panel_1, BorderLayout.EAST);
		
		JLabel lblBildBeskrivning = new JLabel((String) null);
		panel_1.add(lblBildBeskrivning);
		
		JLabel lblBeskrivandeText = new JLabel("Beskrivande text");
		panel_1.add(lblBeskrivandeText);
		
		JLabel lblZoom = new JLabel((String) null);
		panel_1.add(lblZoom);
		
		JPanel panel_2 = new JPanel();
		add(panel_2, BorderLayout.CENTER);
		
		JLabel lblBild = new JLabel("Bild");
		panel_2.add(lblBild);

	}

}
