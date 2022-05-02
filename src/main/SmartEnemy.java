package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class SmartEnemy extends GameObject{
	
private Handler handler;
private GameObject player;
	
	public SmartEnemy(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		for(int i = 0; i < handler.Object.size(); i++) {
			if(handler.Object.get(i).getId() == ID.Player) player = handler.Object.get(i);
		}
		
//		velX = 5;
//		velY = 5;
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		float diffX = x - player.getX() - 8;
		float diffY = y - player.getY() - 8;
		float distance = (float) Math.sqrt((x-player.getX())*(x-player.getX()) + (y-player.getY())*(y-player.getY()));
		
		velX = ((-1/distance) * diffX);
		velY = ((-1/distance) * diffY);
 		
//		if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
//		if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
		
		handler.addObject(new Trail(x, y, ID.Trail, Color.green, 16, 16, 0.05f, handler));
	}

	public void render(Graphics g) {
		if(id == ID.SmartEnemy) g.setColor(Color.green);
		//else if(id == ID.Player2) g.setColor(Color.blue);
		
		g.fillRect((int)x, (int)y, 16, 16);
	}

}
