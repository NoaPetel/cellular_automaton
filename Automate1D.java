import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * La classe {@code Automate1D} représente un automate cellulaire élémentaire 1D.
 * Elle permet de visualiser l'évolution de l'automate basé sur une règle donnée,
 * affichant toutes les générations l'une après l'autre.
 */
public class Automate1D extends JPanel {
    private int[][] history; // Historique de toutes les générations
    private int generationIndex = 0; // Index de la génération actuelle à calculer et afficher

    private final int gridSize; // Nombre de cellules dans la grille
    private final int cellDisplaySize; // Taille d'affichage de chaque cellule
    private int generationsCount; // Nombre de générations à afficher
    private int[] ruleSet; // Ensemble des règles pour l'évolutio

    /**
     * Construit l'automate cellulaire avec une taille de grille spécifiée, une règle, et un nombre de générations.
     *
     * @param gridSize La taille de la grille de l'automate.
     * @param rule Le numéro de la règle à appliquer pour l'évolution de l'automate.
     * @param generations Le nombre total de générations à simuler.
     */
    public Automate1D(int gridSize, int rule, int generations) {
        this.gridSize = gridSize;
        this.generationsCount = generations;
        this.cellDisplaySize = 800 / gridSize; // Ajuste la taille d'affichage pour qu'elle tienne dans 800 pixels
        this.history = new int[generations][gridSize];
        this.history[0][gridSize / 2] = 1; // Commence avec une seule cellule au milieu

        this.ruleSet = new int[8];
        for (int i = 0; i < 8; i++) {
            this.ruleSet[i] = (rule >> i) & 1;
        }
        setPreferredSize(new Dimension(gridSize * this.cellDisplaySize, generations * this.cellDisplaySize));
    }

    /**
     * Initialise la configuration des cellules de l'automate à partir d'une chaîne de caractères.
     *
     * @param config La chaîne de caractères représentant la configuration initiale.
     */
    public void initializeFromString(String config) {
        // Remplir la première génération avec des états morts (0)
        Arrays.fill(history[0], 0);

        // Utiliser la longueur minimale entre la configuration donnée et la taille de la grille
        int configLength = Math.min(config.length(), gridSize);

        // Définir l'état initial de chaque cellule selon la configuration donnée
        for (int i = 0; i < configLength; i++) {
            history[0][i] = config.charAt(i) == '1' ? 1 : 0;
        }
    }


    public boolean isEvolutionComplete() {
        return generationIndex >= generationsCount - 1;
    }
    /**
     * Fait évoluer l'automate d'une génération en utilisant la règle définie à la construction.
     */
    public void evolve() {
        if (!isEvolutionComplete()) {
            int[] newGeneration = new int[gridSize];
            for (int i = 0; i < gridSize; i++) {
                int left = (i > 0) ? history[generationIndex][i - 1] : 0;
                int middle = history[generationIndex][i];
                int right = (i < gridSize - 1) ? history[generationIndex][i + 1] : 0;

                newGeneration[i] = ruleSet[left * 4 + middle * 2 + right];
            }
            generationIndex++;
            history[generationIndex] = newGeneration;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int gen = 0; gen <= generationIndex; gen++) {
            for (int i = 0; i < history[gen].length; i++) {
                g.setColor(history[gen][i] == 1 ? Color.BLACK : Color.WHITE);
                g.fillRect(i * cellDisplaySize, gen * cellDisplaySize, cellDisplaySize, cellDisplaySize);
            }
        }
    }
}



