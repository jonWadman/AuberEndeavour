package commygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.*;
import commygdx.game.Scenes.Hud;
import commygdx.game.actors.Auber;
import commygdx.game.stages.ShipStage;

public class PlayScreen implements Screen {
    private AuberGame auberGame;
    private Hud hud;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private TmxMapLoader mapLoader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;


    //Scene2D
    private Auber player;
    private ShipStage shipStage;

    private Box2dWorld tiles;
    public static int scale=12;


    public PlayScreen(AuberGame auberGame){
        this.auberGame = auberGame;
        gamecam=new OrthographicCamera();
        gamecam.setToOrtho(false,AuberGame.V_WIDTH,AuberGame.V_HEIGHT);
        gamePort=new FitViewport(AuberGame.V_WIDTH, AuberGame.V_HEIGHT,gamecam);
        Gdx.graphics.setWindowedMode(AuberGame.V_WIDTH,AuberGame.V_HEIGHT);
        hud=new Hud(auberGame.batch);
        //load map
        mapLoader=new TmxMapLoader();
        map=mapLoader.load("map1.tmx");
        renderer=new OrthogonalTiledMapRenderer(map,scale);
        //start pos
        gamecam.position.set(400*scale,820*scale,0);


        setupShipStage();
        tiles = new Box2dWorld(this);


    }

    private void setupShipStage(){
        shipStage = new ShipStage();
        player = new Auber(new Vector2(30,30));
        shipStage.addActor(player);
    }

    public void update(float dt){
        shipStage.act(dt);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        update(delta);


        gamecam.update();
        renderer.setView(gamecam);
        //bg colour
        //Gdx.gl.glClearColor(21/255f,25/255f,38/255f,1);
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        renderer.render();


        shipStage.draw();

        auberGame.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE))
            Gdx.app.exit();
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);

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
