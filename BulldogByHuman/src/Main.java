import java.util.Scanner;
import java.util.ArrayList;

/**
 * Main class begins a game where players take turns.
 * The game supports multiple players.
 *
 * Players are WimpPlayer, HumanPlayer, RandomPlayer, FifteenPlayer, and UniquePlayer.
 *
 * Game continues until one player reaches 104.
 */
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the number of players: ");
        int playerNum = Integer.parseInt(scanner.nextLine());
        System.out.println("There will be " + playerNum + " players in this game.");
        ArrayList<Player> playerList = new ArrayList<>();

        for (int i = 0; i < playerNum; i++) {
            System.out.println("Select player " + (i + 1) + " type: ");
            System.out.println("(1) WimpPlayer");
            System.out.println("(2) HumanPlayer");
            System.out.println("(3) RandomPlayer");
            System.out.println("(4) FifteenPlayer");
            System.out.println("(5) UniquePlayer");

            int choice = Integer.parseInt(scanner.nextLine());

            Player player = null;
            switch (choice) {
                case 1:
                    player = new WimpPlayer();
                    break;
                case 2:
                    player = new HumanPlayer();
                    break;
                case 3:
                    player = new RandomPlayer();
                    break;
                case 4:
                    player = new FifteenPlayer();
                    break;
                case 5:
                    player = new UniquePlayer();
                    break;
                default:
                    System.out.println("not valid, choose between 1 and 5");
                    i--;
                    continue;
            }

            playerList.add(player);
        }


        boolean gameOn = true;
        while (gameOn) {
            for (int i = 0; i < playerList.size(); i++) {
                Player player = playerList.get(i);

                if (i > 0) {
                    System.out.println();
                }

                int score = player.play();
                player.setScore(score);

                System.out.println(player.getClass().getSimpleName() + " played and scored " + score);

                System.out.println("Current scores:");
                for (Player p : playerList) {
                    System.out.println(p.getClass().getSimpleName() + ": " + p.getScore());
                }

                if (player.getScore() >= 104) {
                    System.out.println(player.getClass().getSimpleName() + " wins the game!");
                    gameOn = false;
                    break;
                }
            }
        }
    }
}