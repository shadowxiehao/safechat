package shadow.encryption;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class RSA {
	Key key = null;

	static RSA rsa = new RSA();
	static String PUBLIC_KEY_FILE = "PublicKey";
	/** ָ��˽Կ����ļ� */
	static String PRIVATE_KEY_FILE = "PrivateKey";

	public void createKey() throws NoSuchAlgorithmException,
			FileNotFoundException, IOException {
		/** ΪRSA�㷨����һ��KeyPairGenerator���� */
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		/** ����������������Դ��ʼ�����KeyPairGenerator���� */
		keyPairGen.initialize(1024);
		/** �����ܳ׶� */
		KeyPair keyPair = keyPairGen.generateKeyPair();
		/** �õ���Կ */
		Key publicKey = keyPair.getPublic();
		/** �õ�˽Կ */
		Key privateKey = keyPair.getPrivate();
		/** �ö����������ɵ���Կд���ļ� */
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(
				PUBLIC_KEY_FILE));
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(
				PRIVATE_KEY_FILE));
		oos1.writeObject(publicKey);
		oos2.writeObject(privateKey);
		/** ��ջ��棬�ر��ļ������ */
		oos1.close();
		oos2.close();
	}

	public byte[] encrypt(byte[] b) throws Exception {
		ObjectInputStream inputStream = new ObjectInputStream(
				new FileInputStream(PRIVATE_KEY_FILE));
		key = (Key) inputStream.readObject();
		inputStream.close();
		/** �õ�Cipher������ʵ�ֶ�Դ���ݵ�RSA��������ǩ�� */
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		/** ִ�м��ܲ��� */
		byte[] b1 = cipher.doFinal(b);
		return b1;
	}

	public byte[] decrypt(byte[] buf) throws Exception {
		ObjectInputStream inputStream = new ObjectInputStream(
				new FileInputStream(PUBLIC_KEY_FILE));
		key = (Key) inputStream.readObject();
		inputStream.close();
		/** �õ�Cipher���������˽Կǩ�������ݽ���RSA���� */
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		/** ִ�н��ܲ��� */
		byte[] b = cipher.doFinal(buf);
		return b;
	}
}
