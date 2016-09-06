package lib;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

public class LogInClassSQL {
	private SessionFactory sf = HibernateUtil.getSessionFactory();
	//private Connection conn = ConnectionToMYSQL.getInstance().getConnection();
	private String currLogin;

	public boolean checkLoginPassword(String login, String password) {
		Transaction trns =null;
		Session s = sf.getCurrentSession();
		trns= s.beginTransaction();
		UserAccount user = (UserAccount) s.createCriteria(UserAccount.class)
		.add(Restrictions.like("login", login.trim())).uniqueResult();
		trns.commit();
		if(user!=null){
			currLogin = user.getLogin();
			return user.getPassword().equals(password);
		}
		/*PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("SELECT login,password "
					+ "FROM bank_storage.users" + " WHERE login LIKE ?");
			ps.setString(1, login);
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			if (rs.next()) {
				currLogin = rs.getString("login");
				String truepassword = rs.getString("password");
				return truepassword.equals(password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		return false;
	}

	public String getCurrLogin() {
		return currLogin;
	}

	private boolean isFreeLogin(String login) {
		Transaction trns =null;
		Session s = sf.getCurrentSession();
		trns= s.beginTransaction();
		String loginExist = (String) s.createQuery("select login from UserAccount "
				+ "where login like :login")
				.setParameter("login", login).uniqueResult();
		trns.commit();
		if(loginExist==null){
			return true;
		}
		/*PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("SELECT login "
					+ "FROM bank_storage.users" + " WHERE login LIKE ?");
			ps.setString(1, login);
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			if (!rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
*/
		return false;

	}

	private boolean isCorrectCapcha(int number, String capcha) {
		Transaction trns =null;
		Session s = sf.getCurrentSession();
		trns= s.beginTransaction();
		String trueCapcha = (String) s.createQuery("select textPhoto from Captcha "
				+ "where photoNumber=:number")
				.setParameter("number", number).uniqueResult();
		trns.commit();
		if(trueCapcha!=null){
			return trueCapcha.equals(capcha);
		}
	/*	PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("SELECT text_photo "
					+ "FROM bank_storage.captcha" + " WHERE photo_number=?");
			ps.setInt(1, number);
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			if (rs.next()) {
				String trueCapcha = rs.getString("text_photo");
				return trueCapcha.equals(capcha);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}*/
		return false;
	}

	private boolean theSamePass(String password, String confirmPassword) {
		return password.equals(confirmPassword);
	}

	public boolean addNewUser(String login, String password,
			String confirmPassword, int numberOfPicture, String capcha) {
		if (isFreeLogin(login) && theSamePass(password, confirmPassword)
				&& isCorrectCapcha(numberOfPicture, capcha)) {
		Transaction trns =null;
		Session s = sf.openSession();
		trns= s.beginTransaction();
		UserAccount userAccount = new UserAccount();
		userAccount.setLogin(login);
		userAccount.setPassword(password);
		s.save(userAccount);
		trns.commit();
		//s= sf.getCurrentSession();
		trns= s.beginTransaction();
		Integer count =(Integer) s.createCriteria(CountUser.class)
				.setProjection(Projections.max("count")).uniqueResult();
		trns.commit();
		CountUser cu = new CountUser();
		if(count!=null){
			cu.setCount(++count);
			}
		else{ cu.setCount(1);}
		//s= sf.getCurrentSession();
		trns= s.beginTransaction();
		s.save(cu);
		trns.commit();
		s.close();
		return true;
		}
		/*PreparedStatement ps = null;
		PreparedStatement psGetCount = null;
		PreparedStatement psSetCount = null;

		if (isFreeLogin(login) && theSamePass(password, confirmPassword)
				&& isCorrectCapcha(numberOfPicture, capcha)) {
			try {
				ps = conn.prepareStatement("INSERT INTO bank_storage.users"
						+ "(login,password) " + "VALUES (?,?)");
				ps.setString(1, login);
				ps.setString(2, password);
				ps.executeUpdate();
				psGetCount = conn.prepareStatement("SELECT MAX(count) "
					+ "FROM bank_storage.total_users");
				psGetCount.executeQuery();
				ResultSet rs = psGetCount.getResultSet();
				int count = 0;
				if (rs.next()) {
					count = rs.getInt("MAX(count)");
				}
				psSetCount = conn
						.prepareStatement("INSERT INTO bank_storage.total_users"
								+ " (count) "
								+ "VALUE (?)");
				psSetCount.setInt(1, ++count);
				psSetCount.executeUpdate();

			} catch (SQLException e) {
				e.printStackTrace();
			}
			return true;
		}*/
		return false;
	}

	public int getTotalUsers() {
		Transaction trns =null;
		Session s = sf.getCurrentSession();
		trns= s.beginTransaction();
		Integer total = (Integer) s.createCriteria(CountUser.class)
				.setProjection(Projections.max("count")).uniqueResult();
		trns.commit();
		if(total!=null){
			return total;
		}
		/*PreparedStatement ps = null;
		try {
			ps = conn.prepareStatement("SELECT MAX(count) "
					+ "FROM bank_storage.total_users");
			ps.executeQuery();
			ResultSet rs = ps.getResultSet();
			if (rs.next()) {
				return rs.getInt("MAX(count)");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		return 0;
	}

	/*
	 * l.map.put("Standart", "1111"); l.map.put("Buda", "2222");
	 * l.map.put("Sakura", "3333"); l.map.put("Skillet", "4444");
	 * l.map.put("Senemi", "5555");
	 */
	// "dataBaseOfUsers.cvxl"

	/*
	 * l.storageCapcha = new HashMap<Integer, String>(); l.storageCapcha.put(1,
	 * "ednt6"); l.storageCapcha.put(2, "khXrg"); l.storageCapcha.put(3,
	 * "yjfHP"); l.storageCapcha.put(4, "qRDg8"); l.storageCapcha.put(5,
	 * "EqGFn"); l.storageCapcha.put(6, "EtRQn");
	 */
	/*
	 * public static void main(String[] args) { LogInClass l = new LogInClass();
	 * 
	 * try { ObjectOutputStream oos = new ObjectOutputStream(new
	 * FileOutputStream( new File("D:\\Java-resourses\\Проекты\\" +
	 * "SecondWeb\\dataBaseOfCapcha.cvxl"))); oos.writeObject(l.storageCapcha);
	 * oos.close(); } catch (IOException e) { e.printStackTrace(); }
	 * System.out.println("finish"); }
	 */

}
