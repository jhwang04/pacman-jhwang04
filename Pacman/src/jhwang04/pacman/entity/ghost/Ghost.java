package jhwang04.pacman.entity.ghost;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import jhwang04.pacman.PacmanApplet;
import jhwang04.pacman.Tile;
import jhwang04.pacman.entity.Entity;
import jhwang04.pacman.entity.Player;
import jhwang04.pacman.node.Node;

public class Ghost extends Entity {
	private Tile targetTile;
	private Tile lastTile;
	private Color pathColor;
	private Node lastNode;
	private Node twoNodesAgo;
	
	public Ghost(double x, double y) {
		this(x, y, new Color(200, 200, 200));
	}
	
	public Ghost(double x, double y, Color color) {
		super(x, y, 190.0);
		this.targetTile = new Tile(-1, -1, 0);
		this.lastTile = new Tile(-1, -2, 0);
		setDirection("left");
		lastNode = null;
		twoNodesAgo = null;
		pathColor = color;
	}
	
	public void draw(PacmanApplet p) {
		p.pushStyle();
		p.pushMatrix();
		
		p.noStroke();
		p.fill(pathColor.getRed(), pathColor.getGreen(), pathColor.getBlue());
		
		p.translate((float) getX(), (float) getY());
		p.ellipse(0, 0, 30, 30);
		
		p.popMatrix();
		
		//drawing the X at the target
		p.pushMatrix();
		
		p.translate((float) targetTile.getColumn()*20 + 10, targetTile.getRow()*20 + 60);
		p.strokeWeight(3);
		p.stroke(pathColor.getRed(), pathColor.getGreen(), pathColor.getBlue());
		
		p.line(-8, -8, 8, 8);
		p.line(8, -8, -8, 8);
		
		
		p.popMatrix();
		p.popStyle();
	}
	
	public void move(PacmanApplet p) {
		Tile currentTile = p.getTile(getTileY(), getTileX());
		//if(p.getNodeAt(currentTile) != null)
		//	lastNode = p.getNodeAt(currentTile);
		boolean startedInANode = (p.getNodeAt(currentTile) != null);
		
		
		Player player = p.getPlayer();
		if(targetTile.getRow() == -1)
			targetTile = p.getTile(p.getPlayer().getTileY(), p.getPlayer().getTileX());
		int tx = targetTile.getColumn();
		int ty = targetTile.getRow();
		List<Node> path = p.pathFind(p.getTile(getTileY(), getTileX()), targetTile, pathColor, lastNode);
		
		
		
		if(!currentTile.equals(lastTile))
			lastTile = currentTile;
		
		if(p.isNode(currentTile) && getXInTile() >= 5 && getXInTile() <= 15 && getYInTile() >= 5 && getYInTile() <= 15)
			decideDirection(p, path);
		
		
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
		
		if(currentTile.getColumn() >=28)
			setX(0*20 + 10);
		if(currentTile.getColumn() <= -1)
			setX(27*20 + 10);
		
		
		Tile newCurrentTile = p.getTile(getTileY(), getTileX());
		if(p.getNodeAt(newCurrentTile) == null && startedInANode) {
			//System.out.println("newCurrentTile = " + p.getNodeAt(newCurrentTile));
			lastNode = p.getNodeAt(currentTile);
		}
	}
	
	public void decideDirection(PacmanApplet p, List<Node> path) {
		Tile currentTile = p.getTile(getTileY(), getTileX());
		if(p.getNodeAt(currentTile) != null) {
			Tile nextNode;
			if(path.size() < 2)
				nextNode = path.get(0).getTile();
			else
				nextNode = path.get(path.size()-2).getTile();
			//int direction = p.getNeighboringNodes(currentTile).indexOf(path.get(path.size()-2));
			int direction = p.getDirectionToGo(currentTile, nextNode);
			//System.out.println(direction);
			/*if(nextNode.getRow() < getTileY())
				setDirection("up");
			else if(nextNode.getColumn() > getTileX())
				setDirection("right");
			else if(nextNode.getRow() > getTileY())
				setDirection("down");
			else if(nextNode.getColumn() < getTileX())
				setDirection("left");*/
			if(direction == 0)
				setDirection("up");
			if(direction == 1)
				setDirection("right");
			if(direction == 2)
				setDirection("down");
			if(direction == 3)
				setDirection("left");
			
		}
		
		/*Tile above = p.getTile(getTileY() - 1, getTileX());
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
		
		if(possibilities.size() >= 2) { //when ghost reaches a true decision point
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
		}*/
		
		
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
	
	private int getDirection() {
		if(getUp() == true)
			return 0;
		else if(getRight() == true)
			return 1;
		else if(getDown() == true)
			return 2;
		else if(getLeft() == true)
			return 3;
		else
			return -1;
	}
	
	private boolean tilesAreEqual(Tile t1, Tile t2) {
		return (t1.getRow() == t2.getRow() && t1.getColumn() == t2.getColumn());
	}
	
	public Tile getTargetTile() {
		return targetTile;
	}
	
	public void setTargetTile(Tile tile) {
		targetTile = tile;
	}
	
	public Tile furthestTileInDirection(PacmanApplet p, int direction, int maxDistance) {
		int distanceToWall = 0;
		int tempRow = p.getPlayer().getTileY();
		int tempColumn = p.getPlayer().getTileX();
		while(distanceToWall <= maxDistance && p.getTile(tempRow, tempColumn).getType() != Tile.WALL) {
			if(direction == 0)
				tempRow--;
			else if(direction == 1)
				tempColumn++;
			else if(direction == 2)
				tempRow++;
			else if(direction == 3)
				tempColumn--;
			distanceToWall++;
		}
		distanceToWall--;
		
		if(direction == 0)
			return new Tile(p.getPlayer().getTileY() - distanceToWall, p.getPlayer().getTileX(), Tile.AIR);
		else if(direction == 1) {
			return new Tile(p.getPlayer().getTileY(), (p.getPlayer().getTileX() + distanceToWall)%28, Tile.AIR);
		} else if(direction == 2)
			return new Tile(p.getPlayer().getTileY() + distanceToWall, p.getPlayer().getTileX(), Tile.AIR);
		else
			return new Tile(p.getPlayer().getTileY(), (p.getPlayer().getTileX() - distanceToWall + 28)%28, Tile.AIR);
		
		
	}
}
