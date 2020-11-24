package commygdx.game.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.viewport.FitViewport;
import commygdx.game.AuberGame;

public class IntroScreen implements Screen {
    private Texture introTexture;
    private OrthographicCamera gamecam;
    private AuberGame game;
    private FitViewport gamePort;

    public IntroScreen(AuberGame game){
        this.game=game;
        introTexture=new Texture("IntroV2.png");
        gamecam=new OrthographicCamera();
        gamePort=new FitViewport(AuberGame.V_WIDTH, AuberGame.V_HEIGHT,gamecam);

        gamecam.setToOrtho(true, AuberGame.V_WIDTH, AuberGame.V_HEIGHT);


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(21/255f,25/255f,38/255f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //draw buttons
        game.batch.begin();
        float zoom=gamecam.viewportWidth/introTexture.getWidth()/2;
        game.batch.draw(introTexture,0,0,gamePort.getScreenWidth(),gamePort.getScreenHeight());
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.gameState=0;
        }
    }

    @Override
    public void resize(int width, int height) {
        gamePort.update(width,height);


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

    }
}
