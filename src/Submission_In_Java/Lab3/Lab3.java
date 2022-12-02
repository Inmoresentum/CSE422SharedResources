package Submission_In_Java.Lab3;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.ThreadLocalRandom;

public class Lab3 {
    private static int NUMBER_OF_BATSMAN;
    private static int TARGET_SCORE;
    private static final ArrayList<Pair> listOfBatsmen = new ArrayList<>();
    private static final ArrayList<ArrayList<Integer>> population = new ArrayList<>();
    private static final int MAX_NUMBER_OF_ITERATION = (int) 1e5;
    private static final int MUTATION_THRESHOLD = 78;

    public static void main(String[] args) throws IOException {
        geneticAlgorithm();
    }

    private static void createInitialPopulation(int size) {
        for (int i = 0; i < size; i++) {
            population.add(new ArrayList<>());
            for (int j = 0; j < NUMBER_OF_BATSMAN; j++) {
                int randomNumber = ThreadLocalRandom.current().nextInt(0, 101);
                if (randomNumber > 50) population.get(i).add(1);
                else population.get(i).add(0);
            }
        }
    }

    private static int calculateFitness(ArrayList<Integer> currentConfiguration) {
        int totalAverageRun = 0;
        for (int i = 0; i < NUMBER_OF_BATSMAN; i++) {
            if (currentConfiguration.get(i) == 1)
                totalAverageRun += listOfBatsmen.get(i).averageRun;
        }
        if (totalAverageRun > TARGET_SCORE) return 0;
        return (int) ((totalAverageRun / (double) TARGET_SCORE) * 100);
    }

    private static ArrayList<Integer> selectTwoParent(ArrayList<Integer> fitnessValues) {
        ArrayList<Integer> selectedTwoParent = new ArrayList<>();
        int i = 0;
        while (i < 2) {
            int firstRandomParent = ThreadLocalRandom.current().nextInt(0, population.size() - 1);
            int secondRandomParent = ThreadLocalRandom.current().nextInt(0, population.size() - 1);
            if (firstRandomParent == secondRandomParent) continue;
            if (fitnessValues.get(firstRandomParent) >= fitnessValues.get(secondRandomParent) &&
                    !selectedTwoParent.contains(firstRandomParent)) {
                i++;
                selectedTwoParent.add(firstRandomParent);
            } else if (!selectedTwoParent.contains(secondRandomParent)) {
                selectedTwoParent.add(secondRandomParent);
                i++;
            }
        }
        return selectedTwoParent;
    }

    private static void crossOver(ArrayList<Integer> firstParent, ArrayList<Integer> secondParent) {
        int randomCrossOverIndex = ThreadLocalRandom.current().nextInt(1, NUMBER_OF_BATSMAN);
        swapValuesOfList(firstParent, secondParent, randomCrossOverIndex);
    }

    private static void swapValuesOfList(ArrayList<Integer> firstParent, ArrayList<Integer> secondParent, int randomCrossOverIndex) {
        ArrayList<Integer> temp = new ArrayList<>();
        for (int i = randomCrossOverIndex; i < NUMBER_OF_BATSMAN; i++) {
            temp.add(firstParent.get(i));
        }
        for (int i = randomCrossOverIndex; i < NUMBER_OF_BATSMAN; i++) {
            firstParent.set(i, secondParent.get(i));
        }
        for (int i = randomCrossOverIndex; i < NUMBER_OF_BATSMAN; i++) {
            secondParent.set(i, temp.get(i - randomCrossOverIndex));
        }
    }


    private static void mutation(ArrayList<Integer> child) {
        int probabilityOfMutation = ThreadLocalRandom.current().nextInt(0, 101);
        if (probabilityOfMutation > MUTATION_THRESHOLD) {
            int randomIndex = ThreadLocalRandom.current().nextInt(0, NUMBER_OF_BATSMAN);
            child.set(randomIndex, child.get(randomIndex) == 1 ? 0 : 1);
        }
    }

    private static void takeInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tk = new StringTokenizer(br.readLine());
        NUMBER_OF_BATSMAN = Integer.parseInt(tk.nextToken());
        TARGET_SCORE = Integer.parseInt(tk.nextToken());

        for (int i = 0; i < NUMBER_OF_BATSMAN; i++) {
            tk = new StringTokenizer(br.readLine());
            listOfBatsmen.add(new Pair(tk.nextToken(), Integer.parseInt(tk.nextToken())));
        }
    }

    private static void geneticAlgorithm() throws IOException {
        takeInput();
        int initialPopulationSize = NUMBER_OF_BATSMAN % 2 == 1 ? (NUMBER_OF_BATSMAN * NUMBER_OF_BATSMAN) + 1 :
                (NUMBER_OF_BATSMAN * NUMBER_OF_BATSMAN) + 2;
        createInitialPopulation(initialPopulationSize);
        ArrayList<Integer> listOfFitnessValues = new ArrayList<>();
        for (int i = 0; i < population.size(); i++) {
            listOfFitnessValues.add(-1);
        }
        PrintWriter pw = new PrintWriter(new BufferedOutputStream(System.out));
        pw.print("[");

        for (int i = 0; i < listOfBatsmen.size() - 1; i++) {
            pw.print("'" + listOfBatsmen.get(i).bastsManName + "', ");
        }
        pw.println("'" + listOfBatsmen.get(listOfBatsmen.size() - 1).bastsManName + "']");
        boolean foundSolution = false;
        Outer:
        for (int i = 0; i < MAX_NUMBER_OF_ITERATION; i++) {
            for (int j = 0; j < population.size(); j++) {
                listOfFitnessValues.set(j, calculateFitness(population.get(j)));
                if (listOfFitnessValues.get(j) == 100) {
                    pw.println(population.get(j).toString().replace("[", "")
                            .replace("]", "").replace(",", ""));
                    foundSolution = true;
                    break Outer;
                }
            }
            for (int j = 0; j < population.size() / 2; j++) {
                var selectedParents = selectTwoParent(listOfFitnessValues);
                crossOver(population.get(selectedParents.get(0)), population.get(selectedParents.get(1)));
                // Doing mutation
                mutation(population.get(selectedParents.get(0)));
                mutation(population.get(selectedParents.get(1)));
            }
        }
        if (!foundSolution) pw.println(-1);
        pw.close();
    }

    private record Pair(String bastsManName, int averageRun) {
    }
}
