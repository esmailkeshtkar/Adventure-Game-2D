package main;

import java.awt.Rectangle;

public class EventHandler {
	GamePanel gp;
	EventRect  eventRect[][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxWorldCol][gp.maxWorldRow];
		
		int col = 0;
		int row = 0;
		while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
			eventRect[col][row] = new EventRect();
			eventRect[col][row].x = 23;
			eventRect[col][row].y = 23;
			eventRect[col][row].height = 5;
			eventRect[col][row].width = 5;
			eventRect[col][row].eventRectDefaultX = eventRect[col][row].x;
			eventRect[col][row].eventRectDefaultY = eventRect[col][row].y;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
			}
		}
		
	}
	
	public void checkEvent() {
		
		//check if the player character has moved more than 1 tile away from the last event to reset the event
		int xDistance = Math.abs(gp.player.worldX - previousEventX);
		int yDistance = Math.abs(gp.player.worldY - previousEventY);
		int distance = Math.max(xDistance, yDistance);
		if(distance > gp.tileSize) {
			canTouchEvent = true;
		}
		
		if(canTouchEvent == true) {
			if(hit(27,16,"right") == true) { damagePit(27, 16, gp.dialogueState); }
			if(hit(23,19,"any") == true) { damagePit(27, 16, gp.dialogueState); }
			//if(hit(27, 16, "right") == true) {teleport(gp.dialogueState); }
			if(hit(23,12, "up") == true) {healingPool(23, 12, gp.dialogueState); }
		}
		
	}
	
	//checks if player is colliding with event rectangle
	public boolean hit(int col, int row, String reqDirection) {
		
		boolean hit = false;
		gp.player.collisionArea.x = gp.player.worldX + gp.player.collisionArea.x;
		gp.player.collisionArea.y = gp.player.worldY + gp.player.collisionArea.y;
		eventRect[col][row].x = col*gp.tileSize + eventRect[col][row].x;
		eventRect[col][row].y = row*gp.tileSize + eventRect[col][row].y;
		
		if(gp.player.collisionArea.intersects(eventRect[col][row]) && eventRect[col][row].eventDone == false) {
			if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
				hit = true;
				
				previousEventX = gp.player.worldX;
				previousEventY = gp.player.worldY;
			}
		}
		
		//reset collision rectangles
		gp.player.collisionArea.x = gp.player.collisionAreaDefaultX;
		gp.player.collisionArea.y = gp.player.collisionAreaDefaultY;
		eventRect[col][row].x = eventRect[col][row].eventRectDefaultX;
		eventRect[col][row].y = eventRect[col][row].eventRectDefaultY;
		
		return hit;
	}
	
	public void teleport(int gameState) {
		gp.gameState = gameState;
		gp.ui.currentDialogue = "Teleport!";
		gp.player.worldX = gp.tileSize*37;
		gp.player.worldY = gp.tileSize*10;
		
	}
	
	public void damagePit(int col, int row, int gameState) {
		
		gp.gameState = gameState;
		gp.playSoundEffect(6);;
		gp.ui.currentDialogue = "You fall into a pit!";
		gp.player.health--;
		//eventRect[col][row].eventDone = true;
		canTouchEvent = false;
		
	}
	
	public void healingPool(int col, int row, int gameState) {
		
		if(gp.keyH.ePressed == true) {
			gp.gameState = gameState;
			gp.player.atkCanceled = true;
			gp.playSoundEffect(2);
			gp.ui.currentDialogue = "You drink the water.\nYour health and mana have been fully \nrestored.";
			gp.player.health = gp.player.maxHealth;
			gp.player.mana = gp.player.maxMana;
			gp.aPlacer.setMonster();//respawns monsters
		}
	}
	
}
