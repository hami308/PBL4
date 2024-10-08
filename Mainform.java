package View;
import Client.Client_TCP;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Frame;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Container;
import java.awt.SystemColor;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.FlowLayout;
import javax.swing.UIManager;
import java.awt.GridLayout;
import java.awt.Button;

public class Mainform extends JFrame implements ActionListener{
	
	JButton ketthuc = new JButton("Kết thúc");
	JButton ketqua = new JButton("Kết quả ");
	JButton bangsolieu = new JButton("Bảng số liệu");
	JButton btn = new JButton("Chọn file");
	
	static JPanel  panel = new JPanel();
	static JPanel  panel_ = new JPanel();
	static GraphPanel panel_3;
	private static int[][] Matrancoso=null;
    private static int[][] Matrandungluong=null;
    private static Object[][] Matranketqua=null;
    private static int[] Distra= {1};
    private static int[] Distra1= {1};
    static Client_TCP client_tcp;
	public Mainform() {
		
		getContentPane().setBackground(new Color(0, 153, 204));
		setTitle("Thuật toán định luồng cơ sở");
		getContentPane().setLayout(null);
		
		panel_.setBackground(new Color(255, 255, 255));
		panel_.setBounds(30, 51, 335, 317);
		getContentPane().add(panel_);
		panel_.setLayout(null);
		
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(382, 51, 384, 317);
		getContentPane().add(panel);
		panel.setBorder(new TitledBorder(null, "Dữ liệu", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("Luồng lưu thông");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 22));
        lblNewLabel.setBounds(104, 11, 172, 29);
        lblNewLabel.setForeground(SystemColor.textHighlight);
        lblNewLabel.setBackground(new Color(255, 255, 255));
        panel.add(lblNewLabel);
        
        JLabel lblNewLabel_1 = new JLabel("(Gói/s)");
        lblNewLabel_1.setBounds(162, 40, 39, 19);
        panel.add(lblNewLabel_1);
        
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(new Color(255, 255, 255));
        panel_1.setBorder(new TitledBorder(null, "Điều khiển chương trình ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        panel_1.setBounds(77, 409, 507, 109);
        getContentPane().add(panel_1);
        panel_1.setLayout(null);
        
        ketqua.addActionListener(this);
        ketqua.setBackground(new Color(211, 211, 211));
        ketqua.setFont(new Font("Tahoma", Font.PLAIN, 14));
        ketqua.setBounds(206, 44, 111, 30);
        panel_1.add(ketqua);
        
        ketthuc.addActionListener(this);
        ketthuc.setBackground(new Color(240, 128, 128));
        ketthuc.setFont(new Font("Tahoma", Font.PLAIN, 14));
        ketthuc.setBounds(351, 44, 111, 30);
        panel_1.add(ketthuc);
        
        bangsolieu.addActionListener(this);
        bangsolieu.setFont(new Font("Tahoma", Font.PLAIN, 14));
        bangsolieu.setBackground(new Color(211, 211, 211));
        bangsolieu.setBounds(52, 44, 118, 30);
        panel_1.add(bangsolieu);
        
        btn.addActionListener(this);
        btn.setFont(new Font("Tahoma", Font.PLAIN, 14));
        btn.setBackground(new Color(255, 255, 255));
        btn.setBounds(615, 450, 111, 35);
        getContentPane().add(btn);

        setSize(812,593);
        setLocationRelativeTo(null);
        setVisible(true);
	}
	public static  void show(int[][] a) {
	    int num = 0;
	    for (int i = 0; i < a.length; i++) {
	        for (int j = 0; j < a[i].length; j++) {
	            if (a[i][j] != 0) num++;
	        }
	    }
	    panel.removeAll();  
	    panel_.removeAll(); 
	    JPanel panel_2 = new JPanel();
	   
	    panel_2.setLayout(new GridLayout(num/4,2,10,10)); 
	    panel_2.setBackground(Color.white);

	    char[] kitu = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
	    for (int i = 0; i < a.length; i++) {
	        for (int j = 0; j < a[i].length; j++) {
	            if (i < j && a[i][j] != 0) {
	                JLabel x = new JLabel(kitu[i] + "" + kitu[j] + " : " + a[i][j]);
	                x.setHorizontalAlignment(JLabel.CENTER);
	                x.setForeground(Color.RED);
	                x.setFont(new Font("Tahoma", Font.PLAIN, 16));
	                x.setBackground(new Color(211, 211, 211));
	                x.setOpaque(true);
	                panel_2.add(x);
	            }
	        }
	    }
	    panel_3 = new GraphPanel(Matrandungluong,Distra);
        
	    JScrollPane scrollPane1 = new JScrollPane(panel_3);
	    scrollPane1.setBounds(0, 0, 335, 317);
	    panel_.add(scrollPane1);
	    panel_.revalidate();
	    panel_.repaint();
 
	    JScrollPane scrollPane = new JScrollPane(panel_2);
	    scrollPane.setBounds(24, 61, 334, 229); 
	    panel.add(scrollPane);
	    panel.revalidate();
	    panel.repaint();
	    
	}

	public static void main(String[] args) throws UnknownHostException, IOException {
		new Mainform();
		

	}
	public static void readMatrix(BufferedReader reader) throws IOException {
	    int size = Integer.parseInt(reader.readLine());
	    Matrancoso = new int[size][size];
	    Matrandungluong = new int[size][size];

	    // Đọc ma trận Matrancoso
	    for (int i = 0; i < size; i++) {
	        String[] s = reader.readLine().trim().split("\\s+");
	        for (int j = 0; j < size; j++) {
	            Matrancoso[i][j] = Integer.parseInt(s[j]);
	        }
	    }
	 // Đọc ma trận Matrandungluong
	    for (int i = 0; i < size; i++) {
	        String[] s = reader.readLine().trim().split("\\s+");
	        for (int j = 0; j < size; j++) {
	            Matrandungluong[i][j] = Integer.parseInt(s[j]);
	        }
	    }
	}

	
  

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==btn) {
			
			JFileChooser file=new JFileChooser();
			file.setFileSelectionMode(file.FILES_ONLY);
			FileNameExtensionFilter filter=new FileNameExtensionFilter(".txt","txt");
			file.setFileFilter(filter);
			
			int returnValue=file.showOpenDialog(null);
			if(returnValue==file.APPROVE_OPTION) {
				
				String path=file.getSelectedFile().getAbsolutePath();
				 BufferedReader bfr=null;
				
				 try {
					    bfr = new BufferedReader(new FileReader(path));
		                readMatrix(bfr); 
		                show(Matrancoso); 
		                client_tcp=new Client_TCP(Matrancoso, Matrandungluong);
		                client_tcp.connectToServer();
		                Matranketqua=client_tcp.getMatranketqua();
		                Distra1=client_tcp.getDistra1();
		                
		            } catch (FileNotFoundException fnfEx) {
		                // Lỗi không tìm thấy file
		                JOptionPane.showMessageDialog(this, "Không thể tìm thấy file.", "Lỗi", JOptionPane.ERROR_MESSAGE);
		            } catch (IOException ioEx) {
		                // Lỗi đọc file
		                JOptionPane.showMessageDialog(this, "Lỗi khi đọc file. Vui lòng kiểm tra định dạng file.", "Lỗi", JOptionPane.ERROR_MESSAGE);
		            } catch (NumberFormatException nfEx) {
		                // Lỗi định dạng số không hợp lệ
		                JOptionPane.showMessageDialog(this, "File không đúng định dạng yêu cầu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
		            } catch (Exception ex) {
		                // Bắt bất kỳ lỗi nào khác
		                JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + ex.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
		            } finally {
		            	
						try {
							bfr.close();
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
		            }
				
			}
			
		}
		if(e.getSource()==ketthuc) {
			System.exit(0);
		}
		if(e.getSource()==ketqua) {
			if(Matrancoso==null || Matrandungluong==null) {
				return;
			}
			   
			    panel_3.updateData(Matrandungluong, Distra1);
			    panel_.revalidate();
			    panel_.repaint();
			    
		}
	   if(e.getSource()==bangsolieu) {
		 
		this.setVisible(false);
		Bangsolieu bsl=new Bangsolieu(Matranketqua);
		bsl.addWindowListener(new WindowAdapter() {
			public void windowClosed(WindowEvent e) {
				Mainform.this.setVisible(true);
			}
		
		});
	   }
	}
}