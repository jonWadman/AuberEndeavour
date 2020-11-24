package commygdx.game.AI.graph;

import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class PathNode {
    private boolean system;
    private boolean working;

    private ArrayList<PathNode> edges;

    public Vector2 position;

    public PathNode(Vector2 position, boolean system){
        this.position = position;
        this.system = system;
        this.working = true;
        edges = new ArrayList<PathNode>();
    }

    @Override
    public boolean equals(Object obj) {
        if(position==((PathNode)obj).position){
            return  true;
        }
        return false;
    }

    public void addEdge(PathNode node){
        edges.add(node);
    }

    public PathNode[] getEdges() {
        //manually doing toArray to avoid ClassCastException
        PathNode[] edgesArray = new PathNode[edges.size()];
        for(int i=0;i< edges.size();i++){
            edgesArray[i] = edges.get(i);
        }
        return edgesArray;
    }

    public boolean isWorkingSystem(){
        if(system&&working){
            return true;
        }
        return false;
    }

    public void setWorking(boolean working){
        this.working = working;
    }

    public boolean isNonSystem(){
        return !system;
    }

    @Override
    public String toString() {
        String s = position.toString();
        if(system){s= s + " " + "system";}
        if(working){s= s + " " + "working";}
        return s;
    }
}
