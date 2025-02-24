import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        int a = 0;
        Charizard p1 = new Charizard();
        Venusaur p2 = new Venusaur();
        
        Scanner myObj = new Scanner(System.in);
        do {
        System.out.println("Pick move (1-4)");

        String moveChoice = myObj.nextLine();
        
            switch (moveChoice) {
            case ("1"), ("2"), ("3"), ("4"):
                System.out.println(p1.getPokeName() + " uses " + moveChoice);
                a += 1;
                break;
            default :
                System.out.println("Input valid number! Stupid!!");
            }
        }
        while (a != 1);

    }
}