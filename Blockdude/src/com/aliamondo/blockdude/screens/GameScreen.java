package com.aliamondo.blockdude.screens;

import com.aliamondo.blockdude.objects.Data;
import com.aliamondo.blockdude.objects.World;
import com.aliamondo.blockdude.objects.WorldRenderer;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class GameScreen extends BlockdudeScreen {

	private World world;
	private WorldRenderer renderer;
	
	public GameScreen (Game game, Data data, BlockdudeScreen screen) {
		super(game, data, screen);
	}

	@Override
	public void show() {
		world = new World(data.getLevel());
		renderer = new WorldRenderer(world, this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(1f, 1f, 1f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		renderer.render();
	}
	
	public Game getGame() {
		return game;
	}
	
	public Data getData() {
		return data;
	}
}