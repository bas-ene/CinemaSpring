package school.tecno.cinema;

/**
 * User
 */
public class User {

	public String username;
	public Integer id;
	public String hashedPassword;
	public boolean isAdmin;

	public User(String username, Integer id, String hashedPassword, boolean isAdmin) {
		this.username = username;
		this.id = id;
		this.hashedPassword = hashedPassword;
		this.isAdmin = isAdmin;
	}
}
