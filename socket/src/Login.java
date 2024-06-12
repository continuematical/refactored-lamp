import javax.swing.*;
import java.awt.*;

/**
 * 登陆界面
 */

public class Login {
    JTextField textField = null;
    JPasswordField pwdField = null;
    ClientReadAndPrint.LoginListen listener=null;

    public Login() {
        init();
    }

    void init() {
        JFrame jf = new JFrame("登录");
        jf.setBounds(500, 250, 310, 210);
        jf.setResizable(false);  // 设置是否缩放

        JPanel jp1 = new JPanel();
        JLabel headJLabel = new JLabel("登录界面");
        headJLabel.setFont(new Font(null, 0, 35));
        jp1.add(headJLabel);

        JPanel jp2 = new JPanel();
        JLabel nameJLabel = new JLabel("用户名：");
        textField = new JTextField(20);
        JLabel pwdJLabel = new JLabel("密码：    ");
        pwdField = new JPasswordField(20);
        JButton loginButton = new JButton("登录");
        JButton registerButton = new JButton("注册");
        jp2.add(nameJLabel);
        jp2.add(textField);
        jp2.add(pwdJLabel);
        jp2.add(pwdField);
        jp2.add(loginButton);
        jp2.add(registerButton);

        JPanel jp = new JPanel(new BorderLayout());  // BorderLayout布局
        jp.add(jp1, BorderLayout.NORTH);
        jp.add(jp2, BorderLayout.CENTER);

        // 设置监控
        listener = new ClientReadAndPrint().new LoginListen();
        listener.setJTextField(textField);
        listener.setJPasswordField(pwdField);
        listener.setJFrame(jf);
        pwdField.addActionListener(listener);
        loginButton.addActionListener(listener);

        jf.add(jp);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}

