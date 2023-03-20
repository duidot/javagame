import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class MyFrame extends JFrame {
    public MyFrame() {
        Panel_sx panel1 = new Panel_sx();
        Panel_dx panel2 = new Panel_dx();
        setTitle("JFrame con due JPanel");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        getContentPane().setLayout(null);
        panel1.setBounds(0, 0, 200, 400);
        panel2.setBounds(200, 0, 400, 400);
        getContentPane().add(panel1);
        getContentPane().add(panel2);

        setVisible(true);
    }
}