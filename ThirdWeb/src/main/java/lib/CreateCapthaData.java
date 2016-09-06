package lib;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;

public class CreateCapthaData {
	private SessionFactory sf = HibernateUtil.getSessionFactory();
	private static final String[] textCaptcha={"ednt6","khXrg","yjfHP","qRDg8","EqGFn","EtRQn"};
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
