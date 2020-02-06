package com.vsniu.jupiter.bio.server;

import java.io.*;
import java.net.Socket;
import java.util.Map;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/6 15:04
 * @Description: 单独线程处理用户发送来的消息
 */
public class ServerMsgHandler implements Runnable {
    private static final String QUIT = "quit";
    private Socket socket;
    private Map<Integer,Socket> clients;
    public ServerMsgHandler(Socket socket, Map<Integer,Socket> clients) {
        this.socket = socket;
        this.clients = clients;
    }
    @Override
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String msg;
            while ((msg = reader.readLine())!= null){
                sendMsgToOtherClients(msg);
                if (QUIT.equals(msg)){
                    removeSelfFromClients();
                    break;
                }
            }
            System.out.println("服务端收到空消息");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    /**
     *
     * 功能描述: 将消息转发给其他在线用户
     * @auther: wangfeng7
     * @date: 2020/2/6 15:27
     * @param: msg
     */
    private void sendMsgToOtherClients(String msg) throws IOException {
        msg = "客户端【"+socket.getPort()+"】："+msg;
        System.out.println(msg);
        for (Map.Entry<Integer,Socket> entry:clients.entrySet()){
            Socket tmp = entry.getValue();
            if (tmp.getPort() != socket.getPort()){
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(tmp.getOutputStream()));
                writer.write(msg+"\n");
                writer.flush();
            }
        }
    }
    /**
     *
     * 功能描述: 客户端退出操作
     * @auther: wangfeng7
     * @date: 2020/2/6 15:26
     */
    private void removeSelfFromClients() throws IOException {
        if (clients.containsKey(socket.getPort())){
            clients.remove(socket.getPort());
            socket.close();
            System.out.println("客户端【"+socket.getPort()+"】退出");
        }
    }
}
