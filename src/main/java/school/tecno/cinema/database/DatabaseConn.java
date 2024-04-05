package school.tecno.cinema.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import school.tecno.cinema.Film;
import school.tecno.cinema.User;

/**
 * DatabaseConn
 */
public class DatabaseConn {

	private Connection conn;

	public DatabaseConn(String database, String username, String password) throws SQLException {

		this.conn = DriverManager.getConnection(String.format("jdbc:mysql://localhost:3306/%s", database), username,
				password);
	}

	public Integer userExists(String username, String password) {
		Integer id = null;
		String query = "SELECT usr_id FROM users WHERE usr_name = ? AND usr_pw = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, username);
			stmt.setString(2, password);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return id;
	}

	public Boolean userIsAdmin(Integer id) {
		Boolean isAdmin = false;
		String query = "SELECT usr_isAdmin FROM users WHERE usr_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, id);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				isAdmin = rs.getBoolean(1);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return isAdmin;
	}

	public Integer registerUser(String email, String hashString) {
		String query = "INSERT INTO users (usr_name, usr_pw) VALUES (?, ?)";
		Integer id = null;
		try (PreparedStatement stmt = conn.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS)) {
			stmt.setString(1, email);
			stmt.setString(2, hashString);
			stmt.execute();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return id;
	}

	public Film getFilm(Integer id) {
		Film film = null;
		String query = "SELECT * FROM films WHERE flm_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, id);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				film = new Film(rs.getInt("flm_id"), rs.getString("flm_nome"), rs.getString("flm_regista"),
						rs.getString("flm_genere"), rs.getDate("flm_uscita"), rs.getTime("flm_durata"),
						rs.getString("flm_imgPath"));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return film;
	}

	public List<Film> getFilms(int limit) {
		List<Film> films = null;
		String query = "SELECT * FROM films LIMIT ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, limit);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			films = new ArrayList<Film>();
			while (rs.next()) {
				films.add(new Film(rs.getInt("flm_id"), rs.getString("flm_nome"), rs.getString("flm_regista"),
						rs.getString("flm_genere"), rs.getDate("flm_uscita"), rs.getTime("flm_durata"),
						rs.getString("flm_imgPath")));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return films;
	}

	public List<String> getGenres() {
		List<String> genres = null;
		String query = "SELECT DISTINCT flm_genere FROM films";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			genres = new ArrayList<String>();
			while (rs.next()) {
				genres.add(rs.getString(1));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return genres;
	}

	public List<Film> getFilmsByGenre(String genre) {
		List<Film> films = null;
		String query = "SELECT * FROM films WHERE flm_genere = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, genre);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			films = new ArrayList<Film>();
			while (rs.next()) {
				films.add(new Film(rs.getInt("flm_id"), rs.getString("flm_nome"), rs.getString("flm_regista"),
						rs.getString("flm_genere"), rs.getDate("flm_uscita"), rs.getTime("flm_durata"),
						rs.getString("flm_imgPath")));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return films;
	}

	public User getUser(Integer id) {
		User user = null;
		String query = "SELECT * FROM users WHERE usr_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, id);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				user = new User(rs.getString("usr_name"), rs.getInt("usr_id"), rs.getString("usr_pw"),
						rs.getBoolean("usr_isAdmin"));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return user;
	}

	public void deleteFilm(Integer id) {
		String query = "DELETE FROM film WHERE flm_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, id);
			stmt.execute();
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

}
