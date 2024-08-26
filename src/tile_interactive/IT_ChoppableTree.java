package tile_interactive;

import java.awt.Color;

import entity.Entity;
import main.GamePanel;

public class IT_ChoppableTree extends InteractiveTile{

	public IT_ChoppableTree(GamePanel gp, int col, int row) {
		super(gp, col, row);
		this.gp = gp;
		this.worldX = gp.tileSize * col;
		this.worldY = gp.tileSize * row;
		down1 = setup("/tiles_interactive/drytree", gp.tileSize, gp.tileSize);
		destructible = true;
		health = 3;
	}
	
	public boolean correctWpn(Entity entity) {
		boolean correctWpn = false;
		if(entity.currentWpn.type == type_axe) {
			correctWpn = true;
		}
		return correctWpn;
	}
	
	public void playSoundEffect() {
		gp.playSoundEffect(11);
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = new IT_Trunk(gp, worldX/gp.tileSize, worldY/gp.tileSize);
		return tile;
	}
	
	public Color getParticleColor() {
		Color color = new Color(65, 50, 30);
		return color;
	}
	
	public int getParticleSize() {
		int size = 6; //6 pixels
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	
	public int getParticleMaxHealth() {
		int maxHealth = 20; //how long the particle lasts
		return maxHealth;
	}

}
