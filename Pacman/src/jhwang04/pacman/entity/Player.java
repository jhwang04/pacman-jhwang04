package jhwang04.pacman.entity;

import java.awt.event.KeyEvent;

import jhwang04.pacman.PacmanApplet;
import jhwang04.pacman.Tile;

public class Player extends Entity {
	private int movementDirection;
	private int animationStage;
	
	public Player(double x, double y) {
		super(x, y, 200*0.6);
		movementDirection = 0;
		animationStage = 0;
	}
	
	public void draw(PacmanApplet p) {
		int time = p.getTime();
		int openness = 0;
		if(animationStage < 6)
			openness = 25 * (animationStage);
		else
			openness = 25 * (12 - animationStage);
		
		//animationStage = (animationStage + 1)%12;
		
			
		/*p.pushStyle();
		p.pushMatrix();
		p.translate((float) getX(), (float) getY());
		p.fill(255, 255, 0);
		p.ellipse(0, 0, 30, 30);
		p.popStyle();
		p.popMatrix();*/
		p.pushStyle();
		p.pushMatrix();
		p.translate((float) getX(), (float) getY());
		p.fill(255, 255, 0);
		p.rotate((float) ((movementDirection-1) / 4.0 * Math.PI * 2));
		p.arc(0, 0, 30, 30, (float) ((openness/2)*Math.PI/180), (float) (Math.PI * 2 - (openness/2 * Math.PI / 180)));
		p.popMatrix();
		p.popStyle();
	}
	
	public void move(PacmanApplet p) {
		//System.out.println("x = " + x + ", y = " + y + ", left = " + left + ", right = " + right + ", up = " + up + ", down = " + down);
		//int direction;
		//System.out.println(getUp() + ", " + getRight() + ", " + getDown() + ", " + getLeft());
		Tile above = p.getTile(getTileY()-1, getTileX());
		Tile below = p.getTile(getTileY()+1, getTileX());
		Tile onRight = p.getTile(getTileY(), getTileX()+1);
		Tile onLeft = p.getTile(getTileY(), getTileX()-1);
		
		if(onRight.getType() == Tile.WALL && getXInTile() >= 10) {
			setRight(false);
			setXInTile(10.0);
		}
		if(onLeft.getType() == Tile.WALL && getXInTile() <= 10) {
			setLeft(false);
			setXInTile(10.0);
		}
		if(above.getType() == Tile.WALL && getYInTile() <= 10) {
			setUp(false);
			setYInTile(10.0);
		}
		if(below.getType() == Tile.WALL && getYInTile() >= 10) {
			setDown(false);
			setYInTile(10.0);
		}
		
		
		
		if((getUp() && getRight()) || (getUp() && getLeft()) || (getDown() && getRight()) || (getDown() && getLeft())) {
			setUp(false);
			setDown(false);
			setLeft(false);
			setRight(false);
			
			switch(p.keyCode) {
			case KeyEvent.VK_UP:
				setUp(true);
				break;
			case KeyEvent.VK_DOWN:
				setDown(true);
				break;
			case KeyEvent.VK_RIGHT:
				setRight(true);
				break;
			case KeyEvent.VK_LEFT:
				setLeft(true);
				break;
			}
		}
		
		if(getUp() == true) {
			setY(getY() - getSpeed()/60.0);
			movementDirection = 0;
			setXInTile(10.0);
			animationStage = (animationStage + 1)%12;
		}
		if(getDown() == true) {
			setY(getY() + getSpeed()/60.0);
			movementDirection = 2;
			setXInTile(10.0);
			animationStage = (animationStage + 1)%12;
		}
		if(getRight() == true) {
			setX(getX() + getSpeed()/60.0);
			movementDirection = 1;
			setYInTile(10.0);
			animationStage = (animationStage + 1)%12;
		}
		if(getLeft() == true) {
			setX(getX() - getSpeed()/60.0);
			movementDirection = 3;
			setYInTile(10.0);
			animationStage = (animationStage + 1)%12;
		}
		
		//looping mechanic
		if(getTileX() > 27)
			setTileX(0);
		if(getTileY() >= 31)
			setTileY(-1);
		if(getTileX() < 0)
			setTileX(27);
		if(getTileY() < 0)
			setTileY(31);
		
		//eating the pellets
		int tileType = p.getTile(getTileY(), getTileX()).getType();
		if(tileType == Tile.PELLET || tileType == Tile.POWER_PELLET) {
			if(tileType == Tile.POWER_PELLET) {
				p.addPoints(50);
				p.eatPowerPellet();
			} else if(tileType == Tile.PELLET)
				p.addPoints(10);
			p.getTile(getTileY(), getTileX()).setType(Tile.AIR);
		}
			
	}
	
	public int getMovementDirection() {
		return movementDirection;
	}
}
