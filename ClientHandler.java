package Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

public class ClientHandler implements Runnable {
    private Socket client;
    private static ObjectOutputStream out;
    private static ObjectInputStream in;
    public ClientHandler(Socket sk) {
        this.client = sk;
       
    }

    @Override
    public void run() {
    	
        try {
        	while(true) {
            out = new ObjectOutputStream(client.getOutputStream());
            in = new ObjectInputStream(client.getInputStream());
                     // Nhận dữ liệu từ client
            int[][] Matrancoso = (int[][]) in.readObject();
            int[][] Matrandungluong = (int[][]) in.readObject();
            double[][] T_ = new double[Matrancoso.length][Matrancoso[0].length];
            
            // Khởi tạo đối tượng Calculator với dữ liệu nhận được
            Calculator calculator = new Calculator(Matrancoso, Matrandungluong);
            
            // Thực hiện tính toán và nhận về kết quả 
            Object[][] Matranketqua = calculator.computeFlow(Matrancoso, Matrandungluong, T_);
            
            int[] Distra=calculator.dijkstra(T_);
            // Gửi ma trận kết quả về lại cho client
            out.writeObject(Matranketqua);
            out.writeObject(Distra);
            out.flush();
        	}
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } finally {          
                try {
					client.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            
        }
    }

}
