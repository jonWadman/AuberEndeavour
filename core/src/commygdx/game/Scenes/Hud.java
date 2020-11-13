package commygdx.game.Scenes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import commygdx.game.Game;

public class Hud {
    public Stage stage;
    private Viewport viewport;

    public Hud(SpriteBatch sb){
        viewport=new FitViewport(Game.V_WIDTH,Game.V_HEIGHT,new OrthographicCamera());
        stage= new Stage(viewport,sb);
    }
}
