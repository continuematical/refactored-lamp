import javax.swing.*;

/**
 * 登陆成功后的界面
 */


public class ClientView {
    String userName;  //由客户端登录时设置
    JTextField text;
    JTextArea textArea;
    ClientUtil.ChatViewListen listener;

    // 构造函数
    public ClientView(String userName) {
        this.userName = userName ;
        init();
    }
    // 初始化函数
    void init() {
        JFrame jf = new JFrame("客户端");
        jf.setBounds(500,200,400,330);  //设置坐标和大小
        jf.setResizable(false);

        JPanel jp = new JPanel();
        JLabel lable = new JLabel("用户：" + userName);
        textArea = new JTextArea("***************登录成功，欢迎来到多人聊天室！****************\n",12, 35);
        textArea.setEditable(false);
        JScrollPane scroll = new JScrollPane(textArea);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        jp.add(lable);
        jp.add(scroll);

        text = new JTextField(20);
        JButton button = new JButton("  发送  ");
        jp.add(text);
        jp.add(button);

        // 设置“发送”监听
        listener = new ClientUtil().new ChatViewListen();
        listener.setJTextField(text);
        listener.setJTextArea(textArea);
        listener.setChatViewJf(jf);
        text.addActionListener(listener);
        button.addActionListener(listener);

        jf.add(jp);
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setVisible(true);
    }
}
