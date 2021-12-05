package com.njupt.swg.contentlengthtest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main6 {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("服务器已经启动...正在监听8888端口，随时等待客户端连接");
        //服务端创建一个线程来处理客户端请求
        while (!Thread.interrupted()){
            //接收用户请求
            Socket client = server.accept();
            //获取输入输出流
            OutputStream out = client.getOutputStream();

            String c = "hello world!";

            //给用户响应
            PrintWriter pw = new PrintWriter(out);
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type: text/html;charset=utf-8");
            pw.println("Content-Length: 12");
            pw.println("");
            pw.flush();

            pw.write(c);
            pw.flush();

            pw.close();
            out.close();
            client.close();
        }
        server.close();
    }
}
