package main;

import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;

//places assets such as keys, doors, etc on map
public class AssetPlacer {
	GamePanel gp;
	
	public AssetPlacer(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		int i = 0; 
		
		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = gp.tileSize*25;
		gp.obj[i].worldY = gp.tileSize*19;
		i++;
		
		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = gp.tileSize*21;
		gp.obj[i].worldY = gp.tileSize*19;
		i++;
		
		gp.obj[i] = new OBJ_Key(gp);
		gp.obj[i].worldX = gp.tileSize*26;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		
		gp.obj[i] = new OBJ_Axe(gp);
		gp.obj[i].worldX = gp.tileSize*33;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		
		gp.obj[i] = new OBJ_Shield_Blue(gp);
		gp.obj[i].worldX = gp.tileSize*35;
		gp.obj[i].worldY = gp.tileSize*21;
		i++;
		
		gp.obj[i] = new OBJ_Potion_Red(gp);
		gp.obj[i].worldX = gp.tileSize*22;
		gp.obj[i].worldY = gp.tileSize*27;
		i++;
		
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
