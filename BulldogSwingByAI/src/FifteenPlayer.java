/**
 * The FifteenPlayer class extends the Player class and represents a player
 * in the Bulldog game who follows a strategy where the player continues
 * rolling until they reach a total score of 15 or more, or a 6 is rolled.
 * If a 6 is rolled, the player earns no points for that turn.
 */
public class FifteenPlayer extends Player {
    private Dice dice;

    /**
     * Constructs a FifteenPlayer with the given name.
     * Initializes the Dice object to simulate a 6-sided die.
     *
     * @param name The name of the player.
     */
    public FifteenPlayer(String name) {
        super(name);
        dice = new Dice(6);
    }

    /**
     * Simulates a player's turn in the Bulldog game. The player rolls the dice
     * repeatedly until the turn score reaches the WINNING_SCORE or a 6 is rolled.
     * If a 6 is rolled, the player's turn ends immediately with no points earned.
     * If the player reaches the WINNING_SCORE, the turn ends.
     *
     * @return The total score earned during the turn. If a 6 is rolled, the return value is 0.
     */
    @Override
    public int play() {
        int turnScore = 0;

        while (turnScore < WINNING_SCORE) {
            int roll = dice.roll();
            System.out.println("Player " + getName() + " rolled: " + roll);

            if (roll == 6) {
                System.out.println("Rolled a 6! No points earned this turn.");
                return 0;
            } else {
                turnScore += roll;
                System.out.println("Current turn score: " + turnScore);
            }
        }
        System.out.println("Reached " + WINNING_SCORE + " points, ending turn.");
        return turnScore;
    }
}