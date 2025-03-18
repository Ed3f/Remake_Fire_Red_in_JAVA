package com.PokemonGame.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class ChoiseSkinPlayer extends ScreenAdapter implements InputProcessor {
    private final PokemonGame game;
    private final Screen previuosScreen;
    private final Stage stage2;
    private final ShapeRenderer shapeRenderer;
    private Image boy;
    private Image girl;
    private Image rival;
    private Image text_bubble;
    private Image arrow;
    private BitmapFont font;
    private BitmapFont f_field;
    private TextField playerField, rivalField;
    private TextField.TextFieldStyle style;
    private Input.TextInputListener my_input_list;
    private String text;
    public String nikname_player;
    public String choise_skin;
    public String nickname_rival;

    private  boolean lock_b = false;
    private  boolean lock_g = false;
    private boolean finish = false;
    private boolean set_name_rival= false;
    private boolean isSet_name_rival= false;
    private  boolean lock = false;
    public ChoiseSkinPlayer(Screen previousScreen,PokemonGame game){
        this.previuosScreen= previousScreen;
        this.game= game;
        stage2 =  new Stage(new FitViewport(PokemonGame.V_WIDTH, PokemonGame.V_HEIGHT, game.camera )); // settando la scena
        this.shapeRenderer = new ShapeRenderer();
    }
    @Override
    public void show(){

        Texture boy_texture= new Texture(Gdx.files.internal("Player/boy.png"));
        boy = new Image(boy_texture);
        stage2.addActor(boy);
        boy.setSize(300, 300);
        boy.setPosition(game.camera.viewportWidth/2 - 300, 100);

        Texture girl_texture = new Texture(Gdx.files.internal("Player/girl.png"));
        girl = new Image(girl_texture);
        stage2.addActor(girl);
        girl.setSize(300, 300);
        girl.setPosition(game.camera.viewportWidth/2 + 25 ,100);

        // Texture rivale
        rival=  new Image(new Texture("Player/rivale.png"));
        //stage2.addActor(rival);
        rival.setSize(150, 250);
        rival.setPosition(game.camera.viewportWidth/2 -300, 100);

        //box e font dove viene printato il testo

        //testo
        text_bubble = new Image(new Texture("Sprite_intro/SpeachBubble.png"));
        stage2.addActor(text_bubble);
        text_bubble.setPosition(10, 10);
        text_bubble.setSize(700, 80);

        // arrow
        arrow = new Image(new Texture("Player/freccia.png"));
        stage2.addActor(arrow);

        //font
        font = new BitmapFont();
        font.getData().scale(.1f);
        font.setColor(Color.BLACK);

        // style input text
        style = new TextField.TextFieldStyle();
        style.font= new BitmapFont();
        style.fontColor= Color.BLACK;

        //set input listener
        playerField = new TextField("",style);
        playerField.setPosition(game.camera.viewportWidth / 2 + 95,game.camera.viewportHeight - 115);

        rivalField= new TextField("", style);
        rivalField.setPosition(game.camera.viewportWidth / 2 + 95,game.camera.viewportHeight - 115);
        Gdx.input.setInputProcessor(stage2);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(225 / 255f, 200 / 255f, 106 / 255f, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (!lock) {
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                lock_b = true;
                lock_g = false;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                lock_b = false;
                lock_g = true;
            }
            drawingChoise(lock_b, lock_g);
        }

        game.batch.begin();
        stage2.draw();
        if (!Gdx.input.isKeyJustPressed(Input.Keys.X) && !lock) {
            font.draw(game.batch, "Are you boy o girl ?", 240, 55);
        } else if ((lock_g || lock_b) && !set_name_rival ) {
            lock = true;
            font.draw(game.batch, "What is your name? ", game.camera.viewportWidth / 2 + 75, game.camera.viewportHeight - 68);
            nikname_player = chooseNiknameOfPlayer(lock, lock_g, lock_b);

        }
        if(set_name_rival) {
            nickname_rival= chooseNiknameRival(lock_b);
        }

        if(finish){
            game.setScreen(previuosScreen);
        }
        game.batch.end();
    }
    private void drawingChoise(boolean lock_b, boolean lock_g){
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        if (lock_b) {
            shapeRenderer.rect(boy.getX(), boy.getY(), boy.getWidth() + 10, boy.getHeight() + 10);
        } else if (lock_g) {
            shapeRenderer.rect(girl.getX(), girl.getY(), girl.getWidth() + 10, girl.getHeight() + 10);
        }
        shapeRenderer.end();
    }
    private  String  chooseNiknameOfPlayer(boolean lock, boolean lock_g, boolean lock_b) {
        if (lock) {
            text_bubble.setPosition(game.camera.viewportWidth / 2 + 50, game.camera.viewportHeight - 150);
            text_bubble.setSize(300, 100);
            stage2.addActor(playerField);
            if (lock_b) {
                choise_skin = "boy";
                girl.remove();
                arrow.setPosition(game.camera.viewportWidth / 2 + 75, game.camera.viewportHeight - 110);
                arrow.setSize(10, 10);
                playerField.draw(game.batch, 0.1f);
                nikname_player = playerField.getText();

            }
            if (lock_g) {
                boy.remove();
                choise_skin = "girl";
                girl.setPosition(game.camera.viewportWidth / 2 - 300, 100);
                font.draw(game.batch, "What is your name? ", game.camera.viewportWidth / 2 + 75, game.camera.viewportHeight - 68);
                arrow.setPosition(game.camera.viewportWidth / 2 + 75, game.camera.viewportHeight - 110);
                arrow.setSize(10, 10);
                playerField.draw(game.batch, 0.1f);
                nikname_player = playerField.getText();

            }

        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)){
            System.out.println("player");
            set_name_rival = true;
            System.out.println("Set name palyer:"+set_name_rival);
        }
        return (nikname_player);
    }
    private  String chooseNiknameRival(boolean lock_b ){
        if (lock_b){
            boy.remove();
        }
        else {
            girl.remove();
        }
        playerField.remove();
        stage2.addActor(rival);
        stage2.addActor(rivalField);
        font.draw(game.batch, "What is the name of your friend? ", game.camera.viewportWidth / 2 + 75, game.camera.viewportHeight - 68);
        arrow.setPosition(game.camera.viewportWidth / 2 + 75, game.camera.viewportHeight - 110);
        arrow.setSize(10, 10);
        rivalField.draw(game.batch, 0.1f);
        nickname_rival = rivalField.getText();

        if(Gdx.input.isKeyJustPressed(Input.Keys.ENTER) && !nickname_rival.isEmpty()){
            System.out.println("rivale");
            finish = true;
            System.out.println("set name rival: "+ finish);
        }
        return  (nickname_rival);
    }

    @Override
    public void  dispose(){
        shapeRenderer.dispose();
        stage2.dispose();
    }

    @Override
    public boolean keyDown ( int keycode){
        return false;
    }

    @Override
    public boolean keyUp ( int keycode){
        return false;
    }

    @Override
    public boolean keyTyped ( char character){
        return false;
    }

    @Override
    public boolean touchDown ( int screenX, int screenY, int pointer, int button){
        return false;
    }

    @Override
    public boolean touchUp ( int screenX, int screenY, int pointer, int button){
        return false;
    }

    @Override
    public boolean touchCancelled ( int screenX, int screenY, int pointer, int button){
        return false;
    }

    @Override
    public boolean touchDragged ( int screenX, int screenY, int pointer){
        return false;
    }

    @Override
    public boolean mouseMoved ( int screenX, int screenY){
        return false;
    }

    @Override
    public boolean scrolled ( float amountX, float amountY){
        return false;
    }
}
