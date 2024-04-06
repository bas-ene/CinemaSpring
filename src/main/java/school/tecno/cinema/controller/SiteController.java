package school.tecno.cinema.controller;

import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private CinemaService cinemaServ;

	@PostMapping("/login")
	public String login(HttpSession session, Model model, @RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password) {

		User user = cinemaServ.loginUser(email, password);
		if (user != null) {
			session.setAttribute("user", user);
			model.addAttribute("genres", cinemaServ.getGenres());
			return "home";
		} else {
			return "index";
		}
	}

	@PostMapping("/register")
	public String register(HttpSession session, Model model, @RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password) {
		User registeredUser = cinemaServ.registerUser(email, password);
		if (registeredUser != null) {
			session.setAttribute("id", cinemaServ.userExists(email, password));
			return "home";
		} else {
			return "register";
		}
	}

	@DeleteMapping("/film")
	public String deleteFilm(HttpSession session, Model model, @RequestParam(name = "id") Integer id) {
		User user = (User) session.getAttribute("user");
		if (user != null && user.isAdmin) {
			cinemaServ.deleteFilm(id);
		}
		model.addAttribute("genres", cinemaServ.getGenres());
		return "home";
	}

}
