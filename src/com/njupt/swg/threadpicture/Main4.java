package com.njupt.swg.threadpicture;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main4 {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("服务器已经启动...正在监听8888端口，随时等待客户端连接");
        //服务端创建一个线程来处理客户端请求
        while (!Thread.interrupted()){
            //接收用户请求
            Socket client = server.accept();

            new Thread(new ServerThread(client)).start();
        }
        server.close();
    }
}
