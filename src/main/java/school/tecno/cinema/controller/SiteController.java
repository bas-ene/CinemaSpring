package school.tecno.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import school.tecno.cinema.User;
import school.tecno.cinema.services.CinemaService;

@Controller
public class SiteController {

	@PostMapping("/login")
	public String login(HttpSession session, Model model, @RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password) {

		User user = CinemaService.loginUser(email, password);
		if (user != null) {
			session.setAttribute("user", user);
			model.addAttribute("genres", CinemaService.getGenres());
			return "home";
		} else {
			return "index";
		}
	}

	@PostMapping("/register")
	public String register(HttpSession session, Model model, @RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password) {
		User registeredUser = CinemaService.registerUser(email, password);
		if (registeredUser != null) {
			session.setAttribute("id", CinemaService.userExists(email, password));
			return "home";
		} else {
			return "register";
		}
	}

	@DeleteMapping("/film")
	public String deleteFilm(HttpSession session, Model model, @RequestParam(name = "id") Integer id) {
		User user = (User) session.getAttribute("user");
		if (user != null && user.isAdmin) {
			CinemaService.deleteFilm(id);
		}
		model.addAttribute("genres", CinemaService.getGenres());
		return "home";
	}

}
