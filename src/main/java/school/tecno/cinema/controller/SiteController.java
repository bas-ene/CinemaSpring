package school.tecno.cinema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import school.tecno.cinema.services.CinemaService;

@Controller
public class SiteController {

	@PostMapping("/login")
	public String login(HttpSession session, Model model, @RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password) {

		Integer id = CinemaService.userExists(email, password);
		System.out.println(id);
		if (id != null) {
			session.setAttribute("id", id);
			return "redirect:/films";
		} else {
			return "index";
		}
	}

	@PostMapping("/register")
	public String register(HttpSession session, Model model, @RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password) {
		boolean registrationSuccessful = CinemaService.registerUser(email, password);
		if (registrationSuccessful) {
			session.setAttribute("id", CinemaService.userExists(email, password));
			return "redirect:/films";
		} else {
			return "register";
		}
	}

}
