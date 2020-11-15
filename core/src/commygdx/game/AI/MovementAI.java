package commygdx.game.AI;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class MovementAI {
    Body body;
    Vector2 destination;
    public MovementAI(Body body, Vector2 destination){

    }

    public void setDestination(Vector2 destination) {
        this.destination = destination;
    }

    public boolean left(){
        if(body.getPosition().x>destination.x){
            return true;
        }return false;
    }

    public boolean right(){
        if(body.getPosition().x<destination.x){
            return true;
        }return false;
    }

    public boolean up(){
        if(body.getPosition().y<destination.y){
            return true;
        }return false;
    }

    public boolean down(){
        if(body.getPosition().y>destination.y){
            return true;
        }return false;
    }
}
