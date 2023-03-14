import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class MyFrame extends JFrame {

    public MyFrame() {

        setTitle("Finestra con due quadri");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLUE);
                g.fillRect(0, 0, 100, 100);
            }
        };

        JPanel panel2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.RED);
                g.fillOval(0, 0, 100, 100);
            }
        };
        
        getContentPane().setLayout(null);
        panel1.setBounds(0, 0, 200, 400);
        panel2.setBounds(200, 0, 400, 400);
        getContentPane().add(panel1);
        getContentPane().add(panel2);
        
        setVisible(true);
    }
    
}

