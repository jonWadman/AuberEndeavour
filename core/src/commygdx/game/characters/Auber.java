package commygdx.game.characters;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import commygdx.game.input.PlayerInput;
import commygdx.game.syst.MovementSystem;

public class Auber extends Character {

    private World world;
    private Body body;
    private MovementSystem movementSystem;

    public Auber(World world, Vector2 position){
        super(world,position);
    }

    @Override
    public void update(){
        super.update();
    }

    @Override
     void handleMovement(){
        if(PlayerInput.left()){
            movementSystem.left();
        }
        if(PlayerInput.right()){
            movementSystem.right();
        }
        if(PlayerInput.up()){
            movementSystem.up();
        }
        if(PlayerInput.down()){
            movementSystem.down();
        }
    }
}
