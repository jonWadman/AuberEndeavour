package commygdx.game.entities;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Auber extends Sprite {

    private World world;
    private Body body;

    public Auber(World world){
        this.world = world;
        createBody();
    }

    public void update(float dt){
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
