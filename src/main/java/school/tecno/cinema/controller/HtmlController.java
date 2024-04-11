package school.tecno.cinema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import school.tecno.cinema.Film;
import school.tecno.cinema.User;
import school.tecno.cinema.services.CinemaService;
import school.tecno.cinema.services.SessionManager;

/**
 * HtmlController
 */
@Controller
public class HtmlController {

	@Autowired
	private CinemaService cinemaServ;
	@Autowired
	private SessionManager sessionManager;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String loginHTML(Model model, HttpServletRequest r) {
		if (sessionManager.getUserFromSession() != null) {
			model.addAttribute("genres", cinemaServ.getGenres());
			return "home";
		}
		return "login";
	}

	@GetMapping("/register")
	public String registerHTML(Model model, HttpServletRequest r) {
		if (sessionManager.getUserFromSession() != null) {
			model.addAttribute("genres", cinemaServ.getGenres());
			return "home";
		}
		return "register";
	}

	@GetMapping("/films")
	public String films(Model model,
			@RequestParam(name = "genreSelector", required = false) String genre, HttpServletRequest r) {
		List<Film> films;

		User user = sessionManager.getUserFromSession();

		// send user to login page if not logged in
		if (user == null) {
			return "login";
		}

		if (genre != null && !genre.isEmpty()) {
			films = cinemaServ.getFilmsByGenre(genre);
		} else {
			films = cinemaServ.getFilms(20);

		}
		model.addAttribute("films", films);
		model.addAttribute("isAdmin", user.isAdmin);
		return "films";
	}

	@GetMapping("/film")
	public String film(Model model, @RequestParam(name = "id") Integer id) {
		Film film = cinemaServ.getFilm(id);
		if (film == null) {
			return "index";
		}
		model.addAttribute("film", film);
		return "film";
	}

}
