import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


/**
 * 服务器界面
 */

public class ServerView {
    private JFrame frame;
    private JTextArea textArea;

    /**
     * 系统日志
     */
    private JScrollPane sysMsgScrollPane;
    private JTextPane sysMsgArea;
    private JScrollBar sysMsgScrollBar;

    /**
     * 世界聊天
     */


    /**
     * 用户管理
     */
    private JLabel userLabel;
    private JButton kick_button;
    private JList<String> userList;
    private DefaultListModel<String> users_model;

    public ServerView() {
        init();
    }

    //文本区域添加信息
    void setTextArea(String string) {
        textArea.append(string + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    //界面初始化
    void init() {
        frame = new JFrame("服务器端");
        frame.setBounds(100, 100, 450, 500);
        frame.setResizable(false);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("==欢迎来到多人聊天系统（服务器端）==");
        textArea = new JTextArea(23, 38);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        panel.add(label);
        panel.add(scrollPane);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.add(panel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
