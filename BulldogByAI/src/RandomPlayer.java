import java.util.Random;

public class RandomPlayer extends Player {
    private Random random;

    public RandomPlayer(String name) {
        super(name);
        random = new Random();
    }

    @Override
    public int play() {
        int turnScore = 0;
        boolean continueRolling = true;

        while (continueRolling) {
            int roll = random.nextInt(6) + 1;
            System.out.println("Player " + getName() + " rolled: " + roll);

            if (roll == 6) {
                System.out.println("Rolled a 6! No points earned this turn.");
                return 0;
            } else {
                turnScore += roll;
                continueRolling = random.nextBoolean(); // 50% chance to continue
                System.out.println("Current turn score: " + turnScore);
                if (!continueRolling) {
                    System.out.println("Random decision: Ending turn.");
                }
            }
        }
        return turnScore;
    }
}