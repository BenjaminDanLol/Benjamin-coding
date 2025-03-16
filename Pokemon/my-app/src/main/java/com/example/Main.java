package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // This is apparently cleaner and out closes the scanner, after usage in method block
        try (Scanner myScanner = new Scanner(System.in)) {
        Interface myInterface = new Interface();
        System.out.println("What will Player 1's name be?");
        String p1name = myScanner.nextLine();
        Player p1 = new Player(p1name);
        System.out.println("What will Player 2's name be?");
        String p2name = myScanner.nextLine();
        Player p2 = new Player(p2name);
        for (int i = 0; i < 10; i++) {
            p1.addAPokemon(myInterface.getAPokemonStandardized(1000, myScanner, 4, p1name), myScanner);
        }
        p1.displayASpecificPokemon(0);
        p1.getPokemonFromPlayer(0).setASpecificPokemonsMove(0, myInterface.getASpecificMove(myScanner, "Flamethrower"), p1);
        p1.getPokemonFromPlayer(0).getASpecificPokemonsMove(0).displayMoveInfo();

        System.out.println(p1name + " has " + p1.howManyPokemonDoesPlayerActuallyHave() + " pokemon!");

        p2.addAPokemon(myInterface.getAPokemonStandardized(1000, myScanner, 4, p2name), myScanner);

        p2.displayASpecificPokemon(0);
        p2.getPokemonFromPlayer(0).setASpecificPokemonsMove(0, myInterface.getASpecificMove(myScanner, "Poison Jab"), p2);
        p2.getPokemonFromPlayer(0).getASpecificPokemonsMove(0).displayMoveInfo();
        
        System.out.println(p2name + " has " + p1.howManyPokemonDoesPlayerActuallyHave() + " pokemon!");
        myInterface.startBattleTwoPlayers(p1, p2, myScanner);
        }
    }
    
}
// I'll likely have Players as a list or ArrayList, for when there will be multiple people playing
