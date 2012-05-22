package se.cth.hedgehogphoto.global;

import java.awt.Color;
import java.awt.Toolkit;

/**
 * Constants used around the application
 */
public interface Constants {
	public static final int PREFERRED_WINDOW_HEIGHT = 1000;
	public static int PREFERRED_WINDOW_WIDTH = 500;
	public static final int PREFERRED_STARTUP_WINDOW_WIDTH = 410;
	public static final int PREFERRED_STARTUP_WINDOW_HEIGHT = 410;
	public static final int PREFERRED_MODULE_WIDTH = 300; //Width of the left panel-modules
	public static final int PREFERRED_MODULE_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height / 3; //Height of the left panel-modules
	public static final float PREFERRED_PICTURE_HEIGHT = 200;
	public static final float MAX_PICTURE_HEIGHT = Toolkit.getDefaultToolkit().getScreenSize().height - 300;
	public static final float MAX_PICTURE_WIDTH = Toolkit.getDefaultToolkit().getScreenSize().width - PREFERRED_MODULE_WIDTH - 100;
	public static final Color GUI_BACKGROUND = new Color(234, 234, 234);
}
