package commygdx.game.AI;

import com.badlogic.gdx.math.Vector2;
import commygdx.game.AI.graph.PathGraph;
import commygdx.game.AI.graph.PathNode;

public class InfiltratorAI {
    //The Infiltrator will go here if it has nowhere else to go
    private final PathNode restingPosition = new PathNode(new Vector2(100,100),false);

    public MovementAI movAI;
    private PathGraph graph;
    public PathNode goal;

    public InfiltratorAI(PathGraph graph){
        this.graph = graph;
        movAI = new MovementAI();
    }

    public void update(float dt,Vector2 position){
        if(goal==null){
            goal = generateNewGoal(position);
        }
        movAI.setDestination(generateNewDestination(position));
    }

    private PathNode generateNewGoal(Vector2 position){
        PathNode goal = graph.getRandomWorkingSystem();
        if(goal!=null){
            return goal;
        }
        return restingPosition;
    }

    private Vector2 generateNewDestination(Vector2 position){
        PathNode nearest = graph.getNearestNode(position);
        if(MovementAI.closeEnough(nearest.position,position)){
            return graph.findPath(nearest,goal).position;
        }
        return nearest.position;
    }
}