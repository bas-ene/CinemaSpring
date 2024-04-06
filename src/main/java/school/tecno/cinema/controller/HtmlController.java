package school.tecno.cinema.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private CinemaService cinemaServ;

	@GetMapping("/")
	public String index() {
		return "index";
	}

	@GetMapping("/login")
	public String loginHTML(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			model.addAttribute("genres", cinemaServ.getGenres());
			return "home";
		}
		return "login";
	}

	@GetMapping("/register")
	public String registerHTML(HttpSession session, Model model) {
		if (session.getAttribute("user") != null) {
			model.addAttribute("genres", cinemaServ.getGenres());
			return "home";
		}
		return "register";
	}

	@GetMapping("/films")
	public String films(HttpSession session, Model model,
			@RequestParam(name = "genreSelector", required = false) String genre) {
		List<Film> films;
		if (genre != null && !genre.isEmpty()) {
			films = cinemaServ.getFilmsByGenre(genre);
		} else {
			films = cinemaServ.getFilms(20);
		}
		User user = (User) session.getAttribute("user");
		model.addAttribute("films", films);
		model.addAttribute("isAdmin", user.isAdmin);
		return "films";
	}

	@GetMapping("/film")
	public String film(HttpSession session, Model model, @RequestParam(name = "id") Integer id) {
		Film film = cinemaServ.getFilm(id);
		if (film == null) {
			return "index";
		}
		model.addAttribute("film", film);
		return "film";
	}

}
