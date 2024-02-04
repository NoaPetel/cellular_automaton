import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FirePropagation {
    private static final int VIDE = 0;
    private static final int FORET = 1;
    private static final int ENFEU = 2;
    private static final int BRULE = 3;
    private static final double PROBA = 0.125;

    private int vent;
    private double force_vent; // 1 gauche à droite, 2 droite à gauche, 3 haut en bas, 4 bas en haut
    private int dimension;
    private int[][] grid;
/**
 * Constructeur de la classe FirePropagation
 * @param dimension Taille de la grille de simulation
 * @param vent Direction du vent
 * @param d Force du vent
 */
    public FirePropagation(int dimension, int vent, double d) {
        this.dimension = dimension;
        this.vent = vent;
        this.force_vent = d;
        this.grid = new int[dimension][dimension];

        initializeGrid();
    }
/**
 * Méthode getGrid pour obtenir la grille
 * @return int[][]
 */
    public int[][] getGrid() {
        return grid;
    }
/**
 * Méthode initialiseGrid pour initialiser la grille avec des cellules en feu, vide ou de forêt
 */
    public void initializeGrid() {
        Random rand = new Random();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                // Initialisation aléatoire avec une cellule en feu, vide ou de forêt
                double randomValue = rand.nextDouble();
                if (randomValue < PROBA) {
                    grid[i][j] = ENFEU;
                } else if (randomValue < 2 * PROBA) {
                    grid[i][j] = VIDE;
                } else {
                    grid[i][j] = FORET;
                }
            }
        }
    }
/**
 * Méthode evolve pour faire évoluer la d'un nombre de pas donné
 * @param steps nombre d'étape de la simulation
 */
    public void evolve(int steps) {
        for (int step = 0; step < steps; step++) {
            System.out.println("Étape " + step + " :");
            printGrid();
            int[][] newGrid = new int[dimension][dimension];
            List<int[]> cellInFire = CellsOnFire();
            for (int i = 0; i < dimension; i++) {
                for (int j = 0; j < dimension; j++) {
                    int neighbors = countNeighborsOnFire(i, j);
                    if (grid[i][j] == VIDE) {
                        newGrid[i][j] = VIDE;
                    } else if (grid[i][j] == ENFEU) {
                        newGrid[i][j] = BRULE;
                    } else if (grid[i][j] == BRULE) {
                        newGrid[i][j] = BRULE;
                    } else {
                        if (neighbors == 0)
                            newGrid[i][j] = FORET;
                        else{
                            switch (vent) {
                                case 1://cas ou le vent souffle de gauche à droite
                                    if (!cellInFire.isEmpty() && containsElement(cellInFire, i, j-1)) {//si la cellule à gauche est en feu
                                        if (Math.random() <= neighbors * PROBA + force_vent)//si oui la probabilité de propagation est augmentée
                                            newGrid[i][j] = ENFEU; //si par chance la probabilité est respectée, la cellule prend feu
                                        else 
                                            newGrid[i][j] = FORET;//sinon elle reste en forêt
                                    } else if (!cellInFire.isEmpty() && containsElement(cellInFire, i, j+1)) {//si la cellule à droite est en feu
                                        if (Math.random() <= neighbors * PROBA - force_vent)//si oui la probabilité de propagation est diminuée
                                            newGrid[i][j] = ENFEU;//si par chance la probabilité est respectée, la cellule prend feu
                                        else
                                            newGrid[i][j] = FORET;      //sinon elle reste en forêt
                                    } else { //si aucune des cellules à gauche ou à droite n'est en feu mais qu'il y a quand meme des voisins alors la cellule a une probabilité de prendre feu
                                        if (Math.random() <= neighbors * PROBA)
                                            newGrid[i][j] = ENFEU;
                                        else    
                                            newGrid[i][j] = FORET;
                                    }
                                    break;

                                case 2://cas ou le vent souffle de droite à gauche
                                    if (!cellInFire.isEmpty() && containsElement(cellInFire, i, j+1)) {//si la cellule à droite est en feu
                                        if (Math.random() <= neighbors * PROBA + force_vent) 
                                            newGrid[i][j] = ENFEU;
                                        else
                                            newGrid[i][j] = FORET;
                                    } else if (!cellInFire.isEmpty() && containsElement(cellInFire, i, j-1)) {//si la cellule à gauche est en feu
                                        if (Math.random() <= neighbors * PROBA - force_vent)
                                            newGrid[i][j] = ENFEU;
                                        else
                                            newGrid[i][j] = FORET;
                                    } else {
                                        if (Math.random() <= neighbors * PROBA)
                                            newGrid[i][j] = ENFEU;
                                        else
                                            newGrid[i][j] = FORET;
                                    }
                                    break;
                                case 3: //cas ou le vent souffle de haut en bas
                                    if (!cellInFire.isEmpty() && containsElement(cellInFire, i-1, j)) {//si la cellule en haut est en feu
                                        if (Math.random() <= neighbors * PROBA + force_vent)
                                            newGrid[i][j] = ENFEU;
                                        else
                                            newGrid[i][j] = FORET;
                                    } else if (!cellInFire.isEmpty() && containsElement(cellInFire, i+1, j)) {//si la cellule en bas est en feu
                                        if (Math.random() <= neighbors * PROBA - force_vent)
                                            newGrid[i][j] = ENFEU;
                                        else
                                            newGrid[i][j] = FORET;
                                    } else {
                                        if (Math.random() <= neighbors * PROBA)
                                            newGrid[i][j] = ENFEU;
                                        else
                                            newGrid[i][j] = FORET;
                                    }
                                    break;
                                case 4://cas ou le vent souffle de bas en haut
                                    if (!cellInFire.isEmpty() &&  containsElement(cellInFire, i+1, j)) {//si la cellule en bas est en feu
                                        if (Math.random() <= neighbors * PROBA + force_vent)
                                            newGrid[i][j] = ENFEU;
                                        else
                                            newGrid[i][j] = FORET;
                                    } else if (!cellInFire.isEmpty() &&  containsElement(cellInFire, i-1, j)) {//si la cellule en haut est en feu
                                        if (Math.random() <= neighbors * PROBA - force_vent)
                                            newGrid[i][j] = ENFEU;
                                        else
                                            newGrid[i][j] = FORET;
                                    } else {
                                        if (Math.random() <= neighbors * PROBA)
                                            newGrid[i][j] = ENFEU;
                                        else
                                            newGrid[i][j] = FORET;
                                    }
                                    break;
                                default:
                                    if (Math.random() <= neighbors * PROBA)
                                        newGrid[i][j] = ENFEU;
                                    break;
                            }
                        }
                    }
                }
            }

        if(Arrays.deepEquals(this.grid, newGrid)){//si la grille n'a pas changé, on arrete la simulation
                System.exit(0);
            }
            else {
                grid = newGrid;
            }
        }
    }
/**
 * Méthode containsElement pour vérifier si un couple de coordonnées est dans la liste
 * @param list
 * @param x
 * @param y
 * @return boolean
    */
    private boolean containsElement(List<int[]> list, int x, int y) {//methode pour verifier si un couple de coordonées est dans la liste
        for (int[] coordinates : list) {
            if (coordinates[0] == x && coordinates[1] == y) {
                return true;
            }
        }
        return false;
    }
/**
 * Méthode countNeighborsOnFire pour compter les voisins en feu
 * @param x
 * @param y
 * @return int nombre de cellules voisines en feu
 */
    private int countNeighborsOnFire(int x, int y) {//compte le nombre de voisin à la cellule en feu
        int count = 0;
        int[] dx = { -1, -1, -1, 0, 0, 1, 1, 1 };
        int[] dy = { -1, 0, 1, -1, 1, -1, 0, 1 };

        for (int i = 0; i < 8; i++) {
            int nx = x + dx[i];
            int ny = y + dy[i];

            if (nx >= 0 && nx < dimension && ny >= 0 && ny < dimension) {
                if (grid[nx][ny] == ENFEU) {
                    count++;
                }
            }
        }

        return count;
    }/**
    * Méthode CellsOnFire pour déterminer les coordonées des cellules en feu
    * @return List<int[]>
     */
    private List<int[]> CellsOnFire() { 
        List<int[]> count = new ArrayList<>();

        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                if (grid[i][j] == ENFEU) {
                    int[] coordonnees = { i, j };
                    count.add(coordonnees);
                }
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
}