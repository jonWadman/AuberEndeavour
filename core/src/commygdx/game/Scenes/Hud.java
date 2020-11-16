package commygdx.game.Scenes;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import commygdx.game.AuberGame;

import java.awt.*;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    private int systemsUp;
    private Label systemLabel;
    private  Label systemTextLabel;

    private int infiltratorsRemaining;
    private Label infiltratorLabel;
    private Label infiltratorTextLabel;

    private BitmapFont font;
//used for buttons,text, etc
    public Hud(SpriteBatch sb){
        viewport=new FitViewport(AuberGame.V_WIDTH,AuberGame.V_HEIGHT,new OrthographicCamera());
        stage= new Stage(viewport,sb);


        Table table = new Table();
        table.top();
        table.setFillParent(true);

        systemsUp=15;
        infiltratorsRemaining=8;

        System.out.format("%d / 15 systems",systemsUp);
        font=new BitmapFont();
        font.getData().setScale(2);

        systemLabel = new Label(String.format("%d / 15",systemsUp), new Label.LabelStyle(font, Color.WHITE));
        systemTextLabel=new Label("systems operational", new Label.LabelStyle(font, Color.WHITE));


        infiltratorLabel = new Label(String.format("%d / 8 ",infiltratorsRemaining), new Label.LabelStyle(font, Color.WHITE));
        infiltratorTextLabel=new Label("infiltrators caught", new Label.LabelStyle(font, Color.WHITE));
        table.setPosition(250,0);

        table.add(systemLabel).padTop(50);
        table.row();
        table.add(systemTextLabel).padTop(10);
        table.row();
        table.add(infiltratorLabel).padTop(50);
        table.row();
        table.add(infiltratorTextLabel).padTop(10);

        stage.addActor(table);




    }

    public void update(int systemsUp, int infiltratorsRemaining){
        systemLabel.setText(String.format("%d / 15",systemsUp));
        infiltratorLabel.setText(String.format("%d / 8 ",infiltratorsRemaining));

    }
}
