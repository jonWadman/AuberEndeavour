package commygdx.game.AI;

import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import commygdx.game.queue.PriorityItem;
import commygdx.game.queue.PriorityQueue;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

public class PathGraph {
    private final int VERY_LARGE = 2147483647;

    private ArrayList<PathNode> nodes;

    public PathGraph(){
        nodes = new ArrayList<PathNode>();
    }

    public PathGraph(PathNode[] initNodes){
        super();
        for(PathNode node:initNodes){
            addNode(node);
        }
    }

    public void addNode(PathNode node){
        if(nodes.contains(node)){
            return;
        }
        nodes.add(node);
    }

    public void addEdge(PathNode node1,PathNode node2){
        if(nodes.contains(node1)&&nodes.contains(node2)&&!node1.equals(node2)){
            node1.addEdge(node2);
            node2.addEdge(node1);
        }
    }

    public PathNode getNearestNode(Vector2 position){
        PathNode nearest = new PathNode(new Vector2(VERY_LARGE,VERY_LARGE),false);
        for (PathNode node:nodes){
            if(position.dst2(node.position)<position.dst2(nearest.position)){
                nearest = node;
            }
        }
        return nearest;
    }

    public  PathNode findPath(PathNode start,PathNode goal){
        //Maximum amount of iterations before the search ends as a failure
        final int MAX_ITERATIONS = 10000;

        //A* search, returns the next node the AI should go to

        //Custom priority queue used to store the fringe of nodes to be visited
        PriorityQueue fringe = new PriorityQueue();
        fringe.push(new PriorityItem(start,null,0,0));
        int count = 0;
        for(int i =0;i<MAX_ITERATIONS;i++){
            PriorityItem current = fringe.pop();
            if(current.node.equals(goal)){
                return current.firstStep;
            }

            //Inserting children into queue
            PathNode[] next = current.node.getEdges();
            PathNode firstStep = current.firstStep;
            for(PathNode node:next){
                if(current.firstStep == null){
                    firstStep = node;
                }
                fringe.push(new PriorityItem(node,firstStep,
                        heuristic(current.node,goal,node,current.pathCost),pathCost(current.pathCost,current.node,node)));
            }
        }
        return null;
    }

    private float pathCost(float currentPathCost,PathNode current,PathNode next){
        return currentPathCost + current.position.dst2(next.position);
    }

    private float heuristic(PathNode current,PathNode goal,PathNode next, float currentPathCost){
        //path cost
        float heuristic = current.position.dst(next.position);
        //heuristic
        heuristic +=pathCost(currentPathCost,current,next);
        return heuristic;
    }
}
