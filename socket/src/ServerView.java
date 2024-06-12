import javax.swing.*;
import java.awt.*;
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
    private JScrollPane userTextScrollPane;
    private JTextPane userMsgArea;
    private JScrollBar userVertical;

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

    /**
     * 底部发送按钮
     */
    private JTextField text_field;
    private JTextField sysText_field;
    private JButton foot_send;
    private JButton foot_sysSend;
    private JButton foot_userClear;

    /**
     * 整体布局
     */
    JPanel leftPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JLabel headPanel = new JLabel();
    JLabel rootPanel = new JLabel();


    //文本区域添加信息
    void setTextArea(String string) {
        textArea.append(string + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * 左边布局初始化
     */
    void initLeft(){
        JLabel sysMsgLabel = new JLabel("系统日志");
        sysMsgArea = new JTextPane();
        sysMsgArea.setEditable(false);
        sysMsgScrollPane = new JScrollPane();
        sysMsgScrollPane.setViewportView(sysMsgArea);
        sysMsgScrollBar = new JScrollBar();
        sysMsgScrollBar.setAutoscrolls(true);
        sysMsgScrollPane.setVerticalScrollBar(sysMsgScrollBar);
        leftPanel.add(sysMsgLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        leftPanel.add(sysMsgScrollPane, new GridBagConstraints(0, 1, 1, 1, 100, 100,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * 右边布局初始化
     */
    void initRight(){

    }

    /**
     * 底部布局初始化
     */
    void initRoot(){

    }

    /**
     * 顶部布局初始化
     */
    void initHead(){

    }

    //界面初始化
    void init() {
        frame = new JFrame("服务器端");
        frame.setBounds(100, 100, 450, 500);
        frame.setResizable(false);

        initLeft();
        initRight();
        initHead();
        initRoot();

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
