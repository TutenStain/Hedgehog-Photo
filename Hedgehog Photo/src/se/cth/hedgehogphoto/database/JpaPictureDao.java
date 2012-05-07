package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;





public class JpaPictureDao extends JpaDao<Picture, String> implements PictureDao {

	
	public  List<Picture> searchfromComments(String search){
		if(!(search.equals(""))){
			search = search.toLowerCase();
			JpaCommentDao jcd = new JpaCommentDao();
			List<Comment> commments = (List<Comment>)jcd.findByLike("comment", search);
			
		if(commments != null){
			List<Picture> pictures = new ArrayList<Picture>();
			for(Comment c: commments){
				pictures.addAll(findByEntity(c,"Comment"));
			}
			return pictures;
		}
	
		}
		return null;
}
	public  Picture makePicture(FileObject f, Album theAlbum){
		beginTransaction();
		
		Picture pic = new Picture();
		pic.setPath(f.getFilePath());	
			pic.setDate(f.getDate());		
		if(f.getFileName() != null ||(!f.getFileName().equals("")))
			pic.setName(f.getFileName());
		if(theAlbum != null){
			
			List<Picture> thePictures = theAlbum.getPictures();
			if(!(thePictures.contains(pic)))
			thePictures.add(pic);
			theAlbum.setPictures(thePictures);
			pic.setAlbum(theAlbum);
			entityManager.persist(theAlbum);
		}
			persist(pic);
			commitTransaction();	
			return pic;
	
	}
	public  List<Picture> searchfromNames(String search){
		if(!(search.equals(""))){
		search = search.toLowerCase();
		return findByLike("name",search);
		}else{
			return null;
		}
}
	public List<Picture> searchfromDates(String search){
		if(!(search.equals(""))){
		search = search.toLowerCase();
		return findByLike("date",search);
		}else{
			return null;
		}
		}

	public List<Picture> searchfromTags(String search){
		if(!(search.equals(""))){
		JpaTagDao jtd = new JpaTagDao();
		List<Tag> tags = jtd.findByLike("tag", search);
		List<Picture> pictures = new ArrayList<Picture>();
 		for(Tag t: tags){
 			try{
 				System.out.print(findByEntity(t,"dao.database.Tag"));
			pictures.addAll(findByEntity((Object)t,"dao.database.Tag"));
 			}catch(Exception e){
 				
 			}
 			}
 		return pictures;
	}
		return null;
		}
	public List<Picture> searchfromLocations(String search){
		if(!(search.equals(""))){
	
		search = search.toLowerCase();
		List<Picture> 	pictures = new ArrayList<Picture>();
			JpaLocationDao jld = new JpaLocationDao();
			List<Location> locations = jld.findByLike("location", search);
			
			if(!(locations.isEmpty())){
			
				for(Location l:locations){
					pictures.addAll(findByEntity(l, "dao.database.Location"));
				}
	
		}
		return pictures;
		}else{
			return null;
		}
			
	}
	public  Picture getfromPath(String search){
		search = search.toLowerCase();
		return findById(search);

	}
	public void addTag(String tag, String filePath){
		
		tag = tag.toLowerCase();
		filePath = filePath.toLowerCase();
		Picture picture = findById(filePath);
		List<Tag> tags = picture.getTags();
		if( picture != null ){
			JpaTagDao jtd = new JpaTagDao();
			Tag tagg = jtd.findById(tag);
				if(tagg != null){
				List<Picture> pics = tagg.getPictures();
				if(!(pics.contains(picture))){
					beginTransaction();
					pics.add(picture);
					tagg.setPictures(pics);
					if(tags == null)
						tags = new ArrayList<Tag>();
						
					tags.add(tagg);
					picture.setTags(tags);
					entityManager.persist(tagg);
					commitTransaction();	
				}
			}else{
				beginTransaction();
				Tag newTag = new Tag();
				newTag.setTag(tag);
				List<Picture> pictures = new ArrayList<Picture>();
				pictures.add(picture);
				newTag.setPictures(pictures);
				List<Tag> taggs =picture.getTags();
				if(taggs == null)
					taggs = new ArrayList<Tag>();
					
				taggs.add(newTag);
				picture.setTags(tags);
				persist(picture);
				entityManager.persist(newTag);
				commitTransaction();	
			}

		}
}
	public  void addComment(String comment, String filePath){
		comment = comment.toLowerCase();
		filePath = filePath.toLowerCase();
		Picture picture = findById(filePath);
		if(picture != null ) {
			JpaCommentDao jcd = new JpaCommentDao();
			Comment comm = jcd.findById(comment);
				if(comm!= null){
				beginTransaction();
				List<Picture> pics = comm.getPictures();

				if((!pics.contains(picture))){
					pics.add(picture);
					comm.setPicture(pics);
					persist(picture);
					entityManager.persist(comm);;
					commitTransaction();
				}


			}else{
				beginTransaction();
				Comment com = new Comment();
				com.setComment(comment);
				List<Picture> pictures = new ArrayList<Picture>();
				pictures.add(picture);
				com.setPicture(pictures);
				picture.setComment(com);
				entityManager.persist(com);
				persist(picture);
				commitTransaction();
			}
		}
	}
		public void addLocation(String location, String filePath){
			location = location.toLowerCase();
			filePath = filePath.toLowerCase();
			Picture picture = findById(filePath);
			if(picture != null) {
				JpaLocationDao jld = new JpaLocationDao();
				Location loc = (Location)jld.findById(location);
				if(loc != null){
					
					List<Picture> pics =loc.getPictures();

					if((!pics.contains(picture))){

						beginTransaction();
						pics.add(picture);
						loc.setPictures(pics);
						picture.setLocation(loc);
						persist(picture);
						entityManager.persist(loc);
						commitTransaction();
					}


				}else{

					beginTransaction();
					loc= new Location();
					loc.setLocation(location);
					List<Picture> pictures = new ArrayList<Picture>();
					pictures.add(picture);
					loc.setPictures(pictures);
					picture.setLocation(loc);
					entityManager.persist(loc);
					persist(picture);
					commitTransaction();
				}
			}

		}
		public void deleteTags(String filePath){
			filePath = filePath.toLowerCase();
			Picture picture = findById(filePath);
			if(picture != null){
				List<Tag> tags = picture.getTags();
				for(Tag tag: tags){

					beginTransaction();
					List<Picture> pics =tag.getPictures();
					pics.remove(picture);
					tag.setPictures(pics);
					entityManager.persist(tag);
					commitTransaction();
				}
			}
		}
		public void deleteComment(String filePath){
			filePath = filePath.toLowerCase();
			Picture picture = findById(filePath);
			if(picture != null){
				Comment com = picture.getComment();
				beginTransaction();
				List<Picture> pictures = com.getPictures();
				pictures.remove(picture);
				com.setPicture(pictures);
				entityManager.persist(com);
				commitTransaction();
			}
			
		}
		public void deleteLocation(String filePath){
			filePath = filePath.toLowerCase();
			Picture picture = findById(filePath);
			if(picture != null){
				Location loc = picture.getLocation();
				beginTransaction();
				List<Picture>  picts = loc.getPictures();
				picts.remove(picture);
				loc.setPictures(picts);
				entityManager.persist(loc);
				commitTransaction();
			}
		}
	
		 public void deletePicture (String  filePath){
			 Picture picture = findById(filePath);

			 if(picture != null){
				 List<Tag> tags = picture.getTags();
				 for(Tag tag: tags){

					beginTransaction();
					 List<Picture> pics =tag.getPictures();
					 pics.remove(picture);
					 tag.setPictures(pics);
					 entityManager.persist(tag);
					 commitTransaction();	
				 }

					beginTransaction();
				 Album album = picture.getAlbum();
				 List<Picture> pics = album.getPictures();
				 pics.remove(picture);
				 album.setPictures(pics);
				 if(album.getPictures().isEmpty()){
					 entityManager.remove(album);
				 }
				 entityManager.persist(album);
				 commitTransaction();		

				 Location loc = picture.getLocation();

					beginTransaction();
				 List<Picture>  picts = loc.getPictures();
				 picts.remove(picture);
				 loc.setPictures(picts);
				 entityManager.persist(loc);
				 commitTransaction();		

				 Comment com = picture.getComment();

					beginTransaction();
				 List<Picture> pictures = com.getPictures();
				 pictures.remove(picture);
				 com.setPicture(pictures);
				 entityManager.persist(com);
				 commitTransaction();		

				 beginTransaction();
				 entityManager.remove(picture);
				 commitTransaction();	
			 }


		 }






}
