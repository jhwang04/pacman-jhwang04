package jhwang04.pacman.entity.ghost;

import java.awt.Color;

import jhwang04.pacman.PacmanApplet;
import jhwang04.pacman.Tile;

public class BlueGhost extends Ghost{

	public BlueGhost(double x, double y) {
		super(x, y, new Color(0, 255, 255));
	}
	
	public void move(PacmanApplet p) {
		Tile initialTargetTile = furthestTileInDirection(p, p.getPlayer().getMovementDirection(), 2);
		Tile redGhostTile = p.getTile(p.getGhost("red").getTileY(), p.getGhost("red").getTileX());
		int differenceY = redGhostTile.getRow()- initialTargetTile.getRow();
		int differenceX = redGhostTile.getColumn() - initialTargetTile.getColumn();
		Tile newTargetTile = new Tile(initialTargetTile.getRow() - differenceY, initialTargetTile.getColumn() - differenceX, Tile.AIR);
		newTargetTile = p.getClosestTrackableTile(newTargetTile);
		setTargetTile(newTargetTile);
		
		super.move(p);
	}

}
