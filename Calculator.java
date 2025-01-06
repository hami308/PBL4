package Server;

import java.util.Arrays;

public class Calculator {
	 // Thuộc tính lưu trữ ma trận đầu vào
    private int[][] Matrancoso;
    private int[][] Matrandungluong;
    private double[][] T;
    
    // Thuộc tính lưu trữ kết quả
    private Object[][] Matranketqua;
    private int[] Distra;

    // Constructor để khởi tạo các thuộc tính
    public Calculator(int[][] Matrancoso, int[][] Matrandungluong) {
        this.Matrancoso = Matrancoso;
        this.Matrandungluong = Matrandungluong;
        this.T = new double[Matrancoso.length][Matrancoso[0].length];
    }

   
    public static Object[][] computeFlow(int[][] Matrancoso, int[][] Matrandungluong,double[][] T) {
        char[] kitu = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
        int dem = 0;
        int n=0;
        double tong_LD =0;
        for(int i=0;i<Matrancoso.length;i++) {
        	for(int j=0;j<Matrancoso[i].length;j++) {
        		if(Matrancoso[i][j]!=0&&i<j) {
        			n++;
        			tong_LD+=Matrancoso[i][j];
        			
        		}
        	}
        }
        Object[][] Ketqua = new Object[n][7]; // Khởi tạo mảng kết quả
        int index = 0; // Đếm số lượng kết quả
         
        for (int i = 0; i < Matrancoso.length; i++) {
            for (int j = 0; j < Matrancoso[i].length; j++) {
                if (i < j && Matrancoso[i][j] != 0) {
                    Ketqua[index][0] = ++dem;
                    Ketqua[index][1] = kitu[i] + "" + kitu[j];
                    Ketqua[index][2] = Matrancoso[i][j];
                    Ketqua[index][3] = Matrandungluong[i][j];
                 
                    double value4 = (double) Matrandungluong[i][j] * 1000 / 800;
                    Ketqua[index][4] = value4; 
                    Ketqua[index][5] = (double) Math.round((1000.0 / (value4 - (double) Matrancoso[i][j]))); // Làm tròn không có số thập phân
                    Ketqua[index][6] = Math.round(((double) Matrancoso[i][j] / tong_LD) * 1000.0) / 1000.0; // Làm tròn tới 3 chữ số thập phân
                    T[i][j]=(double) Ketqua[index][5];
                    T[j][i]=(double) Ketqua[index][5];
                    index++;
                   
                }
                else {
                	T[i][j]=0;
                }
            }
        }
        return Ketqua; // Trả về mảng kết quả
    }
    
    public static int[] dijkstra(double[][] graph) {
        int n = graph.length;
        double[] dist = new double[n];  // Mảng lưu khoảng cách ngắn nhất từ nguồn đến các đỉnh
        int[] prev = new int[n];        // Mảng lưu đỉnh trước đó trong đường đi
        boolean[] visited = new boolean[n]; // Mảng đánh dấu các đỉnh đã được xử lý

        Arrays.fill(dist, Double.MAX_VALUE); // Ban đầu tất cả các khoảng cách đều là vô cực
        Arrays.fill(prev, -1);               // Các đỉnh trước chưa xác định
        dist[0] = 0;

        for (int i = 0; i < n - 1; i++) {
            // Tìm đỉnh có khoảng cách ngắn nhất chưa được xử lý
            int u = -1;
            for (int j = 0; j < n; j++) {
                if (!visited[j] && (u == -1 || dist[j] < dist[u])) {
                    u = j;
                }
            }

            if (u == -1 || dist[u] == Double.MAX_VALUE) {
                break; // Nếu không còn đỉnh nào để xử lý hoặc khoảng cách là vô hạn
            }

            visited[u] = true;

            // Cập nhật khoảng cách cho các đỉnh kề
            for (int v = 0; v < n; v++) {
                if (graph[u][v] > 0 && !visited[v]) { // Chỉ xét các đỉnh kề chưa được thăm
                    double newDist = dist[u] + graph[u][v];
                    if (newDist < dist[v]) {
                        dist[v] = newDist;
                        prev[v] = u; // Ghi lại đỉnh trước đó
                    }
                }
            }
        }

        // Tìm đường đi ngắn nhất từ đích ngược lại nguồn
        int[] path = new int[n];
        int count = 0;
        for (int at = n-1; at != -1; at = prev[at]) {
            path[count++] = at;
        }

        // Đảo ngược mảng để có đường đi từ nguồn đến đích
        int[] result = new int[count];
        for (int i = 0; i < count; i++) {
            result[i] = path[count - 1 - i];
        }

        return result;
    }
}
