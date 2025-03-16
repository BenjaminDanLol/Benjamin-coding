package com.example;

import java.util.Scanner;

public class Player {
    private String playerName;
    Pokemon[] playersPokemon = new Pokemon[6];
    Pokemon emptyPokemon = new Pokemon();
    public Player(String pName) {
        for (int i = 0, n = playersPokemon.length; i < n; i++) {
            playersPokemon[i] = new Pokemon();
        }
        playerName = pName;
    }

    public int howManyPokemonDoesPlayerActuallyHave() {

        int i = 0;
        while (!playersPokemon[i].PokeName.equals("Pokemon shouldn't exist") && playersPokemon[i].PokeName != null) {
            i++;
        }
        return i;
    }
    public void addAPokemon(int index, Pokemon thePokemon) {
        if (index > 5) {
            System.out.println("There are currently only 6 Pokemon available in this version!");
            return;
        }
        if (playersPokemon[index].PokeName.equals("Pokemon shouldn't exist"))  {
            playersPokemon[index] = thePokemon;
        } else {
            System.out.println(playerName + " has slaughtered " + playersPokemon[index].PokeName +
            " and gived its' remains to his new pet: " + thePokemon.PokeName);
            removeAPokemonFromPlayerByName(thePokemon.PokeName);
            // Level should be kept though, the level itself should be an attribute of Player object
            playersPokemon[index] = emptyPokemon;
            playersPokemon[index] = thePokemon;
        }
    }
    public Pokemon selectAPokemonViaName(String nameOfPokemon) {
        for (Pokemon thePokemon : playersPokemon) {
            if (thePokemon.PokeName.equals(nameOfPokemon)) {
                System.out.println(playerName + " chooses " + thePokemon.PokeName);
                return thePokemon;
            }
        }
        System.out.println("No such Pokemon exists");
        return null;
    }
    public String[] allOfPokemonNames() {
        int i = 0;
        int amountOfPokemonDisplayed = 0;
        /*
        Ugly function but the first thing I could think about as to ensure I have an array of
        the proper sizing. And likewise only displaying the correct pokemon
         */
        for (Pokemon e : playersPokemon) {
            if (!e.PokeName.equals("Pokemon shouldn't exist") && e.PokeName != null) {
                amountOfPokemonDisplayed++;
            }
        }
        String[] playersPokemonAsStringArray = new String[amountOfPokemonDisplayed];
        for (Pokemon e : playersPokemon) {
            if (!e.PokeName.equals("Pokemon shouldn't exist") && e.PokeName != null) {
                playersPokemonAsStringArray[i] = e.PokeName; 
            }
            i++;
        }
        return playersPokemonAsStringArray;
    }
    public Pokemon[] getPlayersPokemon(){
        return playersPokemon;
    }

    public String getPlayerName(){
        return playerName;
    }

    public Pokemon getPokemonFromPlayer(int index) {
        return playersPokemon[index];
    }

    public void displayASpecificPokemon(int spotForPokemonInArray, Scanner myScanner) {
        playersPokemon[spotForPokemonInArray].displayPokeInfo();
    }

    // This is a quick fix. But will also introduce a bug, that will remove the first pokemon with that name.
    // Instead of doing it by positioning.
    // TODO: Should later do it by array index position. 
    public void removeAPokemonFromPlayerByName(String thePokemonsName) {
        for (int i = 0, n = playersPokemon.length; i < n; i++) {
            if (playersPokemon[i].PokeName.equals(thePokemonsName)) {
                playersPokemon[i] = emptyPokemon;
                System.out.println(playerName + " lost their: " + thePokemonsName);
                return;
            }
        }
    }
}

        