import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {

       GamePanel panel;

       GameFrame() {  // costrut

              panel = new GamePanel();

               // this.add( nel); //finest

              this.getContentPane().add(panel); // finestra

              this.setTitle(" GIOCA JOUE ");
              this.setResizable(false);
              panel.setBackground(Color.black);
              this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              this.pack();
              this.setVisible(true);
       }
   
}
