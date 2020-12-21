package jhwang04.pacman;


import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import jhwang04.pacman.entity.Player;
import jhwang04.pacman.entity.ghost.BlueGhost;
import jhwang04.pacman.entity.ghost.Ghost;
import jhwang04.pacman.entity.ghost.OrangeGhost;
import jhwang04.pacman.entity.ghost.PinkGhost;
import jhwang04.pacman.entity.ghost.RedGhost;
import jhwang04.pacman.node.Node;
import processing.core.PApplet;

public class PacmanApplet extends PApplet {
	private int level;
	private int screen;
	private Tile[][] tiles = new Tile[31][28];
	private Player player;
	private RedGhost redGhost;
	private OrangeGhost orangeGhost;
	private PinkGhost pinkGhost;
	private BlueGhost blueGhost;
	private int time;
	
	private int ghostRunningTime;
	
	private List<Tile> trackableTiles;
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
		time = 0;
		level = 1;
		ghostRunningTime = 0;
		screen = TITLE_SCREEN;
		player = new Player(281, 520);
		redGhost = new RedGhost(250, 280);
		orangeGhost = new OrangeGhost(270, 280);
		blueGhost = new BlueGhost(290, 280);
		pinkGhost = new PinkGhost(310, 280);
		nodes = new ArrayList<Node>();
		unexploredNodes = new ArrayList<Node>();
		trackableTiles = new ArrayList<Tile>();
		
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
		
		//initializes trackable tiles (for blue ghost, this algorithm can change)
		for(int i = 0; i < 31; i++) {
			for(int j = 0; j < 28; j++) {
				if(getTile(i, j).getType() == Tile.PELLET || getTile(i, j).getType() == Tile.POWER_PELLET)
					trackableTiles.add(getTile(i,j));
			}
		}
		
		
		//connects the nodes
		for(Node node : nodes) {
			List<Node> neighbors = getNeighboringNodes(node.getTile());
			Tile pTile = node.getTile();
			for(Node neighbor : neighbors)
				Node.connectNodes(node, neighbor, neighbors.indexOf(neighbor));
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
		
		if(ghostRunningTime > 0) {
			ghostRunningTime--;
		} else if(ghostRunningTime == 0) {

			redGhost.setMode(Ghost.CHASE_MODE);
			blueGhost.setMode(Ghost.CHASE_MODE);
			pinkGhost.setMode(Ghost.CHASE_MODE);
			orangeGhost.setMode(Ghost.CHASE_MODE);
		}
		
		pushStyle();
		stroke(255);
		textSize(20);
		textAlign(LEFT, CENTER);
		text("Time = " + Math.round((time/60 + (time%60)/60.0) * 1000.0)/1000.0, 30, 25);
		popStyle();
		
		drawTiles();
		
		//drawNodes();
		
		ghostCollisions(redGhost);
		ghostCollisions(blueGhost);
		ghostCollisions(orangeGhost);
		ghostCollisions(pinkGhost);
		
		if(level != 0) {
			player.move(this);
			player.draw(this);
			
			
			redGhost.move(this);
			redGhost.draw(this);
			blueGhost.move(this);
			blueGhost.draw(this);
			pinkGhost.move(this);
			pinkGhost.draw(this);
			orangeGhost.move(this);
			orangeGhost.draw(this);
			time++;
			
		}
		
		
		popMatrix();
	}
	
	/*public void mousePressed() {
		player.move(this);
		redGhost.move(this);
	}
	
	public void mouseReleased() {
		mousePressed();
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
		
		Node aboveNeighbor = getNodeInDirection(0, row, column);
		Node belowNeighbor = getNodeInDirection(2, row, column);
		Node rightNeighbor = getNodeInDirection(1, row, column);
		Node leftNeighbor = getNodeInDirection(3, row, column);
		
		neighbors.add(aboveNeighbor);
		neighbors.add(rightNeighbor);
		neighbors.add(belowNeighbor);
		neighbors.add(leftNeighbor);
		
		return neighbors;
	}
	
	private Node getNodeInDirection(int direction, int row, int column) {
		if(direction == 1)
			column++;
		else if(direction == 3)
			column--;
		else if(direction == 2)
			row++;
		else if(direction == 0)
			row--;
		
		if(column >=28)
			column = 0;
		else if(column <= -1)
			column = 27;
		
		
		Tile tile = getTile(row, column);
		Tile playerTile = getTile(player.getTileY(), player.getTileX());
		if(tile.getType() == Tile.WALL) {
			return null;
		} else if(/*isNode(tile.getRow(), tile.getColumn())*/ getNodeAt(tile) != null) {
			return getNodeAt(tile);
		} /*else if(tile.getRow() == playerTile.getRow() && tile.getColumn() == playerTile.getColumn()) {
			Node playerNode = new Node(playerTile);
			return playerNode;
		} */else
			return getNodeInDirection(direction, row, column);
			
			
	}
	
	public int getDirectionToGo(Tile currentTile, Tile destinationTile) {
		int u = distanceInDirection(currentTile, destinationTile, 0);
		int r = distanceInDirection(currentTile, destinationTile, 1);
		int d = distanceInDirection(currentTile, destinationTile, 2);
		int l = distanceInDirection(currentTile, destinationTile, 3);
		//System.out.println(u + ", " + r + ", " + d + ", " + l);
		if( u < r && u < d && u < l)
			return 0;
		else if(r < u && r < d && r < l)
			return 1;
		else if(d < u && d < r && d < l)
			return 2;
		else if(l < u && l < r && l < d)
			return 3;
		else
			return -1;
	}
	
	private int distanceInDirection(Tile currentTile, Tile destinationTile, int direction) {
		int distance = 0;
		Tile temporaryTile = new Tile(currentTile.getRow(), currentTile.getColumn(), Tile.AIR); 
		while(distance < 50 && (temporaryTile.getRow() != destinationTile.getRow() || temporaryTile.getColumn() != destinationTile.getColumn())) {
			if(direction == 0)
				temporaryTile = new Tile(temporaryTile.getRow() - 1, temporaryTile.getColumn(), Tile.AIR);
			else if(direction == 1) {
				temporaryTile = new Tile(temporaryTile.getRow(), temporaryTile.getColumn() + 1, Tile.AIR);
				if(temporaryTile.getColumn() >= 28)
					temporaryTile = new Tile(temporaryTile.getRow(), 0, Tile.AIR);
			} else if(direction == 2)
				temporaryTile = new Tile(temporaryTile.getRow() + 1, temporaryTile.getColumn(), Tile.AIR);
			else if(direction == 3) {
				temporaryTile = new Tile(temporaryTile.getRow(), temporaryTile.getColumn() - 1, Tile.AIR);

				if(temporaryTile.getColumn() <= -1)
					temporaryTile = new Tile(temporaryTile.getRow(), 27, Tile.AIR);
			}
			
			distance++;
		}
		return distance;
	}
	
	public boolean isNode(int row, int column) {
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
	
	public boolean isNode(Tile t) {
		return isNode(t.getRow(), t.getColumn());
	}
	
	public Node getNodeAt(Tile t) {
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
	
	public List<Node> pathFind(Tile startTile, Tile finishTile) {
		return pathFind(startTile, finishTile, Color.RED, null);
	}
	
	public List<Node> pathFind(Tile startTile, Tile finishTile, Node nodeToIgnore) {
		return pathFind(startTile, finishTile, Color.RED, nodeToIgnore);
	}
	
	public List<Node> pathFind(Tile startTile, Tile finishTile, Color color, Node nodeToIgnore) {
		//System.out.println("startTile = " + startTile + " , finishTile = " + finishTile);
		Node start = getNodeAt(startTile);
		Node finish = getNodeAt(finishTile);
		//System.out.println(nodeToIgnore);
		
		//System.out.println("nodes.size = " + nodes.size());
		
		boolean wasFinishNull = false;
		if(finish == null) {
			wasFinishNull = true;
			finish = new Node(finishTile);
			List<Node> neighbors = getNeighboringNodes(finish.getTile());
			//System.out.println("finish.neighbors = " + neighbors);
			for(Node neighbor : neighbors) {
				Node.connectNodes(finish, neighbor, neighbors.indexOf(neighbor));
			}
			//System.out.println("finish.connections = " + finish.getConnections());
			nodes.add(finish);
		}
		
		//System.out.println("nodes.size = " + nodes.size());
		
		boolean wasStartNull = false;
		if(start == null) {
			wasStartNull = true;
			start = new Node(startTile);
			List<Node> neighbors = getNeighboringNodes(start.getTile());
			//System.out.println("start.neighbors = " + neighbors);
			for(Node neighbor : neighbors) {
				Node.connectNodes(start, neighbor, neighbors.indexOf(neighbor));
			}
			//System.out.println("start.connections = " + start.getConnections());
			nodes.add(start);
		}
		
		//handes pink turning 180s when target node is between pink's current location and old target
		if(getNeighboringNodes(start.getTile()).contains(finish) && !getNeighboringNodes(start.getTile()).contains(nodeToIgnore)) {
			//System.out.println("changed nodeToIgnore to finish");
			nodeToIgnore = finish;
		}
		
		int ignoreDirection = -1;
		if(nodeToIgnore != null) {
			//System.out.println("currentNode = " + start + ", connections are " + start.getConnections());
			ignoreDirection = start.getConnections().indexOf(nodeToIgnore);
			Node.disconnectNodes(start, nodeToIgnore);
			//System.out.println("lastNode = " + nodeToIgnore + ", connections are " + nodeToIgnore.getConnections());
			//System.out.println("currentNode = " + start + ", connections are " + start.getConnections());
		}
		
		
		//int ignoreDirection = -1;
		/*if(directionToIgnore != -1) {
			//ignoreDirection = start.getConnections().indexOf(nodeToIgnore);
			Node nodeToIgnore = getNodeInDirection(directionToIgnore, start.getTile().getRow(), start.getTile().getColumn());
			Node.disconnectNodes(start, nodeToIgnore);
		}*/
		
		//System.out.println("start.connections = " + start.getConnections() + " , finish.connections = " + finish.getConnections());
		
		//original pathFind starts here
		
		List<Node> path = new ArrayList<Node>();
		populateUnexploredNodes();
		start.setTentativeCost(0);
		finish.setPathTo(start);
		//Node lastNode = getLeastNode();
		while(unexploredNodes.size() > 0) {
			Node currentNode = getLeastNode();
			unexploredNodes.remove(currentNode);
			
			if(currentNode.equals(finish)) {
				break;
			}
			
			for(Node nextNode : currentNode.getConnections()) {
				if(nextNode != null) {
					if(nextNode.getTentativeCost() > currentNode.getTentativeCost() + currentNode.distanceFrom(nextNode.getTile())) {
						//lastNode = currentNode;
						nextNode.setTentativeCost((int) (currentNode.getTentativeCost() + currentNode.distanceFrom(nextNode.getTile())));
						nextNode.setPathTo(currentNode);
					}
				}
			}
			
		}
		Node backtrackingNode = finish;
		//backtrackingNode.setPathTo(backtrackingNode);
		//System.out.println("start = " + start + ", finish = " + finish + ", backtrackingNode = " + backtrackingNode);
		while(!backtrackingNode.equals(start)) {
			path.add(backtrackingNode);
			backtrackingNode = backtrackingNode.getPathTo();
		}
		path.add(start);
		
		//System.out.println(path);
		//System.out.println(start.getConnections() + "\n");
		
		drawPath(path, color);
		
		//original pathfind ends here
		
		/*if(path.size() == 2) {
			Node pacmanNode = path.get(0);
			Node newNode = new Node(new Tile(pacmanNode.getTile().getRow(), pacmanNode.getTile().getColumn(), Tile.AIR));
			path.set(0, newNode);
		}*/
		
		/*if(directionToIgnore != -1) {
			Node nodeToIgnore = getNodeInDirection(directionToIgnore, start.getTile().getRow(), start.getTile().getColumn());
			System.out.println(nodeToIgnore + ", " + nodeToIgnore.getConnections());
			Node.connectNodes(start, nodeToIgnore, directionToIgnore);
		}*/
		if(nodeToIgnore != null) {
			//System.out.println("start, nodeToIgnore, ignoreDirection = " + start + ", " + nodeToIgnore + ", " + ignoreDirection);
			Node.connectNodes(start, nodeToIgnore, ignoreDirection);
		}
		
		if(wasFinishNull == true) {
			if(finish.getConnections().get(0) != null)
				Node.connectNodes(finish.getConnections().get(0), finish.getConnections().get(2), 2);
			if(finish.getConnections().get(1) != null)
				Node.connectNodes(finish.getConnections().get(1), finish.getConnections().get(3), 3);
			nodes.remove(finish);
		}
		
		if(wasStartNull == true) {
			if(start.getConnections().get(0) != null) {
				Node.connectNodes(start.getConnections().get(0), start.getConnections().get(2), 2);
				//System.out.println("start.getConnections() 0 and 2 are " + start.getConnections().get(0) + ", " + start.getConnections().get(2));
			}
			if(start.getConnections().get(1) != null) {
				Node.connectNodes(start.getConnections().get(1), start.getConnections().get(3), 3);
				//System.out.println("start.getConnections() 1 and 3 are " + start.getConnections().get(1) + ", " + start.getConnections().get(3));
			}
			nodes.remove(start);
		}
		
		resetTentativeCosts();
		resetPathTo();
		//System.out.println(path);
		//System.out.println("");
		return path;
	}
	
	private void drawPath(List<Node> path, Color color) {
		pushStyle();
		strokeWeight(2);
		stroke(color.getRed(), color.getGreen(), color.getBlue());
		for(int i = 1; i < path.size(); i++) {
			//if(!(path.size() == 2 && path.get(0).getTile().getRow() == path.get(1).getTile().getRow() && path.get(0).getTile().getColumn() == path.get(1).getTile().getColumn()))
			//System.out.println("direction = " + path.get(i).getConnections().indexOf(path.get(i-1)));
			//System.out.println("path.neighbors = " + path.get(i).getConnections());
			drawPathInDirection(path.get(i).getTile().getRow(), path.get(i).getTile().getColumn(), path.get(i).getConnections().indexOf(path.get(i-1)), path.get(i-1));
		}
		popStyle();
	}
	
	private void drawPath(List<Node> path) {
		drawPath(path, Color.RED);
	}
	
	private void drawPathInDirection(int row, int column, int direction, Node destination) {
		int oldRow = row;
		int oldColumn = column;
		//System.out.println("(" + column + ", " + row + ") , destination = (" + destination.getTile().getColumn() + ", " + destination.getTile().getRow() + ") , direction = " + direction);
		
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

		if(column >= 28)
			column = 0;
		if(column <= -1)
			column = 27;
		
		//System.out.println("oldColumn, column, oldRow, row = " + oldColumn + " " + column + " " + oldRow + " " + row);
		
		if(row != destination.getTile().getRow() || column != destination.getTile().getColumn())
			drawPathInDirection(row, column, direction, destination);
	}
	
	public Ghost getGhost(String color) {
		if(color.equals("red"))
			return redGhost;
		return null;
	}
	
	public Tile getClosestTrackableTile(Tile tile) {
		Tile currentClosest = new Tile(-1, -1, Tile.AIR);
		
		for(Tile newTile : trackableTiles) {
			if(tile.distanceTo(newTile) < tile.distanceTo(currentClosest))
				currentClosest = newTile;
		}
		
		return currentClosest;
	}
	
	public void eatPowerPellet() {
		ghostRunningTime = 600;
		reverseDirection(redGhost);
		reverseDirection(blueGhost);
		reverseDirection(pinkGhost);
		reverseDirection(orangeGhost);
		redGhost.setMode(Ghost.RUN_MODE);
		blueGhost.setMode(Ghost.RUN_MODE);
		pinkGhost.setMode(Ghost.RUN_MODE);
		orangeGhost.setMode(Ghost.RUN_MODE);
		
	}
	
	private void reverseDirection(Ghost ghost) {
		Tile above = getTile(ghost.getTileY() - 1, ghost.getTileX());
		Tile below = getTile(ghost.getTileY() + 1, ghost.getTileX());
		Tile left = getTile(ghost.getTileY(), ghost.getTileX() - 1);
		Tile right = getTile(ghost.getTileY(), ghost.getTileX() + 1);
		int direction = ghost.getDirection();
		boolean shouldTurn = true;
		/*if(((direction + 2)%4) == 0 && above.getType() == Tile.WALL)
			shouldTurn = false;
		else if(((direction + 2)%4) == 1 && right.getType() == Tile.WALL)
			shouldTurn = false;
		else if(((direction + 2)%4) == 2 && below.getType() == Tile.WALL)
			shouldTurn = false;
		else if(((direction + 2)%4) == 3 && left.getType() == Tile.WALL)
			shouldTurn = false;*/
		
		ghost.setLastTile(new Tile(-1, -1, 0));
		
		if(shouldTurn == true)
			ghost.setDirection((direction + 2)%4);
	}
	
	private void ghostCollisions(Ghost ghost) {
		if(player.isTouching(ghost)) {
			if(ghost.getMode() == Ghost.CHASE_MODE)
				level = 0;
			else if(ghost.getMode() == Ghost.RUN_MODE)
				ghost.setMode(Ghost.RETURN_MODE);
		}
	}
	
	public int getGhostRunningTime() {
		return ghostRunningTime;
	}
}

