package com.vsniu.jupiter.bio.client;

import java.io.*;
import java.net.Socket;
import java.net.SocketException;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/6 15:35
 * @Description:
 */
public class ChatClient {
    private Socket socket;
    private static final int SERVER_PORT = 8899;
    private static final String ADDRESS = "127.0.0.1";
    private static final String QUIT = "quit";

    private BufferedWriter writer;
    private BufferedReader reader;

    private void receiveMsg() throws IOException {
        while (true){
            if (!socket.isInputShutdown()){
                String msg = reader.readLine();
                System.out.println(msg);
            }
        }

    }
    public void sendMsg(String msg) throws IOException {
        if (!socket.isOutputShutdown()){
            writer.write(msg+"\n");
            writer.flush();
        }
        if (QUIT.equals(msg)){
            writer.close();
            System.out.println("客户端主动退出");
        }
    }
    private void close(){
        if (writer != null){
            try {
                writer.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    private void start(){
        try {
            this.socket = new Socket(ADDRESS,SERVER_PORT);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            //处理用户输入的消息（发送给服务端）
            new Thread(new UserInputHandler(this)).start();
            //读取服务端发送过来的消息
            receiveMsg();
        }catch (IOException e){
            if (e instanceof SocketException){
                //ignore socket close exception
            }
            e.printStackTrace();
        }finally {
            close();
        }
    }
    public boolean readyToQuit(String msg){
        return QUIT.equals(msg);
    }
    public static void main(String[] args) {
        ChatClient chatClient = new ChatClient();
        chatClient.start();
    }
}
