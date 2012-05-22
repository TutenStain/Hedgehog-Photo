import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.util.List;

import se.cth.hedgehogphoto.database.PictureObject;

public interface JPopupItemI {
	public final Color DEFAULT_COLOR = Color.LIGHT_GRAY;
	public final Color HOVER_COLOR = new Color(230, 230, 180);
	
	public void setBackground(Color color);
	
	public List<PictureObject> getPictures(); 

	public void addMouseListener(MouseAdapter listener);
}
