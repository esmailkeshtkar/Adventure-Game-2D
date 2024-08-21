package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Wood extends Entity{

	public OBJ_Shield_Wood(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = "Wooden Shield";
		down1 = setup("/objects/shield_wood", gp.tileSize, gp.tileSize);
		defValue = 1;
		atkArea.width = gp.tileSize*2/3;
		atkArea.height = gp.tileSize*2/3;
		description = "[" +name+ "]\nA wooden shield that \nprovides very little \ndefense.";
		
	}

}
