import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 服务器界面
 */

public class ServerView {
    private JFrame frame;
    private JTextArea textArea;

    public ServerView() {
        init();
    }

    void setTextArea(String line) {
        textArea.append(line + "\n");
        textArea.setCaretPosition(textArea.getDocument().getLength());
    }

    //界面初始化
    void init() {

        frame = new JFrame("服务器端");
        frame.setBounds(100, 200, 400, 500);

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
                int option = JOptionPane.showConfirmDialog(frame, "确定关闭服务器界面吗？", "提示", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });

        frame.add(panel);
        frame.setVisible(true);
    }
}
