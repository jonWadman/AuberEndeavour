package commygdx.game.AI;

import com.badlogic.gdx.math.Vector2;
import commygdx.game.actors.Infiltrator;

public class InfiltratorAI {
    private MovementAI movAI;
    private PathGraph graph;
    private PathNode goal;

    public InfiltratorAI(PathGraph graph){
        this.graph = graph;
        movAI = new MovementAI();
    }

    public void update(float dt){
        
    }

    private Vector2 generateNewDestination(){

        return null;
    }
}
