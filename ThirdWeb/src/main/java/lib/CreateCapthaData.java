package lib;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

/**
 * @author Maximka
 * For the first invoke of full program after creating all required tables fulfill "captcha" 
 * table with this values. This needed for ability to compare inputed text values from the user
 * to text values which is right in database and also to bind name of picture with the text
 *
 */
public class CreateCapthaData {
	private SessionFactory sf = HibernateUtil.getSessionFactory();
	private static final String[] textCaptcha={"ednt6","khXrg","yjfHP","qRDg8","EqGFn","EtRQn"};
	/**
	 * First check row count from the table "captcha" if it's less than six add six records 
	 * into table. It's easy to change, all that needed to put new images in folder "Images",
	 * write right text and change count table to which number of images is exist in folder
	 * "Images"
	 */
	public void createData(){
	Transaction trsn=null;
		Session s = sf.openSession();
		trsn=s.beginTransaction();
		long countTable =(long) s.createCriteria(Captcha.class)
				.setProjection(Projections.rowCount()).uniqueResult();
		trsn.commit();
		if(countTable<6){
			trsn=s.beginTransaction();
			for (int i = 0; i < 6; i++) {
				Captcha c = new Captcha();
				c.setPhotoNumber(i+1);
				c.setTextPhoto(textCaptcha[i]);
				s.save(c);
			}
			trsn.commit();
			s.flush();
			s.close();
		}
	}
	

}
