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

        private Socket socket;

        Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String inputLine = null;
                StringBuilder requestHeader = new StringBuilder("");
                StringBuilder requestBody = new StringBuilder("");
                int contentLength = 0;

                //首先处理GET请求
                inputLine  = in.readLine();
                requestHeader.append(inputLine + "\n");
                String[] contents = inputLine.split(" ");
                if ("GET".equals(contents[0]) && "/".equals(contents[1])) {
                    socket.getOutputStream().write("Hello, world!\r\n".getBytes());
                    socket.getOutputStream().flush();
                }else{

                    while((inputLine  = in.readLine()).length() > 0) {

                        requestHeader.append(inputLine + "\n");

                        //获取实体长度
                        if(inputLine.contains("Content-Length")) {
                            String[] tmpA = inputLine.split("\\s+");
                            contentLength = Integer.parseInt(tmpA[1]);
                        }


                    }

                    //获取请求体内容
                    if(contentLength != 0) {

                        int c;
                        while((c = in.read()) != -1 ) {
                            requestBody.append((char)c);
                            contentLength--;
                            if(contentLength == 0) break;
                        }
                    }

                    System.out.println("***Request Header:\n" + requestHeader
                            + "\n***Request Body:\n" + requestBody
                            + "\n======================================" );
                }

            } catch (IOException e) {
                System.out.println(e.getMessage());
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }

            }
        }

    }
}


