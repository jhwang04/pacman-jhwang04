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
	private int lastMode;
	private int mode;
	public static final int FROZEN_MODE = 0;
	public static final int CHASE_MODE = 1;
	public static final int RUN_MODE = 2;
	public static final int RETURN_MODE = 3;
	public static final double DEFAULT_GHOST_SPEED = 190*0.6;
	public static final double RETURN_GHOST_SPEED = 400*0.6;
	public static final double RUN_GHOST_SPEED = 160*0.6;
	
	public Ghost(double x, double y) {
		this(x, y, new Color(200, 200, 200));
	}
	
	public Ghost(double x, double y, Color color) {
		super(x, y, DEFAULT_GHOST_SPEED);
		this.targetTile = new Tile(-1, -1, 0);
		this.lastTile = new Tile(-1, -2, 0);
		setDirection("left");
		lastNode = null;
		mode = CHASE_MODE;
		pathColor = color;
	}
	
	public void draw(PacmanApplet p) {
		p.pushStyle();
		p.pushMatrix();
		
		p.noStroke();
		p.fill(pathColor.getRed(), pathColor.getGreen(), pathColor.getBlue());
		
		if(mode == RUN_MODE) {
			if(p.getGhostRunningTime() % 50 < 25 && p.getGhostRunningTime() < 300)
				p.fill(255, 255, 255);
			else
				p.fill(0, 0, 255);
		} else if(mode == RETURN_MODE)
			p.fill(220, 220, 220);
		
		p.translate((float) getX(), (float) getY());
		p.ellipse(0, 0, 30, 30);
		
		p.popMatrix();
		
		//drawing the X at the target
		p.pushMatrix();
		
		p.translate((float) targetTile.getColumn()*20 + 10, targetTile.getRow()*20 + 60);
		p.strokeWeight(3);
		p.stroke(pathColor.getRed(), pathColor.getGreen(), pathColor.getBlue());
		
		if(mode == CHASE_MODE) {
			p.line(-8, -8, 8, 8);
			p.line(8, -8, -8, 8);
		}
		
		
		p.popMatrix();
		p.popStyle();
	}
	
	public void move(PacmanApplet p) {
		Tile currentTile = p.getTile(getTileY(), getTileX());
		//if(p.getNodeAt(currentTile) != null)
		//	lastNode = p.getNodeAt(currentTile);
		boolean startedInANode = (p.getNodeAt(currentTile) != null);
		
		if(mode == CHASE_MODE || mode == RUN_MODE || mode == RETURN_MODE) {
			Player player = p.getPlayer();
			if(targetTile.getRow() == -1)
				targetTile = p.getTile(p.getPlayer().getTileY(), p.getPlayer().getTileX());
			int tx = targetTile.getColumn();
			int ty = targetTile.getRow();
			
			if(mode == RETURN_MODE) {
				targetTile = p.getTile(11, 13);
				if(getTileX() == targetTile.getColumn() && getTileY() == targetTile.getRow())
					setMode(CHASE_MODE);
			}
			
			List<Node> path;
			if(mode == CHASE_MODE || mode == RETURN_MODE)
				path = p.pathFind(p.getTile(getTileY(), getTileX()), targetTile, pathColor, lastNode);
			else {
				path = new ArrayList<Node>();
				path.add(null);
			}
			if(p.isNode(currentTile) && getXInTile() >= 5 && getXInTile() <= 15 && getYInTile() >= 5 && getYInTile() <= 15 && !currentTile.equals(lastTile))
				decideDirection(p, path);
		}
		if(!currentTile.equals(lastTile) && getXInTile() >= 5 && getXInTile() <= 15 && getYInTile() >= 5 && getYInTile() <= 15)
			lastTile = currentTile;
		
		
		
		
		
		
		
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
		
		if(currentTile.getColumn() >=28) {
			setX(0*20 + 10);
			//System.out.println("" + this.getClass().getSimpleName() + " looped from right side to left");
		}
		if(currentTile.getColumn() <= -1) {
			setX(27*20 + 10);
			//System.out.println("" + this.getClass().getSimpleName() + " looped from left side to right");
		}
		
		Tile newCurrentTile = p.getTile(getTileY(), getTileX());
		if(p.getNodeAt(newCurrentTile) == null && startedInANode) {
			//System.out.println("newCurrentTile = " + p.getNodeAt(newCurrentTile));
			lastNode = p.getNodeAt(currentTile);
		}
	}
	
	public void decideDirection(PacmanApplet p, List<Node> path) {
		Tile currentTile = p.getTile(getTileY(), getTileX());
		if(p.getNodeAt(currentTile) != null) {
			int direction = -1;
			
			if(mode == CHASE_MODE || mode == RETURN_MODE) {
				Tile nextNode;
				if(path.size() < 2)
					nextNode = path.get(0).getTile();
				else
					nextNode = path.get(path.size()-2).getTile();
				direction = p.getDirectionToGo(currentTile, nextNode);
				
				if(direction == -1) {
					direction = oldDirectionPickingAlgorithm(p, currentTile);
				}
			}
			
			if(mode == RUN_MODE)
				direction = randomDirection(p);
			
			if(mode != FROZEN_MODE) {
				if(direction == 0)
					setDirection("up");
				if(direction == 1)
					setDirection("right");
				if(direction == 2)
					setDirection("down");
				if(direction == 3)
					setDirection("left");
			}
			
		}
		
		
	}
	
	public void setDirection(int d) {
		if(d == 0)
			setDirection("up");
		else if(d == 1)
			setDirection("right");
		else if(d == 2)
			setDirection("down");
		else if(d == 3)
			setDirection("left");
	}
	
	public void setDirection(String direction) {
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
	
	public int getDirection() {
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
	
	private int oldDirectionPickingAlgorithm(PacmanApplet p, Tile currentTile) {
		Tile above = p.getTile(getTileY() - 1, getTileX());
		Tile below = p.getTile(getTileY() + 1, getTileX());
		Tile left = p.getTile(getTileY(), getTileX() - 1);
		Tile right = p.getTile(getTileY(), getTileX() + 1);
		
		
		
		List<Tile> possibilities = new ArrayList<Tile>();
		/*if(above.getType() != Tile.WALL && !tilesAreEqual(above, lastTile))
			possibilities.add(above);
		if(right.getType() != Tile.WALL && !tilesAreEqual(right, lastTile))
			possibilities.add(right);
		if(below.getType() != Tile.WALL && !tilesAreEqual(below, lastTile))
			possibilities.add(below);
		if(left.getType() != Tile.WALL && !tilesAreEqual(left, lastTile))
			possibilities.add(left);*/
		possibilities.add(above);
		possibilities.add(right);
		possibilities.add(below);
		possibilities.add(left);
		
		//int impossibleDirection = (getDirection()+2)%4;
		int impossibleDirection = p.getDirectionToGo(currentTile, lastNode.getTile());
		//System.out.println(impossibleDirection);
		int newDirection = -1;
		if(possibilities.get(0).getType() != Tile.WALL && impossibleDirection != 0)
			newDirection = 0;
		else if(possibilities.get(1).getType() != Tile.WALL && impossibleDirection != 1)
			newDirection = 1;
		else if(possibilities.get(2).getType() != Tile.WALL && impossibleDirection != 2)
			newDirection = 2;
		else
			newDirection = 3;
	
		return newDirection;
	}
	
	private int randomDirection(PacmanApplet p) {
		Tile above = p.getTile(getTileY() - 1, getTileX());
		Tile below = p.getTile(getTileY() + 1, getTileX());
		Tile left = p.getTile(getTileY(), getTileX() - 1);
		Tile right = p.getTile(getTileY(), getTileX() + 1);
		
		List<Tile> possibilities = new ArrayList<Tile>();
		if(above.getType() != Tile.WALL && !tilesAreEqual(above, lastTile))
			possibilities.add(above);
		if(right.getType() != Tile.WALL && !tilesAreEqual(right, lastTile))
			possibilities.add(right);
		if(below.getType() != Tile.WALL && !tilesAreEqual(below, lastTile))
			possibilities.add(below);
		if(left.getType() != Tile.WALL && !tilesAreEqual(left, lastTile))
			possibilities.add(left);
		
		int decision = (int) (Math.random() * possibilities.size());
		if(possibilities.get(decision).equals(above))
			return 0;
		else if(possibilities.get(decision).equals(right))
			return 1;
		else if(possibilities.get(decision).equals(below))
			return 2;
		else if(possibilities.get(decision).equals(left))
			return 3;
		return decision;
		
	}
	
	public int getMode() {
		return mode;
	}
	
	public void setMode(int mode) {
		this.mode = mode;
		if(mode == RETURN_MODE)
			setSpeed(RETURN_GHOST_SPEED);
		else if(mode == RUN_MODE)
			setSpeed(RUN_GHOST_SPEED);
		else
			setSpeed(DEFAULT_GHOST_SPEED);
	}
	
	public void setLastTile(Tile t) {
		lastTile = t;
	}
}
