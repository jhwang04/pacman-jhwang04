package jhwang04.pacman.entity.ghost;

import jhwang04.pacman.PacmanApplet;
import jhwang04.pacman.Tile;
import jhwang04.pacman.entity.Entity;
import jhwang04.pacman.entity.Player;

public class Ghost extends Entity {
	private Tile targetTile;
	private Tile lastTile;
	
	public Ghost(double x, double y) {
		super(x, y, 200.0);
		this.targetTile = new Tile(-1, -1, 0);
		this.lastTile = new Tile(-1, -1, 0);
	}
	
	public void draw(PacmanApplet p) {
		p.pushStyle();
		p.pushMatrix();
		
		p.noStroke();
		p.fill(200, 200, 200);
		
		p.translate((float) getX(), (float) getY());
		p.ellipse(0, 0, 30, 30);
		
		p.popMatrix();
		p.popStyle();
	}
	
	public void move(PacmanApplet p) {
		Tile currentTile = p.getTile(getTileY(), getTileX());
		targetTile = p.getTile(p.getPlayer().getTileY(), p.getPlayer().getTileX());
		
		if(!currentTile.equals(lastTile)) {
			decideDirection(p);
			lastTile = currentTile;
		}
		
		
		
		
		if(getUp() == true) {
			setY(getY() - getSpeed()/60.0);
			setXInTile(10.0);
		} else if(getDown() == true) {
			setY(getY() + getSpeed()/60.0);
			setXInTile(10.0);
		} else if(getLeft() == true) {
			setX(getX() - getSpeed()/60.0);
			setYInTile(10.0);
		} else if(getRight() == true) {
			setX(getX() + getSpeed()/60.0);
			setYInTile(10.0);
		}
		
	}
	
	public void decideDirection(PacmanApplet p) {
		Player player = p.getPlayer();
		int tx = player.getTileX();
		int ty = player.getTileY();
		
		Tile above = p.getTile(getTileY() - 1, getTileX());
		Tile below = p.getTile(getTileY() + 1, getTileX());
		Tile left = p.getTile(getTileY(), getTileX() - 1);
		Tile right = p.getTile(getTileY(), getTileX() + 1);
		
		
		
		int openPaths = 0;
		if(above.getType() != Tile.WALL)
			openPaths++;
		if(below.getType() != Tile.WALL)
			openPaths++;
		if(left.getType() != Tile.WALL)
			openPaths++;
		if(right.getType() != Tile.WALL)
			openPaths++;
		
		//System.out.println(above.equals(p.getTile(lastTile.getRow(), lastTile.getColumn())));
		System.out.println(getTileY() + ", " + getTileX() + " ; lastTile = " + lastTile.getRow() + ", " + lastTile.getColumn());
		
		if(openPaths == 2) { //when pacman is going down a straight path, he goes to the tile he did not come from
			if(above.getType() != Tile.WALL && above.getRow() != lastTile.getRow() && above.getColumn() != lastTile.getColumn())
				setDirection("up");
			if(below.getType() != Tile.WALL && below.getRow() != lastTile.getRow() && below.getColumn() != lastTile.getColumn())
				setDirection("down");
			if(right.getType() != Tile.WALL && right.getRow() != lastTile.getRow() && right.getColumn() != lastTile.getColumn())
				setDirection("right");
			if(left.getType() != Tile.WALL && left.getRow() != lastTile.getRow() && left.getColumn() != lastTile.getColumn())
				setDirection("left");
		}
		if(openPaths >= 3) { //when pacman reaches a true decision point
			if(tx > getTileX() && p.getTile(getTileY(), getTileX() + 1).getType() != Tile.WALL )
				setDirection("right");
			else if(tx < getTileX() && p.getTile(getTileY(), getTileX() - 1).getType() != Tile.WALL )
				setDirection("left");
			else if(ty > getTileY() && p.getTile(getTileY() + 1, getTileX()).getType() != Tile.WALL )
				setDirection("down");
			else if(ty < getTileX() && p.getTile(getTileY() - 1, getTileX()).getType() != Tile.WALL )
				setDirection("up");
		}
		
		
		
	}
	
	private void setDirection(String direction) {
		setUp(false);
		setDown(false);
		setLeft(false);
		setRight(false);
		
		switch(direction) {
		
		case "left":
			setLeft(true);
			break;
		case "right":
			setRight(true);
			break;
		case "up":
			setUp(true);
			break;
		case "down":
			setDown(true);
			break;
		
		}
	}
}
