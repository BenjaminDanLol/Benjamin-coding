import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        /*
         * Something is obviously wrong, but this is the setup I was thinking about.
         * Will add logic, but I'm not going to work on this project more this weekend. 
         * Got stuff to do. Good luck coding, if your reading this.
         */
        BenjaminScenario runCustomScenario = new BenjaminScenario();
        runCustomScenario.startNewBattle();
        // we need a movelist to choose from and define p1move1 etc as the picked move using the scanner. 
        Move p1move1 = new Flamethrower();
        Move p1move2 = new Flamethrower();
        Move p1move3 = new Flamethrower();
        Move p1move4 = new Flamethrower();


        Move p2move1 = new VineWhip();
        Move p2move2 = new VineWhip();
        Move p2move3 = new VineWhip();
        Move p2move4 = new VineWhip();


        int a = 0;
        Charizard p1 = new Charizard();
        Venusaur p2 = new Venusaur();
        

        Scanner myObj = new Scanner(System.in);
        String moveChoicePlayer[] = new String[2];
        String moveChoicePlayer1 = "0";
        String moveChoicePlayer2 = "0";
        moveChoicePlayer[0] = moveChoicePlayer1;
        moveChoicePlayer[1] = moveChoicePlayer2;

        int check = 0;

        do {
            do {
                System.out.println("P1, Pick a move!");
                System.out.println("Move 1: " + p1move1.getName());
                System.out.println("Move 2: " + p1move2.getName());
                System.out.println("Move 3: " + p1move3.getName());
                System.out.println("Move 4: " + p1move4.getName());

                //for (int i = 0; i > 2; i++)
                moveChoicePlayer[0] = myObj.nextLine();

                switch (moveChoicePlayer[0]) {
                    case "1", "2", "3", "4" -> {
                        check = 1;
                    }
                    default -> {
                        System.out.println("Invalid move, try again.");
                    }
                }

                while (check != 0) {
                    System.out.println("P2, Pick a move!");
                    System.out.println("Move 1: " + p2move1.getName());
                    System.out.println("Move 2: " + p2move2.getName());
                    System.out.println("Move 3: " + p2move3.getName());
                    System.out.println("Move 4: " + p2move4.getName());

                    moveChoicePlayer[1] = myObj.nextLine();

        
                    while (moveChoicePlayer[0].equals("1") && check == 1) {
                            switch (moveChoicePlayer2) {
                                case "1" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move1.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move1.getName());
                                    check += 1;
                                    break;
                                }
                                case "2" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move1.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                case "3" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move1.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                case "4" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move1.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                default -> System.out.println("Invalid move, try again.");
                            }
                    }
                    
                        while (moveChoicePlayer1.equals("2") && check == 1) {
                            switch (moveChoicePlayer2) {
                                case "1" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move1.getName());
                                    check += 1;
                                    break;
                                }
                                case "2" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                case "3" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                case "4" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                default -> System.out.println("Invalid move, try again.");
                            }
                        }

                        while (moveChoicePlayer1.equals("3") && check == 1) {
                            switch (moveChoicePlayer2) {
                                case "1" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move1.getName());
                                    check += 1;
                                    break;
                                }
                                case "2" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                case "3" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                case "4" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                default -> System.out.println("Invalid move, try again.");
                            }
                        }

                        while (moveChoicePlayer1.equals("4") && check == 1) {
                            switch (moveChoicePlayer2) {
                                case "1" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move1.getName());
                                    check += 1;
                                    break;
                                }
                                case "2" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                case "3" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                case "4" -> {
                                    System.out.println(p1.getPokeName() + " uses " + p1move2.getName());
                                    System.out.println(p2.getPokeName() + " uses " + p2move2.getName());
                                    check += 1;
                                    break;
                                }
                                default -> {
                                    System.out.println("Invalid move, try again.");
                                }
                            }
                        }
                        check = 0;
                    
                        }
                    
                    
                
                } 

            while (a != 1);

            a = 0;
            }

        while ((p1.checkFainted() == false) && (p2.checkFainted() == false));
        }
}