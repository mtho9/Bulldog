/********************************************************/
/* David Levine                                         */
/* Login ID: david.b.levine@maine.edu                   */
/* COS 497, Summer 2024                                 */
/* Programming Assignment 6                             */
/* abstract Player class: holds generic info about a    */
/*           player of the game Bulldog                 */
/*      See Kettering University, CS-101, Prog 6        */
/********************************************************/

/**
 * The Player class is an abstract class that serves as a blueprint for all players
 * in the Bulldog game. It holds common information about the player, such as
 * their name, score, and the constant WINNING_SCORE. Each player must implement
 * the abstract play method, which defines their behavior during their turn in the game.
 */
public abstract class Player {

	/**
	 * Constant representing the winning score in the Bulldog game.
	 */
	public static final int WINNING_SCORE = 50;

	private String name;
	private int score;

	/**
	 * Constructs a Player with the given name and initializes their score to 0.
	 *
	 * @param name The name of the player.
	 */
	public Player(String name) {
		this.name = name;
		this.score = 0;
	}

	/**
	 * Gets the name of the player.
	 *
	 * @return The name of the player.
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Gets the current score of the player.
	 *
	 * @return The score of the player.
	 */
	public int getScore() {
		return this.score;
	}

	/**
	 * Sets the score of the player to the given value.
	 *
	 * @param score The score to set for the player.
	 */
	public void setScore(int score) {
		this.score = score;
	}

	/**
	 * Abstract method to simulate a player's turn. Each subclass of Player
	 * must provide an implementation of this method to define how the player
	 * plays their turn in the game.
	 *
	 * @return The score the player earns during their turn.
	 */
	public abstract int play();
}
