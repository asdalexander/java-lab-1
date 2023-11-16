package labFive;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PrimeNumberApp {

    // GUI components
    private JFrame frame;                       // Main window for the application
    private JTextField inputField;              // Text field for user to enter the maximum number
    private JTextField threadInputField;        // Text field for user to enter the number of threads
    private JButton startButton, cancelButton;  // Buttons for starting and canceling the prime search
    private JTextArea resultArea;               // Text area for displaying the results
    private volatile boolean isCancelled;       // A flag to indicate if the prime finding process should be canceled


    // Main method
    public static void main(String[] args) {
        // Ensures that the GUI is created in the Event Dispatch Thread (to properly start the swing application)
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new PrimeNumberApp().createAndShowGUI();
            }
        });
    }

    // Method for setting up and displaying the GUI
    private void createAndShowGUI() {
        frame = new JFrame("Prime Number Finder");          // Initialize the main window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   // Program will exit when the window is closed
        frame.setLayout(new BorderLayout());                    // Using BorderLayout as the layout manager

        // Input for upper limit of prime number search and number of threads to use
        inputField = new JTextField(10);                // Field for max number input
        threadInputField = new JTextField(5);           // Initialize thread input field
        JPanel inputPanel = new JPanel();                       // Field for thread count input
        inputPanel.add(new JLabel("Enter Max Number:"));   // Panel to hold the inputs
        inputPanel.add(inputField);
        inputPanel.add(new JLabel("Threads:"));
        inputPanel.add(threadInputField);

        // Buttons for control
        startButton = new JButton("Start");
        cancelButton = new JButton("Cancel");
        JPanel buttonPanel = new JPanel();          // Panel to hold the buttons
        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);

        // Result display area
        resultArea = new JTextArea(10, 30);
        resultArea.setEditable(false);                          // Users cannot edit this area, it's for display only
        JScrollPane scrollPane = new JScrollPane(resultArea);   // Makes the result area scrollable

        // Adding action listeners for buttons
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startFindingPrimes();                       // Starts the prime finding process when clicked
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                isCancelled = true;  // Set the cancellation flag when clicked
            }
        });

        // Adding components to the frame
        frame.add(inputPanel, BorderLayout.NORTH);
        frame.add(buttonPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Finalizing the GUI setup
        frame.setSize(450,300);               // Sizes the frame so all contents are at or above their preferred sizes
        frame.setVisible(true);     // Makes the window visible
    }


    // Method to start the prime number finding process
    private void startFindingPrimes() {

        // Parse user input and handle invalid input
        int maxNumber, numberOfThreads;
        try {
            maxNumber = Integer.parseInt(inputField.getText());             // Converts text to integer
            numberOfThreads = Integer.parseInt(threadInputField.getText()); // Converts text to integer
        } catch (NumberFormatException e) {
            resultArea.setText("Invalid input! Please enter integers.");    // Shows error message in result area
            return; // Exits the method
        }

        // Validate input values
        if (maxNumber < 2 || numberOfThreads < 1) {
            resultArea.setText("Invalid input! Max number must be at least 2 and threads at least 1.");
            return; // Exits the method
        }

        // Reset the cancellation flag and clear the result area
        isCancelled = false;
        resultArea.setText("");

        // Divide the range and start threads
        int rangePerThread = maxNumber / numberOfThreads;   // Determines the range of numbers each thread will check
        for (int i = 0; i < numberOfThreads; i++) {
            int start = i * rangePerThread + 1;
            int end = (i == numberOfThreads - 1) ? maxNumber : start + rangePerThread - 1;
            new Thread(new PrimeFinder(start, end)).start();    // Creates and starts a new thread
        }
    }

    // PrimeFinder class
    class PrimeFinder implements Runnable {
        private int start, end; // The range of numbers to check for primes

        // Constructor for PrimeFinder
        PrimeFinder(int start, int end) {
            this.start = start; // Set the start of range
            this.end = end;     // Set the end of range
        }

        public void run() {
            // This method is executed when the thread starts
            for (int i = start; i <= end; i++) {
                if (isCancelled) return;  // Check if the cancellation flag is set and stop if it is

                if (isPrime(i)) {   // Check if the current number is prime
                    int finalI = i;  // Final variable for use in SwingUtilities.invokeLater
                    SwingUtilities.invokeLater(() ->    // Update the GUI
                            resultArea.append(finalI + " is prime\n")); // Append the result to the result area
                }
            }
        }

        // Method to check if a number is prime (using prime factorization)
        private boolean isPrime(int number) {
            if (number <= 1) return false;  // Numbers less than or equal to 1 are not prime
            for (int i = 2; i * i <= number; i++) { // Iterate from 2 to the square root of the number
                // Check for factors of the number
                if (number % i == 0) return false;  // If a factor is found, the number is not prime
            }
            return true;    // If no factors are found, the number is prime
        }
    }
}