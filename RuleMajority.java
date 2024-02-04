import java.util.*;

public class RuleMajority implements IRule {
    int[] rule = new int[8];
    ArrayList<ArrayList<Integer>> neighbor;
    int width;

    public RuleMajority(int rule, ArrayList<ArrayList<Integer>> neighbor, int width) {
        for (int i = 0; i < 8; i++) {
            this.rule[i] = (rule >> i) & 1;
        }
        if (neighbor.size() % 2 == 1) {
            this.neighbor = neighbor;
        } else {
            throw new Error("Wrong number of neighbor for Majority Rule");
        }

        this.width = width;
    }

    public void apply(ArrayList<Cell> grid, ArrayList<Integer> cellCoords,
            ArrayList<ArrayList<Integer>> neighborCoords) {
        if (cellCoords.size() == 2) {
            int x = cellCoords.get(0);
            int y = cellCoords.get(1);

            Cell cell = grid.get((y + width) % width + ((x + width) % width) * width);                                                                     
            int countNeighbor = 0;

            for (int i = 0; i < neighbor.size(); i++) {
                int a = neighborCoords.get(i).get(0);
                int b = neighborCoords.get(i).get(1);
                int neighborX = (x + a + width) % width;
                int neighborY = (y + b + width) % width;

                int neighborIndex = neighborY + width * neighborX;
                countNeighbor += grid.get(neighborIndex).getState();
            }

            cell.setNextState(countNeighbor >= Math.floor((double)neighbor.size() / 2) ? 1 : 0);
        } else if (cellCoords.size() == 1) {
            int x = cellCoords.get(0);
            Cell cell = grid.get(x);
            int countNeighbor = 0;

            for (int i = 0; i < neighbor.size(); i++) {
                int neighborIndex = x + neighborCoords.get(i).get(0);

                if (neighborIndex < 0) {
                    neighborIndex = grid.size() - 1;
                } else if (neighborIndex >= grid.size()) {
                    neighborIndex = 0;
                }
                countNeighbor += grid.get(neighborIndex).getState();
            }

            cell.setNextState(countNeighbor >= 2 ? 1 : 0);
        }
    }
}
