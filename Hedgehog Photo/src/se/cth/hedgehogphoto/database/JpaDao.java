package se.cth.hedgehogphoto.database;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.logging.Level;
import se.cth.hedgehogphoto.log.*;
import javax.persistence.Query;


public abstract class JpaDao<E,K> implements Dao<E,K>,Entity {

	protected Class<?> entityClass;

	public JpaDao() {
		ParameterizedType genericSuperclass = (ParameterizedType) getClass().getGenericSuperclass();
		this.entityClass = (Class<?>) genericSuperclass.getActualTypeArguments()[0];
	}
	
	public void beginTransaction() { entityManager.getTransaction().begin(); }

	public void commitTransaction() { entityManager.getTransaction().commit(); }

	public void persist(E entity) { entityManager.persist(entity); }

	public void remove(E entity) { entityManager.remove(entity); }

	public E findById(K id) { 
		return (E) entityManager.find(entityClass, id); 
	}

	public List<E> findByEntity(Object entity,String ent) { 
		String c = entity.getClass().getSimpleName().toLowerCase();
		String s = "select t from " + entityClass.getSimpleName() + " t where t." + c + "=:" + c;
		Object something = entity;
		String theType = ent;
		Class<?> theClass = null;
		try {
			theClass = Class.forName(theType);
		} catch (ClassNotFoundException e) {
			Log.getLogger().log(Level.SEVERE, "ClassNotFoundException", e);
			e.printStackTrace();
		}
		Object obj = theClass.cast(something);
		Query q = entityManager.createQuery(s);
		q.setParameter(c,obj);
		return  (List<E>)q.getResultList();
	}

	public List<E> findByString(String quality,String search) {
		Query q = entityManager.createQuery("select t from " + entityClass.getSimpleName()  + " t where t." + quality.toLowerCase() + "=:" + quality.toLowerCase());
		q.setParameter(quality.toLowerCase(),search.toLowerCase());
		return  (List<E>)q.getResultList();
	}


	public List<E> findByLike(String quality,String search) {
		try{
			search = search.toLowerCase();
			String s = search.charAt(0)+"";
			search = s.toUpperCase() + search.substring(1);
		}catch(Exception i){			
		}

		Query q = entityManager.createQuery("select t from " + entityClass.getSimpleName()  + " t where t." + quality +  " like '%" + search + "%'");
		List<E> query = (List<E>)q.getResultList();
		q = entityManager.createQuery("select t from " + entityClass.getSimpleName()  + " t where t." + quality.toLowerCase() +  " like '%" + search.toLowerCase() + "%'");
		query.addAll((List<E>)q.getResultList());
		return query;
	}

	public List<E> getAll(){
		Query q = entityManager.createQuery("select t from " + entityClass.getSimpleName() + " t");
		return (List<E>)q.getResultList();
	}

}