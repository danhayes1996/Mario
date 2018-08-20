package gameobjects.entities.mobs;

import static maths.Maths.sign;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.List;

import engine.Engine;
import gameobjects.GameObject;
import gameobjects.entities.Entity;
import gameobjects.entities.items.Item;
import gameobjects.tiles.PSwitch;
import gameobjects.tiles.Spike;
import gameobjects.tiles.Tile;
import gameobjects.tiles.itemtiles.ItemTile;
import gfx.ColourSet;
import gfx.Screen;
import levels.LevelCreator;
import sound.SoundEffect;

public class Player extends Mob {

	private static final ColourSet owCols = new ColourSet(ColourSet.TRANSPARENT, 0xff6A6B04, 0xffE39D25, 0xffB13425);

	private static final double MIN_WALK_VEL  = (1D/16D) + (3D/256D);
	private static final double MAX_WALK_VEL  = 1D + (9D/16D);
	private static final double WALK_ACCEL    = (9D/256D) + (8D/4096D);
	private static final double MAX_RUN_VEL   = 2D + (9D/16D);
	private static final double RUN_ACCEL     = (14D/256D) + (4D/4096D);
	private static final double RELEASE_DECEL = 13D/256D;
	//private static final double SKID_DECEL  = (1D/16D) + (10D/256D);
	
	private static final int INVULNERABLE_TIME = 60;
	
	public static enum POWERUP { NONE, MUSHROOM, FIREFLOWER };
	//private POWERUP powerUp;
	
	private static enum STATE { GROUNDED, JUMPING, FALLING };
	private STATE state;
	
	private int score, lives, coins;
	
	private int invulnerable_ticks;
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
		z = 10;

		//powerUp = POWERUP.NONE;
		state = STATE.FALLING;
		
		score = coins = invulnerable_ticks = 0;
		lives = 3;
	}
	
	public static List<GameObject> createFromString(String src) {
		String[] params = { "x:", "y:" };
		int intArgs[] = LevelCreator.parseIntArgs(src, params);
		return Arrays.asList(new Player(intArgs[0], intArgs[1]));
	}
	
	public void tick() {
		if(invulnerable_ticks > 0) invulnerable_ticks--;
		
		if(y > level.getHeight()) kill();
		
		//left movement
		if(Engine.INPUT.isKeyPressed(KeyEvent.VK_A)) {
			if(Engine.INPUT.isKeyPressed(KeyEvent.VK_SHIFT)) {
				velx -= RUN_ACCEL;
				if(velx < -MAX_RUN_VEL) velx = -MAX_RUN_VEL;
			} else {
				velx -= WALK_ACCEL;
				if(velx < -MAX_WALK_VEL) velx = -MAX_WALK_VEL;
			}
		}
		//right movement
		if(Engine.INPUT.isKeyPressed(KeyEvent.VK_D)) {
			if(Engine.INPUT.isKeyPressed(KeyEvent.VK_SHIFT)) {
				velx += RUN_ACCEL;
				if(velx > MAX_RUN_VEL) velx = MAX_RUN_VEL;
			} else {
				velx += WALK_ACCEL;
				if(velx > MAX_WALK_VEL) velx = MAX_WALK_VEL;
			}
		}
		
		//no key press
		if(!Engine.INPUT.isKeyPressed(KeyEvent.VK_A) && !Engine.INPUT.isKeyPressed(KeyEvent.VK_D)) {
			if(velx > MIN_WALK_VEL || velx < -MIN_WALK_VEL) velx -= sign(velx) * RELEASE_DECEL;
			else velx = 0;
		}
		
		if(state == STATE.GROUNDED && vely > 0.61) state = STATE.FALLING;
		
		//jump press
		if(Engine.INPUT.isKeyPressed(KeyEvent.VK_W) && state == STATE.GROUNDED) {
			System.out.println("can jump: " + state + ", " + vely);
			vely = -5;
			state = STATE.JUMPING;
			SoundEffect.JUMP.play();
		}

		vely += GRAVITY;
		
		x += velx;
		y += vely;
		
		collide();
	}
	
	public void render(Screen screen) {
		screen.render((int)x, (int)y, 96, 144, width, height, owCols);

		if(Engine.DEBUG_MODE) {
			screen.renderHitbox(getBoundsTop());
			screen.renderHitbox(getBoundsBottom());
			screen.renderHitbox(getBoundsLeft());
			screen.renderHitbox(getBoundsRight());
		}
	}
	
	@Override
	public void collide() {
		List<GameObject> objects = level.getObjects();
		for(int i = 0; i < objects.size(); i++){
			GameObject o = objects.get(i);
			if(getBounds().intersects(o.getBounds()) && o.isSolid()){
				if(o instanceof Tile) {
					Tile t = (Tile) o;
					
					if(invulnerable_ticks == 0 && t instanceof Spike) {
						hit(this);
					}
					
					if(getBoundsTop().intersects(t.getBounds())){
						vely = 0;
						y = t.getY() + t.getHeight();
						if(o instanceof ItemTile) ((ItemTile)o).hit(this);
					}
					
					if(getBoundsBottom().intersects(t.getBounds())){
						vely = 0;
						y = t.getY() - height + 1;
						state = STATE.GROUNDED;
						
						if(t instanceof PSwitch) ((PSwitch) t).hit();
					}
					
					if(getBoundsLeft().intersects(t.getBounds())){
						if(velx < 0) velx = 0; //if going into the block
						x = t.getX() + t.getWidth();
					}
					
					if(getBoundsRight().intersects(t.getBounds())){
						if(velx > 0) velx = 0;
						x = t.getX() - width;
					}
				}else if(o instanceof Entity && !(o instanceof Player) ) {
					Entity e = (Entity) o;
					if(e instanceof Item) ((Item) e).collect(this);
					else if(getBoundsBottom().intersects(e.getBoundsTop())) {
						if(e instanceof Mob) ((Mob)e).hit(this);
					} else { //TODO: check accuracy here
						if(invulnerable_ticks == 0) hit(this);
					}
				}
			}
		}
	}
	
	public void hit(GameObject o) {
		System.out.println("mario hit");
		invulnerable_ticks = INVULNERABLE_TIME;

		SoundEffect.PIPE.play();
		//TODO: power up loss
	}
	
	public void kill() {
		if(lives == 0) {
			//TODO: GAME OVER
		}else {
			//TODO: DEATH ANIMATION
			lives--;
		}
	}

	public void setPowerUp(POWERUP pu) {
		//TODO: change mario skin
	}

	public STATE getState() {
		return state;
	}
	
	public int getLives() {
		return lives;
	}
	
	public void addLife() {
		lives++;
	}
	
	public void removeLife() {
		lives--;
	}
	
	public int getScore() {
		return score;
	}
	
	public void addScore(int score) {
		this.score += score;
	}
	
	public void addCoins(int coins) {
		this.coins += coins;
		if(this.coins >= 100) {
			this.coins -= 100;
			lives++;
		}
	}
	
	public int getCoins() {
		return coins;
	}
}
