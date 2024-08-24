package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class IT_ChoppableTree extends InteractiveTile{

	public IT_ChoppableTree(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		down1 = setup("/tiles_interactive/drytree", gp.tileSize, gp.tileSize);
		destructible = true;
		health = 3;
	}
	
	public boolean correctWpn(Entity entity) {
		boolean correctWpn = false;
		if(entity.currentWpn.type == type_axe) {
			correctWpn = true;
		}
		return correctWpn;
	}
	
	public void playSoundEffect() {
		gp.playSoundEffect(11);
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}

}
