import java.util.Random;

public class UniquePlayer extends Player {
    private Random random;

    public UniquePlayer(String name) {
        super(name);
        random = new Random();
    }

    @Override
    public int play() {
        int turnScore = 0;
        int rolls = 0;

        while (true) {
            int roll = random.nextInt(6) + 1;
            rolls++;
            System.out.println("Player " + getName() + " rolled: " + roll);

            if (roll == 6) {
                System.out.println("Rolled a 6! No points earned this turn.");
                return 0;
            }

            turnScore += roll;
            System.out.println("Current turn score: " + turnScore);

            // Unique strategy: stop if turn score is a multiple of 7 or after 3 rolls
            if (turnScore % 7 == 0 || rolls >= 3) {
                System.out.println("Unique strategy triggered, ending turn with " + turnScore + " points.");
                break;
            }
        }
        return turnScore;
    }
}