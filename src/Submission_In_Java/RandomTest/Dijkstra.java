package Submission_In_Java.RandomTest;

import java.io.*;
import java.util.*;

public class Dijkstra {
    private static final PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final int SIZE = (int) 1e5 + 10;
    private static final ArrayList<ArrayList<Pair>> graph = new ArrayList<>();
    private static final int[] distance = new int[SIZE];
    private static final int[] parent = new int[SIZE];

    public static void main(String[] args) throws IOException {
        StringTokenizer tk = new StringTokenizer(br.readLine());
        int v = Integer.parseInt(tk.nextToken()), e = Integer.parseInt(tk.nextToken());

        for (int i = 0; i < v + 1; i++) {
            graph.add(new ArrayList<>());
        }

        for (int i = 1; i <= e; i++) {
            tk = new StringTokenizer(br.readLine());
            int n1 = Integer.parseInt(tk.nextToken()), n2 = Integer.parseInt(tk.nextToken()),
                    cost = Integer.parseInt(tk.nextToken());
            graph.get(n1).add(new Pair(n2, cost));
            graph.get(n2).add(new Pair(n1, cost));
        }

        dijkstra(1);

        for (int i = 1; i <= v; i++) {
            pw.println("distance of " + i + " is " + distance[i]);
        }

        int cur = v;
        ArrayList<Integer> path = new ArrayList<>();

        while (cur != 1) {
            path.add(cur);
            cur = parent[cur];
        }
        path.add(1);
        Collections.reverse(path);
        for (int i = 0; i < path.size() - 1; i++) {
            pw.print(path.get(i) + " -> ");
        }
        pw.println(path.get(path.size() - 1));
        pw.close(); // Closing
    }

    private static void dijkstra(@SuppressWarnings("SameParameterValue") int source) {
        Arrays.fill(distance, (int) 1e9);
        Arrays.fill(parent, -1);
        distance[source] = 0;
        PriorityQueue<Pair> pairPriorityQueue = new PriorityQueue<>(Comparator.comparingInt(o -> o.second));
        pairPriorityQueue.add(new Pair(source, 0));
        while (!pairPriorityQueue.isEmpty()) {
            int curNode = pairPriorityQueue.peek().first;
            int cost = pairPriorityQueue.peek().second;
            pairPriorityQueue.poll();
            if (distance[curNode] != cost) continue;
            for (var k : graph.get(curNode)) {
                if (distance[k.first] > cost + k.second) {
                    distance[k.first] = cost + k.second;
                    parent[k.first] = curNode;
                    pairPriorityQueue.add(new Pair(k.first, distance[k.first]));
                }
            }
        }
    }

    private record Pair(int first, int second) {

    }
}
