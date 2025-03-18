package com.PokemonGame.game;


import com.badlogic.gdx.Game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;


public class PokemonGame extends Game {
    public static final int  V_HEIGHT = 480 ;
    public static final int  V_WIDTH = 750 ;
    SpriteBatch batch;
    public IntroScreen intro;
    public SplashScreen splash_screen;
    public GameScreen gameScreen;
    public IntroScreen introScreen;
    public OrthographicCamera camera;
    public AssetManager assets;
    public VideoPlayer videoPlayer;

    @Override
    public void create() {
        assets= new AssetManager();
        camera= new OrthographicCamera();
        camera.setToOrtho(false, V_WIDTH,V_HEIGHT);
        batch = new SpriteBatch();
        videoPlayer = VideoPlayerCreator.createVideoPlayer();
        gameScreen = new GameScreen(this,new Player());
        introScreen= new IntroScreen(this);

        this.setScreen(new LoadingScreen(this));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        assets.dispose();
        this.getScreen().dispose();
    }
}
