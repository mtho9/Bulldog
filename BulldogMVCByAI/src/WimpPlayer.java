public class WimpPlayer extends Player {

	private Dice dice;  // Use the Dice class for rolling the die

	/********************************************************/
	/* Constructor: WimpPlayer                              */
	/* Purpose: Create a default WimpPlayer                 */
	/* Parameters:                                          */
	/*   none                                               */
	/********************************************************/
	public WimpPlayer() {
		this("Wimp");
	}

	/********************************************************/
	/* Constructor: WimpPlayer                              */
	/* Purpose: Create a new WimpPlayer object              */
	/* Parameters:                                          */
	/*   String name:  the name of the Player being created */
	/********************************************************/
	public WimpPlayer(String name) {
		super(name);
		dice = new Dice(6);  // Create a 6-sided die
	}

	/********************************************************/
	/* Method:  play                                        */
	/* Purpose: Take one turn for this Player               */
	/*          One turn for a WimpPlayer is a single roll  */
	/* Parameters:                                          */
	/*   none                                               */
	/* Returns:                                             */
	/*   the score earned by the player on this turn,       */
	/*       which will be zero if a six was rolled         */
	/********************************************************/
	public int play() {
		int roll = dice.roll();
		System.out.print("   Player " + getName() + " rolled " + roll);

		if (roll != 6) {
			System.out.println(" and chose not to continue, scoring "
					+ roll + " for the turn.");
		} else {
			roll = 0;
			System.out.println(" and scored 0 for the turn.");
		}

		return roll;
	}
}