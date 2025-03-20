package com.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // This iss apparently cleaner and out closes the scanner, after usage in method block
        try (Scanner myScanner = new Scanner(System.in)) {
        Interface myInterface = new Interface(myScanner);
        /*
        p1.addAPokemon(myScanner, myInterface, 1200);
        p1.getPokemonFromPlayer(0).setASpecificPokemonsMove(0, myInterface.getASpecificMove(myScanner, "Thunder Wave"));
        p2.addAPokemon(myScanner, myInterface, 1200);
        p2.getPokemonFromPlayer(0).setASpecificPokemonsMove(0, myInterface.getASpecificMove(myScanner, "Thunder Wave"));
        myInterface.startBattleTwoPlayers(p1, p1, myScanner);
        p2.getPokemonFromPlayer(0).getASpecificPokemonsMove(0).displayWAYToMuchInfo();
        */
        }
    }
}
// I'll likely have Players as a list or ArrayList, for when there will be multiple people playing
