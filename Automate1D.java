import javax.swing.*;
import java.awt.*;

/**
 * La classe {@code Automate1D} représente un automate cellulaire élémentaire 1D.
 * Elle permet de visualiser l'évolution de l'automate basé sur une règle donnée,
 * affichant toutes les générations l'une après l'autre.
 */
public class Automate1D extends JPanel {
    private int[][] history; // Historique de toutes les générations
    private int generationIndex = 0; // Index de la génération actuelle à calculer et afficher

    private int[] currentGeneration;
    private int[] ruleSet;
    private final int size = 30;
    private int generationsCount; // Nombre de générations à afficher

    /**
     * Construit l'automate cellulaire avec une taille de grille spécifiée, une règle, et un nombre de générations.
     *
     * @param size La taille de la grille de l'automate.
     * @param rule Le numéro de la règle à appliquer pour l'évolution de l'automate.
     * @param generations Le nombre total de générations à simuler.
     */
    public Automate1D(int size, int rule, int generations) {
        this.currentGeneration = new int[size];
        this.history = new int[generations][size];
        this.history[0] = this.currentGeneration.clone();
        this.generationsCount = generations;

        this.ruleSet = new int[8];
        for (int i = 0; i < 8; i++) {
            this.ruleSet[i] = (rule >> i) & 1;
        }
        setPreferredSize(new Dimension(size * this.size, generations * this.size));
    }

    /**
     * Initialise la configuration des cellules de l'automate à partir d'une chaîne de caractères.
     *
     * @param config La chaîne de caractères représentant la configuration initiale.
     */
    public void initializeFromString(String config) {
        for (int i = 0; i < config.length(); i++) {
            if (i < this.currentGeneration.length && config.charAt(i) == '1') {
                this.currentGeneration[i] = 1;
            }
        }
        this.history[0] = this.currentGeneration.clone();
    }
    public boolean isEvolutionComplete() {
        return generationIndex + 1 >= history.length;
    }
    /**
     * Fait évoluer l'automate d'une génération en utilisant la règle définie à la construction.
     */
    public void evolve() {
        if (generationIndex + 1 < history.length) {
            int[] newGeneration = new int[size];
            for (int i = 0; i < size - 1; i++) {
                int left = 0;
                if (i > 0) {
                    left = history[generationIndex][i - 1];
                }
                int middle = history[generationIndex][i];
                int right = history[generationIndex][i + 1];
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
                if (history[gen][i] == 1) {
                    g.fillRect(i * size, gen * size, size, size);
                }
            }
        }
    }
}


