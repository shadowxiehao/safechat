package shadow.gui;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.Dimension;
import javax.swing.JTextArea;
import java.awt.Rectangle;
import javax.swing.JScrollPane;
import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import shadow.encryption.DES;
import shadow.encryption.MD5;
import shadow.encryption.RSA;
import shadow.value.Value;
import javax.swing.JCheckBox;

public class Message extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel jContentPane = null;
	private JTextArea message_jTextArea = null;
	private JScrollPane message_jScrollPane = null;
	private JButton save_jButton = null;
	private JLabel path_jLabel = null;
	private JTextField path_jTextField = null;
	private JCheckBox md5_rsa_jCheckBox = null;
	private JLabel md_5_rsa_jLabel = null;

	/**
	 * This is the default constructor
	 */
	public Message() {
		super();
		initialize();
		message_jTextArea.setText(Value.message);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(411, 328);
		this.setContentPane(getJContentPane());
		this.setTitle("消息查看器");
		this.setResizable(false);
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			md_5_rsa_jLabel = new JLabel();
			md_5_rsa_jLabel.setBounds(new Rectangle(263, 238, 59, 33));
			md_5_rsa_jLabel.setText("<html>生成摘要<br>并签名</html>");
			path_jLabel = new JLabel();
			path_jLabel.setBounds(new Rectangle(2, 235, 16, 38));
			path_jLabel.setText("<html>路<br>径<html>");
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(getMessage_jTextArea(), null);
			jContentPane.add(getMessage_jScrollPane(), null);
			jContentPane.add(getSave_jButton(), null);
			jContentPane.add(path_jLabel, null);
			jContentPane.add(getPath_jTextField(), null);
			jContentPane.add(getMd5_rsa_jCheckBox(), null);
			jContentPane.add(md_5_rsa_jLabel, null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes message_jTextArea	
	 * 	
	 * @return javax.swing.JTextArea	
	 */
	private JTextArea getMessage_jTextArea() {
		if (message_jTextArea == null) {
			message_jTextArea = new JTextArea();
			message_jTextArea.setLocation(new Point(2, 2));
			message_jTextArea.setSize(new Dimension(385, 216));
		}
		return message_jTextArea;
	}

	/**
	 * This method initializes message_jScrollPane	
	 * 	
	 * @return javax.swing.JScrollPane	
	 */
	private JScrollPane getMessage_jScrollPane() {
		if (message_jScrollPane == null) {
			message_jScrollPane = new JScrollPane(getMessage_jTextArea());
			message_jScrollPane.setBounds(new Rectangle(0, 1, 404, 217));
			message_jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		}
		return message_jScrollPane;
	}

	/**
	 * This method initializes save_jButton	
	 * 	
	 * @return javax.swing.JButton	
	 */
	private JButton getSave_jButton() {
		if (save_jButton == null) {
			save_jButton = new JButton();
			save_jButton.setBounds(new Rectangle(330, 237, 68, 34));
			save_jButton.setText("保存");
			save_jButton.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					try {
						save_message();
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}
			});
		}
		return save_jButton;
	}

	/**
	 * This method initializes path_jTextField	
	 * 	
	 * @return javax.swing.JTextField	
	 */
	private JTextField getPath_jTextField() {
		if (path_jTextField == null) {
			path_jTextField = new JTextField();
			path_jTextField.setBounds(new Rectangle(24, 238, 207, 33));
		}
		return path_jTextField;
	}

	/**
	 * This method initializes md5_rsa_jCheckBox	
	 * 	
	 * @return javax.swing.JCheckBox	
	 */
	private JCheckBox getMd5_rsa_jCheckBox() {
		if (md5_rsa_jCheckBox == null) {
			md5_rsa_jCheckBox = new JCheckBox();
			md5_rsa_jCheckBox.setSize(new Dimension(24, 25));
			md5_rsa_jCheckBox.setLocation(new Point(237, 241));
		}
		return md5_rsa_jCheckBox;
	}
	
	public void save_message() throws Exception
	{
		if (md5_rsa_jCheckBox.isSelected()) {
			MD5 md5 = new MD5();
			String md5String = md5.md5(Value.message);
			System.out.println("MD5 is:" + md5String);
			RSA rsa = new RSA();
			rsa.createKey();
			DES des = new DES();
			String rsaString = des.byteArr2HexStr(rsa.encrypt(md5String.getBytes()));		
			System.out.println("RSA is:" + rsaString);
			String filesString = Value.message + "本次通讯聊天记录消息摘要:\t"
			+ md5String + "\n" + Value.name + "的签名\t" + rsaString;
		//	write_file(Value.message + Value.name + "的签名\t" + rsaString);
			write_file(filesString);
		}
		else {
			write_file(Value.message);
		}
	}
	
	public void write_file(String messages)
	{
		String pathString = path_jTextField.getText();
		if(pathString.length() < 3) {
			JOptionPane.showMessageDialog(null, "请输入正确路径！");
			return ;
		}
		pathString = pathString + "\\message.txt";
		File file = new File(pathString);
		try {
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(messages);
			JOptionPane.showMessageDialog(null, "密文已经保存成功！");
			fileWriter.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}  
