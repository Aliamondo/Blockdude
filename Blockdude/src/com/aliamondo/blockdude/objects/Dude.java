package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Dude {

	TextureRegion 	dudeTexture;
	Rectangle 		rect;

	public enum State {
		WITH_BLOCK, WITHOUT_BLOCK
	}

	final float SPEED = 3.4f;
	static final float JUMP_VELOCITY = 1f;

	Vector2 	position = new Vector2();
	State		state = State.WITHOUT_BLOCK;
	boolean		facingRight = true;

	public Dude(Vector2 position) {
		dudeTexture  = new TextureRegion(new Texture(Gdx.files.internal("blockdude_128.png")), 0, 0, 96, 112);
		this.position = position;
		this.rect = new Rectangle(position.x, position.y, 1f,1f);
	}

	public boolean isFacingRight() {
		return facingRight;
	}

	public void setFacingRight(boolean facingRight) {
		this.facingRight = facingRight;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 newPos) {
		this.position = newPos;
		this.rect.setX(newPos.x);
		this.rect.setY(newPos.y);
	}

	public void setPosition(float x, float y) {
		this.position = new Vector2(x, y);
		this.rect.setX(x);
		this.rect.setY(y);
	}

	public State getState() {
		return state;
	}

	public void setState(State newState) {
		this.state = newState;
	}

	public void render(SpriteBatch batch, float totalX, float totalY) {
		//System.out.println("x: "+ position.x + ", y: " + position.y);
		if (isFacingRight()) {
			//batch.draw(dudeTexture, getPosition().x * 800 / totalX, getPosition().y * 480 / totalY, 800/totalX, 800/totalX * 1.15f);
			batch.draw(dudeTexture, getPosition().x * 96, getPosition().y * 96);
		} else {
			//batch.draw(dudeTexture, (getPosition().x + 1) * 800 / totalX, getPosition().y * 480 / totalY, -800/totalX, 800/totalX * 1.15f);
			batch.draw(dudeTexture, (getPosition().x+1) * 96, getPosition().y * 96, -96, 112);
		}
	}

	public void dispose() {
		dudeTexture.getTexture().dispose();
	}
}