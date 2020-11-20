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

import commygdx.game.syst.MovementSystem;
//import org.graalvm.compiler.lir.aarch64.AArch64Move;

public class Infiltrator extends Character {

    //Constants
    private final float MOV_SPEED = 8f;

    private MovementAI movementAI;
    private Vector2 destination;
    private boolean isArrested;


    public Infiltrator(Vector2 position, SpriteBatch batch) {
        super(position, batch);
    }

    @Override
    protected Texture getTexture(){
        return new Texture(Gdx.files.internal("Characters/infiltratorSprite.png"));
    }

    @Override
    protected void handleMovement() {
        if(movementAI.left(new Vector2(getX(),getY()))){
            movementSystem.left();
        }
        if(movementAI.right(new Vector2(getX(),getY()))){
            movementSystem.right();
        }
        if(movementAI.up(new Vector2(getX(),getY()))){
            movementSystem.up();
        }
        if(movementAI.down(new Vector2(getX(),getY()))){
            movementSystem.down();
        }
    }

    public void arrest(Vector2 jail){
        isArrested = true;
        setPosition(jail.x, jail.y);
    }
}
