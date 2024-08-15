package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

//Abstract class, a blueprint for player and character classes
public class Entity {
	
	GamePanel gp;
	public int worldX, worldY;
	public int speed;
	//an image with an accessible buffer of image data, stores image files
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; 
	public String direction = "down";
	public int spriteCounter = 0;
	public int spriteNum = 1;
	public Rectangle collisionArea = new Rectangle(0, 0, 85, 85);
	public int collisionAreaDefaultX, collisionAreaDefaultY;
	public boolean collisionOn = false;
	public int actionLockCounter = 0;
	public boolean invincible = false;
	public int invincibleCounter = 0;
	String dialogues[] = new String[20];
	int dialogueIndex = 0;
	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	public int entityType; //0 = player, 1 = NPC, 2 = monster
	
	//CHARACTER STATUS
	public int maxLife, life;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {}
	public void speak() {
		
		if(dialogues[dialogueIndex] == null)
		{
			dialogueIndex = 0;
		}
		gp.ui.currentDialogue = dialogues[dialogueIndex];
		dialogueIndex++;
		
		switch(gp.player.direction) {
		case "up": direction = "down"; break;
		case "down" : direction = "up"; break;
		case "right" : direction = "left"; break;
		case "left": direction = "right"; break;
		}
	}
	
	public void update() {
		
		setAction();
		
		collisionOn = false;
		gp.cDetector.checkTile(this);
		gp.cDetector.checkObject(this, false);
		gp.cDetector.checkEntity(this,  gp.npc);
		gp.cDetector.checkEntity(this, gp.mon);
		boolean contactPlayer = gp.cDetector.checkPlayer(this);
		
		//if monster is contacting player
		if(this.entityType == 2 && contactPlayer == true) {
			if(gp.player.invincible == false) {
				//damage can be received
				gp.player.life--;
				gp.player.invincible = true;
			}
		}
		
		if(collisionOn == false) {
			switch(direction) {
			case "up": worldY -= speed; break;
			case "down": worldY += speed; break;
			case "left": worldX -= speed; break;
			case "right": worldX += speed; break;
			}
		}
		
		//used to alternate the sprite's for movement
		spriteCounter++;
		if(spriteCounter > 15)
		{
			if(spriteNum == 1)
			{
				spriteNum = 2;
			}else if(spriteNum == 2) {
				spriteNum = 1;
			}
			spriteCounter = 0;
		}
	}
	
	public void getPlayerImage() {
		
		up1 = setup("player_up_1");
		up2 = setup("player_up_2");
		down1 = setup("player_down_1");
		down2 = setup("player_down_2");
		left1 = setup("player_left_1");
		left2 = setup("player_left_2");
		right1 = setup("player_right_1");
		right2 = setup("player_right_2");
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		int screenX = worldX - gp.player.worldX + gp.player.screenX; //screen position
		int screenY = worldY - gp.player.worldY + gp.player.screenY; //screen position
		
		//boundary of the screen so that the whole map is not drawn at once
		if(worldX + 2*gp.tileSize > gp.player.worldX - gp.player.screenX &&
		   worldX - 2*gp.tileSize < gp.player.worldX + gp.player.screenX &&
		   worldY + 2*gp.tileSize > gp.player.worldY - gp.player.screenY &&
		   worldY - 2*gp.tileSize < gp.player.worldY + gp.player.screenY) {
			
			switch(direction) {
			case "up":
				if(spriteNum == 1) {
					image = up1;
				}
				if(spriteNum == 2) {
					image = up2;
				}
				break;
			case "down":
				if(spriteNum == 1) {
					image = down1;
				}
				if(spriteNum == 2) {
					image = down2;
				}
				break;
			case "left":
				if(spriteNum == 1) {
					image = left1;
				}
				if(spriteNum == 2) {
					image = left2;
				}
				break;
			case "right":
				if(spriteNum == 1) {
					image = right1;
				}
				if(spriteNum == 2) {
					image = right2;
				}
				break;
			}
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
		}
		
	}
	
	public BufferedImage setup(String imagePath) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
			image = uTool.scaleImage(image,  gp.tileSize,  gp.tileSize);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return image;
	}

	
}
