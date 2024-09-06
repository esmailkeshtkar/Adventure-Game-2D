package object;

import entity.Entity;
import main.GamePanel;

public class OBJ_Axe extends Entity{

	public OBJ_Axe(GamePanel gp) {
		super(gp);
		type = type_axe;
		name = "Woodcutter's Axe";
		down1 = setup("/objects/axe", gp.tileSize, gp.tileSize);
		atkValue = 2;
		atkArea.width = gp.tileSize*1/2;
		atkArea.height = gp.tileSize*1/2;
		description = "["+name+"]\nAn old and worn axe.";
		price = 25;
		knockbackStr = 10;
	}

}
