package src.com.aliamondo.blockdude.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import src.com.aliamondo.blockdude.objects.Data;

public class IntroScreen extends BlockdudeScreen {
	TextureRegion intro;
	SpriteBatch batch;
	float time = 0;
	
	boolean backKeyPressed = false;

	public IntroScreen (Game game, Data data, BlockdudeScreen screen) {
		super(game, data, screen);
	}

	@Override
	public void show () {
		Gdx.input.setCatchBackKey(true);
		intro = new TextureRegion(new Texture(Gdx.files.internal("title.png")), 0, 0, 800, 480);
		batch = new SpriteBatch();
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 480);
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(intro, 0, 0);
		batch.end();

		data.showNextAchievement();
		
		time += delta;
		if (time > 0.25) {
			if (Gdx.input.isTouched()) {
				float x = Gdx.input.getX();
				float y = Gdx.input.getY();
				
				if (x >= data.convertScreenWidth(324) && x <= data.convertScreenWidth(475)) {
					if (y >= data.convertScreenHeight(243) && y <= data.convertScreenHeight(288)) {
						game.setScreen(new LevelPickerScreen(game, data, this));
					}
					else if (y >= data.convertScreenHeight(293) && y <= data.convertScreenHeight(339)) {
						game.setScreen(new HelpScreen(game, data, this));
					}
					else if (y >= data.convertScreenHeight(346) && y <= data.convertScreenHeight(390)) {
						game.setScreen(new AboutScreen(game, data, this));
					}
				}
			}
			if (Gdx.input.isKeyPressed(Keys.SPACE)) {
				game.setScreen(new LevelPickerScreen(game, data, this));
			}
//			if (Gdx.input.isKeyPressed(Keys.SPACE) || Gdx.input.justTouched()) {
//				//game.setScreen(new GameScreen(game, data));
//				game.setScreen(new LevelPickerScreen(game, data, this));
//			}
		}
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			backKeyPressed = true;
		}
		
		if (backKeyPressed && !Gdx.input.isKeyPressed(Keys.BACK) && !Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			Gdx.app.exit();
		}
	}

	@Override
	public void hide () {
		//Gdx.app.debug("Blockdude", "dispose intro");
		batch.dispose();
		intro.getTexture().dispose();
	}
}