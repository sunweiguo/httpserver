package com.njupt.swg;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {

    public static void main(String[] args) throws IOException {
        //服务端监听在8888端接口号
        ServerSocket server = new ServerSocket(8888);
        System.out.println("服务器已经启动...正在监听8888端口，随时等待客户端连接");
        //服务端创建一个线程来处理客户端请求
        while (!Thread.interrupted()){
            //接收用户请求
            Socket client = server.accept();
            //获取输入输出流
            InputStream ins = client.getInputStream();
            OutputStream out = client.getOutputStream();
            //打印获取到的请求内容
            int len = 0;
            byte[] b = new byte[1024];
            while((len = ins.read(b)) != -1){
                System.out.println(new String(b,0,len));
            }
        }
    }
}
