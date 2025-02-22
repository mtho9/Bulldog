import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class BulldogGameUI {
    private JFrame frame;
    private JPanel mainMenu;
    private JPanel gamePanel;
    private JComboBox<String> player1Select, player2Select;
    private JTextArea player1Log, player2Log;
    private JButton startButton, rollButton1;
    private int player1Score = 0, player2Score = 0;
    private Random random;
    private String player1Name, player2Name;
    private int turnNumber = 1;
    private boolean isFirstTurn = true;

    public BulldogGameUI() {
        frame = new JFrame("Bulldog Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.setLayout(new CardLayout());

        random = new Random();

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

        rollButton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                takeTurn();
            }
        });

        player1Panel.add(new JLabel(), BorderLayout.NORTH);
        player1Panel.add(new JScrollPane(player1Log), BorderLayout.CENTER);

        player2Panel.add(new JLabel(), BorderLayout.NORTH);
        player2Panel.add(new JScrollPane(player2Log), BorderLayout.CENTER);

        gamePanel.add(player1Panel);
        gamePanel.add(player2Panel);
    }

    private void startGame() {
        ((JLabel) ((JPanel) gamePanel.getComponent(0)).getComponent(0)).setText(player1Name);
        ((JLabel) ((JPanel) gamePanel.getComponent(1)).getComponent(0)).setText(player2Name);
        new Thread(this::autoPlay).start();
    }

    private void autoPlay() {
        while (player1Score < 104 && player2Score < 104) {
            if (isFirstTurn) {
                player1Log.append(player1Name + " is taking the first turn of the game\n");
                isFirstTurn = false;
            } else {
                player1Log.append(player1Name + " is taking turn " + turnNumber + "\n");
            }

            if (!player1Name.equals("Human")) {
                player1Score += takeAutoTurn(player1Name, player1Log);
            }

            if (player1Score >= 104) {
                announceWinner(player1Name, player1Score);
                break;
            }

            player2Log.append(player2Name + " is taking turn " + turnNumber + "\n");

            if (!player2Name.equals("Human")) {
                player2Score += takeAutoTurn(player2Name, player2Log);
            }

            if (player2Score >= 104) {
                announceWinner(player2Name, player2Score);
                break;
            }
            turnNumber++;
        }
    }

    private int takeAutoTurn(String playerType, JTextArea logArea) {
        int turnScore = 0;
        int rolls = 0;
        while (true) {
            int roll = random.nextInt(6) + 1;
            rolls++;
            logArea.append("Rolled: " + roll + "\n");

            if (roll == 6) {
                logArea.append("Rolled a 6! No points earned.\n");
                return 0;
            }
            turnScore += roll;

            if (playerType.equals("Fifteen") && turnScore >= 15) break;
            if (playerType.equals("Unique") && (turnScore % 7 == 0 || rolls >= 3)) break;
            if (playerType.equals("Random") && random.nextBoolean()) break;
        }

        if (playerType.equals(player1Name)) {
            player1Score += turnScore;
            logArea.append("Turn result: " + turnScore + " points\nTotal score: " + player1Score + "\n");
        } else {
            player2Score += turnScore;
            logArea.append("Turn result: " + turnScore + " points\nTotal score: " + player2Score + "\n");
        }
        return turnScore;
    }

    private void announceWinner(String winnerName, int winnerScore) {
        JOptionPane.showMessageDialog(frame, winnerName + " wins with a score of " + winnerScore + "!");
    }

    private void takeTurn() {
        int roll = random.nextInt(6) + 1;
        player1Log.append("Turn " + turnNumber + "\n");
        player1Log.append("Rolled: " + roll + "\n");

        if (roll == 6) {
            player1Log.append("Rolled a 6! No points earned.\n");
        } else {
            player1Score += roll;
            player1Log.append("Total Score: " + player1Score + "\n");
        }

        turnNumber++;
        if (player1Score >= 104) {
            announceWinner(player1Name, player1Score);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BulldogGameUI::new);
    }
}
