import java.awt.*;
import javax.swing.*; //per utilizzare JPanel

public class Panel_dx extends JPanel {

    Cerchio cerchio = new Cerchio();
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        //qui invoco il metodo .draw(g) dei singoli oggetti
        cerchio.draw(g);
    }

}
