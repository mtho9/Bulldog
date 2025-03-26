import java.util.Scanner;

/**
 * The HumanPlayer class extends the Player class and represents a player in the Bulldog game
 * who interacts with the game by rolling the dice and deciding whether to continue rolling
 * based on user input. The player can continue rolling until they reach the WINNING_SCORE
 * or roll a 6, which causes them to earn no points for the turn.
 */
public class HumanPlayer extends Player {
    private Scanner scanner;
    private Dice dice;

    /**
     * Constructs a HumanPlayer with the given name.
     * Initializes the scanner for user input and a Dice object to simulate a 6-sided die.
     *
     * @param name The name of the player.
     */
    public HumanPlayer(String name) {
        super(name);
        scanner = new Scanner(System.in);
        dice = new Dice(6);
    }

    /**
     * Simulates a human player's turn in the Bulldog game. The player rolls the dice and
     * decides whether to continue rolling based on user input. The turn ends if the player
     * rolls a 6 (earning no points) or reaches the WINNING_SCORE.
     *
     * @return The total score earned during the turn. If a 6 is rolled, the return value is 0.
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
                String response = scanner.nextLine().trim().toLowerCase();

                if (!response.equals("y")) {
                    continueRolling = false;
                }
            }
        }
        return turnScore;
    }
}
