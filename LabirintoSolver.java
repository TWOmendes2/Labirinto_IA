import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

class Ponto implements Comparable<Ponto> {
    int x, y;
    Ponto pai;
    int g, h, f;

    public Ponto(int x, int y, Ponto pai, int g, int h) {
        this.x = x;
        this.y = y;
        this.pai = pai;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    @Override
    public int compareTo(Ponto other) {
        return Integer.compare(this.f, other.f);
    }
}

class Labirinto {
    int rows, cols;
    char[][] maze;
    boolean[][] visitado;
    Ponto start, end;

    public Labirinto(char[][] predefinedMaze) {
        this.rows = predefinedMaze.length;
        this.cols = predefinedMaze[0].length;
        this.maze = predefinedMaze;
        this.visitado = new boolean[rows][cols];

        // Find start ('S') and end ('E') positions
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (maze[i][j] == 'S') {
                    start = new Ponto(i, j, null, 0, 0);
                } else if (maze[i][j] == 'E') {
                    end = new Ponto(i, j, null, 0, 0);
                }
            }
        }
    }

    public boolean movimentoValido(int x, int y) {
        return x >= 0 && x < rows && y >= 0 && y < cols && maze[x][y] != 'X' && !visitado[x][y];
    }

    public List<Ponto> construirCaminho(Ponto fim) {
        List<Ponto> caminho = new ArrayList<>();
        Ponto atual = fim;
        while (atual != null) {
            caminho.add(atual);
            atual = atual.pai;
        }
        Collections.reverse(caminho);
        return caminho;
    }

    public void marcarCaminho(List<Ponto> caminho) {
        for (Ponto p : caminho) {
            if (maze[p.x][p.y] != 'S' && maze[p.x][p.y] != 'E') {
                maze[p.x][p.y] = '2'; // Mark path in the maze
            }
        }
    }
}

class LabirintoPanel extends JPanel {
    private Labirinto labirinto;
    private List<Ponto> caminho;
    private Ponto agente;

    public LabirintoPanel(Labirinto labirinto, List<Ponto> caminho) {
        this.labirinto = labirinto;
        this.caminho = caminho;
        if (caminho != null && !caminho.isEmpty()) {
            this.agente = caminho.get(0);
            new javax.swing.Timer(500, e -> moverAgente()).start();
        }
    }

    private void moverAgente() {
        int currentIndex = caminho.indexOf(agente);
        if (currentIndex < caminho.size() - 1) {
            agente = caminho.get(currentIndex + 1);
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int cellSize = Math.min(getWidth() / labirinto.cols, getHeight() / labirinto.rows);

        for (int i = 0; i < labirinto.rows; i++) {
            for (int j = 0; j < labirinto.cols; j++) {
                if (labirinto.maze[i][j] == 'X') {
                    g.setColor(Color.BLACK);
                } else if (labirinto.maze[i][j] == '2') {
                    g.setColor(Color.YELLOW);
                } else if (labirinto.maze[i][j] == 'S') {
                    g.setColor(Color.GREEN);
                } else if (labirinto.maze[i][j] == 'E') {
                    g.setColor(Color.RED);
                } else {
                    g.setColor(Color.WHITE);
                }
                g.fillRect(j * cellSize, i * cellSize, cellSize, cellSize);
                g.setColor(Color.GRAY);
                g.drawRect(j * cellSize, i * cellSize, cellSize, cellSize);
            }
        }
    }
}

public class LabirintoSolver extends JFrame {
    public LabirintoSolver(Labirinto labirinto, List<Ponto> caminho) {
        setTitle("Labirinto Solver");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(new LabirintoPanel(labirinto, caminho));
    }

    public static void main(String[] args) {
        char[][][] predefinedMazes = {
            {
                {'S', '0', 'X', '0', 'X'},
                {'X', '0', 'X', '0', 'X'},
                {'0', '0', '0', '0', '0'},
                {'0', 'X', 'X', 'X', '0'},
                {'0', '0', '0', 'X', 'E'}
            },
            
            {
                {'S', '0', '0', 'X', '0'},
                {'X', 'X', '0', 'X', '0'},
                {'0', '0', '0', '0', '0'},
                {'0', 'X', 'X', 'X', '0'},
                {'0', '0', '0', 'X', '0'},
                {'0', '0', '0', '0', 'E'}
            },
            
            {
                {'S', '0', '0', '0', '0'},
                {'X', 'X', '0', 'X', '0'},
                {'0', '0', '0', 'X', '0'},
                {'0', 'X', 'X', 'E', 'X'},
                {'0', '0', '0', '0', '0'}
            },
            
            {
                {'S', 'X', '0', '0', '0'},
                {'0', 'X', 'X', 'X', '0'},
                {'0', '0', '0', '0', '0'},
                {'0', 'X', '0', 'X', '0'},
                {'X', '0', '0', 'X', 'E'}
            },
            
            {
                {'S', '0', 'X', '0', '0'},
                {'0', 'X', 'X', 'X', '0'},
                {'0', '0', '0', '0', '0'},
                {'0', 'X', 'X', 'X', '0'},
                {'0', '0', 'X', '0', 'E'}
            },
            
            {
                {'S', '0', '0', '0', '0'},
                {'0', 'X', 'X', 'X', '0'},
                {'0', '0', '0', '0', '0'},
                {'0', 'X', 'X', 'X', '0'},
                {'0', '0', '0', '0', 'E'}
            },
            
            {
                {'S', '0', '0', '0', '0', '0', '0', '0'},
                {'0', 'X', 'X', 'X', 'X', 'X', 'X', '0'},
                {'0', 'X', '0', '0', '0', '0', '0', '0'},
                {'0', 'X', '0', 'X', 'X', 'X', 'X', '0'},
                {'0', 'X', '0', 'X', '0', '0', '0', '0'},
                {'0', 'X', '0', 'X', '0', 'X', 'X', '0'},
                {'0', 'X', '0', 'X', '0', '0', 'X', '0'},
                {'0', 'X', '0', 'X', 'X', '0', 'E', '0'}
            },
            
            {
                {'S', '0', '0', '0', '0', '0', '0', '0', '0', 'X'},
                {'0', 'X', 'X', 'X', 'X', 'X', 'X', '0', 'X', '0'},
                {'0', 'X', '0', '0', '0', '0', '0', '0', 'X', '0'},
                {'0', 'X', '0', 'X', 'X', 'X', 'X', 'X', 'X', '0'},
                {'0', 'X', '0', 'X', '0', '0', 'X', '0', '0', '0'},
                {'0', 'X', '0', 'X', 'X', 'X', 'X', '0', 'X', '0'},
                {'0', 'X', '0', '0', '0', '0', '0', '0', 'X', '0'},
                {'0', 'X', '0', 'X', 'X', 'X', 'X', 'X', 'X', '0'},
                {'0', 'X', '0', '0', '0', '0', 'X', '0', '0', '0'},
                {'0', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'X', 'E'}
            }
            
            // Add more predefined mazes as needed
        };

        for (char[][] predefinedMaze : predefinedMazes) {
            Labirinto labirinto = new Labirinto(predefinedMaze);
            List<Ponto> caminho = encontrarCaminhoMaisCurto(labirinto, labirinto.start, labirinto.end);

            if (caminho != null) {
                System.out.println("Caminho encontrado.");
                labirinto.marcarCaminho(caminho);
            } else {
                System.out.println("Caminho não encontrado.");
            }

            SwingUtilities.invokeLater(() -> {
                LabirintoSolver frame = new LabirintoSolver(labirinto, caminho);
                frame.setVisible(true);
            });

            // Pause briefly before displaying the next maze (for visibility in UI)
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static int heuristica(Ponto ponto, Ponto fim) {
        return Math.abs(ponto.x - fim.x) + Math.abs(ponto.y - fim.y);
    }

    public static List<Ponto> encontrarCaminhoMaisCurto(Labirinto labirinto, Ponto inicio, Ponto fim) {
        int[][] movimentos = { {-1, 0}, {1, 0}, {0, -1}, {0, 1} };
        PriorityQueue<Ponto> heap = new PriorityQueue<>();
        heap.add(new Ponto(inicio.x, inicio.y, null, 0, heuristica(inicio, fim)));
        labirinto.visitado[inicio.x][inicio.y] = true;

        while (!heap.isEmpty()) {
            Ponto atual = heap.poll();

            if (atual.x == fim.x && atual.y == fim.y) {
                return labirinto.construirCaminho(atual);
            }

            for (int[] movimento : movimentos) {
                int novaLinha = atual.x + movimento[0];
                int novaColuna = atual.y + movimento[1];

                if (labirinto.movimentoValido(novaLinha, novaColuna)) {
                    labirinto.visitado[novaLinha][novaColuna] = true;
                    int g = atual.g + 1;
                    int h = heuristica(new Ponto(novaLinha, novaColuna, null, 0, 0), fim);
                    heap.add(new Ponto(novaLinha, novaColuna, atual, g, h));
                }
            }
        }

        return null;
    }
}
