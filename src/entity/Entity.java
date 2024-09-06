package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

//Abstract class, a blueprint for player and character classes
public class Entity {
	
	GamePanel gp;
	
	//an image with an accessible buffer of image data, stores image files
	public BufferedImage image, image2, image3;
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2; 
	public BufferedImage atkUp1, atkUp2, atkDown1, atkDown2, atkLeft1, atkLeft2, 
	atkRight1, atkRight2;
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
	public int shotAvailableCounter = 0;
	boolean hpBarOn = false;
	public boolean interactiveTile = false;
	public boolean onPath = false;
	public boolean knockback = false;
	
	//COUNTERS
	public int spriteCounter = 0;
	public int actionLockCounter = 0;
	public int invincibleCounter = 0;
	int dyingCounter = 0;
	int hpBarCounter = 0;
	int knockbackCounter;
	
	//CHARACTER ATTRIBUTES
	
	public String name;
	public int maxHealth, health;
	public int defaultSpeed;
	public int maxMana, mana, ammo;
	public int speed;
	public int level, exp, nextLvlExp;
	public int str, vit;
	public int atk, def;
	public int coins;
	public int value;
	public Entity currentWpn;
	public Entity currentShield;
	public Projectile projectile;
	
	// ITEM ATTRIBUTES
	public ArrayList<Entity> inventory = new ArrayList<Entity>();
	public final int maxInventorySize = 20;
	public int atkValue, defValue;
	public String description = "";
	public int useCost; //cost to shoot projectile
	public int price;
	public int knockbackStr = 0;
	
	//TYPE
	public int type;
	public final int type_player = 0;
	public final int type_npc = 1;
	public final int type_monster = 2;
	public final int type_sword = 3;
	public final int type_axe = 4;
	public final int type_shield = 5;
	public final int type_consumable = 6;
	public final int type_obtainable = 7;
	
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
	
	public void use(Entity entity) {
	}
	
	public void checkDrop() {}
	
	public void dropItem(Entity item) {
		
		//dropped item drops where the enemy is defeated
		for(int i = 0; i < gp.obj[1].length; i++) {
			if(gp.obj[gp.currentMap][i] == null) {
				gp.obj[gp.currentMap][i] = item;
				gp.obj[gp.currentMap][i].worldX = worldX; //defeated enemy's worldX & worldY
				gp.obj[gp.currentMap][i].worldY = worldY;
				break;
			}
		}
	}
	
	public Color getParticleColor() {
		Color color = null;
		return color;
	}
	
	public int getParticleSize() {
		int size = 0;
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 0;
		return speed;
	}
	
	public int getParticleMaxHealth() {
		int maxHealth = 0; //how long the particle lasts
		return maxHealth;
	}
	
	public void generateParticle(Entity generator, Entity target) {
		
		Color color = generator.getParticleColor();
		int size = generator.getParticleSize();
		int speed = generator.getParticleSpeed();
		int maxHealth = generator.getParticleMaxHealth();
		
		Particle p1 = new Particle(gp, target, color, size, speed, maxHealth, -2, -1);
		Particle p2 = new Particle(gp, target, color, size, speed, maxHealth, 2, -1);
		Particle p3 = new Particle(gp, target, color, size, speed, maxHealth, -2, 1);
		Particle p4 = new Particle(gp, target, color, size, speed, maxHealth, 2, 1);
		gp.particleList.add(p1);
		gp.particleList.add(p2);
		gp.particleList.add(p3);
		gp.particleList.add(p4);
	}
	
	public void checkCollision() {

		//check collisions
		collisionOn = false;
		gp.cDetector.checkTile(this);
		gp.cDetector.checkObject(this, false);
		gp.cDetector.checkEntity(this, gp.npc);
		gp.cDetector.checkEntity(this, gp.mon);
		gp.cDetector.checkEntity(this, gp.iTile);
		boolean contactPlayer = gp.cDetector.checkPlayer(this);
		
		//if monster is contacting player
		if(this.type == type_monster && contactPlayer == true) {
			dmgPlayer(atk);
		}
	}
	
	public void update() {
		
		
		
		if(knockback == true) {
			checkCollision();
			if(collisionOn == true || knockbackCounter >= 3) {
				knockbackCounter = 0;
				knockback = false;
				speed = defaultSpeed;
			}else if(collisionOn == false) {
				switch(gp.player.direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			knockbackCounter++;
		}
		else {
			setAction();
			checkCollision();
			if(collisionOn == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
		}
		
		
		
		//used to alternate the sprite's for movement
		spriteCounter++;
		if(spriteCounter > 24)
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
		if(shotAvailableCounter < 30) {
			shotAvailableCounter++;
		}
	}
	
	public void dmgPlayer(int atk) {
		if(gp.player.invincible == false) {
			//damage can be received
			gp.playSoundEffect(6);
			
			int dmg = atk - gp.player.def;
			if(dmg < 0) { dmg = 0; }//so dmg does not go negative and heal
			gp.player.health-=dmg;
			
			gp.player.invincible = true;
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
			if(type == 2 && hpBarOn == true) {
				double oneScale = (double)gp.tileSize/maxHealth;
				double hpValue = oneScale*health;
				
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
			
			
			if(invincible == true && type == type_monster) {
				hpBarOn = true;
				hpBarCounter = 0;
				changeAlpha(g2, .4f);
			}
			
			//dying animation
			if(dying == true) {
				dyingAnimation(g2);
			}
			
			g2.drawImage(image, screenX, screenY, null);
			
			changeAlpha(g2, 1f);
		}
		
		//draw collision rectangle
		g2.setColor(Color.red);
		g2.drawRect(screenX + collisionArea.x, screenY + collisionArea.y, collisionArea.width, collisionArea.height);
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
		
		if(dyingCounter >= 160)	{ alive = false; }
		
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
	
	public void searchPath(int endCol, int endRow) {
		
		int startCol = (worldX+collisionArea.x)/gp.tileSize;
		int startRow = (worldY+collisionArea.y)/gp.tileSize;
		
		gp.pFinder.setNodes(startCol, startRow, endCol, endRow);
		if(gp.pFinder.search() == true) {
			
			//Next WorldX & WorldY
			int nextX = gp.pFinder.pathList.get(0).col*gp.tileSize;
			int nextY = gp.pFinder.pathList.get(0).row*gp.tileSize;
			
			//Entity's collision area positions
			int enLeftX = worldX+collisionArea.x;
			int enRightX = worldX+collisionArea.x + collisionArea.width;
			int enTopY = worldY+collisionArea.y;
			int enBottomY = worldY+collisionArea.y + collisionArea.height;
			
			if(enTopY > nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "up";
			}
			else if(enTopY < nextY && enLeftX >= nextX && enRightX < nextX + gp.tileSize) {
				direction = "down";
			}
			else if(enTopY >= nextY && enBottomY < nextY + gp.tileSize) {
				//direction is left or right
				if(enLeftX > nextX) {
					direction = "left";
				}
				if(enLeftX < nextX) {
					direction = "right";
				}
			}
			else if(enTopY > nextY && enLeftX > nextX) {
				//direction is up or left
				direction = "up";
				checkCollision();
				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if(enTopY > nextY && enLeftX < nextX) {
				//direction is up or right
				direction = "up";
				if(collisionOn == true) {
					direction = "right";
				}
			}
			else if(enTopY < nextY && enLeftX > nextX) {
				//direction is down or left
				direction = "down";
				if(collisionOn == true) {
					direction = "left";
				}
			}
			else if(enTopY < nextY && enLeftX < nextX) {
				//direction is down or right
				direction = "down";
				if(collisionOn == true) {
					direction = "right";
				}
			}
			
			//when the entity reaches the end of the path, ends the pathfinder
//			int nextCol = gp.pFinder.pathList.get(0).col;
//			int nextRow = gp.pFinder.pathList.get(0).row;
//			if(nextCol == endRow && nextRow == endRow) {
//				onPath = false;
//			}
		}
	}

}
