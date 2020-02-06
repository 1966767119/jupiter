package com.vsniu.jupiter.nio.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/6 19:06
 * @Description:
 */
public class UserInputHandler implements Runnable {
    private ChatClient chatClient;

    public UserInputHandler(ChatClient chatClient) {
        this.chatClient = chatClient;
    }
    @Override
    public void run() {
        System.out.println("等待用户输入。。。");
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true){
                String userMsg = consoleReader.readLine();
                chatClient.sendMsg(userMsg);
                if(chatClient.readyToQuit(userMsg)){
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
