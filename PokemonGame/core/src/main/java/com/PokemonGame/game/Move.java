package com.PokemonGame.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Move {
    // globals
    private final Type type;
    private Category category;
    private float power;
    private String name;
    float stateTime;
    //private  Texture attack;

    private Animation<TextureRegion> animation;

    // create static moves allow access
    public static Move nullMove = new Move("", Type.NORMAL, Category.PASSIVE, 0, null ,0 ,0 );
    public static Move VineWhip = new Move("VINEWHIP", Type.GRASS, Category.PHYSICAL, 40f,"AnimationMove/vinewipe.png" , 1,5 );
    public static Move Ember = new Move("EMBER", Type.FIRE, Category.PHYSICAL, 40f,"AnimationMove/ember.png" ,4 , 1);
    public static Move Bubble = new Move("BUBBLE", Type.WATER, Category.PHYSICAL, 40f,"AnimationMove/bubble.png" ,4 ,4 );
    public static Move Growl = new Move("GROWL", Type.NORMAL, Category.PASSIVE, 0f, "AnimationMove/howl.png",5 ,2 );
    public static Move WaterPulse = new Move("WATER PULSE", Type.WATER, Category.PHYSICAL, 60f, "AnimationMove/waterpulse.png",4 ,4 );
    public static Move Screech = new Move("SCREECH", Type.NORMAL, Category.PASSIVE, 0f,"AnimationMove/screech.png" ,4 , 1);
    public static Move Tackle = new Move("TACKLE", Type.NORMAL, Category.PHYSICAL, 40f,"AnimationMove/Takle.png" ,2 ,1 );


    /**
     * @param name
     * @param type
     * @param category
     * @param power
     * @param path
     * @param framecoln
     * @param framerow
     */
    public Move(String name, Type type, Category category, float power, String path, int framecoln, int framerow) {
        this.category = category;
        this.type = type;
        this.power = power;
        this.name = name;
        this.animation= setAnimation(path,framecoln,framerow);
    }

    /**
     * @return the category
     */
    public Category getCategory() {
        return category;
    }

    /**
     * @param category
     *            the category to set
     */
    public void setCategory(Category category) {
        this.category = category;
    }

    /**
     * @return the power
     */
    public float getPower() {
        return power;
    }

    /**
     * @param power
     *            the power to set
     */
    public void setPower(float power) {
        this.power = power;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return
     */
    public Type getType() {
        return type;
    }

    /**
     * @author KLEM
     *
     */
    public enum Category {
        PHYSICAL, PASSIVE
    }
    public  Animation<TextureRegion>  setAnimation(String path, int  FRAME_COLS, int FRAME_ROW){
        if (path != null) {
            Texture attack = new Texture(Gdx.files.internal(path));
            TextureRegion[][] tmp = TextureRegion.split(attack, attack.getWidth() / FRAME_COLS, attack.getHeight() / FRAME_ROW);
            TextureRegion[] animation_frame = new TextureRegion[FRAME_COLS * FRAME_ROW];
            int index = 0;
            for (int i = 0; i < FRAME_ROW; i++) {
                for (int j = 0; j < FRAME_COLS; j++) {
                    animation_frame[index++] = tmp[i][j];
                }
            }
            animation = new Animation<TextureRegion>(0.25f, animation_frame);
        }
        return animation;
    }
    public void drawAnimation(Batch batch, int posX, int posY ){
        stateTime = Gdx.graphics.getDeltaTime();
        TextureRegion current_region= animation.getKeyFrame(stateTime);
        if (getName().equals("VINEWHIP")) {
            if (!animation.isAnimationFinished(stateTime)) {
                batch.draw(current_region, posX, (posY + 40) - stateTime);
            }
        }
        if (getName().equals("EMBER")){
            if(!animation.isAnimationFinished(stateTime)){
                batch.draw(current_region, posX, posY);
            }
        }
        if(getName().equals("BUBBLE")){
            if (!animation.isAnimationFinished(stateTime)){
                batch.draw(current_region,posX, posY);
            }
        }
        if(getName().equals("GROWL")){
            if (!animation.isAnimationFinished(stateTime)){
                batch.draw(current_region,posX, posY);
            }
        }
        if (getName().equals("SCREECH")) {
            if (!animation.isAnimationFinished(stateTime)) {
                batch.draw(current_region, posX, posY);
            }
        }
        if(getName().equals("TACKLE")){
            if(!animation.isAnimationFinished(stateTime)){
                batch.draw(current_region, posX,posY);
            }
        }

    }
    public Animation<TextureRegion> getAnimation(){
        return animation;
    }

}
