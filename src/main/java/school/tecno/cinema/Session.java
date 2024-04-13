package school.tecno.cinema;

import java.util.HashMap;
import java.util.UUID;

/**
 * Session
 */
public class Session {
	private UUID sessionUUID;
	private HashMap<String, Object> data;

	public Session(UUID sessionUUID) {
		this.sessionUUID = sessionUUID;
		this.data = new HashMap<String, Object>();
	}

	public void setAttribute(String key, Object value) {
		data.put(key, value);
	}

	public Object getAttribute(String key) {
		return data.get(key);
	}

	public UUID getSessionUUID() {
		return sessionUUID;
	}
}
