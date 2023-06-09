//**********************************************************
import java.awt.*;

public class Paddle extends Rectangle {

	int id;
	int dx;
	int dy;
	int paddleSpeed = 20;

	Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT, int id) {

		super(x, y, PADDLE_WIDTH, PADDLE_HEIGHT); // costruttore di Rectangle
		this.id = id;

	}

	public void setDeltaX(int xDirection) {
		dx = xDirection * paddleSpeed;
	}

	public void setDeltaY(int yDirection) {
		dy = yDirection * paddleSpeed;
	}

	public void moveX() {
		x = x + dx;
	}

	public void moveY(int dBall) {
		if(dBall == 1)
			y = y + dy;
		else
			y = dBall;
	}

	public void draw(Graphics2D g) {
		g.setColor(Color.white); // colore paddle
		g.fillRect(x, y, width, height);
	}

}