package labFour;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

// The class AminoAcidQuiz extends a jframe to create a GUI
public class AminoAcidQuiz extends JFrame {

    // Store the short names and corresponding full names of amino acids to be quizzed (position of each indicated corresponding values)
    private static final String[] SHORT_NAMES = {"A", "R", "N", "D", "C", "Q", "E", "G", "H", "I", "L", "K", "M", "F", "P", "S", "T", "W", "Y", "V"};
    private static final String[] FULL_NAMES = {"alanine", "arginine", "asparagine", "aspartic acid", "cysteine", "glutamine", "glutamic acid", "glycine", "histidine",
            "isoleucine", "leucine", "lysine", "methionine", "phenylalanine", "proline", "serine", "threonine", "tryptophan", "tyrosine", "valine"};

    // Components of the GUI
    private JLabel questionLabel = new JLabel("Press 'Start Quiz' to begin!");
    private JLabel correctCountLabel = new JLabel("Correct: --");
    private JLabel incorrectCountLabel = new JLabel("Incorrect: --");
    private JLabel instructionsLabel = new JLabel("<html>Enter the one-letter code for the amino acid in the textbox and press the enter key to submit.</html>");
    private JLabel timeLabel = new JLabel("Time: 30 seconds");
    private JTextField answerField = new JTextField();
    private JButton startButton = new JButton("Start Quiz");
    private JButton cancelButton = new JButton("Cancel");
    private JPanel buttonPanel = new JPanel();

    // Components for functionality
    private Random random = new Random();
    private Timer timer;
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int timeLeft = 30;

    // GUI is set up with this constructor
    public AminoAcidQuiz() {
        setTitle("Amino Acid Quiz"); // GUI window title
        setSize(300, 200); // GUI window size
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close the jframe and jvm when the user closes the window
        setLayout(new BorderLayout());

        // Set up the top panel where information will be printed and entered by the user (position is north/top)
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(instructionsLabel);
        northPanel.add(correctCountLabel);
        northPanel.add(incorrectCountLabel);
        northPanel.add(timeLabel);
        northPanel.add(answerField);
        add(northPanel, BorderLayout.NORTH);

        add(questionLabel, BorderLayout.CENTER);

        // Set up the button panel (position is south/bottom)
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(startButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Action listeners are added to the buttons and text field
        startButton.addActionListener(e -> startQuiz());
        cancelButton.addActionListener(e -> stopQuiz());
        answerField.addActionListener(e -> checkAnswer());
    }

    // Method to start quiz
    private void startQuiz() {
        resetQuiz(); // Reset any values present in top panel from previous quiz attempts
        timer = new Timer(1000, e -> updateQuiz()); // This allows the timer to call updateQuiz once per second (1000ms)
        timer.start();
        askQuestion();
    }

    // Method to stop the quiz
    private void stopQuiz() {
        if (timer != null) {
            timer.stop();
        }
        questionLabel.setText("Quiz Ended!");
    }

    // Method to reset the quiz
    private void resetQuiz() {
        correctCount = 0;
        incorrectCount = 0;
        timeLeft = 30;
        correctCountLabel.setText("Correct: 0");
        incorrectCountLabel.setText("Incorrect: 0");
        timeLabel.setText("Time: 30 seconds");
        questionLabel.setText("Press 'Start Quiz' to begin!");
        answerField.setText("");
    }

    // Method to generate a new question
    private void askQuestion() {
        int index = random.nextInt(SHORT_NAMES.length); // pick a random int up to the length of SHORT_NAMES
        questionLabel.setText(FULL_NAMES[index]); // set the questionLabel field to the corresponding amino acid full name
        answerField.setName(SHORT_NAMES[index]); // set the answerField
    }


    // Method to check the user's answer against the question
    private void checkAnswer() {
        String userAnswer = answerField.getText().toUpperCase().trim(); //
        String correctAnswer = answerField.getName();
        if (!userAnswer.isEmpty()) {
            if (userAnswer.charAt(0) == correctAnswer.charAt(0)) {
                correctCount++;
                correctCountLabel.setText("Correct: " + correctCount);
            } else {
                incorrectCount++;
                incorrectCountLabel.setText("Incorrect: " + incorrectCount);
            }
            askQuestion(); // Generate a new question
            answerField.setText(""); // Empty the input text field
        }
    }

    // Method to update the quiz each second
    private void updateQuiz() {
        timeLeft--; // decrement timeLeft
        timeLabel.setText("Time: " + timeLeft + " seconds");
        if (timeLeft <= 0) { // run stopQuiz() once time runs out
            stopQuiz();
        }
    }

    // Main method which runs the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AminoAcidQuiz().setVisible(true);
        });
    }
}
