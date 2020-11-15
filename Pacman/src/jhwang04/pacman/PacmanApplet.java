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
	private List<Node> unexploredNodes;
	
	public static final int TITLE_SCREEN = 0;
	public static final int GAME_SCREEN = 1;
	public static final int[][] BOARD_TEMPLATE = new int[][] {
		{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
		{1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1},
		{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
		{1, 3, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 3, 1},
		{1, 2, 1, 1, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 2, 1, 1, 1, 1, 1, 2, 1, 1, 1, 1, 2, 1},
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
		//player = new Player(30, 170);
		ghost = new Ghost(30, 80);
		nodes = new ArrayList<Node>();
		unexploredNodes = new ArrayList<Node>();
		
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
			Tile pTile = node.getTile();
			for(Node neighbor : neighbors) {
				
				Node.connectNodes(node, neighbor, neighbors.indexOf(neighbor));
			}
			
			//System.out.println("Node done! row = " + node.getTile().getRow() + ", column = " + node.getTile().getColumn());
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
		
		//drawNodes();
		
		if(getTile(player.getTileY(), player.getTileX()) != getTile(ghost.getTileY(), ghost.getTileX())) {
			player.move(this);
			player.draw(this);
			
			
			//ghost.move(this);
			ghost.draw(this);
		}
		
		
		List<Node> path = pathFind(getTile(ghost.getTileY(), ghost.getTileX()), getTile(player.getTileY(), player.getTileX()));
		
		/*if(playerNode != null) {
			
		}*/
		//System.out.println("nodes.size = " + nodes.size());
		
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
		
		/*if(aboveNeighbor != null)
			neighbors.add(aboveNeighbor);
		if(rightNeighbor != null)
			neighbors.add(rightNeighbor);
		if(belowNeighbor != null)
			neighbors.add(belowNeighbor);
		if(leftNeighbor != null)
			neighbors.add(leftNeighbor);*/
		neighbors.add(aboveNeighbor);
		neighbors.add(rightNeighbor);
		neighbors.add(belowNeighbor);
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
		
		if(column >=28) {
			//System.out.println("went off the right");
			column = 0;
		} else if(column <= -1) {
			//System.out.println("went off the left");
			column = 27;
		}
		
		Tile tile = getTile(row, column);
		if(tile.getType() == Tile.WALL) {
			//System.out.println("escaped by hitting wall");
			return null;
		} else if(isNode(tile.getRow(), tile.getColumn())) {
			//System.out.println("escaped by finding node");
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
	
	private int getIndexOf(Node n) {
		return nodes.indexOf(n);
	}
	
	private void resetTentativeCosts() {
		for(Node node : nodes) {
			node.setTentativeCost(100000);
		}
	}
	
	private void resetPathTo() {
		for(Node node : nodes) {
			node.setPathTo(null);
		}
	}
	
	private void populateUnexploredNodes() {
		unexploredNodes.clear();
		for(Node node : nodes) {
			unexploredNodes.add(node);
		}
	}
	
	private Node getLeastNode() {
		Node smallestCost = unexploredNodes.get(0);
		for(Node node : unexploredNodes) {
			if(node.getTentativeCost() <= smallestCost.getTentativeCost())
				smallestCost = node;
		}
		return smallestCost;
	}
	
	private List<Node> pathFind(Tile startTile, Tile finishTile) {
		Node start = getNodeAt(startTile);
		Node finish = getNodeAt(finishTile);
		
		boolean wasFinishNull = false;
		if(finish == null) {
			wasFinishNull = true;
			finish = new Node(getTile(player.getTileY(), player.getTileX()));
			List<Node> neighbors = getNeighboringNodes(finish.getTile());
			for(Node neighbor : neighbors) {
				Node.connectNodes(finish, neighbor, neighbors.indexOf(neighbor));
			}
			nodes.add(finish);
		}
		
		//original pathFind starts here
		
		List<Node> path = new ArrayList<Node>();
		populateUnexploredNodes();
		start.setTentativeCost(0);
		while(unexploredNodes.size() > 0) {
			//System.out.println("unexplorednodes.size = " + unexploredNodes.size());
			Node currentNode = getLeastNode();
			//System.out.println("currentNode = " + nodes.indexOf(currentNode));
			unexploredNodes.remove(currentNode);
			
			if(currentNode.equals(finish))
				break;
			
			for(Node nextNode : currentNode.getConnections()) {
				//this if(nextNode == null) was added
				if(nextNode != null) {
					if(nextNode.getTentativeCost() > currentNode.getTentativeCost() + currentNode.distanceFrom(nextNode.getTile())) {
						nextNode.setTentativeCost((int) (currentNode.getTentativeCost() + currentNode.distanceFrom(nextNode.getTile())));
						//System.out.println("new cost = " + (int) (currentNode.getTentativeCost() + currentNode.distanceFrom(nextNode.getTile())));
						nextNode.setPathTo(currentNode);
					}
				}
			}
		}
		Node backtrackingNode = finish;
		while(!backtrackingNode.equals(start)) {
			path.add(backtrackingNode);
			backtrackingNode = backtrackingNode.getPathTo();
		}
		path.add(start);
		
		drawPath(path);
		
		//original pathfind ends here
		
		if(wasFinishNull == true) {
			nodes.remove(finish);
			if(finish.getConnections().get(0) != null)
				Node.connectNodes(finish.getConnections().get(0), finish.getConnections().get(2), 2);
			if(finish.getConnections().get(1) != null)
				Node.connectNodes(finish.getConnections().get(1), finish.getConnections().get(3), 3);
			Node.disconnectNodes(finish, finish.getConnections().get(0));
		}
		
		resetTentativeCosts();
		resetPathTo();
		
		return path;
	}
	
	private void drawPath(List<Node> path) {
		pushStyle();
		strokeWeight(2);
		stroke(255, 0, 0);
		for(int i = 1; i < path.size(); i++) {
			//System.out.println("i, i-1 = " + i + ", " + (i-1));
			//line(path.get(i).getTile().getColumn()*20 + 10, path.get(i).getTile().getRow()*20 + 60, path.get(i-1).getTile().getColumn()*20 + 10, path.get(i-1).getTile().getRow()*20 + 60);
			//System.out.println(path.get(i).getConnections().indexOf(path.get(i-1)));
	
			/*for(Node node : path) {
				System.out.print(node + ", ");
			}*/
			
			//System.out.println("\npath.size = " + path.size());
			drawPathInDirection(path.get(i).getTile().getRow(), path.get(i).getTile().getColumn(), path.get(i).getConnections().indexOf(path.get(i-1)), path.get(i-1));
		}
		popStyle();
	}
	
	private void drawPathInDirection(int row, int column, int direction, Node destination) {
		int oldRow = row;
		int oldColumn = column;
		
		if(direction == 0) { //up
			row--;
			line(oldColumn * 20 + 10, oldRow * 20 + 60, oldColumn * 20 + 10, oldRow * 20 + 40);
		} else if(direction == 1) { //right
			column++;
			line(oldColumn * 20 + 10, oldRow * 20 + 60, oldColumn * 20 + 30, oldRow * 20 + 60);
		} else if(direction == 2) { //down
			row++;
			line(oldColumn * 20 + 10, oldRow * 20 + 60, oldColumn * 20 + 10, oldRow * 20 + 80);
		} else if(direction == 3) { //left
			column--;
			line(oldColumn * 20 + 10, oldRow * 20 + 60, oldColumn * 20 - 10, oldRow * 20 + 60);
		}

		if(column >=28)
			column = 0;
		if(column <= -1)
			column = 27;
		
		
		//line(oldColumn*20 + 10, oldRow*20 + 60, column*20 + 10, row*20 + 60);
		
		//System.out.println("current location is (" + column + ", " + row + "), destination is (" + destination.getTile().getColumn() + ", " + destination.getTile().getRow() + ")");
		if(row != destination.getTile().getRow() || column != destination.getTile().getColumn())
			drawPathInDirection(row, column, direction, destination);
	}
	
}

