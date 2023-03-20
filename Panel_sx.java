import java.awt.*;
import javax.swing.*; //per utilizzare JPanel

public class Panel_sx extends JPanel {

    Rettangolo rettangolo = new Rettangolo();
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // qui invoco il metodo .draw(g) dei singoli oggetti
        rettangolo.draw(g);
    }

}