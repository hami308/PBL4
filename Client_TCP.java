package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client_TCP {
    private int[][] Matrancoso;
    private int[][] Matrandungluong;
    private Object[][] Matranketqua;
    private int[] Distra1;

    private Socket sk;
    private ObjectOutputStream output;
    private ObjectInputStream input;

    public Client_TCP(int[][] Matrancoso, int[][] Matrandungluong) {
        this.Matrancoso = Matrancoso;
        this.Matrandungluong = Matrandungluong;
    }

    public void connectToServer(int port) throws UnknownHostException, IOException {
        if (sk == null || sk.isClosed()) {
            sk = new Socket("192.168.20.224", port);
            output = new ObjectOutputStream(sk.getOutputStream());
            input = new ObjectInputStream(sk.getInputStream());
        }
    }

    public void sendDataToServer() throws IOException, ClassNotFoundException {
        if (output != null) {
            output.writeObject(Matrancoso);
            output.writeObject(Matrandungluong);
            output.flush();

            // Nhận dữ liệu
            Matranketqua = (Object[][]) input.readObject();
            Distra1 = (int[]) input.readObject();
        }
    }

    public void updateData(int[][] newMatrancoso, int[][] newMatrandungluong) throws IOException, ClassNotFoundException {
        this.Matrancoso = newMatrancoso;
        this.Matrandungluong = newMatrandungluong;

        // Gọi phương thức gửi dữ liệu tới server
        sendDataToServer();
    }

    public void closeConnection() {
        try {
        	  // Gửi tín hiệu kết thúc trước khi đóng kết nối
            output.writeObject(null);
            output.writeObject(null);
            output.flush();

            // Đóng các luồng và socket
            if (output != null) output.close();
            if (input != null) input.close();
            if (sk != null) sk.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int[] getDistra1() {
        return Distra1;
    }

    public Object[][] getMatranketqua() {
        return Matranketqua;
    }
}
