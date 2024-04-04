package school.tecno.cinema.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import school.tecno.cinema.Film;

/**
 * DatabaseConn
 */
public class DatabaseConn {

	private Connection conn;

	public DatabaseConn(String database, String username, String password) throws SQLException {

		this.conn = DriverManager.getConnection(String.format("jdbc:mysql://localhost:3306/%s", database), "root",
				"pw");
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

	// NOTE: potrebbe invece ritornare l'id del utente appena registrato
	public boolean registerUser(String email, String hashString) {
		String query = "INSERT INTO users (usr_name, usr_pw) VALUES (?, ?)";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, email);
			stmt.setString(2, hashString);
			stmt.execute();
			return true;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		}
	}

	public Film getFilm(Integer id) {
		Film film = null;
		String query = "SELECT * FROM film WHERE flm_id = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, id);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			if (rs.next()) {
				film = new Film(rs.getInt("flm_id"), rs.getString("flm_nome"), rs.getString("flm_regista"),
						rs.getString("flm_genere"), rs.getDate("flm_uscita"), rs.getTime("flm_durata"));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}

		return film;
	}

	public List<Film> getFilms(int limit) {
		List<Film> films = null;
		String query = "SELECT * FROM film LIMIT ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setInt(1, limit);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			films = new ArrayList<Film>();
			while (rs.next()) {
				films.add(new Film(rs.getInt("flm_id"), rs.getString("flm_nome"), rs.getString("flm_regista"),
						rs.getString("flm_genere"), rs.getDate("flm_uscita"), rs.getTime("flm_durata")));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return films;
	}

	public List<String> getGenres() {
		List<String> genres = null;
		String query = "SELECT DISTINCT flm_genere FROM film";
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
		String query = "SELECT * FROM film WHERE flm_genere = ?";
		try (PreparedStatement stmt = conn.prepareStatement(query)) {
			stmt.setString(1, genre);
			stmt.execute();
			ResultSet rs = stmt.getResultSet();
			films = new ArrayList<Film>();
			while (rs.next()) {
				films.add(new Film(rs.getInt("flm_id"), rs.getString("flm_nome"), rs.getString("flm_regista"),
						rs.getString("flm_genere"), rs.getDate("flm_uscita"), rs.getTime("flm_durata")));
			}

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		return films;
	}

}
