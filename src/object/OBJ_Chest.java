package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

//chest class
public class OBJ_Chest extends Entity{

	GamePanel gp;
	
	public OBJ_Chest(GamePanel gp) {
		
		super(gp);
		name = "Chest";
		down1 = setup("/objects/chest");
	}
}
