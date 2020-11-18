package commygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import commygdx.game.syst.MovementSystem;

import javax.swing.text.Position;

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

    public Character(Vector2 position,SpriteBatch batch){
        Texture texture = new Texture(Gdx.files.internal("Characters/auber.png"));
        sprite = new Sprite(texture);
        sprite.setSize(250,250);
        movementSystem = new MovementSystem(position,MOV_SPEED);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(),sprite.getHeight());
    }

    protected abstract void handleMovement();

    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(),getY());
    }

}
