import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

// Written by Mandy Ho with the help of ChatGPT

/**
 * The {@code ScoreboardViewer} class implements the {@code ScoreboardObserver} interface
 * and provides a graphical display for the current scoreboard using Swing components.
 * It dynamically updates whenever the {@code ScoreboardModel} notifies a change.
 */
public class ScoreboardViewer implements ScoreboardObserver {
    private JFrame frame;
    private JPanel scoreboardPanel;
    private ArrayList<JLabel> scoreLabels;

    /**
     * Constructs a {@code ScoreboardViewer} that displays player names and scores
     * from the given {@code ScoreboardModel}. Registers itself as an observer to the model.
     *
     * @param model The {@code ScoreboardModel} to observe and display.
     */
    public ScoreboardViewer(ScoreboardModel model) {
        frame = new JFrame("Scoreboard");
        scoreboardPanel = new JPanel();
        scoreboardPanel.setLayout(new BoxLayout(scoreboardPanel, BoxLayout.Y_AXIS));
        scoreLabels = new ArrayList<>();

        // Populate labels for any existing players
        for (int i = 0; i < model.getPlayerCount(); i++) {
            String labelText = model.getPlayerName(i) + ": " + model.getPlayerScore(i);
            JLabel label = new JLabel(labelText);
            scoreLabels.add(label);
            scoreboardPanel.add(label);
        }

        frame.add(scoreboardPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Register as observer to receive updates
        model.addObserver(this);
    }

    /**
     * Called by the {@code ScoreboardModel} when player scores or names change.
     * Updates the scoreboard UI to reflect the new data.
     *
     * @param model The {@code ScoreboardModel} that triggered the update.
     */
    @Override
    public void update(ScoreboardModel model) {
        scoreboardPanel.removeAll();
        scoreLabels.clear();

        for (int i = 0; i < model.getPlayerCount(); i++) {
            String updatedText = model.getPlayerName(i) + ": " + model.getPlayerScore(i);
            JLabel label = new JLabel(updatedText);
            scoreLabels.add(label);
            scoreboardPanel.add(label);
        }

        scoreboardPanel.revalidate();
        scoreboardPanel.repaint();
    }

    /**
     * A demonstration entry point that sets up a sample scoreboard with three players.
     * After displaying the scoreboard, it waits for user input to update a score.
     *
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        ScoreboardModel model = new ScoreboardModel();
        model.addPlayer(new HumanPlayer("Alice"));
        model.addPlayer(new HumanPlayer("Bob"));
        model.addPlayer(new HumanPlayer("Charlie"));

        new ScoreboardViewer(model);

        JOptionPane.showMessageDialog(null, "Click OK to update Bob's score");

        model.setPlayerScore(1, 10); // Update Bob’s score
    }
}
