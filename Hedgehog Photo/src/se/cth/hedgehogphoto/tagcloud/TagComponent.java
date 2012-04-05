package se.cth.hedgehogphoto.tagcloud;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;

/**
 * @author Barnabas Sapan
 */

public class TagComponent extends JLabel {
	TagComponent(String txt){
		super(txt);
	}
}