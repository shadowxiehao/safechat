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
//	String other = "对方发来";
//	String myslef = "己方发出";

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
        des = new DES(Value.password);//设置加密解密 密钥
        if (ip.substring(0, 4).equals("127.")) {//如果ip为本地地址 没有服务进程就启动Server进程端,否则启动Client端
            try {
                startServerThread();
            } catch (Exception e) {
                startClientThread();
            }
        } else {
            startClientThread();
        }
    }

    //开始Client进程
    public void startClientThread() throws UnknownHostException, IOException {
        socket = new Socket(ip, port);
        startThread();
    }

    //开始Server进程
    public void startServerThread() throws IOException {
        serverSocket = new ServerSocket(port);
        socket = serverSocket.accept();//等待连接
        startThread();
    }

    //启动线程
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
        String see = new String();//看的更清楚

        PrintWriter out = new PrintWriter(new BufferedWriter(// 获取对输出流（发送数据）的控制
                new OutputStreamWriter(socket.getOutputStream())), true);
        String send_message = sendmessage.getText();
        String messageString = Value.name + "!@#$~" + send_message;//用户名+识别标识符+内容(标识符设置为!@#$~)
        String s_send = des.encrypt(messageString);//encrypt加密
        String s_local = des.encrypt(send_message);

        //保存记录用
        Value.message += Value.name + "  " + getDate() + "    " + s_local + "\n";

        //调试时直观输出加密
        System.out.println(s_send);

        //发送添加了用户名和标识符的数据
        out.println(s_send);

        //输出内容至聊天面板
        messageString = Value.name + "    " + getDate() + "\n" + send_message;
        for (int i = 0; i < s_local.length(); i++) {
            see += "--";
        }//根据长度增加划分线长度
        messagerecord.append(messageString + "\n" + see + "\n");
        sendmessage.setText("");
    }

    public void receiveData() throws Exception {
        String name = new String();//记录名字
        String see = new String();//看的更清楚

        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String str = in.readLine();
        System.out.println(str);

        String str_decrypt = des.decrypt(str);//解码

        int first = str_decrypt.indexOf("!@#$~"); //标志符号第一次出现的位置
        name = str_decrypt.substring(0, first);
        str_decrypt = str_decrypt.substring(first + 5);

        //保存记录用
        String str_redecrypt = des.encrypt(str_decrypt);
        Value.message += name + "  " + getDate() + "    " + str_redecrypt + "\n";
        System.err.println(str);

        //输出内容至聊天面板
        str_decrypt = name + "    " + getDate() + "\n" + str_decrypt;

        for (int i = 0; i < str_decrypt.length(); i++) {
            see += "--";
        }//根据长度增加划分线长度

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
