import java.util.*;
class Node {
    int x, y;
    int distance;
    Node parent;

    Node(int x, int y, int distance, Node parent) {
        this.x = x;
        this.y = y;
        this.distance = distance;
        this.parent = parent;
    }
}
class Graph {
    private final int[][] grid;
    private final int rows;
    private final int cols;
    public Graph(int[][] grid) {
        this.grid = grid;
        this.rows = grid.length;
        this.cols = grid[0].length;
    }
    public List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<>();
        int[] dRow = {-1, 1, 0, 0};
        int[] dCol = {0, 0, -1, 1};
        for (int i = 0; i < 4; i++) {
            int newRow = node.x + dRow[i];
            int newCol = node.y + dCol[i];

            if (isValid(newRow, newCol)) {
                neighbors.add(new Node(newRow, newCol, grid[newRow][newCol], node));
            }
        }
        return neighbors;
    }
    private boolean isValid(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && grid[row][col] != Integer.MAX_VALUE;
    }
    public List<Node> dijkstra(int startX, int startY, int endX, int endY) {
        PriorityQueue<Node> pq = new PriorityQueue<>(Comparator.comparingInt(n -> n.distance));
        boolean[][] visited = new boolean[rows][cols];
        pq.add(new Node(startX, startY, grid[startX][startY], null));
        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int currentX = current.x;
            int currentY = current.y;
            if (visited[currentX][currentY]) continue;
            visited[currentX][currentY] = true;
            if (currentX == endX && currentY == endY) {
                return reconstructPath(current);
            }
            for (Node neighbor : getNeighbors(current)) {
                if (!visited[neighbor.x][neighbor.y]) {
                    pq.add(new Node(neighbor.x, neighbor.y, current.distance + neighbor.distance, current));
                }
            }
        }
        return Collections.emptyList();
    }

    private List<Node> reconstructPath(Node endNode) {
        List<Node> path = new ArrayList<>();
        for (Node node = endNode; node != null; node = node.parent) {
            path.add(node);
        }
        Collections.reverse(path); 
        return path;
    }
}

class DijkstraSimple {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of rows: ");
        int rows = scanner.nextInt();
        System.out.print("Enter number of columns: ");
        int cols = scanner.nextInt();
        int[][] grid = new int[rows][cols];
        System.out.println("Enter the grid:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = scanner.nextInt();
            }
        }
        System.out.print("Enter start point (row and column): ");
        int startX = scanner.nextInt();
        int startY = scanner.nextInt();
        System.out.print("Enter end point (row and column): ");
        int endX = scanner.nextInt();
        int endY = scanner.nextInt();
        Graph graph = new Graph(grid);
        List<Node> path = graph.dijkstra(startX, startY, endX, endY);
        if (!path.isEmpty()) {
            System.out.println("Path found:");
            int totalCost = 0;
            for (Node node : path) {
                System.out.println("[" + node.x + ", " + node.y + "] with cost: " + grid[node.x][node.y]);
                totalCost += grid[node.x][node.y];
            }
            System.out.println("Minimum cost of the path : " + totalCost);
        } else {
            System.out.println("No path found.");
        }

        scanner.close();
    }
}
