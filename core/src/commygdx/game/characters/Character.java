package commygdx.game.characters;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import commygdx.game.syst.MovementSystem;

public class Character {
    protected final float MOV_SPEED = 100f;
    protected World world;
    protected Body body;
    protected MovementSystem movementSystem;

    public Character(World world, Vector2 position){
        this.world = world;
        createBody(position);
        //movementSystem = new MovementSystem(body,MOV_SPEED);
    }

    public void update(){
        handleMovement();
    }

    protected void handleMovement(){

    }

    private void createBody(Vector2 position){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(position);

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
