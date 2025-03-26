import java.util.Scanner;

/**
 * The RandomPlayer class represents a player in the Bulldog game who rolls the dice
 * randomly and has the option to continue rolling until a score is reached or a 6 is rolled.
 * The player earns points for each roll, but if a 6 is rolled, they lose all points for that turn.
 *
 * This class extends the {@link Player} class and implements the abstract {@link Player#play()} method.
 */
public class RandomPlayer extends Player {
    private Dice dice;

    /**
     * Constructs a RandomPlayer with the given name.
     *
     * @param name The name of the player.
     */
    public RandomPlayer(String name) {
        super(name);
        dice = new Dice(6); // Initializes the Dice object with 6 sides.
    }

    /**
     * Simulates a player's turn in the Bulldog game. The player rolls the dice, adds the roll to
     * their turn score, and has the option to roll again unless a 6 is rolled, in which case the
     * turn ends with no points earned.
     *
     * The player will continue rolling until they either choose not to roll again or they reach
     * or exceed the winning score.
     *
     * @return The score the player earns during their turn. Returns 0 if a 6 is rolled.
     */
    @Override
    public int play() {
        int turnScore = 0;
        boolean continueRolling = true;

        while (continueRolling && turnScore < WINNING_SCORE) {
            int roll = dice.roll();
            System.out.println("Player " + getName() + " rolled: " + roll);

            if (roll == 6) {
                System.out.println("Rolled a 6! No points earned this turn.");
                return 0;
            } else {
                turnScore += roll;
                System.out.println("Current turn score: " + turnScore);

                if (turnScore >= WINNING_SCORE) break;

                System.out.print("Do you want to roll again? (y/n): ");
                String response = new Scanner(System.in).nextLine().trim().toLowerCase();

                if (!response.equals("y")) {
                    continueRolling = false;
                }
            }
        }
        return turnScore;
    }
}
