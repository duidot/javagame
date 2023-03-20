import javax.swing.*;
import java.awt.*;

import java.awt.*; //per Container
import javax.swing.*; //per JFrame, JPanel,...

public class MyFrame extends JFrame {

    public MyFrame() {
        JPanel p = new JPanel();
        JLabel l = new JLabel("Etichetta");
        JButton b = new JButton("Pulsante");
        Container c = this.getContentPane();
        c.add(p);
        p.add(l);
        p.add(b);
        this.setSize(300, 200);
        this.setVisible(true);
    }

    public static void main(String args[]) {
        MyFrame f = new MyFrame();
    }
}