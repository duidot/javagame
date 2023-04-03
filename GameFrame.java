//**********************************************************

import java.awt.*;
import javax.swing.*;

public class GameFrame extends JFrame {
	
	GamePanel panel;
		
	GameFrame() { //costruttore	
		
		
		panel = new GamePanel();
		
		//this.add(panel);  //finestra AWT
		this.getContentPane().add(panel); //finestra Swing
		
		
		this.setTitle(" Sliding Paddle ");
		this.setResizable(false);
		//this.setBackground(Color.red);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setSize(new Dimension(776, 485));
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);

		System.out.println(this.getSize().toString());
		
	
	} //end costruttore

} 


//**********************************************************