package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.util.Random;

public class Player extends GameObject{
	
	Random r = new Random();
	Handler handler;
	
	
	public Player(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
//		velX = r.nextInt(5) * 1;
//		velY = r.nextInt(5);
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 32, 32); //need to cast as int bc they are float before
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		x = Game.clamp(x, 0, Game.WIDTH - 44);
		y = Game.clamp(y, 0, Game.HEIGHT - 66);
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.white, 32, 32, 0.08f, handler));
		collision();
	}

	public void collision() {
		for(int i = 0; i < handler.Object.size(); i++) {
			
			GameObject tempObject = handler.Object.get(i);
			
			if(tempObject.getId() == ID.BasicEnemy || tempObject.getId() == ID.FastEnemy || tempObject.getId() == ID.SmartEnemy) {    //Look at it like tempObject is now basic enemy
				if(getBounds().intersects(tempObject.getBounds())) {
					//collision code
					HUD.HEALTH -= 2;
				}
			}
		}
	}
	
	public void render(Graphics g) {
		
		if(id == ID.Player) g.setColor(Color.white);
		//else if(id == ID.Player2) g.setColor(Color.blue);
		
		g.fillRect((int)x, (int)y, 32, 32);
	}
	
	
}
