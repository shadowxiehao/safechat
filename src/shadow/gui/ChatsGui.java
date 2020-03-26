package shadow.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

import shadow.value.Value;

public class ChatsGui extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPane = null;
    private JPanel com_jPanel = null;
    private JLabel title_jLabel = null;
    private JLabel server_ip_jLabel = null;
    private JLabel port_jLabel = null;
    private JTextField server_ip_jTextField = null;
    private JButton server_jButton = null;
    private JTextField port_jTextField = null;
    private JPanel message_jPanel = null;
    private JTextArea message_jTextArea = null;
    private JPanel send_jPanel = null;
    private JButton send_jButton = null;
    private JButton clear_message_jButton = null;
    private JTextArea input_message_jTextArea = null;
    private JButton se_1_jButton = null;
    private JButton se_2_jButton = null;
    private JScrollPane input_message_jScrollPane = null;
    private JScrollPane message_jScrollPane = null;

    private GuiAction guiAction = null;

    private int count = 0;//计数


    /**
     * 这是默认构造函数
     */
    public ChatsGui(final Password password) {
        super();
        initialize();
        this.addWindowListener(new WindowAdapter() {
                                   @Override
                                   public void windowClosing(WindowEvent arg0) {
                                       password.setVisible(true);
                                       password.reset_button(true);//设置昵称和密钥可以编辑
                                   }
                               }
        );
        guiAction = new GuiAction(input_message_jTextArea, message_jTextArea);
    }

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(624, 575);
        this.setBackground(Color.decode("#00F5FF"));
        this.setContentPane(getJContentPane());
        this.setTitle("计算机网络课程设计");
        this.setResizable(false);

        //设置图标图片
        String image_path = ChatsGui.class.getResource("/shadow/gui/cute.png").getPath();//获取图片绝对路径
        try {
            image_path = java.net.URLDecoder.decode(image_path, "utf-8");//将获取的被重新编码的路径再解码,解决中文问题
        } catch (Exception e) {
        }
        ImageIcon icon = new ImageIcon(image_path);
        Image image = icon.getImage();
        this.setIconImage(image);
    }

    /**
     * 用来初始化 jContentPane
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {
        if (jContentPane == null) {
            jContentPane = new JPanel();
            jContentPane.setLayout(null);
            jContentPane.add(getCom_jPanel(), null);
            jContentPane.add(getMessage_jPanel(), null);
            jContentPane.add(getSend_jPanel(), null);
        }
        return jContentPane;
    }

    /**
     * This method initializes com_jPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getCom_jPanel() {
        if (com_jPanel == null) {
            port_jLabel = new JLabel();
            port_jLabel.setBounds(new Rectangle(260, 56, 31, 32));
            port_jLabel.setText("端口");
            server_ip_jLabel = new JLabel();
            server_ip_jLabel.setText("服务器IP地址");
            server_ip_jLabel.setSize(new Dimension(81, 33));
            server_ip_jLabel.setLocation(new Point(59, 54));

            JLabel extra_jLabel = new JLabel();
            extra_jLabel.setBounds(new Rectangle(5, 5, 80, 32));
            extra_jLabel.setText("扬大XieHao");
            extra_jLabel.setForeground(Color.decode("#96CDCD"));

            com_jPanel = new JPanel();
            com_jPanel.setLayout(null);
            com_jPanel.setBounds(new Rectangle(-5, -5, 626, 107));
            com_jPanel.setBackground(Color.decode("#F8F8FF"));
            com_jPanel.add(server_ip_jLabel, null);
            com_jPanel.add(getServer_ip_jTextField(), null);
            com_jPanel.add(getServer_jButton(), null);
            com_jPanel.add(getSe_1_jButton(), null);
            com_jPanel.add(port_jLabel, null);
            com_jPanel.add(getPort_jTextField(), null);
            com_jPanel.add(extra_jLabel,null);
        }
        return com_jPanel;
    }

    public boolean update_username_hello(boolean confirm) {

        if (count == 0) {
            if (confirm) {
//            Font f = new Font("宋体", Font.BOLD, 14);

                title_jLabel = new JLabel();
                title_jLabel.setBounds(new Rectangle(190, 12, 300, 31));
//            title_jLabel.setBackground(Color.decode("#00FFFF"));

//            title_jLabel.setFont(f);
                title_jLabel.setText(
                        "<html>" +
                                "<head><style>" +
                                ".one{color:#BA55D3;font-size:18;font-family:宋体;}" +
                                ".two{color:#0099ff;font-size:18;font-family:宋体;}" +
                                "</style></head>" +
                                "<body>" +
                                "<span class='one'>欢迎</span>" +
                                "<span class='two'> " + Value.name + " </span>" +
                                "<span class='one'>来到双人加密聊天室</span>" +
                                "</body>" +
                                "</html>");
//            title_jLabel.setForeground(Color.decode("#FFC125"));

                com_jPanel.add(title_jLabel, null);
                count++;
                com_jPanel.updateUI();//更新
                com_jPanel.repaint();
                return true;
            }
        } else {
            com_jPanel.remove(title_jLabel);
            count = 0;
            com_jPanel.updateUI();//更新
            com_jPanel.repaint();
            update_username_hello(true);
        }
        return false;
    }

    /**
     * This method initializes server_ip_jTextField
     *
     * @return javax.swing.JTextField
     */
    private JTextField getServer_ip_jTextField() {
        if (server_ip_jTextField == null) {
            server_ip_jTextField = new JTextField();
            server_ip_jTextField.setText("127.0.0.1");
            server_ip_jTextField.setLocation(new Point(158, 55));
            server_ip_jTextField.setSize(new Dimension(83, 33));
        }
        return server_ip_jTextField;
    }

    private JTextField getPort_jTextField() {
        if (port_jTextField == null) {
            port_jTextField = new JTextField();
            port_jTextField.setText("8888");
            port_jTextField.setBounds(new Rectangle(305, 57, 58, 31));
        }
        return port_jTextField;
    }

    /**
     * This method initializes server_jButton
     *
     * @return javax.swing.JButton
     */
    private JButton getServer_jButton() {
        if (server_jButton == null) {
            server_jButton = new JButton();
            server_jButton.setText("连接");
            server_jButton.setSize(new Dimension(133, 33));
            server_jButton.setLocation(new Point(395, 55));
            server_jButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    try {
                        if (guiAction.server_connect(server_ip_jTextField, port_jTextField)) {
                            connect_button_enable(false);
                        } else {
                            connect_button_enable(true);
                        }
                    } catch (HeadlessException e1) {
                        e1.printStackTrace();
                    } catch (IOException e2) {
                        e2.printStackTrace();
                    } catch (Exception e3) {
                        e3.printStackTrace();
                    }
                }
            });
        }
        return server_jButton;
    }

    /*
     * 开启/关闭 连接按钮
     * 关闭/开启 发送保存消息按钮与聊天框
     */
    public boolean connect_button_enable(boolean act) {
        if (act) {
            server_jButton.setEnabled(true);
            if (!send_jButton.isEnabled()) {
                send_jButton.setEnabled(false);
                input_message_jTextArea.setEditable(false);
                input_message_jTextArea.setEnabled(false);
            }
            return true;
        } else {
            server_jButton.setEnabled(false);
            if (!send_jButton.isEnabled()) {
                send_jButton.setEnabled(true);
                input_message_jTextArea.setEditable(true);
                input_message_jTextArea.setEnabled(true);
            }
            return false;
        }
    }

    /**
     * This method initializes message_jPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getMessage_jPanel() {
        if (message_jPanel == null) {
            message_jPanel = new JPanel();
            message_jPanel.setLayout(null);
            message_jPanel.setBounds(new Rectangle(-5, 102, 625, 298));
            message_jPanel.setBackground(new Color(186, 244, 217));
            message_jPanel.add(getMessage_jTextArea(), null);
            message_jPanel.add(getSe_2_jButton(), null);
            message_jPanel.add(getMessage_jScrollPane(), null);
        }
        return message_jPanel;
    }

    /**
     * This method initializes message_jTextArea
     *
     * @return javax.swing.JTextArea
     */
    private JTextArea getMessage_jTextArea() {
        if (message_jTextArea == null) {
            message_jTextArea = new JTextArea();
            message_jTextArea.setBounds(new Rectangle(5, -1, 754, 283));
            message_jTextArea.setFont(new Font("Dialog", Font.PLAIN, 14));
            Font f = new Font("黑体", Font.PLAIN, 14);
            message_jTextArea.setFont(f);
            message_jTextArea.setBackground(Color.decode("#D4F9E2"));
            message_jTextArea.setForeground(Color.decode("#660E7A"));
            message_jTextArea.setEditable(false);
            message_jTextArea.setLineWrap(true);
        }
        return message_jTextArea;
    }

    /**
     * This method initializes send_jPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getSend_jPanel() {
        if (send_jPanel == null) {
            send_jPanel = new JPanel();
            send_jPanel.setLayout(null);
            send_jPanel.setBackground(Color.decode("#B9D3EE"));
            send_jPanel.setLocation(new Point(-2, 399));
            send_jPanel.setSize(new Dimension(622, 163));
            send_jPanel.add(getSend_jButton(), null);
            send_jPanel.add(getClear_message_jButton(), null);
            send_jPanel.add(getInput_message_jTextArea(), null);
            send_jPanel.add(getInput_message_jScrollPane(), null);
        }
        return send_jPanel;
    }

    /**
     * This method initializes send_jButton
     *
     * @return javax.swing.JButton
     */
    private JButton getSend_jButton() {
        if (send_jButton == null) {
            send_jButton = new JButton();
            send_jButton.setBounds(new Rectangle(378, 27, 106, 98));
            send_jButton.setText("发送");
            send_jButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    try {
                        guiAction.send_message(input_message_jTextArea, message_jTextArea);
                        if (guiAction.ENDCHATS) {
                            server_jButton.setEnabled(true);
                            input_message_jTextArea.setText("已经结束通讯了");
                            input_message_jTextArea.setEditable(false);
                            send_jButton.setEnabled(false);
                        }
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
            });
        }
        return send_jButton;
    }

    /**
     * This method initializes clear_message_jButton
     *
     * @return javax.swing.JButton
     */
    private JButton getClear_message_jButton() {
        if (clear_message_jButton == null) {
            clear_message_jButton = new JButton();
            clear_message_jButton.setBounds(new Rectangle(498, 28, 114, 97));
            clear_message_jButton.setText("保存聊天记录");
            clear_message_jButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    guiAction.save_message(message_jTextArea);
                }
            });
        }
        return clear_message_jButton;
    }

    /**
     * This method initializes input_message_jTextArea
     *
     * @return javax.swing.JTextArea
     */
    private JTextArea getInput_message_jTextArea() {
        if (input_message_jTextArea == null) {
            input_message_jTextArea = new JTextArea();
            input_message_jTextArea.setLineWrap(true);
            input_message_jTextArea.setBounds(new Rectangle(5, 3, 390, 146));
            input_message_jTextArea.setText("请输入消息");
            input_message_jTextArea.setFont(new Font("Dialog", Font.PLAIN, 14));
            input_message_jTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
                public void keyPressed(java.awt.event.KeyEvent e) {
                    //		guiAction.send_message_keyinput(input_message_jTextArea, e, message_jTextArea);
                }

                public void keyTyped(java.awt.event.KeyEvent e) {
				/*	System.out.println(e.getKeyChar());
					System.out.println(e.getKeyCode());
					System.out.println(e.getKeyLocation());*/
                }
            });
            input_message_jTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    guiAction.mouse_input_message(input_message_jTextArea, e);
                }
            });
        }
        return input_message_jTextArea;
    }

    /**
     * This method initializes se_1_jButton
     *
     * @return javax.swing.JButton
     */
    private JButton getSe_1_jButton() {
        if (se_1_jButton == null) {
            se_1_jButton = new JButton();
            se_1_jButton.setBounds(new Rectangle(2, 98, 781, 8));
            se_1_jButton.setBackground(Color.decode("#9AFF9A"));
            se_1_jButton.setEnabled(false);
        }
        return se_1_jButton;
    }

    /**
     * This method initializes se_2_jButton
     *
     * @return javax.swing.JButton
     */
    private JButton getSe_2_jButton() {
        if (se_2_jButton == null) {
            se_2_jButton = new JButton();
            se_2_jButton.setBounds(new Rectangle(4, 285, 780, 8));
            se_2_jButton.setBackground(Color.decode("#9AFF9A"));
            se_2_jButton.setEnabled(false);

        }
        return se_2_jButton;
    }

    /**
     * This method initializes input_message_jScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getInput_message_jScrollPane() {
        if (input_message_jScrollPane == null) {
            input_message_jScrollPane = new JScrollPane(getInput_message_jTextArea());
            input_message_jScrollPane.setBounds(new Rectangle(1, 0, 354, 149));
            input_message_jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        }
        return input_message_jScrollPane;
    }

    /**
     * This method initializes message_jScrollPane
     *
     * @return javax.swing.JScrollPane
     */
    private JScrollPane getMessage_jScrollPane() {
        if (message_jScrollPane == null) {
            message_jScrollPane = new JScrollPane(getMessage_jTextArea());
            message_jScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            message_jScrollPane.setSize(new Dimension(621, 284));
            message_jScrollPane.setLocation(new Point(4, -3));
        }
        return message_jScrollPane;
    }

}  //  @jve:decl-index=0:visual-constraint="10,10" 
