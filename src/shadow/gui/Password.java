package shadow.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import java.io.File;

import shadow.value.Value;

public class Password extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel jContentPanel = null;
    private JLabel name_jLabel = null;
    private JTextField name_jTextField = null;
    private JLabel password_jLabel = null;

    private JPasswordField password_jPasswordField = null;
    private JButton ok_jButton = null;
    private JButton exit_jButton = null;
    ChatsGui chatsGui = null;


    /**
     * This is the default constructor
     */
    public Password() {
        super();
        initialize();
        chatsGui = new ChatsGui(this);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent arg0) {
                chatsGui.dispose();
                System.exit(0);
            }
        });
    }


    //set main width and height
    Dimension frameSize = new Dimension(390, 286);

    /**
     * This method initializes this
     *
     * @return void
     */
    private void initialize() {
        this.setSize(frameSize);
        this.setContentPane(getJContentPane());
        this.setTitle("信息设置");
        this.setResizable(false);

        //设置图标图片
        String image_path = null;
        try {
            image_path = Password.class.getResource("/").getPath();//获取图片绝对路径
            image_path = java.net.URLDecoder.decode(image_path, "utf-8");//将获取的被重新编码的路径再解码,解决中文问题
        } catch (Exception e) {
        }
        ImageIcon icon = new ImageIcon(image_path+"shadow/gui/balloon.jpg");
        Image image = icon.getImage();
        this.setIconImage(image);

    }

    /**
     * This method initializes jContentPanel
     *
     * @return javax.swing.JPanel
     */
    private JPanel getJContentPane() {

        if (jContentPanel == null) {

            Font f = new Font("宋体", Font.PLAIN, 14);

            name_jLabel = new JLabel();
            name_jLabel.setBounds(new Rectangle(90, 46, 60, 30));
            name_jLabel.setText("通讯昵称");
            name_jLabel.setFont(f);
            name_jLabel.setForeground(Color.decode("#4169E1"));

            password_jLabel = new JLabel();
            password_jLabel.setText("通讯密钥");
            password_jLabel.setSize(new Dimension(60, 30));
            password_jLabel.setLocation(new Point(90, 108));
            password_jLabel.setFont(f);
            password_jLabel.setForeground(Color.decode("#008B00"));

            jContentPanel = new JPanel();

            jContentPanel.setLayout(null);
            jContentPanel.add(name_jLabel, null);
            jContentPanel.add(getName_jTextField(), null);
            jContentPanel.add(password_jLabel, null);
            jContentPanel.add(getPassword_jPasswordField(), null);
            jContentPanel.add(getOk_jButton(), null);//确认按钮
//            jContentPanel.add(getExit_jButton(), null);//注册按钮暂时不需要
            jContentPanel.setOpaque(false);
        }
        return jContentPanel;
    }

    /**
     * This method initializes name_jTextField
     *
     * @return javax.swing.JTextField
     */
    private JTextField getName_jTextField() {
        if (name_jTextField == null) {
            name_jTextField = new JTextField();
            name_jTextField.setBounds(new Rectangle(175, 46, 116, 30));
        }
        return name_jTextField;
    }

    /**
     * This method initializes password_jPasswordField
     *
     * @return javax.swing.JPasswordField
     */
    private JPasswordField getPassword_jPasswordField() {
        if (password_jPasswordField == null) {
            password_jPasswordField = new JPasswordField();
            password_jPasswordField.setSize(new Dimension(116, 30));
            password_jPasswordField.setLocation(new Point(177, 110));
        }
        return password_jPasswordField;
    }

    /**
     * This method initializes ok_jButton
     *
     * @return javax.swing.JButton
     */
    private JButton getOk_jButton() {
        if (ok_jButton == null) {
            ok_jButton = new JButton();
            ok_jButton.setBounds(new Rectangle(150, 183, 89, 30));
            ok_jButton.setText("确定");
            ok_jButton.addActionListener(new java.awt.event.ActionListener() {
                @SuppressWarnings("deprecation")
                public void actionPerformed(java.awt.event.ActionEvent e) {//点击确定后
                    //这里可以添加连接服务器，看看是否合法
                    Value.name = name_jTextField.getText();
                    Value.password = password_jPasswordField.getText();
                    chatsGui.setVisible(true);
                    Password.this.setVisible(false);
                    password_jPasswordField.setEditable(false);
                    name_jTextField.setEditable(false);
                    name_jTextField.setEnabled(false);
                    password_jPasswordField.setEnabled(false);

                    chatsGui.update_username_hello(true);//更新聊天界面上方欢迎的用户名
                    chatsGui.repaint();
                }
            });
        }
        return ok_jButton;
    }

    public void reset_button(boolean reset) {
        if (reset) {
            password_jPasswordField.setEditable(true);
            name_jTextField.setEditable(true);
            name_jTextField.setEnabled(true);
            password_jPasswordField.setEnabled(true);
        } else {
            password_jPasswordField.setEditable(false);
            name_jTextField.setEditable(false);
            name_jTextField.setEnabled(false);
            password_jPasswordField.setEnabled(false);
        }
    }
    /**
     * This method initializes exit_jButton
     *
     * @return javax.swing.JButton
     */
//    private JButton getExit_jButton() {
//        if (exit_jButton == null) {
//            exit_jButton = new JButton();
//            exit_jButton.setLocation(new Point(90, 184));
//            exit_jButton.setText("注册");
//            exit_jButton.setSize(new Dimension(89, 30));
//            exit_jButton.addActionListener(new java.awt.event.ActionListener() {
//                @SuppressWarnings("deprecation")
//                public void actionPerformed(java.awt.event.ActionEvent e) {
//                    Value.name = name_jTextField.getText();
//                    Value.password = password_jPasswordField.getText();
//                }
//            });
//        }
//        return exit_jButton;
//    }

}  //  @jve:decl-index=0:visual-constraint="218,11"
