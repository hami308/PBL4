package Client;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Client.Client_TCP;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;
import java.awt.Color;

public class ConnectForm extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	static Client_TCP client_tcp = new Client_TCP(null, null);
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ConnectForm frame = new ConnectForm();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	public ConnectForm() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Client");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Nhập cổng:");
		lblNewLabel.setForeground(new Color(0, 0, 255));
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 15));
		lblNewLabel.setBounds(93, 91, 118, 25);
		contentPane.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Tahoma", Font.PLAIN, 15));
		textField.setBounds(121, 126, 201, 31);
		contentPane.add(textField);
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Kết nối");
		btnNewButton.setBackground(new Color(204, 255, 204));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int port = Integer.parseInt(textField.getText());
					client_tcp.connectToServer(port);
					JOptionPane.showMessageDialog(null, "Đã kết nối đến Server trên cổng "+port+" !", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
					new Mainform(client_tcp);
					dispose();
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(null, "Không thể kết nối đến Server !", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập đúng cổng !", "Thông báo", JOptionPane.INFORMATION_MESSAGE); 
				}
			}
		});
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnNewButton.setBounds(83, 191, 106, 31);
		contentPane.add(btnNewButton);
		
		JButton btnng = new JButton("Đóng");
		btnng.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnng.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnng.setBackground(new Color(255, 153, 102));
		btnng.setBounds(256, 191, 106, 31);
		contentPane.add(btnng);
		
		JLabel lblNewLabel_1_1 = new JLabel("CHƯƠNG TRÌNH");
		lblNewLabel_1_1.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_1.setBackground(Color.WHITE);
		lblNewLabel_1_1.setBounds(93, 0, 165, 56);
		contentPane.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_2 = new JLabel("ĐỊNH LUỒNG CƠ SỞ");
		lblNewLabel_1_2.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel_1_2.setBackground(Color.WHITE);
		lblNewLabel_1_2.setBounds(183, 36, 243, 56);
		contentPane.add(lblNewLabel_1_2);
		
		JLabel lblNewLabel_2 = new JLabel("");
		lblNewLabel_2.setIcon(new ImageIcon(ConnectForm.class.getResource("/Image/z6188571212304_796993845c74df2663e63b0d30a0ae93.jpg")));
		lblNewLabel_2.setBounds(-12, 0, 482, 339);
		contentPane.add(lblNewLabel_2);
	}
}
