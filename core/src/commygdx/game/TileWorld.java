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
    private Hashtable<String,Rectangle> teleporters;
    private List<ShipSystem> shipSystems;
    private List<Rectangle> collisionBoxes;
    private int MAG=12;

    public TileWorld(PlayScreen screen){
        String[] rooms= new String[]{"command", "laboratory", "infirmary","crew","brig","engine"};
        shipSystems = new ArrayList<>();
        collisionBoxes=new ArrayList<>();
        TiledMap map= screen.getMap();

         teleporters = new Hashtable<String,Rectangle>();
        //get systems
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            shipSystems.add(new ShipSystem(rect.x,rect.y,getRoom(rect.x,rect.y)));




        }
        //objects
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            rect.x=rect.x*MAG;
            rect.y=rect.y*MAG;
            rect.height=rect.height*MAG;
            rect.width=rect.width*MAG;
            collisionBoxes.add(rect);

        }
        //teleporters
        int roomId=0;
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            rect.x=rect.x*MAG+rect.width*MAG/2;
            rect.y=rect.y*MAG+rect.height*MAG/2;
            rect.width=25;
            rect.height=25;
            teleporters.put(rooms[roomId],rect);
            roomId+=1;


        }
        //walls

        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            rect.x=rect.x*MAG;
            rect.y=rect.y*MAG;
            rect.width=rect.width*MAG;
            rect.height=rect.height*MAG;
            collisionBoxes.add(rect);



        }


    }

    public Hashtable<String, Rectangle> getTeleporters(){
        return teleporters;

    }
    public List<ShipSystem> getSystems(){
        return shipSystems;
    }

    public List<Rectangle> getCollisionBoxes(){
            return collisionBoxes;
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
