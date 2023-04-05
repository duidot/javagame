//**********************************************************

import java.io.*;

import java.awt.*;
import java.awt.event.*; //per utilizzare EventListner
import java.awt.image.*; //per utilizzare BufferedImage

import javax.sound.sampled.*;

import javax.swing.*; //per utilizzare JPanel

public class PongPanel extends JPanel implements KeyListener, Runnable {

	// definizione costanti
	static final int GAME_HEIGHT = 485;
	static final int GAME_WIDTH = (int) (GAME_HEIGHT * (1.6));
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);

	static final int PADDLE_WIDTH = 16;
	static final int PADDLE_HEIGHT = 100; // 

    static final int BALL_WIDTH = 14;
    static final int BALL_HEIGHT = 14;
	// remarks from Mr. Alcorn:
	// The problem you noticed about the paddle not going all the way to the top
	// was left in because without it good players could monopolize the game.
	// Our motto was "if you can't fix it call it a feature."
	static final int BORDER_OFFSET = 20;
	// il paddle non tocca i bordi superiore ed inferiore se OFFSET >0
	static final int DISTANZA = 20; // =0 i paddle sono sul bordo del campo;

	Thread gameThread; // Thread eseguibile
	BufferedImage buffer; // awg.image
	Graphics2D graphics;

	Paddle paddleR, paddleL; // istanza "paddle" dalla classe Paddle
    Ball ball;
	Score score;
	double velox = 1;
	double veloy = 0;

	PongPanel() { // costruttore
		this.setBackground(Color.cyan);

		
		// creo una istanza "paddle" dalla classe Paddle
		paddleR = new Paddle((GAME_WIDTH - DISTANZA - PADDLE_WIDTH), ((int)(GAME_HEIGHT / 2) - (int)(PADDLE_HEIGHT / 2)),
        PADDLE_WIDTH, PADDLE_HEIGHT, 1);

        paddleL = new Paddle((DISTANZA), ((int)(GAME_HEIGHT / 2) - (int)(PADDLE_HEIGHT / 2)),
        PADDLE_WIDTH, PADDLE_HEIGHT, 2);

		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		
		score.player1 = 0;
		score.player2 = 0;

		this.setFocusable(true);
		this.setPreferredSize(SCREEN_SIZE);

        ball = new Ball(GAME_WIDTH / 2, GAME_HEIGHT / 2, BALL_WIDTH, BALL_HEIGHT);
		// ----- aggiungi al Panel un "listner", un ascoltatore di eventi da tastiera
		// -----
		//
		// this.addKeyListener(new AL()); // extends KeyAdapter
		addKeyListener(this); // implements KeyListner
		//
		// --------------------------------------------------------------------------------

		gameThread = new Thread(this);
		gameThread.start();

		System.out.println(SCREEN_SIZE.getSize());
		System.out.println(GAME_WIDTH - DISTANZA - PADDLE_HEIGHT);
	}

	// ------------------------------- non toccare -------------------------------
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
      	Graphics2D g2 = (Graphics2D) g;

		buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		graphics = buffer.createGraphics();

		draw(graphics);

		g2.drawImage(buffer, 0, 0, this);

	}
	// ----------------------------------------------------------------------------

	public void draw(Graphics2D g) {

		paddleR.draw(g);
        paddleL.draw(g);

		score.draw(g);

        ball.draw(g);

		

		// disegna altri oggetti qui

		// the following line helps with animation ---------------------------
		Toolkit.getDefaultToolkit().sync();
		// This method ensures that the display is up-to-date.
		// It is useful for animation: timing the painting operation
		// should be performed by calling Toolkit.sync()
		// after each paint to ensure the drawing commands
		// are flushed to the graphics card. ---------------------------------
	}

	public void move() {

		

		paddleL.moveY(ball.y);
        paddleR.moveY(1);
		
		
		ball.move();
        
		
		
	}

	public void checkCollision() {

		// ---- stops paddles at window edges ----------------
		if (paddleL.x <= 0)
			paddleL.x = 0;

		if (paddleL.x >= GAME_WIDTH - PADDLE_WIDTH)
			paddleL.x = GAME_WIDTH - PADDLE_WIDTH;

        if (paddleL.y <= 0)
			paddleL.y = 0;

		if (paddleL.y >= GAME_HEIGHT - PADDLE_HEIGHT)
			paddleL.y = GAME_HEIGHT - PADDLE_HEIGHT;
			

        if (paddleR.x <= 0)
			paddleR.x = 0;

		if (paddleR.x >= GAME_WIDTH - PADDLE_WIDTH)
			paddleR.x = GAME_WIDTH - PADDLE_WIDTH;

        if (paddleR.y <= 0)
			paddleR.y = 0;

		if (paddleR.y >= GAME_HEIGHT - PADDLE_HEIGHT)
			paddleR.y = GAME_HEIGHT - PADDLE_HEIGHT;
        
		// check collision ball

		if (ball.y <= 0) {
			ball.dy = -ball.dy;
			playSound("boundary.wav");
		}
		if (ball.y >= GAME_HEIGHT - BALL_WIDTH) {
			ball.dy = -ball.dy;
			playSound("boundary.wav");
		}

		if (ball.x <= 0) {
			ball.dx = 0;
			ball.x = GAME_WIDTH / 2;
			ball.setDX(-velox);
			score.player2++;
			playSound("point.wav");
		}
		if (ball.x >= GAME_WIDTH - BALL_WIDTH) {
			ball.dx = 0;
			ball.x = GAME_WIDTH / 2;
			ball.setDX(velox);
			score.player1++;
			playSound("point.wav");
		}

		// la palla rimbalza quando tocca i margini destro e sinistro

		
		if (ball.intersects(paddleR)) {
			score.hits++;
			playSound("paddle.wav");
			if(score.hits >= 4 && score.hits < 12)
				velox = 1.6;
			if(score.hits >= 12)
				velox = 2;
			ball.setDX(ball.dx<0?-velox:velox);
			ball.dx = -ball.dx;
			double zone = paddleR.intersection(ball).y - paddleR.getCenterY();
			int segno = 1;
			if(zone < 0){
				zone = -zone;
				segno = -segno;
			}
			if(zone < 12)
				veloy = 0;
			if(zone >= 13 && zone < 26)
				veloy = 0.7;
			if(zone >= 27 && zone < 41)
				veloy = 1.4;
			if(zone >= 41)
				veloy = 2;

			veloy *= segno;
			ball.setDY(veloy);		

			
		}

		if (ball.intersects(paddleL)) {
			score.hits++;
			playSound("paddle.wav");
			if(score.hits >= 4 && score.hits < 12)
				velox = 1.6;
			if(score.hits >= 12)
				velox = 2;
			ball.setDX(ball.dx<0?-velox:velox);
			ball.dx = -ball.dx;
			double zone = paddleL.intersection(ball).y - paddleL.getCenterY();
			int segno = 1;
			if(zone < 0){
				zone = -zone;
				segno = -segno;
			}
			if(zone < 12)
				veloy = 0;
			if(zone >= 13 && zone < 26)
				veloy = 0.7;
			if(zone >= 27 && zone < 41)
				veloy = 1.4;
			if(zone >= 41)
				veloy = 2;

			veloy *= segno;
			ball.setDY(veloy);		

			
		}

		
		        
		// ---------------------------------------------------

	} // end checkCollision()

	public void run() { // game loop

		long lastTime = System.nanoTime();
		double amountOfFPS = 30.0; // frames in 1 second
		double duration = 1000000000 / amountOfFPS; // interval (time in ns) beetween 2 frames
		double delta = 0;

		while (true) { // per sempre
			long now = System.nanoTime();
			delta += (now - lastTime) / duration; // tempo trascorso è > intervallo? se sì, incrementa delta
			lastTime = now;

			
			if (delta >= 1) {

				move(); // calls move() method for paddle1...
				checkCollision(); // checks collisions of paddles and boundary
				
				// controllo dei tocchi ed incremento della velocità orizzontale
								
				

				repaint(); // is used to tell a component (gamepanel) to repaint itself.
				delta--;
			} // end if
		} // end while

	} // end run()

	/*
	 * public class AL extends KeyAdapter {
	 * // l’Adapter è un Listner che implementa tutte le funzioni {}
	 * //
	 * // KeyAdapter implementa i 3 metodi:
	 * // KeyPressed, KeyTyped, KeyReleased
	 * // di KeyLisner senza che l'utente debba ridefinirli tutti
	 * // l’utente implementa solo quelli che usa
	 * 
	 * // questo metodo SPOSTA il paddle quando il tasto è premuto
	 * public void keyPressed(KeyEvent e) {
	 * 
	 * // paddle.keyPressed(e);
	 * if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	 * paddle.setDeltaX(-1);
	 * }
	 * if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	 * paddle.setDeltaX(+1);
	 * }
	 * 
	 * }
	 * 
	 * // questo metodo FERMA il paddle rilasciando il tasto, azzerando il DeltaX
	 * public void keyReleased(KeyEvent e) {
	 * 
	 * // paddle.keyReleased(e);
	 * if (e.getKeyCode() == KeyEvent.VK_LEFT) {
	 * paddle.setDeltaX(0);
	 * }
	 * if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
	 * paddle.setDeltaX(0);
	 * }
	 * 
	 * }
	 * 
	 * } // end public class AL
	 */

	@Override
	public void keyPressed(KeyEvent e) {
		// gestisci l'evento di pressione del tasto
		// paddle.keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_W) {
			paddleL.setDeltaY(-1);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			paddleL.setDeltaY(+1);
		}
        if (e.getKeyCode() == KeyEvent.VK_UP) {
			paddleR.setDeltaY(-1);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			paddleR.setDeltaY(+1);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// gestisci l'evento di pressione del tasto
		// paddle.keyPressed(e);
		if (e.getKeyCode() == KeyEvent.VK_W) {
			paddleL.setDeltaY(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_S) {
			paddleL.setDeltaY(0);
		}

        if (e.getKeyCode() == KeyEvent.VK_UP) {
			paddleR.setDeltaY(0);
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			paddleR.setDeltaY(0);
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// gestisci l'evento di digitazione del tasto
	}

	public void playSound(String name) {
		try {
			Clip suono = AudioSystem.getClip();
			suono.open(AudioSystem.getAudioInputStream(getClass().getResource(name)));
			suono.start();
		}
		catch (Exception exc)
		{
			exc.printStackTrace(System.out);
		}
		
		} //end playSound()

} // end GamePanel