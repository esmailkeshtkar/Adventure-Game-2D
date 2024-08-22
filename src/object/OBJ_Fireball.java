package object;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Fireball extends Projectile{

	GamePanel gp;
	
	public OBJ_Fireball(GamePanel gp) {
		super(gp);
		this.gp=gp;
		
		name = "Fireball";
		speed = 8;
		maxHealth = 80;
		health = maxHealth;
		atk = 2;
		useCost = 1;
		alive = false;
		getImage();
		
	}
	
	public void getImage() {
		up1 = setup("/projectile/fireball_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/fireball_up_2", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/fireball_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/fireball_down_2", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/fireball_left_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/fireball_left_2", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/fireball_right_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/fireball_right_2", gp.tileSize, gp.tileSize);
	}
	
	//checks if the user has enough resource to use the fireball
	public boolean hasResource(Entity user) {
		
		boolean hasResource = false;
		
		if(user.mana >= useCost) {
			hasResource = true;
		}
		
		return hasResource;
	}
	
	public void useResource(Entity user) {
		user.mana -= useCost;
	}

}
