package school.tecno.cinema.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import school.tecno.cinema.Film;
import school.tecno.cinema.Secrets;
import school.tecno.cinema.User;
import school.tecno.cinema.Utils;
import school.tecno.cinema.database.DatabaseConn;

/**
 * CinemaService
 */
@Service
public class CinemaService {

	private DatabaseConn db;

	private CinemaService() {
		try {
			db = new DatabaseConn(Secrets.DB_NAME, Secrets.DB_USER, Secrets.DB_PASS);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public List<Film> getFilms(int limit) {
		List<Film> films = db.getFilms(limit);
		if (films == null) {
			films = new ArrayList<>();
		}
		return films;
	}

	public Film getFilm(Integer id) {
		return db.getFilm(id);
	}

	public List<Film> getFilmsByGenre(String genre) {
		List<Film> films = db.getFilmsByGenre(genre);
		if (films == null) {
			films = new ArrayList<>();
		}
		return films;
	}

	public List<String> getGenres() {

		List<String> genres = db.getGenres();
		if (genres == null) {
			genres = new ArrayList<>();
		}
		return genres;
	}

	public Integer userExists(String email, String password) {
		return db.userExists(email, Utils.HashString(password));
	}

	public User getUser(Integer id) {
		return db.getUser(id);
	}

	public User loginUser(String email, String password) {
		Integer id = db.userExists(email, Utils.HashString(password));
		if (id != null) {
			return db.getUser(id);
		}
		return null;
	}

	public User registerUser(String email, String password) {
		Integer id = db.registerUser(email, Utils.HashString(password));
		return db.getUser(id);
	}

	public boolean userIsAdmin(Integer id) {
		return db.userIsAdmin(id);
	}

	public void deleteFilm(Integer id) {
		db.deleteFilm(id);
	}
}
