import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * La classe principale {@code MainApp} qui initialise l'interface utilisateur pour la simulation de différents automates.
 * Elle crée une fenêtre avec des options pour lancer différents jeux et simulations.
 */
public class MainApp extends JFrame {

    private JTextField delayField;
    private JTextField stepsField;
    private JTextField gridSizeField;
    private JTextField windDirectionField;
    private JTextField windStrengthField;
    private JTextField initialConfigField;
    private JTextField ruleField;

    /**
     * Constructeur de {@code MainApp} qui initialise l'interface utilisateur.
     */
    public MainApp() {
        super("Jeux de Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Le panel principal avec un GridLayout pour organiser les sous-panels en une seule colonne
        JPanel panel = new JPanel(new GridLayout(0, 1));

        initialConfigField = createInputField(panel, "Configuration initiale (ex: 0001000):", "0001000");
        ruleField = createInputField(panel, "Règle (0-255):", "170");
        delayField = createInputField(panel, "Délai (ms):", "500");
        stepsField = createInputField(panel, "Nombre d'actualisations:", "1000");
        gridSizeField = createInputField(panel, "Taille de la grille:", "50");
        windDirectionField = createInputField(panel, "Direction du vent (1 > droite, 2 > gauche, 3 > bas, 4 > haut):", "1");
        windStrengthField = createInputField(panel, "Force du vent (0-1):", "0.5");

        JButton btnAutomate1D = new JButton("Automate Élémentaire");
        btnAutomate1D.addActionListener(e -> launchAutomate1D());
        panel.add(btnAutomate1D);

        JButton btnGameOfLife = new JButton("Jeu de la Vie");
        btnGameOfLife.addActionListener(this::launchGameOfLife);
        panel.add(btnGameOfLife);

        JButton btnFirePropagation = new JButton("Propagation du Feu");
        btnFirePropagation.addActionListener(this::launchFirePropagation);
        panel.add(btnFirePropagation);

        setContentPane(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    /**
     * Crée un champ de saisie avec un label et une valeur par défaut.
     *
     * @param panel Le panneau auquel le champ de saisie sera ajouté.
     * @param label Le label du champ de saisie.
     * @param defaultValue La valeur par défaut affichée dans le champ de saisie.
     * @return Le champ de saisie créé.
     */
    private JTextField createInputField(JPanel panel, String label, String defaultValue) {
        JPanel fieldPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fieldPanel.add(new JLabel(label));
        JTextField textField = new JTextField(defaultValue, 10);
        fieldPanel.add(textField);
        panel.add(fieldPanel);
        return textField;
    }

    /**
     * Lance la simulation de l'Automate Élémentaire dans une nouvelle fenêtre.
     */
    private void launchAutomate1D() {
        int gridSize = parseInt(gridSizeField.getText(), 100);
        int rule = parseInt(ruleField.getText(), 170);
        String initialConfig = initialConfigField.getText().trim();

        Automate1D automaton = new Automate1D(gridSize, rule);
        automaton.initializeFromString(initialConfig);
        setContentPane(automaton);
        revalidate();
        repaint();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        int delay = parseInt(delayField.getText(), 500);
        new Timer(delay, event -> automaton.evolve()).start();
    }
    /**
     * Lance la simulation du Jeu de la Vie dans une nouvelle fenêtre.
     *
     * @param e L'événement qui a déclenché la méthode.
     */
    private void launchGameOfLife(ActionEvent e) {
        int delay = parseInt(delayField.getText(), 500);
        AtomicInteger steps = new AtomicInteger(parseInt(stepsField.getText(), 50));
        int gridSize = parseInt(gridSizeField.getText(), 50);

        GameOfLife game = new GameOfLife(gridSize);
        game.initializeRandom();
        GameOfLifePanel gamePanel = new GameOfLifePanel(game.getGrid());
        setContentPane(gamePanel);
        revalidate();
        repaint();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        Timer timer = new Timer(delay, event -> {
            game.evolve(1);
            gamePanel.updateGrid(game.getGrid());
            steps.getAndDecrement();
            if (steps.get() <= 0) {
                ((Timer)event.getSource()).stop();
            }
        });
        timer.start();
    }

    /**
     * Lance la simulation de la Propagation du Feu dans une nouvelle fenêtre.
     *
     * @param e L'événement qui a déclenché la méthode.
     */
    private void launchFirePropagation(ActionEvent e) {
        int delay = parseInt(delayField.getText(), 500);
        AtomicInteger steps = new AtomicInteger(parseInt(stepsField.getText(), 50));
        int gridSize = parseInt(gridSizeField.getText(), 50);
        int windDirection = parseInt(windDirectionField.getText(), 1);
        double windStrength = parseDouble(windStrengthField.getText(), 0.5);

        FirePropagation fire = new FirePropagation(gridSize, windDirection, windStrength);
        fire.initializeGrid();
        FirePropagationPanel firePanel = new FirePropagationPanel(fire.getGrid());
        setContentPane(firePanel);
        revalidate();
        repaint();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        Timer timer = new Timer(delay, event -> {
            fire.evolve(1);
            firePanel.updateGrid(fire.getGrid());
            steps.getAndDecrement();
            if (steps.get() <= 0) {
                ((Timer)event.getSource()).stop();
            }
        });
        timer.start();
    }

    /**
     * Parse une chaîne de caractères en entier avec une valeur par défaut en cas d'erreur de format.
     *
     * @param text Le texte à parser.
     * @param defaultValue La valeur par défaut si le texte ne peut pas être parsé.
     * @return L'entier parsé ou la valeur par défaut.
     */
    private int parseInt(String text, int defaultValue) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Parse une chaîne de caractères en double avec une valeur par défaut en cas d'erreur de format.
     *
     * @param text Le texte à parser.
     * @param defaultValue La valeur par défaut si le texte ne peut pas être parsé.
     * @return Le double parsé ou la valeur par défaut.
     */
    private double parseDouble(String text, double defaultValue) {
        try {
            return Double.parseDouble(text.trim());
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    /**
     * Le point d'entrée du programme.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainApp::new);
    }
}

