package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;
import object.OBJ_Coin_Bronze;
import object.OBJ_Heart;
import object.OBJ_ManaCrystal;
import object.OBJ_Rock;

public class MON_GreenSlime extends Entity{

	GamePanel gp;
	
	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		this.gp = gp;
		
		type = type_monster;
		name = "Green Slime";
		defaultSpeed = 1;
		speed = defaultSpeed;
		maxHealth = 5;
		health = maxHealth;
		atk = 5;
		def = 0;
		exp = 1;
		
		projectile = new OBJ_Rock(gp);
		collisionArea.x = 3;
		collisionArea.y = 26;
		collisionArea.width = 60;
		collisionArea.height = 50;
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		getImage();
	}
	
	public void getImage() {
		
		up1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/monster/greenslime_down_2", gp.tileSize, gp.tileSize);
		down1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/monster/greenslime_down_1", gp.tileSize, gp.tileSize);
	}
	
	public void update() {
		super.update();
		
		int xDistance = Math.abs(worldX- gp.player.worldX);
		int yDistance = Math.abs(worldY- gp.player.worldY);
		int tileDistance = (xDistance+yDistance)/gp.tileSize;
		
		//check if player gets in aggro range
		if(onPath == false && tileDistance < 5) {
			int i = new Random().nextInt(100)+1;
			if(i > 90) {
				onPath = true;
			}
		}
		
		if(onPath == true && tileDistance > 20) {
			onPath = false;
		}
	}
	
	public void setAction() {

		//locks action for 120 frames or 2 seconds
		if(onPath == true) {
			int endCol = (gp.player.worldX + gp.player.collisionArea.x)/gp.tileSize;
			int endRow = (gp.player.worldY + gp.player.collisionArea.y)/gp.tileSize;
			searchPath(endCol, endRow);

			int i = new Random().nextInt(1000)+1;
			if(i > 995 && projectile.alive == false && shotAvailableCounter == 30) {
				projectile.set(worldX, worldY, direction, true,  this);
//				gp.projectileList.add(projectile);
				for(int j = 0; j<gp.projectile[1].length; j++){
					if(gp.projectile[gp.currentMap][j] == null) {
						gp.projectile[gp.currentMap][j] = projectile;
						break;
					}
				}
				shotAvailableCounter = 0;
			}
			
		}
		else {
			actionLockCounter++;
			if(actionLockCounter >= 120) {
				//simple random AI for direction
				Random random = new Random();
				int i = random.nextInt(4)+1; //pick a number from 1 to 100
				if(i == 1) {
					direction = "up";
				}
				if(i == 2) {
					direction = "down";
				}
				if(i == 3) {
					direction = "left";
				}
				if(i == 4) {
					direction = "right";
				}
				actionLockCounter = 0;
			}
		}
	}
	
	//runs away from the player
	public void dmgReaction() {
		
		actionLockCounter = 0;
		onPath = true;
	}
	
	public void checkDrop(){
		//RANDOM VALUE TO DETERMINE DROP
		int i = new Random().nextInt(10)+1;
		//SET ITEM DROP
		if(i < 7) {dropItem(new OBJ_Coin_Bronze(gp));}
		if(i>=7 && i < 9) {dropItem(new OBJ_Heart(gp));}
		if(i>=9) {dropItem(new OBJ_ManaCrystal(gp));}
	}

}
