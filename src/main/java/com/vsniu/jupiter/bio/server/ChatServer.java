package com.vsniu.jupiter.bio.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/6 14:49
 * @Description:
 */
public class ChatServer {
    private static final int DEFAULT_PORT = 8899;
    //服务端socket
    private ServerSocket serverSocket;
    //客户端socket集合
    private Map<Integer, Socket> clientsMap;

    private void start() {
        try {
            this.serverSocket = new ServerSocket(DEFAULT_PORT);
            System.out.println("服务端启动，监听端口"+DEFAULT_PORT);
            this.clientsMap  = new ConcurrentHashMap<Integer,Socket>();
            while (true){
                Socket socket = serverSocket.accept();
                System.out.println(socket.getPort()+"注册到服务端了！！！！");
                clientsMap.put(socket.getPort(),socket);
                //新建一个线程来处理用户输入的消息
                new Thread(new ServerMsgHandler(socket,clientsMap)).start();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                //ignore the close exception
            }
        }
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }
}
