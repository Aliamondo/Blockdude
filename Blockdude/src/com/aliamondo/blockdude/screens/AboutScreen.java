package src.com.aliamondo.blockdude.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import src.com.aliamondo.blockdude.objects.Data;

public class AboutScreen extends BlockdudeScreen {
	TextureRegion about;
	SpriteBatch batch;
	boolean backKeyPressed = false;
	BitmapFont menuFont;
	BitmapFont font;
	Texture rightTexture;
	
	public AboutScreen(Game game, Data data, BlockdudeScreen screen) {
		super(game, data, screen);
	}
	
	@Override
	public void show() {
		Gdx.input.setCatchBackKey(true);
		about = new TextureRegion(new Texture(Gdx.files.internal("menu_clean.jpg")), 0, 0, 800, 480);
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 480);
		menuFont = new BitmapFont(Gdx.files.internal("toast.fnt"), Gdx.files.internal("toast.png"), false);
		menuFont.setColor(Color.BLACK);
		font = new BitmapFont(Gdx.files.internal("default.fnt"), Gdx.files.internal("default.png"), false);
		font.setColor(Color.BLACK);
		rightTexture = new Texture(Gdx.files.internal("right.png"));
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(about, 0, 0);
		menuFont.draw(batch, "About", 205, 480 - 40);
		font.drawWrapped(batch, "The original game: Brandon Sterner\nProgramming and design: Aliamondo", 100, 480 - 120, 600);
		batch.draw(rightTexture, 129, 366, -64*1.2f, 64*1.2f);
		batch.end();
		data.showNextAchievement();
		
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			backKeyPressed = true;
		}
		
		if (backKeyPressed && !Gdx.input.isKeyPressed(Keys.BACK) && !Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			game.setScreen(new IntroScreen(game, data, this));
		}
		
		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			//System.out.println("x: " + touchPos.x + ", y: " + touchPos.y);
			if (touchPos.y <= data.convertScreenHeight(480-365) && touchPos.y >= data.convertScreenHeight(480-365-54*1.2f)) {
				if (touchPos.x >= data.convertScreenWidth(130-64*1.2f) && touchPos.x <= data.convertScreenWidth(130)) {
					game.setScreen(new IntroScreen(game, data, this));
				}
			}
		}
	}

}
