package com.njupt.swg.threadpicture;


import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: swg
 * @date: 2021-12-04 17:01
 * @description:
 */
public class ServerThread implements Runnable {
    //根目录
    private static final String webroot = "F:\\webroot\\";

    private Socket client;
    InputStream ins;
    OutputStream out;

    //存放类型，比如jpg对应的是image/jpeg，这是http协议规定的每种类型的响应格式
    private static Map<String,String> contentMap =  new HashMap<>();
    static {
        contentMap.put("html","text/html;charset=utf-8");
        contentMap.put("jpg","image/jpeg");
        contentMap.put("png","image/jpeg");
    }

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
        //1、获取请求资源名称
        BufferedReader reader = new BufferedReader(new InputStreamReader(ins));
        String requestPath = reader.readLine().split(" ")[1].replace("/","\\");
        if(requestPath.equals("\\")){
            requestPath += "index.html";
        }

        //2、拼接起来就是资源的完整路径
        File file = new File(webroot + requestPath);

        //3、给用户响应
        if (file.exists()) {
            //给用户响应
            PrintWriter pw = new PrintWriter(out);
            InputStream i = new FileInputStream(webroot + requestPath);
            //由于需要将图片也要传给前端，再用这个就不好办了，得用普通的文件输入流
            pw.println("HTTP/1.1 200 OK");
            //返回的类型是动态判断的，图片用图片的类型，文本用文本的类型
            String s = contentMap.get(requestPath.substring(requestPath.lastIndexOf(".")+1,requestPath.length()));
            System.out.println("返回的类型为："+ s);
            pw.println("Content-Type: " + s);
            pw.println("Content-Length:" + i.available());
            pw.println("Server: hello-server");
            pw.println("Date:"+ new Date());
            pw.println("");
            pw.flush();

            //写入输出流中通过PrintWriter发给浏览器
            byte[] buff = new byte[1024];
            int len = 0;
            while ( (len = i.read(buff)) != -1){
                out.write(buff,0,len);
            }
            pw.flush();
            pw.close();
            i.close();
            reader.close();
            client.close();
        }else {
            StringBuffer error = new StringBuffer();
            String s = "<h1>404,File Not Found</h1>";
            error.append("HTTP /1.1 404 file not found /r/n");
            error.append("Content-Type:text/html \r\n");
            error.append("Content-Length:"+s.length()+"\r\n").append("\r\n");
            error.append(s);
            try {
                out.write(error.toString().getBytes());
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
