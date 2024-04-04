package school.tecno.cinema.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import school.tecno.cinema.Film;
import school.tecno.cinema.services.CinemaService;

/**
 * HtmlController
 */
@Controller
public class HtmlController {

	@GetMapping("/")
	public String index(HttpSession session, Model model) {
		if (session.getAttribute("id") != null) {
			return films(session, model, null);
		} else {
			return "index";
		}
	}

	@GetMapping("/login")
	public String loginHTML(HttpSession session, Model model) {
		if (session.getAttribute("id") != null) {
			return films(session, model, null);
		}
		return "login";
	}

	@GetMapping("/register")
	public String registerHTML(HttpSession session, Model model) {
		if (session.getAttribute("id") != null) {
			return films(session, model, null);
		}
		return "register";
	}

	@GetMapping("/films")
	public String films(HttpSession session, Model model,
			@RequestParam(name = "genreSelector", required = false) String genre) {
		List<String> genres = CinemaService.getGenres();
		List<Film> films;
		if (genre != null) {
			films = CinemaService.getFilmsByGenre(genre);
		} else {
			films = CinemaService.getFilms(20);
		}
		model.addAttribute("films", films);
		model.addAttribute("genres", genres);
		return "home";
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
