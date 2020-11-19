package commygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TileWorld {
    private Hashtable<String,Vector2> teleporters;
    private List<ShipSystem> shipSystems;

    public TileWorld(PlayScreen screen){
        String[] rooms= new String[]{"command", "laboratory", "infirmary","crew","brig","engine"};
        shipSystems = new ArrayList<>();
        TiledMap map= screen.getMap();

         teleporters = new Hashtable<String,Vector2>();
        //get systems
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            shipSystems.add(new ShipSystem(rect.x,rect.y,getRoom(rect.x,rect.y)));




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
            teleporters.put(rooms[roomId],center);
            roomId+=1;


        }
        //walls
        //for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            //Rectangle rect = ((RectangleMapObject) object).getRectangle();

        //}

    }

    public Hashtable<String, Vector2> getTeleporters(){
        return teleporters;

    }
    public List<ShipSystem> getSystems(){
        return shipSystems;
    }

    public String getRoom(float x, float y){
        if (y>740){
            return "command";
        }else if (y>545){
            return "laboratory";
        }else if (x<240){
            return "brig";
        }else if (x>550){
            return  "crew";
        }else if (y<305){
            return "engine";
        }else{
            return "infirmary";
        }
    }
}
