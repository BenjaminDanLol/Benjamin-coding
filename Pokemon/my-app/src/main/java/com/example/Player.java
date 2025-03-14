package com.example;

import java.util.Scanner;

public class Player {
    private String playerName;
    Pokemon[] playersPokemon = new Pokemon[6];
    // Maybe make a hashMap, where the entry is based off the Pokemon. That way the pokemon can "own" the moves.
    public Player() {
        // Ensuring that there are 6 empty Pokemon slots each time Player is invoked, I could even have it as a param.
        for (int i = 0, n = playersPokemon.length; i < n; i++) {
            playersPokemon[i] = new Pokemon();
        }
    }
    public void addAPokemon(int index, Pokemon thePokemon) {
        if (index >= 7) {
            System.out.println("There are currently only 6 Pokemon available in this version!");
            return;
        }
        if (playersPokemon[index - 1].PokeName.equals("Pokemon shouldn't exist"))  {
            // The player has in this case simply added a Pokemon, without overwriting any current mons.
            playersPokemon[index - 1] = thePokemon;
        } else {
            System.out.println(playerName + " has slaughtered " + playersPokemon[index - 1].PokeName +
            " and gived its' remains to his new pet: " + thePokemon.PokeName);
            removeAPokemonFromPlayerByName(thePokemon.PokeName);
            playersPokemon[index - 1] = thePokemon;
        }
    }


    public Pokemon getPokemonFromPlayer(int index) {
        return playersPokemon[index - 1];
    }

    public void displayASpecificPokemon(int spotForPokemonInArray, Scanner myScanner) {
        // So for the first pokemon just type 1, it will be adjusted
        playersPokemon[spotForPokemonInArray - 1].displayPokeInfo();
    }

    // This is a quick fix. But will also introduce a bug, that will remove the first pokemon with that name.
    // Instead of doing it by positioning.
    // TODO: Should later do it by array index position. 
    public void removeAPokemonFromPlayerByName(String thePokemonsName) {
        for (int i = 0, n = playersPokemon.length; i < n; i++) {
            if (playersPokemon[i].PokeName.equals(thePokemonsName)) {
                playersPokemon[i].resetPokemon();
                System.out.println(playerName + " lost their: " + thePokemonsName);
                return;
            }
        }
    }

    public void displayPokemonTeam() {
        int i = 0;
        while (!playersPokemon[i].PokeName.equals("Pokemon shouldn't exist")) {
            playersPokemon[i].displayPokeInfo();
            i++;
        }
    }

}

        