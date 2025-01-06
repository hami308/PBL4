package Server;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

public class Server_TCP extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField port;
    private JTextArea textArea;
    private JTable table;
    private DefaultTableModel tableModel;

    JButton start = new JButton("START");
    JButton stop = new JButton("STOP");
    ServerSocket sv;
    boolean status;
    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);

    public static void main(String[] args) {
        Server_TCP frame = new Server_TCP();
        frame.setVisible(true);
    }

    public Server_TCP() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 761, 604);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(null);

        port = new JTextField();
        port.setBounds(307, 48, 156, 34);
        contentPane.add(port);
        port.setColumns(10);

        start.addActionListener(this);
        start.setFont(new Font("Tahoma", Font.PLAIN, 13));
        start.setForeground(new Color(0, 128, 0));
        start.setBounds(473, 48, 77, 33);
        contentPane.add(start);

        JLabel lblNewLabel = new JLabel("PORT");
        lblNewLabel.setBackground(new Color(255, 255, 255));
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setFont(new Font("Mongolian Baiti", Font.BOLD, 24));
        lblNewLabel.setBounds(233, 49, 64, 33);
        contentPane.add(lblNewLabel);

        stop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (sv != null && !sv.isClosed()) {
                        sv.close();
                        textArea.append("Đã đóng Server!\n");
                        status = false;
                    } else {
                        textArea.append("SocketServer chưa được khởi tạo!\n");
                    }
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        stop.setForeground(new Color(255, 0, 0));
        stop.setFont(new Font("Tahoma", Font.PLAIN, 13));
        stop.setBounds(560, 48, 77, 33);
        contentPane.add(stop);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setIcon(new ImageIcon("D:\\workspace\\PBL4\\src\\Image\\Screenshot 2025-01-02 191921.png"));
        lblNewLabel_1.setBounds(20, 11, 704, 103);
        contentPane.add(lblNewLabel_1);

        tabbedPane.setBounds(20, 125, 704, 429);
        contentPane.add(tabbedPane);

        // Tab 1: Thông báo (TextArea)
        textArea = new JTextArea();
        textArea.setForeground(new Color(0, 0, 160));
        textArea.setFont(new Font("Courier New", Font.PLAIN, 18));
        textArea.setEditable(false);
        JScrollPane scrollPane1 = new JScrollPane(textArea);
        tabbedPane.addTab("Thông báo", scrollPane1);

        // Tab 2: Danh sách (JTable)
        tableModel = new DefaultTableModel(new Object[]{"IP","Thời gian kết nối ","Trạng thái"}, 0);
        table = new JTable(tableModel);
        table.setEnabled(false);
        JScrollPane scrollPane2 = new JScrollPane(table);
        tabbedPane.addTab("Danh sách", scrollPane2);
        
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == start) {
            try {
            	 // Kiểm tra xem server đã được khởi tạo và còn mở chưa
                if (sv != null && !sv.isClosed()) {
                    textArea.append("Server đã chạy trên cổng " + port.getText() + "\n");
                    return; // Nếu server đã chạy, không tạo lại server
                }
            	
                int PORT = Integer.parseInt(port.getText().trim());
                status = true;
                new Thread(() -> {
                    try {
                        sv = new ServerSocket(PORT);
                        textArea.append("Server đang chạy trên cổng: " + PORT + "\n");
                        try {
                            while (status) {
                            	if(!sv.isClosed()) {
                                Socket socket = sv.accept();
                                String host = socket.getInetAddress().getHostAddress();
                                textArea.append("Client: " + host + " vừa kết nối đến Server.\n");
                                String connectTime = new Date().toString(); // Lấy thời gian hiện tại
                                tableModel.addRow(new Object[]{host, connectTime, "Đang kết nối"});   
                                int chisohang = tableModel.getRowCount() - 1;
                                new ClientHandler(socket, textArea, tableModel,chisohang);}
                            }
                        } finally {
                            sv.close();
                        }
                    } catch (IOException e1) {
                    	 if (!sv.isClosed()) {
                             textArea.append("Lỗi khi tạo ServerSocket: " + e1.getMessage() + "\n");
                         }
                    }
                }).start();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Vui lòng nhập một số hợp lệ vào ô port.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
