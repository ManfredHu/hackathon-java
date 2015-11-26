import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
				String inputLine = in.readLine();
				String[] contents = inputLine.split(" ");
				//输出contents
				System.out.println("TEST!!!!!!!####!@#!@#" + contents);
				if ("GET".equals(contents[0]) && "/".equals(contents[1])) {
					socket.getOutputStream().write("Hello, world!test\r\n".getBytes());
					socket.getOutputStream().flush();
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

