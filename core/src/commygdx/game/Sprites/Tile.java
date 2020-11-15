package commygdx.game.Sprites;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import commygdx.game.PlayScreen;

public abstract class Tile {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected PlayScreen screen;
    protected MapObject object;

    public Tile(PlayScreen screen, MapObject object){
        this.object = object;
        this.world = screen.getWorld();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        this.map = screen.getMap();
        this.screen = screen;

        BodyDef bdef=new BodyDef();
        FixtureDef fdef= new FixtureDef();
        PolygonShape shape= new PolygonShape();
        bdef.type=BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX()+bounds.getWidth()/2),(bounds.getY()+bounds.getHeight()/2));
        body=world.createBody(bdef);
        shape.setAsBox(bounds.getWidth()/2,bounds.getHeight()/2);
        fdef.shape=shape;
        body.createFixture(fdef);


    }
}
