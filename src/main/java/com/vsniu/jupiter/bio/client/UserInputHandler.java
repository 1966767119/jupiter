package com.vsniu.jupiter.bio.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/6 16:16
 * @Description:
 */
public class UserInputHandler implements Runnable {
    private static final String QUIT = "quit";
    private BufferedWriter writer;
    private int port;

    public UserInputHandler(BufferedWriter writer, int port) {
        this.writer = writer;
        this.port = port;
    }

    @Override
    public void run() {
        System.out.println("等待用户输入。。。");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true){
                String userMsg = consoleReader.readLine();
                writer.write(userMsg+"\n");
                writer.flush();
                if (QUIT.equals(userMsg)){
                    writer.close();
                    System.out.println("客户端主动退出");
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
