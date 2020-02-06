package com.vsniu.jupiter.nio.server;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Set;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/6 17:37
 * @Description: nio 实现聊天室
 */
public class ChatServer {
    private static final int DEFAULT_PORT = 8800;
    //服务端socket
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;
    private ByteBuffer rBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    private ByteBuffer wBuffer = ByteBuffer.allocate(BUFFER_SIZE);
    private static final int BUFFER_SIZE = 1024;
    private Charset charset = Charset.forName("UTF-8");
    private static final String QUIT = "quit";

    private void start() {
        try {
            this.serverSocketChannel = ServerSocketChannel.open();
            //配置channel为非阻塞
            this.serverSocketChannel.configureBlocking(false);
            //绑定监听端口
            this.serverSocketChannel.socket().bind(new InetSocketAddress(DEFAULT_PORT));
            //创建selector
            this.selector = Selector.open();
            //serverSocketChannel   在selector注册监听accept事件
            this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务端启动，监听端口"+DEFAULT_PORT);
            while (true){
                //阻塞等待有可以消费的消息
                selector.select();
                //可消费的SelectionKey 列表
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key:selectionKeys){
                    handleSelectionKey(key);
                }
                //清空处理过的数据
                selectionKeys.clear();
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            close(selector);
        }
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

    private void handleSelectionKey(SelectionKey key) throws IOException {
        //处理服务端ServerSocket的accept事件
        if (key.isAcceptable()){
            ServerSocketChannel channel = (ServerSocketChannel)key.channel();
            SocketChannel clientChannel = channel.accept();
            clientChannel.configureBlocking(false);
            clientChannel.register(selector,SelectionKey.OP_READ);
            System.out.println("客户端【"+clientChannel.socket().getPort()+"】注册到服务器");
        }else if (key.isReadable()){
            SocketChannel clientChannel = (SocketChannel)key.channel();
            String msg = getMsg(clientChannel);

            if (msg.isEmpty()){
                key.cancel();
                selector.wakeup();
            }else {
                sendMsg(clientChannel,msg);
                if (QUIT.equals(msg)){
                    key.cancel();
                    selector.wakeup();
                    System.out.println("客户端【"+clientChannel.socket().getPort()+"】已断开");
                }
            }
        }
    }
    /**
     *
     * 功能描述: 转发消息到其他已注册的channel
     * @auther: wangfeng7
     * @date: 2020/2/6 20:07
     * @param: socketChannel,msg
     */
    private void sendMsg(SocketChannel socketChannel, String msg) throws IOException {
        //已注册的keys
        Set<SelectionKey> selectionKeys = selector.keys();
        for (SelectionKey key:selectionKeys){
            Channel channel = key.channel();
            if (channel instanceof ServerSocketChannel || channel == socketChannel){
                continue;
            }
            if (key.isValid()){
                wBuffer.clear();
                msg = "客户端【"+socketChannel.socket().getPort()+"】:"+msg+"\n";
                System.out.print(msg);
                wBuffer.put(charset.encode(msg));
                wBuffer.flip();
                while (wBuffer.hasRemaining()){
                    ((SocketChannel)channel).write(wBuffer);
                }
            }
        }
    }

    private String getMsg(SocketChannel socketChannel) throws IOException {
        rBuffer.clear();
        while (socketChannel.read(rBuffer) > 0);
        rBuffer.flip();
        return String.valueOf(charset.decode(rBuffer));
    }

    public static void main(String[] args) {
        ChatServer chatServer = new ChatServer();
        chatServer.start();
    }
}
