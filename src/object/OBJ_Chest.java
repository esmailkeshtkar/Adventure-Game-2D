package object;

import entity.Entity;
import main.GamePanel;

//chest class
public class OBJ_Chest extends Entity{

	GamePanel gp;
	Entity loot;
	boolean opened = false;
	
	public OBJ_Chest(GamePanel gp, Entity loot) {
		
		super(gp);
		this.gp = gp;
		this.loot = loot;
		type = type_obstacle;
		name = "Chest";
		image = setup("/objects/chest", gp.tileSize, gp.tileSize);
		image2 = setup("/objects/chest_opened", gp.tileSize, gp.tileSize);
		down1 = image;
		collision = true;
		
		collisionArea.x = gp.tileSize/6;
		collisionArea.y = gp.tileSize/3;
		collisionArea.width = (int)(gp.tileSize/1.5);
		collisionArea.height = (int)(gp.tileSize/2);
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
	}
	
	public void interact() {
		
		if(opened == false) {
			gp.gameState = gp.dialogueState;
			gp.playSoundEffect(3);
			StringBuilder sb = new StringBuilder();
			sb.append("You open the chest and obtain a "+loot.name+"!");
			if(gp.player.canObtainItem(loot) == false) {
				sb.append("\n...Your inventory is too full to carry this!");
			}else {
				sb.append("\nYou obtain "+loot.name);
				down1 = image2;
				opened = true;
			}
			gp.ui.currentDialogue = sb.toString();
		}
	}
}
