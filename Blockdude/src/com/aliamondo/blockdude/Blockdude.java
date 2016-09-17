package com.aliamondo.blockdude;

import com.aliamondo.blockdude.objects.Data;
import com.aliamondo.blockdude.screens.IntroScreen;
import com.badlogic.gdx.Game;

public class Blockdude extends Game {
	// The original game by Brandon Sterner
	
	@Override
	public void create() {
		Data data = new Data();
		setScreen(new IntroScreen(this, data, null));
	}	
}