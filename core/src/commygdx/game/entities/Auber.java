package commygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import commygdx.game.input.PlayerInput;
import commygdx.game.system.MovementSystem;

public class Auber extends Sprite {

    private World world;
    private Body body;
    private MovementSystem movementSystem;

    public Auber(World world){
        this.world = world;
        createBody();
        movementSystem = new MovementSystem(body,1000);
    }

    public void update(float dt){
        handleMovement();
        //testing
        System.out.println(body.getPosition());
    }

    private void handleMovement(){
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

    private void createBody(){
        BodyDef bodyDef = new BodyDef();
        //temporary
        bodyDef.position.set(100,100);

        bodyDef.type = BodyDef.BodyType.DynamicBody;
        body = world.createBody(bodyDef);

        FixtureDef fixtureDef = new FixtureDef();
        //Could change to diff shape
        CircleShape shape = new CircleShape();
        shape.setRadius(10);
        fixtureDef.shape = shape;
        body.createFixture(fixtureDef);
    }
}
