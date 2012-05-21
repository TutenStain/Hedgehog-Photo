package se.cth.hedgehogphoto.database;






import java.awt.Point;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import se.cth.hedgehogphoto.geocoding.model.URLCreator;
import se.cth.hedgehogphoto.geocoding.model.XMLParser;
import se.cth.hedgehogphoto.objects.FileObject;
import se.cth.hedgehogphoto.objects.LocationObjectOther;

public class JpaPictureDao extends JpaDao<Picture, String> implements PictureDao {
	private static JpaAlbumDao albumDao = new JpaAlbumDao();
	private static JpaCommentDao commentDao = new JpaCommentDao();
	private static JpaLocationDao locationDao = new JpaLocationDao();
	private static JpaTagDao tagDao = new JpaTagDao();
	private static JpaPictureDao pictureDao = new JpaPictureDao();
	private static Files files = Files.getInstance();
	private static List<AlbumObject> albumList = files.getAlbumList();
	private static List<PictureObject> pictureList = files.getPictureList();

	public void updateSearchfromComments(String search){
		pictureList.addAll(searchfromComments(search));
		files.setPictureList(pictureList);
	}

	@Override
	public  List<? extends PictureI> searchfromComments(String search){
		if(!(search.equals(""))){
			List<Comment> commments = commentDao.findByLike("comment", search);
			if(commments != null){
				List<Picture> pictures = new ArrayList<Picture>();
				for(CommentObject c: commments){
					pictures.addAll(findByEntity(c,"se.cth.hedgehogphoto.database.Comment"));
				}
				return pictures;
			}
		}
		return null;
	}

	public void updateSearchfromNames(String search){
		pictureList.addAll(searchfromNames(search));
		files.setPictureList(pictureList);
	}

	@Override
	public  List<? extends PictureI> searchfromNames(String search){
		if(!(search.equals(""))){
			return findByLike("name",search);
		}else{
			return null;
		}
	}

	public void updateSearchPicturesfromDates(String search){
		pictureList.addAll(searchfromDates(search));
		files.setPictureList(pictureList);
	}

	@Override
	public List<? extends PictureI> searchfromDates(String search){
		if(!(search.equals(""))){
			return findByLike("date",search);
		}else{
			return null;
		}
	}

	public  void updateSearchfromsLocations(String search){
		pictureList.addAll(searchfromLocations(search));
		files.setPictureList(pictureList);
	}

	public  void updateSearchfromTags(String search){
		pictureList.addAll(searchfromTags(search));
		files.setPictureList(pictureList);
	}

	@Override
	public List<? extends PictureI> searchfromTags(String search){
		if(!(search.equals(""))){
			List<Tag> tags = tagDao.findByLike("tag", search);
			List<? extends PictureI> pictures = new ArrayList<Picture>();
			if(!(tags.isEmpty())){
				for(TagI t: tags){
					try{
						for(TagI tag: tagDao.getAll()){
							if(tag.getTag().equals(t.getTag())){
								pictures =  tag.getPictures();
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

	@Override
	public List<? extends PictureI> searchfromLocations(String search){
		if(!(search.equals(""))){
			List<Picture> 	pictures = new ArrayList<Picture>();
			List<Location> locations = locationDao.findByLike("location", search);
			if(!(locations.isEmpty())){
				for(LocationObject l:locations){
					pictures.addAll(findByEntity(l, "se.cth.hedgehogphoto.database.Location"));
				}
			}
			return pictures;
		}else{
			return null;
		}
	}

	@Override
	public  PictureObject getfromPath(String search){
		return findById(search);
	}

	public  void updateAddTag(String tag, String filePath){
		for(PictureObject f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addTag(tag, filePath);
		files.setPictureList(pictureList);
	}

	@SuppressWarnings("unchecked")
	public void addTag(String tag, String filePath){
		Picture picture = findById(filePath);
		List<TagI> tags = new ArrayList<TagI>();
		try{
			tags = (List<TagI>) picture.getTags();
		}catch(Exception y){

		}
		if( picture != null ){
			Tag tagg = tagDao.findById(tag);
			if(tagg != null){
				List<PictureI> pics = new ArrayList<PictureI>();
				try{
					pics = (List<PictureI>) tagg.getPictures();
				}catch(Exception i){

				}
				if(!(pics.contains(picture))){
					beginTransaction();
					pics.add(picture);
					tagg.setPictures(pics);
					if(tags == null)
						tags = new ArrayList<TagI>();
					tags.add(tagg);
					picture.setTags(tags);
					tagDao.persist(tagg);
					commitTransaction();	
				}
			}else{
				beginTransaction();
				TagI newTag = new Tag();
				newTag.setTag(tag);
				List<PictureI> pictures = new ArrayList<PictureI>();
				pictures.add(picture);
				newTag.setPictures(pictures);
				List<TagI> taggs = new ArrayList<TagI>();
				try{
					taggs = (List<TagI>) picture.getTags();
				}catch(Exception u){

				}
				if(taggs == null)
					taggs = new ArrayList<TagI>();
				taggs.add(newTag);
				picture.setTags(tags);
				persist(picture);
				entityManager.persist(newTag);
				commitTransaction();	
			}
		}
	}

	public  void updateaddComment(String comment, String filePath){
		for(PictureObject f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addComment(comment, filePath);
		files.setPictureList(pictureList);
	}

	public  void addComment(String comment, String filePath){

		Picture picture = findById(filePath);
		if(picture != null){
			System.out.print("1");	
			Comment comm = commentDao.findById(comment);
			if(comm!=null){
				System.out.print("2");	
				beginTransaction();
				List<Picture> pics;
				try{
					pics = comm.getPictures();
				}catch(Exception j){
					pics =new ArrayList<Picture>();
				}
				System.out.print("3");	
				if((!pics.contains(picture)))
					pics.add(picture);
				comm.setPicture(pics);
				picture.setComment(comm);
				commitTransaction();
			}else{
				beginTransaction();
				System.out.print("5");	
				Comment com = new Comment();
				com.setComment(comment);
				List<Picture> pictures = new ArrayList<Picture>();
				pictures.add(picture);
				System.out.print("6");	
				com.setPicture(pictures);
				picture.setComment(com);
				System.out.print("7");	
				entityManager.persist(com);
				commitTransaction();
			}
		}
	}

	public  void updateAddLocation(String location, String filePath){
		for(PictureObject f:pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		addLocation(location, filePath);
		files.setPictureList(pictureList);
	}

	public void addLocation(String location, String filePath){
		Picture picture = findById(filePath);
		if(picture != null) {
			LocationI loc = (Location)locationDao.findById(location);
			if(loc != null){
				@SuppressWarnings("unchecked")
				List<PictureI> pics = (List<PictureI>) loc.getPictures();
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
				URL url = URLCreator.getInstance().queryGeocodingURL(location);
				List<LocationObjectOther> lo = XMLParser.getInstance().processGeocodingSearch(url);
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
		pictureList.addAll(getAllPictures());
		files.setPictureList(pictureList);
	}

	public List<Picture> getAllPictures(){
		return getAll();
	}

	public  void deleteAll(){
		List<Picture> allPictures = getAll();
		for(PictureObject pic:allPictures){
			deletePicture(pic.getPath());
		}
	}

	public void updateDeleteTags(String filePath){
		deleteTags(filePath);
		for(PictureObject p: pictureList){
			if(p.getPath().equals(filePath))
				pictureList.remove(p);
		}
		files.setPictureList(pictureList);
	}

	public void deleteTags(String filePath){
		PictureI picture = findById(filePath);

		if(picture != null){

			try{
				List<? extends TagI> taggs = picture.getTags();
				List<Tag> tags = new ArrayList<Tag>();
				for(TagI t: picture.getTags())
					tags.add((Tag) t);

				beginTransaction();
				for(TagI tag: tags){
					try{
						taggs.remove(tag);

						picture.setTags(taggs);
						List<? extends PictureI> pics =tag.getPictures();
						System.out.print("Ex");
						pics.remove(picture);
						tag.setPictures(pics);
						if(pics.isEmpty()==true)
							tagDao.remove((Tag)tag);
						else
							tagDao.persist((Tag)tag);
						this.persist((Picture) picture);
					}catch(Exception y){
						System.out.print("Exception");
					}


				}

			}catch(Exception y){	
			}
			commitTransaction();

		}
	}

	public  void updateDeleteComment(String filePath){
		deleteComment(filePath);
		for(PictureObject f: pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
		}
		files.setPictureList(pictureList);
	}

	public void deleteComment(String filePath){
		PictureObject picture = findById(filePath);
		if(picture != null){
			CommentI com = picture.getComment();
			beginTransaction();
			List<? extends PictureI> pictures = new ArrayList<Picture>();
			try{
				pictures = com.getPictures();
				pictures.remove(picture);
			}catch(Exception e){
			}
			if(pictures.isEmpty() && com!=null){
				entityManager.remove(com);
			}else if(com != null){
				com.setPicture(pictures);
			}
			commitTransaction();	

		}
	}
	public void deleteLocation(String filePath){
		PictureObject picture = findById(filePath);
		if(picture != null){
			try{
				beginTransaction();
				LocationI location = picture.getLocation();
				List<? extends PictureI>  picts = location.getPictures();
				picts.remove(picture);
				if(picts.isEmpty()){
					entityManager.remove(location);
				}else
					location.setPictures(picts);
			}catch(Exception e){
			}
			commitTransaction();	

		}
	}
	public void updateDeletePicturefromAlbum(String filePath){
		albumDao.deletePicture(filePath);
		String albumName = "";
		for(PictureObject f: pictureList){
			if(f.getPath().equals(filePath))
				pictureList.remove(f);
			albumName = f.getAlbum().getAlbumName();
		}
		files.setPictureList(pictureList);
		if(albumName.equals("")){
			for(AlbumObject f:albumList){
				if(f.getAlbumName().equals(albumName))
					albumList.remove(f);
			}
			files.setAlbumList(albumList);
		}
	}
	public void deletePicturefromAlbum(String filePath){
		Picture picture = findById(filePath);
		if(picture != null){
			Album album = picture.getAlbum();
			if(album!=null){
				beginTransaction();
				List<? extends PictureI> pics = album.getPictures();
				pics.remove(picture);
				album.setPictures(pics);
				if(album.getPictures().isEmpty()){
					entityManager.remove(album);
				}
				entityManager.persist(album);
				commitTransaction();	
			}
		}
	}
	public void updateDeletePicture(String filePath){
		deletePicture(filePath);
		pictureList.addAll(getAllPictures());
		files.setPictureList(pictureList);
	}

	public void deletePicture (String  filePath){
		Picture picture = findById(filePath);
		if(picture.getPath().equals(filePath)){
			List<? extends TagI> tags = picture.getTags();
			for(TagI tag: tags){
				beginTransaction();
				List<? extends PictureI> pics =tag.getPictures();
				pics.remove(picture);
				tag.setPictures(pics);
				if(pics.isEmpty()==true)
					entityManager.remove(tag);
				commitTransaction();
			}

			Album album = picture.getAlbum();
			if(album!=null){
				beginTransaction();
				List<? extends PictureI> pics = album.getPictures();
				pics.remove(picture);
				album.setPictures(pics);
				if(album.getPictures().isEmpty()){
					entityManager.remove(album);
				}
				commitTransaction();
			}

			try{
				beginTransaction();
				LocationI location = picture.getLocation();
				List<? extends PictureI>  picts = location.getPictures();
				picts.remove(picture);
				if(picts.isEmpty()){
					entityManager.remove(location);
				}else
					location.setPictures(picts);
			}catch(Exception e){
			}
			commitTransaction();

			Comment com = picture.getComment();
			beginTransaction();
			List<Picture> pictures = new ArrayList<Picture>();
			try{
				pictures = com.getPictures();
				pictures.remove(picture);
			}catch(Exception e){
			}
			if(pictures.isEmpty() && com!=null){
				entityManager.remove(com);
				commitTransaction();	
			}else if(com != null){
				com.setPicture(pictures);
				commitTransaction();
			}
			beginTransaction();
			entityManager.remove(picture);
			commitTransaction();
		}
	}
	public  void insertPicture(FileObject f){
		if(findById(f.getFilePath())==null){
			Album album = new Album();
			if(f.getFilePath() != null || (!(f.getFilePath().equals("")))){
				String albumName ="";
				try{
				albumName= f.getAlbumName().toLowerCase();
				String s =f.getAlbumName().charAt(0) + "";
				albumName  = s.toUpperCase() + albumName.substring(1);
				}catch(Exception u){
					
				}
				if(!(albumName.equals(""))){
					
						album = albumDao.findById(albumName);
						if(album != null){
						beginTransaction();
						try{
							
							if(album.getCoverPath().equals("")|| album.getCoverPath()==null)
								album.setCoverPath(f.getFilePath());
						}catch(Exception o){		
						}
						commitTransaction();
					}else{
						beginTransaction();
						album = new Album();	
						album.setAlbumName(albumName);
						album.setCoverPath(f.getFilePath());
						entityManager.persist(album);
						commitTransaction();
					}
				}
			}
			Picture picture = new Picture();
			String date  = "";
			String fileName = "";
			try{
		 date = f.getDate().toLowerCase();
			String s = f.getDate().charAt(0) + "";
			date  = s.toUpperCase() + date.substring(1);
			}catch(Exception u){
				
			}
			try{
			 fileName = f.getFileName().toLowerCase();
			String s = f.getFileName().charAt(0) + "";
			fileName = s.toUpperCase() + fileName.substring(1);
	}catch(Exception u){
				
			}
				
				picture = findById(f.getFilePath());
				if(picture != null){
				beginTransaction();
				if(!(f.getDate().equals("")))
					picture.setDate(date);
				if(picture.getName().equals(""))
					picture.setName(fileName);
				List<PictureI> thePictures = new ArrayList<PictureI>();
				thePictures.addAll(album.getPictures());
				if(!(thePictures.contains(picture)))
					thePictures.add(picture);
				album.setPictures(thePictures);
				commitTransaction();
			}else{
				if(entityManager.getTransaction().isActive())
					entityManager.getTransaction().commit();
				beginTransaction();
				picture = new Picture();
				picture.setPath(f.getFilePath());	
				picture.setDate(date);
				if(f.getFileName() != null ||(!f.getFileName().equals(""))){
					picture.setName(fileName);
					picture.setAlbum(album);
					List<PictureI> thePictures = new ArrayList<PictureI>();
					try{
						thePictures.addAll(album.getPictures());
					}catch(Exception p){

					}
					thePictures.add(picture);

					if(album != null)
						album.setPictures(thePictures);
					entityManager.persist(picture);
					commitTransaction();

				}
			}	
			setTags(f, picture);
			setComment(f, picture);
			setLocation(f, picture);
		}
	}

	public void setComment(FileObject f,Picture picture){
		if(picture != null){
			try{
				if(!(f.getComment().equals(""))){
					Comment comment = commentDao.findById(f.getComment());
					if(comment.getComment().equals(f.getComment())){
						beginTransaction();
						List<Picture> pics = comment.getPictures();
						pics.add(picture);
						comment.setPicture(pics);
						picture.setComment(comment);							
						commitTransaction();
					}

				}
			}

			catch(Exception k){
	
				if(commentDao.findById(f.getComment())==null){
					beginTransaction();
					Comment comment = new Comment();
					String comm ="";
					try{
						 comm = f.getComment().toLowerCase();
							String s = f.getComment().charAt(0) + "";
							comm  = s.toUpperCase() + comm.substring(1);
							}catch(Exception u){
								
							}
					comment.setComment(comm);		
					List<Picture> pics = new ArrayList<Picture>();
					pics.add(picture);
					comment.setPicture(pics);
					picture.setComment(comment);
					entityManager.persist(comment);
					commitTransaction();
				}
			}
		}
	}

	public void setTags(FileObject f,Picture picture){
		if(picture != null){
			if(f.getTags() != null){
				List<String> tags = new ArrayList<String>();
				for(String tagg: f.getTags()){
					String tgg ="";
					try{
						 tgg = tagg.toLowerCase();
							String s = f.getDate().charAt(0) + "";
							tgg = s.toUpperCase() + tagg.substring(1);
							}catch(Exception u){
								
							}
					tags.add(tgg);
				}
				List<String> pictags = new ArrayList<String>();
				for(TagObject tagg: picture.getTags()){
					pictags.add(tagg.getTag());
				}

				for(int i = 0; i <tags.size();i++){	
					if(!(pictags.contains(tags.get(i)) && tags.get(i).equals(""))){
						TagI tag= tagDao.findById(tags.get(i));
						try{
							if((tag!=null)){
								beginTransaction();
								List<PictureI> ptag= (List<PictureI>) tag.getPictures();				
								if(!(ptag.contains(picture))){
									ptag.add(picture);
									tag.setPictures(ptag);	
									List<TagI> pTags = (List<TagI>) picture.getTags();
									if(!(pTags.contains(tag))){
										pTags.add(tag);

										picture.setTags(pTags);
									}
								}
							}
							commitTransaction();
						}catch(Exception e){
							beginTransaction();
							tag = new Tag();
							tag.setTag(tags.get(i));			
							List<Picture> peg = new ArrayList<Picture>();
							peg.add(picture);
							tag.setPictures(peg);
							List<TagI> pTags = (List<TagI>) picture.getTags();
							if(pTags==null)
								pTags = new ArrayList<TagI>();
							pTags.add(tag);		
							picture.setTags(pTags);
							entityManager.persist(tag);
							commitTransaction();
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
					Location location = locationDao.findById(f.getLocation());
					if(location.getLocation().equals(f.getLocation())){
						beginTransaction();
						location.setLatitude((f.getLocationObject().getLatitude()));
						location.setLongitude(f.getLocationObject().getLongitude());
						picture.setLocation(location);
						commitTransaction();
					}

				}catch(Exception j){
					beginTransaction();
					Location location = new Location();

					location.setLatitude((f.getLocationObject().getLatitude()));
					location.setLongitude(f.getLocationObject().getLongitude());
					location.setLocation(f.getLocation());
					List<Picture> pics = new ArrayList<Picture>();
					pics.add(picture);
					location.setPictures(pics);
					picture.setLocation(location);
					entityManager.persist(location);
					commitTransaction();

				}
			}else if(f.getLocationObject().getLatitude()!=100 && f.getLocationObject().getLongitude() != 200 ){
				//queryReverseGeocodingURL(Point.Double coords) URL creator
				Point.Double p= new Point.Double();
				p.setLocation(f.getLocationObject().getLatitude(), f.getLocationObject().getLongitude());
				URL url =URLCreator.getInstance().queryReverseGeocodingURL(p);
				LocationObjectOther lo = XMLParser.getInstance().processReverseGeocodingSearch(url);
				beginTransaction();
				Location location = new Location();
				location.setLatitude((f.getLocationObject().getLatitude()));
				location.setLongitude(f.getLocationObject().getLongitude());
				location.setLocation(lo.getLocation());
				List<Picture> pics = new ArrayList<Picture>();
				pics.add(picture);
				location.setPictures(pics);
				picture.setLocation(location);
				entityManager.persist(location);
				commitTransaction();
			}
		}
	}

	public void setName(String name, String path){
		Picture p = findById(path);
		if(p != null){
			beginTransaction();
			p.setName(name);
			commitTransaction();
		}
	}
}
