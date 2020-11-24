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


    private Rectangle infirmary;
    private Rectangle brig;
    private Rectangle crew;
    private Rectangle command;
    private Rectangle laboratory;
    private Rectangle engine;
    private int scale;


    public TileWorld(PlayScreen screen){
        /* Creates all objects that the sprites can interactive with
        *@param screen the main game screen*/

        TiledMap map= screen.getMap();
        this.scale=AuberGame.ZOOM;
        createRooms(map);


        // create systems
        shipSystems = new ArrayList<>();
        for(MapObject object : map.getLayers().get(1).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = magnifyRectange(((RectangleMapObject) object).getRectangle());
            shipSystems.add(new ShipSystem(rect.x,rect.y,getRoom(rect.x,rect.y),screen.graph));


        }

        //create objects
        collisionBoxes=new ArrayList<>();

        for(MapObject object : map.getLayers().get(2).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = magnifyRectange(((RectangleMapObject) object).getRectangle());
            collisionBoxes.add(rect);

        }
        //create teleporters
        teleporters = new Hashtable<String,Rectangle>();
        for(MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = ((RectangleMapObject) object).getRectangle();
            rect.x=rect.x*scale+rect.width*scale/2;
            rect.y=rect.y*scale+rect.height*scale/2;
            rect.width=25;
            rect.height=25;
            teleporters.put(getRoom(rect.x,rect.y),rect);


        }
        //walls

        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)){
            Rectangle rect = magnifyRectange(((RectangleMapObject) object).getRectangle());
            collisionBoxes.add(rect);

        }
    }

    private void createRooms(TiledMap map){
        /*sets bounds for each room as a rectangle
        * @param map the tmx tile map of the ship*/
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
        /*Magnifies the bounds of the rectangle to fit with the zoom of screen
        * @param rect the bounds that is being enlarged*/
        rect.x=rect.x*scale;
        rect.y=rect.y*scale;
        rect.width=rect.width*scale;
        rect.height=rect.height*scale;
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
        /*Finds the room that a coordinate is in
        * @param x the x coordinate
        * @param y the y coordinate
        * @returns a string of the room name*/
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
