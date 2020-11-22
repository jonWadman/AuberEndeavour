package commygdx.game;

public class ShipSystem {
    private float x;
    private float y;
    private int state;
    private String room;
    public ShipSystem(float x,float y, String room){
        this.x=x;
        this.y=y;
        this.room=room;
        this.state=0;
    }
    public void setState(int state){
        //state 0= operational, state 1=under attack, state 2= not operational
        this.state=state;
    }
    public int getState(){
        return state;
    }
    public String getRoom(){
        return room;
    }
}
