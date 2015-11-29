package com.hackathon;

import com.hackathon.servlet.Filter;
import com.hackathon.servlet.Request;
import com.hackathon.servlet.Response;
import com.hackathon.servlet.Servlet;
import com.hackathon.servlet.impl.ServletRequest;
import com.hackathon.servlet.impl.ServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by beatk on 2015/11/26.
 */
public class Server {

    public static void main(String[] args) {
        ServerSocket serverSocket = null;

        System.out.println("***Server Init Spring!!!!");

        //加载并初始化所有Servlet
        ApplicationContext ctx =
                new ClassPathXmlApplicationContext("ApplicationContext.xml");
        Servlet.initServlet(ctx);


        try {
            serverSocket = new ServerSocket(8080);
            while (true) {
                Socket client = serverSocket.accept();
                new Thread(new Handler(client)).start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (serverSocket != null)
                    serverSocket.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static class Handler implements Runnable {

        private Socket socket = null;

        private Request request = null;
        private Response response = null;
        private Servlet servlet = null;
        private Filter filter = null;

        Handler(Socket socket) {
            this.socket = socket;
            this.response = new ServletResponse(this.socket);
        }

        @Override
        public void run() {

            //获取HTTP信息
            StringBuilder requestHeader = new StringBuilder("");
            StringBuilder requestBody = new StringBuilder("");
            if(!readInfo(requestHeader,requestBody)) {

                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                return;
            }

            //创建Request、Response对象
            this.request = new ServletRequest(requestHeader.toString(),requestBody.toString());
            String uri = "";    //请求的处理程序的uri

            //判断请求方法，设置uri


            if(!this.request.getRequestType().equals("PATCH")) {

                uri = this.request.getRequestURI();

            }else {    //如果请求方法类型为PATCH,则进行特殊的URL解析

                //获取patchParam参数
                String[] tmp = request.getRequestURI().split("/");
                request.setPatchParam(tmp[tmp.length - 1]);

                //获取Patch请求URI
                uri = request.getRequestURI().substring(0,
                        request.getRequestURI().length()
                                - request.getPatchParam().length() - 1);

            }

            //根据URI，获取映射的Servlet，将请求交给相应Servlet处理（先经过Filter）
            System.out.println("****uri =: " + uri);
            this.servlet = Servlet.getServlet(uri);
            if(this.servlet == null) {
                response.setStatusCode("404", "NOT FOUND");
                try {
                    response.outPut();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }else {

                this.filter = new ServletFilter(this.servlet);
                filter.doFilter(request,response);
                System.out.println("****Servlet Over");
            }

            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        //初始过程：解析HTTP请求，获取请求报头以及请求体
        private boolean readInfo(StringBuilder requestHeader,StringBuilder requestBody) {

            try {

                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String inputLine = null;
                int contentLength = 0;

                while ((inputLine = in.readLine()) != null && inputLine.length() > 0) {

                    requestHeader.append(inputLine + "\n");

                    //获取实体长度
                    if (inputLine.contains("Content-Length")) {
                        String[] tmpA = inputLine.split("\\s+");
                        contentLength = Integer.parseInt(tmpA[1]);
                    }
                }

                //若请求头为空，返回false
                if(inputLine == null) return false;

                //获取请求体内容
                if (contentLength != 0) {

                    int c;
                    while ((c = in.read()) != -1) {
                        requestBody.append((char) c);
                        contentLength--;
                        if (contentLength == 0) break;
                    }
                }

                System.out.println("***Request Header:\n" + requestHeader
                        + "\n***Request Body:\n" + requestBody
                        + "\n======================================");
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            return true;
        }
    }
}


