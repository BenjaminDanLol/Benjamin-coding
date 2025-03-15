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

        p1.addAPokemon(1, myInterface.getAPokemonStandardized(1000, myScanner, 4, p1name));
        p1.getPokemonFromPlayer(1).setASpecificPokemonsMove(1, myInterface.getASpecificMove(myScanner, "Flamethrower"), p1);
        p1.getPokemonFromPlayer(1).getASpecificPokemonsMove(1).displayMoveInfo();

        System.out.println(p1name + " has " + p1.getPlayersPokemon().length + " pokemon!");

        for (int i = 0, n = p1.getPlayersPokemon().length; i < n; i++) {
                if (p1.getPokemonFromPlayer(i + 1).PokeName.equals("Pokemon shouldn't exist")) {
                    break;
                }
            System.out.println(p1name + "'s " + p1.getPokemonFromPlayer(i + 1).PokeName + " has " + p1.getPokemonFromPlayer(i + 1).howManyMovesDoesPokemonHave() + " moves");
            }

        p2.addAPokemon(1, myInterface.getAPokemonStandardized(1000, myScanner, 4, p2name));
        p2.getPokemonFromPlayer(1).setASpecificPokemonsMove(1, myInterface.getASpecificMove(myScanner, "Poison Jab"), p2);
        p2.getPokemonFromPlayer(1).getASpecificPokemonsMove(1).displayMoveInfo();
        
        System.out.println(p2name + " has " + p1.getPlayersPokemon().length + " pokemon!");
        for (int i = 0, n = p2.getPlayersPokemon().length; i < n; i++) {
                if (p2.getPokemonFromPlayer(i + 1).PokeName.equals("Pokemon shouldn't exist")) {
                    break;
                }
            System.out.println(p2name + "'s " + p2.getPokemonFromPlayer(i + 1).PokeName + " has " + p2.getPokemonFromPlayer(i + 1).howManyMovesDoesPokemonHave() + " moves");
            }
        
        myInterface.startBattleTwoPlayers(p1, p2, myScanner);
        }
    }
    
}
// I'll likely have Players as a list or ArrayList, for when there will be multiple people playing
