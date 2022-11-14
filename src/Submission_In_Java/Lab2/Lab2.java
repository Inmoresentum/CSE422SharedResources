package Submission_In_Java.Lab2;

import java.io.*;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * <h1 style="color:orange;">
 * Todo: Please use JDK version 18.0.2 or higher or else code may not compile properly!
 * </h1>
 */

public class Lab2 {
    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static final PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out)
            , true);
    private static final ArrayList<Integer> points = new ArrayList<>();
    private static final ArrayList<Integer> listOfPointsAfterShuffle = new ArrayList<>();
    private static int minLimit, maxLimit, totalPointsToWin, totalNumberOfShuffle;

    public static void main(String[] args) {
        initialize();
        // Task 1
        var achievedPoint = alphaBetaPunning(0, 0, true, points,
                Integer.MIN_VALUE, Integer.MAX_VALUE);
        pw.println("Generated 8 random points between the minimum and maximum point");
        pw.println("limits: " + points);
        pw.println("Total Points to win: " + totalPointsToWin);
        pw.println("Achieved point by applying alpha-beta pruning " + achievedPoint);
        pw.println(achievedPoint < totalPointsToWin ? "The Winner is Megatron"
                : "The winner is Optimus Prime");
        // Task 2
        shuffle();
        pw.println("\nAfter shuffle:");
        pw.println("List of all points values from each shuffle: " + listOfPointsAfterShuffle);
        var stat = getStat();
        int totalWinCount = stat.totalWinCount, maximumValue = stat.maximumValue;
        pw.println("The maximum value of all shuffles: " + maximumValue);
        pw.println("Won " + totalWinCount + " times out of " + totalNumberOfShuffle + " number of shuffles");
        pw.close();
    }

    private static void initialize() {
        try {
            takeInputAndSetValues();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        createRandomListOfPoints();
    }

    private static void takeInputAndSetValues() throws IOException {
        pw.println("Please enter your student ID");
        var studentID = new StringTokenizer(br.readLine()).nextToken()
                .replace("0", "8");
        br.close();
        if (studentID.length() != 8) throw new RuntimeException("Student ID must need to be of length 8");
        minLimit = Integer.parseInt(String.valueOf(studentID.charAt(4)));
        maxLimit = (int) Math.round(Integer.parseInt(new StringBuilder(studentID
                .substring(6)).reverse().toString()) * 1.5);
        totalPointsToWin = Integer.parseInt(new StringBuilder(studentID
                .substring(6)).reverse().toString());
        totalNumberOfShuffle = Integer.parseInt(String.valueOf(studentID.charAt(3)));
    }


    private static void createRandomListOfPoints() {
        for (int i = 0; i < 8; i++) {
            points.add(ThreadLocalRandom.current().nextInt(minLimit, maxLimit + 1));
        }
    }

    private static void shuffle() {
        for (int i = 0; i < totalNumberOfShuffle; i++) {
            shuffleList();
            listOfPointsAfterShuffle.add(alphaBetaPunning(0, 0, true,
                    points, Integer.MIN_VALUE, Integer.MAX_VALUE));
        }
    }

    private static void shuffleList() {
        Collections.shuffle(points);
    }

    private static Pair getStat() {
        int winCount = 0;
        int maxPoint = Integer.MIN_VALUE;
        for (var points : listOfPointsAfterShuffle) {
            if (points >= totalPointsToWin) winCount++;
            maxPoint = Integer.max(maxPoint, points);
        }
        return new Pair(winCount, maxPoint);
    }

    private static int alphaBetaPunning(int curDept, int nodeIndex, boolean isMaximizingPlayer,
                                        ArrayList<Integer> points, int alpha, int beta) {
        if (curDept == 3) return points.get(nodeIndex);
        if (isMaximizingPlayer) {
            int maxPoints = Integer.MIN_VALUE;
            for (int i = 0; i < 2; i++) {
                int curValue = alphaBetaPunning(curDept + 1, nodeIndex * 2 + i,
                        false, points, alpha, beta);
                maxPoints = Integer.max(maxPoints, curValue);
                alpha = Integer.max(alpha, maxPoints);
                if (beta <= alpha) break;
            }
            return maxPoints;
        } else {
            int minPoints = Integer.MAX_VALUE;

            for (int i = 0; i < 2; i++) {
                int curValue = alphaBetaPunning(curDept + 1, nodeIndex * 2 + i,
                        true, points, alpha, beta);
                minPoints = Integer.min(curValue, minPoints);

                beta = Integer.min(curValue, beta);

                if (beta <= alpha) break;
            }
            return minPoints;
        }
    }

    private record Pair(int totalWinCount, int maximumValue) {
    }
}
