public class FifteenPlayer extends Player {
    /**
     * Default constructor for the FifteenPlayer class.
     */
    public FifteenPlayer() {
        this("FifteenPlayer");
    }

    /**
     * Constructs a new FifteenPlayer with the name.
     *
     * @param name the name of the player
     */
    public FifteenPlayer(String name) {
        super(name);
    }
    /**
     * This player's strategy is to roll until the turn score
     * reaches at least 15 or rolls a 6.
     *
     * @return the total score of the player
     */
    public int play() {
        int roll = (int) (Math.random() * 6 + 1);
        int turnScore = 0;

        while (turnScore < 15 && roll != 6) {
            System.out.print(getName() + " rolled " + roll + "!\n");
            turnScore += roll;
            System.out.println(getName() + " scored " + roll + " for the turn.");
            // roll = (int) (Math.random() * 6 + 1);
        }

        setScore(getScore() + turnScore);
        return getScore();
    }
}
