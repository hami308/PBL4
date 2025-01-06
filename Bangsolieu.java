package Client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.TextArea;
import java.awt.TextField;
import java.awt.Label;
import javax.swing.ImageIcon;

public class Bangsolieu extends JFrame {
	
	private JTable table;
	private static DefaultTableModel model ;
	
	private Object[][] Matranketqua;
	
	public static void main(String[] args) {
		
	}

	public Bangsolieu(Object Matranketqua[][]) {
		getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 11));
		this.Matranketqua=Matranketqua;
		
		
		setSize(829,593);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setLayout(null);
		getContentPane().setBackground(new Color(0, 153, 204));
		// Định nghĩa tên các cột
		String[] columnNames = {"i", "Line", "Ld(pkts/sec)", "C(kbps)", "mC(pkts/sec)", "T(msec)", "Trọng số"};

		// Tạo DefaultTableModel với tên cột và không có dòng dữ liệu
		 model = new DefaultTableModel(columnNames, 0);
		table = new JTable(model);

		JTableHeader header = table.getTableHeader();
		header.setBackground(Color.lightGray);
		header.setForeground(Color.RED);
		header.setFont(new Font("Arial", Font.BOLD, 15));
		
	     //Thêm JTable vào JScrollPane
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(51, 25, 704, 349);
		getContentPane().add(scrollPane);
		
		JLabel lblbngPhnTch = new JLabel("<html>Bảng phân tích mạng con sử dụng kích thước trung bình của gói là 800  bits.Luồng lưu thông ngược (BA,CB..) cũng giống luồng lưu thông thuận(AB,BC..)</html>");
		lblbngPhnTch.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblbngPhnTch.setBounds(51, 385, 694, 48);
		getContentPane().add(lblbngPhnTch);
		
		JLabel label = new JLabel("<html>Ld(pkts/sec): số gói tin truyền<br>\r\nC(kbps) : dung lượng trọng số trên mỗi đường<br>\r\nmC(pkts/sec) : số trung bình các gói/s trên mỗi đường<br>\r\nT(msec) : độ trễ tủng bình của mỗi đường </html>");
		label.setBackground(new Color(255, 255, 255));
		label.setForeground(new Color(0, 0, 153));
		label.setFont(new Font("Tahoma", Font.BOLD, 15));
		label.setBounds(51, 435, 419, 108);
		getContentPane().add(label);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon("D:\\workspace\\PBL4\\src\\Image\\Screenshot 2024-10-15 021019.png"));
		lblNewLabel.setBounds(0, 0, 813, 554);
		getContentPane().add(lblNewLabel);
		if(Matranketqua!=null) {
			Show(Matranketqua);
		}
		else {
			JOptionPane.showMessageDialog(this, "Không nhận được dữ liệu !","lỗi",JOptionPane.ERROR_MESSAGE);
		}
		setLocationRelativeTo(null);
		setVisible(true);
	}
	public static void Show(Object[][] Matranketqua) {		
	    for(Object[] row : Matranketqua) {
	    	model.addRow(row);
	    }
	}
}
