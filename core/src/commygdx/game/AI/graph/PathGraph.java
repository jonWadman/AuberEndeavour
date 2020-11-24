package commygdx.game.AI.graph;

import com.badlogic.gdx.math.Vector2;
import commygdx.game.AI.graph.queue.PriorityItem;
import commygdx.game.AI.graph.queue.PriorityQueue;
import sun.awt.image.ImageWatched;

import java.util.ArrayList;
import java.util.LinkedList;

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

    public PathNode getRandomWorkingSystem(){
        LinkedList<PathNode> workingSystems = new LinkedList<PathNode>();
        for(PathNode node:nodes){
            if(node.isWorkingSystem()){
                workingSystems.add(node);
            }
        }
        if(workingSystems.isEmpty()){return null;}
        return workingSystems.get((int) Math.floor(Math.random()*workingSystems.size()));
    }

    public  PathNode findPath(PathNode start,PathNode goal){
        //Maximum amount of iterations before the search ends as a failure
        final int MAX_ITERATIONS = 100;

        //A* search, returns the next node the AI should go to

        //Custom priority queue used to store the fringe of nodes to be visited
        PriorityQueue fringe = new PriorityQueue();
        LinkedList<PathNode> visited = new LinkedList<>();
        fringe.push(new PriorityItem(start,null,0,0));
        int count = 0;
        while(count<MAX_ITERATIONS && !fringe.isEmpty()){
            count++;
            PriorityItem current = fringe.pop();
            if(current.node.equals(goal)){
                return current.firstStep;
            }
            visited.add(current.node);

            //Inserting children into queue
            PathNode[] next = current.node.getEdges();
            PathNode firstStep = current.firstStep;
            for(PathNode node:next) {
                if (!visited.contains(node)) {
                    if (current.firstStep == null) {
                        firstStep = node;
                    }
                    fringe.push(new PriorityItem(node, firstStep,
                            heuristic(current.node, goal, node, current.pathCost),
                            pathCost(current.pathCost, current.node, node)));
                }
            }
        }
        return null;
    }

    private float pathCost(float currentPathCost,PathNode current,PathNode next){
        return currentPathCost + current.position.dst2(next.position);
    }

    private float heuristic(PathNode current,PathNode goal,PathNode next, float currentPathCost){
        //heuristic
        float heuristic = goal.position.dst(next.position);
        //path cost
        heuristic +=pathCost(currentPathCost,current,next);
        return heuristic;
    }

    public ArrayList<PathNode> getNodes(){
        return nodes;
    }
}
