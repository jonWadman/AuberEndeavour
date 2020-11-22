package commygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
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
import commygdx.game.AI.graph.PathGraph;
import commygdx.game.AI.graph.PathNode;
import commygdx.game.actors.Infiltrator;
import commygdx.game.stages.Hud;
import commygdx.game.actors.Auber;
import commygdx.game.stages.ShipStage;

import java.io.BufferedReader;
import java.io.IOException;
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
    public ArrayList<Vector2> Jail;


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

        testCreatePathGraph();
    }

    private void setupShipStage(){
        shipStage = new ShipStage(new StretchViewport(AuberGame.V_WIDTH, AuberGame.V_HEIGHT,gamecam));
        player = new Auber(new Vector2(450*scale,778*scale), auberGame.batch);
        player.sprite.setPosition(450*scale,778*scale);
        enemy=new Infiltrator(new Vector2(4732,7356), auberGame.batch);
        enemy.sprite.setPosition(4732,7356);
        Enemies=new ArrayList<Infiltrator>();//Test version of array
        Jail=new ArrayList<Vector2>(8);

        shipStage.addActor(player);
        shipStage.addActor(enemy);
        Enemies.add(enemy);
        Jail.add(new Vector2(2272,5496));
        Jail.add(new Vector2(2012,5496));
        Jail.add(new Vector2(1752,5496));
        Jail.add(new Vector2(1492,5496));
        Jail.add(new Vector2(2272,5736));
        Jail.add(new Vector2(2012,5736));
        Jail.add(new Vector2(1752,5736));
        Jail.add(new Vector2(1492,5736));


    }

    public void update(float dt){
        shipStage.act(dt);
        player.arrest(Enemies,Jail,hud);
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

    private void testCreatePathGraph(){
        PathGraph graph = createPathGraph("csv/nodes.csv","csv/edges.csv");
        System.out.println("test");
        for(PathNode node:graph.getNodes()){
            System.out.println(node);
            System.out.println(node.getEdges()[0]);
        }
    }

    private PathGraph createPathGraph(String nodesFilepath,String edgesFilepath){
        PathGraph graph = new PathGraph();
        try {
            //Getting nodes from file
            LinkedList<PathNode> nodes = new LinkedList<PathNode>();

            FileHandle nodesFile = Gdx.files.internal(nodesFilepath);
            BufferedReader reader = nodesFile.reader(1000);
            String line = reader.readLine();

            while ((line=reader.readLine())!=null){
                String data[] =line.split(",");
                PathNode node = new PathNode(new Vector2(Float.parseFloat(data[2]),Float.parseFloat(data[3])),Boolean.parseBoolean(data[4]));
                nodes.add(node);
                graph.addNode(node);
            }

            //Getting edges from file
            FileHandle edgesFile = Gdx.files.internal(edgesFilepath);
            reader = edgesFile.reader(100);
            line = reader.readLine();

            while ((line=reader.readLine())!=null){
                String data[] =line.split(",");
                graph.addEdge(nodes.get(Integer.parseInt(data[0])),nodes.get(Integer.parseInt(data[1])));
            }

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
        return graph;
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
