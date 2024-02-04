import java.util.*;

public class Rule1D implements IRule {

    int[] rule = new int[8];


    public Rule1D(int rule) {
        for (int i = 0; i < 8; i++) {
            this.rule[i] = (rule >> i) & 1;
        }
    }

    public void apply(ArrayList<Cell> grid, ArrayList<Integer> coords, ArrayList<ArrayList<Integer>> neighborCoords) {
        int x = coords.get(0);
        Cell cell = grid.get(x);
        int newState;
        if(x == 0){
            int endGrid = grid.size() - 1;
            newState = (grid.get(endGrid).getState() << 2) | (grid.get(x).getState() << 1) | grid.get(x + 1).getState();            
        } else if(x == grid.size() - 1){
            newState = (grid.get(x - 1).getState() << 2) | (grid.get(x).getState() << 1) | grid.get(0).getState();
        } else {
            newState = (grid.get(x - 1).getState() << 2) | (grid.get(x).getState() << 1) | grid.get(x + 1).getState();
        }
        
        cell.setNextState(rule[newState]);
    }

}
