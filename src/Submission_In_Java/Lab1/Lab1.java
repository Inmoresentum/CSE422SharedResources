package Submission_In_Java.Lab1;

import java.io.*;
import java.util.*;

public class Lab1 {
    private static final PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static BufferedReader br;

    static {
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream("~ path to your input.txt")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final HashMap<String, Node> graph = new HashMap<>();
    private static final ArrayList<String> inputLines = new ArrayList<>();
    private static final HashMap<String, Integer> distance = new HashMap<>();
    private static final HashMap<String, String> parent = new HashMap<>();

    public static void main(String[] args) throws IOException {
        // Take input file
        takeInputFromTxtFile();
        // Initialize graph
        initializeGraph();
        // Build the graph
        buildGraph();
        // Need to take input from the console
        // So re-assigning it to the std i/o
        br = new BufferedReader(new InputStreamReader(System.in));
        pw.print("Start Node: ");
        pw.flush();
        String source = new StringTokenizer(br.readLine()).nextToken();
        pw.print("Destination: ");
        pw.flush();
        String destination = new StringTokenizer(br.readLine()).nextToken();
        br.close(); // Closing the input stream
        // Need to write A*
        aStarSearch(source, destination);
        if (distance.get(destination) == Integer.MAX_VALUE) {
            pw.println("No path found");
            System.exit(0);
        }
        pw.println(distance.get(destination));
        ArrayList<String> path = new ArrayList<>();
        String curCity = destination;
        path.add(curCity);
        while (!curCity.equals(source)) {
            curCity = parent.get(curCity);
            path.add(curCity);
        }
        //noinspection DuplicatedCode
        Collections.reverse(path);
        for (int i = 0; i < path.size() - 1; i++) {
            pw.print(path.get(i) + " -> ");
        }
        pw.println(path.get(path.size() - 1));
        pw.close();
    }

    private static void aStarSearch(String source, String destination) {
        distance.put(source, 0);
        var sourceFinalCost = graph.get(source).heuristicValue;
        PriorityQueue<StringIntIntPair> pq = new PriorityQueue<>(Comparator.comparingInt(o -> o.third));
        pq.add(new StringIntIntPair(source, 0, sourceFinalCost));
        while (!pq.isEmpty()) {
            var curCity = pq.peek().first;
            var curDistance = pq.peek().second;
            pq.poll();
            if (curCity.equals(destination)) break;
            if (distance.get(curCity) != curDistance) continue;
            for (var k : graph.get(curCity).adjNodes) {
                if (distance.get(k.first) > curDistance + k.second) {
                    distance.put(k.first, curDistance + k.second);
                    parent.put(k.first, curCity);
                    var curFinalCost = distance.get(k.first) + graph.get(k.first).heuristicValue;
                    pq.add(new StringIntIntPair(k.first, distance.get(k.first), curFinalCost));
                }
            }
        }
    }


    private static void buildGraph() {
        for (var l : inputLines) {
            StringTokenizer tk = new StringTokenizer(l);
            String cityName = tk.nextToken();
            var curNode = graph.get(cityName);
            curNode.heuristicValue = Integer.parseInt(tk.nextToken());
            while (tk.countTokens() > 0) {
                String adjCity = tk.nextToken();
                int distance = Integer.parseInt(tk.nextToken());
                curNode.adjNodes.add(new StringIntPair(adjCity, distance));
                graph.get(adjCity).adjNodes.add(new StringIntPair(cityName, distance));
            }
        }
    }

    private static void initializeGraph() {
        for (var l : inputLines) {
            StringTokenizer tk = new StringTokenizer(l);
            var cityName = tk.nextToken();
            graph.put(cityName, new Node());
            distance.put(cityName, (int) 1e9);
            parent.put(cityName, null);
        }
    }

    private static void takeInputFromTxtFile() throws IOException {
        String inputLine;
        while ((inputLine = br.readLine()) != null) {
            inputLines.add(inputLine);
        }
    }

    private record StringIntPair(String first, int second) {
    }

    private record StringIntIntPair(String first, int second, int third) {
    }

    private static class Node {
        private int heuristicValue;
        private final List<StringIntPair> adjNodes = new ArrayList<>();
    }
}
