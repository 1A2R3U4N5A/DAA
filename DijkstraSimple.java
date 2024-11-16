import java.util.*;
class Node {
    int x, y, cost;
    Node(int x, int y, int cost) {
        this.x = x;
        this.y = y;
        this.cost = cost;
    }
}
public class DijkstraSimple {
    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int[][] grid = new int[4][4];
        for(int i=0;i<4;i++){
           for(int j=0;j<4;j++){
               grid[i][j]=sc.nextInt();
           }
        }
        Node start = new Node(0, 0, grid[0][0]);
        Node end = new Node(2, 2, grid[3][2]);
        int shortestPathCost = dijkstra(grid, start, end);
        System.out.println("Shortest path cost: " + shortestPathCost);
    }
    private static int dijkstra(int[][] grid, Node start, Node end) {
        int rows = grid.length, cols = grid[0].length;
        int[][] distances = new int[rows][cols];
        boolean[][] visited = new boolean[rows][cols];
        for (int[] row : distances) Arrays.fill(row, Integer.MAX_VALUE);
        distances[start.x][start.y] = 0;
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        pq.add(new Node(start.x, start.y, 0));
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            if (current.x == end.x && current.y == end.y) return distances[current.x][current.y];
            if (visited[current.x][current.y]) continue;
            visited[current.x][current.y] = true;
            for (int[] dir : DIRECTIONS) {
                int newX = current.x + dir[0], newY = current.y + dir[1];
                if (newX >= 0 && newX < rows && newY >= 0 && newY < cols) {
                    int newCost = distances[current.x][current.y] + grid[newX][newY];
                    if (newCost < distances[newX][newY]) {
                        distances[newX][newY] = newCost;
                        pq.add(new Node(newX, newY, newCost));
                    }
                }
            }
        }
        return -1; 
    }
}