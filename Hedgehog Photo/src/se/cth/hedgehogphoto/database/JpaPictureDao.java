package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.objects.FileObject;

public class JpaPictureDao extends JpaDao<Picture, String> implements PictureDao {
	private static JpaAlbumDao jad = new JpaAlbumDao();
	private static JpaCommentDao jcd = new JpaCommentDao();
	private static JpaLocationDao jld = new JpaLocationDao();
	private static JpaTagDao jtd = new JpaTagDao();
	private static JpaPictureDao jpd = new JpaPictureDao();
	private static Files files = Files.getInstance();
	private static List<Album> albumList = files.getAlbumList();
	private static List<Picture> pictureList = files.getPictureList();
	public void updateSearchfromComments(String search){
		pictureList = searchfromComments(search);
		files.setPictureList(pictureList);
	}
	public  List<Picture> searchfromComments(String search){
		if(!(search.equals(""))){
			search = search.toLowerCase();
			List<Comment> commments = (List<Comment>)jcd.findByLike("comment", search);
			if(commments != null){
				List<Picture> pictures = new ArrayList<Picture>();
				for(Comment c: commments){
					pictures.addAll(findByEntity(c,"se.cth.hedgehogphoto.database.Comment"));
				}
				return pictures;
			}
		}
		return null;
	}
	public void updateSearchfromNames(String search){
		pictureList = searchfromNames(search);
		files.setPictureList(pictureList);
	}
	public  List<Picture> searchfromNames(String search){
		if(!(search.equals(""))){
			search = search.toLowerCase();
			return findByLike("name",search);
		}else{
			return null;
		}
	}
	public void updateSearchPicturesfromDates(String search){
		pictureList = searchfromDates(search);
		files.setPictureList(pictureList);
	}
	public List<Picture> searchfromDates(String search){
		if(!(search.equals(""))){
			search = search.toLowerCase();
			return findByLike("date",search);
		}else{
			return null;
		}
	}
	public  void updateSearchfromsLocations(String search){
		pictureList = searchfromLocations(search);
		files.setPictureList(pictureList);
	}
	public  void updateSearchfromTags(String search){
		pictureList =searchfromTags(search);
		files.setPictureList(pictureList);
	}
	public List<Picture> searchfromTags(String search){
		if(!(search.equals(""))){
			search = search.toLowerCase();
			List<Tag> tags = jtd.findByLike("tag", search);
			List<Picture> pictures = new ArrayList<Picture>();
			if(!(tags.isEmpty())){
				for(Tag t: tags){
					try{
						for(Tag tag: jtd.getAll()){
							if(tag.getTag().equals(t.getTag())){
								pictures.addAll(tag.getPictures());
							}
						}
					}catch(Exception e){
					}
				}
				return pictures;
			}
		}
		return null;
	}
	public List<Picture> searchfromLocations(String search){
		if(!(search.equals(""))){
			search = search.toLowerCase();
			List<Picture> 	pictures = new ArrayList<Picture>();
			List<Location> locations = jld.findByLike("location", search);
			if(!(locations.isEmpty())){
				for(Location l:locations){
					pictures.addAll(findByEntity(l, "se.cth.hedgehogphoto.database.Location"));
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
	public  void updateAddTag(String tag, String filePath){
		for(Picture f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addTag(tag, filePath);
		files.setPictureList(pictureList);
	}

	public void addTag(String tag, String filePath){
		tag = tag.toLowerCase();
		filePath = filePath.toLowerCase();
		Picture picture = findById(filePath);
		List<Tag> tags = picture.getTags();
		if( picture != null ){
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
	public  void updateaddComment(String comment, String filePath){
		for(Picture f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addComment(comment, filePath);
		files.setPictureList(pictureList);
	}
	public  void addComment(String comment, String filePath){
		comment = comment.toLowerCase();
		filePath = filePath.toLowerCase();
		Picture picture = findById(filePath);
		if(picture != null ) {
			Comment comm = jcd.findById(comment);
			if(comm!= null){
				beginTransaction();
				List<Picture> pics = comm.getPictures();
				if((!pics.contains(picture))){
					pics.add(picture);
					comm.setPicture(pics);
					entityManager.persist(picture);
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
				entityManager.persist(picture);
				commitTransaction();
			}
		}
	}

	public  void updateAddLocation(String location, String filePath){
		for(Picture f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addLocation(location, filePath);
		files.setPictureList(pictureList);
	}

	public void addLocation(String location, String filePath){
		location = location.toLowerCase();
		filePath = filePath.toLowerCase();
		Picture picture = findById(filePath);
		if(picture != null) {
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
	public void updateAllPictures(){
		pictureList = getAllPictures();
		files.setPictureList(pictureList);


	}
	public List<Picture> getAllPictures(){
		return getAll();

	}
	public  void deleteAll(){
		
		List<Picture> allPictures = getAll();
		for(Picture pic:allPictures){
			deletePicture(pic.getPath());
		}
	}
	public void updateDeleteTags(String filePath){
		deleteTags(filePath);
		for(Picture p: pictureList){
			if(p.getPath().equals(filePath))
				pictureList.remove(p);
		}
		files.setPictureList(pictureList);
	}

	public void deleteTags(String filePath){
		filePath = filePath.toLowerCase();
		Picture picture = findById(filePath);
		if(picture != null){
			List<Tag> tags = picture.getTags();
			for(Tag tag: tags){
				entityManager.getTransaction().begin();
				List<Picture> pics =tag.getPictures();
				pics.remove(picture);
				tag.setPictures(pics);
				if(pics.isEmpty()==true)
					entityManager.remove(tag);
				entityManager.persist(picture);
				entityManager.persist(tag);
				entityManager.getTransaction().commit();	
			}
			/*List<Tag> tags = picture.getTags();
				for(Tag tag: tags){
					beginTransaction();
					List<Picture> pics =tag.getPictures();
					pics.remove(picture);
					tag.setPictures(pics);
					entityManager.persist(tag);
					commitTransaction();
			 */}
	}
	public  void updateDeleteComment(String filePath){
		deleteComment(filePath);
		for(Picture f: pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);

		}
		files.setPictureList(pictureList);
	}
	public void deleteComment(String filePath){
		filePath = filePath.toLowerCase();
		Picture picture = findById(filePath);
		if(picture != null){
			Comment com = picture.getComment();
			entityManager.getTransaction().begin();
			List<Picture> pictures = new ArrayList<Picture>();
			try{
				pictures = com.getPictures();
				pictures.remove(picture);
			}catch(Exception e){
			}
			if(pictures.isEmpty() && com!=null){
				entityManager.remove(com);
				entityManager.persist(com);
			}else if(com != null){
				com.setPicture(pictures);
				entityManager.persist(com);
			}
			entityManager.getTransaction().commit();	
			/*	Comment com = picture.getComment();
				beginTransaction();
				List<Picture> pictures = com.getPictures();
				pictures.remove(picture);
				com.setPicture(pictures);
				entityManager.persist(com);
				commitTransaction();
			 */}
	}
	public void deleteLocation(String filePath){
		filePath = filePath.toLowerCase();
		Picture picture = findById(filePath);
		if(picture != null){
			try{
				Location loc = picture.getLocation();
				entityManager.getTransaction().begin();
				Location location = picture.getLocation();
				List<Picture>  picts = location.getPictures();
				picts.remove(picture);
				if(picts.isEmpty()){
					entityManager.remove(location);
					entityManager.persist( location);
				}else
					location.setPictures(picts);
				entityManager.persist( location);
			}catch(Exception e){
			}
			entityManager.getTransaction().commit();	
			/*	Location loc = picture.getLocation();
				beginTransaction();
				List<Picture>  picts = loc.getPictures();
				picts.remove(picture);
				loc.setPictures(picts);
				entityManager.persist(loc);
				commitTransaction();
			 */}
	}
	public void updateDeletePicturefromAlbum(String filePath){
		jad.deletePicture(filePath);
		String albumName = "";
		for(Picture f: pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
			albumName = f.getAlbum().getAlbumName();
		}
		files.setPictureList(pictureList);
		if(albumName.equals("")){
			for(Album f:albumList){
				if(f.getAlbumName().equals(albumName))
					albumList.remove(f);
			}
			files.setAlbumList(albumList);
		}
	}
	public void deletePicturefromAlbum(String filePath){
		filePath = filePath.toLowerCase();
		Picture picture = findById(filePath);
		if(picture != null){
			Album album = picture.getAlbum();
			if(album!=null){
				entityManager.getTransaction().begin();
				List<Picture> pics = album.getPictures();
				pics.remove(picture);
				album.setPictures(pics);
				if(album.getPictures().isEmpty()){
					entityManager.remove(album);
				}
				entityManager.persist(album);
			entityManager.getTransaction().commit();	
			}
		}
	}
	public  void updateDeletePicture(String filePath){
		deletePicture(filePath);
		pictureList = getAllPictures();
		files.setPictureList(pictureList);
	}
	public void deletePicture (String  filePath){
		Picture picture = findById(filePath);
		if(picture.getPath().equals(filePath)){
			//this.deleteTags(filePath);
			List<Tag> tags = picture.getTags();
			for(Tag tag: tags){
				entityManager.getTransaction().begin();
				List<Picture> pics =tag.getPictures();
				pics.remove(picture);
				tag.setPictures(pics);
				if(pics.isEmpty()==true)
					entityManager.remove(tag);
				entityManager.persist(tag);
				entityManager.getTransaction().commit();	
			}
			//this.deletePicturefromAlbum(filePath);
			Album album = picture.getAlbum();
			if(album!=null){
				entityManager.getTransaction().begin();
				List<Picture> pics = album.getPictures();
				pics.remove(picture);
				album.setPictures(pics);
				if(album.getPictures().isEmpty()){
					entityManager.remove(album);
				}
				entityManager.persist(album);
			}
			entityManager.getTransaction().commit();	
			//this.deleteLocation(filePath);
			try{
				Location loc = picture.getLocation();
				entityManager.getTransaction().begin();
				Location location = picture.getLocation();
				List<Picture>  picts = location.getPictures();
				picts.remove(picture);
				if(picts.isEmpty()){
					entityManager.remove(location);
					entityManager.persist( location);
				}else
					location.setPictures(picts);
				entityManager.persist( location);
			}catch(Exception e){
			}
			entityManager.getTransaction().commit();	
			//		this.deleteComment(filePath);
			Comment com = picture.getComment();
			entityManager.getTransaction().begin();
			List<Picture> pictures = new ArrayList<Picture>();
			try{
				pictures = com.getPictures();
				pictures.remove(picture);
			}catch(Exception e){
			}
			if(pictures.isEmpty() && com!=null){
				entityManager.remove(com);
				entityManager.persist(com);
			}else if(com != null){
				com.setPicture(pictures);
				entityManager.persist(com);
			}
			entityManager.getTransaction().commit();	
			entityManager.getTransaction().begin();
			entityManager.remove(picture);
			entityManager.getTransaction().commit();
		}
	}
	public  void insertPicture(FileObject f){
		try{
		if(findById(f.getFilePath())==null){
			Album album = new Album();
			if(f.getFilePath() != null || (!(f.getFilePath().equals("")))){
				String albumName= f.getAlbumName().toLowerCase();
				if(!(albumName.equals(""))){
					album = jad.findById(albumName);
					if(album!=null){
						entityManager.getTransaction().begin();	
						if(album.getCoverPath().equals("")|| album.getCoverPath()==null)
							album.setCoverPath(f.getFilePath().toLowerCase());
						entityManager.getTransaction().commit();
					}else {
						entityManager.getTransaction().begin();
						album = new Album();	
						album.setAlbumName(albumName);
						album.setCoverPath(f.getFilePath());
						entityManager.persist(album);	
						entityManager.getTransaction().commit();
					}
				}
				Picture picture = new Picture();
				boolean pictureExist = false;
				picture = findById(f.getFilePath());
				if(picture != null){
					entityManager.getTransaction().begin();
					if(!(f.getDate().equals("")))
						picture.setDate(f.getDate().toLowerCase());
					if(picture.getName().equals(""))
						picture.setName(f.getFileName().toLowerCase());
					List<Picture> thePictures = album.getPictures();
					if(!(thePictures.contains(picture)))
						thePictures.add(picture);
					album.setPictures(thePictures);
					entityManager.getTransaction().commit();

				}else{
					entityManager.getTransaction().begin();
					picture = new Picture();
					picture.setPath(f.getFilePath());	
					picture.setDate(f.getDate().toLowerCase());
					if(f.getFileName() != null ||(!f.getFileName().equals(""))){
						picture.setName(f.getFileName().toLowerCase());
						picture.setAlbum(album);
						List<Picture> thePictures = album.getPictures();
						thePictures.add(picture);
						album.setPictures(thePictures);
						entityManager.persist(picture);
						entityManager.getTransaction().commit();
						pictureExist=true;
					}
				}	
				setTags(f,picture);
				setComment(f,picture);
				setLocation(f,picture);
			}
		}
		}catch(Exception j){
			
		}
	}
	public void setComment(FileObject f,Picture picture){
		if(picture != null){
			try{
				if(!(f.getComment().equals(""))){
					Comment comment = jcd.findById(f.getComment().toLowerCase());
					if(comment.getComment().equals(f.getComment().toLowerCase())){
						entityManager.getTransaction().begin();
						List<Picture> pics = comment.getPictures();
						pics.add(picture);
						comment.setPicture(pics);
						picture.setComment(comment);							
						entityManager.getTransaction().commit();
					}

				}
			}

			catch(Exception k){
				if(jcd.findById(f.getComment().toLowerCase())==null){
					entityManager.getTransaction().begin();
					Comment comment = new Comment();		
					comment.setComment(f.getComment().toLowerCase());		
					List<Picture> pics = new ArrayList<Picture>();
					pics.add(picture);
					comment.setPicture(pics);
					picture.setComment(comment);
					entityManager.persist(comment);
					entityManager.getTransaction().commit();
				}
			}
		}
	}
	public void setTags(FileObject f,Picture picture){
		if(picture != null){
			if(f.getTags() != null){
				List<String> tags = new ArrayList<String>();
				for(String tagg: f.getTags()){
					tags.add(tagg.toLowerCase());
				}
				List<String> pictags = new ArrayList<String>();
				for(Tag tagg: picture.getTags()){
					pictags.add(tagg.getTag());
				}

				for(int i = 0; i <tags.size();i++){	
					if(!(pictags.contains(tags.get(i)) && tags.get(i).equals(""))){
						Tag tag= (Tag) jtd.findById(tags.get(i));
						try{
							if((tag!=null)){
								entityManager.getTransaction().begin();
								List<Picture> ptag= tag.getPictures();						
								if(!(ptag.contains(picture))){
									ptag.add(picture);
									tag.setPictures(ptag);	
									List<Tag> pTags = picture.getTags();
									if(!(pTags.contains(tag))){
										pTags.add(tag);

										picture.setTags(pTags);
									}
								}
							}
							entityManager.getTransaction().commit();
						}catch(Exception e){
							entityManager.getTransaction().begin();

							tag = new Tag();
							tag.setTag(tags.get(i));			
							List<Picture> peg = new ArrayList<Picture>();
							peg.add(picture);
							tag.setPictures(peg);
							List<Tag> pTags = picture.getTags();
							if(pTags==null)
								pTags = new ArrayList<Tag>();
							pTags.add(tag);		
							picture.setTags(pTags);
							entityManager.persist(tag);
							entityManager.getTransaction().commit();
						}
					}
				}

			}
		}
	}
	public void setLocation(FileObject f,Picture picture){
		if(picture != null){
			if(!(f.getLocation().equals(""))){
				try{
					Location location = jld.findById(f.getLocation().toLowerCase());
					if(location.getLocation().equals(f.getLocation().toLowerCase())){
						entityManager.getTransaction().begin();
						location.setLatitude((f.getLocationObject().getLatitude()));
						location.setLongitude(f.getLocationObject().getLongitude());
						picture.setLocation(location);
						entityManager.getTransaction().commit();
					}

				}catch(Exception j){
					entityManager.getTransaction().begin();
					Location location = new Location();
					
					location.setLatitude((f.getLocationObject().getLatitude()));
					location.setLongitude(f.getLocationObject().getLongitude());
					location.setLocation(f.getLocation().toLowerCase());
					List<Picture> pics = new ArrayList<Picture>();
					pics.add(picture);
					location.setPictures(pics);
					picture.setLocation(location);
					entityManager.persist(location);
					entityManager.getTransaction().commit();

				}
			}
		}
	}
}
