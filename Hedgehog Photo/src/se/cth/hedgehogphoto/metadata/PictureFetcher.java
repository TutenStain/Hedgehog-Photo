package se.cth.hedgehogphoto.metadata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import se.cth.hedgehogphoto.ImageObject;

/**
 * 
 * @author Julia
 *
 */
public class PictureFetcher {
	private  static JFileChooser chooser;
	//	private  static List<File> files;
	//private static Map<String, ArrayList<File>> files = new HashMap<String, ArrayList<File>>();
	private static List<ImageObject> imageObjects = new ArrayList<ImageObject>(); 
	private static Metadata metadata = new Metadata();
	private static String [] validFileExtensions = {"png", "jpg", "gif"};

	public PictureFetcher() {

		chooser = new JFileChooser();
		chooser.setDialogTitle("Choose pictures");

		chooser.setCurrentDirectory(new java.io.File( System.getProperty("user.home") + "/Pictures"));
		System.out.print(System.getProperty("user.home") + "/Pictures");
		chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		chooser.setAcceptAllFileFilterUsed(false);
		chooser.setMultiSelectionEnabled(true);
		ImagePreviewPanel preview = new ImagePreviewPanel();
		chooser.setAccessory(preview);
		chooser.addPropertyChangeListener(preview);

		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images & PNG", "jpg", "gif","png");
		chooser.setFileFilter(filter);
		fetchFiles();

	}

	public static void fetchFiles() {
		int returnVal = chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {

			System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
			File[] directorys = chooser.getSelectedFiles();
			for(int i = 0; i < directorys.length;i++){
				if(directorys[i].isDirectory()){
					String album = directorys[i].getName();
					System.out.print("ALBUM : " + album);
				//if(chooser.getSelectedFile().isDirectory()){
					//ArrayList<File> synonymer = files.get(chooser.getSelectedFile().getName());

					File[] dirFiles = chooser.getSelectedFile().listFiles();
					for(int j = 0; j < dirFiles.length; j++ ){
						if(dirFiles[j].isDirectory()==false){
							int pos = dirFiles[j].getName().lastIndexOf(".");              //find the pos of the . in the filename
							String end =	dirFiles[j].getName().substring(pos + 1);      // get extention name and place into string ext
							if (isValidFileExtension(end) && dirFiles[j].isFile()){                                //check the file extension is a jpg file type
								System.out.println(dirFiles[j]);      //send filename to client
								ImageObject imageObject;
								if(dirFiles[j].isDirectory()==false){
									imageObject = metadata.getImageObject(dirFiles[j]);
									imageObject.setAlbumName(album);
									
									imageObject.setFileName(dirFiles[j].getName());
									imageObject.setFilePath(dirFiles[j].getPath());
									imageObjects.add(imageObject);
								} 
							}
						}
					}

				}

				else{

					for(int k = 0; k < chooser.getSelectedFiles().length;k++){
						ImageObject  imageObject = Metadata.getImageObject(metadata.extractMetadata((chooser.getSelectedFiles()[k]))); /* TODO: Right now we have two equal ImageObject-classes in different packages. Where to put it? */
						imageObject.setAlbumName(chooser.getSelectedFiles()[k].getParentFile().getName());
						imageObject.setFileName(chooser.getSelectedFiles()[k].getName());
						
						imageObject.setFilePath(chooser.getSelectedFiles()[k].getPath());
						imageObjects.add(imageObject);

					}
				}
			}
		}
	}

	public  List<ImageObject> getImageObjects(){
		return imageObjects;
	}
	
	private static boolean isValidFileExtension(String fileExtension) {
		boolean isValid = false;
		int nbrOfFileExtensions = validFileExtensions.length;
		for (int i = 0; i < nbrOfFileExtensions; i++) {
			if (fileExtension.equalsIgnoreCase(validFileExtensions[i])) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}
}
