package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Star {
	TextureRegion	starTexture;
	Rectangle 		rect;

	Vector2 	position = new Vector2();
	boolean		visible = true;

	public Star(Vector2 position) {
		starTexture  = new TextureRegion(new Texture(Gdx.files.internal("star.png")), 0, 0, 96, 96);
		this.position = position;
		this.rect = new Rectangle(position.x, position.y, 1f, 1f);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisibility(boolean visible) {
		this.visible = visible;
	}

	public Vector2 getPosition() {
		return position;
	}

	public void render(SpriteBatch batch, float totalX, float totalY) {
		//System.out.println("x: "+ position.x + ", y: " + position.y);
		if (visible) {
			//batch.draw(starTexture, getPosition().x * 800 / totalX, getPosition().y * 480 / totalY, 800/totalX, 800/totalX);
			batch.draw(starTexture, getPosition().x * 96, getPosition().y * 96);
		}
	}
	public void dispose() {
		starTexture.getTexture().dispose();
	}
}
