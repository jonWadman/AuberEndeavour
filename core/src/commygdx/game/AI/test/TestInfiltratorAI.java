package commygdx.game.AI.test;

import com.badlogic.gdx.math.Vector2;
import commygdx.game.AI.InfiltratorAI;
import commygdx.game.AI.graph.PathGraph;
import commygdx.game.AI.graph.PathNode;

public class TestInfiltratorAI {
    public static void main(String args[]){
        PathGraph graph = getGraph();
        InfiltratorAI infiltratorAI = new InfiltratorAI(graph);

        infiltratorAI.update(10,new Vector2(300,101));
        System.out.println(infiltratorAI.goal);
        System.out.println(infiltratorAI.movAI.destination);

        System.out.println("left");
        System.out.println(infiltratorAI.left(new Vector2(-1000,0),false));
        System.out.println(infiltratorAI.left(new Vector2(2000,0),false));
        System.out.println("right");
        System.out.println(infiltratorAI.right(new Vector2(-1000,0),false));
        System.out.println(infiltratorAI.right(new Vector2(2000,0),false));
        System.out.println("up");
        System.out.println(infiltratorAI.up(new Vector2(-1000,-2000),false));
        System.out.println(infiltratorAI.up(new Vector2(2000,3000),false));
        System.out.println("down");
        System.out.println(infiltratorAI.down(new Vector2(-1000,-2000),false));
        System.out.println(infiltratorAI.down(new Vector2(2000,3000),false));
        System.out.println("arrested");
        System.out.println(infiltratorAI.left(new Vector2(2000,-2000),false));
        System.out.println(infiltratorAI.left(new Vector2(2000,3000),true));


    }

    private static PathGraph getGraph(){
        PathGraph graph = new PathGraph();

        PathNode system = new PathNode(new Vector2(1000,-200),true);
        graph.addNode(system);
        PathNode doorInner = new PathNode(new Vector2(850,-150),false);
        graph.addNode(doorInner);
        PathNode doorOuter = new PathNode(new Vector2(800,-150),false);
        graph.addNode(doorOuter);
        PathNode corner = new PathNode(new Vector2(800,100),false);
        graph.addNode(corner);
        PathNode starting = new PathNode(new Vector2(650,100),false);
        graph.addNode(starting);

        PathNode corner1 = new PathNode(new Vector2(300,100),false);
        graph.addNode(corner1);
        PathNode doorOuter1 = new PathNode(new Vector2(300,-150),false);
        graph.addNode(doorOuter1);
        PathNode doorInner1 = new PathNode(new Vector2(250,-150),false);
        graph.addNode(doorInner1);
        PathNode system1 = new PathNode(new Vector2(200,-170),true);
        graph.addNode(system1);

        //edges
        graph.addEdge(system,doorInner);
        graph.addEdge(doorInner,doorOuter);
        graph.addEdge(doorOuter,corner);
        graph.addEdge(corner,starting);

        graph.addEdge(system1,doorInner1);
        graph.addEdge(doorInner1,doorOuter1);
        graph.addEdge(doorOuter1,corner1);
        graph.addEdge(corner1,starting);
        return graph;
    }
}
