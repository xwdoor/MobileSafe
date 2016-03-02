package net.xwdoor.mobilesafe.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

	public static String encode(String password) {
		try {
			MessageDigest digest = MessageDigest.getInstance("MD5");
			byte[] bytes = digest.digest(password.getBytes());

			StringBuffer sb = new StringBuffer();
			for (byte b : bytes) {
				int i = b & 0xff;// 获取低8位内容
				String hexString = Integer.toHexString(i);

				if (hexString.length() == 1) {
					hexString = "0" + hexString;
				}

				sb.append(hexString);
			}

			String md5 = sb.toString();
			return md5;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return null;
	}
}
