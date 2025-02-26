import java.util.Scanner;

public class TempMain {
    public static void main(String[] args) {
        // we need a movelist to choose from and define p1move1 etc as the picked move using the scanner. 
        

        Scanner myObj = new Scanner(System.in);
        System.out.println("Welcome to Benjamin and Oliver's little game project!");
        System.out.println("Play at your own risk!!!!");
        System.out.println("Please enter number of players. (1-2)");
        int players = myObj.nextInt();
        boolean proceed = false;

        while (!proceed) {
            switch (players) {
                case (1) -> {
                System.out.println("You have chosen to play against the computer.");
                break;
                } case (2) -> {
                System.out.println("You have chosen to play against a friend.");
                proceed = true;
                break;
                } default -> {
                System.out.println("Invalid input, try again.");
                }
            }
        }

        do {
            proceed = false;
            System.out.println("Player 1, please enter your name.");
            String player1 = myObj.nextLine();
            switch (player1) {
                case "" -> {
                    System.out.println("Invalid input, try again.");
                } default -> {
                    proceed = true;
                }
            }

        } 
        while (players == 2 && !proceed);


        //Her skal vi dele den op i to forskellige situationer, 1 hvor man spiller mod computeren
        //og en hvor man spiller imod en anden spiller.
        //Jeg går ud fra at vi spiller med to spillere her:

    }
}