package commygdx.game.syst;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class MovementSystem {

    Collider collider;
    float movementSpeed;

    public MovementSystem(Vector2 position,float speed){
        this.collider = new Collider(position);
        this.movementSpeed = speed;
    }

    public Vector2 left(){
        Vector2 newPos = collider.position;
        newPos.x -= movementSpeed;
        return newPos;
    }

    public Vector2 right(){
        Vector2 newPos = collider.position;
        newPos.x += movementSpeed;
        return newPos;
    }

    public Vector2 up(){
        Vector2 newPos = collider.position;
        newPos.y += movementSpeed;
        return newPos;
    }

    public Vector2 down(){
        Vector2 newPos = collider.position;
        newPos.y -= movementSpeed;
        return newPos;
    }

    public Vector2 getPos(){
        return collider.position;

    }
}
