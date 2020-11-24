package commygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import commygdx.game.TileWorld;
import commygdx.game.input.PlayerInput;
import commygdx.game.stages.Hud;
import commygdx.game.syst.MovementSystem;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class Auber extends Character {

    private final float MOV_SPEED = 10f;

    public Auber(Vector2 position, SpriteBatch batch) {
        super(position,batch);
        shuffle();
        movementSystem.setSpeed(MOV_SPEED);
    }



    @Override
    protected Texture getTexture() {
        return new Texture(Gdx.files.internal("Characters/auberSprite.png"));
    }

    @Override
    protected void handleMovement() {
        //Left movement
        if(PlayerInput.getDirection()==1){
            Vector2 position = movementSystem.left();
            setPosition(position.x,position.y);
        }
        //Right movement
        if(PlayerInput.getDirection()==2){
            Vector2 position = movementSystem.right();
            setPosition(position.x,position.y);
        }
        //Up movement
        if(PlayerInput.getDirection()==3){
            Vector2 position = movementSystem.up();
            setPosition(position.x,position.y);
        }
        //Down movement
        if(PlayerInput.getDirection()==4){
            Vector2 position = movementSystem.down();
            setPosition(position.x,position.y);
        }
    }

    public boolean  teleportCheck(TileWorld tiles){
        //check if standing on teleporter
        for ( Rectangle rect : tiles.getTeleporters().values()) {
            if(sprite.getBoundingRectangle().contains(rect)){
                return true;
            }
        }
        return false;
    }

    public void arrest(ArrayList<Infiltrator> infiltrators,Hud hud){
        /*Arrests the infiltrator if in range and puts it in jail
        * @param infiltrators this list of infiltrators that are being checked
        * @hud the hud overlay*/
        if(PlayerInput.arrest()) {
            for (Infiltrator infiltrator : infiltrators) {
                if (Math.abs(infiltrator.getX() - this.getX()) < 200 && Math.abs(infiltrator.getY() - this.getY()) < 200) {
                    infiltrator.arrest(new Vector2((float)Math.random()*1000+1200,(float)Math.random()*400+5400));
                    hud.infiltratorCaught();
                }
            }
        }
    }
    //moves the camera to the auber when game starts
    public void shuffle(){
        Vector2 position = movementSystem.left();
        setPosition(position.x,position.y);
    }

}

