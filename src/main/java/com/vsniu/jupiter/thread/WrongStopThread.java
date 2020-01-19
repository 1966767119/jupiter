package com.vsniu.jupiter.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @Auther: wangfeng7
 * @Date: 2020-01-19 16:46
 * @Description: 错误的停止线程的方式
 */
public class WrongStopThread {
    static BlockingQueue<String> queue = new ArrayBlockingQueue<String>(100);
    static volatile boolean flag = false;

    public static void main(String[] args) throws Exception{
        new  Thread(new Producer(queue)).start();
        Thread.sleep(1000);
        Consumer consumer = new Consumer(queue);
        while (consumer.needMore()){
            System.out.println("消费者："+queue.take());
            Thread.sleep(100);
        }
        System.out.println("消费者不需要更多数据了");
        flag = true;
    }

    /**
     *
     * 功能描述: 生产者
     * @auther: wangfeng7
     * @date: 2020-01-19 16:48
     */
    static class Producer implements Runnable{
        BlockingQueue<String> queue;
        @Override
        public void run() {
            int num = 0;
            try {
                while (!flag && num<100000){
                    if (num % 100 == 0 ){
                        String str = "hello world"+num;
                        queue.put(str);
                        System.out.println("生产者："+str+",flag:"+flag+",size:"+queue.size());

                    }
                    num++;

                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                System.out.println("生产结束");
            }

        }

        public Producer(BlockingQueue<String> queue) {
            this.queue = queue;
        }
    }
    /**
     *
     * 功能描述: 消费者
     * @auther: wangfeng7
     * @date: 2020-01-19 16:49
     */
    static class Consumer {
        BlockingQueue<String> queue;
        private boolean needMore(){
            if (Math.random()>0.95){
                return false;
            }else {
                return true;
            }
        }
        public Consumer(BlockingQueue<String> queue) {
            this.queue = queue;
        }
    }
}
