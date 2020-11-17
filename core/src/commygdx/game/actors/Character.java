package commygdx.game.actors;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import commygdx.game.syst.MovementSystem;

public abstract class Character extends Actor {

    //Constants
    protected final float MOV_SPEED = 10f;

    Sprite sprite;
    MovementSystem movementSystem;
    Batch batch;

    @Override
    public void act(float delta) {
        handleMovement();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    public Character(Vector2 position){
        setupSprite();
        movementSystem = new MovementSystem(position,MOV_SPEED);
    }

    protected abstract void handleMovement();

    private void setupSprite(){
        batch = new SpriteBatch();
        Texture texture = new Texture(Gdx.files.internal("badlogic.jpg"));
        sprite = new Sprite(texture);
    }
}
