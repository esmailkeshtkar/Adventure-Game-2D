package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.KeyHandler;
import main.UtilityTool;

//manages the player entity
public class Player extends Entity{
	
	GamePanel gp;
	KeyHandler keyH;
	
	//indicate where the player is drawn on the screen
	public final int screenX; 
	public final int screenY;
	public int numKey = 0;
	int standCounter = 0;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		this.gp = gp;
		this.keyH = keyH;
		
		//displays character at the center of the screen
		screenX = gp.screenWidth/2- (gp.tileSize/2);
		screenY = gp.screenHeight/2- (gp.tileSize/2);
		
		//collision area rectangle
		collisionArea = new Rectangle();
		
		collisionArea.x = 4*gp.scale;//starting x
		collisionArea.y = 8*gp.scale;//starting y
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		collisionArea.width = gp.tileSize - (gp.scale-2)*gp.originalTileSize;//width of collision area
		collisionArea.height = gp.tileSize - (gp.scale-2)*gp.originalTileSize;//height of collision area
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
		speed = 6;//how many pixels the character moves per frame
		direction = "down";
		
	}
	
	//stores the player images for each direction
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
	
	public BufferedImage setup(String imageName) {
		UtilityTool uTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/" +imageName +".png"));
			image = uTool.scaleImage(image,  gp.tileSize,  gp.tileSize);
			
		}catch(IOException e){
			e.printStackTrace();
		}
		
		return image;
	}
	
	public void update() {
		
		if(keyH.upPressed == true || keyH.downPressed == true 
		|| keyH.leftPressed == true || keyH.rightPressed == true) {
			//character movement based on key pressed
			if(keyH.upPressed == true) {
				direction = "up";
			}
			else if(keyH.downPressed == true) {
				direction = "down";
			}
			else if(keyH.leftPressed == true) {
				direction = "left";
			}
			else if(keyH.rightPressed == true) {
				direction = "right";
			}
			
			//Check tile collision
			collisionOn = false;
			gp.cDetector.checkTile(this);
			
			//Check object collision
			int objIndex = gp.cDetector.checkObject(this, true);
			pickUpObject(objIndex);
			
			//if tile collision is false, the player can move
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
		else {
			standCounter++;
			if(standCounter >= 20)
			{
				spriteNum = 1;
				standCounter=0;
			}
		}
		
	}
	
	//interactions with objects
	public void pickUpObject(int i) {
		
		if(i != 999) {
			String objectName = gp.obj[i].name;
			
			switch(objectName) {
			case "Key": //picks up a key
				gp.playSoundEffect(1);
				numKey++;
				gp.obj[i] = null;
				gp.ui.showMessage("You obtained a key!");
				break;
			case "Door": //opens door if player has a key
				if(numKey > 0) {
					gp.playSoundEffect(3);
					gp.obj[i] = null;
					numKey--;
					gp.ui.showMessage("You opened the door with the key!");
				}
				else{
					gp.ui.showMessage("You need a key to open the door!");
				}
				break;
			case "Boots":
				gp.playSoundEffect(2);
				speed += 1;
				gp.obj[i] = null;
				gp.ui.showMessage("Your movement speed has increased!");
				break;
			case "Chest":
				gp.ui.gameFinished = true;
				gp.stopMusic();
				gp.playSoundEffect(4);
				break;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		
		//image based on direction, switches for movement
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
		
		g2.drawImage(image, screenX, screenY, null);
		
		//DEBUG
		//check collision rectangle
		g2.setColor(Color.red);
		g2.drawRect(screenX + collisionArea.x, screenY + collisionArea.y, collisionArea.width, collisionArea.height);
		
	}
}
