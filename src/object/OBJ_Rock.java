package object;

import java.awt.Color;

import entity.Entity;
import entity.Projectile;
import main.GamePanel;

public class OBJ_Rock extends Projectile{

	GamePanel gp;
	
	public OBJ_Rock(GamePanel gp) {
		super(gp);
		this.gp=gp;
		
		name = "Rock";
		speed = 4;
		maxHealth = 80;
		health = maxHealth;
		atk = 2;
		useCost = 1;
		alive = false;
		getImage();
		
	}
	
	public void getImage() {
		up1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		up2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		down2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		left2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right1 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
		right2 = setup("/projectile/rock_down_1", gp.tileSize, gp.tileSize);
	}
	
	public boolean hasResource(Entity user) {
		
		boolean hasResource = false;
		
		if(user.ammo >= useCost) {
			hasResource = true;
		}
		
		return hasResource;
	}
	
	public void useResource(Entity user) {
		user.ammo -= useCost;
	}

	public Color getParticleColor() {
		Color color = new Color(40, 50, 0);
		return color;
	}
	
	public int getParticleSize() {
		int size = 10; //6 pixels
		return size;
	}
	
	public int getParticleSpeed() {
		int speed = 1;
		return speed;
	}
	
	public int getParticleMaxHealth() {
		int maxHealth = 15; //how long the particle lasts
		return maxHealth;
	}
}
