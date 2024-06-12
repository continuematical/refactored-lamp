import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务器
 */

public class Server {
    static ServerSocket server = null;
    static Socket socket = null;
    static List<Socket> list = new ArrayList<Socket>();

    public static void main(String[] args) throws IOException {
        ServerView serverView = new ServerView();

        server = new ServerSocket(8081);
        while (true) {
            socket = server.accept();
            list.add(socket);

            ServerUtil serverUtil = new ServerUtil(socket, serverView);
            serverUtil.start();
        }
    }
}


class ServerUtil extends Thread {
    Socket nowSocket = null;
    ServerView serverView = null;
    BufferedReader in = null;
    PrintWriter out = null;

    public ServerUtil(Socket socket, ServerView serverView) {
        this.nowSocket = socket;
        this.serverView = serverView;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(nowSocket.getInputStream()));
            while (true) {
                String line = in.readLine();
                for (Socket socket : Server.list) {
                    out = new PrintWriter(socket.getOutputStream(), true);
                    if (socket == nowSocket) {
                        out.println("你：" + line);
                    } else {
                        out.println(line);
                    }
                }
                serverView.setTextArea(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
