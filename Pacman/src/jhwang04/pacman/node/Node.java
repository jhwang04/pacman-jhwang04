package jhwang04.pacman.node;
import jhwang04.pacman.Tile;

/**
 * Represents a Pacman pathfinding node
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
     * Creates a new node at the location of the given Tile
     * @param tile The Tile at the location of the new Node
     */
    public Node(Tile tile) {
	this.tile = tile;
    }

    /**
     * Returns the Tile that the Node is on top of
     */
    public Tile getTile() {
	return tile;
    }

    /**
     * Connects the given nodes to each other
     */
    public static void connectNodes(Node node1, Node node2) {
	if(!node1.getConnections().contains(node2)) {
	    node1.addNode(node2);
	    connectNodes(node2, node1);
	}
	
    }

    /**    
     * Helper method for connectNodes, adds one to list
     */
    private void addNode(Node n) {
	connections.add(n);
    }

    /**
     * Returns the List of Nodes that this Node is connected to
     * @return The List of Nodes, the "connections" field
     */
    public List<Node> getConnections() {
	return connections;
    }
}
