import maze.domain.*;
import maze.search.Search;
import java.util.*;

public class SearchTest {
    static void check(boolean condition) { if (!condition) throw new AssertionError(); }
    static void invalid(List<String> input) {
        try { new Maze(input); throw new AssertionError("Accepted invalid maze"); }
        catch (IllegalArgumentException expected) {}
    }
    public static void main(String[] args) {
        invalid(List.of()); invalid(List.of("S..", "E")); invalid(List.of("SSE")); invalid(List.of("...")); invalid(List.of("S?E"));
        Maze weighted = new Maze(List.of("S9E", "..."));
        check(Search.solve(weighted, Search.Algorithm.BFS).cost() == 10);
        check(Search.solve(weighted, Search.Algorithm.DIJKSTRA).cost() == 4);
        check(Search.solve(weighted, Search.Algorithm.ASTAR).cost() == 4);
        for (var algorithm : Search.Algorithm.values()) {
            check(!Search.solve(new Maze(List.of("SXE")), algorithm).found());
            check(Search.solve(new Maze(List.of("SE")), algorithm).cost() == 1);
        }
        Random random = new Random(2026);
        for (int test = 0; test < 300; test++) {
            List<String> rows = new ArrayList<>();
            for (int r=0;r<8;r++) {
                StringBuilder row = new StringBuilder();
                for(int c=0;c<8;c++) row.append(random.nextInt(5)==0 ? 'X' : (char)('1'+random.nextInt(9)));
                rows.add(row.toString());
            }
            rows.set(0, 'S'+rows.get(0).substring(1)); rows.set(7, rows.get(7).substring(0,7)+'E');
            Maze maze = new Maze(rows);
            var d = Search.solve(maze, Search.Algorithm.DIJKSTRA);
            var a = Search.solve(maze, Search.Algorithm.ASTAR);
            check(d.cost()==a.cost());
            if (a.found()) {
                check(a.path().get(0).equals(maze.start())); check(a.path().get(a.path().size()-1).equals(maze.end()));
                for(int i=1;i<a.path().size();i++) check(maze.neighbors(a.path().get(i-1)).contains(a.path().get(i)));
            }
        }
        System.out.println("Search tests passed: validation, weighted/unreachable paths, 300 seeded comparisons");
    }
}
