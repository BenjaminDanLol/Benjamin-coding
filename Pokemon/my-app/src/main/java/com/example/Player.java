package com.example;


import java.util.ArrayList;
import java.util.Scanner;



public class Player {
    private String playerName;
    ArrayList<Pokemon> playersPokemon = new ArrayList<>();
    // Maybe make a hashMap, where the entry is based off the Pokemon. That way the pokemon can "own" the moves.
    public Player(){
    }
    public void addPokemon(Interface myInterface, Scanner myScanner) {
        if (playersPokemon.size() + 1 == 7) {
            // Looks weird but it's because the scanner minuses by one by default as to not confuse player
            // with arrays and such.
            System.out.println("Sorry you cannot add more Pokemon to team");
            return;
        }
        playersPokemon.add(myInterface.getAPokemonStandardized(2000, myScanner, 4));
    }

    public Pokemon getPokemonFromPlayer(int index) {
        return playersPokemon.get(index - 1);
    }

    public void displayASpecificPokemon(int spotForPokemonInArray, Scanner myScanner) {
        // So for the first pokemon just type 1, it will be adjusted
        playersPokemon.get(spotForPokemonInArray - 1).displayPokeInfo();;
    }
    public void displayPokemonTeam() {
        for (int i = 0; i < playersPokemon.size(); i++) {
            playersPokemon.get(i).displayPokeInfo();
        }
    }

}

        