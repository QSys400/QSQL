package qutils;

 
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;


public class StringCipher {

	private static final char[] PASSWORD = "enF1ds@bNls0Gdlksd#04".toCharArray();
	private static final byte[] SALT = { (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12, (byte) 0xde, (byte) 0x33,
			(byte) 0x10, (byte) 0x12, };

	public static String encrypt(String property) {
		String output = property;
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
			Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
			pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
			output = new String(Base64.getEncoder().encode(pbeCipher.doFinal(property.getBytes("UTF-8"))));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;

	}

	public static String decrypt(String property) {
		String output = property;
		try {
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWithMD5AndDES");
			SecretKey key = keyFactory.generateSecret(new PBEKeySpec(PASSWORD));
			Cipher pbeCipher = Cipher.getInstance("PBEWithMD5AndDES");
			pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
			output = new String(pbeCipher.doFinal(Base64.getDecoder().decode(property)), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return output;
	}
	
 
}
