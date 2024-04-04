package school.tecno.cinema.services;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import school.tecno.cinema.Film;
import school.tecno.cinema.Secrets;
import school.tecno.cinema.Utils;
import school.tecno.cinema.database.DatabaseConn;

/**
 * CinemaService
 */
@Service
public class CinemaService {

	private static DatabaseConn db = null;

	private static void init() {
		if (db == null) {
			try {
				db = new DatabaseConn(Secrets.DB_NAME, Secrets.DB_USER, Secrets.DB_PASS);
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
			}
		}
	}

	public static List<Film> getFilms(int limit) {
		CinemaService.init();
		List<Film> films = db.getFilms(limit);
		if (films == null) {
			films = new ArrayList<>();
		}
		return films;
	}

	public static Film getFilm(Integer id) {
		CinemaService.init();

		return db.getFilm(id);
	}

	public static List<Film> getFilmsByGenre(String genre) {
		CinemaService.init();

		List<Film> films = db.getFilmsByGenre(genre);
		if (films == null) {
			films = new ArrayList<>();
		}
		return films;
	}

	public static List<String> getGenres() {
		CinemaService.init();

		List<String> genres = db.getGenres();
		if (genres == null) {
			genres = new ArrayList<>();
		}
		return genres;
	}

	public static Integer userExists(String email, String password) {
		CinemaService.init();

		return db.userExists(email, Utils.HashString(password));
	}

	public static boolean registerUser(String email, String password) {
		CinemaService.init();

		return db.registerUser(email, Utils.HashString(password));
	}

	public static boolean userIsAdmin(Integer id) {
		CinemaService.init();

		return db.userIsAdmin(id);
	}
}
