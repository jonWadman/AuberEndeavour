package commygdx.game;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import commygdx.game.Screens.PlayScreen;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class TileWorld {
    private Hashtable<String,Rectangle> teleporters;
    private ArrayList<ShipSystem> shipSystems;
    private ArrayList<Rectangle> collisionBoxes;
    private int MAG=12;

    private Rectangle infirmary;
    private Rectangle brig;
    private Rectangle crew;
    private Rectangle command;
    private Rectangle laboratory;
    private Rectangle engine;


    public TileWorld(PlayScreen screen){


        String[] rooms= new String[]{"command", "laboratory", "infirmary","crew","brig","engine"};
        shipSystems = new ArrayList<>();
        collisionBoxes=new ArrayList<>();
        TiledMap map= screen.getMap();

        createRooms(map);

         teleporters = new Hashtable<String,Rectangle>();
        // systems
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = magnifyRectange(((RectangleMapObject) object).getRectangle());
            shipSystems.add(new ShipSystem(rect.x,rect.y,getRoom(rect.x,rect.y),screen.graph));


        }
        //temp
        System.out.println(shipSystems.get(0));
        //objects
        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = magnifyRectange(((RectangleMapObject) object).getRectangle());
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
            Rectangle rect = magnifyRectange(((RectangleMapObject) object).getRectangle());
            collisionBoxes.add(rect);

        }
    }

    private void createRooms(TiledMap map){
        MapObject roomObj=new MapObject();
        roomObj=map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class).get(0);
        infirmary = magnifyRectange(((RectangleMapObject) roomObj).getRectangle());
        roomObj=map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class).get(0);
        brig = magnifyRectange(((RectangleMapObject) roomObj).getRectangle());
        roomObj=map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class).get(0);
        crew = magnifyRectange(((RectangleMapObject) roomObj).getRectangle());
        roomObj=map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class).get(0);
        laboratory = magnifyRectange(((RectangleMapObject) roomObj).getRectangle());
        roomObj=map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class).get(0);
        command = magnifyRectange(((RectangleMapObject) roomObj).getRectangle());
        roomObj=map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class).get(0);
        engine = magnifyRectange(((RectangleMapObject) roomObj).getRectangle());

    }
    private Rectangle magnifyRectange(Rectangle rect){
        rect.x=rect.x*MAG;
        rect.y=rect.y*MAG;
        rect.width=rect.width*MAG;
        rect.height=rect.height*MAG;
        return rect;
    }

    public Hashtable<String, Rectangle> getTeleporters(){
        return teleporters;

    }

    public Rectangle getInfirmary(){return infirmary;}

    public ArrayList<ShipSystem> getSystems(){
        return shipSystems;
    }

    public ArrayList<Rectangle> getCollisionBoxes(){
            return collisionBoxes;
    }

    public String getRoom(float x, float y){
        if (infirmary.contains(x,y)){
            return "infirmary";
        }else if (command.contains(x,y)){
            return "command";
        }else if (laboratory.contains(x,y)){
            return "laboratory";
        }else if (brig.contains(x,y)){
            return "brig";
        }else if (crew.contains(x,y)){
            return  "crew";
        }else if (engine.contains(x,y)){
            return "engine";
        } else{
            return "none";
        }

    }
}
