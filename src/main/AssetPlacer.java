package main;

import entity.NPC_Merchant;
import entity.NPC_OldMan;
import monster.MON_GreenSlime;
import object.OBJ_Axe;
import object.OBJ_Boots;
import object.OBJ_Chest;
import object.OBJ_Coin_Bronze;
import object.OBJ_Door;
import object.OBJ_Heart;
import object.OBJ_Key;
import object.OBJ_ManaCrystal;
import object.OBJ_Potion_Red;
import object.OBJ_Shield_Blue;
import tile_interactive.IT_ChoppableTree;

//places assets such as keys, doors, etc on map
public class AssetPlacer {
	GamePanel gp;
	
	public AssetPlacer(GamePanel gp) {
		this.gp = gp;
	}
	
	public void setObject() {
		
		int mapNum = 0;
		int i = 0; 
		
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*25;
		gp.obj[mapNum][i].worldY = gp.tileSize*19;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*21;
		gp.obj[mapNum][i].worldY = gp.tileSize*19;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Coin_Bronze(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*26;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Axe(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*33;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Shield_Blue(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*35;
		gp.obj[mapNum][i].worldY = gp.tileSize*21;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Potion_Red(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*22;
		gp.obj[mapNum][i].worldY = gp.tileSize*27;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_Heart(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*23;
		gp.obj[mapNum][i].worldY = gp.tileSize*27;
		i++;
		
		gp.obj[mapNum][i] = new OBJ_ManaCrystal(gp);
		gp.obj[mapNum][i].worldX = gp.tileSize*24;
		gp.obj[mapNum][i].worldY = gp.tileSize*27;
		i++;

	}
	
	public void setNPC() {
		
		//OVERWORLD MAP
		int mapNum = 0;
		
		gp.npc[mapNum][0] = new NPC_OldMan(gp);
		gp.npc[mapNum][0].worldX = gp.tileSize*21;
		gp.npc[mapNum][0].worldY = gp.tileSize*21;
		
		//MAP #1
		mapNum = 1;
		gp.npc[mapNum][0] = new NPC_Merchant(gp);
		gp.npc[mapNum][0].worldX = gp.tileSize*12;
		gp.npc[mapNum][0].worldY = gp.tileSize*7;
		
//		gp.npc[0] = new NPC_OldMan(gp);
//		gp.npc[0].worldX = gp.tileSize*9;
//		gp.npc[0].worldY = gp.tileSize*10;
	}
	
	public void setMonster() {
		int mapNum = 0;
		int i = 0;
		
		gp.mon[mapNum][i] = new MON_GreenSlime(gp);
		gp.mon[mapNum][i].worldX = gp.tileSize*23;
		gp.mon[mapNum][i].worldY = gp.tileSize*36;
		i++;
		
		gp.mon[mapNum][i] = new MON_GreenSlime(gp);
		gp.mon[mapNum][i].worldX = gp.tileSize*23;
		gp.mon[mapNum][i].worldY = gp.tileSize*37;
		
		gp.mon[mapNum][i] = new MON_GreenSlime(gp);
		gp.mon[mapNum][i].worldX = gp.tileSize*23;
		gp.mon[mapNum][i].worldY = gp.tileSize*38;
		i++;
		
		gp.mon[mapNum][i] = new MON_GreenSlime(gp);
		gp.mon[mapNum][i].worldX = gp.tileSize*23;
		gp.mon[mapNum][i].worldY = gp.tileSize*39;
		i++;
		
		gp.mon[mapNum][i] = new MON_GreenSlime(gp);
		gp.mon[mapNum][i].worldX = gp.tileSize*23;
		gp.mon[mapNum][i].worldY = gp.tileSize*36;
		i++;
		
		gp.mon[mapNum][i] = new MON_GreenSlime(gp);
		gp.mon[mapNum][i].worldX = gp.tileSize*23;
		gp.mon[mapNum][i].worldY = gp.tileSize*40;
		i++;
		
//		gp.mon[0] = new MON_GreenSlime(gp);
//		gp.mon[0].worldX = gp.tileSize*11;
//		gp.mon[0].worldY = gp.tileSize*10;
//		
//		gp.mon[1] = new MON_GreenSlime(gp);
//		gp.mon[1].worldX = gp.tileSize*11;
//		gp.mon[1].worldY = gp.tileSize*11;
	}
	
	public void setInteractiveTile() {
		int mapNum = 0;
		for(int i = 0; i < 7; i++) {
			gp.iTile[mapNum][i] = new IT_ChoppableTree(gp, 27+i,12);
		}
	}
}
