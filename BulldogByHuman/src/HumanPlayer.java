import java.util.Scanner;

public class HumanPlayer extends Player {

    /**
     * Default constructor for the HumanPlayer class
     */
    public HumanPlayer() {
        this("HumanPlayer");
    }

    /**
     * Constructs a new HumanPlayer with the name.
     *
     * @param name the name of the player
     */
    public HumanPlayer(String name) {
        super(name);
    }

    /**
     * This player's strategy is to roll as long as the user wants or until a
     * 6 is rolled.
     *
     * @return the total score of the player
     */
    public int play() {
        Scanner scanner = new Scanner(System.in);

        String userDecision = "y";

        int turnScore = 0;

        while (userDecision.equalsIgnoreCase("y")) {
            int roll = (int) (Math.random() * 6 + 1);

            System.out.print(getName() + " rolled " + roll + "!\n");

            if (roll == 6) {
                System.out.println(" and scored 0 for the turn");
                break;
            } else {
                turnScore += roll;
                System.out.println("\nTurn score: " + turnScore);
            }

            System.out.println("Do you want to play again? (y/n)");
            userDecision = scanner.nextLine();
        }

        if (turnScore > 0) {
            setScore(getScore() + turnScore);
        }

        System.out.println("Your play is over.");
        return getScore();
    }
}

