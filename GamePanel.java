import java.util.*; //per utilizzare la classe Random
import java.awt.*;
import java.awt.image.*; //per utilizzare BufferedImage
import javax.swing.*; //per utilizzare JPanel

public class GamePanel extends JPanel implements Runnable {

    // definizione costanti
    static final int GAME_WIDTH = 800;
    static final int GAME_HEIGHT = 600;
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int BALL_DIAMETER = 20; // palla rotonda

    // --------------------------- non toccare -------------------------------
    Thread gameThread; // Thread eseguibile
    Graphics graphics;
    // -----------------------------------------------------------------------

    // ---- qui si dichiarano gli oggetti che verranno istanziati -------
    Random random; // istanza per ottenere numeri casuali
    Ball ball; // istanza "ball" dalla classe Ball
    // ------------------------------------------------------------------

    public GamePanel() { // costruttore

        // istanzio gli oggetti
        random = new Random();

        // creo una istanza "ball" dalla classe Ball al centro dello schermo
        // ma ad una altezza casuale
        ball = new Ball((GAME_WIDTH / 2) - (BALL_DIAMETER / 2),
                random.nextInt(GAME_HEIGHT - BALL_DIAMETER),
                BALL_DIAMETER,
                BALL_DIAMETER);

        // --------------------------- non toccare ---------------------------
        this.setFocusable(true);
        this.setPreferredSize(SCREEN_SIZE);
        gameThread = new Thread(this);
        gameThread.start();
        // -------------------------------------------------------------------

    } // end costruttore

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        ball.draw(g);

        // the following line helps with animation ---------------------------
        Toolkit.getDefaultToolkit().sync();
        // This method ensures that the display is up-to-date.
        // It is useful for animation: timing the painting operation
        // should be performed by calling Toolkit.sync()
        // after each paint to ensure the drawing commands
        // are flushed to the graphics card. ————————————————

    }

    public void move() {

        // qui invoco l'aggiornamento della posizione degli oggetti grafici
        ball.move();
    }

    public void checkCollision() {

        // ----- la palla rimbalza quando tocca i margini superiore ed inferiore
        if (ball.y <= 0) {
            ball.dy = -ball.dy;
        }
        if (ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball.dy = -ball.dy;
        }

        // la palla rimbalza quando tocca i margini destro e sinistro
        if (ball.x <= 0) {
            ball.dx = -ball.dx;
        }
        if (ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            ball.dx = -ball.dx;
        }

    } // end checkCollision()
      // -----------------------------------------------------

    public void run() { // game loop

        long lastTime = System.nanoTime();
        double FPS = 30.0; // picture frames in 1 secondo
        double duration = 1000000000 / FPS; // intervallo in ns tra 2 frame
        double delta = 0;

        while (true) { // per sempre
            long now = System.nanoTime();
            // il tempo trascorso è > intervallo?
            // se sì, devo disegnare nuovo frame
            delta += (now - lastTime) / duration;
            lastTime = now;

            if (delta >= 1) {
                move();
                checkCollision();
                repaint(); // is used to tell a component to repaint itself.
                delta--;
            }
        } // end while

    } // end run()

} // end GamePanel