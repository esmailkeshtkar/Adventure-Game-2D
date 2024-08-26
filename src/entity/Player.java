package entity;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import main.GamePanel;
import main.KeyHandler;
import object.OBJ_Fireball;
import object.OBJ_Key;
import object.OBJ_Rock;
import object.OBJ_Shield_Wood;
import object.OBJ_Sword_Normal;

//manages the player entity
public class Player extends Entity{
	
	KeyHandler keyH;
	
	public final int screenX; 
	public final int screenY;
	int standCounter = 0;
	public boolean atkCanceled = false;
	public ArrayList<Entity> inventory = new ArrayList<Entity>();
	public final int maxInventorySize = 20;
	
	public Player(GamePanel gp, KeyHandler keyH) {
		
		super(gp);//calling constructor of super class
		
		this.keyH = keyH;
		
		//displays character at the center of the screen
		screenX = gp.screenWidth/2- (gp.tileSize/2);
		screenY = gp.screenHeight/2- (gp.tileSize/2);
		
		//collision area rectangle
		collisionArea = new Rectangle();
		
		collisionArea.x = 16;//starting x
		collisionArea.y = 40;//starting y
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		collisionArea.width = 48;//width of collision area
		collisionArea.height = 32;//height of collision area
		
		setDefaultValues();
		getPlayerImage();
		getPlayerAtkImage();
		setItems();
	}
	
	public void setDefaultValues() {
		
		worldX = gp.tileSize * 23;
		worldY = gp.tileSize * 21;
//		worldX = gp.tileSize * 10;
//		worldY = gp.tileSize * 13;
		
		speed = 6;//how many pixels the character moves per frame
		direction = "down";
		
		//PLAYER STATUS
		maxHealth = 6;
		health = maxHealth;
		maxMana = 4;
		mana = maxMana;
		ammo = 10;
		level = 1;
		str = 1; //more str = more damage dealt
		vit = 1; //more dex = less dmg taken
		exp = 0;
		nextLvlExp = 5;
		coins = 0;
		currentWpn = new OBJ_Sword_Normal(gp);
		currentShield = new OBJ_Shield_Wood(gp);
		atk = getAtk(); //atk is decided by str and weapon
		def = getDef(); //def is decided by vit and shield
		projectile = new OBJ_Fireball(gp);
		
	}
	
	public void setItems() {
		
		inventory.add(null);
		inventory.add(currentWpn);
		inventory.add(currentShield);
		inventory.add(new OBJ_Key(gp));
	}
	
	private int getDef() {
		return vit * currentShield.defValue;
	}

	private int getAtk() {
		atkArea = currentWpn.atkArea;
		return str*currentWpn.atkValue;
	}

	//stores the player images for each direction
	public void getPlayerImage() {
		
		up1 = setup("/player/boy_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/player/boy_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/player/boy_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/player/boy_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/player/boy_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/player/boy_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/player/boy_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/player/boy_right_2", gp.tileSize, gp.tileSize);
	}
	
	public void getPlayerAtkImage() {
		
		if(currentWpn.type == type_sword) {
			atkUp1 = setup("/player/boy_attack_up_1", gp.tileSize, gp.tileSize*2);
			atkUp2 = setup("/player/boy_attack_up_2", gp.tileSize, gp.tileSize*2);
			atkDown1 = setup("/player/boy_attack_down_1", gp.tileSize, gp.tileSize*2);
			atkDown2 = setup("/player/boy_attack_down_2", gp.tileSize, gp.tileSize*2);
			atkLeft1 = setup("/player/boy_attack_left_1", gp.tileSize*2, gp.tileSize);
			atkLeft2 = setup("/player/boy_attack_left_2", gp.tileSize*2, gp.tileSize);
			atkRight1 = setup("/player/boy_attack_right_1", gp.tileSize*2, gp.tileSize);
			atkRight2 = setup("/player/boy_attack_right_2", gp.tileSize*2, gp.tileSize);
		}
		
		if(currentWpn.type == type_axe) {
			atkUp1 = setup("/player/boy_axe_up_1", gp.tileSize, gp.tileSize*2);
			atkUp2 = setup("/player/boy_axe_up_2", gp.tileSize, gp.tileSize*2);
			atkDown1 = setup("/player/boy_axe_down_1", gp.tileSize, gp.tileSize*2);
			atkDown2 = setup("/player/boy_axe_down_2", gp.tileSize, gp.tileSize*2);
			atkLeft1 = setup("/player/boy_axe_left_1", gp.tileSize*2, gp.tileSize);
			atkLeft2 = setup("/player/boy_axe_left_2", gp.tileSize*2, gp.tileSize);
			atkRight1 = setup("/player/boy_axe_right_1", gp.tileSize*2, gp.tileSize);
			atkRight2 = setup("/player/boy_axe_right_2", gp.tileSize*2, gp.tileSize);
		}
	}
	
	public void update() {
		
		if(atking == true) {
			attacking();
		}
		else if(keyH.upPressed == true || keyH.downPressed == true 
		|| keyH.leftPressed == true || keyH.rightPressed == true || keyH.ePressed == true) {
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
			
			//Check NPC Collision
			int npcIndex = gp.cDetector.checkEntity(this, gp.npc);
			interactNPC(npcIndex);
			
			//Check Monster Collision
			int monIndex = gp.cDetector.checkEntity(this, gp.mon);
			contactMonster(monIndex);
			
			//Check Interactive tile Collision
			int iTileIndex = gp.cDetector.checkEntity(this, gp.iTile);
			
			//Check Event
			gp.eHandler.checkEvent();
			
			//if tile collision is false, the player can move
			if(collisionOn == false && keyH.ePressed == false) {
				switch(direction) {
				case "up": worldY -= speed; break;
				case "down": worldY += speed; break;
				case "left": worldX -= speed; break;
				case "right": worldX += speed; break;
				}
			}
			
			//player can attack
			if(keyH.ePressed == true && atkCanceled == false) {
				gp.playSoundEffect(7);
				atking = true;
				spriteCounter = 0;
			}
			
			atkCanceled = false;
			gp.keyH.ePressed = false;
			
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
		
		//projectile key pressed, launch projectile
		if(gp.keyH.shotKeyPressed == true && projectile.alive == false 
		&& shotAvailableCounter == 30 && projectile.hasResource(this) == true) {
			
			//SET DEFAULT COORDINATES AND DIRECTION FOR PROJECTILE
			projectile.set(worldX, worldY, direction, true, this);
			
			//SUBTRACT COST OF RESOURCE
			projectile.useResource(this);
			
			//ADD PROJECTILE TO ARRAYLIST
			gp.projectileList.add(projectile);
			gp.playSoundEffect(10);
			shotAvailableCounter = 0;
		}
		
		//Invincible Counter, the period of time the player is invincible after getting hit
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter >= 60) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
		
		if(shotAvailableCounter < 30) {shotAvailableCounter++;}
		if(health > maxHealth) {health = maxHealth;}
		if(health <= 0) {health=0;}
		if(mana > maxMana) {mana = maxMana;}
	}
	
	//attacking sprite counter
	public void attacking() {
		
		spriteCounter++;
		
		if(spriteCounter <= 5) {
			spriteNum = 1;
		}
		if(spriteCounter > 5 && spriteCounter <= 25) {
			spriteNum = 2;
			
			//hit detection
			//store players current worldx, worldy and collision area
			int currentWorldX =  worldX;
			int currentWorldY = worldY;
			int collisionAreaWidth = collisionArea.width;
			int collisionAreaHeight = collisionArea.height;
			
			//Adjust player's worldx and worldy for the atk area
			switch(direction) {
			case "up": worldY -= atkArea.height; break;
			case "down": worldY += atkArea.height; break;
			case "left": worldX -= atkArea.width; break;
			case "right": worldX += atkArea.width; break;
			}
			
			//change collision area to atk area
			collisionArea.width = atkArea.width;
			collisionArea.height = atkArea.height;
			
			//check monster collision with updated world x, y and collision area
			int monIndex = gp.cDetector.checkEntity(this, gp.mon);
			dmgMonster(monIndex, atk);
			
			int iTileIndex = gp.cDetector.checkEntity(this, gp.iTile);
			dmgInteractiveTile(iTileIndex);
			
			//after checking the collision, restore original variables
			worldX = currentWorldX;
			worldY = currentWorldY;
			collisionArea.width = collisionAreaWidth;
			collisionArea.height = collisionAreaHeight;
		}
		if(spriteCounter > 25) {
			spriteNum = 1;
			spriteCounter = 0;
			atking = false;
		}
		
	}
	
	//interactions with objects
	public void pickUpObject(int i) {
		
		if(i != 999) {
			//OBTAINABLE ITEMS
			if(gp.obj[i].type == type_obtainable) {
				gp.obj[i].use(this);
				gp.obj[i] = null;
			}
			else {
				String txt;
				
				if(inventory.size() <= maxInventorySize) {
					inventory.add(gp.obj[i]);
					gp.playSoundEffect(1);
					txt = "Obtained a "+gp.obj[i].name+"!";
				}
				else {
					txt = "Your inventory is full!";
				}
				gp.ui.addMsg(txt);
				gp.obj[i] = null;
			}
		}
		//INVENTORY ITEMS
	}
	
	public void interactNPC(int i) {
		
		if(gp.keyH.ePressed == true) {
			if (i != 999) {
				atkCanceled = true;
				gp.gameState = gp.dialogueState;
				gp.npc[i].speak();
			}
		}
		
	}
	
	public void contactMonster(int i) {
		if(i != 999) {
			//player receives damage only when not invincible
			if(invincible == false && gp.mon[i].dying == false) {
				gp.playSoundEffect(6); //damaged SE
				
				int dmg = gp.mon[i].atk - def;
				if(dmg < 0) { dmg = 0; }//so dmg does not go negative and heal
				health-=dmg;
				
				invincible = true;
			}
		}
	}
	
	public void dmgMonster(int i, int atk) {
		
		if(i != 999) {
			if(gp.mon[i].invincible == false) {
				gp.playSoundEffect(5); //hit monster SE
				
				int dmg = atk - gp.mon[i].def;
				if(dmg < 0) { dmg = 0; }//so dmg does not go negative and heal
				gp.mon[i].health-= dmg;
				gp.ui.addMsg("Dealt " + dmg + " dmg to the " + gp.mon[i].name+"!");
				
				gp.mon[i].invincible = true;
				gp.mon[i].dmgReaction();
				
				if(gp.mon[i].health <= 0) {
					gp.mon[i].dying = true;
					gp.ui.addMsg("Defeated the "+gp.mon[i].name+"!");
					gp.ui.addMsg("Obtained "+gp.mon[i].exp+" EXP!");
					exp+= gp.mon[i].exp;
					checkLvlUp();
				}
			}
		}
	}
	
	public void dmgInteractiveTile(int i) {
		if(i != 999 && gp.iTile[i].destructible == true && gp.iTile[i].correctWpn(this) == true &&
		gp.iTile[i].invincible == false) {
			gp.iTile[i].playSoundEffect();
			gp.iTile[i].health--;
			gp.iTile[i].invincible = true;
			
			//Generate particle
			generateParticle(gp.iTile[i], gp.iTile[i]);
			
			if(gp.iTile[i].health <= 0) {
				gp.iTile[i] = gp.iTile[i].getDestroyedForm();
			}
		}
	}
	
	public void checkLvlUp() {
		if(exp >= nextLvlExp) {
			level++;
			nextLvlExp *= 3;
			maxHealth += 2;
			str++;
			vit++;
			atk = getAtk();
			def = getDef();
			gp.playSoundEffect(8);
			gp.gameState = gp.dialogueState;
			gp.ui.currentDialogue = "You leveled up! You are now level " +level+"!\n Your stats have increased!";
		}
	}
	
	public void selectItem() {
		
		int itemIndex = gp.ui.getItemIndex();
		
		if(itemIndex < inventory.size()) {
			
			Entity selectedItem = inventory.get(itemIndex);
			
			if(selectedItem.type == type_sword || selectedItem.type == type_axe) {
				currentWpn = selectedItem;
				atk = getAtk();
				getPlayerAtkImage();
			}
			if(selectedItem.type == type_shield) {
				currentShield = selectedItem;
				def = getDef();
			}
			if(selectedItem.type == type_consumable) {
				selectedItem.use(this);
				inventory.remove(itemIndex);
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		
		//g2.setColor(Color.white);
		//g2.fillRect(x, y, gp.tileSize, gp.tileSize);
		
		BufferedImage image = null;
		//temporary variables used for attacking up and attacking left
		//so that the character image does not slide
		int tempScreenX = screenX;
		int tempScreenY = screenY;
		
		//image based on direction, switches for movement and attacking
		switch(direction) {
		case "up":
			if(atking == false) {
				if(spriteNum == 1) {image = up1;}
				if(spriteNum == 2) {image = up2;}
			}
			if(atking == true) {
				tempScreenY  = screenY-gp.tileSize;
				if(spriteNum == 1) {image = atkUp1;}
				if(spriteNum == 2) {image = atkUp2;}
			}
			break;
		case "down":
			if(atking == false) {
				if(spriteNum == 1) {image = down1;}
				if(spriteNum == 2) {image = down2;}
			}
			if(atking == true) {
				if(spriteNum == 1) {image = atkDown1;}
				if(spriteNum == 2) {image = atkDown2;}
			}
			break;
		case "left":
			if(atking == false) {
				if(spriteNum == 1) {image = left1;}
				if(spriteNum == 2) {image = left2;}
			}
			if(atking == true) {
				tempScreenX = screenX - gp.tileSize;
				if(spriteNum == 1) {image = atkLeft1;}
				if(spriteNum == 2) {image = atkLeft2;}
			}
			break;
		case "right":
			if(atking == false) {
				if(spriteNum == 1) {image = right1;}
				if(spriteNum == 2) {image = right2;}
			}
			if(atking == true) {
				if(spriteNum == 1) {image = atkRight1;}
				if(spriteNum == 2) {image = atkRight2;}
			}
			break;
		}
		
		//set opacity level if player is hit to show they are invincible
		if(invincible == true) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
		}
		
		g2.drawImage(image, tempScreenX, tempScreenY, null);

		//reset opacity
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		
		
		//DEBUG
		//check collision rectangle
		g2.setColor(Color.red);
		g2.drawRect(screenX + collisionArea.x, screenY + collisionArea.y, collisionArea.width, collisionArea.height);
//		g2.setFont(new Font("Arial", Font.PLAIN, 26));
//		g2.setColor(Color.white);
//		g2.drawString("Invincible: " +invincibleCounter, gp.tileSize, gp.tileSize*5);
		
	}
}
