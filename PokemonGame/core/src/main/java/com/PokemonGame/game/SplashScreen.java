package com.PokemonGame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.scenes.scene2d.VideoActor;

import java.io.FileNotFoundException;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;

public class SplashScreen implements Screen {
    private  PokemonGame game;
    private Stage stage;
    private Image pk_logo;
    private FileHandle file;
    private VideoActor player;
    private  boolean lock;
    public SplashScreen(PokemonGame game){
        this.game = game;
        this.stage = new Stage(new FillViewport(PokemonGame.V_WIDTH, PokemonGame.V_HEIGHT, game.camera )); // settando la scena
        Gdx.input.setInputProcessor(stage);// serve per tenere traccia di tutti gli eventi passati agli attori di quella stage
        Texture texture_pk_logo= game.assets.get("Sprite_intro/pk_logo.png", Texture.class);
        pk_logo = new Image(texture_pk_logo);

        player= new VideoActor(game.videoPlayer);

        player.getVideoPlayer().setOnCompletionListener(new VideoPlayer.CompletionListener() {
            @Override
            public void onCompletionListener(FileHandle file) {
                game.setScreen(new IntroScreen(game));
            }
        });
        file = Gdx.files.internal("video/Pokemon_video.webm");

    }


    @Override
    public void show() {
        pk_logo.setPosition(stage.getWidth() / 4 , stage.getHeight() / 4  );
        pk_logo.setSize(stage.getWidth() / 2 , stage.getHeight() / 2 );
        stage.addActor(pk_logo);
        stage.addActor(player);
        pk_logo.addAction(sequence(alpha(0), fadeIn(3f), fadeOut(3f), run( new Runnable(){
            @Override
            public void run() {
                try {
                    System.out.println(player.getVideoPlayer().getVideoWidth());
                    player.getVideoPlayer().play(file);

                } catch (FileNotFoundException e) {
                    Gdx.app.error("gdx-video", "Oh no!");
                }
            }
        })));


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        update(delta);

        game.batch.begin();
        stage.draw();
        ///comm.draw(game.batch,"Click X", 200, 100);

        Texture frame = player.getVideoPlayer().getTexture();
        if (frame != null) {
            game.batch.draw(frame, 0, 0, PokemonGame.V_WIDTH, PokemonGame.V_HEIGHT);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.X)){
            lock = true;
        }
        if (lock ){
            player.getVideoPlayer().stop();
            game.setScreen(new IntroScreen(game));
        }
        game.batch.end();
    }
    public void update(float delta){
        stage.act(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width,height,true);

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
        stage.dispose();
    }
}
