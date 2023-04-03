//**********************************************************

import java.awt.*;

public class Score extends Rectangle {

	static int GAME_WIDTH;
	static int GAME_HEIGHT;
    static int hits;
	int player1;
	int player2;

	Score(int GAME_WIDTH, int GAME_HEIGHT){
		
		Score.GAME_WIDTH = GAME_WIDTH;
		Score.GAME_HEIGHT = GAME_HEIGHT;
	
	}

	public void draw(Graphics g) {

		//linea di metà campo
		for (int i=0; i<30; i++)
			g.drawLine(GAME_WIDTH/2, i*2*Math.round(GAME_HEIGHT/54), GAME_WIDTH/2, (i*2+1)*Math.round(GAME_HEIGHT/54)); 
		
		g.setColor(Color.white);
		g.setFont(new Font("Consolas",Font.PLAIN,60));
		//punteggio a due cifre: decine e unità
		g.drawString(String.valueOf(player1/10)+String.valueOf(player1%10), (GAME_WIDTH/2)-85, 50);
		g.drawString(String.valueOf(player2/10)+String.valueOf(player2%10), (GAME_WIDTH/2)+20, 50);

	}
	
} // end Class Score

//**********************************************************