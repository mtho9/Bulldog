public class HumanUniquePlayer extends Player {

    /**
     * Default constructor for the UniquePlayer class.
     */
    public HumanUniquePlayer() {
        this("Unique Player");
    }

    /**
     * Constructs a new UniquePlayer with the name.
     *
     * @param name the name of the player
     */
    public HumanUniquePlayer(String name) {
        super(name);
    }


    /**
     * This player's strategy is to roll exactly three times unless a
     * 6 is rolled which means no points added.
     *
     * @return the total score of the player
     */
    public int play() {
        int turnScore = 0;

        for (int i = 0; i < 3; i++) {
            int roll = (int) (Math.random() * 6 + 1);
            System.out.println(getName() + " rolled " + roll + "!");

            if (roll == 6) {
                System.out.println(getName() + " rolled a 6 and got no points from this turn.");
                return 0;
            }

            turnScore += roll;
        }

        System.out.println(getName() + " played and scored " + turnScore);
        return turnScore;
    }
}
