package com.aliamondo.blockdude.screens;

import com.aliamondo.blockdude.objects.Data;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

public abstract class BlockdudeScreen implements Screen {
        Game game;
        Data data;

        public BlockdudeScreen(Game game, Data data, BlockdudeScreen screen) {
                this.game = game;
                this.data = data;
                screen = null;
        }

        @Override
        public void resize (int width, int height) {
        }

        @Override
        public void show () {
        }

        @Override
        public void hide () {
        }

        @Override
        public void pause () {
        }

        @Override
        public void resume () {
        }

        @Override
        public void dispose () {
        }
}