package com.njupt.swg.thread;


import java.io.*;
import java.net.Socket;
import java.util.Date;

/**
 * @author: swg
 * @date: 2021-12-04 17:01
 * @description:
 */
public class ServerThread implements Runnable {
    private Socket client;
    InputStream ins;
    OutputStream out;

    public ServerThread(Socket client){
        this.client = client;
        init();
    }

    private void init(){
        try {
            ins = client.getInputStream();
            out = client.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            go();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void go() throws IOException {
        //给用户响应
        PrintWriter pw = new PrintWriter(out);
        InputStream i = new FileInputStream("f:\\webroot\\index.html");
        BufferedReader br = new BufferedReader(new InputStreamReader(i));
        pw.println("HTTP/1.1 200 OK");
        pw.println("Content-Type: text/html;charset=utf-8");
        pw.println("Content-Length:" + i.available());
        pw.println("Server:hello-server");
        pw.println("Date:"+new Date());
        pw.println("");
        pw.flush();

        String c = null;
        while ((c = br.readLine()) != null){
            pw.println(c);
        }
        pw.flush();

        pw.close();
        br.close();
        i.close();
        client.close();
    }
}
