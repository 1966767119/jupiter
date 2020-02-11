package com.vsniu.jupiter.basic;

import java.io.*;

/**
 * @Auther: wangfeng7
 * @Date: 2020/2/11 21:19
 * @Description: 序列化知识(如果序列化后的对象被读出来的时候，原来的类增加或者减少属性会不会报错)
 */
public class SerializableKnowledge {
    private static final String filePath = "./object.txt";
    private static void writeObject() throws IOException {
        Domain domain = new Domain(18,"wf7",12.68);
        ObjectOutput output = new ObjectOutputStream(new FileOutputStream(filePath));
        output.writeObject(domain);
        output.flush();
        output.close();
    }
    private static void readObject() throws IOException, ClassNotFoundException {
        ObjectInput input = new ObjectInputStream(new FileInputStream(filePath));
        Domain domain = (Domain)input.readObject();
        System.out.println(domain);
        input.close();
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        writeObject();
        readObject();
    }
}
