package object;

import java.io.IOException;

import javax.imageio.ImageIO;

import entity.Entity;
import main.GamePanel;

//door class
public class OBJ_Door extends Entity{
	
	GamePanel gp;
	
	public OBJ_Door(GamePanel gp) {
		
		super(gp);
		name = "Door";
		down1 = setup("/objects/door", gp.tileSize, gp.tileSize);
		collision = true;
		
		collisionArea.x = 0;
		collisionArea.y = 16;
		collisionArea.width = 80;
		collisionArea.height = 54;
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
	}
}
