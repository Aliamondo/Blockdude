package com.aliamondo.blockdude.screens;

import com.aliamondo.blockdude.objects.Data;
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

public class LevelPickerScreen extends BlockdudeScreen {

	TextureRegion intro;
	Texture frame;
	Texture rightTexture;
	SpriteBatch batch;
	BitmapFont font;
	BitmapFont menuFont;
	boolean[] levels = new boolean[11];

	boolean backKeyPressed = false;
	
	float time = 0;

	public LevelPickerScreen (Game game, Data data, BlockdudeScreen screen) {
		super(game, data, screen);
	}

	@Override
	public void show () {
		intro = new TextureRegion(new Texture(Gdx.files.internal("menu_clean.jpg")), 0, 0, 800, 480);
		frame = new Texture(Gdx.files.internal("frame.png"));
		rightTexture = new Texture(Gdx.files.internal("right.png"));
		batch = new SpriteBatch();
		menuFont = new BitmapFont(Gdx.files.internal("toast.fnt"), Gdx.files.internal("toast.png"), false);
		menuFont.setColor(Color.BLACK);
		font = new BitmapFont(Gdx.files.internal("default.fnt"), Gdx.files.internal("default.png"), false);
		font.setColor(Color.WHITE);
		font.setScale(1);
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 480);
		for (int i=0; i<data.getMaxLevel(); i++) {
			if (i<=10) levels[i] = true;
		}
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(intro, 0, 0);
		menuFont.draw(batch, "PICK YOUR LEVEL", 157, 480 - 44);

		for (int i=0; i<levels.length; i++) {
			if (levels[i]) {
				batch.draw(frame, 128 * (i%4) + 125, -128 * (int)(i/4) + 275, 128, 128);
				font.draw(batch, (i+1)+"", 128 * (i%4) + 175, -128 * (int)(i/4) + 355);
			}
		}

		batch.draw(rightTexture, 129, 366, -64*1.2f, 64*1.2f);
		batch.end();
		data.showNextAchievement();

		if (Gdx.input.justTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			//System.out.println("x: " + touchPos.x + ", y: " + touchPos.y);
			if (touchPos.y <= data.convertScreenHeight(480-365) && touchPos.y >= data.convertScreenHeight(480-365-54*1.2f)) {
				if (touchPos.x >= data.convertScreenWidth(130-64*1.2f) && touchPos.x <= data.convertScreenWidth(130)) {
					game.setScreen(new IntroScreen(game, data, this));
				}
			}

			for (int i=0; i<levels.length; i++) {
				if (levels[i]) {
					if (touchPos.y <= data.convertScreenHeight(480+128 * (int)(i/4) - 295) && touchPos.y >= data.convertScreenHeight(480-128+128 * (int)(i/4) - 255)) {
						if (touchPos.x >= data.convertScreenWidth(128 * (i%4) + 135) && touchPos.x <= data.convertScreenWidth(128 * (i%4) + 240)) {
							data.setLevel(i+1);
							game.setScreen(new GameScreen(game, data, this));
							//System.out.println((i+1)+"");
						}
					}
				}
			}
		}
		
		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			backKeyPressed = true;
		}
		
		if (backKeyPressed && !Gdx.input.isKeyPressed(Keys.BACK) && !Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			game.setScreen(new IntroScreen(game, data, this));
		}
	}

}
