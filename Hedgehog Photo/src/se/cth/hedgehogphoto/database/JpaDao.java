package se.cth.hedgehogphoto.database;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;


	public abstract class JpaDao<E,K> implements Dao<E,K>,Entity {
	
		protected Class entityClass;
		
			
		
	//	protected EntityManager entityManager;
	
		public JpaDao() {
			ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
			this.entityClass = (Class) genericSuperclass.getActualTypeArguments()[0];
		
			/*Album album = new Album();
			beginTransaction();
			album.setAlbumName("Lallanarnsstggdfgadrxsfsllgkaafsglsdgggssra");
			album.setDate("d");
			/*Comment com = new Comment();
			com.setComment("majsalsfssrlsgsssrnsdataaalra");
			List<Album> albums = new ArrayList<Album>();
			albums.add(album);
			com.setAlbums(albums);
			album.setComment(com);
			 entityManager.persist(com);*/
			/* entityManager.persist(album);
			 commitTransaction();
			*/
			

		}
		public void beginTransaction() { entityManager.getTransaction().begin(); }
		
		public void commitTransaction() { entityManager.getTransaction().commit(); }
		
		public void persist(E entity) { entityManager.persist(entity); }

		public void remove(E entity) { entityManager.remove(entity); }

		public E findById(K id) { 
		
			return (E) entityManager.find(entityClass, id); }
		
		public List<E> findByEntity(Object entity,String ent) { 
		 
			System.out.println("Simple name" + entityClass.getSimpleName());
			//System.out.println("Simple name2" + entity.getSimpleName());
			System.out.println("Entity" + entity);
			String c = entity.getClass().getSimpleName().toLowerCase();
			String s = "select t from " +entityClass.getSimpleName() + " t where t." + c + "=:" + c;
			System.out.println("Query: " + s);
			   Object something = entity;
			    String theType = ent;
			    Class<?> theClass = null;
				try {
					theClass = Class.forName(theType);
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    Object obj = theClass.cast(something);
			Query q = entityManager.createQuery(s);
			q.setParameter(c,obj);
			System.out.println(q.getResultList());
			return  (List<E>)q.getResultList();
	}
		public List<E> findByString(String quality,String search) {
			System.out.print("Query " + "select t from " + entityClass.getSimpleName()  + " t where t." + quality + "=:" + quality);
			System.out.println(quality + search);
			Query q = entityManager.createQuery("select t from " + entityClass.getSimpleName()  + " t where t." + quality.toLowerCase() + "=:" + quality.toLowerCase());
		
			q.setParameter(quality.toLowerCase(),search.toLowerCase());
			System.out.println(entityClass.getSimpleName());
		
			return  (List<E>)q.getResultList();
		
	}
		public List<E> findByLike(String quality,String search) {
			System.out.print("select t from " + entityClass.getSimpleName()  + " t where t." + quality +  " like '%" + search + "%'");
			//System.out.println(quality + search);
			Query q = entityManager.createQuery("select t from " + entityClass.getSimpleName()  + " t where t." + quality.toLowerCase() +  " like '%" + search.toLowerCase() + "%'");
		
		
			return  (List<E>)q.getResultList();
		
	}
		public List<E> getAll(){
			Query q = entityManager.createQuery("select t from " + entityClass.getSimpleName() + " t");
			return (List<E>)q.getResultList();
		}

}