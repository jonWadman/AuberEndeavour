package commygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Path;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.*;
import commygdx.game.AI.graph.PathGraph;
import commygdx.game.AI.graph.PathNode;

import commygdx.game.AuberGame;
import commygdx.game.ShipSystem;
import commygdx.game.TileWorld;
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
    public ArrayList<Infiltrator> enemies;
    public ArrayList<Vector2> Jail;
    public PathGraph graph;


    //Scene2D
    private Auber player;
    private Infiltrator enemy;
    private ShipStage shipStage;

    private boolean hallucinate;
    private Texture hallucinateTexture;


    private TileWorld tiles;

    private ShapeRenderer sh;
    private int scale;




    public PlayScreen(AuberGame auberGame){
        this.auberGame = auberGame;
        this.scale=AuberGame.ZOOM;
        gamecam=new OrthographicCamera();
        gamePort=new FitViewport(AuberGame.V_WIDTH, AuberGame.V_HEIGHT,gamecam);
        /*Possible fullscreen
        Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());*/

        //load map
        mapLoader=new TmxMapLoader();
        map=mapLoader.load("mapV2.tmx");
        renderer=new OrthogonalTiledMapRenderer(map,scale);

        graph = createPathGraph("csv/nodes.csv","csv/edges.csv");
        setupShipStage();


        tiles = new TileWorld(this);
        hallucinateTexture=new Texture("hallucinateV2.png");
        hallucinate=false;


        hud=new Hud(auberGame.batch,enemies,tiles.getSystems());
    }

    private void setupShipStage(){
        /*Creates stage and adds characters (auber and infiltrators) to it*/
        shipStage = new ShipStage(new StretchViewport(AuberGame.V_WIDTH, AuberGame.V_HEIGHT,gamecam));
        player = new Auber(new Vector2(450*scale,778*scale), auberGame.batch);
        player.sprite.setPosition(450*scale,778*scale);


        /*enemies=new ArrayList<Infiltrator>(Arrays.asList(
                new Infiltrator(new Vector2(4500,7356), auberGame.batch,1),
                new Infiltrator(new Vector2(4732,7356), auberGame.batch,3),
                new Infiltrator(new Vector2(5000,7356), auberGame.batch,1),
                new Infiltrator(new Vector2(4732,9000), auberGame.batch,2),
                new Infiltrator(new Vector2(4732,7500), auberGame.batch,4),
                new Infiltrator(new Vector2(4732,7800), auberGame.batch,3),
                new Infiltrator(new Vector2(4200,7800), auberGame.batch,1),
                new Infiltrator(new Vector2(5400,7800), auberGame.batch,1)
        ));//Test version of array*/

        enemies=new ArrayList<Infiltrator>(Arrays.asList(
                new Infiltrator(new Vector2(4500,7356), auberGame.batch,1,graph),
                new Infiltrator(new Vector2(4732,7356), auberGame.batch,3,graph),
                new Infiltrator(new Vector2(5000,7356), auberGame.batch,1,graph),
                new Infiltrator(new Vector2(4732,9000), auberGame.batch,2,graph),
                new Infiltrator(new Vector2(4732,7500), auberGame.batch,1,graph),
                new Infiltrator(new Vector2(4732,7800), auberGame.batch,3,graph),
                new Infiltrator(new Vector2(4200,7800), auberGame.batch,1,graph),
                new Infiltrator(new Vector2(5400,7800), auberGame.batch,1,graph)
        ));//Test version of array



        shipStage.addActor(player);
        //shipStage.addActor(enemy);
        for (Infiltrator enemy:enemies) {
            shipStage.addActor(enemy);
        }






    }

    public void update(float dt){
        shipStage.act(dt);
        player.arrest(enemies,hud);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        /*Draws the game to screen and updates game
        * @param delta time difference from last call*/
        checkGameState();
        update(delta);
        updateInfiltrators(delta);

        Vector3 pos=new Vector3((player.getX())+player.getWidth()/2,(player.getY())+player.getHeight()/2,0);
        shipStage.getViewport().getCamera().position.set(pos);
        gamecam.position.set(pos);
        gamecam.update();
        renderer.setView(gamecam);
        //bg colour
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();
        shipStage.draw();
        auberGame.batch.setProjectionMatrix(hud.stage.getCamera().combined);

        hud.updateAttacks(tiles.getSystems());

        //switch to teleport menu
        if (player.teleportCheck(tiles) && auberGame.onTeleport=="false"){
            auberGame.setScreen(new TeleportMenu(auberGame));
        }
        //teleport auber
        if (auberGame.onTeleport!="true" && auberGame.onTeleport!="false"){
            teleportAuber();
            auberGame.onTeleport="false";
        }
        player.checkCollision(tiles.getCollisionBoxes());

        if (hallucinate){ drawHallucinate();}

        hud.stage.draw();

        printPosition();
    }

    private void drawHallucinate(){
        /*Draws hallucination texture overlay on screen and removes it when auber in infirmary*/
        auberGame.batch.begin();
        auberGame.batch.draw(hallucinateTexture,0,0);
        auberGame.batch.end();
        if (player.sprite.getBoundingRectangle().overlaps(tiles.getInfirmary())){
            hud.showHallucinateLabel(false);
            hallucinate=false;

        }

    }





    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);
    }

    public void teleportAuber(){
        /*Update aubers position to teleportation destination*/
        float x=tiles.getTeleporters().get(auberGame.onTeleport).x+100;
        float y=tiles.getTeleporters().get(auberGame.onTeleport).y;
        player.setPosition(x,y);
        player.movementSystem.updatePos(new Vector2(x,y));
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

    private void checkGameState(){
        /*Shows GameOverScreen if player won or lost*/
        if (hud.getInfiltratorsRemaining()==0){
            auberGame.setGameState(2);
        }
        if (hud.getSystemsUp()==0){
            auberGame.setGameState(3);
        }
    }

    public void updateInfiltrators(float dt){

        for (Infiltrator enemy:enemies){
            enemy.updateTimers(dt*100);

            if (enemy.getPowerCooldown()>1000 && inRange(enemy)){
                enemy.usePower(this);
            }
            if (enemy.getPowerDuration()>500){
                enemy.stopPower(this);
            }
        }
        checkInfiltratorsSystems();
    }

    public boolean inRange(Infiltrator enemy){
        if (enemy.getX()<player.getX()+1000 && enemy.getX()>player.getX()-1000 &&
        enemy.getY()<player.getY()+1000 && enemy.getY()>player.getY()-1000){
            return true ;
        }
        return false;

    }

    public void setHallucinate(boolean hallucinate){
        this.hallucinate=hallucinate;
        hud.showHallucinateLabel(hallucinate);
    }

    public TiledMap getMap(){
        return map;
    }

    private void checkInfiltratorsSystems(){
        final float range = 20;
        for(Infiltrator infiltrator:enemies){
            if(infiltrator.isAvailable()){
                for(ShipSystem system:tiles.getSystems()){
                    if(system.getState() ==0){
                        if(new Vector2(infiltrator.getX(),infiltrator.getY()).dst(system.getPosition())<range){
                            infiltrator.startDestruction(system);
                            system.startAttack();
                        }
                    }
                }
            }
        }
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

    private void printPosition(){
        if(Gdx.input.isKeyJustPressed(Input.Keys.P)){
            System.out.println(new Vector3(player.getX(),player.getY(),0));
        }
    }
}
