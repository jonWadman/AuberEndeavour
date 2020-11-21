package commygdx.game.AI.test;

import com.badlogic.gdx.math.Vector2;
import commygdx.game.AI.MovementAI;

public class TestAI {
    public static void main(String args[]){
        MovementAI mov = new MovementAI();
        System.out.println(mov.hasDestination());
        mov.setDestination(new Vector2(100,100));
        Vector2 pos[] = {
                new Vector2(100,50),
                new Vector2(1010,-25),
                new Vector2(96,99),
                new Vector2(-870,100),
        };
        for(int i=0;i<pos.length;i++){
            System.out.println(i);
            System.out.println(pos[i]);
            System.out.println(mov.left(pos[i]));
            System.out.println(mov.right(pos[i]));
            System.out.println(mov.up(pos[i]));
            System.out.println(mov.down(pos[i]));
            System.out.println(mov.atDestination(pos[i]));
        }
    }
}
