package school.tecno.cinema;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Utils {

	// prendi la stringa, hashala, e formattala in esadecimale (sempre 2 cifre,
	// qundi 32 caratteri per md5)
	public static String HashString(String str) {
		try {
			byte[] hash = MessageDigest.getInstance("MD5").digest(str.getBytes());
			StringBuilder hexString = new StringBuilder(32);
			for (byte b : hash) {
				hexString.append(String.format("%02x", b));
			}
			return hexString.toString();
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
	}
}
