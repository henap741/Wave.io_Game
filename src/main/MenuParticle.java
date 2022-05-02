package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

public class MenuParticle extends GameObject{
	
	private Handler handler;
	Random r = new Random();

	private Color col;
	int dir = 0;
	
	public MenuParticle(int x, int y, ID id, Handler handler) {
		super(x, y, id);
		this.handler = handler;
		
		velX = (r.nextInt(10 - -10) + -10);
		velY = (r.nextInt(10 - -10) + -10);
		if(velX == 0) velX = 1;
		if(velY == 0) velY = 1;
//		dir = r.nextInt(2);   
//		if(dir == 0) {
//			velX = 9;
//			velY = 9;
//		} else if(dir == 1) {
//			velX = 9;
//			velY = 2;
//		}

		col = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
	}
	
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, 16, 16);
	}
	
	public void tick() {
		x += velX;
		y += velY;
		
		if(y <= 0 || y >= Game.HEIGHT - 32) velY *= -1;
		if(x <= 0 || x >= Game.WIDTH - 16) velX *= -1;
		
		handler.addObject(new Trail(x, y, ID.Trail, col, 16, 16, 0.05f, handler));
	}

	public void render(Graphics g) {
		g.setColor(col);
		//else if(id == ID.Player2) g.setColor(Color.blue);
		
		g.fillRect((int)x, (int)y, 16, 16);
	}

}
