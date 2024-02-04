import java.util.ArrayList;
import java.util.Arrays;

public class Automaton {

    int dimension;
    ArrayList<ArrayList<Integer>> neighbor;
    int state;
    int width;
    ArrayList<Cell> grid = new ArrayList<Cell>();
    IRule rule;

    public Automaton(ArrayList<Cell> grid, int dimension, ArrayList<ArrayList<Integer>> neighbor, int state,
            IRule rule, int width) {
        this.grid = grid;
        this.width = grid.size();
        this.dimension = dimension;
        this.state = state;
        this.rule = rule;
        this.neighbor = neighbor;
        this.width = width;
    }

    public void start() {
        printGrid();
        System.out.println();

        int count = 0;
        while (count < width * dimension) {
            this.update();
            count++;
        }
    }

    public void update() {

        if (dimension == 1) {
            for (int i = 0; i < grid.size(); i++) {
                ArrayList<Integer> coords = new ArrayList<Integer>();
                coords.add(i);
                rule.apply(grid, coords, neighbor);
            }

            for (Cell cell : grid) {
                cell.update();
            }
            printGrid();
        } else if (dimension == 2) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < width; j++) {
                    ArrayList<Integer> coords = new ArrayList<Integer>();
                    coords.add(i);
                    coords.add(j);
                    rule.apply(grid, coords, neighbor);
                }
            }

            for (Cell cell : grid) {
                cell.update();
            }
            printGrid();
        }

    }

    public void printGrid() {
        if (dimension == 1) {
            for (int i = 0; i < width; i++) {
                System.out.print(grid.get(i).getState() + " ");
            }
            System.out.println();

        } else if (dimension == 2) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < width; j++) {
                    System.out.print(grid.get(j + i * width).getState() + " ");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

}