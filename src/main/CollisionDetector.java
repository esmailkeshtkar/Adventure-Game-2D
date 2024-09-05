package main;

import entity.Entity;

//Class for detecting collision with tiles and objects
public class CollisionDetector {
	
	GamePanel gp;
	
	public CollisionDetector(GamePanel gp) {
		this.gp = gp;
	}
	
	public void checkTile(Entity entity) {
		
		//4 areas to check collision, left X, right X, top Y, bottom Y
		int entityLeftWorldX = entity.worldX + entity.collisionArea.x;
		int entityRightWorldX = entity.worldX + entity.collisionArea.x + entity.collisionArea.width;
		int entityTopWorldY = entity.worldY + entity.collisionArea.y;
		int entityBottomWorldY = entity.worldY + entity.collisionArea.y + entity.collisionArea.height;
		
		int entityLeftCol = entityLeftWorldX/gp.tileSize;
		int entityRightCol = entityRightWorldX/gp.tileSize;
		int entityTopRow = entityTopWorldY/gp.tileSize;
		int entityBottomRow = entityBottomWorldY/gp.tileSize;
		
		int tileNum1, tileNum2;
		
		//detecting collision based on the direction the player is moving
		switch(entity.direction) {
		case "up":
			entityTopRow = (entityTopWorldY - entity.speed)/gp.tileSize; //predict where player will be after the player will move to detect collision
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "down":
			entityBottomRow = (entityBottomWorldY + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "left":
			entityLeftCol = (entityLeftWorldX - entity.speed)/gp.tileSize; 
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityLeftCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		case "right":
			entityRightCol = (entityRightWorldX + entity.speed)/gp.tileSize;
			tileNum1 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityTopRow];
			tileNum2 = gp.tileM.mapTileNum[gp.currentMap][entityRightCol][entityBottomRow];
			if(gp.tileM.tile[tileNum1].collision == true || gp.tileM.tile[tileNum2].collision == true) {
				entity.collisionOn = true;
			}
			break;
		}
	}
	
	public int checkObject(Entity entity, boolean player) {
		
		int index = 999;
		
		//scan object array
		for(int i = 0; i < gp.obj[1].length; i++) {
			
			if(gp.obj[gp.currentMap][i] != null) {
				//Get entity's collision area position
				entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
				entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
				
				//Get the object's collision area position
				gp.obj[gp.currentMap][i].collisionArea.x  = gp.obj[gp.currentMap][i].worldX + gp.obj[gp.currentMap][i].collisionArea.x;
				gp.obj[gp.currentMap][i].collisionArea.y = gp.obj[gp.currentMap][i].worldY + gp.obj[gp.currentMap][i].collisionArea.y;
				
				//checks if objects are colliding
				switch(entity.direction) {
				case "up":
					entity.collisionArea.y -= entity.speed; break;
				case "down":
					entity.collisionArea.y += entity.speed; break;
				case "left":
					entity.collisionArea.x -= entity.speed; break;
				case "right":
					entity.collisionArea.x += entity.speed; break;
				}
				if(entity.collisionArea.intersects(gp.obj[gp.currentMap][i].collisionArea)) {
					if(gp.obj[gp.currentMap][i].collision == true) {
						entity.collisionOn = true;
					}
					if(player == true) {
						index = i;
					}
				}
				
				//reset collision area
				entity.collisionArea.x = entity.collisionAreaDefaultX;
				entity.collisionArea.y = entity.collisionAreaDefaultY;
				gp.obj[gp.currentMap][i].collisionArea.x = gp.obj[gp.currentMap][i].collisionAreaDefaultX;
				gp.obj[gp.currentMap][i].collisionArea.y = gp.obj[gp.currentMap][i].collisionAreaDefaultY;
			}
		}
		
		return index;
	}
	
	//check if player is colliding with NPC or Monster Collision
	public int checkEntity(Entity entity, Entity[][] target) {
		int index = 999;

		//scan object array
		for(int i = 0; i < target[1].length; i++) {
			
			if(target[gp.currentMap][i] != null) {
				//Get entity's collision area position
				
				entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
				entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
				
				//Get the object's collision area position
				target[gp.currentMap][i].collisionArea.x  = target[gp.currentMap][i].worldX + target[gp.currentMap][i].collisionArea.x;
				target[gp.currentMap][i].collisionArea.y = target[gp.currentMap][i].worldY + target[gp.currentMap][i].collisionArea.y;
				
				//checks if objects are colliding
				switch(entity.direction) {
				case "up": entity.collisionArea.y -= entity.speed; break;
				case "down": entity.collisionArea.y += entity.speed; break;
				case "left": entity.collisionArea.x -= entity.speed; break;
				case "right": entity.collisionArea.x += entity.speed; break;
				}
				if(entity.collisionArea.intersects(target[gp.currentMap][i].collisionArea)) {
					if(target[gp.currentMap][i] != entity) {
						entity.collisionOn = true;
						index = i;
					}
				}
				
				//reset collision area
				entity.collisionArea.x = entity.collisionAreaDefaultX;
				entity.collisionArea.y = entity.collisionAreaDefaultY;
				target[gp.currentMap][i].collisionArea.x = target[gp.currentMap][i].collisionAreaDefaultX;
				target[gp.currentMap][i].collisionArea.y = target[gp.currentMap][i].collisionAreaDefaultY;
			}
		}
		return index;
	}
	
	//checks if an NPC or Monster is colliding with the player
	public boolean checkPlayer(Entity entity) {
		
		boolean contactPlayer = false;
		
		if(gp.player != null) {
			//Get entity's collision area position
			entity.collisionArea.x = entity.worldX + entity.collisionArea.x;
			entity.collisionArea.y = entity.worldY + entity.collisionArea.y;
			
			//Get the object's collision area position
			gp.player.collisionArea.x  = gp.player.worldX + gp.player.collisionArea.x;
			gp.player.collisionArea.y = gp.player.worldY + gp.player.collisionArea.y;
			
			//checks if objects are colliding
			switch(entity.direction) {
			case "up": entity.collisionArea.y -= entity.speed; break;
			case "down": entity.collisionArea.y += entity.speed; break;
			case "left": entity.collisionArea.x -= entity.speed; break;
			case "right": entity.collisionArea.x += entity.speed; break;
			}
			
			if(entity.collisionArea.intersects(gp.player.collisionArea)) {
				entity.collisionOn = true;
				contactPlayer = true;
			}
			
			//reset collision area
			entity.collisionArea.x = entity.collisionAreaDefaultX;
			entity.collisionArea.y = entity.collisionAreaDefaultY;
			gp.player.collisionArea.x = gp.player.collisionAreaDefaultX;
			gp.player.collisionArea.y = gp.player.collisionAreaDefaultY;
			
		}
		
		return contactPlayer;
	}
}
