package shadow.chats;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Calendar;
import javax.swing.JTextArea;

import shadow.encryption.DES;
import shadow.value.Value;

public class Chats {

    int port;
    String ip;
    Thread serverThread = null;
    ServerSocket serverSocket = null;
    public Socket socket = null;
    JTextArea sendmessage = null;
    JTextArea messagerecord = null;
//	String other = "�Է�����";
//	String myslef = "��������";

    DES des = null;

//	public Chats(int port,JTextArea sendmessage,JTextArea messagerecord) throws Exception
//	{
//		this.port = port;
//		this.sendmessage = sendmessage;
//		this.messagerecord = messagerecord;
//		encryption = new DES(Value.password);
//		startServerThread();
//	}

    public Chats(String ip, int port, JTextArea sendmessage, JTextArea messagerecord) throws Exception {
        this.ip = ip;
        this.port = port;
        this.sendmessage = sendmessage;
        this.messagerecord = messagerecord;
        des = new DES(Value.password);//���ü��ܽ��� ��Կ
        if (ip.substring(0, 4).equals("127.")) {//���ipΪ���ص�ַ û�з�����̾�����Server���̶�,��������Client��
            try {
                startServerThread();
            } catch (Exception e) {
                startClientThread();
            }
        } else {
            startClientThread();
        }
    }

    //��ʼClient����
    public void startClientThread() throws UnknownHostException, IOException {
        socket = new Socket(ip, port);
        startThread();
    }

    //��ʼServer����
    public void startServerThread() throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();//�ȴ�����
        startThread();
    }

    //�����߳�
    public void startThread() {
        serverThread = new Thread() {
            public void run() {
                while (socket != null) {
                    try {
                        receiveData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        serverThread.start();
    }

    public void send() throws Exception {
        sendData();
    }

    public void sendData() throws Exception {
        String see = new String();//���ĸ����

        PrintWriter out = new PrintWriter(new BufferedWriter(// ��ȡ����������������ݣ��Ŀ���
                new OutputStreamWriter(socket.getOutputStream())), true);
        String send_message = sendmessage.getText();
        String messageString = Value.name + "!@#$~" + send_message;//�û���+ʶ���ʶ��+����(��ʶ������Ϊ!@#$~)
        String s_send = des.encrypt(messageString);//encrypt����
        String s_local = des.encrypt(send_message);

        //�����¼��
        Value.message += Value.name + "  " + getDate() + "    " + s_local + "\n";

        //����ʱֱ���������
        System.out.println(s_send);

        //����������û����ͱ�ʶ��������
        out.println(s_send);

        //����������������
        messageString = Value.name + "    " + getDate() + "\n" + send_message;
        for (int i = 0; i < s_local.length(); i++) {
            see += "--";
        }//���ݳ������ӻ����߳���
        messagerecord.append(messageString + "\n" + see + "\n");
        sendmessage.setText("");
    }

    public void receiveData() throws Exception {
        String name = new String();//��¼����
        String see = new String();//���ĸ����

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String str = in.readLine();
        System.out.println(str);

        String str_decrypt = des.decrypt(str);//����

        int first = str_decrypt.indexOf("!@#$~"); //��־���ŵ�һ�γ��ֵ�λ��
        name = str_decrypt.substring(0, first);
        str_decrypt = str_decrypt.substring(first + 5);

        //�����¼��
        String str_redecrypt = des.encrypt(str_decrypt);
        Value.message += name + "  " + getDate() + "    " + str_redecrypt + "\n";
        System.err.println(str);

        //����������������
        str_decrypt = name + "    " + getDate() + "\n" + str_decrypt;

        for (int i = 0; i < str_decrypt.length(); i++) {
            see += "--";
        }//���ݳ������ӻ����߳���

        messagerecord.append(str_decrypt + "\n" + see + "\n");
    }

    public static String getDate() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        return hour + ":" + minute + ":" + second;
    }

}
