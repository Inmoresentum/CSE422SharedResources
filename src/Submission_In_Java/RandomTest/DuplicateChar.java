package Submission_In_Java.RandomTest;

import java.io.*;

public class DuplicateChar {
    private static final PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        String input = br.readLine();
        long checker = 0;
        boolean contains = false;
        for (var k : input.toCharArray()) {
            int value = k - 'A';
            if ((checker & (1L << value)) > 0) {
                pw.println("Yes it contains duplicate chars");
                contains = true;
                break;
            }
            checker |= (1L << value);
        }
        pw.print(contains ? "" : "No it does not contains any duplicate chars\n");
        pw.close();
    }
}
