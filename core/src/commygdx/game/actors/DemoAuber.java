package commygdx.game.actors;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import commygdx.game.AI.DemoAI;
import commygdx.game.AI.graph.PathGraph;
import commygdx.game.TileWorld;
import commygdx.game.input.PlayerInput;
import commygdx.game.stages.Hud;

import java.util.ArrayList;

public class DemoAuber extends Auber{
    private DemoAI ai;

    public DemoAuber(Vector2 position, SpriteBatch batch, PathGraph graph) {
        super(position, batch);
        ai = new DemoAI(graph);
    }

    @Override
    public boolean teleportCheck(TileWorld tiles) {
        return false;
    }

    @Override
    public void act(float delta) {
        ai.update(delta,getPosition());
        super.act(delta);
    }

    @Override
    protected void handleMovement() {
        //Left movement
        if(ai.left(getPosition())){
            Vector2 position = movementSystem.left();
            setPosition(position.x,position.y);
            if (facingRight==true){
                sprite.flip(true,false);
                facingRight=false;
            }
        }
        //Right movement
        if(ai.right(getPosition())){
            Vector2 position = movementSystem.right();
            setPosition(position.x,position.y);
            if (facingRight==false){
                sprite.flip(true,false);
                facingRight=true;
            }
        }
        //Up movement
        if(ai.up(getPosition())){
            Vector2 position = movementSystem.up();
            setPosition(position.x,position.y);
        }
        //Down movement
        if(ai.down(getPosition())){
            Vector2 position = movementSystem.down();
            setPosition(position.x,position.y);
        }
    }

    @Override
    public void arrest(ArrayList<Infiltrator> infiltrators, Hud hud) {
        /*Arrests the infiltrator if in range and puts it in jail
         * @param infiltrators this list of infiltrators that are being checked
         * @hud the hud overlay*/
        for (Infiltrator infiltrator : infiltrators) {
            if (Math.abs(infiltrator.getX() - this.getX()) < 200 && Math.abs(infiltrator.getY() - this.getY()) < 200) {
                infiltrator.arrest(new Vector2((float)Math.random()*1000+1200,(float)Math.random()*400+5400));
                hud.infiltratorCaught();
            }
        }
    }
}
