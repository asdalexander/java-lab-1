package labOne;

import java.util.Random;

public class labOne
{
    // Variables to store probabilities of generating each nucleotide
    public static double P_A=0.12;
    public static double P_T=0.38;
    public static double P_C=0.39;
    public static double P_G=0.11;

    // Variables to store size of k-mer and quantity of k-mers to generate
    public static int KMER_SIZE=3;
    public static int KMER_QUANTITY=1000;

    // Validate that nucleotide frequency probabilities add up to 1. This is necessary to map a random number to a probability space of 1.
    public static void validateProbability()
    {
        System.out.println("Checking probability of nucleotide frequency totals 100%: ");
        if (P_A + P_T + P_G + P_C == 1)
            System.out.println("PASS, generating k-mers...\n");
        else
            System.out.println("FAIL, please make sure nucleotide probabilities sum to 1.\n");
    }


    public static String generateSingleKmer()
    /*
    Randomly pull from available nucleotides: A,T,C,G based on the provided probabilities and generate k-mers of provided size.
    To pull a random nucleotide we generate a random number and match the number to the probability space each nucleotide occupies.
     */
    {
        Random random = new Random();
        String kmer = "";

        for (int i = 0; i < KMER_SIZE; i++) {
            double rand = random.nextDouble();
            if (rand < P_A) {
                kmer = kmer + "A";
            } else if (rand < P_A + P_T) {
                kmer = kmer + "T";
            } else if (rand < P_A + P_T + P_C) {
                kmer = kmer + "C";
            } else if (rand < P_A + P_T + P_C + P_G) {
                kmer = kmer + "G";
            }
        }
        return kmer.toString(); // using return instead of System.out.println() to pass result to temporary variable in main method
    }

    public static void generateAllKmers()
    // Print k-mers to terminal and check whether they meet the stated condition.
    {
        String current_kmer = "";
        int match_count = 0;

        for (int x = 0; x < KMER_QUANTITY; x++)
        {
            current_kmer = generateSingleKmer();
            if (current_kmer.equals("AAA")) { // Does the k-mer match the string?
                match_count += 1;
                System.out.println(current_kmer + ": Condition match!");
            }
            else
                System.out.println(current_kmer);
        }
        System.out.println("\n Total Condition Matches: " + match_count);
    }

    public static void main(String[] args)
    {
        System.out.println("\nStarting Java Lab #1...\n");
        validateProbability();
        generateAllKmers();
    }
}
