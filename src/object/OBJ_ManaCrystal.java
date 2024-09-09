package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_ManaCrystal extends Entity{
	
	GamePanel gp;
	
	public OBJ_ManaCrystal(GamePanel gp) {
		
		super(gp);
		this.gp=gp;
		type = type_obtainable;
		name = "Mana Crystal";
		value = 2;
		down1 =setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
		image = setup("/objects/manacrystal_full", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/manacrystal_blank", gp.tileSize, gp.tileSize);
	}
	
	public boolean use(Entity entity) {
		
		gp.playSoundEffect(2);
		gp.ui.addMsg("Restored "+value+" mana.");
		entity.mana += value;
		return true;
	}
	
}
