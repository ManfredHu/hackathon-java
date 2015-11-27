import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.StringBuilder;
import java.net.ServerSocket;
import java.net.Socket;

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
				StringBuilder requestHeader = new StringBuilder();
                System.out.println("***socket data:  " + Thread.currentThread());

//				inputLine  = in.readLine();
//				String[] contents = inputLine.split(" ");
//				if ("GET".equals(contents[0]) && "/".equals(contents[1])) {
//					socket.getOutputStream().write("Hello, world!\r\n".getBytes());
//					socket.getOutputStream().flush();
//				}else{
//
//				}
                while((inputLine  = in.readLine())!= null) {

					System.out.println(Thread.currentThread() + inputLine);

					String[] contents = inputLine.split(" ");
					if ("GET".equals(contents[0]) && "/".equals(contents[1])) {
						socket.getOutputStream().write("Hello, world!\r\n".getBytes());
						socket.getOutputStream().flush();
					}else{

					}
                }

				socket.getOutputStream().write("hehe!!\r\n".getBytes());
				socket.getOutputStream().flush();
				socket.close();



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

