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
	private JFileChooser chooser =  new JFileChooser();;
	private List<ImageObject> imageObjects = new ArrayList<ImageObject>(); 
	private String [] validFileExtensions = {"png", "jpg", "jpeg", "gif"};

	public PictureFetcher() { 
		this.chooser.setDialogTitle("Choose pictures");
		this.chooser.setCurrentDirectory(new File( System.getProperty("user.home") + "/Pictures"));
		this.chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		this.chooser.setAcceptAllFileFilterUsed(false);
		this.chooser.setMultiSelectionEnabled(true);
		ImagePreviewPanel preview = new ImagePreviewPanel();
		this.chooser.setAccessory(preview);
		this.chooser.addPropertyChangeListener(preview);
		FileNameExtensionFilter filter = new FileNameExtensionFilter("JPG & GIF Images & PNG", "jpg", "gif","png");
		this.chooser.setFileFilter(filter);
		this.fetchFiles();
	}

	public void fetchFiles() {
		int returnVal = this.chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File[] directorys = this.chooser.getSelectedFiles();
			for(int i = 0; i < directorys.length;i++){
				if(directorys[i].isDirectory()){
					String album = directorys[i].getName();
					File[] dirFiles = this.chooser.getSelectedFile().listFiles();
					for(int j = 0; j < dirFiles.length; j++ ){
						if(dirFiles[j].isDirectory()==false){
							/*find the pos of the . in the filename*/
							int pos = dirFiles[j].getName().lastIndexOf("."); 
							/*get extension name and place into string ext*/
							String end = dirFiles[j].getName().substring(pos + 1);
							if (isValidFileExtension(end) && dirFiles[j].isFile()){ 
								ImageObject imageObject;
								if(dirFiles[j].isDirectory() == false){
									imageObject = Metadata.getImageObject(dirFiles[j]);
									imageObject.setAlbumName(album);
									imageObject.setFileName(dirFiles[j].getName());
									imageObject.setFilePath(dirFiles[j].getAbsolutePath());
									this.imageObjects.add(imageObject);
								} 
							}
						}
					}
				}

				else{
					for(int k = 0; k < this.chooser.getSelectedFiles().length; k++){
						ImageObject  imageObject = Metadata.getImageObject(this.chooser.getSelectedFiles()[k]); 
						imageObject.setAlbumName(this.chooser.getSelectedFiles()[k].getParentFile().getName());
						imageObject.setFileName(chooser.getSelectedFiles()[k].getName());

						imageObject.setFilePath(this.chooser.getSelectedFiles()[k].getAbsolutePath());
						this.imageObjects.add(imageObject);
					}
				}
			}
		}
	}

	public  List<ImageObject> getImageObjects(){
		return this.imageObjects;
	}

	private boolean isValidFileExtension(String fileExtension) {
		boolean isValid = false;
		int nbrOfFileExtensions = this.validFileExtensions.length;
		
		for (int i = 0; i < nbrOfFileExtensions; i++) {
			if (fileExtension.equalsIgnoreCase(this.validFileExtensions[i])) {
				isValid = true;
				break;
			}
		}
		return isValid;
	}
}
