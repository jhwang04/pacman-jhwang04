package jhwang04.pacman.entity.ghost;

import java.util.ArrayList;
import java.util.List;

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
		this.lastTile = new Tile(-1, -2, 0);
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
		
		
		
		List<Tile> possibilities = new ArrayList<Tile>();
		if(above.getType() != Tile.WALL && !tilesAreEqual(above, lastTile))
			possibilities.add(above);
		if(below.getType() != Tile.WALL && !tilesAreEqual(below, lastTile))
			possibilities.add(below);
		if(left.getType() != Tile.WALL && !tilesAreEqual(left, lastTile))
			possibilities.add(left);
		if(right.getType() != Tile.WALL && !tilesAreEqual(right, lastTile))
			possibilities.add(right);
		
		//System.out.println(above.equals(p.getTile(lastTile.getRow(), lastTile.getColumn())));
		//System.out.println(getTileY() + ", " + getTileX() + " ; lastTile = " + lastTile.getRow() + ", " + lastTile.getColumn());
		//System.out.println(possibilities.size());
		
		if(possibilities.size() == 1) { //when pacman is going down a straight path, he goes to the tile he did not come from
			if(tilesAreEqual(possibilities.get(0), above))
				setDirection("up");
			if(tilesAreEqual(possibilities.get(0), below))
				setDirection("down");
			if(tilesAreEqual(possibilities.get(0), right))
				setDirection("right");
			if(tilesAreEqual(possibilities.get(0), left))
				setDirection("left");
		}
		
		if(possibilities.size() >= 2) { //when pacman reaches a true decision point
			Tile choice = new Tile(100, 100, 0);
			for(Tile tile : possibilities) {
				if(tile.distanceTo(targetTile) <= choice.distanceTo(targetTile))
					choice = tile;
			}
			
			if(tilesAreEqual(right, choice))
				setDirection("right");
			else if(tilesAreEqual(left, choice))
				setDirection("left");
			else if(tilesAreEqual(below, choice))
				setDirection("down");
			else if(tilesAreEqual(above, choice))
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
	
	private boolean tilesAreEqual(Tile t1, Tile t2) {
		return (t1.getRow() == t2.getRow() && t1.getColumn() == t2.getColumn());
	}
}
