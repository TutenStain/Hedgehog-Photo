package se.cth.hedgehogphoto.view;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageUtils {
	public static BufferedImage resize(Image image, int width, int height) {
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
	
	public static BufferedImage resize(Image image, Dimension dimension) {
		return ImageUtils.resize(image, dimension.width, dimension.height);
	}
}
