package Submission_In_Java.Lab1.Question1;

import java.io.*;

public class Question1 {
    private static final PrintWriter pw;
    private static final BufferedReader br;

    static {
        try {
            pw = new PrintWriter(new BufferedOutputStream(new FileOutputStream("/home/denuvo-drm/Academic/Extra/Practice/src/Submission_In_Java/Lab1/Question1/output.txt")));
            br = new BufferedReader(new InputStreamReader(new FileInputStream("/home/denuvo-drm/Academic/Extra/Practice/src/Submission_In_Java/Lab1/Question1/input.txt")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int SIZE = (int) 1e2 + 340;
    private static final boolean[][] visited = new boolean[SIZE][SIZE];
    private static final char[][] graph = new char[SIZE][];
    private static int m = 0, n = 0;
    private static int curSize = 0;
    private static int maxSize = Integer.MIN_VALUE;

    public static void main(String[] args) throws IOException {
        // Take the input
        String inputLine = br.readLine();
        n = inputLine != null ? inputLine.replace(" ", "").length() : 0;
        while (inputLine != null) {
            graph[m] = inputLine.replace(" ", "").toCharArray();
            m++;
            inputLine = br.readLine();
        }
        // Done taking input ...
        br.close();
        // Time to dfs

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                pw.print(graph[i][j]);
            }
            pw.println();
        }

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (!visited[i][j] && graph[i][j] == 'Y') {
                    dfs(i, j);
                    maxSize = Integer.max(maxSize, curSize);
                    curSize = 0;
                }
            }
        }
        pw.println(maxSize);
        pw.close();
    }

    private static void dfs(int i, int j) {
        visited[i][j] = true;
        curSize++;
        // Time to check and call
        if (i > 0 && j > 0 && !visited[i - 1][j - 1] && graph[i - 1][j - 1] == 'Y') dfs(i - 1, j - 1);
        if (i > 0 && !visited[i - 1][j] && graph[i - 1][j] == 'Y') dfs(i - 1, j);
        if (i > 0 && j < n - 1 && !visited[i - 1][j + 1] && graph[i - 1][j + 1] == 'Y') dfs(i - 1, j + 1);
        if (j > 0 && !visited[i][j - 1] && graph[i][j - 1] == 'Y') dfs(i, j - 1);
        if (j < n - 1 && !visited[i][j + 1] && graph[i][j + 1] == 'Y') dfs(i, j + 1);
        if (i < m - 1 && j > 0 && !visited[i + 1][j - 1] && graph[i + 1][j - 1] == 'Y') dfs(i + 1, j - 1);
        if (i < m - 1 && !visited[i + 1][j] && graph[i + 1][j] == 'Y') dfs(i + 1, j);
        if (i < m - 1 && j < n - 1 && !visited[i + 1][j + 1] && graph[i + 1][j + 1] == 'Y') dfs(i + 1, j + 1);
    }
}
