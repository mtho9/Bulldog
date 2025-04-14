import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BulldogGameUI {
    private JFrame frame;
    private JPanel mainMenu, gamePanel;
    private JComboBox<String> player1Select, player2Select;
    private JTextArea player1Log, player2Log;
    private JButton startButton, rollButton1;
    private String player1Name, player2Name;
    private int turnNumber = 1;
    private boolean isFirstTurn = true;

    // Dice object to handle randomness
    private Dice dice;

    // Model and Viewer for scoreboard
    private ScoreboardModel model;
    private ScoreboardViewer scoreboardViewer;

    // Scoreboard panel components
    private JPanel scoreboardPanel;
    private JLabel player1ScoreLabel, player2ScoreLabel;

    public BulldogGameUI() {
        frame = new JFrame("Bulldog Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new CardLayout());

        // Initialize the Dice object
        dice = new Dice(6);

        // Initialize the model and scoreboard viewer
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
        mainMenu.setLayout(new GridLayout(4, 1));

        player1Select = new JComboBox<>(new String[]{"Wimp", "Human", "Random", "Fifteen", "Unique"});
        player2Select = new JComboBox<>(new String[]{"Wimp", "Human", "Random", "Fifteen", "Unique"});
        startButton = new JButton("Start Game");

        mainMenu.add(new JLabel("Select Player 1 Type:"));
        mainMenu.add(player1Select);
        mainMenu.add(new JLabel("Select Player 2 Type:"));
        mainMenu.add(player2Select);
        mainMenu.add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player1Name = (String) player1Select.getSelectedItem();
                player2Name = (String) player2Select.getSelectedItem();

                Player player1 = createPlayer(player1Name);
                Player player2 = createPlayer(player2Name);

                model.addPlayer(player1);
                model.addPlayer(player2);

                ((CardLayout) frame.getContentPane().getLayout()).show(frame.getContentPane(), "GamePanel");
                startGame();
            }
        });
    }

    private void initializeGamePanel() {
        gamePanel = new JPanel(new GridLayout(1, 2));

        JPanel player1Panel = new JPanel(new BorderLayout());
        JPanel player2Panel = new JPanel(new BorderLayout());

        player1Log = new JTextArea();
        player2Log = new JTextArea();
        player1Log.setEditable(false);
        player2Log.setEditable(false);

        rollButton1 = new JButton("Take Turn");
        rollButton1.addActionListener(e -> takeTurn());

        player1Panel.add(new JLabel(), BorderLayout.NORTH);
        player1Panel.add(new JScrollPane(player1Log), BorderLayout.CENTER);

        player2Panel.add(new JLabel(), BorderLayout.NORTH);
        player2Panel.add(new JScrollPane(player2Log), BorderLayout.CENTER);

        gamePanel.add(player1Panel);
        gamePanel.add(player2Panel);

        initializeScoreboardPanel();
        gamePanel.add(scoreboardPanel, BorderLayout.SOUTH);
    }

    private void initializeScoreboardPanel() {
        scoreboardPanel = new JPanel(new GridLayout(2, 1));

        player1ScoreLabel = new JLabel("Player 1: 0");
        player2ScoreLabel = new JLabel("Player 2: 0");

        scoreboardPanel.add(player1ScoreLabel);
        scoreboardPanel.add(player2ScoreLabel);
    }

    private void startGame() {
        JPanel player1Panel = (JPanel) gamePanel.getComponent(0);
        JPanel player2Panel = (JPanel) gamePanel.getComponent(1);

        if (player1Panel != null && player1Panel.getComponentCount() > 0) {
            JLabel player1Label = (JLabel) player1Panel.getComponent(0);
            player1Label.setText(player1Name);
        }

        if (player2Panel != null && player2Panel.getComponentCount() > 0) {
            JLabel player2Label = (JLabel) player2Panel.getComponent(0);
            player2Label.setText(player2Name);
        }

        new Thread(this::autoPlay).start();
    }

    private void autoPlay() {
        while (model.getPlayerScore(0) < Player.WINNING_SCORE && model.getPlayerScore(1) < Player.WINNING_SCORE) {
            playTurn(player1Name, player1Log, 0); // Player 1's turn
            if (model.getPlayerScore(0) >= Player.WINNING_SCORE) break;

            playTurn(player2Name, player2Log, 1); // Player 2's turn
            if (model.getPlayerScore(1) >= Player.WINNING_SCORE) break;

            turnNumber++;
            updateScoreboard();
        }
    }

    private void playTurn(String playerName, JTextArea logArea, int playerIndex) {
        logArea.append(playerName + " is taking turn " + turnNumber + "\n");

        if (playerName.equals("Human")) {
            takeTurn();
        } else {
            int score = takeAutoTurn(playerName, logArea);
            model.setPlayerScore(playerIndex, model.getPlayerScore(playerIndex) + score);
        }

        if (model.getPlayerScore(playerIndex) >= Player.WINNING_SCORE) {
            announceWinner(playerName, model.getPlayerScore(playerIndex));
        }
    }

    private void takeTurn() {
        SwingUtilities.invokeLater(() -> {
            int roll = dice.roll();
            player1Log.append("Turn " + turnNumber + "\n");
            player1Log.append("Rolled: " + roll + "\n");

            if (roll != 6) {
                model.setPlayerScore(0, model.getPlayerScore(0) + roll);
                player1Log.append("Total Score: " + model.getPlayerScore(0) + "\n");
            } else {
                player1Log.append("Rolled a 6! No points earned.\n");
            }

            turnNumber++;
            updateScoreboard();
        });
    }

    private int takeAutoTurn(String playerType, JTextArea logArea) {
        int turnScore = 0;
        int rolls = 0;

        while (true) {
            int roll = dice.roll();
            rolls++;
            logArea.append("Rolled: " + roll + "\n");

            if (roll == 6) {
                logArea.append("Rolled a 6! No points earned.\n");
                return 0;
            }

            turnScore += roll;

            if (playerType.equals("Fifteen") && turnScore >= 15) break;
            if (playerType.equals("Unique") && (turnScore % 7 == 0 || rolls >= 3)) break;
            if (playerType.equals("Random") && Math.random() > 0.5) break;
        }

        model.setPlayerScore(playerType.equals(player1Name) ? 0 : 1, turnScore);
        logArea.append("Turn result: " + turnScore + " points\nTotal score: " + model.getPlayerScore(playerType.equals(player1Name) ? 0 : 1) + "\n");
        return turnScore;
    }

    private void announceWinner(String winnerName, int winnerScore) {
        SwingUtilities.invokeLater(() -> JOptionPane.showMessageDialog(frame, winnerName + " wins with a score of " + winnerScore + "!"));
    }

    private void updateScoreboard() {
        SwingUtilities.invokeLater(() -> {
            player1ScoreLabel.setText(player1Name + ": " + model.getPlayerScore(0));
            player2ScoreLabel.setText(player2Name + ": " + model.getPlayerScore(1));
        });
    }

    private Player createPlayer(String playerType) {
        switch (playerType) {
            case "Human": return new HumanPlayer(playerType);
            case "Fifteen": return new FifteenPlayer(playerType);
            case "Random": return new RandomPlayer(playerType);
            case "Unique": return new UniquePlayer(playerType);
            default: return new WimpPlayer(playerType);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BulldogGameUI::new);
    }
}
