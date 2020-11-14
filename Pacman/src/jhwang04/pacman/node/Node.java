package jhwang04.pacman.node;

import java.util.ArrayList;
import java.util.List;

import jhwang04.pacman.Tile;
import processing.core.PApplet;

/**
 * Represents a Pacman pathfinding Node
 * @author jhwang04
 */
public class Node {
	/**
	 * The Tile that the Node is on
	 */
	private Tile tile;

	/**
	 * The Nodes that this Node is connected to
	 */
	private List<Node> connections;

	/**
	 * Score that will be used by A* pathfinding, hopefully
	 */
	private int tentativeCost;
	
	/**
	 * Node that leads to this one
	 */
	private Node pathTo;
	
	/**
	 * Creates a new node at the location of the given Tile
	 * 
	 * @param tile The Tile at the location of the new Node
	 */
	public Node(Tile tile) {
		this.tile = tile;
		tentativeCost = 100000;
		connections = new ArrayList<Node>();
		for(int i = 0; i < 4; i++) {
			connections.add(null);
		}
	}

	/**
	 * Returns the Tile that the Node is on top of
	 */
	public Tile getTile() {
		return tile;
	}

	/**
	 * Connects the given Nodes to each other, if they aren't already
	 */
	public static void connectNodes(Node node1, Node node2) {
		if(node1 != null && node2 != null) {
			if(!node1.getConnections().contains(node2))
				node1.addNode(node2);
			if(!node2.getConnections().contains(node1))
				node2.addNode(node1);
		}
	}

	private void addNode(Node n) {
		if(n.getTile().getRow() < tile.getRow())
			connections.set(0, n);
		else if(n.getTile().getColumn() > tile.getColumn())
			connections.set(1, n);
		else if(n.getTile().getRow() > tile.getRow())
			connections.set(2, n);
		else if(n.getTile().getColumn() < tile.getColumn())
			connections.set(3, n);
		
		//connections.add(n);
	}
	
	
	/**
	 * Disconnects the given Nodes from each other, if they're connected
	 */
	public static void disconnectNodes(Node node1, Node node2) {
		/*if(node1.getConnections().contains(node2))
			node1.removeNode(node2);
		if(node2.getConnections().contains(node1))
			node2.removeNode(node1);*/
		List<Node> n1 = node1.getConnections();
		List<Node> n2 = node2.getConnections();
		if(n1.contains(node2))
			n1.set(n1.indexOf(node2), null);
		if(n2.contains(node1))
			n2.set(n2.indexOf(node1), null);
	}
	
	private void removeNode(Node n) {
		connections.remove(n);
	}
	
	/**
	 * Calculates the distance between the implicit Node and the given Node
	 * @param node The Node to calculate distance to
	 */
	public float distanceFrom(Node node) {
		int changeX = tile.getColumn() - node.getTile().getColumn();
		int changeY = tile.getRow() - node.getTile().getRow();
		return (float) Math.sqrt(changeX*changeX + changeY*changeY);
	}
	
	/**
	 * Calculates the distance between the implicit Node and the given Tile
	 * @param tile The Tile to calculate distance to
	 */
	public float distanceFrom(Tile tile) {
		int changeX = this.tile.getColumn() - tile.getColumn();
		int changeY = this.tile.getRow() - tile.getRow();
		return (float) Math.sqrt(changeX*changeX + changeY*changeY);
	}
	
	/**
	 * Draws a green square at the Node's location
	 * @param The PApplet DrawingSurface for the Node to draw in
	 */
	public void draw(PApplet p) {
		p.pushStyle();
		p.pushMatrix();
		p.translate(tile.getColumn()*20, tile.getRow()*20 + 50);
		
		p.noStroke();
		p.fill(0, 255, 0);
		p.rect(0, 0, 20, 20);
		
		p.fill(0);
		p.textSize(10);
		p.textAlign(PApplet.CENTER, PApplet.CENTER);
		p.text(connections.size(), 10, 10);
		
		p.popMatrix();
		p.popStyle();
	}
	
	/**
	 * Returns the List of Nodes that this Node is connected to
	 * 
	 * @return The List of Nodes, the "connections" field
	 */
	public List<Node> getConnections() {
		return connections;
	}
	
	/**
	 * Returns the tentative cost of the Node
	 * @return The tentative cost of the Node
	 */
	public int getTentativeCost() {
		return tentativeCost;
	}
	
	/**
	 * Sets the tentative cost of the Node
	 * @param cost New tentative cost of the Node
	 */
	public void setTentativeCost(int cost) {
		tentativeCost = cost;
	}
	
	/**
	 * Returns the Node that leads to this one (shortest distance)
	 * @return The Node that leads to this one
	 */
	public Node getPathTo() {
		return pathTo;
	}
	
	/**
	 * Sets the Node that leads to this one (shortest distance)
	 * @param node The new Node that leads to this one
	 */
	public void setPathTo(Node node) {
		pathTo = node;
	}
}
