package labThree;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

public class FastaSequence {

    // Store the header and sequence data for a FASTA record
    private String header;
    private String sequence;

    // Retrieve the header of the sequence - getter
    public String getHeader() {
        return header;
    }

    // Retrieve the sequence - getter
    public String getSequence() {
        return sequence;
    }

    // Initialize a FastaSequence object
    public FastaSequence(String header, String sequence) {
        this.header = header;
        this.sequence = sequence;
    }

    // Read a FASTA file and return list of FastaSequence objects
    public static List<FastaSequence> readFastaFile(String filepath) throws Exception {
        // Create a list to hold the sequences from the file
        List<FastaSequence> fastaList = new ArrayList<>();

        // Read the file line-by-line.
        BufferedReader reader = new BufferedReader(new FileReader(filepath));
        String line;
        String currentHeader = "";

        // Use StringBuilder for string concatenation
        StringBuilder currentSequence = new StringBuilder();

        // Read the file until the end of the file is reaced
        while ((line = reader.readLine()) != null) {
            // Check if the line starts with a header
            if (line.startsWith(">")) {
                // If the line read is not the first header add the previous sequence to the list
                if (currentHeader.length() > 0) {
                    fastaList.add(new FastaSequence(currentHeader, currentSequence.toString()));
                    // Reset the sequence
                    currentSequence.setLength(0);
                }
                // Remove the '>'
                currentHeader = line.substring(1);
            } else {
                // If the line is a sequence append the trimmed line to the current sequence.
                currentSequence.append(line.trim());
            }
        }
        // Add the last sequence from the file
        fastaList.add(new FastaSequence(currentHeader, currentSequence.toString()));

        // Close the file
        reader.close();
        return fastaList;
    }

    // Generate a file with the required details
    public static void writeTableSummary(List<FastaSequence> fastaList, File outputFile) throws Exception {
        // Use BufferedWriter to write strings to a file
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        // Write the header to be tab seperated with a new line at the end
        writer.write("sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence\n");

        // Loop through each sequence in the list
        for (int i = 0; i < fastaList.size(); i++) {
            // Get item from fastaList at the index i
            FastaSequence fs = fastaList.get(i);
            // Initialize and set variables to 0 for storing base counts
            int numA = 0, numC = 0, numG = 0, numT = 0;

            // Convert the sequence string to a character array for iteration
            char[] bases = fs.sequence.toCharArray();

            // Loop through each base in the sequence
            for (int j = 0; j < bases.length; j++) {
                char base = bases[j];
                // Count and store each base
                if (base == 'A') numA++;
                if (base == 'C') numC++;
                if (base == 'G') numG++;
                if (base == 'T') numT++;
            }
            // Write the sequence details to the file
            writer.write(fs.header + "\t" + numA + "\t" + numC + "\t" + numG + "\t" + numT + "\t" + fs.sequence + "\n");
        }
        // Close the file
        writer.close();
    }

    // Calculate the ratio of G and C in thee sequence
    public float getGCRatio() {
        // Initialize variable to count G and C bases
        int gcCount = 0;

        char[] bases = sequence.toCharArray();
        for (int i = 0; i < bases.length; i++) {
            char base = bases[i];
            // Add if the base is G or C
            if (base == 'G' || base == 'C') {
                gcCount++;
            }
        }
        // Calculate ratio of G nad C to the total length of the sequence
        return (float) gcCount / sequence.length();
    }

    // Main method from lab instructions to demonstrate functionality
    public static void main(String[] args) throws Exception {
        // Read sequences from the file
        List<FastaSequence> fastaList = FastaSequence.readFastaFile("./labThree/sequence.fasta");

        // Print method output to terminal
        for( FastaSequence fs : fastaList)
        {
            System.out.println(fs.getHeader());
            System.out.println(fs.getSequence());
            System.out.println(fs.getGCRatio());
        }

        // Generate the summary file
        File myFile = new File("./labThree/output.txt");
        writeTableSummary(fastaList, myFile);
    }
}
