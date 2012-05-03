package se.cth.hedgehogphoto.database;

public interface Dao<E,K> {
	public void commitTransaction(); 
	public void beginTransaction();
	   public  void persist(E entity);
	    public  void remove(E entity);
	    public  E findById(K id);
}
