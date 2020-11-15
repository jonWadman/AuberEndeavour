package commygdx.game.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import commygdx.game.AI.MovementAI;
import commygdx.game.syst.MovementSystem;

public class Infiltrator extends Character {

    private MovementAI movementAI;
    private Vector2 destination;


    public Infiltrator(World world, Vector2 position){
        super(world,position);
        movementAI = new MovementAI(body,destination);
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
