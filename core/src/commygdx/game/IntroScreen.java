package commygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;

public class IntroScreen implements Screen {
    private Texture introTexture;
    private OrthographicCamera gamecam;
    private AuberGame game;

    public IntroScreen(AuberGame game){
        this.game=game;
        introTexture=new Texture("Intro.png");
        gamecam=new OrthographicCamera();

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
        game.batch.draw(introTexture,100,100,introTexture.getWidth()*2.5f,introTexture.getHeight()*2.5f);
        game.batch.end();

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            game.start=true;
        }
    }

    @Override
    public void resize(int width, int height) {

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
