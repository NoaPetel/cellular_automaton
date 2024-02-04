import javax.swing.*;
import java.awt.*;

/**
 * La classe {@code GameOfLifePanel} sert à visualiser le Jeu de la Vie.
 * Elle hérite de {@code JPanel} et dessine une grille où chaque cellule peut être vivante ou morte.
 */
class GameOfLifePanel extends JPanel {
    private int[][] grid;

    /**
     * La taille de chaque cellule pour l'affichage.
     */
    private final int size;

    /**
     * Construit un panneau d'affichage pour le Jeu de la Vie.
     *
     * @param grid La grille du jeu de la vie, représentée par un tableau 2D d'entiers.
     */
    public GameOfLifePanel(int[][] grid) {
        this.grid = grid;
        this.size = 15;
        int dimension = grid.length;
        setPreferredSize(new Dimension(dimension * size, dimension * size));
    }

    /**
     * Peint le composant en dessinant la grille du jeu de la vie.
     * Chaque cellule vivante est dessinée en noir et chaque cellule morte en blanc.
     *
     * @param g L'objet graphique utilisé pour le dessin.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 1) {
                    g.setColor(Color.BLACK);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * size, i * size, size, size);
            }
        }
    }

    /**
     * Met à jour la grille affichée avec une nouvelle grille.
     *
     * @param newGrid La nouvelle grille du jeu de la vie à afficher.
     */
    public void updateGrid(int[][] newGrid) {
        this.grid = newGrid;
        repaint();
    }
}
