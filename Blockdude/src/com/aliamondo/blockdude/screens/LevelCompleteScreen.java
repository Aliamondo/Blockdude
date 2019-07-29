package src.com.aliamondo.blockdude.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;

import src.com.aliamondo.blockdude.objects.Achievement;
import src.com.aliamondo.blockdude.objects.Data;

public class LevelCompleteScreen extends BlockdudeScreen {
	TextureRegion title;
	TextureRegion starTexture;
	//Texture leftTexture;
	Texture rightTexture;
	Texture restartTexture;
	SpriteBatch batch;
	BitmapFont menuFont;
	private Achievement achievement = new Achievement();

	float time = 0;

	boolean backKeyPressed = false;

	public LevelCompleteScreen (Game game, Data data, BlockdudeScreen screen) {
		super(game, data, screen);
	}

	@Override
	public void show () {
		title = new TextureRegion(new Texture(Gdx.files.internal("menu_clean.jpg")), 0, 0, 800, 480);
		starTexture  = new TextureRegion(new Texture(Gdx.files.internal("star.png")), 0, 0, 96, 96);
		//leftTexture = new Texture(Gdx.files.internal("left.png"));
		rightTexture = new Texture(Gdx.files.internal("right.png"));
		restartTexture = new Texture(Gdx.files.internal("restart.png"));
		batch = new SpriteBatch();
		menuFont = new BitmapFont(Gdx.files.internal("toast.fnt"), Gdx.files.internal("toast.png"), false);
		menuFont.setColor(Color.BLACK);
		if ((data.getTotalStars()) % 5 == 0 && data.getTotalStars() > 0) {
			achievement = new Achievement(data.getTotalStars() + " stars collected!", "star_2");
			data.addAchievementInQueue(achievement);
		}
		if (!data.getGameComplete() && data.getMaxLevel() > 11 && !data.getCheater()) {
			data.setGameComplete(true);

			FileHandle file = Gdx.files.local( "data.txt" );
			file.writeString(data.getLevel()+"\n", false);
			file.writeString(data.getTotalStars()+"\n", true);
			file.writeString(data.getMaxLevel()+"\n", true);
			file.writeString(data.getGameComplete() + "\n", true);

			achievement = new Achievement("The mind master!", "dude_2");
			data.addAchievementInQueue(achievement);
		}
		if (data.getCheater()) {
			achievement = new Achievement("Cheater!!! >_<", "dude_cheater");
			data.addAchievementInQueue(achievement);
			data.setCheater(false);
		}
		batch.getProjectionMatrix().setToOrtho2D(0, 0, 800, 480);
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.begin();
		batch.draw(title, 0, 0);
		menuFont.draw(batch, "LEVEL COMPLETE!", 205, 480 - 90);

		if(data.getStarsCollected() % 2 == 1) {
			batch.draw(starTexture, 400-64, 240);
			if (data.getStarsCollected() == 3) {
				batch.draw(starTexture, 300-64, 240);
				batch.draw(starTexture, 500-64, 240);
			}
		}
		else if (data.getStarsCollected() == 2) {
			batch.draw(starTexture, 350-64, 240);
			batch.draw(starTexture, 450-64, 240);
		}
		else {
			menuFont.draw(batch, "You cheated!", 250, 270);
		}
		batch.draw(rightTexture, 268, 50, -128, 128);
		batch.draw(rightTexture, 500, 50);
		batch.draw(restartTexture, 320, 50);

		batch.end();
		//		achievement.show();
		data.showNextAchievement();

		if (Gdx.input.isTouched()) {
			Vector3 touchPos = new Vector3();
			touchPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
			//System.out.println("x: " + touchPos.x + ", y: " + touchPos.y);
			if (touchPos.y <= data.convertScreenHeight(480-50) && touchPos.y >= data.convertScreenHeight(480-50-128)) {
				if (touchPos.x >= data.convertScreenWidth(140) && touchPos.x <= data.convertScreenWidth(268)) {
					//					data.finishShowingAchievement();
					data.setLevel(data.getLevel());
					game.setScreen(new LevelPickerScreen(game, data, this));
				} else if (touchPos.x >= data.convertScreenWidth(500) && touchPos.x <= data.convertScreenWidth(500+128)) {
					//					data.finishShowingAchievement();
					game.setScreen(new GameScreen(game, data, this));
				} else if (touchPos.x >= data.convertScreenWidth(320) && touchPos.x <= data.convertScreenWidth(320+128)) {
					//					data.finishShowingAchievement();
					data.setLevel(data.getLevel()-1);
					game.setScreen(new GameScreen(game, data, this));
				}
			}
		}

		if (Gdx.input.isKeyPressed(Keys.ENTER)) {
			game.setScreen(new GameScreen(game, data, this));
		}
		else if (Gdx.input.isKeyPressed(Keys.R)) {
			data.setLevel(data.getLevel()-1);
			game.setScreen(new GameScreen(game, data, this));
		}

		if (Gdx.input.isKeyPressed(Keys.BACK) || Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			backKeyPressed = true;
		}

		if (backKeyPressed && !Gdx.input.isKeyPressed(Keys.BACK) && !Gdx.input.isKeyPressed(Keys.BACKSPACE)) {
			data.setLevel(data.getLevel());
			game.setScreen(new LevelPickerScreen(game, data, this));
		}
	}

	@Override
	public void hide () {
		//Gdx.app.debug("Blockdude", "dispose main menu");
		batch.dispose();
		title.getTexture().dispose();
	}
}