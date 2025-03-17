package com.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private String playerName;
    Pokemon[] playersPokemon = new Pokemon[6];
    Pokemon emptyPokemon = new Pokemon();
    private int howManyPokemonDoesPlayerActuallyHave = 0;
    public Player(Scanner myScanner) {
        while (true) { 
            System.out.println("What will your name be player? ");
            playerName = myScanner.nextLine().trim();
            System.out.println("Playername will be " + playerName + " are you sure?");
            System.out.println("yes to confirm:");
            if (myScanner.nextLine().trim().toLowerCase().equals("yes")) {
                break;
            }
        }
            for (int i = 0, n = playersPokemon.length; i < n; i++) {
                playersPokemon[i] = emptyPokemon;
            }
    }
    public int howManyPokemonDoesPlayerActuallyHave() {

        int amount = 0;
        for (int i = 0; i < 6; i++) {
            if (!playersPokemon[i].PokeName.equals("Pokemon shouldn't exist") && playersPokemon[i].PokeName != null) {
                amount++;
            }
        }
        return amount;
    }
    public void addAPokemon(Scanner myScanner, Interface myInterface, int filter) {
        /*
         * Things to remember howManyPokemonDoesPlayerActuallyHave is accurate now, therefor no changes
         * are applied, and it must be checked before adding a pokemon. So the code is less error prone.
         * This method is used to add a randomized pokemon, therefor if it should be a specific pokemon
         * then I would have to add a seperate function for that.
         * The function getAPokemonStandardized has 2 things that are sort of weird, but will be used in game.
         * 1. they have a pool to expect, where from the rng presentations of pokemon come, since Ghost/Dragon
         * only have 4 pokemon total, it needs to be 4. So in practice it isn't really that randomized.
         * 2. The filter is used to determine how the sorting of the pokemon list should go i.e. should
         * it look for the highest value pokemon, and determine the pool from there, or should it start from
         * the bottom? These effects will become more pronounced as the game has more Pokemon.
         */
        howManyPokemonDoesPlayerActuallyHave = howManyPokemonDoesPlayerActuallyHave();
        if (howManyPokemonDoesPlayerActuallyHave >= 6) {
            System.out.println("Party of pokemon can only be 6." + playerName +" is currently replacing a pokemon.");
            System.out.println("Type anything to continue. Type no to cancel! ");
            myScanner.nextLine();
            String yesno = myScanner.nextLine().trim().toLowerCase();
            if (yesno.equals("no")) {
                System.out.println("No new pokemon was chosen.");
                return;
            }
            System.out.println(playerName + " choose a pokemon from party to replace.");
            int pChoice = Interface.presentOptionsIndex(allOfPokemonNames(), 6, myScanner, playerName);
            playersPokemon[pChoice] = emptyPokemon;
            // After checking how many pokemon there are and, replacing in this case. The filter is applied and a new rand poke is added.
            playersPokemon[pChoice] = myInterface.getAPokemonStandardized(filter, myScanner, 4, playerName);
        }
        else {
            playersPokemon[howManyPokemonDoesPlayerActuallyHave] = myInterface.getAPokemonStandardized(filter, myScanner, 4, playerName);
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
        ArrayList<String> pokemonNames = new ArrayList<>();
        for (Pokemon validPokemon : playersPokemon) {
            if (validPokemon != null && !validPokemon.PokeName.equals("Pokemon shouldn't exist")){
                pokemonNames.add(validPokemon.PokeName);
            }
        }
        return pokemonNames.toArray(new String[0]);
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
}

        