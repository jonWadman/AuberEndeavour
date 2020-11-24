package commygdx.game.stages;

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
import commygdx.game.ShipSystem;
import commygdx.game.actors.Infiltrator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Hud {
    public Stage stage;
    private Viewport viewport;
    private int systemsUp;
    private Label systemLabel;
    private  Label systemTextLabel;

    private int infiltratorsRemaining;
    private Label infiltratorLabel;
    private Label infiltratorTextLabel;

    private Label attackLabel;
    private Label attackTextLabel;

    private Label hallucinateLabel;

    private BitmapFont font;

    private ArrayList<Infiltrator> enemies;
    private ArrayList<ShipSystem> systems;

//used for buttons,text, etc
    public Hud(SpriteBatch sb,ArrayList<Infiltrator> enemies,ArrayList<ShipSystem> systems){
        viewport=new FitViewport(AuberGame.V_WIDTH,AuberGame.V_HEIGHT,new OrthographicCamera());
        stage= new Stage(viewport,sb);

        this.enemies=enemies;
        this.systems=systems;

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        systemsUp=systems.size();
        infiltratorsRemaining=enemies.size();

        System.out.format("%d / 15 systems",systemsUp);
        font=new BitmapFont();
        font.getData().setScale(3f);


        systemLabel = new Label(String.format("%d / 15",systemsUp), new Label.LabelStyle(font, Color.WHITE));
        systemTextLabel=new Label("systems operational", new Label.LabelStyle(font, Color.WHITE));


        infiltratorLabel = new Label(String.format("%d / 8 ",infiltratorsRemaining), new Label.LabelStyle(font, Color.WHITE));
        infiltratorTextLabel=new Label("infiltrators remaining", new Label.LabelStyle(font, Color.WHITE));

        attackLabel=new Label("None", new Label.LabelStyle(font, Color.WHITE));
        attackTextLabel=new Label("Current attacks", new Label.LabelStyle(font, Color.WHITE));

        hallucinateLabel=new Label("", new Label.LabelStyle(font, Color.WHITE));


        table.setPosition(viewport.getScreenWidth()/2+150, 0);

        table.add(systemLabel).expandX().padTop(50);
        table.row();
        table.add(systemTextLabel).expandX().padTop(10);
        table.row();
        table.add(infiltratorLabel).expandX().padTop(50);
        table.row();
        table.add(infiltratorTextLabel).expandX().padTop(10);
        table.row();
        table.add(attackTextLabel).expandX().padTop(50);
        table.row();
        table.add(attackLabel).expandX().padTop(10);
        table.row();
        table.add(hallucinateLabel).expandX().padTop(50);


        stage.addActor(table);


    }


    public void infiltratorCaught(){
        infiltratorsRemaining-=1;
        infiltratorLabel.setText(String.format("%d / 8",infiltratorsRemaining));

    }


    public void showHallucinateLabel(boolean show){
        if (show){
            hallucinateLabel.setText("You are hallucinating \n Go to infirmary to heal ");
        }else{
            hallucinateLabel.setText("");
        }
    }

    public void updateAttacks(List<ShipSystem> systems){
        /*Update hud to reflect attacks*/
        String room=new String();
        systemsUp=0;
        for (ShipSystem system:systems){
            if (system.getState()==1){
                if (!room.contains(system.getRoom())){
                    room+=system.getRoom();
                    room+="\n";
                }
            }
            if (system.getState()!=2){systemsUp+=1;}
        }
        if( room.length()<1){
            room="None";
        }
        attackLabel.setText(room);
        systemLabel.setText(String.format("%d / 15",systemsUp));
    }
    public int getInfiltratorsRemaining(){return infiltratorsRemaining;}
    public int getSystemsUp(){return systemsUp;}
}
