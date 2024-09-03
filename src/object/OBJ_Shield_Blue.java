package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Shield_Blue extends Entity{

	public OBJ_Shield_Blue(GamePanel gp) {
		super(gp);
		
		type = type_shield;
		name = "Steel Shield";
		down1 = setup("/objects/shield_blue", gp.tileSize, gp.tileSize);
		defValue = 2;
		description = "[" +name+ "]\nA steel shield that \nprovides decent \ndefense.";
		price = 100;
	}

}
