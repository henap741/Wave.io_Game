package main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.Random;

public class Game extends Canvas implements Runnable{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = -7876443063572951875L;

	public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;
	private Thread thread; //entire game gonna run within this thread - not really recommeded but simple
	private boolean running = false;
	
	private Random r;
	private Handler handler;
	private HUD hud;
	private Spawn spawner;
	private Menu menu;
	
	public enum STATE{
		Menu,
		Help,
		Game,
		End
	};
	
	public static STATE gameState = STATE.Menu; //Now we can cast this as value
	
	public Game() {
		handler = new Handler();
		hud = new HUD();
		menu = new Menu(this, handler, hud);
		this.addKeyListener(new KeyInput(handler));
		this.addMouseListener((menu));
		
		new Window(WIDTH, HEIGHT, "Let's Biuld a Game!", this);
		
		spawner = new Spawn(handler, hud);
		menu = new Menu(this, handler, hud);
		r = new Random();
		
		if(gameState == STATE.Game) {
		
		handler.addObject(new Player(WIDTH/2-32, HEIGHT/2-32, ID.Player, handler));
		handler.addObject(new BasicEnemy(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.BasicEnemy, handler));
		} else {
			for(int i = 0; i < 20; i++) { //Amount of particles in menu
				handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
			}
		}
		}
//			handler.addObject(new Player(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.Player));
//		}
		
	
	
	public synchronized void start() {
		// TODO Auto-generated method stub
		thread = new Thread(this);
		thread.start();
		running = true;
	}
	
	public synchronized void stop() {
		try {
			thread.join();
			running = false;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() { //popular game loop
		this.requestFocus();
		long lastTime = System.nanoTime();
		double amountOfTicks = 60;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		long timer = System.currentTimeMillis();
		int frames = 0;
		while(running){
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
					while(delta >= 1){
						tick();
						delta--;
					}
			if(running)
				render();
			frames++;
			
			if(System.currentTimeMillis() - timer > 1000){
				timer += 1000;
				//System.out.println("FPS: " + frames);
				frames = 0;
			}
		}
		stop();
		
	}
	/**
	 * Explanation of above game loop:
	1. Gets the time in nano seconds (nano seconds? I don't know... nano time?) long numbers contain many decimal places
	2. sets the ticks per frame (Also known as the frames per second, so the game will run in 60 FPS) a double contains a whole number and some decimal places
	3. ns or nano seconds, divided by amountOfTicks to see how many Seconds there have been
	4.delta is the time since the game last updated
	5. the current time in Milliseconds
	6. frames is the number of frames per seconds you're actually getting
	7. while the game is running
	8. now is the number of milliseconds, this has changed from lastTime as time has passed
	9. by misusing the time before by the time now, you can work out the space in-between(or the delta) devide by ns to calculate the seconds
	10.  sets the lastTime to now, therefore the delta can adapt and change to the number of frames you're getting.
	11. While the time between each frame is less than one tick and minus one from the delta
	12. if the game is running: render images, add one to frames
	13. if the gap between when the timer variable was  set and now is less than 1000 Milliseconds: Add 1000 to timer, and print the frames, then set frames to 0 so it can be calculated again.
	   *
	 */
	
	private void tick() {
		handler.tick();
		if(gameState == STATE.Game) {
			hud.tick();
			spawner.tick();
			
			if(HUD.HEALTH <= 0) {
				HUD.HEALTH = 100;
				hud.setLevel(1);
				hud.setScore(0);
				gameState = STATE.End;
				handler.clearEnemys();
				for(int i = 0; i < 20; i++) { //Amount of particles in menu
					handler.addObject(new MenuParticle(r.nextInt(WIDTH), r.nextInt(HEIGHT), ID.MenuParticle, handler));
				}
			}
			
		} else if(gameState == STATE.Menu || gameState == STATE.End) {
			menu.tick();
		}
	
	}
	
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			this.createBufferStrategy(3); //Shouldn't go over 3 buffers - so game has 3 buffers
			return;
		}
		
		Graphics g = bs.getDrawGraphics();
		
		g.setColor(Color.black); //colour of background in game window
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		handler.render(g);
		
		if(gameState == STATE.Game) {
			hud.render(g);
			
		} else if(gameState == STATE.Menu || gameState == STATE.Help || gameState == STATE.End) {
			menu.render(g);
		} 
		
		g.dispose();
		bs.show();
	}
	
	public static float clamp(float var, float min, float max) {
		if(var >= max)
			return var = max;
		else if(var <= min)
			return var = min;
		else
			return var;
	}
	
	public static void main(String args[]) {
		new Game();
	}

}
