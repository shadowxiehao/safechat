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
	/** 指定私钥存放文件 */
	static String PRIVATE_KEY_FILE = "PrivateKey";

	public void createKey() throws NoSuchAlgorithmException,
			FileNotFoundException, IOException {
		/** 为RSA算法创建一个KeyPairGenerator对象 */
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
		/** 利用上面的随机数据源初始化这个KeyPairGenerator对象 */
		keyPairGen.initialize(1024);
		/** 生成密匙对 */
		KeyPair keyPair = keyPairGen.generateKeyPair();
		/** 得到公钥 */
		Key publicKey = keyPair.getPublic();
		/** 得到私钥 */
		Key privateKey = keyPair.getPrivate();
		/** 用对象流将生成的密钥写入文件 */
		ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream(
				PUBLIC_KEY_FILE));
		ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream(
				PRIVATE_KEY_FILE));
		oos1.writeObject(publicKey);
		oos2.writeObject(privateKey);
		/** 清空缓存，关闭文件输出流 */
		oos1.close();
		oos2.close();
	}

	public byte[] encrypt(byte[] b) throws Exception {
		ObjectInputStream inputStream = new ObjectInputStream(
				new FileInputStream(PRIVATE_KEY_FILE));
		key = (Key) inputStream.readObject();
		inputStream.close();
		/** 得到Cipher对象来实现对源数据的RSA进行数字签名 */
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		/** 执行加密操作 */
		byte[] b1 = cipher.doFinal(b);
		return b1;
	}

	public byte[] decrypt(byte[] buf) throws Exception {
		ObjectInputStream inputStream = new ObjectInputStream(
				new FileInputStream(PUBLIC_KEY_FILE));
		key = (Key) inputStream.readObject();
		inputStream.close();
		/** 得到Cipher对象对已用私钥签名的数据进行RSA解密 */
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, key);
		/** 执行解密操作 */
		byte[] b = cipher.doFinal(buf);
		return b;
	}
}
