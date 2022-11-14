import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;;

public class App {
    public static void main(String[] args) throws Exception {
        Scanner s = new Scanner(System.in);
        System.out.println("Enter grid size: ");
        int gridSize = s.nextInt();
        String[][] grid = new String[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                grid[i][j] = "_";
            }
        }
        System.out.println("Enter number of obstacles: ");
        System.out.println("Enter obstaceles: ");
        int obstacleSize = s.nextInt();
        for (int i = 0; i < obstacleSize; i++) {
            int obstacleX = s.nextInt();
            int obstacleY = s.nextInt();
            grid[obstacleX - 1][obstacleY - 1] = "X";
        }
        System.out.println("Enter source: ");
        int sourceX = s.nextInt();
        int sourceY = s.nextInt();
        System.out.println("Enter destination: ");
        grid[sourceX - 1][sourceY - 1] = "S";
        int destanationX = s.nextInt();
        int destanationY = s.nextInt();
        grid[destanationX - 1][destanationY - 1] = "T";
        System.out.println("Initial Grid");
        displayGrid(gridSize, grid);
        leeMaze(gridSize, grid, sourceX, sourceY, destanationX, destanationY);
        backTrack(gridSize, grid, sourceX, sourceY, destanationX, destanationY);
        s.close();
    }

    private static void backTrack(int gridSize, String[][] grid, int sourceX, int sourceY, int destanationX,
            int destanationY) {
        int currentX = destanationX - 1;
        int currentY = destanationY - 1;
        ArrayList<Integer[]> aList = new ArrayList<>();
        aList.add(new Integer[] { destanationX, destanationY });
        int minDistance = Integer.parseInt(grid[currentX][currentY]);
        int minX = 0, minY = 0;
        while (true) {
            if (currentX > 0
                    && !(grid[currentX - 1][currentY].equals("_") || grid[currentX - 1][currentY].equals("T")
                            || grid[currentX - 1][currentY].equals("X"))) {
                if (grid[currentX - 1][currentY].equals("S")) {
                    aList.add(new Integer[] { currentX, currentY + 1 });
                    printPath(aList);
                    return;
                } else if (minDistance > Integer.parseInt(grid[currentX - 1][currentY])) {
                    minDistance = Integer.parseInt(grid[currentX - 1][currentY]);
                    minX = currentX - 1;
                    minY = currentY;
                }
            }
            if (currentX + 1 < gridSize
                    && !(grid[currentX + 1][currentY].equals("_") || grid[currentX + 1][currentY].equals("T")
                            || grid[currentX + 1][currentY].equals("X"))) {
                if (grid[currentX + 1][currentY].equals("S")) {
                    aList.add(new Integer[] { currentX + 2, currentY + 1 });
                    printPath(aList);
                    return;
                } else if (minDistance > Integer.parseInt(grid[currentX + 1][currentY])) {
                    minDistance = Integer.parseInt(grid[currentX + 1][currentY]);
                    minX = currentX + 1;
                    minY = currentY;
                }
            }
            if (currentY > 0
                    && !(grid[currentX][currentY - 1].equals("_") || grid[currentX][currentY - 1].equals("T")
                            || grid[currentX][currentY - 1].equals("X"))) {
                if (grid[currentX][currentY - 1].equals("S")) {
                    aList.add(new Integer[] { currentX + 1, currentY });
                    printPath(aList);
                    return;
                } else if (minDistance > Integer.parseInt(grid[currentX][currentY - 1])) {
                    minDistance = Integer.parseInt(grid[currentX][currentY - 1]);
                    minX = currentX;
                    minY = currentY - 1;
                }
            }
            if (currentY + 1 < gridSize
                    && !(grid[currentX][currentY + 1].equals("_") || grid[currentX][currentY + 1].equals("T")
                            || grid[currentX][currentY + 1].equals("X"))) {
                if (grid[currentX][currentY + 1].equals("S")) {
                    aList.add(new Integer[] { currentX + 1, currentY + 2 });
                    printPath(aList);
                    return;
                } else if (minDistance > Integer.parseInt(grid[currentX][currentY + 1])) {
                    minDistance = Integer.parseInt(grid[currentX][currentY + 1]);
                    minX = currentX;
                    minY = currentY + 1;
                }
            }
            currentX = minX;
            currentY = minY;
            aList.add(new Integer[] { currentX + 1, currentY + 1 });
        }
    }

    private static void printPath(ArrayList<Integer[]> aList) {
        System.out.println("Path taken: ");
        Collections.reverse(aList);
        Iterator<Integer[]> it = aList.iterator();
        Integer[] cell = it.next();
        System.out.print("(" + cell[0] + ", " + cell[1] + ")");
        while (it.hasNext()) {
            cell = it.next();
            System.out.print(" --> " + "(" + cell[0] + ", " + cell[1] + ")");
        }
        System.out.println();
    }

    private static void leeMaze(int gridSize, String[][] grid, int sourceX, int sourceY, int destanationX,
            int destanationY) {
        Queue<Integer[]> queue = new LinkedList<>();
        queue.add(new Integer[] { sourceX - 1, sourceY - 1 });
        int count = 0;
        while (!queue.isEmpty()) {
            Queue<Integer[]> duplicateQueue = new LinkedList<>(queue);
            Iterator<Integer[]> it = duplicateQueue.iterator();
            queue.clear();
            while (it.hasNext()) {
                Integer[] cell = it.next();
                int cellX = cell[0];
                int cellY = cell[1];
                grid[cellX][cellY] = Integer.toString(count);
                if (cellX == destanationX - 1 && cellY == destanationY - 1) {
                    grid[sourceX - 1][sourceY - 1] = "S";
                    grid[cellX][cellY] = "T";
                    System.out.println("Final Grid");
                    displayGrid(gridSize, grid);
                    grid[cellX][cellY] = Integer.toString(count);
                    return;
                }
                if (cellX > 0 && (grid[cellX - 1][cellY].equals("_") || grid[cellX - 1][cellY].equals("T")))
                    queue.add(new Integer[] { cellX - 1, cellY });
                if (cellX + 1 < gridSize && (grid[cellX + 1][cellY].equals("_") || grid[cellX + 1][cellY].equals("T")))
                    queue.add(new Integer[] { cellX + 1, cellY });
                if (cellY > 0 && (grid[cellX][cellY - 1].equals("_") || grid[cellX][cellY - 1].equals("T")))
                    queue.add(new Integer[] { cellX, cellY - 1 });
                if (cellY + 1 < gridSize && (grid[cellX][cellY + 1].equals("_") || grid[cellX][cellY + 1].equals("T")))
                    queue.add(new Integer[] { cellX, cellY + 1 });
            }
            count++;
        }
    }

    private static void displayGrid(int gridSize, String[][] grid) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                System.out.print(grid[i][j] + "\t");
            }
            System.out.println();
        }
    }
}
