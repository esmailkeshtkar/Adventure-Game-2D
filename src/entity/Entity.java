package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
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
	
	//an image with an accessible buffer of image data, stores image files
	public BufferedImage image, image2, image3;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; 
	public BufferedImage atkUp1, atkUp2, atkDown1, atkDown2, 
	atkLeft1, atkLeft2, atkRight1, atkRight2;
	public Rectangle collisionArea = new Rectangle(0, 0, 85, 85);
	public Rectangle atkArea = new Rectangle(0, 0, 0, 0);
	public int collisionAreaDefaultX, collisionAreaDefaultY;
	public boolean collision = false;
	String dialogues[] = new String[20];
	
	//STATE
	public int worldX, worldY;
	public String direction = "down";
	public int spriteNum = 1;
	int dialogueIndex = 0;
	public boolean collisionOn = false;
	public boolean invincible = false;
	boolean atking = false;
	public boolean alive = true;
	public boolean dying = false;
	boolean hpBarOn = false;
	
	//COUNTERS
	public int spriteCounter = 0;
	public int actionLockCounter = 0;
	public int invincibleCounter = 0;
	int dyingCounter = 0;
	int hpBarCounter = 0;
	
	//CHARACTER ATTRIBUTES
	public int entityType; //0 = player, 1 = NPC, 2 = monster
	public String name;
	public int maxLife, life;
	public int speed;
	public int level;
	public int exp;
	public int nextLvlExp;
	public int str;
	public int dex;
	public int atk;
	public int def;
	public int coins;
	public Entity currentWpn;
	public Entity currentShield;
	
	// ITEM ATTRIBUTES
	public int atkValue;
	public int defValue;
	
	public Entity(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setAction() {}
	
	public void dmgReaction() {}
	
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
				gp.playSoundEffect(6);
				
				int dmg = atk - gp.player.def;
				if(dmg < 0) { dmg = 0; }//so dmg does not go negative and heal
				gp.player.life-=dmg;
				
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
		
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter >= 40) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}
	
	public void getPlayerImage() {
		
		up1 = setup("boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("boy_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("boy_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("boy_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("boy_right_2", gp.tileSize, gp.tileSize);
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
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
				break;
			case "down":
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
				break;
			case "left":
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
				break;
			case "right":
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
				break;
			}
			
			//displays monster's HP bar when attacked for up 10 seconds
			if(entityType == 2 && hpBarOn == true) {
				double oneScale = (double)gp.tileSize/maxLife;
				double hpValue = oneScale*life;
				
				g2.setColor(new Color(35,35,35));
				g2.fillRect(screenX, screenY - gp.tileSize/10, gp.tileSize, gp.tileSize/6);
				
				g2.setColor(new Color(255, 0, 30));
				g2.fillRect(screenX, screenY - gp.tileSize/10, (int)hpValue, gp.tileSize/6);
				
				hpBarCounter++;
				
				if(hpBarCounter >= 600) {
					hpBarCounter = 0;
					hpBarOn = false;
				}
			}
			
			
			if(invincible == true) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, .4f);
			}
			
			//dying animation
			if(dying == true) {
				dyingAnimation(g2);
			}
			
			g2.drawImage(image, screenX, screenY, gp.tileSize, gp.tileSize, null);
			
			changeAlpha(g2, 1f);
		}
		
	}
	
	
	private void dyingAnimation(Graphics2D g2) {
		
		dyingCounter+=5;
		
		//every 5 frames switch monster's opacity to make it blink on death for 32 frames
		if(dyingCounter%10 == 0) { 
			changeAlpha(g2, 1f);
		}
		else if(dyingCounter%15 == 0) { 
			changeAlpha(g2, .2f);
		}
		
		if(dyingCounter >= 160)	{ dying = false; alive = false; }
		
	}
	
	//changes opacity of a given sprite
	public void changeAlpha(Graphics2D g2, float alpha) {
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha)); 
	}
	

	public BufferedImage setup(String imagePath, int width, int height) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream(imagePath +".png"));
			image = uTool.scaleImage(image,  width, height);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return image;
	}

	
}
