package com.PokemonGame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

public class LoadingScreen implements Screen {
    private final PokemonGame game;
    private ShapeRenderer shapeRenderer;
    private  float progress;
    private BitmapFont font;
    public LoadingScreen(final PokemonGame game){
        this.game = game;
        this.shapeRenderer= new ShapeRenderer();

        this.progress = 0f;

        queueAssets();
    }


    @Override
    public void show() {
        System.out.println("Loading screen");

        font = new BitmapFont();
        font.getData().scale(0.8f);
        font.setColor(0, 0, 0, 1);
    }
    private void update(float delta){
        progress = MathUtils.lerp(progress, game.assets.getProgress(),.1f);
        if (game.assets.update() && progress >= game.assets.getProgress() - .11f) {
            game.setScreen(new SplashScreen(game));
        }

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.BLACK);
        shapeRenderer.rect(150, game.camera.viewportHeight / 2 - 8, game.camera.viewportWidth / 2 + 40 ,  16);

        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.rect(150, game.camera.viewportHeight / 2 - 8, progress * (game.camera.viewportWidth / 2 + 40),  16);

        shapeRenderer.end();

        game.batch.begin();
        font.draw(game.batch, "LOADING", 40, 40);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
    }

    private void queueAssets(){
        //game.assets.load("Sprite_intro/pocket_monsters_logo.jpg", Texture.class);
        game.assets.load("Sprite_intro/pk_logo.png", Texture.class);
        game.assets.load("Sprite_intro/pocket_monsters1.png",Texture.class);
        game.assets.load("Sprite_intro/charizard/charizard_black.png", Texture.class);
        game.assets.load("Sprite_intro/charizard/charizard_red.png", Texture.class);
    }
}

