package main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {

	LinkedList<GameObject> Object = new LinkedList<GameObject>(); //List of all GameObjects
	
	public void tick() {
		for(int i = 0; i < Object.size(); i++) {
			GameObject tempObject = Object.get(i);
			
			tempObject.tick();
		}
		
	}
	
	public void render(Graphics g) {
		for(int i = 0; i < Object.size(); i++) {
			GameObject tempObject = Object.get(i);
			
			tempObject.render(g);
		}
	}
	
	public void clearEnemys() {
		for(int i = 0; i < Object.size(); i++) {
			GameObject tempObject = Object.get(i);
			
			if(tempObject.getId() == ID.Player)
				Object.clear();
			if(Game.gameState != Game.STATE.End)
			addObject(new Player((int)tempObject.getX(), (int)tempObject.getY(), ID.Player, this));
			
		}
	}
	
	public void addObject(GameObject object) {
		this.Object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.Object.remove(object);
	}
}
