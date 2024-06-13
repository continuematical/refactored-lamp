import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * 客户端
 */


public class Client {
    // 主函数，新建登录窗口
    public static void main(String[] args) {
        new Login();
    }
}

/**
 *  负责客户端的读和写，以及登录和发送的监听
 */
class ClientUtil extends Thread{
    static Socket mySocket = null;
    static JTextField textInput;
    static JTextArea textShow;
    static JFrame chatViewJFrame;
    static BufferedReader in = null;
    static PrintWriter out = null;
    static String userName;

    // 用于接收从服务端发送来的消息
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));  // 输入流
            while (true) {
                String str = in.readLine();
                textShow.append(str + '\n');
                textShow.setCaretPosition(textShow.getDocument().getLength());
            }
        } catch (Exception e) {}
    }

    /**********************登录监听(内部类)**********************/
    class LoginListen implements ActionListener{
        JTextField textField;
        JPasswordField pwdField;
        JFrame loginJFrame;  // 登录窗口本身

        ClientView clientView = null;

        public void setJTextField(JTextField textField) {
            this.textField = textField;
        }
        public void setJPasswordField(JPasswordField pwdField) {
            this.pwdField = pwdField;
        }
        public void setJFrame(JFrame jFrame) {
            this.loginJFrame = jFrame;
        }
        public void actionPerformed(ActionEvent event) {
            userName = textField.getText();
            String userPwd = String.valueOf(pwdField.getPassword());
            if(userName.length() >= 1 && userPwd.equals("123456")) {
                clientView = new ClientView(userName);
                // 建立和服务器的联系
                try {
                    InetAddress addr = InetAddress.getByName(null);
                    mySocket = new Socket(addr,8081);
                    loginJFrame.setVisible(false);
                    out = new PrintWriter(mySocket.getOutputStream());
                    out.println("用户【" + userName + "】进入聊天室！");
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ClientUtil readAndPrint = new ClientUtil();
                readAndPrint.start();
            }
            else {
                JOptionPane.showMessageDialog(loginJFrame, "账号或密码错误，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    /**
     * 聊天界面监听
     */
    class ChatViewListen implements ActionListener{
        public void setJTextField(JTextField text) {
            textInput = text;
        }
        public void setJTextArea(JTextArea textArea) {
            textShow = textArea;
        }
        public void setChatViewJf(JFrame jFrame) {
            chatViewJFrame = jFrame;
            // 设置关闭聊天界面的监听
            chatViewJFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                    out.println("用户【" + userName + "】离开聊天室！");
                    out.flush();
                    System.exit(0);
                }
            });
        }
        // 监听执行函数
        public void actionPerformed(ActionEvent event) {
            try {
                String str = textInput.getText();
                // 文本框内容为空
                if("".equals(str)) {
                    textInput.grabFocus();
                    // 弹出消息对话框（警告消息）
                    JOptionPane.showMessageDialog(chatViewJFrame, "输入为空，请重新输入！", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                out.println(userName + "说：" + str);  // 输出给服务端
                out.flush();

                textInput.setText("");
                textInput.grabFocus();
				textInput.requestFocus(true);
            } catch (Exception e) {}
        }
    }
}
