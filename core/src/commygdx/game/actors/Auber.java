package commygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import commygdx.game.Box2dWorld;
import commygdx.game.PlayScreen;
import commygdx.game.input.PlayerInput;

public class Auber extends Character {

    public Auber(Vector2 position, SpriteBatch batch) {
        super(position,batch);
    }

    @Override
    protected void handleMovement() {
        //Left movement
        if(PlayerInput.left()){
            Vector2 position = movementSystem.left();
            setPosition(position.x,position.y);
        }
        //Right movement
        if(PlayerInput.right()){
            Vector2 position = movementSystem.right();
            setPosition(position.x,position.y);
        }
        //Up movement
        if(PlayerInput.up()){
            Vector2 position = movementSystem.up();
            setPosition(position.x,position.y);
        }
        //Down movement
        if(PlayerInput.down()){
            Vector2 position = movementSystem.down();
            setPosition(position.x,position.y);
        }
    }

    public boolean  teleportCheck(Box2dWorld tiles){
        for ( Vector2 key : tiles.teleporters.keySet()) {
            if( getX()>key.x-50 & getX()<key.x+50 & getY()>key.y-50& getY()<key.y+50){
                return true;

            }
        }
        return false;
    }

    //moves the camera to the auber when game starts
    public void shuffle(){
        setPosition(getX()+1,getY());
    }
}
