import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * 
 * @author David Grankvist
 */

@SuppressWarnings("serial")
public class ColorChooser extends JPanel{
	
	private JButton[] buttonGrid = new JButton[9];
	
	public ColorChooser(){
		this.setLayout(new GridLayout(3, 3));
		this.arrangeButtons();
	}
	
	public void arrangeButtons(){
		for(int i=0; i < buttonGrid.length; i++){
			buttonGrid[i] = new JButton();
			this.add(buttonGrid[i]);
		}
		
		this.buttonGrid[0].setBackground(Color.white);
		this.buttonGrid[1].setBackground(Color.black);
		this.buttonGrid[2].setBackground(Color.gray);
		this.buttonGrid[3].setBackground(Color.blue);
		this.buttonGrid[4].setBackground(Color.green);
		this.buttonGrid[5].setBackground(Color.cyan);
		this.buttonGrid[6].setBackground(Color.red);
		this.buttonGrid[7].setBackground(Color.orange);
		this.buttonGrid[8].setBackground(Color.yellow);
	}
	
	public void addListener(ActionListener listener){
		for(int i=0; i < this.buttonGrid.length; i++){
			this.buttonGrid[i].addActionListener(listener);
		}
	}
}
