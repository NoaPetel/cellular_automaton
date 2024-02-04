import java.util.*;

public class AutomateCellulaire {
    public int d; // Dimension de l'automate
    public List<Integer> Q; // Alphabet (états possibles pour chaque cellule)
    public List<List<Integer>> etatCourant; // État actuel de l'automate
    public int[][] V; // Voisinage des cellules
    public Map<List<Integer>, Integer> delta; // Règle de transition

    public int etape = 1;

    /** Construcuteur de la classe pour l'automate cellulaire
     * 
     * @param d dimension de l'automate cellulaire(1 ou 2 ici)
     * @param Q l'ensemble de l'alphabet
     * @param V l'ensemble des voisinages de chaque cellule
     * @param delta règle de transition locale
     * 
     */  

    // Constructeur de la classe
    public AutomateCellulaire(int d, List<Integer> Q, int[][] V, Map<List<Integer>, Integer> delta) {
        this.d = d;
        this.Q = new ArrayList<>(Q);
        this.V = V;
        this.delta = delta;
        this.etatCourant = new ArrayList<>();

        // Initialisation de l'état courant
        if (d == 1) {
            this.etatCourant.add(new ArrayList<>(Collections.nCopies(Q.size(), Q.get(0))));
        } else if (d == 2) {
            // Exemple d'initialisation pour d = 2
            this.etatCourant.add(Arrays.asList(0, 1, 0, 1));
            this.etatCourant.add(Arrays.asList(1, 0, 1, 0));
        }
    }

    /**Méthode pour configurer l'état initial de l'automate
     * 
     * @param etatInitial etat initial de l'automate
     */
    public void setEtatInitial(List<List<Integer>> etatInitial) {
        this.etatCourant = new ArrayList<>(etatInitial);
    }

    /**Méthode pour appliquer la règle de transition à l'ensemble de l'automate (1D)
     */
    public void appliquerTransition1D() {
        List<List<Integer>> nouvelEtat = new ArrayList<>();

        for (List<Integer> ligne : etatCourant) {
            List<Integer> nouvelleLigne = new ArrayList<>();
            for (int i = 0; i < ligne.size(); i++) {
                List<Integer> contexte = new ArrayList<>();
                for (int[] voisinage : V) {
                    int indexVoisin = (i + voisinage[0] + ligne.size()) % ligne.size();
                    contexte.add(ligne.get(indexVoisin));
                }
                Integer nouvelEtatCellule = delta.get(contexte);
                if (nouvelEtatCellule != null) {
                    nouvelleLigne.add(nouvelEtatCellule);
                }
            }
            nouvelEtat.add(nouvelleLigne);
        }

        this.etatCourant = nouvelEtat;
    }

    /**Méthode pour appliquer la règle de transition à l'ensemble de l'automate (2D)
     */
    public void appliquerTransition2D() {
        List<List<Integer>> nouvelEtat = new ArrayList<>();

        for (int i = 0; i < etatCourant.size(); i++) {
            List<Integer> nouvelleLigne = new ArrayList<>();
            for (int j = 0; j < etatCourant.get(i).size(); j++) {
                List<Integer> contexte = new ArrayList<>();
                for (int[] voisinage : V) {
                    int indexLigne = (i + voisinage[0] + etatCourant.size()) % etatCourant.size();
                    int indexColonne = (j + voisinage[1] + etatCourant.get(i).size()) % etatCourant.get(i).size();
                    contexte.add(etatCourant.get(indexLigne).get(indexColonne));
                }
                Integer nouvelEtatCellule = delta.get(contexte);
                if (nouvelEtatCellule != null) {
                    nouvelleLigne.add(nouvelEtatCellule);
                }
            }
            nouvelEtat.add(nouvelleLigne);
        }

        this.etatCourant = nouvelEtat;
    }

    /**Méthode pour afficher l'état actuel de l'automate
     */
    public void afficherEtat() {
        System.out.println("Etape " + etape);
        for (List<Integer> ligne : etatCourant) {
            for (int etat : ligne) {
                System.out.print(etat + " ");
            }
            System.out.println();
        }
        System.out.println("------------");
        etape += 1;
    }

    /**Méthode pour exécuter l'automate pour un certain nombre d'itérations

     * 
     * @param iterations nombre d'itérations
     */
    public void executer(int iterations) {
        for (int i = 0; i < iterations; i++) {
            afficherEtat();
            if (d == 1) {
                appliquerTransition1D();
            } else if (d == 2) {
                appliquerTransition2D();
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> Q = Arrays.asList(0, 1); // Définition de l'alphabet
        int[][] V = {{-1}, {0}, {1}}; // Définition du voisinage
        Map<List<Integer>, Integer> delta = new HashMap<>(); // Définition de la règle de transition

        // Exemple de règle de transition pour le calcul
        delta.put(Arrays.asList(0, 0, 0), 0);
        delta.put(Arrays.asList(0, 0, 1), 1);
        delta.put(Arrays.asList(0, 1, 0), 1);
        delta.put(Arrays.asList(0, 1, 1), 0);
        delta.put(Arrays.asList(1, 0, 0), 1);
        delta.put(Arrays.asList(1, 0, 1), 0);
        delta.put(Arrays.asList(1, 1, 0), 0);
        delta.put(Arrays.asList(1, 1, 1), 1);

        AutomateCellulaire automate1D = new AutomateCellulaire(1, Q, V, delta);

        // Définir l'état initial de l'automate 1D
        List<List<Integer>> etatInitial1D = Arrays.asList(
            Arrays.asList(0, 1, 0, 1, 1, 0, 1, 0)
        );
        automate1D.setEtatInitial(etatInitial1D);

        // Exécuter l'automate 1D
        automate1D.executer(5);

        // Exemple d'automate 2D
        AutomateCellulaire automate2D = new AutomateCellulaire(2, Q, V, delta);

        // Définir l'état initial de l'automate 2D
        List<List<Integer>> etatInitial2D = Arrays.asList(
            Arrays.asList(0, 1, 0, 1),
            Arrays.asList(1, 0, 1, 0)
        );
        automate2D.setEtatInitial(etatInitial2D);

        // Exécuter l'automate 2D
        //automate2D.executer(5);
    }
}
