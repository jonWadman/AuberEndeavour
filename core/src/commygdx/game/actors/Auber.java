package commygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import commygdx.game.TileWorld;
import commygdx.game.input.PlayerInput;

import java.util.List;
import java.util.Vector;

public class Auber extends Character {

    public Auber(Vector2 position, SpriteBatch batch,float MOV_SPEED) {
        super(position,batch,MOV_SPEED);
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

    public boolean  teleportCheck(TileWorld tiles){
        for ( Vector2 val : tiles.getTeleporters().values()) {
            if( getX()>val.x-50 & getX()<val.x+50 & getY()>val.y-50& getY()<val.y+50){
                return true;
            }
        }
        return false;
    }

    public void arrest(List<Infiltrator> infiltrators,Vector2 jail){
        if(PlayerInput.arrest()) {
            for (Infiltrator infiltrator : infiltrators) {
                if (Math.abs(infiltrator.getX() - this.getX()) < 10 && Math.abs(infiltrator.getY() - this.getY()) < 10) {
                    infiltrator.setX(jail.x);
                    infiltrator.setY(jail.y);
                }
            }
        }
    }
    //moves the camera to the auber when game starts
    public void shuffle(){
        setPosition(getX()+1,getY());
    }

}

