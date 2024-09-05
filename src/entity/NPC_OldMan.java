package entity;

import java.awt.Color;
import java.util.Random;

import main.GamePanel;

public class NPC_OldMan extends Entity{
	
	public NPC_OldMan(GamePanel gp){
		super(gp);
		
		direction = "down";
		speed = 2;
		type = type_npc;
		
		getImage();
		setDialogue();
		
		collisionArea.x = gp.tileSize/4;
		collisionArea.y = gp.tileSize/5;
		collisionAreaDefaultX = collisionArea.x;
		collisionAreaDefaultY = collisionArea.y;
		collisionArea.width = gp.tileSize/2;
		collisionArea.height = (int)(gp.tileSize/1.3);
		
	}
	
	public void getImage() {
		
		up1 = setup("/npc/oldman_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/npc/oldman_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/npc/oldman_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/npc/oldman_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/npc/oldman_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/npc/oldman_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/npc/oldman_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/npc/oldman_right_2", gp.tileSize, gp.tileSize);
	}
	
	//sets the dialogue text for the character
	public void setDialogue() {
		
		dialogues[0] = "Hello, lad.";
		dialogues[1] = "So you've come to this island to find the \ntreasure?";
		dialogues[2] = "I used to be a great wizard now but... \nI'm a bit too old for taking an adventure.";
		dialogues[3] = "Well, good luck to you.";

	}
	
	public void setAction() {
		
		if(onPath == true) {
			
//			int endCol = 12;
//			int endRow = 9;
			//follow player
			int endCol = (gp.player.worldX + gp.player.collisionArea.x)/gp.tileSize;
			int endRow = (gp.player.worldY + gp.player.collisionArea.y)/gp.tileSize;
			searchPath(endCol, endRow);
			
		}else {
			
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
	
	public void speak() {
		super.speak();
		onPath = true;
	}
	
	
}
