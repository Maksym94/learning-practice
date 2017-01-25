package dbModels;

public interface LogInClass {
	public boolean checkLoginPassword(String login, String password);
	public boolean addNewUser(String login, String password,
			String confirmPassword, int numberOfPicture, String capcha);
	public int getTotalUsers();
	public boolean isFreeLogin(String login);
	public boolean isCorrectCapcha(int number, String capcha);

}
