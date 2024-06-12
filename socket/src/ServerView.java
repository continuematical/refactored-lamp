import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

//https://cloud.tencent.com/developer/article/1660809

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

    /**
     * 底部发送按钮
     */
    private JTextField text_field;
    private JTextField sysText_field;
    private JButton foot_send;
    private JButton foot_sysSend;
    private JButton foot_userClear;

    /**
     * 顶部布局
     */
    private JButton send_button;
    private JButton exit_button;

    /**
     * 整体布局
     */
    JPanel panel = new JPanel();
    JPanel leftPanel = new JPanel();
    JPanel centerPanel = new JPanel();
    JPanel rightPanel = new JPanel();
    JLabel headPanel = new JLabel();
    JLabel footPanel = new JLabel();

    /**
     * 布局排序
     */
    BorderLayout layout = new BorderLayout();
    GridBagLayout gridBagLayout = new GridBagLayout();
    FlowLayout flowLayout = new FlowLayout();

    /**
     * 构造函数
     */
    public ServerView() {
        init();
    }

    //文本区域添加信息
    void setTextArea(String string) {
        textArea.append(string + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    /**
     * 左边布局初始化
     */
    void initLeft() {
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
     * 中间布局初始化
     */
    void initMedium() {
        JLabel userMsgLabel = new JLabel("世界聊天");
        userMsgArea = new JTextPane();
        userMsgArea.setEditable(false);
        userTextScrollPane = new JScrollPane();
        userTextScrollPane.setViewportView(userMsgArea);
        userVertical = new JScrollBar();
        userVertical.setAutoscrolls(true);
        userTextScrollPane.setVerticalScrollBar(userVertical);

        centerPanel.add(userMsgLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        centerPanel.add(userTextScrollPane, new GridBagConstraints(0, 1, 1, 1, 100, 100,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));

    }

    /**
     * 右边布局初始化
     */
    void initRight() {
        userLabel = new JLabel("当前连接用户：0");
        kick_button = new JButton("踢出");
        users_model = new DefaultListModel<>();
        userList = new JList<String>(users_model);
        JScrollPane userListPane = new JScrollPane(userList);

        rightPanel.add(userLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        rightPanel.add(kick_button, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        rightPanel.add(userListPane, new GridBagConstraints(0, 3, 1, 1, 100, 100,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
    }

    /**
     * 底部布局初始化
     */
    void initRoot() {
        foot_send = new JButton("发送聊天信息");
        foot_sysSend = new JButton("发送系统信息");
        foot_sysSend.setPreferredSize(new Dimension(110, 0));
        foot_userClear = new JButton("清空聊天消息");
        foot_userClear.setPreferredSize(new Dimension(148, 0));

        sysText_field = new JTextField();
        sysText_field.setPreferredSize(new Dimension(230, 0));
        text_field = new JTextField();
        footPanel.add(sysText_field, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
        footPanel.add(foot_sysSend, new GridBagConstraints(1, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 3), 0, 0));
        footPanel.add(text_field, new GridBagConstraints(2, 0, 1, 1, 100, 100,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
        footPanel.add(foot_send, new GridBagConstraints(3, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
        footPanel.add(foot_userClear, new GridBagConstraints(4, 0, 1, 1, 1.0, 1.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(1, 1, 1, 1), 0, 0));
    }

    /**
     * 顶部布局初始化
     */
    void initHead() {
        send_button = new JButton("发送");
        exit_button = new JButton("退出");

        headPanel.add(send_button);
        headPanel.add(exit_button);
    }

    /**
     * frame设置
     */
    void initFrame() {
        frame = new JFrame("服务器端");
        frame.setSize(900, 600);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setContentPane(panel);
        frame.setLayout(layout);
    }

    /**
     * Panel大小配置
     */
    void initPanel() {
        headPanel.setLayout(flowLayout);
        footPanel.setLayout(gridBagLayout);
        leftPanel.setLayout(gridBagLayout);
        centerPanel.setLayout(gridBagLayout);
        rightPanel.setLayout(gridBagLayout);

        leftPanel.setPreferredSize(new Dimension(350, 0));
        rightPanel.setPreferredSize(new Dimension(155, 0));
        footPanel.setPreferredSize(new Dimension(0, 40));
    }

    /**
     * 设置各种监听
     */
    void setListener() {
        //服务器窗口关闭事件
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int option = JOptionPane.showConfirmDialog(frame, "确定关闭服务器界面吗？", "提示", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        //系统日志监听
        sysMsgArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == KeyEvent.VK_ENTER) {
                    String sysText = sysText_field.getText(); //获取输入框中的内容
                    if (sysText != null && !sysText.equals("")) {
                        sendSysMsg(sysText, 2);
                        sysText_field.setText("");
                        insertMessage(sysTextScrollPane, sysMsgArea, null, "[系统日志] " + df.format(new Date()), "[管理员]" + adminName + "：" + sysText, sysVertical, true);
                    }
                }
            }
        });
    }

    //界面初始化
    void init() {
        initFrame();
        initPanel();
        initLeft();
        initRight();
        initMedium();
        initHead();
        initRoot();

//          原始布局
//        JPanel panel = new JPanel();
//        JLabel label = new JLabel("==欢迎来到多人聊天系统（服务器端）==");
//        textArea = new JTextArea(23, 38);
//        textArea.setEditable(false);
//        JScrollPane scrollPane = new JScrollPane(textArea);
//        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
//        panel.add(label);
//        panel.add(scrollPane);


        //设置顶层布局
        panel.add(headPanel, "North");
        panel.add(footPanel, "South");
        panel.add(leftPanel, "West");
        panel.add(rightPanel, "East");
        panel.add(centerPanel, "Center");


        frame.setVisible(true);
    }
}
