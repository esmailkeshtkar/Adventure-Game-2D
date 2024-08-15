package monster;

import java.util.Random;

import entity.Entity;
import main.GamePanel;

public class MON_GreenSlime extends Entity{

	public MON_GreenSlime(GamePanel gp) {
		super(gp);
		
		entityType = 2;
		name = "Green Slime";
		speed = 1;
		maxLife = 4;
		life = maxLife;
		
		collisionArea.x = 3;
		collisionArea.y = 26;
		collisionArea.width = 60;
		collisionArea.height = 50;
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		
		getImage();
	}
	
	public void getImage() {
		
		up1 = setup("/monster/greenslime_down_1");
		up2 = setup("/monster/greenslime_down_2");
		down1 = setup("/monster/greenslime_down_1");
		down2 = setup("/monster/greenslime_down_1");
		left1 = setup("/monster/greenslime_down_1");
		left2 = setup("/monster/greenslime_down_1");
		right1 = setup("/monster/greenslime_down_1");
		right2 = setup("/monster/greenslime_down_1");
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
	}

}
