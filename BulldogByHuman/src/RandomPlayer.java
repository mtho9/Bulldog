import java.util.Random;

public class RandomPlayer extends Player {

    /**
     * Default constructor for the RandomPlayer class.
     */
    public RandomPlayer() {
        this("RandomPlayer");
    }

    /**
     * Constructs a new RandomPlayer with the name.
     *
     * @param name the name of the player
     */
    public RandomPlayer(String name) {
        super(name);
    }

    /**
     * This player's strategy is to randomly decide after each roll
     * whether to go again or not (50 50 chance for either). No
     * points added if rolls a 6.
     *
     * @return the total score of the player
     */
    public int play() {

        Random rand = new Random();

        Boolean turnDecision = true;

        while (turnDecision == true) {
            int roll = (int) (Math.random()*6 + 1);
            System.out.print("   Player " + getName() + " rolled " + roll + "!\n");
            if (roll != 6) {
                setScore(getScore() + roll);
                System.out.println("RandomPlayer has a score of " + getScore());
                turnDecision = rand.nextBoolean();
            } else {
                roll = 0;
                System.out.println(" and scored 0 for the turn.");
            }
        }

        return getScore();
    }
}
