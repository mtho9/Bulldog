import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class BulldogGameUI {
    private JFrame frame;
    private JPanel mainMenu, gamePanel;
    private JComboBox<String> player1Select, player2Select;
    private JTextArea player1Log, player2Log;
    private JButton startButton;
    private String player1Name, player2Name;

    private Dice dice;
    private ScoreboardModel model;
    private ScoreboardViewer scoreboardViewer;
    private JPanel scoreboardPanel;
    private JLabel player1ScoreLabel, player2ScoreLabel;

    private JButton humanRollButton;
    private int currentHumanTurnScore = 0;
    private Runnable onHumanTurnComplete;

    private GameController controller;

    public BulldogGameUI() {
        frame = new JFrame("Bulldog Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new CardLayout());

        dice = new Dice(6);
        model = new ScoreboardModel();
        scoreboardViewer = new ScoreboardViewer(model);

        initializeMainMenu();
        initializeGamePanel();

        frame.add(mainMenu, "MainMenu");
        frame.add(gamePanel, "GamePanel");
        frame.setVisible(true);
    }

    private void initializeMainMenu() {
        mainMenu = new JPanel();
        mainMenu.setLayout(new GridLayout(5, 1));

        player1Select = new JComboBox<>(new String[]{"Wimp", "Human", "Random", "Fifteen", "Unique"});
        player2Select = new JComboBox<>(new String[]{"Wimp", "Human", "Random", "Fifteen", "Unique"});
        startButton = new JButton("Start Game");

        mainMenu.add(new JLabel("Select Player 1 Type:"));
        mainMenu.add(player1Select);
        mainMenu.add(new JLabel("Select Player 2 Type:"));
        mainMenu.add(player2Select);
        mainMenu.add(startButton);

        startButton.addActionListener(e -> {
            player1Name = (String) player1Select.getSelectedItem();
            player2Name = (String) player2Select.getSelectedItem();

            Player player1 = createPlayer(player1Name);
            Player player2 = createPlayer(player2Name);

            model.addPlayer(player1);
            model.addPlayer(player2);

            controller = new GameController(new Player[]{player1, player2}, model, this);
            ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "GamePanel");
            controller.startGame();
        });
    }

    private void initializeGamePanel() {
        gamePanel = new JPanel(new BorderLayout());

        JPanel playerPanel = new JPanel(new GridLayout(1, 2));
        JPanel player1Panel = new JPanel(new BorderLayout());
        JPanel player2Panel = new JPanel(new BorderLayout());

        player1Log = new JTextArea();
        player2Log = new JTextArea();
        player1Log.setEditable(false);
        player2Log.setEditable(false);

        player1Panel.add(new JLabel("Player 1 Log"), BorderLayout.NORTH);
        player1Panel.add(new JScrollPane(player1Log), BorderLayout.CENTER);

        player2Panel.add(new JLabel("Player 2 Log"), BorderLayout.NORTH);
        player2Panel.add(new JScrollPane(player2Log), BorderLayout.CENTER);

        playerPanel.add(player1Panel);
        playerPanel.add(player2Panel);
        gamePanel.add(playerPanel, BorderLayout.CENTER);

        initializeScoreboardPanel();
        gamePanel.add(scoreboardPanel, BorderLayout.SOUTH);
    }

    private void initializeScoreboardPanel() {
        scoreboardPanel = new JPanel(new GridLayout(3, 1));

        player1ScoreLabel = new JLabel("Player 1: 0");
        player2ScoreLabel = new JLabel("Player 2: 0");
        humanRollButton = new JButton("Roll");
        humanRollButton.setEnabled(false);

        humanRollButton.addActionListener(e -> handleHumanRoll());

        scoreboardPanel.add(player1ScoreLabel);
        scoreboardPanel.add(player2ScoreLabel);
        scoreboardPanel.add(humanRollButton);
    }

    private void handleHumanRoll() {
        int roll = dice.roll();
        player1Log.append("You rolled: " + roll + "\n");

        if (roll == 6) {
            player1Log.append("Rolled a 6! No points this turn.\n");
            currentHumanTurnScore = 0;
            humanRollButton.setEnabled(false);
            if (onHumanTurnComplete != null) onHumanTurnComplete.run();
        } else {
            currentHumanTurnScore += roll;
            player1Log.append("Turn score: " + currentHumanTurnScore + "\n");
            int result = JOptionPane.showConfirmDialog(frame, "Roll again?", "Your Turn", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.NO_OPTION) {
                humanRollButton.setEnabled(false);
                if (onHumanTurnComplete != null) onHumanTurnComplete.run();
            }
        }
    }

    public void startHumanTurn(Runnable onComplete) {
        currentHumanTurnScore = 0;
        onHumanTurnComplete = onComplete;
        humanRollButton.setEnabled(true);
    }

    public int getHumanTurnScore() {
        return currentHumanTurnScore;
    }

    public void updateScoreboard() {
        SwingUtilities.invokeLater(() -> {
            player1ScoreLabel.setText(player1Name + ": " + model.getPlayerScore(0));
            player2ScoreLabel.setText(player2Name + ": " + model.getPlayerScore(1));
        });
    }

    public void announceWinner(String winnerName, int winnerScore) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, winnerName + " wins with a score of " + winnerScore + "!"));
    }

    public JTextArea getPlayer1Log() {
        return player1Log;
    }

    public JTextArea getPlayer2Log() {
        return player2Log;
    }

    private Player createPlayer(String type) {
        switch (type) {
            case "Human": return new HumanPlayer(type);
            case "Fifteen": return new FifteenPlayer(type);
            case "Random": return new RandomPlayer(type);
            case "Unique": return new UniquePlayer(type);
            default: return new WimpPlayer(type);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BulldogGameUI::new);
    }
}
