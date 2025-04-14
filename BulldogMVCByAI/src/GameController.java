import javax.swing.*;

public class GameController {
    private final Player[] players;
    private final ScoreboardModel model;
    private final BulldogGameUI view;
    private int currentTurn = 1;
    private int currentPlayerIndex = 0;

    public GameController(Player[] players, ScoreboardModel model, BulldogGameUI view) {
        this.players = players;
        this.model = model;
        this.view = view;
    }

    public void startGame() {
        continueTurn();
    }

    private void continueTurn() {
        if (model.getPlayerScore(0) >= Player.WINNING_SCORE || model.getPlayerScore(1) >= Player.WINNING_SCORE) return;

        Player player = players[currentPlayerIndex];
        JTextArea logArea = (currentPlayerIndex == 0) ? view.getPlayer1Log() : view.getPlayer2Log();
        logArea.append(player.getName() + " is taking turn " + currentTurn + "\n");

        if (player instanceof HumanPlayer && currentPlayerIndex == 0) {
            view.startHumanTurn(() -> {
                int score = view.getHumanTurnScore();
                model.setPlayerScore(0, model.getPlayerScore(0) + score);
                view.updateScoreboard();
                logArea.append("Turn complete. Earned: " + score + "\n");

                if (model.getPlayerScore(0) >= Player.WINNING_SCORE) {
                    view.announceWinner(player.getName(), model.getPlayerScore(0));
                    return;
                }

                switchPlayer();
                continueTurn();
            });
        } else {
            new Thread(() -> {
                int turnScore = player.play();
                model.setPlayerScore(currentPlayerIndex, model.getPlayerScore(currentPlayerIndex) + turnScore);
                SwingUtilities.invokeLater(() -> {
                    logArea.append("Scored: " + turnScore + "\n");
                    view.updateScoreboard();

                    if (model.getPlayerScore(currentPlayerIndex) >= Player.WINNING_SCORE) {
                        view.announceWinner(player.getName(), model.getPlayerScore(currentPlayerIndex));
                        return;
                    }

                    switchPlayer();
                    continueTurn();
                });
            }).start();
        }
    }

    private void switchPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % 2;
        if (currentPlayerIndex == 0) currentTurn++;
    }
}
