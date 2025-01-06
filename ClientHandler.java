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

import javax.swing.JTextArea;
import javax.swing.table.DefaultTableModel;

public class ClientHandler extends Thread {
    private Socket client;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private JTextArea textArea; 
    private DefaultTableModel tableModel;
    private int chisohang;
    public ClientHandler(Socket sk, JTextArea textArea,DefaultTableModel tableModel,int chisohang) throws IOException {
        this.client = sk;
        this.textArea = textArea;
        this.tableModel=tableModel;
        this.chisohang=chisohang;
        out = new ObjectOutputStream(client.getOutputStream());
        in = new ObjectInputStream(client.getInputStream());
        start();
    }
   
    @Override
    public void run() {
    	
        try {
        	
        	while(true) {           
                     // Nhận dữ liệu từ client
            int[][] Matrancoso = (int[][]) in.readObject();
            int[][] Matrandungluong = (int[][]) in.readObject();
            
            // Kiểm tra nếu client gửi tín hiệu kết thúc (gửi null)
            if (Matrancoso == null || Matrandungluong == null) {           
                break; // Thoát khỏi vòng lặp nếu nhận tín hiệu kết thúc
            }
            
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
        	  textArea.append("Lỗi: " + e.getMessage() + "\n");
        } finally {          
        	 try {
                 String host = client.getInetAddress().getHostName();
                 textArea.append("Client " + host + " đã kết thúc kết nối!\n");
                 client.close();
                 tableModel.setValueAt("Đã ngắt kết nối.", chisohang, 2);
             } catch (IOException e) {
                 textArea.append("Lỗi khi đóng kết nối: " + e.getMessage() + "\n");
             }
        }
    }

}
