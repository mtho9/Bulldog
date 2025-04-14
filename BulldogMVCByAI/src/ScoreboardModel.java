import java.util.ArrayList;
import java.util.List;


// Written by Mandy Ho with the help of ChatGPT



/**
 * The {@code ScoreboardModel} class represents the data model for a scoreboard
 * in the Bulldog game. It manages a list of players and their scores,
 * and notifies registered observers whenever changes occur.
 */
public class ScoreboardModel {
    private List<Player> players;
    private List<ScoreboardObserver> observers;

    /**
     * Constructs a new {@code ScoreboardModel} with empty player and observer lists.
     */
    public ScoreboardModel() {
        players = new ArrayList<>();
        observers = new ArrayList<>();
    }

    /**
     * Adds a player to the scoreboard.
     *
     * @param player The {@code Player} to add.
     */
    public void addPlayer(Player player) {
        players.add(player);
        notifyObservers();
    }

    /**
     * Returns the number of players currently in the scoreboard.
     *
     * @return The number of players.
     */
    public int getPlayerCount() {
        return players.size();
    }

    /**
     * Returns the name of the player at the specified index.
     *
     * @param index The index of the player.
     * @return The player's name, or "Unknown Player" if the index is invalid.
     */
    public String getPlayerName(int index) {
        if (index >= 0 && index < players.size()) {
            return players.get(index).getName();
        } else {
            return "Unknown Player";
        }
    }

    /**
     * Returns the score of the player at the specified index.
     *
     * @param index The index of the player.
     * @return The player's score, or 0 if the index is invalid.
     */
    public int getPlayerScore(int index) {
        if (index >= 0 && index < players.size()) {
            return players.get(index).getScore();
        } else {
            return 0;
        }
    }

    /**
     * Sets the score of the player at the specified index and notifies observers.
     *
     * @param index The index of the player.
     * @param score The new score to set.
     */
    public void setPlayerScore(int index, int score) {
        if (index >= 0 && index < players.size()) {
            players.get(index).setScore(score);
            notifyObservers();
        }
    }

    /**
     * Registers a {@code ScoreboardObserver} to receive updates when the model changes.
     *
     * @param observer The observer to register.
     */
    public void addObserver(ScoreboardObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifies all registered observers that the model has changed.
     */
    private void notifyObservers() {
        for (ScoreboardObserver observer : observers) {
            observer.update(this);
        }
    }
}
