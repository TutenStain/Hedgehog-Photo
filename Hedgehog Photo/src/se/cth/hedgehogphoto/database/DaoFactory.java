package se.cth.hedgehogphoto.database;

/**
 * 
 * @author Julia Gustafsson
 */
public class DaoFactory {

	private JpaAlbumDao jad;
	private JpaCommentDao jcd;
	private JpaLocationDao jld;
	private JpaTagDao jtd;
	private JpaPictureDao jpd;
	private static DaoFactory daoFactory;
	
	public static DaoFactory getInstance(){
		if(daoFactory == null)
			daoFactory = new DaoFactory();
		return daoFactory;
	}

	private DaoFactory(){
		jad = new JpaAlbumDao();
		jcd = new JpaCommentDao();
		jld = new JpaLocationDao();
		jtd = new JpaTagDao();
		jpd = new JpaPictureDao();

	}
	
	public JpaAlbumDao getJpaAlbumDao() {
		return jad;
	}

	public void setJpaAlbumDao(JpaAlbumDao jad) {
		this.jad = jad;
	}

	public JpaCommentDao getJpaCommentDao () {
		return jcd;
	}

	public void setJpaCommentDao (JpaCommentDao jcd) {
		this.jcd = jcd;
	}

	public JpaLocationDao getJpaLocationDao() {
		return jld;
	}

	public void setJpaLocationDao(JpaLocationDao jld) {
		this.jld = jld;
	}

	public JpaTagDao getJpaTagDao() {
		return jtd;
	}

	public void setJpaTagDao(JpaTagDao jtd) {
		this.jtd = jtd;
	}

	public JpaPictureDao getJpaPictureDao() {
		return jpd;
	}

	public void setJpaPictureDao(JpaPictureDao jpd) {
		this.jpd = jpd;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}
}
