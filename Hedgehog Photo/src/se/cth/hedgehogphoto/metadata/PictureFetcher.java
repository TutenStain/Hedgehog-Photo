package se.cth.hedgehogphoto.metadata;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import se.cth.hedgehogphoto.objects.ImageObject;

/**
 * 
 * @author Julia
 *
 */
public class PictureFetcher {
	private  static JFileChooser chooser =  new JFileChooser();;
	//	private  static List<File> files;
	//private static Map<String, ArrayList<File>> files = new HashMap<String, ArrayList<File>>();
	private static List<ImageObject> imageObjects = new ArrayList<ImageObject>(); 
	private static String [] validFileExtensions = {"png", "jpg", "jpeg", "gif"};

	public PictureFetcher() { 
		chooser.setDialogTitle("Choose pictures");

		chooser.setCurrentDirectory(new java.io.File( System.getProperty("user.home") + "/Pictures"));
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
			File[] directorys = chooser.getSelectedFiles();
			for(int i = 0; i < directorys.length;i++){
				if(directorys[i].isDirectory()){
					String album = directorys[i].getName();
				//if(chooser.getSelectedFile().isDirectory()){
					//ArrayList<File> synonymer = files.get(chooser.getSelectedFile().getName());

					File[] dirFiles = chooser.getSelectedFile().listFiles();
					for(int j = 0; j < dirFiles.length; j++ ){
						if(dirFiles[j].isDirectory()==false){
							int pos = dirFiles[j].getName().lastIndexOf(".");              //find the pos of the . in the filename
							String end =	dirFiles[j].getName().substring(pos + 1);      // get extension name and place into string ext
							if (isValidFileExtension(end) && dirFiles[j].isFile()){ 
								ImageObject imageObject;
								if(dirFiles[j].isDirectory()==false){
									imageObject = Metadata.getImageObject(dirFiles[j]);
									imageObject.setAlbumName(album);
									imageObject.setFileName(dirFiles[j].getName());
									imageObject.setFilePath(dirFiles[j].getAbsolutePath());
									imageObjects.add(imageObject);
								} 
							}
						}
					}

				}

				else{

					for(int k = 0; k < chooser.getSelectedFiles().length;k++){
						ImageObject  imageObject = Metadata.getImageObject(chooser.getSelectedFiles()[k]); 
						imageObject.setAlbumName(chooser.getSelectedFiles()[k].getParentFile().getName());
						imageObject.setFileName(chooser.getSelectedFiles()[k].getName());
						
						imageObject.setFilePath(chooser.getSelectedFiles()[k].getAbsolutePath());
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
