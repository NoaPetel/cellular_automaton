import javax.swing.*;
import java.awt.*;

/**
 * La classe {@code Automate1D} représente un automate cellulaire élémentaire 1D.
 * Elle permet de visualiser l'évolution de l'automate basé sur une règle donnée.
 */
public class Automate1D extends JPanel {
    private int[] cells;
    private int[] ruleSet;

    /**
     * La taille de chaque cellule pour l'affichage.
     */
    private final int size = 15;

    /**
     * Construit l'automate cellulaire avec une taille de grille spécifiée et une règle.
     *
     * @param size La taille de la grille de l'automate.
     * @param rule Le numéro de la règle à appliquer pour l'évolution de l'automate.
     */
    public Automate1D(int size, int rule) {
        cells = new int[size];
        cells[size / 2] = 1; // Initialisation avec une cellule au centre
        ruleSet = new int[8];
        for (int i = 0; i < 8; i++) {
            ruleSet[i] = (rule >> i) & 1;
        }
        setPreferredSize(new Dimension(size * this.size, this.size));
    }

    /**
     * Initialise la configuration des cellules de l'automate à partir d'une chaîne de caractères.
     *
     * @param config La chaîne de caractères représentant la configuration initiale.
     *               Les caractères '1' représentent les cellules vivantes et '0' les cellules mortes.
     */
    public void initializeFromString(String config) {
        cells = new int[cells.length];
        for (int i = 0; i < config.length(); i++) {
            if (i < cells.length && config.charAt(i) == '1') {
                cells[i] = 1;
            }
        }
    }

    /**
     * Fait évoluer l'automate d'une génération en utilisant la règle définie à la construction.
     */
    public void evolve() {
        int[] newCells = new int[cells.length];
        for (int i = 1; i < cells.length - 1; i++) {
            int left = cells[i - 1];
            int middle = cells[i];
            int right = cells[i + 1];
            newCells[i] = ruleSet[left * 4 + middle * 2 + right];
        }
        cells = newCells;
        repaint();
    }

    /**
     * Peint le composant avec l'état actuel des cellules de l'automate.
     *
     * @param g L'objet graphique utilisé pour le dessin.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < cells.length; i++) {
            if (cells[i] == 1) {
                g.fillRect(i * this.size, 0, this.size, this.size);
            }
        }
    }
}


