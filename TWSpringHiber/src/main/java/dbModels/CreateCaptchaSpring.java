package dbModels;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Maximka
 * For the first invoke of full program after creating all required tables fulfill "captcha" 
 * table with this values. This needed for ability to compare inputed text values from the user
 * to text values which is right in database and also to bind name of picture with the text
 *
 */
public class CreateCaptchaSpring implements CreateCaptcha{
	
	private static final String[] textCaptcha={"ednt6","khXrg","yjfHP","qRDg8","EqGFn","EtRQn"};
	
	private HibernateTemplate template;
	public CreateCaptchaSpring(HibernateTemplate template) {
	this.template = template;
	}
	/**
	 * First check row count from the table "captcha" if it's less than six add six records 
	 * into table. It's easy to change, all that needed to put new images in folder "Images",
	 * write right text and change count table to which number of images is exist in folder
	 * "Images"
	 */
	@Transactional
	@Override
	public void createData() {
		long countTable =(long) template.findByCriteria(DetachedCriteria
				.forClass(Captcha.class).setProjection(Projections.rowCount())).get(0);
		if(countTable<6){
			for (int i = 0; i < 6; i++) {
				Captcha c = new Captcha();
				c.setPhotoNumber(i+1);
				c.setTextPhoto(textCaptcha[i]);
				template.save(c);
			}
		}
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((template == null) ? 0 : template.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CreateCaptchaSpring other = (CreateCaptchaSpring) obj;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		return true;
	}

}
