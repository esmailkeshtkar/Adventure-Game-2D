package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import main.UtilityTool;

//parent class of all object classes
public class SuperObject {
	
	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle collisionArea = new Rectangle(0, 0, 48, 48);
	public int collisionAreaDefaultX = 0;
	public int collisionAreaDefaultY = 0;
	UtilityTool uTool = new UtilityTool();
	
	public void draw(Graphics2D g2, GamePanel gp) {
		
		int screenX = worldX - gp.player.worldX + gp.player.screenX; //screen position
		int screenY = worldY - gp.player.worldY + gp.player.screenY; //screen position
		
		//boundary of the screen so that the whole map is not drawn at once
		if(worldX + 2*gp.tileSize > gp.player.worldX - gp.player.screenX &&
		   worldX - 2*gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY + 2*gp.tileSize > gp.player.worldY - gp.player.screenY &&
		   worldY - 2*gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
		
	}
}
