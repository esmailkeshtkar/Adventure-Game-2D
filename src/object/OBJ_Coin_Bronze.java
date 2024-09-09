package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Coin_Bronze extends Entity{

	GamePanel gp;
	
	public OBJ_Coin_Bronze(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_obtainable;
		name = "Bronze Coin";
		down1 = setup("/objects/coin_bronze", gp.tileSize, gp.tileSize);
		value = 1;
	}
	
	public boolean use(Entity entity) {
		
		gp.playSoundEffect(1);
		gp.ui.addMsg("Obtained "+value+" coin");
		gp.player.coins += value;
		return true;
	}

}
