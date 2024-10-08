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
    private ObjectOutputStream obj;
    private ObjectInputStream ois;

    public Client_TCP(int[][] Matrancoso, int[][] Matrandungluong) {
        this.Matrancoso = Matrancoso;
        this.Matrandungluong = Matrandungluong;
    }

    public void connectToServer() throws UnknownHostException, IOException {
        if (sk == null || sk.isClosed()) {
            sk = new Socket("localhost", 2027);
            obj = new ObjectOutputStream(sk.getOutputStream());
            ois = new ObjectInputStream(sk.getInputStream());
        }
    }

    public void sendDataToServer() throws IOException, ClassNotFoundException {
        if (obj != null) {
            obj.writeObject(Matrancoso);
            obj.writeObject(Matrandungluong);
            obj.flush();

            // Nhận dữ liệu
            Matranketqua = (Object[][]) ois.readObject();
            Distra1 = (int[]) ois.readObject();
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
            if (sk != null && !sk.isClosed()) {
                sk.close();
            }
            if (obj != null) {
                obj.close();
            }
            if (ois != null) {
                ois.close();
            }
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
