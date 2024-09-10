package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Potion_Red extends Entity{
	
	GamePanel gp;
	

	public OBJ_Potion_Red(GamePanel gp) {
		super(gp);
		this.gp = gp;
		type = type_consumable;
		value = 5;
		name = "Health Potion";
		down1 = setup("/objects/potion_red", gp.tileSize, gp.tileSize);
		description = "["+name+"]\nA potion that restores \nhealth by "+value;
		stackable = true;
	}
	
	public boolean use(Entity entity) {
		gp.gameState = gp.dialogueState;
		gp.ui.currentDialogue = "You drink the " +name +"\n"
				+"You have recovered "+value+" health!";
		entity.health+=value;
		gp.playSoundEffect(2);
		return true;
	}

}
