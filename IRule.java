import java.util.*;

public  interface IRule {

    public void apply(ArrayList<Cell> grid, ArrayList<Integer> coords, ArrayList<ArrayList<Integer>> neighborCoords);


}
