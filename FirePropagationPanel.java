import javax.swing.*;
import java.awt.*;

/**
 * La classe {@code FirePropagationPanel} représente le panneau d'affichage pour la simulation de la propagation du feu.
 * Elle étend {@code JPanel} et affiche une grille où chaque cellule représente un état de la forêt.
 */
class FirePropagationPanel extends JPanel {
    private int[][] grid; // Grille représentant l'état actuel de la simulation de feu.

    /**
     * Taille de chaque cellule pour l'affichage.
     */
    private final int size;

    /**
     * Construit un panneau de propagation du feu avec une grille spécifiée.
     *
     * @param grid La grille du jeu de propagation du feu, représentée par un tableau 2D d'entiers.
     */
    public FirePropagationPanel(int[][] grid) {
        this.grid = grid;
        this.size = 10; // Valeur par défaut de la taille de chaque cellule.
        int dimension = grid.length;
        setPreferredSize(new Dimension(dimension * size, dimension * size));
    }

    /**
     * Méthode de dessin qui affiche les différentes cellules de la grille selon leur état :
     * forêt, feu, brûlé ou vide.
     *
     * @param g L'objet graphique utilisé pour le dessin.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                switch (grid[i][j]) {
                    case 1: // Forêt
                        g.setColor(new Color(0, 128, 0)); // Vert
                        break;
                    case 2: // En feu
                        g.setColor(Color.RED);
                        break;
                    case 3: // Brûlé
                        g.setColor(Color.DARK_GRAY);
                        break;
                    default:
                        g.setColor(Color.WHITE); // Vide
                }
                g.fillRect(j * size, i * size, size, size);
            }
        }
    }

    /**
     * Met à jour la grille affichée avec une nouvelle grille représentant l'état actuel de la simulation.
     *
     * @param newGrid La nouvelle grille représentant l'état de la simulation de feu à afficher.
     */
    public void updateGrid(int[][] newGrid) {
        this.grid = newGrid;
        repaint();
    }
}


