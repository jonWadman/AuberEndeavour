package commygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.*;
import commygdx.game.actors.Infiltrator;
import commygdx.game.stages.Hud;
import commygdx.game.actors.Auber;
import commygdx.game.stages.ShipStage;

import java.util.*;

public class PlayScreen implements Screen {
    private AuberGame auberGame;
    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    public ArrayList<Infiltrator> Enemies;


    //Scene2D
    private Auber player;
    private Infiltrator enemy;
    private ShipStage shipStage;

    private TileWorld tiles;
    public static int scale=12;

    private ShapeRenderer sh;




    public PlayScreen(AuberGame auberGame){
        this.auberGame = auberGame;
        gamecam=new OrthographicCamera();
        gamePort=new FitViewport(AuberGame.V_WIDTH, AuberGame.V_HEIGHT,gamecam);
        /*Possible fullscreen
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());*/
        hud=new Hud(auberGame.batch);
        //load map
        mapLoader=new TmxMapLoader();
        map=mapLoader.load("mapV2.tmx");
        renderer=new OrthogonalTiledMapRenderer(map,scale);


        setupShipStage();
        tiles = new TileWorld(this);


    }

    private void setupShipStage(){
        shipStage = new ShipStage(new StretchViewport(AuberGame.V_WIDTH, AuberGame.V_HEIGHT,gamecam));
        player = new Auber(new Vector2(450*scale,778*scale), auberGame.batch);
        player.sprite.setPosition(450*scale,778*scale);
        enemy=new Infiltrator(new Vector2(4732,7356), auberGame.batch);
        enemy.sprite.setPosition(4732,7356);
        Enemies=new ArrayList<Infiltrator>();//Test version of array

        shipStage.addActor(player);
        shipStage.addActor(enemy);
        Enemies.add(enemy);



    }

    public void update(float dt){
        shipStage.act(dt);
        player.arrest(Enemies,new Vector2(450*scale,778*scale));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);
        Vector3 pos=new Vector3((player.getX())+player.getWidth()/2,(player.getY())+player.getHeight()/2,0);
        shipStage.getViewport().getCamera().position.set(pos);
        gamecam.position.set(pos);
        gamecam.update();
        gamecam.update();
        renderer.setView(gamecam);
        //bg colour
        //Gdx.gl.glClearColor(21/255f,25/255f,38/255f,1);
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();

        shipStage.draw();

        auberGame.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.updateAttacks(tiles.getSystems());
        hud.stage.draw();

        boolean t=player.teleportCheck(tiles);
        if (player.teleportCheck(tiles) && auberGame.onTeleport=="false"){
            auberGame.setScreen(new TeleportMenu(auberGame));
        }

        if (auberGame.onTeleport!="true" && auberGame.onTeleport!="false"){
            teleportAuber();
            auberGame.onTeleport="false";
        }
        player.checkCollision(tiles.getCollisionBoxes());




    }



    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    public void teleportAuber(){
        float x=tiles.getTeleporters().get(auberGame.onTeleport).x+100;
        float y=tiles.getTeleporters().get(auberGame.onTeleport).y;
        player.setPosition(x,y);
        player.movementSystem.updatePos(new Vector2(x,y));
    }



    public TiledMap getMap(){
        return map;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

        map.dispose();
        renderer.dispose();
        shipStage.dispose();


    }
}
