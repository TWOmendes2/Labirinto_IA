package maze.search;

import maze.domain.Cell;
import maze.domain.Maze;
import java.util.*;

public final class Search {
    public enum Algorithm { BFS, DIJKSTRA, ASTAR }
    public record Result(List<Cell> path, int cost, int expanded) {
        public Result { path = List.copyOf(path); }
        public boolean found() { return !path.isEmpty(); }
    }
    private record Entry(Cell cell, int distance, int priority, long order) {}

    public static Result solve(Maze maze, Algorithm algorithm) {
        Objects.requireNonNull(algorithm);
        Map<Cell, Cell> parents = new HashMap<>();
        Map<Cell, Integer> distance = new HashMap<>();
        Queue<Entry> frontier = algorithm == Algorithm.BFS ? new ArrayDeque<>()
            : new PriorityQueue<>(Comparator.comparingInt(Entry::priority).thenComparingLong(Entry::order));
        long order = 0;
        int expanded = 0;
        distance.put(maze.start(), 0);
        frontier.add(new Entry(maze.start(), 0, 0, order++));
        while (!frontier.isEmpty()) {
            Entry current = frontier.remove();
            if (current.distance() != distance.get(current.cell())) continue;
            expanded++;
            if (current.cell().equals(maze.end())) {
                LinkedList<Cell> path = new LinkedList<>();
                for (Cell c = current.cell(); c != null; c = parents.get(c)) path.addFirst(c);
                int cost = path.stream().skip(1).mapToInt(maze::cost).sum();
                return new Result(path, cost, expanded);
            }
            for (Cell next : maze.neighbors(current.cell())) {
                int candidate = current.distance() + (algorithm == Algorithm.BFS ? 1 : maze.cost(next));
                if (candidate >= distance.getOrDefault(next, Integer.MAX_VALUE)) continue;
                distance.put(next, candidate);
                parents.put(next, current.cell());
                int heuristic = algorithm == Algorithm.ASTAR ? Math.abs(next.row() - maze.end().row()) + Math.abs(next.col() - maze.end().col()) : 0;
                frontier.add(new Entry(next, candidate, candidate + heuristic, order++));
            }
        }
        return new Result(List.of(), -1, expanded);
    }
}
