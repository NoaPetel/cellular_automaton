public class GameOfLife {
    private int[][] grid;
    private int dimension;

    /**
     * Constructeur de la classe GameOfLife
     * @param dimension
     * @return void
     */
    public GameOfLife(int dimension) {
        this.dimension = dimension;
        this.grid = new int[dimension][dimension];
    }
/**
 * Méthode getGrid pour obtenir la grille
 * @return int[][]
 */
    public int[][] getGrid() {
        return grid;
    }
/**
 * Méthode initialiseRandom pour définir la grille aléatoirement
 * @return void
 */
    public void initializeRandom() {
        for (int i = 0; i < dimension; i++) {
        for (int j = 0; j < dimension; j++) {
            grid[i][j] = Math.random() > 0.5 ? 1 : 0;
        }
        }
    }
/**
 * Méthode evolve pour faire évoluer la d'un nombre de pas donné
 * @param steps
 * @return void
 */
    public void evolve(int steps) {
        for (int step = 0; step < steps; step++) {
            System.out.println("Étape " + step + " :");
            printGrid();
            int[][] newGrid = new int[dimension][dimension];

            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    int neighbors = countNeighbors(i, j);

                    if (neighbors == 3) {
                        newGrid[i][j] = 1;
                    } else if (neighbors == 2) {
                        if (grid[i][j] == 1) {
                            newGrid[i][j] = 1;
                        } else {
                            newGrid[i][j] = 0;
                        }
                    } else {
                        newGrid[i][j] = 0;
                    }
                }
            }

            grid = newGrid;
        }
    }
/**
 * Méthode countNeighbors pour compter les voisins
 * @param x absisse de la cellule
 * @param y ordonnée de la cellule
 * @return int nombre de cellules voisines vivantes
 */
    private int countNeighbors(int x, int y) {
        int count = 0;
        int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < dimension && ny >= 0 && ny < dimension) {
                count += grid[nx][ny];
            }
        }

        return count;
    }
/**
 * Méthode printGrid pour afficher la grille
 * @return void
 */
    private void printGrid() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                System.out.print(grid[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("----------------");
    }
/**
 * Main pour tester la classe GameOfLife
 * @param args
 */
    public static void main(String[] args) {
        int dimension = 5;
        GameOfLife game = new GameOfLife(dimension);
        game.initializeRandom();
        game.evolve(5);
    }
}