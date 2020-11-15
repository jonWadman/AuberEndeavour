package commygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import commygdx.game.AI.MovementAI;
import commygdx.game.syst.MovementSystem;

public class Infiltrator extends Sprite {
    private World world;
    private Body body;
    private MovementSystem movementSystem;
    private MovementAI movementAI;
    private Vector2 destination;


    public Infiltrator(World world){
        this.world = world;
        createBody();
        movementSystem = new MovementSystem(body,1000);
        movementAI = new MovementAI(body,destination);
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

    private void handleMovement(){
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
