package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_Trunk extends InteractiveTile{

	public IT_Trunk(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		down1 = setup("/tiles_interactive/trunk", gp.tileSize, gp.tileSize);
		
		//remove the collision area from the tile so it can be walked through
		collisionArea.x = 0;
		collisionArea.y = 0;
		collisionArea.width = 0;
		collisionArea.height = 0;
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
	}

}
