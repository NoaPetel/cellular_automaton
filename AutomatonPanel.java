import javax.swing.*;
import java.awt.*;

class AutomatonPanel extends JPanel {
    private Automaton automaton;
    private int cellSize;

    public AutomatonPanel(Automaton automaton, int cellSize) {
        this.automaton = automaton;
        this.cellSize = cellSize;
        int panelSize = automaton.width * cellSize;
        setPreferredSize(new Dimension(panelSize, panelSize));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < automaton.width; i++) {
            for (int j = 0; j < automaton.width; j++) {
                int cellState = automaton.grid.get(i + j * automaton.width).getState();
                g.setColor(cellState == 1 ? Color.BLACK : Color.WHITE);
                g.fillRect(i * cellSize, j * cellSize, cellSize, cellSize);
            }
        }
    }
}
