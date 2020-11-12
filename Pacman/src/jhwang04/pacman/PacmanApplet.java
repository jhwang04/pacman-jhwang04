package jhwang04.pacman;


import java.util.ArrayList;
import java.util.List;

import jhwang04.pacman.entity.Player;
import jhwang04.pacman.entity.ghost.Ghost;
import jhwang04.pacman.node.Node;
import processing.core.PApplet;

public class PacmanApplet extends PApplet {
	private int level;
	private int screen;
	private Tile[][] tiles = new Tile[31][28];
	private Player player;
	private Ghost ghost;
	private List<Node> nodes;
	
	public static final int TITLE_SCREEN = 0;
	public static final int GAME_SCREEN = 1;
	public static final int[][] BOARD_TEMPLATE = new int[][] {
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
		{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1},
		{1, 3, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 3, 1},
		{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1},
		{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
		{1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
		{1, 2, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 2, 1},
		{1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
		{1, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1},
		{0, 0, 0, 0, 0, 1, 2, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 2, 1, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},
		{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1},
		{0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0},
		{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1},
		{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},
		{0, 0, 0, 0, 0, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 0, 0, 0, 0, 0},
		{1, 1, 1, 1, 1, 1, 2, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 2, 1, 1, 1, 1, 1, 1},
		{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
		{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
		{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
		{1, 3, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 0, 0, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 3, 1},
		{1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},
		{1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 2, 1, 1, 1},
		{1, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 1},
		{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
		{1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 1},
		{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
	};
	
	/*
	 * Notes for later:
	 * red ghost rgb = 255, 0, 0
	 * pink ghost rgb = 255, 184, 255
	 * blue ghost rgb = 0, 255, 255
	 * orange ghost rgb = 255, 184, 82
	 */
	
	//initializes all values
	public PacmanApplet() {
		level = 0;
		screen = TITLE_SCREEN;
		player = new Player(280, 520);
		ghost = new Ghost(30, 80);
		nodes = new ArrayList<Node>();
		
		//initializes the tiles
		for(int i = 0; i < 31; i++ ) {
			for(int j = 0; j < 28; j++) {
				tiles[i][j] = new Tile(i, j, BOARD_TEMPLATE[i][j]);
			}
		}
		
		//initializes the nodes
		for(int i = 0; i < 31; i++) {
			for(int j = 0; j < 28; j++) {
				if(isNode(i,j))
					nodes.add(new Node(tiles[i][j]));
			}
		}
		//connects the nodes
		for(Node node : nodes) {
			List<Node> neighbors = getNeighboringNodes(node.getTile());
			for(Node neighbor : neighbors)
				Node.connectNodes(node, neighbor);
			System.out.println("Node done! row = " + node.getTile().getRow() + ", column = " + node.getTile().getColumn());
		}
	}
	
	//sets the width and height to 800
	public void settings() {
		size(560, 800);
	}
	
	public void draw() {
		pushMatrix();
		scale((float) (width/560.0), (float) (height/800.0));
		background(0);
		
		if(keyPressed) {
			if(keyCode == UP) {
				if(player.getUp() == false)
					player.setDown(false);
				player.setUp(true);
			}
			if(keyCode == DOWN) {
				if(player.getDown() == false)
					player.setUp(false);
				player.setDown(true);
			}
			if(keyCode == RIGHT) {
				if(player.getRight() == false)
					player.setLeft(false);
				player.setRight(true);
			}
			if(keyCode == LEFT) {
				if(player.getLeft() == false)
					player.setRight(false);
				player.setLeft(true);
			}
		}
		
		drawTiles();
		
		drawNodes();
		
		if(getTile(player.getTileY(), player.getTileX()) != getTile(ghost.getTileY(), ghost.getTileX())) {
			player.move(this);
			player.draw(this);
			
			ghost.move(this);
			ghost.draw(this);
		}
		
		popMatrix();
	}
	
	/*public void mouseReleased() {
		player.move(this);
		ghost.move(this);
	}*/
	
	//helper method to draw the tiles
	private void drawTiles() {
		for(int i = 0; i < tiles.length; i++) {
			Tile[] column = tiles[i];
			for(int j = 0; j < column.length; j++)
				column[j].draw(this);
		}
	}
	
	//helper method to draw the nodes
	private void drawNodes() {
		for(Node node : nodes) {
			node.draw(this);
		}
	}
	
	public Tile[][] getTiles() {
		return tiles;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	
	
	//returns a blank tile at that location if it is out of range
	public Tile getTile(int row, int column) {
		Tile t;
		try {
			t = tiles[row][column];
		} catch(ArrayIndexOutOfBoundsException e) {
			t = new Tile(row, column, 0);
		}
		
		return t;
	}
	
	//returns all neighboring nodes
	public List<Node> getNeighboringNodes(Tile t) {
		List<Node> neighbors = new ArrayList<Node>();
		
		int column = t.getColumn();
		int row = t.getRow();
		
		Node aboveNeighbor = getNodeInDirection("up", row, column);
		Node belowNeighbor = getNodeInDirection("down", row, column);
		Node rightNeighbor = getNodeInDirection("right", row, column);
		Node leftNeighbor = getNodeInDirection("left", row, column);
		
		if(aboveNeighbor != null)
			neighbors.add(aboveNeighbor);
		if(belowNeighbor != null)
			neighbors.add(belowNeighbor);
		if(rightNeighbor != null)
			neighbors.add(rightNeighbor);
		if(leftNeighbor != null)
			neighbors.add(leftNeighbor);
		
		return neighbors;
	}
	
	private Node getNodeInDirection(String direction, int row, int column) {
		if(direction.equals("right"))
			column++;
		else if(direction.equals("left"))
			column--;
		else if(direction.equals("down"))
			row++;
		else if(direction.equals("up"))
			row--;
		
		Tile tile = getTile(row, column);
		if(tile.getType() == Tile.WALL) {
			System.out.println("escaped by hitting wall");
			return null;
		} else if(!isTileInRange(tile)) {
			System.out.println("escaped by going out of bounds");
			return null;
		} else if(isNode(tile.getRow(), tile.getColumn())) {
			System.out.println("escaped by finding node");
			return getNodeAt(tile);
		} else
			return getNodeInDirection(direction, row, column);
			
			
	}
	
	private boolean isNode(int row, int column) {
		Tile tile = getTile(row, column);
		Tile above = getTile(tile.getRow()-1, tile.getColumn());
		Tile below = getTile(tile.getRow()+1, tile.getColumn());
		Tile right = getTile(tile.getRow(), tile.getColumn()+1);
		Tile left = getTile(tile.getRow(), tile.getColumn()-1);
		
		if(tile.getType() != Tile.WALL && (right.getType() != Tile.WALL || left.getType() != Tile.WALL) && (above.getType() != Tile.WALL || below.getType() != Tile.WALL)) {
			//northeast, southeast, nortwest, southwest tiles
			Tile ne = getTile(tile.getRow()-1, tile.getColumn()+1);
			Tile nw = getTile(tile.getRow()-1, tile.getColumn()-1);
			Tile se = getTile(tile.getRow()+1, tile.getColumn()+1);
			Tile sw = getTile(tile.getRow()+1, tile.getColumn()-1);
			if(ne.getType()==Tile.WALL && se.getType()==Tile.WALL && nw.getType()==Tile.WALL && sw.getType()==Tile.WALL)
				return true;
		}
		return false;
	}
	
	private Node getNodeAt(Tile t) {
		for(Node node : nodes) {
			if(node.getTile().getRow() == t.getRow() && node.getTile().getColumn() == t.getColumn())
				return node;
		}
		return null;
	}
	
	private boolean isTileInRange(Tile t) {
		return(t.getColumn() > -1 && t.getColumn() < 29 && t.getRow() > -1 && t.getColumn() < 32);
	}
}

