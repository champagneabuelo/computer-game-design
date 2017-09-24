package edu.virginia.engine.game;

import java.awt.Point;
import java.awt.Rectangle;

import edu.virginia.engine.display.AnimatedSprite;
import edu.virginia.engine.display.Animation;
import edu.virginia.engine.display.Frame;
import edu.virginia.engine.display.Sprite;
import edu.virginia.engine.events.Event;
import edu.virginia.engine.events.IEventListener;
import edu.virginia.engine.layers.TopLayer;
import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class Player extends AnimatedSprite implements IEventListener {	
	// class statics:
	public static int len = Parameters.DIMENSION;
	public static int player_x = TopLayer.player_x;
	public static int player_y = TopLayer.player_y;
	public static int player_x_reversed =  TopLayer.player_x_reversed;
	public static int player_y_reversed = TopLayer.player_y_reversed;

	private int currX = player_x;
	
	private Rectangle hitBox;
	public int x_position;

	// Animations that the sprite will have access to
	private Animation walkLeft;
	private Animation walkRight;
	private Animation standingRight;
	private Animation standingLeft;


	// default player constructor, will point to default spritesheet
	public Player ( String id ) {
		super ( id, "player.png", 32);	
		this.x_position = player_x;
		this.hitBox = new Rectangle( this.x_position , player_y - Parameters.HIT_BOX_HEIGHT , 
						Parameters.HIT_BOX_WIDTH , Parameters.HIT_BOX_HEIGHT );
		
		BufferedImage[] wl = {this.getSprite(2,1), this.getSprite(1,1), this.getSprite(0,1)};
		BufferedImage[] wr = {this.getSprite(0,2), this.getSprite(1,2), this.getSprite(2,2)};
		BufferedImage[] sr = {this.getSprite(1,2)};
		BufferedImage[] sl = {this.getSprite(1,1)};

		this.walkLeft = new Animation(wl, 3);
		this.walkRight = new Animation(wr, 3);
		this.standingRight = new Animation(sr, 3);
		this.standingLeft = new Animation(sl, 3);

		this.setAnimation(this.standingRight);
	}
	
	public Player ( String id, String filename ) {
		super ( id, filename );
		this.x_position = player_x;
		this.hitBox = new Rectangle( this.x_position , player_y - Parameters.HIT_BOX_HEIGHT , 
						Parameters.HIT_BOX_WIDTH , Parameters.HIT_BOX_HEIGHT );
	}

	public Rectangle getHitBox( ) {
		return this.hitBox;
	}
	
	public boolean checkHit ( Point p ) { // in Prototype, p is the position of some enemy
		
		Rectangle rect_ref = this.hitBox; // runtime opt
		
		// define bounds of hit-box for comparisons
		int this_x_min = rect_ref.x;					// for readability
		int this_x_max = rect_ref.x + rect_ref.width;	// for readability
			
		int this_y_min = rect_ref.y - rect_ref.height - Parameters.HIT_TOLERANCE;	// for readability
		int this_y_max = rect_ref.y + Parameters.HIT_TOLERANCE ;					// for readability
		
		
		boolean in_x = (this_x_min <= p.x) && (this_x_max >= p.x);
		boolean in_y = (this_y_min <= p.y) && (this_y_max >= p.y);
		
		return in_y && in_x;
	}

	public void update(ArrayList<String> pressedKeys) {	
		this.getAnimation().update();

		// reads input from keyboard
		String right_key = KeyEvent.getKeyText(KeyEvent.VK_RIGHT);	
		String left_key = KeyEvent.getKeyText(KeyEvent.VK_LEFT);

		if (this.currX == player_x) {
			this.setAnimation(this.standingRight);
		}

		if (this.currX == player_x_reversed) {
			this.setAnimation(this.standingLeft);
		}	
		
		if (pressedKeys.contains(right_key)) {
			this.setAnimation(this.walkRight);
			this.currX = player_x;
		}

		if (pressedKeys.contains(left_key)) {
			this.setAnimation(this.walkLeft);
			this.currX = player_x_reversed;
		}
		
		this.getAnimation().start();
	}

	public void draw(Graphics g) {
		Graphics2D g2d = ( Graphics2D ) g;

		applyTransformations(g2d);
		
		g2d.drawImage(this.getAnimation().getSprite(), 
				currX - this.getWidth() + 20, 
				player_y - this.getHeight() + 10, 
				this.getWidth(), this.getHeight(), null);	

		reverseTransformations(g2d);
	}

	
	@Override
	public void handleEvent( Event event ) {
		// TODO Auto-generated method stub	
	}
	
	

}
