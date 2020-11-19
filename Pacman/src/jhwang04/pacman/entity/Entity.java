package jhwang04.pacman.entity;

import java.awt.event.KeyEvent;

import jhwang04.pacman.PacmanApplet;
import jhwang04.pacman.Tile;

public class Entity {
	private double x, y, speed;
	private boolean up, down, left, right; //movement variables
	
	public Entity(double x, double y, double speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
		up = false;
		down = false;
		left = false;
		right = false;
	}
	
	public void draw(PacmanApplet p) {	
		//different for each entity
	}
	
	public void move(PacmanApplet p) {
		//different for every entity
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getSpeed() {
		return speed;
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
	
	public boolean isTouching(Entity other) {
		/*if(other.getTileX() != getTileX())
			return false;
		if(other.getTileY() != getTileY())
			return false;
		if(Math.abs(other.getXInTile() - getXInTile()) > 14)
			return false;
		if(Math.abs(other.getYInTile() - getYInTile()) > 14)
			return false;*/
		if(Math.abs(other.getX() - getX()) > 14 || Math.abs(other.getY() - getY()) > 14)
			return false;
		return true;
	}
}
