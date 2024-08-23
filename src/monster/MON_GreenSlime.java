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
		speed = 1;
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
	
	public void setAction() {

		//locks action for 120 frames or 2 seconds
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
		

		int i = new Random().nextInt(100)+1;
		if(i > 99 && projectile.alive == false && shotAvailableCounter == 30) {
			projectile.set(worldX, worldY, direction, true,  this);
			gp.projectileList.add(projectile);
			shotAvailableCounter = 0;
		}
	}
	
	//runs away from the player
	public void dmgReaction() {
		
		actionLockCounter = 0;
		direction = gp.player.direction;
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
