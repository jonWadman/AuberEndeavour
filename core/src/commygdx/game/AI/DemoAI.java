package commygdx.game.AI;

import com.badlogic.gdx.math.Vector2;
import commygdx.game.AI.graph.PathGraph;
import commygdx.game.AI.graph.PathNode;

public class DemoAI extends InfiltratorAI {

    public DemoAI(PathGraph graph){
        super(graph);
    }

    @Override
    protected PathNode generateNewGoal() {
        PathNode goal = graph.getRandomNonSystem();
        if(goal!=null){
            return goal;
        }
        return restingPosition;
    }

    public boolean left(Vector2 position) {
        if (movAI.left(position)) {
            return true;
        }
        return false;
    }

    public boolean right(Vector2 position) {
        if (movAI.right(position)) {
            return true;
        }
        return false;
    }

    public boolean up(Vector2 position) {
        if (movAI.up(position)) {
            return true;
        }
        return false;
    }

    public boolean down(Vector2 position) {
        if (movAI.down(position)) {
            return true;
        }
        return false;
    }
}
