package commygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import commygdx.game.TileWorld;
import commygdx.game.input.PlayerInput;
import commygdx.game.syst.MovementSystem;

import java.util.List;
import java.util.Vector;

public class Auber extends Character {


    public Auber(Vector2 position, SpriteBatch batch,float MOV_SPEED) {
        Texture texture = new Texture(Gdx.files.internal("Characters/auberSprite.png"));
        sprite = new Sprite(texture);
        sprite.setSize(150,170);
        movementSystem = new MovementSystem(position,MOV_SPEED);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(),sprite.getHeight());
        shuffle();
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
        Vector2 position = movementSystem.left();
        setPosition(position.x,position.y);
    }

}

