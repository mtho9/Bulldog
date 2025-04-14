import java.util.Random;

/**
 * The Dice class models a dice with a specified number of sides.
 * It provides a method to roll the dice, returning a random integer
 * between 1 and the number of sides (inclusive).
 */
public class Dice {
    private static Random random = new Random();
    private int sides;

    /**
     * Constructs a Dice object with a specified number of sides.
     *
     * @param sides The number of sides on the dice. Must be at least 2.
     * @throws IllegalArgumentException if the number of sides is less than 2.
     */
    public Dice(int sides) {
        if (sides < 2) {
            throw new IllegalArgumentException("A dice must have at least 2 sides");
        }
        this.sides = sides;
    }

    /**
     * Rolls the dice and returns a random number between 1 and the number of sides (inclusive).
     *
     * @return A random integer between 1 and the number of sides (inclusive).
     */
    public int roll() {
        return random.nextInt(sides) + 1;
    }
}