package jhwang04.pacman.entity.ghost;

import java.awt.Color;

import jhwang04.pacman.PacmanApplet;

public class RedGhost extends Ghost{

	public RedGhost(double x, double y) {
		super(x, y, Color.RED);
	}
	
	public void move(PacmanApplet p) {
		setTargetTile(p.getTile(p.getPlayer().getTileY(), p.getPlayer().getTileX()));
		super.move(p);
	}

}
