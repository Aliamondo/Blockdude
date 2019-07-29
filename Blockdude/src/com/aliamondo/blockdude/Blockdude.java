package src.com.aliamondo.blockdude;

import com.badlogic.gdx.Game;

import src.com.aliamondo.blockdude.objects.Data;
import src.com.aliamondo.blockdude.screens.IntroScreen;

public class Blockdude extends Game {
	// The original game by Brandon Sterner
	
	@Override
	public void create() {
		Data data = new Data();
		setScreen(new IntroScreen(this, data, null));
	}	
}