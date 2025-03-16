package com.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Player {
    private String playerName;
    Pokemon[] playersPokemon = new Pokemon[6];
    Pokemon emptyPokemon = new Pokemon();
    private int howManyPokemonDoesPlayerActuallyHave = 0;
    public Player(String pName) {
        for (int i = 0, n = playersPokemon.length; i < n; i++) {
            playersPokemon[i] = emptyPokemon;
        }
        playerName = pName;
    }

    public int howManyPokemonDoesPlayerActuallyHave() {

        int amount = 0;
        for (int i = 0; i < 6; i++) {
            if (!playersPokemon[i].PokeName.equals("Pokemon shouldn't exist") && playersPokemon[i].PokeName != null) {
                amount++;
            }
        }
        return amount + 1;
    }
    // The ordering is a bit weird, where the you choose the pokemon before it being added.
    // Should be the opposite especially when players team is 6, then you've chosen a replacement unknowingly
    public void addAPokemonSpecificIndex(int index, Pokemon thePokemon, Scanner myScanner) {
        // Plus one cause we're one step behind kind of.
        howManyPokemonDoesPlayerActuallyHave = howManyPokemonDoesPlayerActuallyHave() + 1;

        if (howManyPokemonDoesPlayerActuallyHave > 6) {
            System.out.println("Party of pokemon can only be 6. You are currently replacing a pokemon.");
            System.out.println("Type anything to continue. Type no to cancel! ");
            myScanner.nextLine();
            String yesno = myScanner.nextLine().trim().toLowerCase();
            if (yesno.equals("no")) {
                System.out.println("No new pokemon was chosen.");
                return;
            }

            int pChoice = Interface.presentOptionsIndex(allOfPokemonNames(), 6, myScanner, playerName);
            playersPokemon[pChoice] = emptyPokemon;
            // thePokemon is chosen before the player has chosen which pokemon to replace, so the ordering is akward.
            playersPokemon[pChoice] = thePokemon;
        }
        else if (playersPokemon[index].PokeName.equals("Pokemon shouldn't exist"))  {
            playersPokemon[index] = thePokemon;
            System.out.println(playerName + " hAs pOkEmOn: " + howManyPokemonDoesPlayerActuallyHave);
            System.out.println(Arrays.toString(allOfPokemonNames()));
        }
    }
    public void addAPokemon(Pokemon thePokemon, Scanner myScanner) {
        if (howManyPokemonDoesPlayerActuallyHave == 0) {
            addAPokemonSpecificIndex(0, thePokemon, myScanner);
        } else {
            addAPokemonSpecificIndex(howManyPokemonDoesPlayerActuallyHave, thePokemon, myScanner);
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

    public void displayASpecificPokemon(int spotForPokemonInArray) {
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
                howManyPokemonDoesPlayerActuallyHave--;
                return;
            }
        }
    }
}

        