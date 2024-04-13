
package school.tecno.cinema.services;

import school.tecno.cinema.Session;

import java.util.HashMap;
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

	private HashMap<UUID, Session> sessions = new HashMap<UUID, Session>();
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

	private Session buildNewSession() {
		UUID sessionUUID = UUID.randomUUID();
		Session s = new Session(sessionUUID);
		return s;
	}

	public Session sessionStart() {
		Cookie sessionCookie = this.getCookie(SESSION_COOKIE_NAME);
		if (sessionCookie == null) {
			Session s = this.buildNewSession();
			sessions.put(s.getSessionUUID(), s);
			this.setCookie(SESSION_COOKIE_NAME, s.getSessionUUID().toString());
			return s;
		} else {
			UUID sessionUUID = UUID.fromString(sessionCookie.getValue());
			Session s = this.sessions.get(sessionUUID);
			if (s == null) {
				s = this.buildNewSession();
				sessions.put(s.getSessionUUID(), s);
				this.setCookie(SESSION_COOKIE_NAME, s.getSessionUUID().toString());
			}
			return s;
		}
	}

	public void sessionDestroy() {
		Cookie sessionCookie = this.getCookie(SESSION_COOKIE_NAME);
		if (sessionCookie != null) {
			UUID sessionUUID = UUID.fromString(sessionCookie.getValue());
			this.sessions.remove(sessionUUID);
			this.deleteCookie(SESSION_COOKIE_NAME);
		}
	}
}
