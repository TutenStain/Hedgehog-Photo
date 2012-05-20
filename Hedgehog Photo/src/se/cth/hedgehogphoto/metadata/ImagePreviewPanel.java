package se.cth.hedgehogphoto.metadata;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
 /**
  * @author Michael Urban 
  */

@SuppressWarnings("serial")
public class ImagePreviewPanel extends JPanel
        implements PropertyChangeListener {
    
    private int width, height;
    private ImageIcon icon;
    private Image image;
    private static final int SIZE = 155;
    private Color bg;
    
    public ImagePreviewPanel() {
        setPreferredSize(new Dimension(SIZE, -1));
        bg = getBackground();
    }
    
    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        
        // Make sure we are responding to the right event.
        if (propertyName.equals(JFileChooser.SELECTED_FILE_CHANGED_PROPERTY)) {
            File selection = (File)e.getNewValue();
            String name;
            
            if (selection == null)
                return;
            else
                name = selection.getAbsolutePath();
            
            /*
             * Make reasonably sure we have an image format that AWT can
             * handle so we don't try to draw something silly.
             */
            if (name != null) {
            	String nameLower = name.toLowerCase();
            	if (nameLower.endsWith(".jpg") ||
            			nameLower.endsWith(".jpeg") ||
            			nameLower.endsWith(".gif")) { //TODO: Put this in a separate method, and the types in a list. Make it easily extendable. See pictureFetcher.isValidFileExtendsion
            		icon = new ImageIcon(name);
            		image = icon.getImage();
            		scaleImage();
            		repaint();
            	}
            }
        }
    }
    
    private void scaleImage() {
        width = image.getWidth(this);
        height = image.getHeight(this);
        double ratio = 1.0;
       
        /* 
         * Determine how to scale the image. Since the accessory can expand
         * vertically make sure we don't go larger than 150 when scaling
         * vertically.
         */
        if (width >= height) {
            ratio = (double)(SIZE-5) / width;
            width = SIZE-5;
            height = (int)(height * ratio);
        }
        else {
            if (getHeight() > 150) {
                ratio = (double)(SIZE-5) / height;
                height = SIZE-5;
                width = (int)(width * ratio);
            }
            else {
                ratio = (double)getHeight() / height;
                height = getHeight();
                width = (int)(width * ratio);
            }
        }
                
        image = image.getScaledInstance(width, height, Image.SCALE_DEFAULT);
    }
    
    public void paintComponent(Graphics g) {
        g.setColor(bg);
        
        /*
         * If we don't do this, we will end up with garbage from previous
         * images if they have larger sizes than the one we are currently
         * drawing. Also, it seems that the file list can paint outside
         * of its rectangle, and will cause odd behavior if we don't clear
         * or fill the rectangle for the accessory before drawing. This might
         * be a bug in JFileChooser.
         */
        g.fillRect(0, 0, SIZE, getHeight());
        g.drawImage(image, getWidth() / 2 - width / 2 + 5,
                getHeight() / 2 - height / 2, this);
    }
    
}
