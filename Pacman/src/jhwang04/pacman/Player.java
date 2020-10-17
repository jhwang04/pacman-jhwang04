package jhwang04.pacman;

import java.awt.event.KeyEvent;

public class Player {
	private double x, y;
	private int score;
	private double speed;
	private boolean up, down, left, right; //movement variables
	
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
	
	public void move(PacmanApplet p) {
		//System.out.println("x = " + x + ", y = " + y + ", left = " + left + ", right = " + right + ", up = " + up + ", down = " + down);
		//int direction;
		Tile above = p.getTile(getTileY()-1, getTileX());
		Tile below = p.getTile(getTileY()+1, getTileX());
		Tile onRight = p.getTile(getTileY(), getTileX()+1);
		Tile onLeft = p.getTile(getTileY(), getTileX()-1);
		
		if(onRight.getType() == Tile.WALL && getXInTile() >= 10) {
			right = false;
			setXInTile(10.0);
		}
		if(onLeft.getType() == Tile.WALL && getXInTile() <= 10) {
			left = false;
			setXInTile(10.0);
		}
		if(above.getType() == Tile.WALL && getYInTile() <= 10) {
			up = false;
			setYInTile(10.0);
		}
		if(below.getType() == Tile.WALL && getYInTile() >= 10) {
			down = false;
			setYInTile(10.0);
		}
		
		
		
		if((up && right) || (up && left) || (down && right) || (down && left)) {
			up = false;
			down = false;
			left = false;
			right = false;
			
			switch(p.keyCode) {
			case KeyEvent.VK_UP:
				up = true;
				break;
			case KeyEvent.VK_DOWN:
				down = true;
				break;
			case KeyEvent.VK_RIGHT:
				right = true;
				break;
			case KeyEvent.VK_LEFT:
				left = true;
				break;
			}
		}
		
		if(up == true) {
			y -= speed/60.0;
			setXInTile(10.0);
		}
		if(down == true) {
			y += speed/60.0;
			setXInTile(10.0);
		}
		if(right == true) {
			x += speed/60.0;
			setYInTile(10.0);
		}
		if(left == true) {
			x -= speed/60.0;
			setYInTile(10.0);
		}
		
		
		//looping mechanic
		if(getTileX() >= 28)
			setTileX(-1);
		if(getTileY() >= 31)
			setTileY(-1);
		if(getTileX() < 0)
			setTileX(28);
		if(getTileY() < 0)
			setTileY(31);
		
		//eating the pellets
		int tileType = p.getTile(getTileY(), getTileX()).getType();
		if(tileType == Tile.PELLET || tileType == Tile.POWER_PELLET)
			p.getTile(getTileY(), getTileX()).setType(Tile.AIR);
			
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
	
	public void setTileX(int tx) {
		x = tx*20 + getXInTile();
	}
	
	public void setTileY(int ty) {
		y = ty*20 + getYInTile() + 50;
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
		this.y = (getTileY()*20.0) + 50 + y;
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
	
	public boolean getUp() {
		return up;
	}
	
	public boolean getDown() {
		return down;
	}
	
	public boolean getRight() {
		return right;
	}
	
	public boolean getLeft() {
		return left;
	}
}
