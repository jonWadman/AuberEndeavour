package commygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.viewport.FitViewport;
import commygdx.game.actors.Auber;

public class TeleportMenu implements Screen {

    private AuberGame game;
    private Texture brigTexture;
    private Texture commandTexture;
    private Texture crewTexture;
    private Texture engineTexture;
    private Texture infirmaryTexture;
    private Texture laboratoryTexture;
    private Texture teleportTexture;
    private OrthographicCamera gamecam;

    private Rectangle brigRect;
    private  Rectangle commandRect;
    private  Rectangle crewRect;
    private  Rectangle engineRect;
    private  Rectangle infirmaryRect;
    private  Rectangle  laboratoryRext;
    private final int zoom=10;

    private int tWidth;
    private int tHeight;

    public TeleportMenu(AuberGame game){

        this.game=game;
        gamecam=new OrthographicCamera();

        gamecam.setToOrtho(true, AuberGame.V_WIDTH, AuberGame.V_HEIGHT);
        //get button textures
        brigTexture=new Texture("Signs/brig.png");
        commandTexture=new Texture("Signs/command.png");
        crewTexture=new Texture("Signs/crew.png");
        engineTexture=new Texture("Signs/engine.png");
        infirmaryTexture=new Texture("Signs/infirmary.png");
        laboratoryTexture=new Texture("Signs/laboratory.png");
        teleportTexture= new Texture("Signs/teleport.png");

        //button rectange bounds
        tWidth=brigTexture.getWidth()*zoom;
        tHeight=brigTexture.getHeight()*zoom;

        brigRect=new Rectangle(1000,950,tWidth,tHeight);
        commandRect=new Rectangle(1000,800,tWidth,tHeight);
        crewRect=new Rectangle(1000,650,tWidth,tHeight);
        engineRect=new Rectangle(1000,500,tWidth,tHeight);
        infirmaryRect=new Rectangle(1000,350,tWidth,tHeight);
        laboratoryRext=new Rectangle(1000,200,tWidth,tHeight);


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //draw menu
        Gdx.gl.glClearColor(21/255f,25/255f,38/255f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        int textureWidth=brigTexture.getWidth()*zoom;
        int textureHeight=brigTexture.getHeight()*zoom;

        //draw buttons
        game.batch.begin();
        game.batch.draw(teleportTexture,1000,1100,textureWidth,textureHeight);
        game.batch.draw(brigTexture,1000,950,textureWidth,textureHeight);
        game.batch.draw(commandTexture,1000,800,textureWidth,textureHeight);
        game.batch.draw(crewTexture,1000,650,textureWidth,textureHeight);
        game.batch.draw(engineTexture,1000,500,textureWidth,textureHeight);
        game.batch.draw(infirmaryTexture,1000,350,textureWidth,textureHeight);
        game.batch.draw(laboratoryTexture,1000,200,textureWidth,textureHeight);
        game.batch.end();


        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){onClick(Gdx.input.getX(),Gdx.input.getY());};



    }

    private void onClick(int x,int y){
            //convert click screen cords to world
            Vector3 clickPos= new Vector3(x,y,0);
            gamecam.unproject(clickPos);
            clickPos.y=AuberGame.V_HEIGHT-clickPos.y;

            //check which button pressed
            if (brigRect.contains(clickPos.x,clickPos.y)){game.onTeleport="brig";}
            if (commandRect.contains(clickPos.x,clickPos.y)){game.onTeleport="command";}
            if (crewRect.contains(clickPos.x,clickPos.y)){game.onTeleport="crew";}
            if (engineRect.contains(clickPos.x,clickPos.y)){game.onTeleport="engine";}
            if (infirmaryRect.contains(clickPos.x,clickPos.y)){game.onTeleport="infirmary";}
            if (laboratoryRext.contains(clickPos.x,clickPos.y)){game.onTeleport="laboratory";}


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
