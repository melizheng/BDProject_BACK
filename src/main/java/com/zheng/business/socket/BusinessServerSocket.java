package com.zheng.business.socket;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 服务器的监听代码 监听链接的用户，一旦链接便加入list用户中，并且创建对应的新线程对信息进行处理
 * Date:2022/3/179:59
 **/

@Service
public class BusinessServerSocket {
    private ServerSocket serverSocket;
    private ArrayList<SocketUser> list;
    private HashMap<String,String> waitSendMessages;
    public BusinessServerSocket() {
    }

    public void ServerSocketWorking(){
        try {
            //开启服务的套接字端口
            serverSocket = new ServerSocket(10001);
            list=new ArrayList<>();
            waitSendMessages=new HashMap<>();
            System.out.println("服务端开始工作~");
            while (true) {
                //等待接受客户端连接请求
                Socket socket = serverSocket.accept();
                //暂时不能确定名字与id，需要在对应线程中接受客户端的消息得到id和名字才可以修改
                SocketUser user = new SocketUser("id","name", socket);
                list.add(user);
                // 创建一个新的线程，接收信息并转发
                ServerThread thread = new ServerThread(user, list,waitSendMessages);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
