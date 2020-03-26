package shadow.gui;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.net.InetAddress;

import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import shadow.chats.Chats;

public class GuiAction {
	
	Thread thread = null;
	String INPUTMESSAGE = "请输入消息";
	Chats chats = null;
	JTextArea sendmessage = null;
	JTextArea messagerecord = null;
	public boolean ENDCHATS = false;
	
	public GuiAction(JTextArea sendmessage ,JTextArea messagerecord) {
		this.sendmessage = sendmessage;
		this.messagerecord = messagerecord;
	}
	public boolean server_connect(JTextField ipserver, JTextField portJTextField) throws Exception
	{
		String hostName = "";
		String ip = ipserver.getText();
		String portString= portJTextField.getText();
		int port = Integer.parseInt(portString);
		
		if (ip.equals("")) {	//as server at first
			chats = new Chats("127.0.0.1",port,sendmessage,messagerecord);
		}
		else {					//as client at first
			InetAddress address = InetAddress.getByName(ip);
			if (!address.isReachable(5000)) {
	    		hostName= address.getHostName();
	    		JOptionPane.showMessageDialog(null, "无法与" + hostName + "连接");
	    		return false;//无法连接
			}
			chats = new Chats(ip, port,sendmessage,messagerecord);
		}
		return true;
	}
	
	public void send_message(JTextArea sendMessage,JTextArea messagerecord) throws Exception
	{
		String messageString = sendMessage.getText();
		if (messageString.equals("") || messageString.equals(INPUTMESSAGE + "\n") 
				|| messageString.equals(INPUTMESSAGE)|| messageString.equals("\n")) {
			sendMessage.setText(INPUTMESSAGE);	
		}
		else {
			if (chats.socket != null) {
				chats.send();
			}
			else {
				ENDCHATS = true;
			}
		}
	}
	
	public void save_message(JTextArea messageRecord)
	{
			new Message().setVisible(true);
	}
	
	public void mouse_input_message(JTextArea inputMessage,MouseEvent event)
	{
		if (inputMessage.getText().equals(INPUTMESSAGE)) {
			inputMessage.setText("");
		}
	}
	
	public void send_message_keyinput(JTextArea sendmessage, KeyEvent event,JTextArea messagerecord) throws Exception
	{
		if (event.getKeyCode() == '\n') {
			send_message(sendmessage, messagerecord);
		}
	}
}
