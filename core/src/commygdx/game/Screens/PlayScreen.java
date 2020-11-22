package commygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.*;
import commygdx.game.AuberGame;
import commygdx.game.TileWorld;
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
    public ArrayList<Infiltrator> enemies;
    public ArrayList<Vector2> Jail;



    //Scene2D
    private Auber player;
    private Infiltrator enemy;
    private ShipStage shipStage;

    private boolean hallucinate;
    private Texture hallucinateTexture;


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
        hallucinateTexture=new Texture("hallucinateV2.png");
        hallucinate=false;


    }

    private void setupShipStage(){
        shipStage = new ShipStage(new StretchViewport(AuberGame.V_WIDTH, AuberGame.V_HEIGHT,gamecam));
        player = new Auber(new Vector2(450*scale,778*scale), auberGame.batch);
        player.sprite.setPosition(450*scale,778*scale);

        enemies=new ArrayList<Infiltrator>(Arrays.asList(
                new Infiltrator(new Vector2(4500,7356), auberGame.batch,1),
                new Infiltrator(new Vector2(4732,7356), auberGame.batch,3),
                new Infiltrator(new Vector2(5000,7356), auberGame.batch,1),
                new Infiltrator(new Vector2(4732,9000), auberGame.batch,2),
                new Infiltrator(new Vector2(4732,7500), auberGame.batch,4),
                new Infiltrator(new Vector2(4732,7800), auberGame.batch,3),
                new Infiltrator(new Vector2(4200,7800), auberGame.batch,1),
                new Infiltrator(new Vector2(5400,7800), auberGame.batch,1)
        ));//Test version of array

        Jail=new ArrayList<Vector2>(Arrays.asList(
                new Vector2(2272,5496),
                new Vector2(2012,5496),
                new Vector2(1752,5496),
                new Vector2(1492,5496),
                new Vector2(2272,5736),
                new Vector2(2012,5736),
                new Vector2(1752,5736),
                new Vector2(1492,5736)));

        shipStage.addActor(player);
        //shipStage.addActor(enemy);
        for (Infiltrator enemy:enemies) {
            shipStage.addActor(enemy);
        }






    }

    public void update(float dt){
        shipStage.act(dt);
        player.arrest(enemies,Jail,hud);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
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
        hud.stage.draw();

        boolean t=player.teleportCheck(tiles);
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


    }

    private void drawHallucinate(){
        auberGame.batch.begin();
        auberGame.batch.draw(hallucinateTexture,0,0);
        auberGame.batch.end();
        if (player.sprite.getBoundingRectangle().overlaps(tiles.getInfirmary())){
            hallucinate=false;
        }

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

    private void checkGameState(){
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
