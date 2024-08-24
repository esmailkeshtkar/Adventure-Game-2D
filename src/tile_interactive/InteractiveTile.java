package tile_interactive;

import entity.Entity;
import main.GamePanel;

public class InteractiveTile extends Entity{
	
	GamePanel gp;
	public boolean destructible = false;

	public InteractiveTile(GamePanel gp, int col, int row) {
		super(gp);
		this.gp = gp;
	}
	
	public boolean correctWpn(Entity entity) {
		boolean correctWpn = false;
		return correctWpn;
	}
	
	public void playSoundEffect() {
	}
	
	public InteractiveTile getDestroyedForm() {
		InteractiveTile tile = null;
		return tile;
	}
	
	public void update() {
		if(invincible == true) {
			invincibleCounter++;
			if(invincibleCounter > 30) {
				invincible = false;
				invincibleCounter = 0;
			}
		}
	}

}
