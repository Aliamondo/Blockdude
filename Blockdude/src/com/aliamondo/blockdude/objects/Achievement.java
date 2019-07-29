package src.com.aliamondo.blockdude.objects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Achievement {
	public static final int ACHIEVEMENT_SHOW_TIME_LIMIT = 4;
	String text = "";
	Texture icon;
	BitmapFont font;
	TextureRegion bg;
	float t = 0;
	boolean isDummyAchievement = false;

	public Achievement() {
		this.isDummyAchievement = true;
		this.t = ACHIEVEMENT_SHOW_TIME_LIMIT;
	}
	
	public Achievement(String text, String icon) {
		this.isDummyAchievement = false;
		this.text = text;
		this.icon = new Texture(Gdx.files.internal("ach_icons\\" + icon + ".png"));
		this.font = new BitmapFont(Gdx.files.internal("default.fnt"), Gdx.files.internal("default.png"), false);
		this.font.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		this.font.setScale(1);
		this.font.setColor(Color.WHITE);
		this.t = 0;
		this.bg = new TextureRegion(new Texture(Gdx.files.internal("achievement_frame.png")), 0, 0, 350, 70);
	}

	public void show() {
		if (!isDummyAchievement && t < ACHIEVEMENT_SHOW_TIME_LIMIT) {
			SpriteBatch batch = new SpriteBatch();
			batch.begin();
			batch.draw(bg, convertScreenWidth(225), convertScreenHeight(100), convertScreenWidth(350), convertScreenHeight(70));
			batch.draw(icon, convertScreenWidth(225 + 5), convertScreenHeight(100 + 3), convertScreenWidth(64), convertScreenHeight(64));
			font.draw(batch, text, convertScreenWidth(225 + 75 + 5), convertScreenHeight(100 + 60));
			t += Gdx.graphics.getDeltaTime();
			batch.end();
			batch.dispose();
		}
	}
	public boolean doneShowing() {
		return (t >= ACHIEVEMENT_SHOW_TIME_LIMIT);
	}
	public float convertScreenWidth(float x) {
		return (x / 800) * Gdx.graphics.getWidth();
	}
	public float convertScreenHeight(float y) {
		return (y / 480) * Gdx.graphics.getHeight();
	}
}
