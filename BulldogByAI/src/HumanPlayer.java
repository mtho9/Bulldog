import java.util.Scanner;

public class HumanPlayer extends Player {
    private Scanner scanner;

    public HumanPlayer(String name) {
        super(name);
        scanner = new Scanner(System.in);
    }

    @Override
    public int play() {
        int turnScore = 0;
        boolean continueRolling = true;

        while (continueRolling) {
            int roll = (int) (Math.random() * 6) + 1;
            System.out.println("Player " + getName() + " rolled: " + roll);

            if (roll == 6) {
                System.out.println("Rolled a 6! No points earned this turn.");
                return 0;
            } else {
                turnScore += roll;
                System.out.println("Current turn score: " + turnScore);
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