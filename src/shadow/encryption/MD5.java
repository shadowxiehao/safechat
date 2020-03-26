package shadow.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    //主构造调用函数
	public String md5(String inputText) {
		return encrypt(inputText);
	}

	//进行MD5加密
	private  String encrypt(String inputText) {
		try {
			MessageDigest m = MessageDigest.getInstance("md5");
			m.update(inputText.getBytes("UTF8"));
			byte s[] = m.digest();
			return hex(s);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 返回十六进制字符串
	private static String hex(byte[] arr) {
		StringBuffer st = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			st.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,3));
		}
		return st.toString();
	}
}
