package shadow.encryption;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    //��������ú���
	public String md5(String inputText) {
		return encrypt(inputText);
	}

	//����MD5����
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

	// ����ʮ�������ַ���
	private static String hex(byte[] arr) {
		StringBuffer st = new StringBuffer();
		for (int i = 0; i < arr.length; ++i) {
			st.append(Integer.toHexString((arr[i] & 0xFF) | 0x100).substring(1,3));
		}
		return st.toString();
	}
}
