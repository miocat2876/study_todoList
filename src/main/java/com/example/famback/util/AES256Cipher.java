package com.example.famback.util;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class AES256Cipher {

	private final static String BLOCK_TYPE = "AES/CBC/PKCS5Padding";
	private final static String ENCODING = "UTF-8";
	private final static String key = "linlinlinlinlinlin";
	private final static String ivKey = "miocatmiocatmiocat";

	@SuppressWarnings("static-access")
	public static String AesEncode(String str) throws Exception {
		Cipher c = Cipher.getInstance(BLOCK_TYPE);
		c.init(c.ENCRYPT_MODE, createKey(), createIvKey());
		byte[] encodeStr = c.doFinal(str.getBytes(ENCODING));
		return new String(new Base64().encodeBase64(encodeStr));
	}

	@SuppressWarnings("static-access")
	public static String AesDecode(String str) throws Exception {
		Cipher c = Cipher.getInstance(BLOCK_TYPE);
		c.init(c.DECRYPT_MODE, createKey(), createIvKey());
		byte[] decodeStr = Base64.decodeBase64(str.getBytes());
		return new String(c.doFinal(decodeStr),ENCODING);
	}

	private static IvParameterSpec createIvKey() throws Exception {
		return new IvParameterSpec(getKeyByte(AES256Cipher.ivKey));
	}

	private static SecretKeySpec createKey() throws Exception {
		return new SecretKeySpec(getKeyByte(AES256Cipher.key), "AES");
	}

	private static byte[] getKeyByte(String key) throws Exception {
		if(key.length() < 16) {
			throw new Exception("key length is not 16");
		}
		byte[] keyBytes = new byte[16];
		byte[] paramKey = key.getBytes(ENCODING);
		System.arraycopy(paramKey, 0, keyBytes, 0, Math.min(paramKey.length, keyBytes.length));
		return keyBytes;
	}
}
