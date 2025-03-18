package com.PokemonGame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    public Sprite sprite;
    private TextureRegion currentFrame;
    private float stateTime;
    private String stance = "";

    public String choose = "boy" ;
    private String path_up;
    private String path_down;
    private String path_left;
    private String path_right;
    // Row -
    // Column |

    private final Animation<TextureRegion> walkUpAnimation;

    private final Animation<TextureRegion> walkDownAnimation;

    private final Animation<TextureRegion> walkRightAnimation;

    private final Animation<TextureRegion> walkLeftAnimation;

    private int frame;

    float frames = 0.15f;

    //players pokemon
    private final ArrayList<Pokemon> pokemon = new ArrayList<>();

    /**
     * @return the pokemon
     */
    public ArrayList<Pokemon> getPokemon() {
        return pokemon;
    }


    public Player() {

        sprite = new Sprite();
        // walk Up Frame Split
        updateSkin();

        Texture textureUp = new Texture(path_up);
        TextureRegion[][] up = TextureRegion.split(textureUp, textureUp.getWidth() / frame, textureUp.getHeight());
        // Up
        TextureRegion[] walkUpFrames = new TextureRegion[frame];
        int index = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < frame; j++) {
                walkUpFrames[index++] = up[i][j];
            }
        }

        // Walk Down Frame Split
        Texture textureDown = new Texture(path_down);
        TextureRegion[][] down = TextureRegion.split(textureDown, textureDown.getWidth() / frame,
            textureDown.getHeight());
        // Down
        TextureRegion[] walkDownFrames = new TextureRegion[frame];
        int index2 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < frame; j++) {
                walkDownFrames[index2++] = down[i][j];
            }
        }

        // Walk Right Frame Split
        Texture textureRight = new Texture(path_right);
        TextureRegion[][] right = TextureRegion.split(textureRight, textureRight.getWidth() / frame,
            textureRight.getHeight());
        // Right
        TextureRegion[] walkRightFrames = new TextureRegion[frame];
        int index3 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < frame; j++) {
                walkRightFrames[index3++] = right[i][j];
            }
        }

        // Walk Left Frame Split
        Texture textureLeft = new Texture(path_left);
        TextureRegion[][] left = TextureRegion.split(textureLeft, textureLeft.getWidth() / frame,
            textureLeft.getHeight());
        // Left
        TextureRegion[] walkLeftFrames = new TextureRegion[frame];
        int index4 = 0;
        for (int i = 0; i < 1; i++) {
            for (int j = 0; j < frame; j++) {
                walkLeftFrames[index4++] = left[i][j];
            }
        }

        //set frames
        walkUpAnimation = new Animation<>(frames, walkUpFrames);
        walkDownAnimation = new Animation<>(frames, walkDownFrames);
        walkRightAnimation = new Animation<>(frames, walkRightFrames);
        walkLeftAnimation = new Animation<>(frames, walkLeftFrames);

        //get the frame
        stateTime = 0f;
        currentFrame = walkUpAnimation.getKeyFrame(stateTime);
        sprite = new Sprite(currentFrame);

    }
    public void setSkin(String choice){
        this.choose = choice;

    }

    public void updateSkin(){
        System.out.println(choose);
        if (choose.equals("girl")){
            path_up = "Player/girl/girl_up.png";
            path_down = "Player/girl/girl_down.png";
            path_left= "Player/girl/girl_left.png";
            path_right="Player/girl/girl_right.png";
            frame=3;
        } else if (choose.equals("boy")) {
            path_up = "Player/red4.png";
            path_down = "Player/red1.png";
            path_left= "Player/red2.png";
            path_right="Player/red3.png";
            frame=4;
        }

    }
    public String getSkin(){
        return choose ;
    }
    /**
     * @param batch
     * @param stance
     */
    public void drawPlayer(SpriteBatch batch, String stance) {
        this.stance = stance;
        updatePlayer();
        sprite.draw(batch);
    }

    public void updatePlayer() {
        //set the frame based on the key pressed and state time
        if (Objects.equals(stance, "walkUp") && Gdx.input.isKeyPressed(Input.Keys.W)) {
            stateTime += Gdx.graphics.getDeltaTime();
            if (stateTime > walkUpAnimation.getAnimationDuration()) {
                stateTime -= walkUpAnimation.getAnimationDuration();
            }
            currentFrame = walkUpAnimation.getKeyFrame(stateTime);

        } else if (Objects.equals(stance, "walkDown") && Gdx.input.isKeyPressed(Input.Keys.S)) {
            stateTime += Gdx.graphics.getDeltaTime();
            if (stateTime > walkDownAnimation.getAnimationDuration()) {
                stateTime -= walkDownAnimation.getAnimationDuration();
            }
            currentFrame = walkDownAnimation.getKeyFrame(stateTime);

        } else if (Objects.equals(stance, "walkRight") && Gdx.input.isKeyPressed(Input.Keys.D)) {
            stateTime += Gdx.graphics.getDeltaTime();
            if (stateTime > walkRightAnimation.getAnimationDuration()) {
                stateTime -= walkRightAnimation.getAnimationDuration();
            }
            currentFrame = walkRightAnimation.getKeyFrame(stateTime);

        } else if (Objects.equals(stance, "walkLeft") && Gdx.input.isKeyPressed(Input.Keys.A)) {
            stateTime += Gdx.graphics.getDeltaTime();
            if (stateTime > walkLeftAnimation.getAnimationDuration()) {
                stateTime -= walkLeftAnimation.getAnimationDuration();
            }
            currentFrame = walkLeftAnimation.getKeyFrame(stateTime);
        }
        sprite.setRegion(currentFrame);
    }

    /**
     * @param f
     */
    public void translateX(float f) {
        sprite.translateX(f);
    }

    /**
     * @param f
     */
    public void translateY(float f) {
        sprite.translateY(f);
    }

    /**
     * @return
     */
    public int getX() {
        return (int) sprite.getX();
    }

    /**
     * @return
     */
    public int getY() {
        return (int) sprite.getY();
    }

    /**
     * @return
     */
    public Rectangle getBoundingRectangle() {
        return sprite.getBoundingRectangle().setSize(14, 19);
    }

    /**
     * @param i
     * @param j
     */
    public void setPosition(int i, int j) {
        sprite.setPosition(i, j);

    }

}
