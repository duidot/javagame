import javax.swing.*;
import java.awt.*;

import java.awt.*; //per Container
import javax.swing.*; //per JFrame, JPanel,...

public class myFrame extends JFrame {

    public myFrame() {
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
        myFrame f = new myFrame();
    }
}