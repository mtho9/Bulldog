/**
 * The UniquePlayer class represents a player in the Bulldog game who follows a unique strategy.
 * The player rolls the dice and accumulates points. If a 6 is rolled, they earn no points for the turn.
 * The player's turn ends when any of the following conditions are met:
 * - The player's turn score is a multiple of 7,
 * - The player has rolled three times,
 * - The player reaches or exceeds the winning score.
 *
 * This class extends the {@link Player} class and implements the abstract {@link Player#play()} method.
 */
public class UniquePlayer extends Player {
    private Dice dice;

    /**
     * Constructs a UniquePlayer with the given name.
     *
     * @param name The name of the player.
     */
    public UniquePlayer(String name) {
        super(name);
        dice = new Dice(6); // Initializes the Dice object with 6 sides.
    }

    /**
     * Simulates a player's turn in the Bulldog game. The player rolls the dice and adds the roll to
     * their turn score. The turn ends if any of the following conditions are met:
     * - The player rolls a 6 (no points earned),
     * - The player's turn score is a multiple of 7,
     * - The player rolls three times,
     * - The player reaches or exceeds the winning score.
     *
     * @return The score the player earns during their turn. Returns 0 if a 6 is rolled.
     */
    @Override
    public int play() {
        int turnScore = 0;
        int rolls = 0;

        while (turnScore < WINNING_SCORE) {
            int roll = dice.roll();
            rolls++;
            System.out.println("Player " + getName() + " rolled: " + roll);

            if (roll == 6) {
                System.out.println("Rolled a 6! No points earned this turn.");
                return 0;
            }

            turnScore += roll;
            System.out.println("Current turn score: " + turnScore);

            if (turnScore % 7 == 0 || rolls >= 3 || turnScore >= WINNING_SCORE) {
                System.out.println("Unique strategy triggered, ending turn with " + turnScore + " points.");
                break;
            }
        }
        return turnScore;
    }
}
