package se.cth.hedgehogphoto.database;

import java.util.ArrayList;
import java.util.List;

public class JpaAlbumDao  extends JpaDao<Album, String> implements AlbumDao{
	private static Files files = Files.getInstance();
	private static List<AlbumObject> albumList = files.getAlbumList();

	public JpaAlbumDao(){
		super();
	}

	@Override
	public void updateAllAlbums(){
		albumList.clear();
		albumList.addAll(getAllAlbums());
		files.setAlbumList(albumList);
	}
	
	@Override
	public List<? extends AlbumI> getAllAlbums(){
		return getAll();
	}
	
	@Override
	public void updateSearchfromDates(String search){
		albumList.clear();
		albumList.addAll(searchfromDates(search));
		files.setAlbumList(albumList);
	}
	
	@Override
	public void updateSearchPicturesfromDates(String search){
		albumList.clear();
		albumList.addAll(searchfromDates(search));
		files.setAlbumList(albumList );
	}
	
	@Override
	public List<? extends AlbumI> searchfromDates(String search){
		if(search.equals("")){
			return findByLike("date",search.toLowerCase());
		}else{
			return null;
		}
	}
	
	@Override
	public void updateSearchfromNames(String search){
		albumList.clear();
		albumList.addAll(searchfromNames(search));
		files.setAlbumList(albumList );
	}
	
	@Override
	public List<? extends AlbumI> searchfromNames(String search){
		if(search.equals("")){
			return findByLike("albumName", search.toLowerCase());
		}else{
			return null;
		}

	}
	
	@Override
	public void updateSearchfromComments(String search){
		albumList.clear();
		albumList.addAll(searchfromComments(search));
		files.setAlbumList(albumList);
	}
	
	@Override
	public List<? extends AlbumI> searchfromComments(String search){
		search = search.toLowerCase();
		if(!(search.equals(""))){
			JpaCommentDao jcd = new JpaCommentDao();
			List<Comment> comments = jcd.findByLike("comment", search);
			if(!(comments.isEmpty())){
				List<Album> albums = new ArrayList<Album>();
				for(CommentObject comment:comments)
					albums.addAll(findByEntity(comment,"se.cth.hedgehogphoto.database.Comment"));
			}
		}
		return null;
	}
	
	@Override
	public void updateSearchfromTags(String search){
		albumList.clear();
		albumList.addAll(searchfromTags(search));
		files.setAlbumList(albumList);
	}
	
	@Override
	public List<? extends AlbumI> searchfromTags(String search){
		search = search.toLowerCase();
		if(!(search.equals(""))){
			JpaTagDao jtd = new JpaTagDao();
			List<Tag> tags = jtd.findByLike("Tag", search);
			List<Album> albums = new ArrayList<Album>();
			for(TagObject t: tags){
				albums.addAll(findByEntity(t,"se.cth.hedgehogphoto.database.Tag"));
			}
			return albums;
		}
		return null;
	}
	
	@Override
	public void updateSearchfromLocations(String search){
		albumList.clear();
		albumList.addAll(searchfromLocations(search));
		files.setAlbumList(albumList);
	}
	
	@Override
	public List<? extends AlbumI> searchfromLocations(String search){
		search = search.toLowerCase();
		if(!(search.equals(""))){
			JpaLocationDao jld = new JpaLocationDao();
			List<Location> locations = jld.findByLike("Location", search);
			List<Album> albums = new ArrayList<Album>();
			for(LocationObject l:locations){
				albums.addAll(findByEntity(l,"dao.database.Location"));
			}
			return albums;
		}
		return null;
	}
	
	@Override
	public void updateAddTag(String tag, String albumName){
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		addTag(tag, albumName);
		files.setAlbumList(albumList);
	}
	
	@Override
	public void addTag(String tag, String albumName){
		Album album = findById(albumName);
		if(album != null) {

			JpaTagDao jtd = new JpaTagDao();
			Tag tagg = jtd.findById(tag);
			if(tagg!= null){
				List<Album> albums = tagg.getAlbums();
				if(!(albums.contains(album))){
					beginTransaction();
					albums.add(album);
					tagg.setAlbums(albums);
					entityManager.persist(tagg);
					commitTransaction();
				}
			}else{
				beginTransaction();
				Tag newTag = new Tag();
				newTag.setTag(tag);
				List<Album> albums = new ArrayList<Album>();
				albums.add(album);
				newTag.setAlbums(albums);
				List<Tag> tags =album.getTags();
				tags.add(newTag);
				album.setTags(tags);
				persist(album);
				entityManager.persist(newTag);
				commitTransaction();
			}

		}

	}
	
	@Override
	public void updateAddComment(String comment, String albumName){
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		addComment(comment, albumName);
		files.setAlbumList(albumList);
	}

	@Override
	public void addComment(String comment, String albumName){
		albumName = albumName.toLowerCase();
		comment = comment.toLowerCase();
		Album album = findById(albumName);
		if(album != null) {
			JpaCommentDao jcd = new JpaCommentDao();
			Comment comm = jcd.findById(comment);
			if(comm != null){
				beginTransaction();
				List<Album> albums = comm.getAlbums();
				if((!albums.contains(album))){
					albums.add(album);
					comm.setAlbums(albums);
					persist(album);
					entityManager.persist(comm);;
					commitTransaction();
				}

			}else{
				beginTransaction();
				Comment com = new Comment();
				com.setComment(comment);
				List<Album> albums = new ArrayList<Album>();
				albums.add(album);
				com.setAlbums(albums);
				album.setComment(com);
				entityManager.persist(com);
				persist(album);
				commitTransaction();
			}
		}
	}
	
	@Override
	public void updateAddLocation(String location, String albumName){
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		addLocation(location, albumName);
		files.setAlbumList(albumList);
	}

	@Override
	public void addLocation(String location, String albumName){
		albumName = albumName.toLowerCase();
		location = location.toLowerCase();
		Album album = findById(albumName);
		if(album != null) {
			JpaLocationDao jld = new JpaLocationDao();
			Location loc = jld.findById(location);
			if(loc != null){
				List<AlbumI> albums = (List<AlbumI>) loc.getAlbums();

				if((!albums.contains(album))){

					beginTransaction();
					albums.add(album);
					loc.setAlbums(albums);
					album .setLocation(loc);
					persist(album);
					entityManager.persist(loc);
					commitTransaction();
				}
			}else{
				beginTransaction();
				loc= new Location();
				loc.setLocation(location);
				List<Album> albums = new ArrayList<Album>();
				albums.add(album);
				loc.setAlbums(albums);
				album .setLocation(loc);
				entityManager.persist(loc);
				persist(album);
				commitTransaction();
			}
		}

	}
	
	@Override
	public void updateDeleteComment(String albumName){
		deleteComment(albumName);
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		files.setAlbumList(albumList);
	}
	
	@Override
	public void deleteComment(String albumName){
		albumName = albumName.toLowerCase();
		AlbumObject album = findById(albumName);
		if(album != null){
			CommentI com = album.getComment();
			beginTransaction();
			List<? extends AlbumI> albums = com.getAlbums();
			albums.remove(album);
			com.setAlbums(albums);
			entityManager.persist(com);
			commitTransaction();
		}
	}
	
	@Override
	public void updateDeleteLocation(String albumName){
		deleteLocation(albumName);
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		files.setAlbumList(albumList);
	}
	@Override
	public void deleteLocation(String albumName){
		AlbumObject album = findById(albumName);
		if(album!= null){
			LocationI loc = album.getLocation();
			beginTransaction();
			List<? extends AlbumI> albums = loc.getAlbums();
			albums.remove(album);
			loc.setAlbums(albums);
			entityManager.persist(loc);
			commitTransaction();
		}
	}

	@Override
	public void updateDeleteTags(String albumName){
		deleteTags(albumName);
		for(AlbumObject f:albumList){
			if(f.getAlbumName().equals(albumName))
				albumList.remove(f);
		}
		files.setAlbumList(albumList);
	}
	
	@Override
	public void deleteTags(String albumName){
		albumName = albumName.toLowerCase();
		AlbumObject album = findById(albumName);
		if(album!= null){
			List<? extends TagI> tags = album.getTags();
			for(TagI tag: tags){
				beginTransaction();
				List<? extends AlbumI> albums =tag.getAlbums();
				albums .remove(album);
				tag.setAlbums(albums);
				entityManager.persist(tag);
				commitTransaction();
			}
		}
	}

	@Override
	public void deletePicture(String filePath){
		JpaPictureDao jpd = new JpaPictureDao();
		Picture picture = jpd.findById(filePath);

		if(picture != null){
			beginTransaction();
			Album album = picture.getAlbum();
			List<? extends PictureI> pics = album.getPictures();
			pics.remove(picture);
			album.setPictures(pics);
			persist(album);
			beginTransaction();	
		}
	}
}
