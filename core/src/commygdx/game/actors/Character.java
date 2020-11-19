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

    public Character(Vector2 position,SpriteBatch batch,float MOV_SPEED){
        Texture texture = new Texture(Gdx.files.internal("Characters/auberSprite.png"));
        sprite = new Sprite(texture);
        sprite.setSize(150,170);
        movementSystem = new MovementSystem(position,MOV_SPEED);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(),sprite.getHeight());
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
