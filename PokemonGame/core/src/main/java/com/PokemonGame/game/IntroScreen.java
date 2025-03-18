package com.PokemonGame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.LinkedList;
import java.util.Queue;

import static com.badlogic.gdx.scenes.scene2d.actions.Actions.*;
import static com.badlogic.gdx.scenes.scene2d.actions.Actions.alpha;

public class IntroScreen extends ScreenAdapter implements InputProcessor {
    // declaration 2d Scene
    private Stage stage1;
    private  PokemonGame game;
    private Music music, music2;
    private ShapeRenderer shapeRenderer;
    private Image logo;
    private Image charizard_black;
    private Image charizard_red;
    private ParticleEffect flame;
    private ParticleEffectPool flame_pool;
    private Array<ParticleEffectPool.PooledEffect> effects;
    private Sprite oak;
    private BitmapFont comm;
    private BitmapFont comm1;
    boolean draw = false;
    private Sprite textBubble;
    private String string;
    private String skin;
    private int index;
    private String text = "";
    private String nikname;

    private  float p_ttl;
    boolean lockPrint = false;
    boolean lock= false;
    private Sound click;
    private final Queue<String> words = new LinkedList<>();



    public IntroScreen(PokemonGame game){
        this.game = game;
        this.stage1 = new Stage(new FitViewport(PokemonGame.V_WIDTH, PokemonGame.V_HEIGHT, game.camera )); // settando la scena
        Gdx.input.setInputProcessor(stage1);// serve per tenere traccia di tutti gli eventi passati agli attori di quella stage


        this.shapeRenderer= new ShapeRenderer();


    }
    @Override
    public void show(){
        //miusica
        music = Gdx.audio.newMusic(Gdx.files.internal("Music/opening_music.mp3"));
        music2 = Gdx.audio.newMusic(Gdx.files.internal("Music/titlescreen.mp3"));


        Texture texture_logo =game.assets.get("Sprite_intro/pocket_monsters1.png");
        logo= new Image(texture_logo);
        stage1.addActor(logo);
        logo.setPosition(PokemonGame.V_WIDTH / 2f - 210 , PokemonGame.V_HEIGHT / 2f + 60 );

        Texture texture_charizard_red = game.assets.get("Sprite_intro/charizard/charizard_red.png");
        charizard_red = new Image(texture_charizard_red);
        stage1.addActor(charizard_red);
        charizard_red.addAction(alpha(0));

        Texture texture_charizard_black = game.assets.get("Sprite_intro/charizard/charizard_black.png");
        charizard_black= new Image(texture_charizard_black);
        stage1.addActor(charizard_black);
        charizard_black.setSize(280, 200);
        charizard_black.setPosition(game.camera.viewportWidth/2, game.camera.viewportHeight/6);
        charizard_black.addAction(sequence(alpha(0), fadeIn(4), run(new Runnable() {
            @Override
            public void run() {
                charizard_red.setSize(280, 200);
                charizard_red.setPosition(game.camera.viewportWidth/2, game.camera.viewportHeight/6);
                charizard_black.addAction(alpha(0));
                charizard_red.addAction(alpha(1));
                for (int i =0; i < 50; i++){
                    ParticleEffectPool.PooledEffect effect= flame_pool.obtain();
                    effect.setPosition(i*80, 123);
                    effects.add(effect);
                }
            }
        })));

        flame = new ParticleEffect();
        flame.load(Gdx.files.internal("Sprite_intro/flame/intro_flame.p"), Gdx.files.internal("Sprite_intro/flame"));
        flame.setPosition(45,45);
        flame.start();
        flame_pool = new ParticleEffectPool(flame, 0,300);
        effects = new Array<ParticleEffectPool.PooledEffect>();



        oak = new Sprite(new Texture("Sprite_intro/oak.png"));
        oak.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        textBubble = new Sprite(new Texture("Sprite_intro/SpeachBubble.png"));
        textBubble.setPosition(10, 10);
        textBubble.setSize(700, 80);

        // Font carateri
        comm = new BitmapFont();
        comm1 = comm;
        comm1.getData().scale(.1f);
        comm1.setColor(Color.BLACK);
        comm.getData().scale(0.3f);
        comm.setColor(0, 0, 0, 1);



    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        update(delta);

        if (!draw){
            music.play();
        }
        else {
            music2.play();
        }

        if (lockPrint && !words.isEmpty()){
            //printText(words.element());
            printText(words.remove());

        }


        if (Gdx.input.isKeyJustPressed(Input.Keys.X)){
            if (!lock){
                music.stop();
                addText();
                draw = true;
                lock = true;
            }
        }
        else if (Gdx.input.isKeyJustPressed(Input.Keys.B) && lock && words.isEmpty()){
            game.setScreen(game.gameScreen);
            music2.stop();
        }
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.valueOf("ff6127"));
        shapeRenderer.rect(0, game.camera.viewportHeight - 15, game.camera.viewportWidth, 15);

        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(0, 0, game.camera.viewportWidth, 15);


        shapeRenderer.setColor(Color.valueOf("3fccab"));
        shapeRenderer.rect(0, game.camera.viewportHeight/4  , game.camera.viewportWidth, 240);
        shapeRenderer.end();
        game.batch.begin();

        if (!draw){
            for (ParticleEffectPool.PooledEffect effect : effects){
                effect.draw(game.batch, Gdx.graphics.getDeltaTime());
            }

            stage1.draw();
            comm.setColor(Color.GRAY);
            comm.draw(game.batch, "PRESS START", 150, 70);

            //comm.getData().scale(.3f);
            comm1.draw(game.batch, "Prodoct by Exodius company ", game.camera.viewportWidth/2 - 100, 15);

        }else {
            oak.draw(game.batch);
            if (text.equals("Tell me abuot of you...")){

                game.setScreen(new ChoiseSkinPlayer(this, game));
            }
            textBubble.draw(game.batch);
            comm.draw(game.batch, text, 40, 60);
        }
        game.batch.end();

    }
    public void update(float delta){
        stage1.act(delta);
    }
    public void printText(String strings) {
        // re fills text with new string one letter at a time
        text = "";
        string = strings;
        index = 0;
        lockPrint = false;
        System.out.println(index + string);
        Timer.schedule(new Timer.Task() {

            public void run() {
                if (index < string.length()) {
                    text += string.charAt(index);
                    index += 1;
                } else if (Gdx.input.isKeyPressed(Input.Keys.B)) {
                    // resets the print text method
                    //click.play();
                    cancel();
                    lockPrint = true;
                    text = "";
                }
            }
        }, 0.03f, 0.03f);
    }
    public void addText(){
        lockPrint = true;
        words.add("Hello there, glad to meet you");
        words.add("Welcome to the world of POKeMON");
        words.add("My name is OAK");
        words.add("People refer to me as the Pokemon Professor");
        words.add("This world....");
        words.add("is inhabited far and wide by creatures called Pokemon");
        words.add("Some people have them as pets");
        words.add("Others use them to battle..");
        words.add("As for myself.. I study them as a profession!");
        words.add("Tell me abuot of you...");
        // words.add("Very well"+ nikname + "are you ready");
        words.add("Remember press SPACE to talk to someone..");
        words.add("Press B to keep talking...");
        words.add("Press ENTER to claim items");
        words.add(" ");
    }


    @Override
    public boolean keyDown(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        // TODO Auto-generated method stub
        return false;
    }


}
