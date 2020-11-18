package jhwang04.pacman.entity.ghost;

import java.awt.Color;

import jhwang04.pacman.PacmanApplet;
import jhwang04.pacman.Tile;

public class PinkGhost extends Ghost{

	public PinkGhost(double x, double y) {
		super(x, y, new Color(255, 184, 255));
	}
	
	public void move(PacmanApplet p) {
		int playerDirection = p.getPlayer().getMovementDirection();
		
		setTargetTile(furthestTileInDirection(p, playerDirection, 4));
		
		super.move(p);
	}

}
