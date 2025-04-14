import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ScoreboardViewer implements ScoreboardObserver {
    private JFrame frame;
    private JPanel scoreboardPanel;
    private ArrayList<JLabel> scoreLabels;

    public ScoreboardViewer(ScoreboardModel model) {
        // Initialize frame and panel
        frame = new JFrame("Scoreboard");
        scoreboardPanel = new JPanel();
        scoreboardPanel.setLayout(new BoxLayout(scoreboardPanel, BoxLayout.Y_AXIS));

        scoreLabels = new ArrayList<>();

        // Populate labels for initial players (only if players are present)
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

        // Register as observer
        model.addObserver(this);
    }

    @Override
    public void update(ScoreboardModel model) {
        // Clear the old labels and recreate them with the updated scores
        scoreboardPanel.removeAll();
        scoreLabels.clear();

        // Update the labels when the score changes
        for (int i = 0; i < model.getPlayerCount(); i++) {
            String updatedText = model.getPlayerName(i) + ": " + model.getPlayerScore(i);
            JLabel label = new JLabel(updatedText);
            scoreLabels.add(label);
            scoreboardPanel.add(label);
        }

        // Revalidate and repaint to ensure the UI updates
        scoreboardPanel.revalidate();
        scoreboardPanel.repaint();
    }

    // For demo (to simulate score change and update)
    public static void main(String[] args) {
        // Setup model
        ScoreboardModel model = new ScoreboardModel();
        model.addPlayer(new HumanPlayer("Alice"));
        model.addPlayer(new HumanPlayer("Bob"));
        model.addPlayer(new HumanPlayer("Charlie"));

        // Create viewer
        new ScoreboardViewer(model);

        // Pause with a dialog
        JOptionPane.showMessageDialog(null, "Click OK to update Bob's score");

        // Modify Bob's score (index 1)
        model.setPlayerScore(1, 10);
    }
}
