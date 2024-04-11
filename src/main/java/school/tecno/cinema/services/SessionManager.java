
package school.tecno.cinema.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import school.tecno.cinema.User;
import school.tecno.cinema.database.DatabaseConn;

/**
 * SessionManager
 */
@Service
public class SessionManager {

	@Autowired
	private DatabaseConn db;

	private static final String SESSION_COOKIE_NAME = "session";

	private HttpServletRequest getRequest() {
		HttpServletRequest r = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return r;
	}

	private HttpServletResponse getResponse() {
		HttpServletResponse r = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		return r;
	}

	private Cookie getCookie(String cookieName) {
		HttpServletRequest r = getRequest();
		Cookie[] cookies = r.getCookies();
		if (cookies != null) {
			for (Cookie c : cookies) {
				if (c.getName().equals(cookieName)) {
					return c;
				}
			}
		}
		return null;
	}

	private void setCookie(String cookieName, String cookieValue) {
		HttpServletResponse r = getResponse();
		r.addCookie(new Cookie(cookieName, cookieValue));
	}

	private void deleteCookie(String cookieName) {
		HttpServletResponse r = getResponse();
		Cookie c = new Cookie(cookieName, null);
		c.setMaxAge(0);
		r.addCookie(c);
	}

	public void createNewSession(User user) throws Exception {
		UUID sessionUUID = db.createNewSession(user.id);
		if (sessionUUID == null) {
			throw new Exception("Could not create session");
		}
		this.setCookie(SESSION_COOKIE_NAME, sessionUUID.toString());
	}

	public User getUserFromSession() {
		Cookie sessionCookie = this.getCookie(SESSION_COOKIE_NAME);
		if (sessionCookie == null) {
			return null;
		}

		String sessionUUID = sessionCookie.getValue();

		User user = this.db.getUserFromSession(sessionUUID);
		return user;
	}
}
