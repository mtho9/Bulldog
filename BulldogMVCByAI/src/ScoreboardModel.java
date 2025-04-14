import java.util.ArrayList;
import java.util.List;

public class ScoreboardModel {
    private List<Player> players;
    private List<ScoreboardObserver> observers;

    public ScoreboardModel() {
        players = new ArrayList<>();
        observers = new ArrayList<>();
    }

    // Add a player to the scoreboard
    public void addPlayer(Player player) {
        players.add(player);
        notifyObservers();
    }

    // Get the number of players
    public int getPlayerCount() {
        return players.size();
    }

    // Get the name of a player by index
    public String getPlayerName(int index) {
        if (index >= 0 && index < players.size()) {
            return players.get(index).getName();
        } else {
            return "Unknown Player";
        }
    }

    // Get the score of a player by index
    public int getPlayerScore(int index) {
        if (index >= 0 && index < players.size()) {
            return players.get(index).getScore();
        } else {
            return 0; // Default score if index is out of bounds
        }
    }

    // Set the score of a player
    public void setPlayerScore(int index, int score) {
        if (index >= 0 && index < players.size()) {
            players.get(index).setScore(score);
            notifyObservers();
        }
    }

    // Add an observer to the list
    public void addObserver(ScoreboardObserver observer) {
        observers.add(observer);
    }

    // Notify all observers that the model has changed
    private void notifyObservers() {
        for (ScoreboardObserver observer : observers) {
            observer.update(this);
        }
    }
}
