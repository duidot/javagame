//**********************************************************

import java.awt.*;
import javax.swing.*;

public class PongFrame extends JFrame {
	
	PongPanel panel;
		
	PongFrame() { //costruttore	
		
		
		panel = new PongPanel();
		
		//this.add(panel);  //finestra AWT
		this.getContentPane().add(panel); //finestra Swing
		
		
		this.setTitle(" Sliding Paddle ");
		this.setResizable(false);
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);

		System.out.println(this.getSize().toString());
		
	
	} //end costruttore

} 


//**********************************************************