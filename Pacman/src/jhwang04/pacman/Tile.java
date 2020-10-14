package jhwang04.pacman;

public class Tile {
	private int row, column;
	private int type;
	
	public static final int AIR = 0;
	public static final int WALL = 1;
	public static final int PELLET = 2;
	public static final int POWER_PELLET = 3;
	
	public Tile(int row, int column, int type) {
		this.row = row;
		this.column = column;
		this.type = type;
		
	}
	
	public void draw(PacmanApplet p) {
		p.pushStyle();
		p.pushMatrix();
		p.translate(column * 20, row * 20);
		
		Tile[][] tiles = p.getTiles();
		Tile above, below, left, right;
		
		//making sure that above, below, left and right are not null
		if(column == 0)
			left = new Tile(row, column-1, 0);
		else
			left = tiles[row][column-1];
		
		if(column == tiles[0].length - 1)
			right = new Tile(row, column+1, 0);
		else
			right = tiles[row][column+1];
		
		if(row == 0)
			above = new Tile(row-1, column, 0);
		else
			above = tiles[row-1][column];
		
		if(row == tiles.length - 1)
			below = new Tile(row+1, column, 0);
		else
			below = tiles[row+1][column];
		
		
		switch(type) {
		case 1:
			p.noStroke();
			p.noFill();
			p.strokeWeight(3);
			p.stroke(0, 0, 200);
			
			if(above.getType() == WALL)
				p.line(10, 0, 10, 10);
			if(right.getType() == WALL)
				p.line(20, 10, 10, 10);
			if(below.getType() == WALL)
				p.line(10, 20, 10, 10);
			if(left.getType() == WALL)
				p.line(0, 10, 10, 10);
			
			break;
		case 2:
			p.noStroke();
			p.fill(255);
			p.ellipse(10, 10, 5, 5);
			break;
		case 3:
			p.noStroke();
			p.fill(255);
			p.ellipse(10, 10, 10, 10);
			break;
		}
		
		//border of the tile
		/*p.strokeWeight(1);
		p.stroke(255);
		p.noFill();
		p.rect(0, 0, 20, 20);*/
		
		p.popMatrix();
		p.popStyle();
	}
	
	public int getType() {
		return type;
	}
}
