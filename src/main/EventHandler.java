package main;

import entity.Entity;

public class EventHandler {
	GamePanel gp;
	EventRect  eventRect[][][];
	
	int previousEventX, previousEventY;
	boolean canTouchEvent = true;
	int tempMap, tempCol, tempRow;
	
	public EventHandler(GamePanel gp) {
		this.gp = gp;
		
		eventRect = new EventRect[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		int map = 0;
		int col = 0;
		int row = 0;
		while(map < gp.maxMap && col < gp.maxWorldCol && row < gp.maxWorldRow) {
			eventRect[map][col][row] = new EventRect();
			eventRect[map][col][row].x = 23;
			eventRect[map][col][row].y = 23;
			eventRect[map][col][row].height = 5;
			eventRect[map][col][row].width = 5;
			eventRect[map][col][row].eventRectDefaultX = eventRect[map][col][row].x;
			eventRect[map][col][row].eventRectDefaultY = eventRect[map][col][row].y;
			
			col++;
			if(col == gp.maxWorldCol) {
				col = 0;
				row++;
				
				if(row == gp.maxWorldRow) {
					row = 0;
					map++;
				}
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
			if(hit(0,27,16,"right") == true) { damagePit(gp.dialogueState); }
			//if(hit(27, 16, "right") == true) {teleport(gp.dialogueState); }
			else if(hit(0,23,12, "up") == true) {healingPool(gp.dialogueState); }
			else if(hit(0,10,39,"any") == true) {teleport(1, 12,13);} //go to interior dungeon at 10,39
			else if(hit(1, 12,13,"any") == true) {teleport(0,10,39);} //return to field map
			else if(hit(1,12,9,"up") == true) {speak(gp.npc[1][0]);}
		}
		
	}
	
	//checks if player is colliding with event rectangle
	public boolean hit(int map, int col, int row, String reqDirection) {
		
		boolean hit = false;
		if(map == gp.currentMap) {
			gp.player.collisionArea.x = gp.player.worldX + gp.player.collisionArea.x;
			gp.player.collisionArea.y = gp.player.worldY + gp.player.collisionArea.y;
			eventRect[map][col][row].x = col*gp.tileSize + eventRect[map][col][row].x;
			eventRect[map][col][row].y = row*gp.tileSize + eventRect[map][col][row].y;
			
			if(gp.player.collisionArea.intersects(eventRect[map][col][row]) && eventRect[map][col][row].eventDone == false) {
				if(gp.player.direction.contentEquals(reqDirection) || reqDirection.contentEquals("any")) {
					hit = true;
					
					previousEventX = gp.player.worldX;
					previousEventY = gp.player.worldY;
				}
			}
			
			//reset collision rectangles
			gp.player.collisionArea.x = gp.player.collisionAreaDefaultX;
			gp.player.collisionArea.y = gp.player.collisionAreaDefaultY;
			eventRect[map][col][row].x = eventRect[map][col][row].eventRectDefaultX;
			eventRect[map][col][row].y = eventRect[map][col][row].eventRectDefaultY;
		}
		
		return hit;
	}
	
	public void teleport(int map, int col, int row) {
		
		gp.gameState = gp.transitionState;
		tempMap = map;
		tempCol = col;
		tempRow = row;
		canTouchEvent = false;
		gp.playSoundEffect(13);
	}
	
	public void damagePit(int gameState) {
		
		gp.gameState = gameState;
		gp.playSoundEffect(6);;
		gp.ui.currentDialogue = "You fall into a pit!";
		gp.player.health--;
		//eventRect[col][row].eventDone = true;
		canTouchEvent = false;
		
	}
	
	public void healingPool(int gameState) {
		
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
	
	public void speak(Entity entity) {
		if(gp.keyH.ePressed == true) {
			gp.gameState = gp.dialogueState;
			gp.player.atkCanceled = true;
			entity.speak();
		}
	}
	
}
