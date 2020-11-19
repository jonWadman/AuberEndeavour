package commygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
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

    public TeleportMenu(AuberGame game){

        this.game=game;
        gamecam=new OrthographicCamera();
        gamecam.setToOrtho(true, 2560, 1440);
        //get button textures
        brigTexture=new Texture("Signs/brig.png");
        commandTexture=new Texture("Signs/command.png");
        crewTexture=new Texture("Signs/crew.png");
        engineTexture=new Texture("Signs/engine.png");
        infirmaryTexture=new Texture("Signs/infirmary.png");
        laboratoryTexture=new Texture("Signs/laboratory.png");
        teleportTexture= new Texture("Signs/teleport.png");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //draw menu
        int mag=10;
        Gdx.gl.glClearColor(21/255f,25/255f,38/255f,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        int textureWidth=brigTexture.getWidth()*mag;
        int textureHeight=brigTexture.getHeight()*mag;
        game.batch.begin();
        game.batch.draw(teleportTexture,1000,1100,textureWidth,textureHeight);
        game.batch.draw(brigTexture,1000,950,textureWidth,textureHeight);
        game.batch.draw(commandTexture,1000,800,textureWidth,textureHeight);
        game.batch.draw(crewTexture,1000,650,textureWidth,textureHeight);
        game.batch.draw(engineTexture,1000,500,textureWidth,textureHeight);
        game.batch.draw(infirmaryTexture,1000,350,textureWidth,textureHeight);
        game.batch.draw(laboratoryTexture,1000,200,textureWidth,textureHeight);
        game.batch.end();
        //get button pressed
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            Rectangle brigBounds=new Rectangle(1000,950,brigTexture.getWidth()*mag,brigTexture.getHeight()*mag);
            Vector3 clickPos= new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            gamecam.unproject(clickPos);
            clickPos.y=1400-clickPos.y;

            if (clickPos.x>1000 & clickPos.x<1000+textureWidth){
                if (clickPos.y>950-textureHeight/2 & clickPos.y<950+textureHeight/2){
                    game.onTeleport="brig";
                }
                if (clickPos.y>800-textureHeight/2 & clickPos.y<800+textureHeight/2){
                    game.onTeleport="command";
                }
                if (clickPos.y>650-textureHeight/2 & clickPos.y<650+textureHeight/2){
                    game.onTeleport="crew";
                }
                if (clickPos.y>500-textureHeight/2 & clickPos.y<500+textureHeight/2){
                    game.onTeleport="engine";
                }
                if (clickPos.y>350-textureHeight/2 & clickPos.y<350+textureHeight/2){
                    game.onTeleport="infirmary";
                }
                if (clickPos.y>200-textureHeight/2 & clickPos.y<200+textureHeight/2){
                    game.onTeleport="laboratory";
                }
            }



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
