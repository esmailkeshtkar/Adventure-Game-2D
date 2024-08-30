package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;
import main.UtilityTool;

//manages tile placements on the world
public class TileManager {

	GamePanel gp;
	public Tile[] tile; //A tile array that stores the different types of tiles
	public int mapTileNum[][][];//a 3D array, first dimension stores the map type, 2nd is x (column) and 3rd is y (row)
	
	public TileManager(GamePanel gp) {
		this.gp = gp;
		tile = new Tile[50]; //total number of tile types
		
		mapTileNum = new int[gp.maxMap][gp.maxWorldCol][gp.maxWorldRow];
		
		getTileImage();
		loadMap("/maps/worldv3.txt", 0);
		loadMap("/maps/interior01.txt", 1);
	}
	
	public void getTileImage() {
		//placeholder tiles 0-9
		for(int i = 0; i <= 10; i++)
		{
			setup(i, "grass00", false);
		}
		//setup actual tiles
		setup(11, "grass01", false);
		setup(12, "water00", true);
		setup(13, "water01", true);
		setup(14, "water02", true);
		setup(15, "water03", true);
		setup(16, "water04", true);
		setup(17, "water05", true);
		setup(18, "water06", true);
		setup(19, "water07", true);
		setup(20, "water08", true);
		setup(21, "water09", true);
		setup(22, "water10", true);
		setup(23, "water11", true);
		setup(24, "water12", true);
		setup(25, "water13", true);
		setup(26, "road00", false);
		setup(27, "road01", false);
		setup(28, "road02", false);
		setup(29, "road03", false);
		setup(30, "road04", false);
		setup(31, "road05", false);
		setup(32, "road06", false);
		setup(33, "road07", false);
		setup(34, "road08", false);
		setup(35, "road09", false);
		setup(36, "road10", false);
		setup(37, "road11", false);
		setup(38, "road12", false);
		setup(39, "earth", false);
		setup(40, "wall", true);
		setup(41, "tree", true);
		setup(42, "hut", false);
		setup(43, "floor01", false);
		setup(44, "table01", true);
		
	}
	
	public void setup(int index, String imageName, boolean collision) {
		
		UtilityTool uTool = new UtilityTool();
		
		try {
			tile[index] = new Tile();
			tile[index].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" +imageName+".png"));
			tile[index].image = uTool.scaleImage(tile[index].image, gp.tileSize, gp.tileSize);
			tile[index].collision = collision;
		}catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	//read map data from a text file line by line
	//adds the map tile type to the array
	//map data is in a specified format
	//i.e. 1 0 1 2 0 0 1 0 
	public void loadMap(String filePath, int map) {
		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is)); //reads content of text file
			
			int col = 0;
			int row = 0;
			
			//adds the tile types read from the input file into the tile array
			while(col < gp.maxWorldCol && row < gp.maxWorldRow) {
				
				String line = br.readLine();
				
				while(col < gp.maxWorldCol) {
					String numbers[] = line.split(" ");
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[map][col][row] = num;
					col++;
				}
				
				if(col == gp.maxWorldCol) {
					col = 0;
					row++;
				}
			}
			
			br.close();
			
		}catch(Exception e) {
			
		}
	}
	
	//draws the world tiles
	public void draw(Graphics2D g2) {
		
		int worldCol = 0;
		int worldRow = 0;
		
		//draws the tile map
		while(worldCol < gp.maxWorldCol && worldRow < gp.maxWorldRow) {
			
			int tileNum = mapTileNum[gp.currentMap][worldCol][worldRow];
			
			//WorldX/Y is the position on map
			//ScreenX/Y is the position we draw
			int worldX = worldCol * gp.tileSize;
			int worldY = worldRow * gp.tileSize;
			int screenX = worldX - gp.player.worldX + gp.player.screenX; //screen position
			int screenY = worldY - gp.player.worldY + gp.player.screenY; //screen position
			
			//boundary of the screen so that the whole map is not drawn at once
			if(worldX + 2*gp.tileSize > gp.player.worldX - gp.player.screenX &&
			   worldX - 2*gp.tileSize < gp.player.worldX + gp.player.screenX &&
			   worldY + 2*gp.tileSize > gp.player.worldY - gp.player.screenY &&
			   worldY - 2*gp.tileSize < gp.player.worldY + gp.player.screenY){
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, null);
			}
			
			worldCol++;
			
			if(worldCol == gp.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}
