package commygdx.game.system;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class MovementSystem {

    Body body;
    float movementSpeed;

    public MovementSystem(Body body,float speed){
        this.body = body;
        this.movementSpeed = speed;
    }

    public void left(){
        body.applyForce(new Vector2(-movementSpeed,0),body.getWorldCenter(),true);
    }

    public void right(){
        body.applyForce(new Vector2(movementSpeed,0),body.getWorldCenter(),true);
    }

    public void up(){
        body.applyForce(new Vector2(0,movementSpeed),body.getWorldCenter(),true);
    }

    public void down(){
        body.applyForce(new Vector2(0,-movementSpeed),body.getWorldCenter(),true);
    }
}
