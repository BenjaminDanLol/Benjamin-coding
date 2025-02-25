import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // we need a movelist to choose from and define p1move1 etc as the picked move using the scanner. 
        Move p1move1 = new Flamethrower();
        Move p1move2 = new VineWhip();
        Move p1move3 = new Flamethrower();
        Move p1move4 = new VineWhip();


        Move p2move1 = new Flamethrower();
        Move p2move2 = new VineWhip();
        Move p2move3 = new Flamethrower();
        Move p2move4 = new VineWhip();


        int a = 0;
        Charizard p1 = new Charizard();
        Venusaur p2 = new Venusaur();
        

        Scanner myObj = new Scanner(System.in);
        //String moveChoicePlayer[] = new String[2];
        String moveChoicePlayer1 = null;
        String moveChoicePlayer2 = null;

        do {
            do {
                System.out.println("P1, Pick a move!");
                System.out.println("Move 1: " + p1move1.getName());
                System.out.println("Move 2: " + p1move2.getName());
                System.out.println("Move 3: " + p1move3.getName());
                System.out.println("Move 4: " + p1move4.getName());

                //for (int i = 0; i > 2; i++)
                moveChoicePlayer1 = myObj.nextLine();

                switch (moveChoicePlayer1) {
                    case ("1"), ("2"), ("3"), ("4"):
                        
                        break;
                
                    default :
                        System.out.println("Invalid move, try again.");
                        moveChoicePlayer1 = null;
                    }

                do {
                    System.out.println("P2, Pick a move!");
                    System.out.println("Move 1: " + p2move1.getName());
                    System.out.println("Move 2: " + p2move2.getName());
                    System.out.println("Move 3: " + p2move3.getName());
                    System.out.println("Move 4: " + p2move4.getName());

                    moveChoicePlayer2 = myObj.nextLine();

        
                    switch (moveChoicePlayer1) {
                        case ("1"):
                            switch (moveChoicePlayer2) {
                                case ("1"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move1.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move1.getName());
                                    a += 1;
                                    break;
                                case ("2"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move1.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                case ("3"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move1.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                case ("4"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move1.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                default :
                                    System.out.println("Invalid move, try again.");
                                }

                            break;
                    
                        case ("2"):
                            switch (moveChoicePlayer2) {
                                case ("1"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move1.getName());
                                    a += 1;
                                    break;
                                case ("2"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                case ("3"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                case ("4"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                default :
                                    System.out.println("Invalid move, try again.");
                                }

                        case ("3"):
                            System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                            switch (moveChoicePlayer2) {
                                case ("1"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move1.getName());
                                    a += 1;
                                    break;
                                case ("2"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                case ("3"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                case ("4"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                default :
                                    System.out.println("Invalid move, try again.");
                                }

                        case ("4"):
                            switch (moveChoicePlayer2) {
                                case ("1"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move1.getName());
                                    a += 1;
                                    break;
                                case ("2"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                case ("3"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                case ("4"):
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    a += 1;
                                    break;
                                default :
                                    System.out.println("Invalid move, try again.");
                                }
                    
                        default :
                        }
                    }

                while (moveChoicePlayer1 != null);
                
                moveChoicePlayer1 = null;
                moveChoicePlayer2 = null;
                } 

            while (a != 1);

            a = 0;
            }

        while ((p1.checkFainted() == false) && (p2.checkFainted() == false));
        }
}