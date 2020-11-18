package commygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Hashtable;
import java.util.List;

public class Box2dWorld {
    public Hashtable<Vector2, Boolean> systems;
    public Hashtable<Vector2, String> teleporters;

    public Box2dWorld(PlayScreen screen){
        String[] rooms= new String[]{"command", "lab", "infirmary","crew","brig","engine"};

        TiledMap map= screen.getMap();

         systems = new Hashtable<Vector2, Boolean>();
         teleporters = new Hashtable<Vector2, String>();
        //get systems
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            Vector2 center=new Vector2(rect.x,rect.y);
            systems.put(center,true);


        }
        //objects
        //for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            //Rectangle rect = ((RectangleMapObject) object).getRectangle();

        //}
        //teleporters
        int roomId=0;
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            Vector2 center=new Vector2(rect.x*12,rect.y*12);

            teleporters.put(center,rooms[roomId]);
            roomId+=1;


        }
        //walls
        //for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            //Rectangle rect = ((RectangleMapObject) object).getRectangle();

        //}

    }
}
