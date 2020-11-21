package commygdx.game.AI.graph.queue;

import com.badlogic.gdx.math.Vector2;
import commygdx.game.AI.graph.PathNode;

public class TestQueue {

    public static void main(String args[]){
        PriorityItem[] items = {
                new PriorityItem(new PathNode(new Vector2(100,100),true),null,100,100),
                new PriorityItem(new PathNode(new Vector2(700,700),true),null,700,100),
                new PriorityItem(new PathNode(new Vector2(300,300),true),null,300,100),
                new PriorityItem(new PathNode(new Vector2(800,800),true),null,800,100),
                new PriorityItem(new PathNode(new Vector2(200,200),true),null,200,100),
                new PriorityItem(new PathNode(new Vector2(500,500),true),null,500,100),
                new PriorityItem(new PathNode(new Vector2(400,400),true),null,400,100),
        };
        PriorityQueue queue = new PriorityQueue();
        for (PriorityItem item:items){
            queue.push(item);
        }
        while(!queue.isEmpty()){
            System.out.println(queue.pop().node.position.x);
        }
    }
}
