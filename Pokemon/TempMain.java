import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import org.w3c.dom.UserDataHandler;

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

        //Her skal vi dele den op i to forskellige situationer, 1 hvor man spiller mod computeren
        //og en hvor man spiller imod en anden spiller.
        //Jeg går ud fra at vi spiller med to spillere her:

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

        do {
            proceed = false;
            System.out.println("Player 2, please enter your name.");
            String player2 = myObj.nextLine();
            switch (player2) {
                case "" -> {
                    System.out.println("Invalid input, try again.");
                } default -> {
                    proceed = true;
                }
            }
        } while (players == 2 && !proceed);

        do {
            proceed = false;
            
            //Her skal vi lave en metode der henter (4) random pokemon fra en liste af pokemon.
            //pokeFetch(4); 

            Pokemon[] rMon = new Pokemon[4];
            for (int i = 0; i < 4; i++) {  
                rMon[i] = new Pokemon(pokeFetch(0), stringToIntConv(pokeFetch(1)), stringToIntConv(pokeFetch(2)), 
                stringToIntConv(pokeFetch(3)), stringToIntConv(pokeFetch(4)), stringToIntConv(pokeFetch(5)), 
                stringToIntConv(pokeFetch(6)), stringToIntConv(pokeFetch(7)), pokeFetch(8), pokeFetch(9));
            }

            System.out.println("Player 1, pick your Pokemon.");
            System.out.println("1: " + rMon[0].getPokeName());
            System.out.println("2: " + rMon[1].getPokeName());
            System.out.println("3: " + rMon[2].getPokeName());
            System.out.println("4: " + rMon[3].getPokeName());
            String player1Pokemon = myObj.nextLine();

            switch (player1Pokemon) {
                case "1", "2", "3", "4" -> {
                    proceed = true;
                } default -> {
                    System.out.println("Invalid input, try again.");
                }
            }

        } while (players == 2 && !proceed);

    }

    public int stringToIntConv(String s) {
        int foo = 0;
            try {
                foo = Integer.parseInt(s);
                return foo;
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return foo;
        }
    }

    public String pokeFetch(int info) {
        java.util.Random r = new java.util.Random();
        int low = 1;
        int high = 151;
        int result = r.nextInt(high - low) + low;
        String line = "";

        try {
            BufferedReader reader = new BufferedReader(new FileReader("PokeData.txt"));
            for (int x = 0; x < (result * 10 + info) ; x++) {
                line = reader.readLine();
            }
            return line;

        } catch (IOException e) {
            e.printStackTrace();
            return "0";
        }
    }
}
