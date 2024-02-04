
public class Cell{
    int state;
    int nextState;

    public Cell(int state){
        this.state = state;
        this.nextState = state;
    }

    public int getState(){
        return state;
    }

    public void setState(int i){
        this.state = i;
    }

    public void setNextState(int newState){
        this.nextState = newState;
    }

    public void update(){
        state = nextState;
    }

}