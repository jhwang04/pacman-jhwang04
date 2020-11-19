package jhwang04.pacman.entity.ghost;

import java.awt.Color;

import jhwang04.pacman.PacmanApplet;
import jhwang04.pacman.Tile;

public class OrangeGhost extends Ghost{

	public OrangeGhost(double x, double y) {
		super(x, y, new Color(255, 184, 82));
	}
	
	public void move(PacmanApplet p) {
		double distance = p.getTile(p.getPlayer().getTileY(), p.getPlayer().getTileX()).distanceTo(new Tile(getTileY(), getTileX(), Tile.AIR));
		
		if(distance < 8.0)
			setTargetTile(p.getTile(29, 1));
		else
			setTargetTile(p.getTile(p.getPlayer().getTileY(), p.getPlayer().getTileX()));
		
		super.move(p);
	}

}
