package se.cth.hedgehogphoto.metadata;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.sanselan.ImageInfo;
import org.apache.sanselan.ImageReadException;
import org.apache.sanselan.Sanselan;
import org.apache.sanselan.common.IImageMetadata;

/**
 * A wrapper/test-class for the metadata extractor
 * @author Florian
 */
public class Metadata {
	
	private static final String[] metadataTypes = 
			{"Modify Date", "Artist", "XPComment", "XPAuthor", 
					"XPKeywords", "Date Time Original"};
	
	public static void main(String [] args) {
		try {
			searchTag(extract());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static String extract() {
		File file = new File("finbild.jpg");
		return extract(file);
	}
	
	public static String extract(File file) {
		IImageMetadata metadata = null;
			try {
				metadata = Sanselan.getMetadata(file);
			} catch (ImageReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		return metadata.toString();
	}
	
	public static void searchTag(String text) throws IOException {
		BufferedReader br = new BufferedReader( new StringReader(text) );
		List<String> foundTypes = new ArrayList<String>();
		
		String line;
		int nbrOfTypes = metadataTypes.length;
		while((line = br.readLine()) != null) {		
			if (containsTargetMetadata(line)) {
				foundTypes.add(line);
			}
		}
		
		printList(foundTypes);
	}
	
	private static boolean containsTargetMetadata(String line) {
		int nbrOfTypes = metadataTypes.length;
		for(int i = 0; i < nbrOfTypes; i++) {
			if(line.contains(metadataTypes[i])) {
				return true;
			}
		}
		return false;
	}
	
	
	
	private static void printList(List<String> list) {
		for(int i = 0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}
	}
	
	
}
