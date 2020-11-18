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

    AuberGame game;
    Texture brigTexture;
    Texture commandTexture;
    Texture crewTexture;
    Texture engineTexture;
    Texture infirmaryTexture;
    Texture laboratoryTexture;
    private OrthographicCamera gamecam;

    public TeleportMenu(AuberGame game){

        this.game=game;
        gamecam=new OrthographicCamera();
        gamecam.setToOrtho(true, 2560, 1440);
        brigTexture=new Texture("Signs/brig.png");
        commandTexture=new Texture("Signs/command.png");
        crewTexture=new Texture("Signs/crew.png");
        engineTexture=new Texture("Signs/engine.png");
        infirmaryTexture=new Texture("Signs/infirmary.png");
        laboratoryTexture=new Texture("Signs/laboratory.png");
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        int mag=10;
        Gdx.gl.glClearColor(0,0,0,0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();
        game.batch.draw(brigTexture,1000,950,brigTexture.getWidth()*mag,brigTexture.getHeight()*mag);
        game.batch.draw(commandTexture,1000,800,brigTexture.getWidth()*mag,brigTexture.getHeight()*mag);
        game.batch.draw(crewTexture,1000,650,brigTexture.getWidth()*mag,brigTexture.getHeight()*mag);
        game.batch.draw(engineTexture,1000,500,brigTexture.getWidth()*mag,brigTexture.getHeight()*mag);
        game.batch.draw(infirmaryTexture,1000,350,brigTexture.getWidth()*mag,brigTexture.getHeight()*mag);
        game.batch.draw(laboratoryTexture,1000,200,brigTexture.getWidth()*mag,brigTexture.getHeight()*mag);
        game.batch.end();
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)){
            Rectangle brigBounds=new Rectangle(1000,950,brigTexture.getWidth()*mag,brigTexture.getHeight()*mag);
            Vector3 c= new Vector3(Gdx.input.getX(),Gdx.input.getY(),0);
            gamecam.unproject(c);
            c.y=1400-c.y;
            if (c.x>1000 & c.x<1000+brigTexture.getWidth()*mag){
                if (c.y>950-brigTexture.getHeight()*mag/2 & c.y<950+brigTexture.getHeight()*mag/2){
                    System.out.println("brig");
                    game.onTeleport="brig";
                }
                if (c.y>800-brigTexture.getHeight()*mag/2 & c.y<800+brigTexture.getHeight()*mag/2){
                    System.out.println("command");
                    game.onTeleport="command";
                }
                if (c.y>650-brigTexture.getHeight()*mag/2 & c.y<650+brigTexture.getHeight()*mag/2){
                    System.out.println("crew");
                    game.onTeleport="crew";
                }
                if (c.y>500-brigTexture.getHeight()*mag/2 & c.y<500+brigTexture.getHeight()*mag/2){
                    System.out.println("engine");
                    game.onTeleport="engine";

                }
                if (c.y>350-brigTexture.getHeight()*mag/2 & c.y<350+brigTexture.getHeight()*mag/2){
                    System.out.println("infirmary");
                    game.onTeleport="infirmary";
                }
                if (c.y>200-brigTexture.getHeight()*mag/2 & c.y<200+brigTexture.getHeight()*mag/2){
                    System.out.println("laboratory");
                    game.onTeleport="laboratory";
                }
            }





        }
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
            Gdx.app.exit();
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
