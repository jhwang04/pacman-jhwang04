package jhwang04.pacman;

public class Player {
	private double x, y;
	private int score;
	private double speed;
	private boolean up, down, left, right; //movement variables
	
	private static final int NONE = 0;
	private static final int UP = 1;
	private static final int RIGHT = 2;
	private static final int DOWN = 3;
	private static final int LEFT = 4;
	
	public Player(double x, double y) {
		this.x = x;
		this.y = y;
		up = down = left = right = false;
		speed = 200.0;
		this.score = 0;
	}
	
	public void draw(PacmanApplet p) {
		p.pushStyle();
		p.pushMatrix();
		p.translate((float) x, (float) y);
		p.fill(255, 255, 0);
		p.ellipse(0, 0, 30, 30);
		p.popStyle();
		p.popMatrix();
	}
	
	//these are booleans, of which keys are currently pressed
	public void move() {
		
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public int getTileX() {
		return (int) (x/20.0);
	}
	
	public int getTileY() {
		return (int) ((y - 50.0) / 20.0);
	}
	
	public double getXInTile() {
		return x%20.0;
	}
	
	public double getYInTile() {
		return (y-50.0)%20.0;
	}
	
	public void setXInTile(double x) {
		this.x = getTileX()*20.0 + x;
	}
	
	public void setYInTile(double y) {
		this.y = (getTileY()+50.0)*20.0 + y;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setUp(boolean b) {
		up = b;
	}
	
	public void setDown(boolean b) {
		down = b;
	}
	
	public void setLeft(boolean b) {
		left = b;
	}
	
	public void setRight(boolean b) {
		right = b;
	}
}
