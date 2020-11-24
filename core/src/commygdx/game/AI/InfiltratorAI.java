package commygdx.game.AI;

import com.badlogic.gdx.math.Vector2;
import commygdx.game.AI.graph.PathGraph;
import commygdx.game.AI.graph.PathNode;

public class InfiltratorAI {
    //The Infiltrator will go here if it has nowhere else to go
    private final PathNode restingPosition = new PathNode(new Vector2(4712,4956),false);

    private MovementAI movAI;
    private PathGraph graph;
    private PathNode goal;

    public InfiltratorAI(PathGraph graph){
        this.graph = graph;
        movAI = new MovementAI();
    }

    public void update(float dt,Vector2 position) {
        //If the AI does not have a goal a new goal is generated, if the player is at the generated goal a new one will be generated
        //An appropriate destination is then set
        if (goal==null) {
            while (goal == null) {
                goal = generateNewGoal();
                if (MovementAI.closeEnough(position, goal.position)) {
                    goal = null;
                }
            }
            movAI.setDestination(generateNewDestination(position));
        }
        //If the Ai is at it's destination the next one is set
        if (movAI.atDestination(position)) {
            if(movAI.destination == restingPosition.position){
                goal = generateNewGoal();
            }
            movAI.setDestination(generateNewDestination(position));
        }
    }

    private PathNode generateNewGoal(){
        PathNode goal = graph.getRandomWorkingSystem();
        if(goal!=null){
            return goal;
        }
        return restingPosition;
    }

    private Vector2 generateNewDestination(Vector2 position){
        PathNode nearest = graph.getNearestNode(position);
        if(MovementAI.closeEnough(nearest.position,position)){
            PathNode destNode = graph.findPath(nearest,goal);
            if(destNode == null){
                destNode = graph.getMostEdgesAdjacentNode(nearest);
                goal = null;
            }
            return destNode.position;
        }
        return nearest.position;
    }

    //Directional movement methods



    public boolean left(Vector2 position,boolean arrested){
        if(!arrested && movAI.left(position)){
            return true;
        }
        return false;
    }

    public boolean right(Vector2 position,boolean arrested){
        if(!arrested && movAI.right(position)){
            return true;
        }
        return false;
    }

    public boolean up(Vector2 position,boolean arrested){
        if(!arrested && movAI.up(position)){
            return true;
        }
        return false;
    }

    public boolean down(Vector2 position,boolean arrested){
        if(!arrested && movAI.down(position)){
            return true;
        }
        return false;
    }
}
