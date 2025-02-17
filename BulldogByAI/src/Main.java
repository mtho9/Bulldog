import java.util.*;
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Player> players = new ArrayList<>();
        Random random = new Random();

        System.out.print("Enter the number of players: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < numPlayers; i++) {
            System.out.println("Choose player type for Player " + (i + 1) + " (Wimp, Human, Random, Fifteen, Unique, Mandys): ");
            String type = scanner.nextLine().trim().toLowerCase();

            System.out.print("Enter player name: ");
            String name = scanner.nextLine().trim();

            switch (type) {
                case "wimp":
                    players.add(new WimpPlayer(name));
                    break;
                case "human":
                    players.add(new HumanPlayer(name));
                    break;
                case "random":
                    players.add(new RandomPlayer(name));
                    break;
                case "fifteen":
                    players.add(new FifteenPlayer(name));
                    break;
                case "unique":
                    players.add(new UniquePlayer(name));
                    break;
                case "humans":
                    players.add(new HumanUniquePlayer(name));
                    break;
                default:
                    System.out.println("Invalid choice, assigning RandomPlayer.");
                    players.add(new RandomPlayer(name));
                    break;
            }
        }

        boolean gameWon = false;
        while (!gameWon) {
            for (Player player : players) {
                System.out.println("\n" + player.getName() + "'s turn:");
                int turnScore = player.play();
                player.setScore(player.getScore() + turnScore);

                System.out.println(player.getName() + "'s total score: " + player.getScore());

                if (player.getScore() >= 104) {
                    System.out.println("\nGame Over! " + player.getName() + " wins with " + player.getScore() + " points!");
                    gameWon = true;
                    break;
                }
            }
        }
        scanner.close();
    }
}