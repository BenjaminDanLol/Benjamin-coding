package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // This is apparently cleaner and out closes the scanner, after usage in method block
        try (Scanner myScanner = new Scanner(System.in)) {
        Interface myInterface = new Interface();
        Player p1 = new Player();
        p1.addAPokemon(1, myInterface.getAPokemonStandardized(1000, myScanner, 4));
        p1.addAPokemon(2, myInterface.getAPokemonStandardized(1000, myScanner, 4));
        p1.addAPokemon(1, myInterface.getAPokemonStandardized(1000, myScanner, 4));
        p1.displayPokemonTeam();
        p1.getPokemonFromPlayer(1).setASpecificPokemonsMove(1, myInterface.getASpecificMove(myScanner, "Flamethrower"), p1);
        p1.getPokemonFromPlayer(1).getASpecificPokemonsMove(1).displayMoveInfo();
        }
    }
    
}
