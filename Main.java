import java.util.ArrayList;
import java.util.Arrays;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        
        // // Test règle de majorité 1D
        // int width = 8;
        // ArrayList<Cell> grid = new ArrayList<>();
        // for (int i = 0; i < width; i++) {
        //     grid.add(new Cell(0));
        // }
        // grid.get(1).setState(1);
        // grid.get(3).setState(1);

        // grid.get(5).setState(1);
        // grid.get(7).setState(1);

        int width = 5;
        ArrayList<Cell> grid = new ArrayList<>();
        for (int i = 0; i < width * width; i++) {
            grid.add(new Cell(0));
        }
        grid.get(2 + 1 * width).setState(1);
        grid.get(1 + 2 * width).setState(1);
        grid.get(3 + 2 * width).setState(1);
        grid.get(2 + 3 * width).setState(1);

        // Rule1D rule = new Rule1D(240);

        // Coordonnées des voisins 

        // ArrayList<ArrayList<Integer>> neighborCoords =  new ArrayList<ArrayList<Integer>>();
        // ArrayList<Integer> neighborCoord = new ArrayList<>(Arrays.asList(0, 0));
        // neighborCoords.add(neighborCoord);
        // neighborCoord = new ArrayList<>(Arrays.asList(1, 0));
        // neighborCoords.add(neighborCoord);
        // neighborCoord = new ArrayList<>(Arrays.asList(-1, 1));
        // neighborCoords.add(neighborCoord);

        ArrayList<ArrayList<Integer>> neighborCoords =  new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> neighborCoord = new ArrayList<>(Arrays.asList(0, 0));
        neighborCoords.add(neighborCoord);
        neighborCoord = new ArrayList<>(Arrays.asList(-1, -1));
        neighborCoords.add(neighborCoord);
        neighborCoord = new ArrayList<>(Arrays.asList(-1, 0));
        neighborCoords.add(neighborCoord);
        neighborCoord = new ArrayList<>(Arrays.asList(-1, 1));
        neighborCoords.add(neighborCoord);
        neighborCoord = new ArrayList<>(Arrays.asList(0, -1));
        neighborCoords.add(neighborCoord);
        neighborCoord = new ArrayList<>(Arrays.asList(0, 1));
        neighborCoords.add(neighborCoord);
        neighborCoord = new ArrayList<>(Arrays.asList(1, -1));
        neighborCoords.add(neighborCoord);
        neighborCoord = new ArrayList<>(Arrays.asList(1, 0));
        neighborCoords.add(neighborCoord);
        neighborCoord = new ArrayList<>(Arrays.asList(1, 1));
        neighborCoords.add(neighborCoord);


        // Rule et lancement 
        RuleMajority rule = new RuleMajority(0, neighborCoords, width);
        Automaton automaton = new Automaton(grid, 2, neighborCoords, 2, rule, width);

        automaton.start();
    }
}
