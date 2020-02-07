package com.vsniu.jupiter.nio.client;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/6 18:48
 * @Description:
 */
public class ChatClient {
    private SocketChannel socketChannel;
    private static final int SERVER_PORT = 8800;
    private static final String ADDRESS = "127.0.0.1";
    private Selector selector;
    private ByteBuffer rBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    private ByteBuffer wBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    private static final int BUFFER_SIZE = 1024;
    private Charset charset = Charset.forName("UTF-8");
    private static final String QUIT = "quit";
    private void start(){
        try {
            this.socketChannel = SocketChannel.open();
            this.socketChannel.configureBlocking(false);
            this.selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress(ADDRESS,SERVER_PORT));
            while (true){
                selector.select();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key:selectionKeys){
                    handleSelectionKey(key);
                }
            }
        }catch (IOException e){
            if (e instanceof SocketException){
                //ignore socket close exception
            }
            e.printStackTrace();
        }finally {
            close(selector);
        }
    }

    private void handleSelectionKey(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        //处理服务端ServerSocket的accept事件
        if (key.isConnectable()){
            if (socketChannel.isConnectionPending()){
                socketChannel.finishConnect();
                //新开线程处理用户输入
                new Thread(()->{
                    System.out.println("等待用户输入。。。");
                    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                    try {
                        while (true){
                            String userMsg = consoleReader.readLine();
                            sendMsg(userMsg);
                            if(readyToQuit(userMsg)){
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
                socketChannel.register(selector,SelectionKey.OP_READ);
            }
        }else if (key.isReadable()){
            String msg = getMsg(socketChannel);
            if (msg.isEmpty()){
                close(selector);
            }else {
                System.out.print(msg);
            }
        }
    }

    public void sendMsg(String msg) throws IOException {
        wBuffer.clear();
        wBuffer.put(charset.encode(msg));
        wBuffer.flip();
        while (wBuffer.hasRemaining()){
            socketChannel.write(wBuffer);
        }
        if (QUIT.equals(msg)){
            close(selector);
            System.out.println("客户端主动退出");
        }
    }
    private String getMsg(SocketChannel socketChannel) throws IOException {
        rBuffer.clear();
        while (socketChannel.read(rBuffer) > 0);
        rBuffer.flip();
        return String.valueOf(charset.decode(rBuffer));
    }
    /**
     *
     * 功能描述: 关闭流
     * @auther: wangfeng7
     * @date: 2020/2/6 18:07
     * @param: closeable
     */
    private void close(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            e.printStackTrace();
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
