package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
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
		
//		gp.npc[0] = new NPC_OldMan(gp);
//		gp.npc[0].worldX = gp.tileSize*9;
//		gp.npc[0].worldY = gp.tileSize*10;
	}
	
	public void setMonster() {
		
		int i = 0;
		
		gp.mon[i] = new MON_GreenSlime(gp);
		gp.mon[i].worldX = gp.tileSize*23;
		gp.mon[i].worldY = gp.tileSize*36;
		i++;
		
		gp.mon[i] = new MON_GreenSlime(gp);
		gp.mon[i].worldX = gp.tileSize*23;
		gp.mon[i].worldY = gp.tileSize*37;
		
		gp.mon[i] = new MON_GreenSlime(gp);
		gp.mon[i].worldX = gp.tileSize*23;
		gp.mon[i].worldY = gp.tileSize*38;
		i++;
		
		gp.mon[i] = new MON_GreenSlime(gp);
		gp.mon[i].worldX = gp.tileSize*23;
		gp.mon[i].worldY = gp.tileSize*39;
		i++;
		
		gp.mon[i] = new MON_GreenSlime(gp);
		gp.mon[i].worldX = gp.tileSize*23;
		gp.mon[i].worldY = gp.tileSize*36;
		i++;
		
		gp.mon[i] = new MON_GreenSlime(gp);
		gp.mon[i].worldX = gp.tileSize*23;
		gp.mon[i].worldY = gp.tileSize*40;
		i++;
		
		
//		gp.mon[0] = new MON_GreenSlime(gp);
//		gp.mon[0].worldX = gp.tileSize*11;
//		gp.mon[0].worldY = gp.tileSize*10;
//		
//		gp.mon[1] = new MON_GreenSlime(gp);
//		gp.mon[1].worldX = gp.tileSize*11;
//		gp.mon[1].worldY = gp.tileSize*11;
		
		
	}
	
}
