package commygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import commygdx.game.syst.MovementSystem;

import javax.swing.text.Position;
import java.util.List;

public abstract class Character extends Actor {

    //Constants
    protected final float MOV_SPEED = 10f;

    public Sprite sprite;
    public MovementSystem movementSystem;
    Batch batch;

    @Override
    public void act(float delta) {
        handleMovement();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }



    protected abstract void handleMovement();

    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(),getY());
    }

    public boolean checkCollision(List<Rectangle> walls){
        for (Rectangle wall: walls){
            //System.out.println(sprite.getBoundingRectangle());
            //System.out.println(wall);
            if(sprite.getBoundingRectangle().overlaps(wall)){
                System.out.println("collide");
                movementSystem.getDirection();
                movementSystem.setCollided(true);
                return true;
            }
        }
        return false;
    }

}
