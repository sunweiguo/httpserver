package com.njupt.swg;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Main2 {

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket(8888);
        System.out.println("服务器已经启动...正在监听8888端口，随时等待客户端连接");
        //服务端创建一个线程来处理客户端请求
        while (!Thread.interrupted()){
            //接收用户请求
            Socket client = server.accept();
            //获取输入输出流
            InputStream ins = client.getInputStream();
            OutputStream out = client.getOutputStream();
            //给用户响应
            //首先读取html文件流，准备返回给浏览器
            PrintWriter pw = new PrintWriter(out);
            InputStream i = new FileInputStream("f:\\webroot\\index.html");
            BufferedReader br = new BufferedReader(new InputStreamReader(i));
            //配置http协议需要的响应头信息，注意响应头结束后有一行空行
            pw.println("HTTP/1.1 200 OK");
            pw.println("Content-Type: text/html;charset=utf-8");
            pw.println("Content-Length:" + i.available());
            pw.println("Server:hello-server");
            pw.println("Date:"+new Date());
            pw.println("");
            //在空行之后就是返回响应体，即html给浏览器渲染展示
            String c = null;
            while ((c = br.readLine()) != null){
                pw.println(c);
            }
            pw.flush();
            System.out.println(" 本次请求处理结束");
        }
    }
}
