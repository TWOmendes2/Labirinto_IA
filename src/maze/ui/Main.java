package maze.ui;

import maze.domain.*;
import maze.search.Search;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public final class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Labirinto v2 — BFS / Dijkstra / A*");
            JTextArea input = new JTextArea("S9E\n...", 8, 24);
            input.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 20));
            JComboBox<Search.Algorithm> algorithms = new JComboBox<>(Search.Algorithm.values());
            JLabel status = new JLabel("S=início E=fim X=parede .=livre 1–9=custo");
            JPanel canvas = new JPanel();
            JButton solve = new JButton("Buscar caminho");
            solve.addActionListener(event -> {
                try {
                    Maze maze = new Maze(Arrays.asList(input.getText().strip().split("\\R")));
                    if (maze.width() > 100 || maze.height() > 100) throw new IllegalArgumentException("Limite visual: 100 × 100");
                    Search.Result result = Search.solve(maze, (Search.Algorithm) algorithms.getSelectedItem());
                    canvas.removeAll(); canvas.setLayout(new GridLayout(maze.height(), maze.width(), 2, 2));
                    for (int r = 0; r < maze.height(); r++) for (int c = 0; c < maze.width(); c++) {
                        Cell cell = new Cell(r, c);
                        JLabel label = new JLabel(String.valueOf(maze.at(cell)), SwingConstants.CENTER);
                        label.setOpaque(true);
                        label.setBackground(maze.at(cell) == 'X' ? Color.GRAY : result.path().contains(cell) ? Color.YELLOW : Color.WHITE);
                        canvas.add(label);
                    }
                    status.setText(result.found() ? "Passos: " + (result.path().size()-1) + " | Custo: " + result.cost() + " | Expandidos: " + result.expanded() : "Sem caminho");
                    canvas.revalidate(); canvas.repaint();
                } catch (IllegalArgumentException error) { status.setText(error.getMessage()); }
            });
            JPanel controls = new JPanel(new BorderLayout());
            controls.add(new JScrollPane(input), BorderLayout.CENTER);
            JPanel actions = new JPanel(); actions.add(algorithms); actions.add(solve);
            controls.add(actions, BorderLayout.SOUTH);
            frame.add(controls, BorderLayout.WEST); frame.add(canvas, BorderLayout.CENTER); frame.add(status, BorderLayout.SOUTH);
            frame.setSize(900, 500); frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); frame.setVisible(true);
        });
    }
}
