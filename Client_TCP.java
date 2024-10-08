package Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client_TCP {
	private static int[][] Matrancoso=null;
    private static int[][] Matrandungluong=null;
    private static Object[][] Matranketqua=null;
    private static int[] Distra1= {1};
    public Client_TCP(int[][] Matrancoso,int[][] Matrandungluong) {
    	this.Matrancoso=Matrancoso;
    	this.Matrandungluong=Matrandungluong;
    }
    public  void  connectToServer(){
		 
		   try (Socket sk = new Socket("localhost", 2027);
			        ObjectOutputStream obj = new ObjectOutputStream(sk.getOutputStream())) {
			        ObjectInputStream ois =new ObjectInputStream(sk.getInputStream());
			        
			        obj.writeObject(Matrancoso);
			        obj.writeObject(Matrandungluong);	
			        obj.flush();  
			       
			        Matranketqua=(Object[][])ois.readObject();
			        Distra1=(int[]) ois.readObject();
			       
			    } catch (IOException | ClassNotFoundException ex ) {
			        ex.printStackTrace(); 
			    }
		  
	}
  
    public int[] getDistra1() {
        return Distra1;
    }
	public Object[][] getMatranketqua() {
		 return Matranketqua;
	}
    
}
