package Submission_In_Java.Practice.Question2;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Question2 {
    private static final PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out), true);
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static char[][] graph;
    private static int[][] cost;
    private static int m, n;

    // Have not yet implemented the file input and output. Might want to do that when you can.
    public static void main(String[] args) throws IOException {
        m = Integer.parseInt(new StringTokenizer(br.readLine()).nextToken());
        n = Integer.parseInt(new StringTokenizer(br.readLine()).nextToken());
        graph = new char[m][];
        cost = new int[m][n];
        for (int i = 0; i < m; i++) {
            graph[i] = br.readLine().replace(" ", "").toCharArray();
            Arrays.fill(cost[i], Integer.MAX_VALUE);
        }
        br.close();
        // Time to print the things!!
        for (var s : graph) {
            pw.println(Arrays.toString(s));
        }
        // Time to do a BFS from each Alien Position
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (graph[i][j] == 'A') {
                    bfs(i, j);
                }
            }
        }

        for (var k : cost) {
            pw.println(Arrays.toString(k));
        }
        // Time to check -- nice!!
        int minTime = Integer.MIN_VALUE;
        int survived = 0;
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (graph[i][j] == 'H' && cost[i][j] != Integer.MAX_VALUE) {
                    minTime = Integer.max(minTime, cost[i][j]);
                }
                if (graph[i][j] == 'H' && cost[i][j] == Integer.MAX_VALUE) {
                    survived++;
                }
            }
        }
        pw.println("Min Time " + minTime);
        pw.println(survived == 0 ? "No one survived!" : survived + " survived");
        pw.close();
    }

    private static void bfs(int first, int second) {
        boolean[][] visited = new boolean[m][n];
        visited[first][second] = true;
        ArrayDeque<Pair> deque = new ArrayDeque<>();
        deque.addLast(new Pair(first, second));
        int curCost = 1;
        while (!deque.isEmpty()) {
            int i = deque.getFirst().first, j = deque.getFirst().second;
            boolean foundNewHuman = false;
            if (i > 0 && !visited[i - 1][j] && graph[i - 1][j] == 'H') {
                visited[i - 1][j] = true;
                cost[i - 1][j] = Integer.min(cost[i - 1][j], curCost);
                deque.addLast(new Pair(i - 1, j));
                foundNewHuman = true;
            }

            if (i < m - 1 && !visited[i + 1][j] && graph[i + 1][j] == 'H') {
                visited[i + 1][j] = true;
                cost[i + 1][j] = Integer.min(cost[i + 1][j], curCost);
                deque.addLast(new Pair(i + 1, j));
                foundNewHuman = true;
            }

            if (j > 0 && !visited[i][j - 1] && graph[i][j - 1] == 'H') {
                visited[i][j - 1] = true;
                cost[i][j - 1] = Integer.min(cost[i][j - 1], curCost);
                deque.addLast(new Pair(i, j - 1));
                foundNewHuman = true;
            }

            if (j < n - 1 && !visited[i][j + 1] && graph[i][j + 1] == 'H') {
                visited[i][j + 1] = true;
                cost[i][j + 1] = Integer.min(cost[i][j + 1], curCost);
                deque.addLast(new Pair(i, j + 1));
                foundNewHuman = true;
            }
            if (foundNewHuman) {
                curCost++;
            }
            deque.removeFirst();
        }
    }

    private record Pair(int first, int second) {

    }
}
