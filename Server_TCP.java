package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server_TCP {
	private static int PORT=2027;
	//private static ExecutorService  ex=Executors.newCachedThreadPool();
	
	public static void main(String[] args) throws IOException  {
		ServerSocket sv=new ServerSocket(PORT);
		System.out.println("Server đang chạy trên cổng: " + PORT);		
		try {
		    while (true) {
		        Socket socket = sv.accept();		       
		        Thread thread = new Thread( new ClientHandler(socket));
                thread.start();
		    }
		} finally {
		    sv.close();
		}

			
		
	}
}
