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

	static final int PADDLE_WIDTH = 100;
	static final int PADDLE_HEIGHT = 10; // orizzontale

    static final int BALL_WIDTH = 10;
    static final int BALL_HEIGHT = 10;
	// remarks from Mr. Alcorn:
	// The problem you noticed about the paddle not going all the way to the top
	// was left in because without it good players could monopolize the game.
	// Our motto was "if you can't fix it call it a feature."
	static final int BORDER_OFFSET = 20;
	// il paddle non tocca i bordi superiore ed inferiore se OFFSET >0
	static final int DISTANZA = 20; // =0 i paddle sono sul bordo del campo;

	Thread gameThread; // Thread eseguibile
	BufferedImage buffer; // awg.image
	Graphics graphics;

	Paddle paddleR, paddleL; // istanza "paddle" dalla classe Paddle
    Ball ball;

	PongPanel() { // costruttore
		this.setBackground(Color.cyan);


		// creo una istanza "paddle" dalla classe Paddle
		paddleR = new Paddle((GAME_WIDTH - DISTANZA - PADDLE_HEIGHT), ((int)(GAME_HEIGHT / 2) - (int)(PADDLE_WIDTH / 2)),
        PADDLE_HEIGHT, PADDLE_WIDTH, 1);

        paddleL = new Paddle((DISTANZA), ((int)(GAME_HEIGHT / 2) - (int)(PADDLE_WIDTH / 2)),
        PADDLE_HEIGHT, PADDLE_WIDTH, 2);

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

		buffer = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
		graphics = buffer.getGraphics();

		draw(graphics);

		g.drawImage(buffer, 0, 0, this);

	}
	// ----------------------------------------------------------------------------

	public void draw(Graphics g) {

		paddleR.draw(g);
        paddleL.draw(g);

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

		paddleL.moveY();
        paddleR.moveY();

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

		if (paddleL.y >= GAME_HEIGHT - PADDLE_WIDTH)
			paddleL.y = GAME_HEIGHT - PADDLE_WIDTH;

        if (paddleR.x <= 0)
			paddleR.x = 0;

		if (paddleR.x >= GAME_WIDTH - PADDLE_WIDTH)
			paddleR.x = GAME_WIDTH - PADDLE_WIDTH;

        if (paddleR.y <= 0)
			paddleR.y = 0;

		if (paddleR.y >= GAME_HEIGHT - PADDLE_WIDTH)
			paddleR.y = GAME_HEIGHT - PADDLE_WIDTH;
        
		// check collision ball

		if (ball.y <= 0) {
			ball.dy = -ball.dy;
		}
		if (ball.y >= GAME_HEIGHT - BALL_WIDTH) {
			ball.dy = -ball.dy;
		}

		// la palla rimbalza quando tocca i margini destro e sinistro

		
		if (ball.intersects(paddleR)) {
			playSound("paddle.wav");
			ball.dx = -ball.dx;
		}

		if (ball.intersects(paddleL)) {
			playSound("paddle.wav");
			ball.dx = -ball.dx;
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