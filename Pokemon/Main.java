import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Flamethrower f1 = new Flamethrower();
        int a = 0;
        Charizard p1 = new Charizard();
        Venusaur p2 = new Venusaur();
        
        
        Scanner myObj = new Scanner(System.in);
        String moveChoice;
        do {
        do {
        System.out.println("P1 turn");
        System.out.println("Pick a move!");
        System.out.println("Move 1: " + f1.getName());
        System.out.println("Move 2: " + f1.getName());


        moveChoice = myObj.nextLine();
        
            switch (moveChoice) {
            case ("1"), ("2"), ("3"), ("4"):
                System.out.println(p1.getPokeName() + " uses " + moveChoice);
                //execute the move on the victim
                a += 1;
                break;
            default :
                System.out.println("Input valid number! Stupid!!");
            }
        }
        while (a != 1);

        a = 0;

        }
        while ((p1.checkFainted() == false) && (p2.checkFainted() == false));


    }
}