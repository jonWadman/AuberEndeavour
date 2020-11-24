package commygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import commygdx.game.AI.InfiltratorAI;
import commygdx.game.AI.MovementAI;

import commygdx.game.AI.graph.PathGraph;
import commygdx.game.Screens.PlayScreen;
import commygdx.game.ShipSystem;
import commygdx.game.stages.Hud;
import commygdx.game.syst.MovementSystem;
//import org.graalvm.compiler.lir.aarch64.AArch64Move;

public class Infiltrator extends Character {

    //Constants
    private final float MOV_SPEED = 9f;
    private final float TIME_TO_DESTROY = 500f;

    private InfiltratorAI ai;
    private Vector2 destination;
    private boolean isArrested;
    //0=none, 1=invisibility, 2=hallucination 3=shapeshift 4=speed booast
    private int power;
    private float powerCoolDown;
    private float powerDuration;
    private boolean powerOn;
    private ShipSystem destroyingSystem=null;
    private float destructionTimer = 0;
    private boolean facingRight;


    public Infiltrator(Vector2 position, SpriteBatch batch, int power,PathGraph graph) {
        super(position, batch);
        setPosition(position.x,position.y);
        this.power=power;
        powerOn=false;
        powerDuration=0;
        powerCoolDown=0;
        ai = new InfiltratorAI(graph);
        facingRight=true;
    }

    @Override
    public void act(float delta) {
        if(isArrested){
            if (destroyingSystem!=null){destroyingSystem.setState(0);}
            return;}
        if(destroyingSystem!=null){
            destructionTimer += delta*100;
            if(destructionTimer>TIME_TO_DESTROY){
                destroyingSystem.destroy();
                destroyingSystem = null;
            }
        }else {
            ai.update(delta, new Vector2(getX(), getY()));
            super.act(delta);
            powerCoolDown = (int) (Math.random() * 1000);
        }
    }

    public void usePower(PlayScreen screen,String room){
        resetPower();
        if (power==1){sprite.setTexture(new Texture(Gdx.files.internal("Characters/infiltratorInvisibleSprite.png")));}
        if (power==2&&room!="infirmary"){screen.setHallucinate(true);}
        if (power==3){sprite.setTexture(new Texture(Gdx.files.internal("Characters/infiltratorShapeshift.png")));}
        if (power==4){movementSystem.setSpeed(20f);}
    }

    private void resetPower(){
        powerCoolDown=0;
        powerDuration=0;
        powerOn=true;
    }

    public void stopPower(PlayScreen screen){
        if (power==1){resetTexture(); }
        if (power==3){resetTexture();}
        if (power==4){movementSystem.setSpeed(MOV_SPEED);}
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
        if(ai.left(new Vector2(getX(),getY()),isArrested)){
            Vector2 pos = movementSystem.left();
            setPosition(pos.x, pos.y);
            if (facingRight==true){
                sprite.flip(true,false);
                facingRight=false;
            }
        }
        if(ai.right(new Vector2(getX(),getY()),isArrested)){
            Vector2 pos = movementSystem.right();
            setPosition(pos.x, pos.y);
            if (facingRight==false){
                sprite.flip(true,false);
                facingRight=true;
            }
        }
        if(ai.up(new Vector2(getX(),getY()),isArrested)){
            Vector2 pos = movementSystem.up();
            setPosition(pos.x, pos.y);
        }
        if(ai.down(new Vector2(getX(),getY()),isArrested)){
            Vector2 pos = movementSystem.down();
            setPosition(pos.x, pos.y);
        }
    }

    public void arrest(Vector2 coords){
        isArrested = true;
        setPosition(coords.x, coords.y);


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

    public boolean isAvailable(){
        return !(isArrested&&destroyingSystem!=null);
    }

    public void startDestruction(ShipSystem system){
        destroyingSystem=system;
        destructionTimer = 0;
    }

    public boolean getIsArrested(){return isArrested;}



}
