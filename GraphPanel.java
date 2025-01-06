package Client;

import java.awt.*;


import javax.swing.JPanel;

class GraphPanel extends JPanel {

    private int[][] matrix;
    private int [] dis;
    private int n, d;
    private int R = 140; // Bán kính của vòng tròn để vẽ các đỉnh
    private int r = 15; // Bán kính của đỉnh

    public GraphPanel(int[][] matrix, int [] dis) {
        this.matrix = matrix;
        this.n = matrix.length;
        this.dis = dis;
        this.d = dis.length;
    }
    public void updateData(int[][] matrix, int[] dis) {
        this.matrix = matrix;
        this.dis = dis;
        this.d = dis.length;
        this.n = matrix.length;
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Tính toán tọa độ các đỉnh trên vòng tròn
        Point[] v = new Point[n];
        int X = getWidth() / 2;
        int Y = getHeight() / 2;

        for (int i = 0; i < n; i++) {
            double a = 2 * Math.PI * i / n;
            int x = (int) (X + R * Math.cos(a));
            int y = (int) (Y + R * Math.sin(a));
            v[i] = new Point(x, y);
        }
        g2d.setStroke(new BasicStroke(2)); // Độ rộng nét là 5px

        // Vẽ các cạnh và hiển thị dung lượng
        g2d.setColor(Color.BLACK);
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                if (matrix[i][j] > 0) { // Chỉ vẽ nếu có dung lượng
                    g2d.drawLine(v[i].x, v[i].y, v[j].x, v[j].y);

                    // Tính toán vị trí giữa hai đỉnh để vẽ dung lượng
                    int midX = (v[i].x + v[j].x) / 2;
                    int midY = (v[i].y + v[j].y) / 2;
                    g2d.drawString(String.valueOf(matrix[i][j]), midX, midY);
                }
            }
        }
        g2d.setColor(Color.RED);
 		if (dis[0] ==0) for (int i=0; i<d-1; i++) g2d.drawLine(v[dis[i]].x, v[dis[i]].y, v[dis[i+1]].x, v[dis[i+1]].y);
        // Vẽ các đỉnh
        g2d.setColor(Color.YELLOW);
        for (int i = 0; i < n; i++) {
            int x = v[i].x - r / 2;
            int y = v[i].y - r / 2;
            g2d.fillOval(x, y, r, r);

            // Vẽ nhãn cho các đỉnh (A, B, C, ...)
            g2d.setColor(Color.BLACK);
            g2d.drawString(String.valueOf((char) ('A' + i)), v[i].x - 10, v[i].y - 10);
            g2d.setColor(Color.YELLOW);
        }
        g2d.setColor(new Color(0,153,204));
        if (dis[0] ==0) for (int i = 0; i < d; i++) {
            int x = v[dis[i]].x - r / 2;
            int y = v[dis[i]].y - r / 2;
            g2d.fillOval(x, y, r, r);
        }
    }
}
