package school.tecno.cinema.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import school.tecno.cinema.Film;
import school.tecno.cinema.User;
import school.tecno.cinema.services.CinemaService;

/**
 * HtmlController
 */
@Controller
public class HtmlController {

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String loginHTML(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			model.addAttribute("genres", CinemaService.getGenres());
			return "home";
		}
		return "login";
	}

	@GetMapping("/register")
	public String registerHTML(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			model.addAttribute("genres", CinemaService.getGenres());
			return "home";
		}
		return "register";
	}

	@GetMapping("/films")
	public String films(HttpSession session, Model model,
			@RequestParam(name = "genreSelector", required = false) String genre) {
		List<Film> films;
		if (genre != null && !genre.isEmpty()) {
			films = CinemaService.getFilmsByGenre(genre);
		} else {
			films = CinemaService.getFilms(20);
		}
		User user = (User) session.getAttribute("user");
		model.addAttribute("films", films);
		model.addAttribute("isAdmin", user.isAdmin);
		return "films";
	}

	@GetMapping("/film")
	public String film(HttpSession session, Model model, @RequestParam(name = "id") Integer id) {
		Film film = CinemaService.getFilm(id);
		if (film == null) {
			return "index";
		}
		model.addAttribute("film", film);
		return "film";
	}

}
