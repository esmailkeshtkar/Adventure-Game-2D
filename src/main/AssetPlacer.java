package main;

import entity.NPC_OldMan;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;

//places assets such as keys, doors, etc on map
public class AssetPlacer {
	GamePanel gp;
	
	public AssetPlacer(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
	}
	
	public void setNPC() {
		gp.npc[0] = new NPC_OldMan(gp);
		gp.npc[0].worldX = gp.tileSize*21;
		gp.npc[0].worldY = gp.tileSize*21;
	}
	
}
