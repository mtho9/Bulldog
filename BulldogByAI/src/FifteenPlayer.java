import java.util.Random;

public class FifteenPlayer extends Player {
    private Random random;

    public FifteenPlayer(String name) {
        super(name);
        random = new Random();
    }

    @Override
    public int play() {
        int turnScore = 0;

        while (turnScore < 15) {
            int roll = random.nextInt(6) + 1;
            System.out.println("Player " + getName() + " rolled: " + roll);

            if (roll == 6) {
                System.out.println("Rolled a 6! No points earned this turn.");
                return 0;
            } else {
                turnScore += roll;
                System.out.println("Current turn score: " + turnScore);
            }
        }
        System.out.println("Reached 15 points, ending turn.");
        return turnScore;
    }
}