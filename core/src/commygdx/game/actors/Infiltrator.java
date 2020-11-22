package commygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import commygdx.game.AI.MovementAI;

import commygdx.game.Screens.PlayScreen;
import commygdx.game.syst.MovementSystem;
//import org.graalvm.compiler.lir.aarch64.AArch64Move;

public class Infiltrator extends Character {

    //Constants
    private final float MOV_SPEED = 8f;

    private MovementAI movementAI;
    private Vector2 destination;
    private boolean isArrested;
    //0=none, 1=invisibility, 2=hallucination 3=shapeshift 4=speed booast
    private int power;
    private int powerCoolDown;
    private int powerDuration;
    private boolean powerOn;


    public Infiltrator(Vector2 position, SpriteBatch batch, int power) {
        super(position, batch);
        shuffle();
        this.power=power;
        powerOn=false;
        powerDuration=0;
        powerCoolDown=(int)(Math.random()*1000);

    }

    public void usePower(PlayScreen screen){
        resetPower();
        if (power==1){sprite.setTexture(new Texture(Gdx.files.internal("Characters/infiltratorInvisibleSprite.png")));}
        if (power==2){screen.setHallucinate(true);}
        if (power==3){  sprite.setTexture(new Texture(Gdx.files.internal("Characters/infiltratorShapeshift.png")));}
        if (power==4){movementSystem.setMovementSpeed(12f);}
    }

    private void resetPower(){
        powerCoolDown=0;
        powerDuration=0;
        powerOn=true;
    }

    public void stopPower(PlayScreen screen){
        if (power==1){resetTexture(); }
        if (power==3){resetTexture();}
        if (power==4){movementSystem.setMovementSpeed(MOV_SPEED);}
        powerOn=false;


    }

    public float getPowerDuration(){return powerDuration;}
    public float getPowerCooldown(){return powerCoolDown;}

    @Override
    protected Texture getTexture(){
        return new Texture(Gdx.files.internal("Characters/infiltratorSprite.png"));
    }

    @Override
    protected void handleMovement() {
    //    if(movementAI.left(new Vector2(getX(),getY()))){
    //        movementSystem.left();
    //    }
    //    if(movementAI.right(new Vector2(getX(),getY()))){
    //        movementSystem.right();
    //    }
    //    if(movementAI.up(new Vector2(getX(),getY()))){
    //        movementSystem.up();
    //    }
    //    if(movementAI.down(new Vector2(getX(),getY()))){
    //        movementSystem.down();
    //    }
    }

    public void arrest(Vector2 jail){
        isArrested = true;
        setPosition(jail.x, jail.y);
    }
    public void shuffle(){
        Vector2 position = movementSystem.left();
        setPosition(position.x,position.y);
    }



    public void resetTexture(){
        powerCoolDown=0;
        powerDuration=0;
        powerOn=true;
        sprite.setTexture(getTexture());
    }



    public void updateTimers(float dt){
        if (powerOn==false){
            powerCoolDown+=dt;
        }
        if (powerOn==true){
            powerDuration+=dt;
        }
    }



}
