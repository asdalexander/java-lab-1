package labTwo;

import java.util.Random;
import java.util.Scanner;

public class labTwo {
    private static final String[] SHORT_NAMES =
            // Array to store single-letter amino acids
            { "A", "R", "N", "D", "C", "Q", "E", "G", "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V" };

    private static final String[] FULL_NAMES =
            // Array to store full amino acid names
            {"alanine", "arginine", "asparagine", "aspartic acid", "cysteine", "glutamine", "glutamic acid", "glycine", "histidine",
             "isoleucine", "leucine", "lysine", "methionine", "phenylalanine", "proline", "serine", "threonine","tryptophan", "tyrosine", "valine"};

    private static long startTime; // Time at which the quiz starts

    private static boolean timeLimitMet() {
        long timeSpent = System.currentTimeMillis() - startTime;
        // Calculate how many millisecs have passed since the start of the quiz
        return timeSpent < 30000;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); // To accept user input
        Random random = new Random(); // To generates a random number to select a random amino acid
        int score = 0; // To store the running score
        boolean continueQuiz = true; // To determine whether the quiz should be stopped or if it should continue

        startTime = System.currentTimeMillis(); // Assigns the current system time in milliseconds to the startTime var

        while (continueQuiz) { // While true... continue
            int index = random.nextInt(SHORT_NAMES.length); // Assigns a random integer between 0 and the length of the SHORT_NAMES array, to select a random amino acid

            System.out.println(FULL_NAMES[index]); // Prints the FULL_NAME associated with the randomly generated index

            String answer = scanner.nextLine().toUpperCase().trim(); // Collect and format the user's response (trimming, same case)
            String correctAnswer = SHORT_NAMES[index]; // Identify the actual correct answer using the same random index

            if (answer.charAt(0) == correctAnswer.charAt(0) && timeLimitMet()) {
                // Compare first char of user input to correct answer and add 1 to score and continue quiz if it matches and is within 30 seconds of quiz start
                score++;
                System.out.println("Correct, score: " + score);
            } else if (answer.charAt(0) != correctAnswer.charAt(0) && timeLimitMet()) {
                // Stop quiz if the user input does not match the correct answer within 30 seconds of quiz start
                System.out.println("Incorrect, final score: " + score);
                continueQuiz = false;
            } else if (timeLimitMet() == false ) {
                // Stop quiz if answer is later than 30 seconds of quiz start
                System.out.println("Out of time, final score: " + score);
                continueQuiz = false;
            }
        }
        scanner.close();
    }
}