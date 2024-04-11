package school.tecno.cinema.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import school.tecno.cinema.User;
import school.tecno.cinema.services.CinemaService;
import school.tecno.cinema.services.SessionManager;

@Controller
public class SiteController {

	@Autowired
	private CinemaService cinemaServ;

	@Autowired
	private SessionManager sessionManager;

	@PostMapping("/login")
	public String login(Model model, @RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password, HttpServletResponse response) {

		User user = cinemaServ.loginUser(email, password);
		if (user != null) {
			try {
				sessionManager.createNewSession(user);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return "errorPage";
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			model.addAttribute("genres", cinemaServ.getGenres());
			return "home";
		} else {
			return "index";
		}
	}

	@PostMapping("/register")
	public String register(Model model, @RequestParam(name = "email") String email,
			@RequestParam(name = "password") String password, HttpServletResponse response) {
		User registeredUser = cinemaServ.registerUser(email, password);
		if (registeredUser != null) {
			try {
				sessionManager.createNewSession(registeredUser);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					return "errorPage";
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			return "home";
		} else {
			return "register";
		}
	}

	@DeleteMapping("/film")
	public String deleteFilm(Model model, @RequestParam(name = "id") Integer id, HttpServletRequest request) {
		User user = sessionManager.getUserFromSession();
		if (user != null && user.isAdmin) {
			cinemaServ.deleteFilm(id);
		}
		model.addAttribute("genres", cinemaServ.getGenres());
		return "home";
	}

}
