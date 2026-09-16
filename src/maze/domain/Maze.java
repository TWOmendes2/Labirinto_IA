package maze.domain;

import java.util.ArrayList;
import java.util.List;

public final class Maze {
    private final List<String> rows;
    private final Cell start;
    private final Cell end;

    public Maze(List<String> input) {
        if (input == null || input.isEmpty() || input.get(0) == null || input.get(0).isEmpty())
            throw new IllegalArgumentException("Labirinto vazio");
        rows = List.copyOf(input);
        Cell s = null, e = null;
        for (int r = 0; r < rows.size(); r++) {
            if (rows.get(r).length() != width()) throw new IllegalArgumentException("Linhas irregulares");
            for (int c = 0; c < width(); c++) {
                char value = rows.get(r).charAt(c);
                if ("SE.X123456789".indexOf(value) < 0) throw new IllegalArgumentException("Célula inválida");
                if (value == 'S') { if (s != null) throw new IllegalArgumentException("Início duplicado"); s = new Cell(r, c); }
                if (value == 'E') { if (e != null) throw new IllegalArgumentException("Fim duplicado"); e = new Cell(r, c); }
            }
        }
        if (s == null || e == null) throw new IllegalArgumentException("Defina um S e um E");
        start = s; end = e;
    }

    public int width() { return rows.get(0).length(); }
    public int height() { return rows.size(); }
    public Cell start() { return start; }
    public Cell end() { return end; }
    public char at(Cell cell) { return rows.get(cell.row()).charAt(cell.col()); }
    public int cost(Cell cell) { char c = at(cell); return c >= '1' && c <= '9' ? c - '0' : 1; }
    public List<Cell> neighbors(Cell cell) {
        List<Cell> result = new ArrayList<>();
        for (int[] d : new int[][]{{-1,0},{0,1},{1,0},{0,-1}}) {
            Cell n = new Cell(cell.row() + d[0], cell.col() + d[1]);
            if (n.row() >= 0 && n.row() < height() && n.col() >= 0 && n.col() < width() && at(n) != 'X') result.add(n);
        }
        return result;
    }
}
