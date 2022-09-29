package Submission_In_Java.RandomTest;

import java.io.*;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class BFS {
    private static final PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final ArrayList<ArrayList<Integer>> graph = new ArrayList<>();
    private static final int SIZE = (int) 1e5 + 3;
    private static final boolean[] visited = new boolean[SIZE];

    public static void main(String[] args) throws IOException {
        StringTokenizer tk = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(tk.nextToken()), e = Integer.parseInt(tk.nextToken());
        // Initialize ArrayList
        for (int i = 0; i < SIZE; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 0; i < e; i++) {
            tk = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(tk.nextToken()), b = Integer.parseInt(tk.nextToken());
            graph.get(a).add(b);
            graph.get(b).add(a);
        }

        br.close();

        // Done with the input of the graph

        for (int i = 1; i < v + 1; i++) {
            if (!visited[i]) {
                pw.println("Calling bfs at " + i);
                bfs(i);
            }
        }
        pw.close();
    }

    private static void bfs(int start) {
        visited[start] = true;
        ArrayDeque<Integer> deque = new ArrayDeque<>();
        deque.addLast(start);
        while (!deque.isEmpty()) {
            int curVertex = deque.getFirst();
            pw.println("BFS at " + curVertex);
            for (var k : graph.get(curVertex)) {
                if (!visited[k]) {
                    visited[k] = true;
                    deque.addLast(k);
                }
            }
            deque.removeFirst();
        }
    }
}
