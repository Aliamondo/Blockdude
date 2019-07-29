package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public abstract class Block {
	TextureRegion	blockTexture;
	Rectangle 		rect;
	String 			textureName;
	Type			type;

	public enum Type {
		STATIC_BLOCK, FINISH_BLOCK, MOVABLE_BLOCK
	}

	Vector2 position = new Vector2();

	public Block(Vector2 pos, Type type) {
		if (type == Type.STATIC_BLOCK) this.textureName = "block01.png";
		else if (type == Type.FINISH_BLOCK) this.textureName = "door.png";
		else this.textureName = "block.png";

		blockTexture  = new TextureRegion(new Texture(Gdx.files.internal(textureName)), 0, 0, 96, 96);
		this.position = pos;
		this.type = type;
		this.rect = new Rectangle(position.x, position.y, 1f, 1f);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void render(SpriteBatch batch, float totalX, float totalY) {
		//System.out.println("x: "+ position.x + ", y: " + position.y);
		//batch.draw(blockTexture, getPosition().x * 800 / totalX, getPosition().y * 480 / totalY, 800/totalX, 480/totalY);
		batch.draw(blockTexture, (getPosition().x) * 96, getPosition().y * 96);
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
	
	public void dispose() {
		blockTexture.getTexture().dispose();
	}
}