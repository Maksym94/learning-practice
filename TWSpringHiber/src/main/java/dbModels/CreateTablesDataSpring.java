package dbModels;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Maximka
 * For the first invoke of full program after creating all required tables fulfill "captcha" 
 * table with this values, also check if there are two roles are available, if not create 
 * two roles with different authorities. If admin user is not exist, will be created it. 
 * This needed for ability to compare inputed text values from the user to text values which is 
 * right in database and also to bind name of picture with the text. Two roles used for user and 
 * admin, which have different functions on the server.
 *
 */
public class CreateTablesDataSpring implements CreateTablesData{
	
	private static final String[] textCaptcha={"ednt6","khXrg","yjfHP","qRDg8","EqGFn","EtRQn"};
	private static final String USERNAME_ADMIN="admin";
	// admin default password is 1111
	private static final String PASSWORD_ADMIN="$2a$10$G1adD.VdCMo8SAjoFp.ck.J6sHG0u1MArVA/h9ctwBnC5BIEIRdhq";
	private static final String ROLE_ADMIN="ROLE_ADMIN";
	private static final String ROLE_USER="ROLE_USER";
	
	private HibernateTemplate template;
	public CreateTablesDataSpring(HibernateTemplate template) {
	this.template = template;
	}
	/**
	 * First check row count from the table "captcha" if it's less than six, add six records 
	 * into table. It's easy to change, all that needed to put new images in folder "Images",
	 * write right text and change count table to which number of images is exist in folder
	 * "Images". In additions check count of "role" table, if there less than two roles, actually 
	 * means that no roles are exist in a table create it. Also check count of "user" table if no 
	 * user is exist create admin user. It's mandatory to have "admin" user, because there is no 
	 * ability to create admin outside
	 */
	@Transactional
	@Override
	public void createData() {
		long countCaptchaTable =(long) template.findByCriteria(DetachedCriteria
				.forClass(Captcha.class).setProjection(Projections.rowCount())).get(0);
		if(countCaptchaTable<6){
			for (int i = 0; i < 6; i++) {
				Captcha c = new Captcha();
				c.setPhotoNumber(i+1);
				c.setTextPhoto(textCaptcha[i]);
				template.save(c);
			}
		}
		long countRoleTable =(long) template.findByCriteria(DetachedCriteria
				.forClass(Role.class).setProjection(Projections.rowCount())).get(0);
		if(countRoleTable<2){
			Role userRole = new Role();
			Role adminRole = new Role();
			userRole.setRoleName(ROLE_USER);
			adminRole.setRoleName(ROLE_ADMIN);
			template.save(userRole);
			template.save(adminRole);
		}
		long countUserTable =(long) template.findByCriteria(DetachedCriteria
				.forClass(UserAccount.class).setProjection(Projections.rowCount())).get(0);
		if(countUserTable<1){
			Set<Role> roles = new HashSet<>();
			Role r = (Role) template.findByCriteria(DetachedCriteria
					.forClass(Role.class).add(Restrictions.eq("roleName", "ROLE_ADMIN"))).get(0);
			roles.add(r);
			UserAccount admin = new UserAccount();
			admin.setLogin(USERNAME_ADMIN);
			admin.setPassword(PASSWORD_ADMIN);
			admin.setRoles(roles);
			template.save(admin);
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
		CreateTablesDataSpring other = (CreateTablesDataSpring) obj;
		if (template == null) {
			if (other.template != null)
				return false;
		} else if (!template.equals(other.template))
			return false;
		return true;
	}

}
