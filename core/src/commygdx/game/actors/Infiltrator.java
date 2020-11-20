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
import org.graalvm.compiler.lir.aarch64.AArch64Move;

public class Infiltrator extends Character {

    private MovementAI movementAI;
    private Vector2 destination;


    public Infiltrator(Vector2 position, SpriteBatch batch,float MOV_SPEED) {
        super(position,batch,MOV_SPEED);
        Texture texture = new Texture(Gdx.files.internal("Characters/infiltratorSprite.png"));
        sprite = new Sprite(texture);
        sprite.setSize(150,170);
        movementSystem = new MovementSystem(position,MOV_SPEED);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(),sprite.getHeight());

    }


    @Override
    protected void handleMovement(){
        if(movementAI.left()){
            movementSystem.left();
        }
        if(movementAI.right()){
            movementSystem.right();
        }
        if(movementAI.up()){
            movementSystem.up();
        }
        if(movementAI.down()){
            movementSystem.down();
        }
    }
}
