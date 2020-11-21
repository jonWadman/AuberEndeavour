package commygdx.game.AI.test;

import com.badlogic.gdx.math.Vector2;
import commygdx.game.AI.graph.PathGraph;
import commygdx.game.AI.graph.PathNode;

public class TestGraph {
    public static void main(String args[]){
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

        //testing nearest node
        //testNearest(graph);

        /*
        //test path finding
        System.out.println(graph.findPath(starting,system1).position);
        System.out.println(graph.findPath(corner1,system1).position);
        System.out.println(graph.findPath(doorOuter1,system1).position);
        System.out.println(graph.findPath(doorInner1,system1).position);*/

        for(int i=0;i<10;i++){
            PathNode pos = graph.getRandomWorkingSystem();
            if(pos!=null) {
                System.out.println(pos);
            }else{System.out.println("none");}
        }
    }
    private static void testNearest(PathGraph graph){
        double randomx;
        double randomy;
        for(int i=0;i<10;i++){
            randomx = (Math.random()*800 + 400);
            randomy = (Math.random()*600 -300);
            System.out.println(randomx);
            System.out.println(randomy);
            System.out.println(graph.getNearestNode(new Vector2((float)randomx,(float)randomy)).position);
        }
    }
}
