package se.cth.hedgehogphoto.metadata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;

/**
 * A wrapper/test-class for the metadata extractor
 * @author Florian
 */
public class Metadata {
	
	public static void main(String [] args) {
		extract();
	}
	
	public static void extract(File file) {
		
	}
	
	public static void extract() {
		File file = new File("finbild.jpg");
		IImageMetadata metadata = null;
		try {
			metadata = Sanselan.getMetadata(file);
		} catch (ImageReadException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList items = metadata.getItems();
		System.out.println(metadata.toString()+"\n\n");
		
		for(int i = 0; i<items.size();i++) {
			System.out.println(items.get(i).toString());
		}
	}
}
